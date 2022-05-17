import extensions.*

plugins {
    id(GradlePlugin.ANDROID_APPLICATION)
    id(GradlePlugin.KOTLIN_ANDROID)
    id(GradlePlugin.COMPOSE)
    kotlin("kapt")
}

android {
    addDefaultConfig()
    defaultConfig {
        applicationId = ConfigData.applicationId
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        targetSdk = ConfigData.targetSdkVersion
    }
    configureBuildTypes()
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
    addKoinDependencies()
    addCoroutinesDependencies()
    addCommonDependencies()
}