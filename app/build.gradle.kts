import extensions.addCommonDependencies
import extensions.addComposeConfig
import extensions.addComposeDependencies
import extensions.addCoroutinesDependencies
import extensions.addDefaultConfig
import extensions.addKoinDependencies
import extensions.configureBuildTypes

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

    setDynamicFeatures(setOf(":features:favorites"))
}

repositories {
    google()
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
    implementation(project(Features.login))
    implementation(project(Modules.cloudservices))
    implementation(project(Modules.firebase))
    implementation(Dependencies.playCore)
    implementation(Dependencies.splashCore)
    implementation(project(Modules.observer))
    addKoinDependencies()
    addCoroutinesDependencies()
    addCommonDependencies()
    addComposeDependencies()
    debugApi(Compose.composeTooling)
    debugApi(Compose.composeCustomview)
    debugApi(Compose.composePoolingContainer)
}
apply(plugin = GradlePlugin.GOOGLE_PLUGIN)
