plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "dev.baseio"
version "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.6.1")
}

android {
    compileSdk = (33)
    defaultConfig {
        applicationId = "dev.baseio.android"
        minSdk = (24)
        targetSdk = (33)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}