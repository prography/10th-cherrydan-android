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
        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://cherrydan.com\""
        )

        val secretsFile = file("${rootProject.projectDir}/secrets.properties")
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