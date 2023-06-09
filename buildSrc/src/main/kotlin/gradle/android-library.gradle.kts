package gradle

import extensions.addCommonDependencies
import extensions.addCoroutinesDependencies
import extensions.addDefaultConfig
import extensions.addKoinDependencies
import extensions.configureBuildTypes

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("lint.detekt")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

android {
    addDefaultConfig()
    configureBuildTypes()
}

dependencies {
    addCommonDependencies()
    addCoroutinesDependencies()
    addKoinDependencies()
}
