# IMPORTANT

Current versions of Ad Astra are in maintenance mode. This means that only
critical bugs will be fixed and no new features will be added. This is because
we're working on a complete rewrite of the mod. The reason for this is that 
the core concepts of Ad Astra were built off another mod and I would like to
stray away from that. The current version of Ad Astra is kinda barren and
not something I'm particularly proud of. In addition to this, I had written 
the original code base when I was very new to Java and Minecraft Modding and 
as such it's very bad lol. The rework aims to provide a complete overhaul of
the mod – basically everything will be different and so much better. This is
currently in development in the rework branch:

https://github.com/terrarium-earth/Ad-Astra/tree/new-rework

If you're looking to contribute to Ad Astra, such as new features or language
translations, I'd suggest you hold off from doing so until the rework is
complete.

TL;DR: Ad Astra is being completely rewritten and the current version will not receive content updates.

---

To add this library to your project, do the following:

```groovy
repositories {
  maven {
    // Location of the maven that hosts Terrarium and Team Resourceful's files.
    name = "Resourceful Bees Maven"
    url = "https://maven.resourcefulbees.com/repository/maven-public/"
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
