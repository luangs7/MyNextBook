repositories {
    google()
    mavenCentral()
}

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    id("org.sonarqube") version "3.5.0.2730"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=requires-opt-in")
        jvmTarget = "11"
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.google.gms:google-services:4.3.15")
}

sonarqube {
    properties {
        property("sonar.projectKey", "luangs7_MyNextBook")
        property("sonar.organization", "luangs7")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
