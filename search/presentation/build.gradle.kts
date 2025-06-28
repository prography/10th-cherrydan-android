plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.feature.ui)
}

android {
    namespace = "com.hyunjung.search.presentation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(projects.core.domain)
    implementation(projects.search.domain)
}