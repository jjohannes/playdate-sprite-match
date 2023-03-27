pluginManagement {
    includeBuild("gradle/playdate-picture-game")
}

dependencyResolutionManagement {
    repositories {
        ivy {
            url = uri("https://download-keycdn.panic.com/playdate_sdk")
            patternLayout {
                artifact("[module]-[revision].zip")
            }
            metadataSources {
                artifact()
            }
            content {
                includeGroup("playdate")
            }
        }
        ivy {
            url = uri("https://kenney.nl/media/pages/assets/")
            patternLayout {
                artifact("[organization]/[module].zip")
                setM2compatible(true)
            }
            metadataSources {
                artifact()
            }
            content {
                excludeGroup("playdate")
            }
        }
    }
}
