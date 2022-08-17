package com.github.alexnijjar.ad_astra;

import com.github.alexnijjar.ad_astra.config.AdAstraConfig;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.data.PlanetData;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.registry.*;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.world.chunk.PlanetChunkGenerator;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class AdAstra implements ModInitializer {

	public static final String MOD_ID = "ad_astra";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static AdAstraConfig CONFIG;

	@Environment(EnvType.SERVER)
	public static Set<Planet> planets = new HashSet<>();

	@Override
	public void onInitialize() {

		// Register config.
		AutoConfig.register(AdAstraConfig.class, Toml4jConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(AdAstraConfig.class).getConfig();

		// Registry.
		ModFluids.register();
		ModItems.register();
		ModArmour.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModEntityTypes.register();
		ModScreenHandlers.register();
		ModCommands.register();
		ModSoundEvents.register();
		ModParticleTypes.register();
		ModPaintings.register();

		// Data.
		PlanetData.register();

		// Worldgen.
		ModFeatures.register();
		ModStructures.register();
		Registry.register(Registry.CHUNK_GENERATOR, new ModIdentifier("planet_noise"), PlanetChunkGenerator.CODEC);

		// Packets.
		ModC2SPackets.register();

		ModCriteria.register();

		AdAstra.LOGGER.info("Ad Astra Initialized! 🚀");
	}
}