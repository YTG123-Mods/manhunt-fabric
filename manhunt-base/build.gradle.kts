plugins {
    id("fabric-loom")
    java
}

object Globals {
    const val baseVer = "1.1.0"
    const val clothConfigVer = "5.0.34"
    const val modmenuVer = "2.0.3"
    const val aegisVer = "1.0.1"
}

repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
        content {
            includeGroup("me.lucko")
        }
    }
    
    maven(url = "https://maven.terraformersmc.com/releases")
    maven(url = "https://maven.shedaniel.me")
}

version = Globals.baseVer

dependencies {
    modImplementation("com.terraformersmc", "modmenu", Globals.modmenuVer)

    modApi("me.shedaniel.cloth", "cloth-config-fabric", Globals.clothConfigVer) {
        exclude(group = "net.fabricmc.fabric-api")
    }
    // include("me.shedaniel.cloth", "cloth-config-fabric", Globals.clothConfigVer)

    // Commands
    include(modApi("com.github.P03W", "Aegis", Globals.aegisVer))

    // Lucko's API
    modCompileOnly("me.lucko", "fabric-permissions-api", "0.1-SNAPSHOT")

    implementation(project(":manhunt-api"))
}

tasks {
    processResources {
        inputs.property("version", Globals.baseVer)

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to Globals.baseVer)
        }
    }
}
