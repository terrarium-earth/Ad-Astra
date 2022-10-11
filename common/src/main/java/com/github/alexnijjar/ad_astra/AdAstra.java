package com.github.alexnijjar.ad_astra;

import com.github.alexnijjar.ad_astra.config.AdAstraConfig;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.data.PlanetData;
import com.github.alexnijjar.ad_astra.networking.NetworkHandling;
import com.github.alexnijjar.ad_astra.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class AdAstra {
	public static final String MOD_ID = "ad_astra";
	public static final Logger LOGGER = LoggerFactory.getLogger("Ad Astra");
	public static AdAstraConfig CONFIG;

	public static Set<Planet> planets = new HashSet<>();
	public static Set<RegistryKey<World>> adAstraWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> orbitWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> planetWorlds = new HashSet<>();
	public static Set<RegistryKey<World>> worldsWithOxygen = new HashSet<>();

	public static void init() {
		// Register config
		AutoConfig.register(AdAstraConfig.class, Toml4jConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(AdAstraConfig.class).getConfig();

		// Registry
		ModFluids.register();
		ModBlocks.register();
		ModItems.register();
		ModBlockEntities.register();
		ModRecipes.register();
		ModEntityTypes.register();
		ModScreenHandlers.register();
		ModCommands.register();
		ModSoundEvents.register();
		ModParticleTypes.register();
		ModPaintings.register();

		// Data
		PlanetData.register();

		// Worldgen
		ModFeatures.register();
		ModStructures.register();

		// Packets
		NetworkHandling.register();

		ModCriteria.register();
	}

	public static void postInit() {
		new AxeItem(ToolMaterials.GOLD, 0, 0, new Item.Settings()) {
			static {
				STRIPPED_BLOCKS.put(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());
			}
		};
	}
}
