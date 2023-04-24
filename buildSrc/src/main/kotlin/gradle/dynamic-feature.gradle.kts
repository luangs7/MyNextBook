package gradle

import Dependencies
import extensions.addCommonDependencies
import extensions.addCoroutinesDependencies
import extensions.addDefaultConfig
import extensions.configureBuildTypes

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
    kotlin("kapt")
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
    implementation(project(Modules.app))
    androidTestImplementation(project(Modules.app))
    addCommonDependencies()
    addCoroutinesDependencies()
    implementation(Dependencies.playCore)
}
