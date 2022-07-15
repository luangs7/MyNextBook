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
    debugApi(Compose.composeTooling)
    debugApi(Compose.composeCustomview)
    debugApi(Compose.composePoolingContainer)
}