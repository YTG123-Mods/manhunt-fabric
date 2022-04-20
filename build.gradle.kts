plugins {
    java
    id("org.quiltmc.loom") version "0.12-SNAPSHOT"
    `maven-publish`
    id("com.modrinth.minotaur") version "2.+"
    kotlin("jvm") version "1.6.20"
    id("org.jetbrains.dokka") version "1.6.20"
}

object Globals {
    const val mcVer = "1.18.2"
    const val kotlinVer = "1.6.20"

	const val loaderVer = "0.16.0-beta.7"
	const val qslVer = "1.1.0-beta.3+${mcVer}"
    const val fapiVer = "1.0.0-beta.5+0.48.0"
    const val flkVer = "1.7.3"

    const val yarnVer = "3"
    const val quiltMVer = "22"

    const val grp = "io.github.ytg1234"
	const val abn = "manhunt"
    const val modVer = "2.0.3"

    const val modrinthId = "z0z6kFjN"
    const val unstable = false
    val modrinthMcVers = arrayOf("1.18.1")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.quiltmc.loom")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    repositories {
        // maven(url = "https://jitpack.io/") {
        //     name = "Jitpack"
        //     content {
        //         includeGroup("com.github.P03W")
        //     }
        // }
    }

    group = Globals.grp

    dependencies {
        // Essentials
        minecraft("com.mojang", "minecraft", Globals.mcVer)

        mappings(loom.layered {
            addLayer(quiltMappings.mappings("org.quiltmc:quilt-mappings:${Globals.mcVer}+build.${Globals.quiltMVer}:v2"))
        })

        modImplementation("org.quiltmc", "quilt-loader", Globals.loaderVer)

        // QSL
        modImplementation("org.quiltmc", "qsl", Globals.qslVer)
        modImplementation("org.quiltmc.quilted-fabric-api", "quilted-fabric-api", "${Globals.fapiVer}-${Globals.mcVer}")

        // Kotlin
        // modImplementation("net.fabricmc", "fabric-language-kotlin", "${Globals.flkVer}+kotlin.${Globals.kotlinVer}")
    }

    tasks {
        withType<JavaCompile> {
			sourceCompatibility = "17"
            options.encoding = "UTF-8"
            options.release.set(17)
        }

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "17"
        }

        jar {
            from("LICENSE") {
				rename { "${it}_${Globals.abn}" }
			}
        }

        withType(org.jetbrains.dokka.gradle.DokkaTask::class).configureEach {
            dokkaSourceSets {
                configureEach {
                    includes.from("Module.md")
                }
            }
        }
    }

	tasks.processResources {
		inputs.property("version", project.version)
		inputs.property("maven_group", project.group)
		inputs.property("loader_version", Globals.loaderVer)
		inputs.property("minecraft_version", Globals.mcVer)
	
		filesMatching("quilt.mod.json") {
			expand(
				"version"                    to project.version,
				"maven_group"                to Globals.grp,
				"loader_version"             to Globals.loaderVer,
				"minecraft_version"          to Globals.mcVer,
				"quilted_fabric_api_version" to Globals.fapiVer
			)
		}
	}

	java {
		withSourcesJar()
	}
}

version = Globals.modVer

dependencies {
    implementation(project(":manhunt-api"))
    implementation(project(":manhunt-base"))
    afterEvaluate {
        include(project(":manhunt-api"))
        include(project(":manhunt-base"))
    }
}

tasks.getByName("remapJar").dependsOn(project(":manhunt-api").tasks.getByName("remapJar"))
tasks.getByName("remapJar").dependsOn(project(":manhunt-base").tasks.getByName("remapJar"))

modrinth {
    token.set(System.getenv("MODRINTH_API_TOKEN"))
    projectId.set(Globals.modrinthId)
    versionNumber.set("v${Globals.modVer}")
    // uploadFile.set("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}.jar")
    uploadFile.set(project.tasks.getByName("remapJar"))
    gameVersions.addAll("1.18.2")
    loaders.add("quilt")
    additionalFiles.addAll(arrayOf(
        "${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}-dev.jar",
        "${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}-sources.jar",
    ))

    versionName.set("Manhunt: Fabric v${Globals.modVer}")
    // syncBodyFrom.set(rootProject.file("README.md").text)

}

// tasks.modrinth.dependsOn(tasks.modrinthSyncBody)
// tasks.modrinth.dependsOn(remapJar)
// tasks.modrinth.dependsOn(project.tasks.getByName("sourcesJar"))


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
			// group(project.group)
            from(components["java"])
        }
    }
}
