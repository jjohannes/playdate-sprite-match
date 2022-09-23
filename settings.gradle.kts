pluginManagement {
    includeBuild("gradle/playdate-picture-game")
    repositories.maven("https://gradle.onepiece.software:1443/releases")
}

buildCache {
    // local { directory = File(rootDir, "build-cache") }
    remote<HttpBuildCache> {
        url = uri("https://gradle.onepiece.software:5071/cache/")
        isPush = true
        credentials {
            username = "jendrik"
            password = providers.environmentVariable("SUPER_SECRET").get()
        }
    }
}