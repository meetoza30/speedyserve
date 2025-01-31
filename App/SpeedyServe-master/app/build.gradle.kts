plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.speedyserve"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.speedyserve"
        minSdk = 24
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // LiveData (if needed)
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Lifecycle Runtime
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.razorpay:checkout:1.6.41")
    implementation(libs.accompanist.pager)
    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.material.icons.extended)
    implementation(libs.androidx.compose.material.material.icons.extended)

    //Retrofit
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation(libs.converter.gson)
    //noinspection GradleDependency
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.adapter.rxjava2) // Optional if using RxJava
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0") // Required for coroutines support

    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // ViewModel for Jetpack Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Optional: LiveData support
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    // Optional: SavedState module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.6")
    // Hilt Navigation for Jetpack Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}