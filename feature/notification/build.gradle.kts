plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.feature.notification"
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.domain)
}