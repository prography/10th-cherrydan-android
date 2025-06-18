plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
    alias(libs.plugins.hyunjung.cherrydan.android.room)
}

android {
    namespace = "com.hyunjung.core.database"
}

dependencies {
    implementation(projects.core.domain)
}