plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.COMPOSE_LIBRARY)
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.navigation))
    implementation(project(Modules.designsystem))
    implementation(project(Features.preferences))
    implementation(project(Modules.domain))
}