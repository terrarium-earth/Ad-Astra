package net.mrscauthd.beyond_earth;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.data.Planet;
import net.mrscauthd.beyond_earth.data.PlanetData;
import net.mrscauthd.beyond_earth.data.SolarSystem;
import net.mrscauthd.beyond_earth.networking.ModC2SPackets;
import net.mrscauthd.beyond_earth.registry.ModArmour;
import net.mrscauthd.beyond_earth.registry.ModBiomes;
import net.mrscauthd.beyond_earth.registry.ModBlockEntities;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import net.mrscauthd.beyond_earth.registry.ModCommands;
import net.mrscauthd.beyond_earth.registry.ModEntities;
import net.mrscauthd.beyond_earth.registry.ModFeatures;
import net.mrscauthd.beyond_earth.registry.ModFluids;
import net.mrscauthd.beyond_earth.registry.ModItems;
import net.mrscauthd.beyond_earth.registry.ModOres;
import net.mrscauthd.beyond_earth.registry.ModRecipes;
import net.mrscauthd.beyond_earth.registry.ModScreenHandlers;
import net.mrscauthd.beyond_earth.registry.ModStructures;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.chunk.PlanetChunkGenerator;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "beyond_earth";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Environment(EnvType.SERVER)
    public static List<Planet> planets = new ArrayList<>();
    @Environment(EnvType.SERVER)
    public static List<SolarSystem> solarSystems = new ArrayList<>();

    @Override
    public void onInitialize() {

        // Registry.
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModFluids.register();
        ModArmour.register();
        ModRecipes.register();
        ModEntities.register();
        ModScreenHandlers.register();
        ModCommands.register();

        // Data.
        PlanetData.register();

        // Worldgen.
        ModBiomes.register();
        ModFeatures.register();
        ModOres.register();
        ModStructures.register();
        Registry.register(Registry.CHUNK_GENERATOR, new ModIdentifier("planet_noise"), PlanetChunkGenerator.CODEC);

        // Packets.
        ModC2SPackets.register();

        BeyondEarth.LOGGER.info("Beyond Earth Initialized!");
    }
}