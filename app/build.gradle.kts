plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ventz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ventz"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



dependencies {


implementation(libs.appcompat)
implementation(libs.material)
implementation(libs.activity)
implementation(libs.constraintlayout)
implementation(libs.annotation)
implementation(libs.lifecycle.livedata.ktx)
implementation(libs.lifecycle.viewmodel.ktx)


testImplementation(libs.junit)
androidTestImplementation(libs.ext.junit)
androidTestImplementation(libs.espresso.core)


implementation(kotlin("script-runtime"))


implementation(libs.volley)


implementation(libs.zxing.android.embedded)
implementation(libs.xzxing.android.embedded)
implementation("com.journeyapps:zxing-android-embedded:4.3.0")


coreLibraryDesugaring(libs.desugar.jdk.libs)
coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.2")

// Multidex for large projects
implementation(libs.multidex)
implementation("androidx.multidex:multidex:2.0.1")


    
}