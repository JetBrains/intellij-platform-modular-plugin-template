import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.aware.SplitModeAware

group = "org.jetbrains.plugins.template"
version = "1.0"

plugins {
    application
    id("java")
    id("org.jetbrains.intellij.platform")

    alias(libs.plugins.rpc) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
}

subprojects {
    apply(plugin = "org.jetbrains.intellij.platform.module")
}

dependencies {
    intellijPlatform {
        intellijIdea(libs.versions.intellij.platform)

        pluginModule(implementation(project(":shared")))
        pluginModule(implementation(project(":frontend")))
        pluginModule(implementation(project(":backend")))
        testFramework(TestFrameworkType.Platform)
    }
}

intellijPlatform {
    splitMode = true
    pluginInstallationTarget = SplitModeAware.PluginInstallationTarget.BOTH

    pluginVerification {
        ides {
            create(IntelliJPlatformType.IntellijIdeaUltimate, libs.versions.intellij.platform)
        }
    }
}

tasks {
    // Set up the additional debug/trace log categories if needed
    runIdeBackend {
        systemProperty("idea.log.debug.categories", "")
    }
    runIdeFrontend {
        systemProperty("idea.log.trace.categories", "")
    }
}