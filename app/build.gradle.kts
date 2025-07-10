import java.util.Properties

plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.application.compose)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
    alias(libs.plugins.mapsplatform.secrets.plugin)
}

secrets {
    defaultPropertiesFileName = "secrets.properties"
}

android {
    namespace = "com.hyunjung.cherrydan"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "BASE_URL",
            "\"https://cherrydan.com\""
        )

        val secretsFile = file("${rootProject.projectDir}/secrets.properties")
        val kakaoKey = if (secretsFile.exists()) {
            val properties = Properties()
            properties.load(secretsFile.inputStream())
            properties.getProperty("KAKAO_NATIVE_APP_KEY", "")
        } else {
            ""
        }

        manifestPlaceholders["kakaoScheme"] = "kakao$kakaoKey"
        manifestPlaceholders["kakaoKey"] = kakaoKey
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // Coil
    implementation(libs.coil.compose)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Crypto
    implementation(libs.androidx.security.crypto.ktx)

    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Location
    implementation(libs.google.android.gms.play.services.location)

    // Splash screen
    implementation(libs.androidx.core.splashscreen)

    // Timber
    implementation(libs.timber)

    // Google Play Services
    api(libs.play.app.update)
    api(libs.play.feature.delivery)
    api(libs.play.review)

    implementation(projects.feature.auth)
    implementation(projects.feature.home)
    implementation(projects.feature.notification)
    implementation(projects.feature.search)

    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.presentation.ui)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.network)
    implementation(projects.core.common)

    // Kakao SDK
    implementation(libs.kakao.auth)
    implementation(libs.kakao.common)
    implementation(libs.kakao.user)
}