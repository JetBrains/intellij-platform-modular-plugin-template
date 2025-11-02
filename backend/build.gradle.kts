import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType

version = "1.0"

plugins {
    id("intellij-platform-remdev")
}

dependencies {
    intellijPlatform {
        create(IntelliJPlatformType.IntellijIdeaUltimate, libs.versions.ij.platform) {
            useInstaller = false
        }
        pluginModule(implementation(project(":shared")))
        bundledModule("intellij.platform.rpc.backend")
        bundledModule("intellij.platform.backend")
    }
}