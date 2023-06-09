plugins {
    id(GradlePlugin.KMM_MODULE)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.coroutinesCore)
            }
        }
    }
}
