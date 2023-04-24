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

dependencies {
    implementation(project(Modules.app))
    androidTestImplementation(project(Modules.app))
    addCommonDependencies()
    addCoroutinesDependencies()
    implementation(Dependencies.playCore)
}
