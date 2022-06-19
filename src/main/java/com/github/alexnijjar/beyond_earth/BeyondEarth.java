package com.github.alexnijjar.beyond_earth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.data.PlanetData;
import com.github.alexnijjar.beyond_earth.networking.ModC2SPackets;
import com.github.alexnijjar.beyond_earth.registry.ModArmour;
import com.github.alexnijjar.beyond_earth.registry.ModBiomes;
import com.github.alexnijjar.beyond_earth.registry.ModBlockEntities;
import com.github.alexnijjar.beyond_earth.registry.ModCommands;
import com.github.alexnijjar.beyond_earth.registry.ModEntities;
import com.github.alexnijjar.beyond_earth.registry.ModFeatures;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.registry.ModPaintings;
import com.github.alexnijjar.beyond_earth.registry.ModParticles;
import com.github.alexnijjar.beyond_earth.registry.ModRecipes;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;
import com.github.alexnijjar.beyond_earth.registry.ModSounds;
import com.github.alexnijjar.beyond_earth.registry.ModStructures;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "beyond_earth";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Environment(EnvType.SERVER)
    public static List<Planet> planets = new ArrayList<>();

    @Override
    public void onInitialize() {

        // Registry.
        ModItems.register();
        ModBlockEntities.register();
        ModFluids.register();
        ModArmour.register();
        ModRecipes.register();
        ModEntities.register();
        ModScreenHandlers.register();
        ModCommands.register();
        ModSounds.register();
        ModParticles.register();
        ModPaintings.register();

        // Data.
        PlanetData.register();

        // Worldgen.
        ModBiomes.register();
        ModFeatures.register();
        ModStructures.register();
        // Registry.register(Registry.CHUNK_GENERATOR, new ModIdentifier("planet_noise"), PlanetChunkGenerator.CODEC);

        // Packets.
        ModC2SPackets.register();

        BeyondEarth.LOGGER.info("Beyond Earth Initialized! 🚀");
    }
}