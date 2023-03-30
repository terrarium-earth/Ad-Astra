<center>

## Ad Astra

[![Requires Resourceful Lib](https://cdn.discordapp.com/attachments/1073717602880327761/1073717942014972034/RLib_vector.svg)](http://modrinth.com/mod/resourceful-lib)
[![Requires Resourceful Config](https://cdn.discordapp.com/attachments/1073717602880327761/1073717981118480535/RConfig_vector.svg)](http://modrinth.com/mod/resourceful-config)
[![Requires Botarium](https://cdn.discordapp.com/attachments/1073717602880327761/1090775450437046392/Requires_Botarium_vector.svg)](http://modrinth.com/mod/botarium)

[![Made by Terrarium](https://cdn.discordapp.com/attachments/1073717602880327761/1073718144910233691/Terrarium_vector.svg)](https://discord.terrarium.earth)
[![Modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/ad-astra)
[![Curseforge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg)](https://www.curseforge.com/minecraft/mc-mods/ad-astra)
<hr>

### 📖About 📖
<hr>
</center>

Ad Astra! (translation: "To the Stars!") is a space mod for Fabric and Forge with a focus on technology, travel and
exploration. We've got everything you need to become an astronaut: rockets, space suits, space stations and machines.
Why not take a stroll on the Moon? Build a self-sustaining bunker on Mars? Go mining on Venus? The entire Solar System,
better yet, Milky Way, is at your disposal!

- Five celestial bodies: The Moon, Mars, Venus, Mercury and Glacio, in two different solar systems. Each planet expresses a distinctive flair, with specific mobs and unique materials.
- Vehicles: Four rocket tiers for interplanetary travel and a rover for traversing the rough, alien terrain ahead.
- Technology: Standard processing machines and generators, along with oxygen generation and distribution, fuel refining, water pumping, cable, fluid pipes and a rocket workbench.
- Building: Spruce up your builds with over 250 building blocks, ranging from planet-specific bricks and stones to space station deco and metal plating.
- An in-game guidebook, explaining everything you need to progress and prosper in Ad Astra!

<center>
<hr>

[![Use code "Terrarium" for 25% off at BisectHosting](https://www.bisecthosting.com/images/CF/RiseNFall_Kingdom/BH_RNF_PromoCard.png)](http://bisecthosting.com/terrarium)

<hr>

## ✨Socials✨

[![youtube-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/social/youtube-plural_vector.svg)](https://youtube.terrarium.earth)
[![twitch-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/social/twitch-plural_vector.svg)](https://www.twitch.tv/terrariumearth)
[![twitter-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/social/twitter-plural_vector.svg)](https://twitter.terrarium.earth)
[![kofi-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/donate/kofi-plural_vector.svg)](https://kofi.terrarium.earth)
[![discord-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/social/discord-plural_vector.svg)](https://discord.terrarium.earth)
[![modrinth](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/available/modrinth_vector.svg)](https://modrinth.com/user/Terrarium)
[![curseforge](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy-minimal/available/curseforge_vector.svg)](https://www.curseforge.com/members/terrariumearth/projects)
<hr>
</center>

## Developers

To add this library to your project, do the following:

```groovy
repositories {
  maven {
    // Location of the maven that hosts Terrarium and Team Resourceful's files.
    name = "Team Resourceful Maven"
    url = "https://maven.resourcefulbees.com/repository/maven-public/"
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

![Version](https://img.shields.io/maven-metadata/v?label=Ad%20Astra%20Version&metadataUrl=https%3A%2F%2Fnexus.resourcefulbees.com%2Frepository%2Fmaven-public%2Fearth%2Fterrarium%2Fad_astra-common-1.19.2%2Fmaven-metadata.xml)
</div>
