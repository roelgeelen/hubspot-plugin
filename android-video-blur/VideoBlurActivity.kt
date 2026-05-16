package com.example.videoblur

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.File

class VideoBlurActivity : AppCompatActivity() {

    private lateinit var btnPick:     Button
    private lateinit var btnProcess:  Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus:    TextView

    private var selectedUri: Uri? = null
    private val processor by lazy { VideoBlurProcessor(this) }

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedUri = uri
            btnProcess.isEnabled = true
            tvStatus.text = "Video geselecteerd. Druk op Verwerken."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_blur)

        btnPick     = findViewById(R.id.btnPick)
        btnProcess  = findViewById(R.id.btnProcess)
        progressBar = findViewById(R.id.progressBar)
        tvStatus    = findViewById(R.id.tvStatus)

        btnProcess.isEnabled = false

        btnPick.setOnClickListener {
            pickVideo.launch("video/*")
        }

        btnProcess.setOnClickListener {
            val uri = selectedUri ?: return@setOnClickListener
            startProcessing(uri)
        }
    }

    private fun startProcessing(uri: Uri) {
        val outputFile = File(getExternalFilesDir(null), "blurred_${System.currentTimeMillis()}.mp4")

        btnPick.isEnabled    = false
        btnProcess.isEnabled = false
        progressBar.visibility = View.VISIBLE
        progressBar.progress   = 0
        tvStatus.text = "Bezig met verwerken..."

        lifecycleScope.launch {
            try {
                processor.process(uri, outputFile) { progress ->
                    runOnUiThread {
                        progressBar.progress = (progress * 100).toInt()
                        tvStatus.text = "Verwerken: ${(progress * 100).toInt()}%"
                    }
                }

                // Make file visible in media scanner
                sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(outputFile)))

                tvStatus.text = "Klaar! Opgeslagen als: ${outputFile.name}"
                Toast.makeText(this@VideoBlurActivity, "Video opgeslagen", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                tvStatus.text = "Fout: ${e.message}"
                Toast.makeText(this@VideoBlurActivity, "Verwerking mislukt: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                btnPick.isEnabled    = true
                btnProcess.isEnabled = true
                progressBar.visibility = View.GONE
            }
        }
    }
}
