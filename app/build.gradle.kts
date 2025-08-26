import java.util.Properties


plugins {
    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ktlint)
}

val properties =
    Properties().apply { load(project.rootProject.file("local.properties").inputStream()) }


android {
    namespace = "com.moduro.barrier_free_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moduro.barrier_free_app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "AIR_KOREA_BASE_URL", "\"${properties["AIR_KOREA_BASE_URL"]}\"")
        buildConfigField("String", "PUBLIC_DATA_SERVICE_KEY", "\"${properties["PUBLIC_DATA_SERVICE_KEY"]}\"")

        buildConfigField("String", "LOCATION_TEMP_BASE_URL", "\"${properties["LOCATION_TEMP_BASE_URL"]}\"")

        buildConfigField("String", "LOCATION_NAME_BASE_URL", "\"${properties["LOCATION_NAME_BASE_URL"]}\"")
        buildConfigField("String", "LOCATION_NAME_SERVICE_KEY", "\"${properties["LOCATION_NAME_SERVICE_KEY"]}\"")


        buildConfigField("String", "NAVER_CLIENT_ID", "\"${properties["naver.client.id"]}\"")
        buildConfigField("String", "NAVER_CLIENT_SECRET", "\"${properties["naver.client.secret"]}\"")

        buildConfigField("String", "MODURO_BASE_URL", "\"${properties["MODURO_BASE_URL"]}\"")

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
	buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Compose
    implementation(libs.compose.compiler)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3.compose)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.lifecycle.compose)
    implementation(libs.constraintlayout.compose)

    // Kotlin
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.collections)

    // AndroidX Core
    implementation(libs.core.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.compose)
    implementation(libs.accompanist.insets)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.javax.inject)

    // Retrofit (Networking)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

    // Image Loading
    implementation(libs.coil.compose)

    // Logging
    implementation(libs.timber)

    // DataStore (Local Storage)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.encrypted.datastore.preference.ksp)
    implementation(libs.encrypted.datastore.preference.ksp.annotations)
    implementation(libs.encrypted.datastore.preference.security)

    // ViewPager Indicator
    implementation(libs.viewpager.indicator)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Accompanist System UI Controller
    implementation(libs.accompanist.systemuicontroller)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Naver Map
    implementation(libs.naver.map.compose)
    implementation(libs.naver.map.location)
    implementation(libs.play.services.location)
    implementation("com.naver.maps:map-sdk:3.22.0")

    //사진 등록시 필요
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("io.coil-kt:coil-compose:2.4.0")

    //실시간 경도 위도 받아오기
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("androidx.browser:browser:1.8.0")
    
    implementation("androidx.compose.runtime:runtime-livedata")
}