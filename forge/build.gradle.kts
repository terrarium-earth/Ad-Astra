architectury {
    forge()
}

loom {
    forge {
        mixinConfig("adastra-common.mixins.json")
        mixinConfig("adastra.mixins.json")
    }
    runs {
        create("data") {
            data()
            programArgs("--all", "--mod", "ad_astra")
            programArgs("--output", project(":common").file("src/main/generated/resources").absolutePath)
            programArgs("--existing", project(":common").file("src/main/resources").absolutePath)
        }
        named("client") {
            property("mixin.env.remapRefMap", "true")
            property("mixin.env.refMapRemappingFile", "${projectDir}/build/createSrgToMcp/output.srg")

            programArgs("-mixin.config=create.mixins.json")
        }
    }
}

repositories {
    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            includeGroup("com.tterrag.registrate")
            includeGroup("com.jozufozu.flywheel")
            includeGroup("com.simibubi.create")
        }
    }
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentForge"].extendsFrom(this)
}

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) {
        isTransitive = false
    }

    val minecraftVersion: String by project
    val mixinExtrasVersion: String by project
    val forgeVersion: String by project
    val reiVersion: String by project
    val jeiVersion: String by project
    val registrateForgeVersion: String by project
    val flywheelForgeVersion: String by project
    val createForgeVersion: String by project
    val ponderForgeVersion: String by project

    forge(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")

    modLocalRuntime(group = "mezz.jei", name = "jei-$minecraftVersion-forge", version = jeiVersion) {
        isTransitive = false
    }

    modLocalRuntime(group = "maven.modrinth", name = "jade", version = "2iRQrBk4")
    modLocalRuntime(group = "maven.modrinth", name = "mekanism", version = "10.4.2.16")
    modLocalRuntime(group = "maven.modrinth", name = "modular-routers", version = "12.1.1")

    forgeRuntimeLibrary("com.teamresourceful:yabn:1.0.3")
    forgeRuntimeLibrary("com.teamresourceful:bytecodecs:1.0.2")

    forgeRuntimeLibrary(group = "javazoom", name = "jlayer", version = "1.0.1")

    modImplementation("dev.engine-room.flywheel:flywheel-forge-api-${minecraftVersion}:${flywheelForgeVersion}")
    modImplementation("dev.engine-room.flywheel:flywheel-forge-${minecraftVersion}:${flywheelForgeVersion}")
    modImplementation("com.simibubi.create:create-${minecraftVersion}:${createForgeVersion}:slim") { isTransitive = false }
    modImplementation("net.createmod.ponder:Ponder-Forge-${minecraftVersion}:${ponderForgeVersion}")
    modImplementation("com.tterrag.registrate:Registrate:${registrateForgeVersion}")

    // TODO: remove in neoforge
    "annotationProcessor"(group = "io.github.llamalad7", name = "mixinextras-forge", version = mixinExtrasVersion).apply {
        implementation(this)
        "include"(this)
    }

}
