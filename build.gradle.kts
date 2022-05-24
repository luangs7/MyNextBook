buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}
