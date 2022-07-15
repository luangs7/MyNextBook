import extensions.*

plugins {
    id(GradlePlugin.ANDROID_APPLICATION)
    id(GradlePlugin.KOTLIN_ANDROID)
    kotlin("kapt")
}

android {
    addDefaultConfig()
    addComposeConfig()
    defaultConfig {
        applicationId = ConfigData.applicationId
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        targetSdk = ConfigData.targetSdkVersion
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    configureBuildTypes()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    setDynamicFeatures(setOf(":features:favorites"))
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.navigation))
    implementation(project(Modules.designsystem))
    implementation(project(Modules.remote))
    implementation(project(Modules.domain))
    implementation(project(Modules.local))
    implementation(project(Modules.datastore))
    implementation(project(Modules.repository))
    implementation(project(Features.preferences))
    implementation(project(Features.finder))
    implementation(project(Features.home))
    implementation(project(Modules.split))
    implementation(Dependencies.playCore)
    implementation(Dependencies.splashCore)
    addKoinDependencies()
    addCoroutinesDependencies()
    addCommonDependencies()
    addComposeDependencies()
    debugApi(Compose.composeTooling)
    debugApi(Compose.composeCustomview)
    debugApi(Compose.composePoolingContainer)
}