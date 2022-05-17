import extensions.addDesignDependencies

plugins {
    id(GradlePlugin.ANDROID_LIBRARY)
    id(GradlePlugin.COMPOSE_LIBRARY)
}

dependencies {
    addDesignDependencies()
}