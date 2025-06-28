plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.notification.presentation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.domain)
    implementation(projects.notification.domain)
}