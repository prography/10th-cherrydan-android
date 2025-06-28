plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.jvm.ktor)
}

android {
    namespace = "com.hyunjung.search.data"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.search.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}