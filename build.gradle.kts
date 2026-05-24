import earth.terrarium.cloche.api.metadata.CommonMetadata
import groovy.json.StringEscapeUtils

plugins {
    java
    `maven-publish`
    id("com.teamresourceful.resourcefulgradle") version "0.0.+"
    id("earth.terrarium.cloche") version "0.18.14"
}

//val stationsFile: String = file("stations.json").absolutePath

val minecraftVersion: String by project
val modId = project.name

base {
    archivesName.set("$modId-$minecraftVersion")
}

repositories {
    mavenCentral()

    cloche {
        main()
        mavenNeoforged()
        mavenNeoforgedMeta()
        mavenFabric()
    }

    maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
    maven(url = "https://maven.firstdarkdev.xyz/snapshots")
    maven(url = "https://maven.shedaniel.me")

    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
}

cloche {
    val reiVersion: String by project

    minecraftVersion = project.property("minecraftVersion") as String

    metadata {
        modId = "ad_astra"

        name = "Ad Astra"
        description = "Live long and prosper, Ad Astra!"

        url = "https://www.curseforge.com/minecraft/mc-mods/ad-astra"
        issues = "https://github.com/terrarium-earth/ad-astra/issues"
        icon = "icon.png"
        license = "Terrarium License"

        author("Alex Nijjar", "alex@terrarium.earth")
        contributor("CodexAdrian", "adrian@terrarium.earth")
        contributor("Facu")
        contributor("Fizz")
        contributor("MsRandom", "ashley@terrarium.earth")
        contributor("ThatGravyBoat", "sophie@terrarium.earth")
        contributor("Mrbysco")

        require("resourcefullib", "3.0.0")
        require("resourcefulconfig", "3.0.0")
        require("common_storage_lib", "0.0.7")
    }

    common {
        mixins.from("src/main/adastra-common.mixins.json")

        dependencies {
            implementation(module(group = "javazoom", name = "jlayer", version = "1.0.1"))
            compileOnly(module(group = "me.shedaniel", name = "REIPluginCompatibilities-forge-annotations", version = "8.+"))
            compileOnly("org.jetbrains:annotations:26.1.0")
            compileOnly("com.teamresourceful:bytecodecs:1.1.2")
        }
    }

    neoforge {
        val neoforgeVersion: String by project

        loaderVersion = neoforgeVersion

        data()

        mixins.from("src/neoforge/adastra.mixins.json")

        dependencies {
//    modLocalRuntime(group = "maven.modrinth", name = "jade", version = "13.2.2")
//    modLocalRuntime(group = "maven.modrinth", name = "mekanism", version = "10.4.2.16")

//            legacyClasspath("com.teamresourceful:yabn:1.0.3")
//            legacyClasspath("com.teamresourceful:bytecodecsbytecodecs:1.1.2")

            legacyClasspath(module(group = "javazoom", name = "jlayer", version = "1.0.1"))
        }

        runs {
            data(){
                arguments("--existing", file("src/main/resources/").getAbsolutePath())
            }
        }
    }

    fabric {
        val fabricLoaderVersion: String by project

        includedClient()

        loaderVersion = fabricLoaderVersion

        mixins.from("src/fabric/adastra.mixins.json")

        metadata {
            entrypoint("main", "earth.terrarium.adastra.fabric.AdAstraFabric::init")
            entrypoint("main", "earth.terrarium.adastra.AdAstra::postInit")

            entrypoint("client", "earth.terrarium.adastra.client.fabric.AdAstraClientFabric::init")

            entrypoint("rei_client", "earth.terrarium.adastra.common.compat.rei.AdAstraReiPlugin")
            entrypoint("jei_mod_plugin", "earth.terrarium.adastra.common.compat.jei.AdAstraJeiPlugin")

            dependency {
                modId = "fabric-api"
                type = CommonMetadata.Dependency.Type.Required
            }
        }

        dependencies {
            val fabricApiVersion: String by project
            val modMenuVersion: String by project

            fabricApi(fabricApiVersion)

            modLocalRuntime(module(group = "me.shedaniel", name = "RoughlyEnoughItems-fabric", version = reiVersion))

            modLocalRuntime(module(group = "com.terraformersmc", name = "modmenu", version = modMenuVersion))

            // modLocalRuntime(module(group = "RebornCore", name = "RebornCore-1.20", version = "5.10.2")) { isTransitive = false }
            // modLocalRuntime(module(group = "TechReborn", name = "TechReborn-1.20", version = "5.10.2")) { isTransitive = false }

            modLocalRuntime(module(group = "maven.modrinth", name = "jade", version = "15.10.5+fabric"))
            // modLocalRuntime(module(group = "maven.modrinth", name = "dcwa", version = "5.0")) // Disable custom world advice
        }
    }

    targets.all {
        datagenDirectory = file("src/main/generated/resources")

        runs {
            client {
//                jvmArgs("-Dadastra.stations=", stationsFile)
            }

            server {
//                jvmArgs("-Dadastra.stations=", stationsFile)
            }
        }

        dependencies {
            val resourcefulLibVersion: String by project
            val resourcefulConfigVersion: String by project
            val cslVersion: String by project
            val athenaVersion: String by project
            val cadmusVersion: String by project
            val argonautsVersion: String by project
            val jeiVersion: String by project
            val patchouliVersion: String by project
            val shimmerVersion: String by project

            modApi(
                module(
                    group = "com.teamresourceful.resourcefullib",
                    name = "resourcefullib-$loaderName-1.21",
                    version = resourcefulLibVersion
                )
            )
            modApi(
                module(
                    group = "com.teamresourceful.resourcefulconfig",
                    name = "resourcefulconfig-$loaderName-1.21",
                    version = resourcefulConfigVersion
                )
            )
            modApi(
                module(
                    group = "earth.terrarium.common_storage_lib",
                    name = "common-storage-lib-$loaderName-${minecraftVersion.get()}",
                    version = cslVersion
                )
            )

            /*
modCompileOnly(module(group = "earth.terrarium.cadmus", name = "cadmus-$loaderName-$minecraftVersion", version = cadmusVersion)) {
    isTransitive = false
}
modCompileOnly(module(group = "earth.terrarium.argonauts", name = "argonauts-$loaderName-$minecraftVersion", version = argonautsVersion)) {
    isTransitive = false
}
 */

            val jlayer = module(group = "javazoom", name = "jlayer", version = "1.0.1")
            implementation(jlayer)
            include(jlayer)
            /*
            modLocalRuntime(module(group = "earth.terrarium.cadmus", name = "cadmus-$loaderName-$minecraftVersion", version = cadmusVersion)) {
                isTransitive = false
            }
            modLocalRuntime(module(group = "earth.terrarium.argonauts", name = "argonauts-$loaderName-$minecraftVersion", version = argonautsVersion)) {
                isTransitive = false
            }

             */

            modCompileOnly(module(group = "mezz.jei", name = "jei-${minecraftVersion.get()}-$loaderName", version = jeiVersion)) {
                isTransitive = false
            }

            modLocalRuntime(
                module(
                    group = "earth.terrarium.athena",
                    name = "athena-$loaderName-${minecraftVersion.get()}",
                    version = athenaVersion
                )
            )
            modCompileOnly(module(group = "me.shedaniel", name = "RoughlyEnoughItems-api-$loaderName", version = reiVersion))
            modCompileOnly(
                module(
                    group = "me.shedaniel",
                    name = "RoughlyEnoughItems-default-plugin-$loaderName",
                    version = reiVersion
                )
            )
//            modLocalRuntime(module(group = "vazkii.patchouli", name = "Patchouli", version = "$minecraftVersion-$patchouliVersion-${loaderName.uppercase()}"))
//            modLocalRuntime(module(group = "com.lowdragmc.shimmer", name = "Shimmer-$loaderName", version = "$minecraftVersion-$shimmerVersion")) { isTransitive = false }
        }
    }

    mappings {
        val parchmentVersion: String by project

        official()
        parchment(parchmentVersion)
    }
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            pom {
                val github = "https://github.com/terrarium-earth/Ad-Astra"

                name.set("Ad Astra")
                url.set(github)

                scm {
                    connection.set("git:$github.git")
                    developerConnection.set("git:$github.git")
                    url.set(github)
                }

                licenses {
                    license {
                        name.set("Terrarium Licence (https://gist.github.com/CodexAdrian/4bb2a1868bb2d2a91ca74ea40424e69d)")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            setUrl("https://maven.teamresourceful.com/repository/terrarium/")
            credentials {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_PASS")
            }
        }
    }
}

idea {
    module {
        excludeDirs.add(file("run"))
    }
}

resourcefulGradle {
    templates {
        register("embed") {
            val changelog: String = file("changelog.md").readText(Charsets.UTF_8)
            val fabricLink: String? = System.getenv("FABRIC_RELEASE_URL")
            val forgeLink: String? = System.getenv("FORGE_RELEASE_URL")

            source.set(file("templates/embed.json.template"))
            injectedValues.set(
                mapOf(
                    "minecraft" to minecraftVersion,
                    "version" to version,
                    "changelog" to StringEscapeUtils.escapeJava(changelog),
                    "fabric_link" to fabricLink.orEmpty(),
                    "forge_link" to forgeLink.orEmpty(),
                )
            )
        }
    }
}

tasks {
    withType(JavaCompile::class).configureEach {
        options.compilerArgs.addAll(listOf("-Xmaxerrs", "2000"))
    }
}