import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.yuyakaido.alembic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yuyakaido.alembic"
        minSdk = 24
        targetSdk = 35

        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())
        buildConfigField(
            type = "String",
            name = "GITHUB_OAUTH_CLIENT_ID",
            value = "\"${properties["GITHUB_OAUTH_CLIENT_ID"]}\"",
        )
        buildConfigField(
            type = "String",
            name = "GITHUB_OAUTH_CLIENT_SECRET",
            value = "\"${properties["GITHUB_OAUTH_CLIENT_SECRET"]}\"",
        )
    }

    kotlin {
        jvmToolchain(21)
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.dataStore)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Local Data
    implementation(libs.protobuf.javalite)

    // Remote Data
    implementation(platform(libs.coil.bom))
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)
    implementation(libs.kotlinx.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.serialization)

    // Logging
    implementation(libs.timber)
}
