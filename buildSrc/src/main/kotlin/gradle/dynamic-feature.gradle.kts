package gradle

import Dependencies
import extensions.addDefaultConfig
import extensions.configureBuildTypes

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
}

android {
    addDefaultConfig()
    configureBuildTypes()
}

dependencies {
    implementation(project(Modules.app))
    androidTestImplementation(project(Modules.app))

    implementation(Dependencies.playCore)
}
