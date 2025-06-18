plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
}

android {
    namespace = "com.hyunjung.core.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}