package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AdAstraDataGenEntrypoint implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		AdAstra.LOGGER.info("Beyond Earth Datagen started...");

		// Client
		fabricDataGenerator.addProvider(ModModelProvider::new);

		// Server
		fabricDataGenerator.addProvider(ModBlockLootTableProvider::new);
		fabricDataGenerator.addProvider(ModRecipeProvider::new);
		AdAstra.LOGGER.info("Beyond Earth Datagen finished 🚀");
		
	}
}
