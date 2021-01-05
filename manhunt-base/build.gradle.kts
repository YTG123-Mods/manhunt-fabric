plugins {
    id("fabric-loom")
    java
}

object Globals {
    const val baseVer = "1.1.0"
    const val autoConfigVer = "3.2.2"
    const val clothConfigVer = "4.8.2"
    const val modmenuVer = "1.14.6+build.31"
    const val aegisVer = "1.0.1"
}

repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots") {
        content {
            includeGroup("me.lucko")
        }
    }
}

version = Globals.baseVer

dependencies {
    modApi("me.sargunvohra.mcmods", "autoconfig1u", Globals.autoConfigVer) {
        exclude(group = "net.fabricmc.fabric-api")
    }
    include("me.sargunvohra.mcmods", "autoconfig1u", Globals.autoConfigVer)

    modImplementation("io.github.prospector", "modmenu", Globals.modmenuVer)

    modApi("me.shedaniel.cloth", "config-2", Globals.clothConfigVer) {
        exclude(group = "net.fabricmc.fabric-api")
    }
    include("me.shedaniel.cloth", "config-2", Globals.clothConfigVer)

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
