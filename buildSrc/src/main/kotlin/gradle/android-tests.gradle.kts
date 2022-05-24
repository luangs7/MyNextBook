package gradle

import extensions.addTestDependencies
import extensions.addTestApiDependencies
import extensions.addDefaultConfig
import extensions.configureBuildTypes

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

dependencies{
    addTestDependencies()
    addTestApiDependencies()
}

