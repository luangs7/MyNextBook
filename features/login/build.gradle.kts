import extensions.addCoroutinesDependencies

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.COMPOSE_LIBRARY)
}

dependencies {
    implementation(project(Modules.cloudservices))
    implementation(project(Modules.common))
    implementation(project(Modules.designsystem))
    implementation(project(Modules.domain))
    implementation(project(Modules.navigation))
    implementation(Dependencies.googleAuth)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
}