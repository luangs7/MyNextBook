plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.COMPOSE_LIBRARY)
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.designsystem))
    implementation(project(Modules.navigation))
    implementation(project(Modules.cloudservices))
}
