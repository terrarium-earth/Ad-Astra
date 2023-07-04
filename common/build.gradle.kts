architectury {
    val enabledPlatforms: String by rootProject
    common(enabledPlatforms.split(","))
}