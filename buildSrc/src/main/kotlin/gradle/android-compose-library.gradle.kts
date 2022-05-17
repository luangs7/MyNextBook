package gradle

import extensions.addComposeConfig
import extensions.addComposeDependencies

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    addComposeConfig()
}

dependencies {
    addComposeDependencies()
    debugApi("androidx.compose.ui:ui-tooling:1.1.1")
}