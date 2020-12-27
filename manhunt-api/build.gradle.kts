object Globals {
    const val apiVer = "1.0.0"
}

version = Globals.apiVer

tasks {
    processResources {
        inputs.property("version", Globals.apiVer)

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to Globals.apiVer)
        }
    }
}
