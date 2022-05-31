
plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
}

dependencies {
    implementation(project(Modules.common))
    implementation(project(Modules.repository))
    implementation(Dependencies.dataStore)
    implementation(Dependencies.gson)
}