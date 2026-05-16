package com.example.videoblur

import android.content.Context
import android.graphics.*
import android.media.*
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.CountDownLatch

/**
 * Processes a video file frame-by-frame:
 *  1. Decodes each frame via MediaCodec
 *  2. Detects all text regions via ML Kit Text Recognition
 *  3. Blurs those regions using a down-scale / up-scale trick
 *  4. Re-encodes to H.264 MP4 via MediaCodec + MediaMuxer
 */
class VideoBlurProcessor(private val context: Context) {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    companion object {
        private const val BLUR_FACTOR       = 12
        private const val CODEC_TIMEOUT_US  = 10_000L
        private const val ENCODER_BIT_RATE  = 8_000_000
        private const val ENCODER_FPS       = 30
        private const val ENCODER_IFRAME    = 1
    }

    /**
     * @param inputUri  source video URI (e.g. from a file picker / gallery intent)
     * @param outputFile destination MP4 file (created or overwritten)
     * @param onProgress 0.0..1.0 progress callback, called on the processing thread
     */
    suspend fun process(
        inputUri: Uri,
        outputFile: File,
        onProgress: (Float) -> Unit = {}
    ) = withContext(Dispatchers.Default) {

        val extractor = MediaExtractor().apply { setDataSource(context, inputUri, null) }
        val (videoTrack, inputFormat) = findVideoTrack(extractor)
            ?: throw IllegalArgumentException("Input has no video track")

        extractor.selectTrack(videoTrack)

        val width    = inputFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height   = inputFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val duration = inputFormat.getLong(MediaFormat.KEY_DURATION)
        val mime     = inputFormat.getString(MediaFormat.KEY_MIME)!!

        // Decoder – uses raw byte buffers so we can inspect pixel data
        val decoder = MediaCodec.createDecoderByType(mime).apply {
            configure(inputFormat, null, null, 0)
            start()
        }

        // Encoder – NV21 input, AVC output
        val encoderFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
            setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar)
            setInteger(MediaFormat.KEY_BIT_RATE,          ENCODER_BIT_RATE)
            setInteger(MediaFormat.KEY_FRAME_RATE,         ENCODER_FPS)
            setInteger(MediaFormat.KEY_I_FRAME_INTERVAL,   ENCODER_IFRAME)
        }
        val encoder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC).apply {
            configure(encoderFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            start()
        }

        if (outputFile.exists()) outputFile.delete()
        val muxer = MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)
        var muxerTrack   = -1
        var muxerStarted = false

        val decoderInfo = MediaCodec.BufferInfo()
        val encoderInfo = MediaCodec.BufferInfo()
        var inputExhausted  = false
        var decoderFinished = false

        try {
            while (isActive && !decoderFinished) {

                // ── Feed packets to decoder ───────────────────────────────────
                if (!inputExhausted) {
                    val inIdx = decoder.dequeueInputBuffer(CODEC_TIMEOUT_US)
                    if (inIdx >= 0) {
                        val buf  = decoder.getInputBuffer(inIdx)!!
                        val size = extractor.readSampleData(buf, 0)
                        if (size < 0) {
                            decoder.queueInputBuffer(inIdx, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                            inputExhausted = true
                        } else {
                            decoder.queueInputBuffer(inIdx, 0, size, extractor.sampleTime, 0)
                            extractor.advance()
                        }
                    }
                }

                // ── Pull decoded frame from decoder ───────────────────────────
                val outIdx = decoder.dequeueOutputBuffer(decoderInfo, CODEC_TIMEOUT_US)
                if (outIdx >= 0) {
                    val isEos = (decoderInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0

                    if (decoderInfo.size > 0) {
                        val raw    = decoder.getOutputBuffer(outIdx)!!
                        val fmt    = decoder.outputFormat
                        val bitmap = yuvBufferToBitmap(raw, fmt, width, height)
                        val blurred = blurTextRegions(bitmap)

                        feedToEncoder(encoder, blurred, decoderInfo.presentationTimeUs)
                        drainEncoder(encoder, encoderInfo, muxer, muxerTrack, muxerStarted) { track, started ->
                            muxerTrack   = track
                            muxerStarted = started
                        }

                        bitmap.recycle()
                        blurred.recycle()

                        val progress = (decoderInfo.presentationTimeUs.toFloat() / duration).coerceIn(0f, 1f)
                        onProgress(progress)
                    }

                    decoder.releaseOutputBuffer(outIdx, false)

                    if (isEos) {
                        encoder.signalEndOfInputStream()
                        decoderFinished = true
                        // drain remaining encoder output
                        drainEncoder(encoder, encoderInfo, muxer, muxerTrack, muxerStarted, drain = true) { track, started ->
                            muxerTrack   = track
                            muxerStarted = started
                        }
                    }
                }
            }
        } finally {
            runCatching { decoder.stop() }
            runCatching { decoder.release() }
            runCatching { encoder.stop() }
            runCatching { encoder.release() }
            runCatching { extractor.release() }
            runCatching { if (muxerStarted) muxer.stop() }
            runCatching { muxer.release() }
        }

        onProgress(1f)
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private fun findVideoTrack(extractor: MediaExtractor): Pair<Int, MediaFormat>? {
        for (i in 0 until extractor.trackCount) {
            val fmt = extractor.getTrackFormat(i)
            if (fmt.getString(MediaFormat.KEY_MIME)?.startsWith("video/") == true) return i to fmt
        }
        return null
    }

    private fun yuvBufferToBitmap(buf: ByteBuffer, fmt: MediaFormat, w: Int, h: Int): Bitmap {
        val bytes = ByteArray(buf.remaining()).also { buf.get(it) }
        // Treat as NV12 (most common decoder output); swap UV for NV21
        val nv21 = nv12ToNv21(bytes, w, h)
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, w, h, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, w, h), 92, out)
        return BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.size())
            .copy(Bitmap.Config.ARGB_8888, true)
    }

    private fun nv12ToNv21(src: ByteArray, w: Int, h: Int): ByteArray {
        val dst = src.copyOf()
        val frameSize = w * h
        var i = frameSize
        while (i < src.size - 1) {
            dst[i]     = src[i + 1]
            dst[i + 1] = src[i]
            i += 2
        }
        return dst
    }

    /** Runs ML Kit synchronously (via CountDownLatch) and blurs each text bounding box. */
    private fun blurTextRegions(src: Bitmap): Bitmap {
        val result = src.copy(Bitmap.Config.ARGB_8888, true)
        val latch  = CountDownLatch(1)
        var boxes  = emptyList<Rect>()

        recognizer.process(InputImage.fromBitmap(src, 0))
            .addOnSuccessListener { visionText ->
                boxes = visionText.textBlocks.flatMap { block ->
                    block.lines.mapNotNull { line -> line.boundingBox ?: block.boundingBox }
                }
                latch.countDown()
            }
            .addOnFailureListener { latch.countDown() }

        latch.await()

        val canvas = Canvas(result)
        for (box in boxes) {
            val safe = Rect(
                box.left.coerceAtLeast(0),
                box.top.coerceAtLeast(0),
                box.right.coerceAtMost(result.width),
                box.bottom.coerceAtMost(result.height)
            )
            if (safe.isEmpty || safe.width() < 2 || safe.height() < 2) continue
            val patch   = Bitmap.createBitmap(result, safe.left, safe.top, safe.width(), safe.height())
            val smallW  = (patch.width  / BLUR_FACTOR).coerceAtLeast(1)
            val smallH  = (patch.height / BLUR_FACTOR).coerceAtLeast(1)
            val small   = Bitmap.createScaledBitmap(patch, smallW, smallH, true)
            val blurred = Bitmap.createScaledBitmap(small, patch.width, patch.height, true)
            canvas.drawBitmap(blurred, null, safe, null)
            patch.recycle(); small.recycle(); blurred.recycle()
        }
        return result
    }

    private fun feedToEncoder(encoder: MediaCodec, bitmap: Bitmap, presentationUs: Long) {
        val inIdx = encoder.dequeueInputBuffer(CODEC_TIMEOUT_US)
        if (inIdx < 0) return
        val buf  = encoder.getInputBuffer(inIdx) ?: return
        buf.clear()
        val nv21 = bitmapToNv21(bitmap)
        buf.put(nv21)
        encoder.queueInputBuffer(inIdx, 0, nv21.size, presentationUs, 0)
    }

    private fun drainEncoder(
        encoder: MediaCodec,
        info: MediaCodec.BufferInfo,
        muxer: MediaMuxer,
        currentTrack: Int,
        currentStarted: Boolean,
        drain: Boolean = false,
        update: (Int, Boolean) -> Unit
    ) {
        var muxerTrack   = currentTrack
        var muxerStarted = currentStarted

        while (true) {
            val outIdx = encoder.dequeueOutputBuffer(info, if (drain) CODEC_TIMEOUT_US * 5 else 0)
            when {
                outIdx == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    muxerTrack   = muxer.addTrack(encoder.outputFormat)
                    muxerStarted = true
                    muxer.start()
                    update(muxerTrack, muxerStarted)
                }
                outIdx >= 0 -> {
                    if ((info.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG) == 0 && info.size > 0) {
                        val buf = encoder.getOutputBuffer(outIdx)!!
                        buf.position(info.offset)
                        buf.limit(info.offset + info.size)
                        if (muxerStarted) muxer.writeSampleData(muxerTrack, buf, info)
                    }
                    encoder.releaseOutputBuffer(outIdx, false)
                    if (!drain || (info.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) break
                }
                else -> break
            }
        }
    }

    private fun bitmapToNv21(bitmap: Bitmap): ByteArray {
        val argb = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(argb, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val w = bitmap.width
        val h = bitmap.height
        val frameSize = w * h
        val nv21 = ByteArray(frameSize + frameSize / 2)
        var yIdx  = 0
        var uvIdx = frameSize
        for (j in 0 until h) {
            for (i in 0 until w) {
                val px = argb[j * w + i]
                val r  = (px shr 16) and 0xFF
                val g  = (px shr 8)  and 0xFF
                val b  =  px         and 0xFF
                val y  = ((66 * r + 129 * g +  25 * b + 128) shr 8) + 16
                nv21[yIdx++] = y.coerceIn(0, 255).toByte()
                if (j % 2 == 0 && i % 2 == 0) {
                    val cb = ((-38 * r -  74 * g + 112 * b + 128) shr 8) + 128
                    val cr = ((112 * r -  94 * g -  18 * b + 128) shr 8) + 128
                    nv21[uvIdx++] = cb.coerceIn(0, 255).toByte()
                    nv21[uvIdx++] = cr.coerceIn(0, 255).toByte()
                }
            }
        }
        return nv21
    }
}
