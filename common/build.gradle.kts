architectury {
    val enabledPlatforms: String by rootProject
    common(enabledPlatforms.split(","))
}

loom {
    accessWidenerPath.set(file("src/main/resources/ad_astra.accesswidener"))
}

dependencies {
    modCompileOnly(group = "tech.thatgravyboat", name = "commonats", version = "1.0")
}
