import extensions.addFirebaseDependencies
import extensions.addKoinDependencies

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
}

dependencies {
    implementation(platform(Firebase.firebaseBom))
    implementation(project(Modules.cloudservices))
    addFirebaseDependencies()
    addKoinDependencies()
}