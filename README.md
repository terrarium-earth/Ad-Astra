To add this library to your project, do the following:

```groovy
repositories {
  maven {
    // Location of the maven that hosts Terrarium and Team Resourceful's files.
    name = "Resourceful Bees Maven"
    url = "https://maven.teamresourceful.com/repository/maven-public/"
  }
}
```

In an Architectury project, you would implement it like so:

Common

```groovy
dependencies {
    modImplementation "earth.terrarium.adastra:adastra-common-$minecraft_version:$ad_astra_version"
}
```

Fabric

```groovy
dependencies {
    modImplementation "earth.terrarium.adastra:adastra-fabric-$minecraft_version:$ad_astra_version"
}
```

Forge

```groovy
dependencies {
    modImplementation "earth.terrarium.adastra:adastra-forge-$minecraft_version:$ad_astra_version"
}
```
