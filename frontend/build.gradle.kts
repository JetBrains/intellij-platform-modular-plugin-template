import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

version = "1.0"
plugins {
    id("intellij-platform-remdev")
    alias(libs.plugins.compose.compiler)
}

dependencies {
    intellijPlatform {
        create(IntelliJPlatformType.IntellijIdeaUltimate, libs.versions.ij.platform) {
            useInstaller = false
        }
        pluginModule(implementation(project(":shared")))
        bundledModules(
            "intellij.platform.frontend",
            "intellij.libraries.skiko",
            "intellij.libraries.compose.foundation.desktop",
            "intellij.libraries.compose.runtime.desktop",
            "intellij.platform.jewel.foundation",
            "intellij.platform.jewel.ui",
            "intellij.platform.jewel.ideLafBridge",
        )
    }
}