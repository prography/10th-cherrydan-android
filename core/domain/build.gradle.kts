plugins {
    alias(libs.plugins.hyunjung.cherrydan.android.library)
}

android{
    namespace = "com.hyunjung.core.domain"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)
    implementation(libs.kotlinx.coroutines.core)
}