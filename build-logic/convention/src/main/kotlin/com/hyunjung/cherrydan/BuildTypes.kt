package com.hyunjung.cherrydan

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                this@configureBuildTypes,
                                apiKey
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                this@configureBuildTypes,
                                commonExtension,
                                apiKey
                            )
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                this@configureBuildTypes,
                                apiKey
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                this@configureBuildTypes,
                                commonExtension,
                                apiKey
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Project.getBaseUrl(): String {
    val localProperties = gradleLocalProperties(rootDir, providers)
    val localBaseUrl = localProperties.getProperty("BASE_URL")
    if (!localBaseUrl.isNullOrEmpty()) {
        return localBaseUrl
    }

    val secretsPropertiesFile = rootProject.file("secrets.properties")
    if (secretsPropertiesFile.exists()) {
        val secretsProperties = Properties()
        secretsProperties.load(secretsPropertiesFile.inputStream())
        val secretsBaseUrl = secretsProperties.getProperty("BASE_URL")
        if (!secretsBaseUrl.isNullOrEmpty()) {
            return secretsBaseUrl
        }
    }

    return "https://cherrydan.com"
}

private fun BuildType.configureDebugBuildType(project: Project, apiKey: String) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    val baseUrl = project.getBaseUrl()
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
}

private fun BuildType.configureReleaseBuildType(
    project: Project,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    val baseUrl = project.getBaseUrl()
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

    isMinifyEnabled = false
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}