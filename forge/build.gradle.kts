architectury {
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    forge {
        mixinConfig("ad_astra-common.mixins.json")
        mixinConfig("ad_astra.mixins.json")
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
    val forgeVersion: String by project
    val reiVersion: String by project

    forge(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
    modCompileOnly(group = "me.shedaniel", name = "RoughlyEnoughItems-api-forge", version = reiVersion)
    modLocalRuntime(group = "me.shedaniel", name = "RoughlyEnoughItems-forge", version = reiVersion)
}
