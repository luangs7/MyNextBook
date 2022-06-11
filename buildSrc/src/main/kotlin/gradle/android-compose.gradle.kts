package gradle

import extensions.addComposeConfig
import extensions.addComposeDependencies

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    addComposeConfig()
}

dependencies {
    addComposeDependencies()
    debugApi("androidx.compose.ui:ui-tooling:1.1.1")
    debugApi("androidx.customview:customview:1.2.0-alpha01")
    debugApi("androidx.customview:customview-poolingcontainer:1.0.0-beta02")
}