pluginManagement {
    repositories {
        jcenter()
        maven {
            name = "Fabric"
            setUrl("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "manhunt"
include("manhunt-api")
include("manhunt-base")
