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
            programArgs("--all", "--mod", "adastra")
            programArgs("--output", project(":common").file("src/main/generated/resources").absolutePath)
            programArgs("--existing", project(":common").file("src/main/resources").absolutePath)
        }
    }
}

dependencies {
    val minecraftVersion: String by project
    val forgeVersion: String by project
    val reiVersion: String by project

    forge(group = "net.minecraftforge", name = "forge", version = "$minecraftVersion-$forgeVersion")
    "modLocalRuntime"(group = "me.shedaniel", name = "RoughlyEnoughItems-forge", version = reiVersion)
}
