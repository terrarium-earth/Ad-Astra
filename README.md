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

– Alex

---

<a href="https://modrinth.com/mod/resourceful-lib">
    <p align="center">
        <img src="https://media.discordapp.net/attachments/1023678603289972797/1042445198183120946/BH_NU_BADGE.png" alt="Resourceful Lib" width="400"/> 
    </p>
</a>

<a href="https://modrinth.com/mod/resourceful-config">
    <p align="center">
        <img src="https://i.imgur.com/2oA0JFA.png" alt="Resourceful Config" width="400"/> 
    </p>
</a>

<div align="center">

Also uses [Botarium](https://modrinth.com/mod/botarium)
</div>

<a href="https://discord.terrarium.earth/">
    <p align="center">
        <img src="https://cdn.discordapp.com/attachments/1005798262273495041/1018731591314448495/discord.png" alt="Discord" width="400"/> 
    </p>
</a>

<a>
    <p align="center">
        <img src="https://cdn.discordapp.com/attachments/1005798262273495041/1018343461642055690/Ad_Astra.png" alt="Ad Astra!" width="400"/> 
    </p>
</a>

Ad Astra! (translation: "To the Stars!") is a space mod for Fabric and Forge with a focus on technology, travel and
exploration. We've got
everything you need to become an astronaut: rockets, space suits, space stations and machines. Why not take a stroll on
the Moon? Build a self-sustaining bunker on Mars? Go mining on Venus? The entire Solar System, better yet, Milky Way, is
at your disposal!

<a>
    <p align="center">
        <img src="https://cdn.discordapp.com/attachments/1005798262273495041/1018343462006964234/Features.png" alt="Features" width="400"/> 
    </p>
</a>

- Five celestial bodies: The Moon, Mars, Venus, Mercury and Glacio, in two different solar systems. Each planet
  expresses a distinctive flair, with specific mobs and unique materials.
- Vehicles: Four rocket tiers for interplanetary travel and a rover for traversing the rough, alien terrain ahead.
- Technology: Standard processing machines and generators, along with oxygen generation and distribution, fuel refining,
  water pumping, cables, fluid pipes and a rocket workbench.
- Building: Spruce up your builds with over 250 building blocks, ranging from planet-specific bricks and stones to space
  station deco and metal plating.
- An in-game guidebook, explaining everything you need to progress and prosper in Ad Astra!

## Developers

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
    modImplementation "earth.terrarium.adastra:ad_astra-common-$minecraft_version:$ad_astra_version"
}
```

Fabric

```groovy
dependencies {
    modImplementation "earth.terrarium.adastra:ad_astra-fabric-$minecraft_version:$ad_astra_version"
}
```

Forge

```groovy
dependencies {
    modImplementation "earth.terrarium.adastra:ad_astra-forge-$minecraft_version:$ad_astra_version"
}
```
