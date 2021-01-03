import java.io.BufferedReader
import com.modrinth.minotaur.TaskModrinthUpload
import java.io.InputStreamReader

plugins {
    id("fabric-loom") version "0.5-SNAPSHOT"
    `maven-publish`
    id("com.modrinth.minotaur") version "1.1.0"
}


object Globals {
    const val mcVer = "1.16.4"
    const val yarnVer = "7"
    const val loaderVer = "0.10.8"
    const val fapiVer = "0.29.1+1.16"

    const val grp = "io.github.ytg1234"
    const val modVer = "2.0.0"

    const val modrinthId = "z0z6kFjN"
    val modrinthMcVers = arrayOf("1.16.2", "1.16.3", "1.16.4", "20w45a", "20w46a", "20w48a", "20w49a", "20w50a", "20w51a")
}

allprojects {
    apply(plugin = "fabric-loom")

//    java {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }

    group = Globals.grp

    dependencies {
        minecraft("com.mojang", "minecraft", Globals.mcVer)
        mappings("net.fabricmc", "yarn", "${Globals.mcVer}+build.${Globals.yarnVer}", classifier = "v2")
        modImplementation("net.fabricmc", "fabric-loader", Globals.loaderVer)

        modImplementation("net.fabricmc.fabric-api", "fabric-api", Globals.fapiVer)
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

        register<Jar>("sourcesJar") {
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        jar {
            from("LICENSE")
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

tasks.remapJar.get().dependsOn(project(":manhunt-api").tasks.remapJar)
tasks.remapJar.get().dependsOn(project(":manhunt-base").tasks.remapJar)

tasks {
    register<TaskModrinthUpload>("publishModrinth") {
        val br = BufferedReader(InputStreamReader(System.`in`))

        println("Access Token: ")
        token = br.readLine()

        projectId = Globals.modrinthId
        versionNumber = "v${Globals.modVer}"
        uploadFile = remapJar
        Globals.modrinthMcVers.forEach { addGameVersion(it) }
        addLoader("fabric")

        versionName = "Manhunt: Fabric v${Globals.modVer}"

        println("Unstable (yes/no)? ")
        releaseType = if(br.readLine() == "yes") "beta" else "release"

        dependsOn(remapJar)

        addFile(project.tasks.getByName("sourcesJar"))
        dependsOn(project.tasks.getByName("sourcesJar"))
    }

    javadoc {
        options {
            source = "8"
            encoding = "UTF-8"
            memberLevel = JavadocMemberLevel.PRIVATE
        }

        allprojects.forEach { source(it.sourceSets["main"].allJava) }

        classpath = sourceSets["main"].runtimeClasspath
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
            artifact(tasks.jar) {
                builtBy(tasks.remapJar)
            }
            artifact("${project.buildDir.absolutePath}/libs/${project.name}-${Globals.modVer}.jar") {
                builtBy(tasks.remapJar)
            }
            artifact(tasks.getByName("sourcesJar")) {
                builtBy(tasks.remapSourcesJar)
            }
        }
    }

    repositories {}
}
