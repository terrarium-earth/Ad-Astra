architectury {
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    forge {
        mixinConfig("adastra-common.mixins.json")
        mixinConfig("adastra.mixins.json")
        convertAccessWideners.set(true)
    }
    runs {
        create("data") {
            data()
            programArgs("--all", "--mod", "ad_astra")
            programArgs("--output", project(":common").file("src/main/generated/resources").absolutePath)
            programArgs("--existing", project(":common").file("src/main/resources").absolutePath)
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

    forge(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")

    modLocalRuntime(group = "maven.modrinth", name = "jade", version = "2iRQrBk4")
    modLocalRuntime(group = "maven.modrinth", name = "mekanism", version = "10.4.2.16")

    forgeRuntimeLibrary("com.teamresourceful:yabn:1.0.3")
    forgeRuntimeLibrary("com.teamresourceful:bytecodecs:1.0.2")

    forgeRuntimeLibrary(group = "javazoom", name = "jlayer", version = "1.0.1")

    // TODO: remove in neoforge
    "annotationProcessor"(group = "io.github.llamalad7", name = "mixinextras-forge", version = mixinExtrasVersion).apply {
        implementation(this)
        "include"(this)
    }
}
