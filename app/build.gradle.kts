import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jmailen.kotlinter")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = "com.neilturner.aerialviews"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.neilturner.aerialviews"
        minSdk = 22 // to support Fire OS 5, Android v5.1, Lvl 22
        targetSdk = 33
        versionCode = 11
        versionName = "1.4.1"

        manifestPlaceholders["analyticsCollectionEnabled"] = false
        manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        manifestPlaceholders["performanceCollectionEnabled"] = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            //isMinifyEnabled = true
            //proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            buildConfigField("String", "BUILD_TIME", "\"" + System.currentTimeMillis().toString() + "\"")

        }
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            isMinifyEnabled = true

            manifestPlaceholders["analyticsCollectionEnabled"] = true
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            manifestPlaceholders["performanceCollectionEnabled"] = true
            //isDebuggable = true
            buildConfigField("String", "BUILD_TIME", "\"" + System.currentTimeMillis().toString() + "\"")
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("github") {
            dimension = "version"
        }
        create("beta") {
            dimension = "version"
            versionNameSuffix = "-beta1"
        }
        create("googleplay") {
            dimension = "version"
        }
        create("amazon") {
            dimension = "version"
            minSdk = 25
        }
    }
}

// Use different keys for different flavors of app
androidComponents {
    onVariants { variant ->
        if (variant.buildType == "release" && variant.flavorName.isNullOrEmpty()) {
            val keystoreFile = rootProject.file("signing/${variant.flavorName}.properties")
            if (keystoreFile.exists()) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystoreFile))
                val signingConfig = android.signingConfigs.create(variant.flavorName!!)
                signingConfig.storeFile = rootProject.file(keystoreProperties["storeFile"]!!)
                signingConfig.storePassword = keystoreProperties["storePassword"] as String?
                signingConfig.keyAlias = keystoreProperties["keyAlias"] as String?
                signingConfig.keyPassword = keystoreProperties["keyPassword"] as String?
                variant.signingConfig.setConfig(signingConfig)
            }
        }
    }
}

dependencies {
    // Kotlin
    val kotlinVersion = "1.8.20"
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Modern Storage
    implementation("com.google.modernstorage:modernstorage-permissions:1.0.0-alpha06")
    implementation("com.google.modernstorage:modernstorage-storage:1.0.0-alpha06")

    // Android X
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("androidx.leanback:leanback-preference:1.0.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.activity:activity-ktx:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase
    implementation("com.google.firebase:firebase-analytics-ktx:21.2.2")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.6")
    implementation("com.google.firebase:firebase-perf-ktx:20.3.1")

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // ExoPlayer
    implementation("com.google.android.exoplayer:exoplayer-core:2.18.5")

    // Kotpref
    implementation("com.chibatching.kotpref:kotpref:2.13.2")
    implementation("com.chibatching.kotpref:initializer:2.13.2")
    implementation("com.chibatching.kotpref:enum-support:2.13.2")

    // SMB
    implementation("com.hierynomus:smbj:0.11.5")

    // Memory leaks
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

    // Unit testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}


tasks.withType<Test> {
    useJUnitPlatform()
//    testLogging {
//        exceptionFormat "full"
//        events "started", "skipped", "passed", "failed"
//        showStandardStreams true
//    }
}