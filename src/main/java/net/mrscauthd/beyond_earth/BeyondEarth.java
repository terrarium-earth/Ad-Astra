package net.mrscauthd.beyond_earth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.registry.*;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.chunk.PlanetChunkGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeyondEarth implements ModInitializer {

    public static final String MOD_ID = "beyond_earth";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {

        // Registry.
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModFluids.register();
        ModArmour.register();
        ModScreenHandlers.register();
        ModRecipes.register();

        // Worldgen
        ModStructures.register();
        ModOres.register();
        Registry.register(Registry.CHUNK_GENERATOR, new ModIdentifier("planet_noise"), PlanetChunkGenerator.CODEC);

        BeyondEarth.LOGGER.info("Beyond Earth Initialized!");
    }
}
