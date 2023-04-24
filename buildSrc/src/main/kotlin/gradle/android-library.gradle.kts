package gradle

import extensions.addCommonDependencies
import extensions.addCoroutinesDependencies
import extensions.addDefaultConfig
import extensions.addKoinDependencies
import extensions.configureBuildTypes
import gradle.kotlin.dsl.accessors._aaea1f5f099e0f580c925125501af1c8.java

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

android {
    addDefaultConfig()
    configureBuildTypes()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    addCommonDependencies()
    addCoroutinesDependencies()
    addKoinDependencies()
}
