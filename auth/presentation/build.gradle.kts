plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.auth.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}