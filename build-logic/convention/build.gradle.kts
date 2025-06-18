import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.hyunjung.cherrydan.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.set(
            listOf(
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xjvm-default=all"
            )
        )
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "hyunjung.cherrydan.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "hyunjung.cherrydan.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "hyunjung.cherrydan.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "hyunjung.cherrydan.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi") {
            id = "hyunjung.cherrydan.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("androidRoom") {
            id = "hyunjung.cherrydan.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "hyunjung.cherrydan.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("jvmKtor") {
            id = "hyunjung.cherrydan.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}