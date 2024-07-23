import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.tsgk.lab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tsgk.lab"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += ""
            }
        }
    }

    signingConfigs {
        create("release") {
            val keystoreProperties = Properties().apply {
                load(FileInputStream(rootProject.file("gradle.properties")))
            }
            keyAlias = keystoreProperties["RELEASE_KEY_ALIAS"]?.toString() ?: error("RELEASE_KEY_ALIAS not found in keystore.properties")
            keyPassword = keystoreProperties["RELEASE_KEY_PASSWORD"]?.toString() ?: error("RELEASE_KEY_PASSWORD not found in keystore.properties")
            storeFile = file(keystoreProperties["RELEASE_STORE_FILE"]?.toString() ?: error("RELEASE_STORE_FILE not found in keystore.properties"))
            storePassword = keystoreProperties["RELEASE_STORE_PASSWORD"]?.toString() ?: error("RELEASE_STORE_PASSWORD not found in keystore.properties")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}