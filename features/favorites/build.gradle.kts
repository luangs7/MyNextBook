import extensions.addComposeConfig
import extensions.addComposeDependencies
import extensions.addKoinDependencies

plugins {
    id(GradlePlugin.DYNAMIC_FEATURE)
}

android {
    addComposeConfig()
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.navigation))
    implementation(project(Modules.designsystem))
    implementation(project(Modules.domain))
    addComposeDependencies()
    addKoinDependencies()
    debugApi(Compose.composeTooling)
    debugApi(Compose.composeCustomview)
    debugApi(Compose.composePoolingContainer)
}