import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.aware.SplitModeAware.SplitModeTarget

plugins {
    id("intellij-platform-main")
}

group = "com.example"
version = "1.0"

dependencies {
    intellijPlatform {
        create(IntelliJPlatformType.IntellijIdeaUltimate, libs.versions.ij.platform) {
            useInstaller = false
        }
        pluginModule(implementation(project(":shared")))
        pluginModule(implementation(project(":frontend")))
        pluginModule(implementation(project(":backend")))
        testFramework(TestFrameworkType.Platform)
    }
}

intellijPlatform {
    splitMode = true
    splitModeTarget = SplitModeTarget.BOTH

    pluginVerification {
        ides {
            create(IntelliJPlatformType.IntellijIdeaUltimate, libs.versions.ij.platform) {
                useInstaller = false
            }
        }
    }
}