pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/") {
            name = "Fabric"
        }

        maven(url = "https://maven.quiltmc.org/repository/snapshot") {
            name = "QuiltMC Snapshot"
        }

        maven(url = "https://maven.quiltmc.org/repository/release") {
            name = "QuiltMC Release"
        }

        gradlePluginPortal()
		mavenCentral()
    }
}

rootProject.name = "manhunt"
include("manhunt-api")
include("manhunt-base")
