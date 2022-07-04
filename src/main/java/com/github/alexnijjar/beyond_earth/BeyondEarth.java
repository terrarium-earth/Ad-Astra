package com.github.alexnijjar.beyond_earth;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexnijjar.beyond_earth.config.BeyondEarthConfig;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.data.PlanetData;
import com.github.alexnijjar.beyond_earth.networking.ModC2SPackets;
import com.github.alexnijjar.beyond_earth.registry.ModArmour;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModCommands;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;
import com.github.alexnijjar.beyond_earth.registry.ModFeatures;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.registry.ModOres;
import com.github.alexnijjar.beyond_earth.registry.ModPaintings;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;
import com.github.alexnijjar.beyond_earth.registry.ModSounds;
import com.github.alexnijjar.beyond_earth.registry.ModStructures;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.world.chunk.PlanetChunkGenerator;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "beyond_earth";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static BeyondEarthConfig CONFIG;

    @Environment(EnvType.SERVER)
    public static Set<Planet> planets = new HashSet<>();

    @Override
    public void onInitialize() {

        // Register config.
        AutoConfig.register(BeyondEarthConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(BeyondEarthConfig.class).getConfig();

        // Registry.
        ModFluids.register();
        ModItems.register();
        ModArmour.register();
        ModBlockEntities.register();
        ModRecipes.register();
        ModEntityTypes.register();
        ModScreenHandlers.register();
        ModCommands.register();
        ModSounds.register();
        ModParticleTypes.register();
        ModPaintings.register();

        // Data.
        PlanetData.register();

        // Worldgen.
        ModFeatures.register();
        ModOres.register();
        ModStructures.register();
        Registry.register(Registry.CHUNK_GENERATOR, new ModIdentifier("planet_noise"), PlanetChunkGenerator.CODEC);

        // Packets.
        ModC2SPackets.register();

        BeyondEarth.LOGGER.info("Beyond Earth Initialized! 🚀");
    }
}