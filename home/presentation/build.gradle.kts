plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.home.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.home.domain)
}