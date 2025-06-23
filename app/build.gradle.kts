plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.yuyakaido.alembic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yuyakaido.alembic"
        minSdk = 24
        targetSdk = 35
    }

    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)

    // Networking
    implementation(libs.kotlinx.serialization)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.serialization)
}
