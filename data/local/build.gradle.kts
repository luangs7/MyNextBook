import extensions.addRoomDependencies

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
}

dependencies {
    implementation(project(Modules.repository))
    addRoomDependencies()
}