plugins {
    `kotlin-dsl`
    id("maven-publish")
}
repositories {
    gradlePluginPortal()
}

group = "software.onepiece"
version = "1.0"

publishing {
    repositories.maven("https://gradle.onepiece.software:1443/releases") {
        credentials {
            username = "jendrik"
            password = providers.environmentVariable("SUPER_SECRET").get()
        }
    }
}
