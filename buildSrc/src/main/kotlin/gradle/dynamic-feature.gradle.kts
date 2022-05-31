package gradle

import Dependencies
import extensions.addDefaultConfig

plugins {
    id("com.android.dynamic-feature")
    id("kotlin-android")
}

android {
    addDefaultConfig()
}

dependencies {
    implementation(project(Modules.app))
    androidTestImplementation(project(Modules.app))

    implementation(Dependencies.playCore)
}
