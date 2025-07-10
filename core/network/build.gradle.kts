import java.util.Properties

plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}

android {
    namespace = "com.hyunjung.cherrydan.core.network"

    defaultConfig {
        val secrets = Properties().apply {
            load(file("${rootProject.projectDir}/secrets.properties").inputStream())
        }
        buildConfigField("String", "BASE_URL", "\"${secrets["BASE_URL"]}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Crypto
    implementation(libs.androidx.security.crypto.ktx)

    implementation(libs.bundles.koin)

    // Timber
    implementation(libs.timber)

    implementation(projects.core.common)
    implementation(projects.core.model)

    // Kakao SDK
    implementation(libs.kakao.auth)
    implementation(libs.kakao.common)
    implementation(libs.kakao.user)
}