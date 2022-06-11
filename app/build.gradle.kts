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
    addKoinDependencies()
    addCoroutinesDependencies()
    addCommonDependencies()
}