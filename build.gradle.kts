import com.modrinth.minotaur.TaskModrinthUpload

plugins {
    java
    id("fabric-loom") version "0.5-SNAPSHOT"
    `maven-publish`
    id("com.modrinth.minotaur") version "1.1.0"
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.dokka") version "1.4.20"
}

object Globals {
    const val mcVer = "1.16.5"
    const val yarnVer = "1"
    const val loaderVer = "0.11.0"
    const val fapiVer = "0.29.3+1.16"
    const val flkVer = "1.4.21+build.1"

    const val grp = "io.github.ytg1234"
	const val abn = "manhunt"

    const val modVer = "2.0.3"

    const val modrinthId = "z0z6kFjN"
    const val unstable = false
    val modrinthMcVers =
        arrayOf("1.16.4-pre1", "1.16.3", "1.16.3-rc1", "1.16.2", "1.16.4-rc1", "1.16.4-pre2", "1.16.4", "20w45a", "20w46a", "20w48a", "20w49a", "20w51a", "1.16.5-rc1", "1.16.5")
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "fabric-loom")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")

    repositories {
        maven(url = "https://jitpack.io/") {
            name = "Jitpack"
            content {
                includeGroup("com.github.P03W")
            }
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    group = Globals.grp

    dependencies {
        // Essentials
        minecraft("com.mojang", "minecraft", Globals.mcVer)
        mappings("net.fabricmc", "yarn", "${Globals.mcVer}+build.${Globals.yarnVer}", classifier = "v2")
        modImplementation("net.fabricmc", "fabric-loader", Globals.loaderVer)

        // Fabric API
        modImplementation("net.fabricmc.fabric-api", "fabric-api", Globals.fapiVer)

        // Kotlin
        modImplementation("net.fabricmc", "fabric-language-kotlin", Globals.flkVer)
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType(JavaCompile::class).configureEach {
            if (JavaVersion.current().isJava9Compatible) {
                options.compilerArgs.addAll(listOf("--release", "8"))
            } else {
                sourceCompatibility = "8"
                targetCompatibility = "8"
            }
        }

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }

        register<Jar>("sourcesJar") {
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        jar {
            from("LICENSE")
        }

        withType(org.jetbrains.dokka.gradle.DokkaTask::class).configureEach {
            dokkaSourceSets {
                configureEach {
                    includes.from("Module.md")
                }
            }
        }
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

tasks {
    register<TaskModrinthUpload>("publishModrinth") {
        token = System.getenv("MODRINTH_API_TOKEN")
        projectId = Globals.modrinthId
        versionNumber = "v${Globals.modVer}"
        uploadFile = "${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}.jar"
        Globals.modrinthMcVers.forEach { addGameVersion(it) }
        addLoader("fabric")
		addFile("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}-dev.jar")
        addFile("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.modVer}-sources.jar")

        versionName = "Manhunt: Fabric v${Globals.modVer}"

        dependsOn(remapJar)
        dependsOn(project.tasks.getByName("sourcesJar"))
    }

    processResources {
        inputs.property("version", Globals.modVer)

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to Globals.modVer)
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.getByName("jar")) {
                builtBy(tasks.getByName("remapJar"))
            }
            artifact(tasks.getByName("sourcesJar")) {
                builtBy(tasks.getByName("remapSourcesJar"))
            }
        }
    }

    repositories {
        if (System.getenv("MAVEN_REPO") != null) {
            maven(url = System.getenv("MAVEN_REPO"))
        }
    }
}
