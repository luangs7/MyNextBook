package extensions

import AnnotationProcessor
import Compose
import Dependencies
import Firebase
import Modules
import Tests
import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.kotlin.dsl.project

/**
 * Adds the Compose dependencies on Gradle.
 */

fun DependencyHandler.addCommonDependencies() {
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.kotlin)
    implementation(Compose.pagingCompose)
    implementation(Dependencies.animationNavController)
    kapt(AnnotationProcessor.lifecycle)
    testImplementation(project(Modules.tests))
    testImplementation(Tests.mockk)
    androidTestImplementation(project(Modules.tests))
    implementation(project(Modules.tests))
    api(Dependencies.lifecycleLivedata)
}

fun DependencyHandler.addKoinDependencies() {
    implementation(Dependencies.koinAndroid)
    implementation(Dependencies.koinCompose)
}

fun DependencyHandler.addDesignDependencies() {
    api(Dependencies.appCompat)
    api(Dependencies.materialDesign)
    api(Dependencies.circleimageview)
    api(Dependencies.lifecycleKtx)
}

fun DependencyHandler.addComposeDependencies() {
    implementation(platform(Compose.composeBom))
    implementation(Compose.composeUi)
    implementation(Compose.composeMaterial)
    implementation(Compose.composePreview)
    implementation(Compose.composeActivity)
    implementation(Compose.navigationCompose)
    implementation(Compose.coilKt)
    implementation(Compose.lottieCompose)
    implementation(Compose.constraintLayout)
    implementation(Dependencies.lifecycleCompose)
    androidTestImplementation(Tests.composeJunit)
    androidTestImplementation(Tests.composeUITest)
}

fun DependencyHandler.addCoroutinesDependencies() {
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
}

fun DependencyHandler.addRoomDependencies() {
    implementation(Dependencies.room)
    implementation(Dependencies.roomCoroutines)
    kapt(AnnotationProcessor.room)
}

fun DependencyHandler.addTestDependencies() {
    api(Tests.coroutines)
    api(Tests.coreTesting)
    api(Tests.jUnit)
    api(Tests.jUnitExt)
}

fun DependencyHandler.addTestApiDependencies() {
    testApi(Tests.androidxCore)
    testApi(Tests.coroutines)
    testApi(Tests.coreTesting)
    testApi(Tests.jUnit)
    testApi(Tests.jUnitExt)
    testApi(Tests.koinTest)
}

private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.androidTestApi(dependencyNotation: Any): Dependency? =
    add("androidTestApi", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.testApi(dependencyNotation: Any): Dependency? =
    add("testApi", dependencyNotation)

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, "androidTestImplementation", dependencyNotation, dependencyConfiguration
)

fun DependencyHandler.addFirebaseDependencies() {
    implementation(Firebase.firebaseMessaging)
    implementation(Firebase.firebaseAnalytics)
    implementation(Firebase.firebaseInApp)
    implementation(Firebase.firebaseAuth)
    implementation(Firebase.firebaseConfig)
    implementation(Firebase.firebaseFirestore)
}