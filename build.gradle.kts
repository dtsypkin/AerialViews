val kotlinVersion: String by project

buildscript {
    val kotlinVersion = "1.8.0"

    repositories {
        mavenCentral()
        google()
        maven("https://plugins.gradle.org/m2/")
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.4")
        classpath("com.google.firebase:perf-plugin:1.4.2")
        classpath("org.jmailen.gradle:kotlinter-gradle:3.12.0")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.0")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}