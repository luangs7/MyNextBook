package extensions

import com.android.build.api.dsl.CommonExtension

fun CommonExtension<*, *, *, *>.addDefaultConfig() {
    compileSdk = ConfigData.compileSdkVersion
    defaultConfig {
        compileSdk = ConfigData.compileSdkVersion
        minSdk = ConfigData.minSdkVersion

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

fun CommonExtension<*, *, *, *>.addComposeConfig() {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeVersion
    }

    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

fun CommonExtension<*, *, *, *>.configureBuildTypes() {
    flavorDimensions.add("api")
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    productFlavors {
        create("prod") {
            buildConfigField("Boolean","IS_MOCK","false")
            dimension = "api"
        }

        create("mock"){
            buildConfigField("Boolean","IS_MOCK","true")
            dimension = "api"
        }
        forEach {
            it.buildConfigField("String","API_URL","\"https://api.github.com/\"")
        }
    }
}

