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
exploration. We've got everything you need to become an astronaut: rockets, space suits, space stations and machines.
Why not take a stroll on the Moon? Build a self-sustaining bunker on Mars? Go mining on Venus? The entire Solar System,
better yet, Milky Way, is at your disposal!

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
        url = "https://nexus.resourcefulbees.com/repository/maven-public/"
    }
}
```

In an Architectury project, you would implement it like so:

Common

```groovy
dependencies {
    modImplementation "earth.terrarium:ad_astra-common-$rootProject.minecraft_version:$rootProject.ad_astra_version"
}
```

Fabric

```groovy
dependencies {
    modImplementation "earth.terrarium:ad_astra-fabric-$rootProject.minecraft_version:$rootProject.ad_astra_version"
}
```

Forge

```groovy
dependencies {
    modImplementation "earth.terrarium:ad_astra-forge-$rootProject.minecraft_version:$rootProject.ad_astra_version"
}
```

---

<div align="center">

![Version](https://img.shields.io/maven-metadata/v?label=Ad%20Astra%20Version&metadataUrl=https%3A%2F%2Fnexus.resourcefulbees.com%2Frepository%2Fmaven-public%2Fearth%2Fterrarium%2Fad_astra-common-1.19.3%2Fmaven-metadata.xml)
</div>