plugins {
    id("org.quiltmc.loom")
    java
}

object Globals {
    const val apiVer = "1.0.0"
}

version = Globals.apiVer

java {
	withJavadocJar()
}
