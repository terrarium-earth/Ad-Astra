architectury {
    fabric()
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentFabric"].extendsFrom(this)
}

dependencies {
    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) {
        isTransitive = false
    }

    val minecraftVersion: String by project
    val fabricLoaderVersion: String by project
    val fabricApiVersion: String by project
    val modMenuVersion: String by project
    val reiVersion: String by project

    modImplementation(group = "net.fabricmc", name = "fabric-loader", version = fabricLoaderVersion)
    modApi(group = "net.fabricmc.fabric-api", name = "fabric-api", version = "$fabricApiVersion+$minecraftVersion")
    modApi(group = "com.terraformersmc", name = "modmenu", version = modMenuVersion)
    modLocalRuntime(group = "me.shedaniel", name = "RoughlyEnoughItems-fabric", version = reiVersion)

    modLocalRuntime(group = "RebornCore", name = "RebornCore-1.20", version = "5.8.3")
    modLocalRuntime(group = "TechReborn", name = "TechReborn-1.20", version = "5.8.3")
}
