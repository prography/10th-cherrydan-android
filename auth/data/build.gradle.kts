plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
}

android {
    namespace = "com.hyunjung.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}