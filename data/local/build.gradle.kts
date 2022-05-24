import extensions.addRoomDependencies

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
}

dependencies {
    implementation(project(Modules.repository))
    implementation(project(Modules.common))
    implementation(Dependencies.gson)
    testImplementation(Tests.roboletric)
    addRoomDependencies()
}