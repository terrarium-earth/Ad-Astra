package com.github.alexnijjar.beyond_earth.datagen;

import com.github.alexnijjar.beyond_earth.BeyondEarth;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BeyondEarthDataGenEntrypoint implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		BeyondEarth.LOGGER.info("Beyond Earth Datagen started...");

		// Client
		fabricDataGenerator.addProvider(ModModelProvider::new);

		// Server
		fabricDataGenerator.addProvider(ModBlockLootTableProvider::new);
		fabricDataGenerator.addProvider(ModRecipeProvider::new);
		BeyondEarth.LOGGER.info("Beyond Earth Datagen finished 🚀");
	}
}
