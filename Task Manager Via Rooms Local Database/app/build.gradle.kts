plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.charlotte.assignment11"
    compileSdk = 36

    defaultConfig {
        applicationId = "edu.charlotte.assignment11"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.room:room-runtime:2.5.2")
    implementation(libs.room.common.jvm)
    annotationProcessor("androidx.room:room-compiler:2.5.2")

}