plugins {
    id("rpc")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    intellijPlatform {
        intellijIdea(libs.versions.intellij.platform)
    }
}