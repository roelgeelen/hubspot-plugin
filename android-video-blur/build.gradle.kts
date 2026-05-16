// Paste this into your app/build.gradle.kts (module level)

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.videoblur"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.videoblur"
        minSdk = 26          // MediaCodec COLOR_FormatYUV420Flexible stable from API 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")      // lifecycleScope
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // ML Kit – on-device text recognition (Latin script; no network needed)
    implementation("com.google.mlkit:text-recognition:16.0.1")
}
