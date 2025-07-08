plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
}

android {
    namespace = "com.hyunjung.core.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(libs.bundles.koin)
    implementation(libs.timber)

    implementation(libs.androidx.security.crypto.ktx)
}