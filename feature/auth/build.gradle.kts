plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.feature.auth"
}

dependencies {
    implementation(projects.core.domain)
}