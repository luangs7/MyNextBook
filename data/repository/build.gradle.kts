plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
}

dependencies {
    implementation(project(Modules.cloudservices))
    implementation(project(Modules.common))
    implementation(project(Modules.domain))
}