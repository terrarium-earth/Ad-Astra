package com.github.alexnijjar.ad_astra;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.alexnijjar.ad_astra.config.BeyondEarthConfig;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.data.PlanetData;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.registry.ModArmour;
import com.github.alexnijjar.ad_astra.registry.ModBlockEntities;
import com.github.alexnijjar.ad_astra.registry.ModCommands;
import com.github.alexnijjar.ad_astra.registry.ModEntityTypes;
import com.github.alexnijjar.ad_astra.registry.ModFeatures;
import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModPaintings;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import com.github.alexnijjar.ad_astra.registry.ModSounds;
import com.github.alexnijjar.ad_astra.registry.ModStructures;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "ad_astra";
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
        ModStructures.register();

        // Packets.
        ModC2SPackets.register();

        BeyondEarth.LOGGER.info("Beyond Earth Initialized! 🚀");
    }
}