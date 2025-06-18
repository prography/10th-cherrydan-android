import com.android.build.gradle.LibraryExtension
import com.hyunjung.cherrydan.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("hyunjung.cherrydan.android.library")
            }

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}