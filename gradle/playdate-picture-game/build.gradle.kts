plugins {
    id("java-gradle-plugin")
    id("groovy")
}

gradlePlugin {
    plugins.create("playdate-picture-game") {
        id = name
        implementationClass = "org.example.PlaydatePictureGamePlugin"
    }
}
