package com.github.alexnijjar.beyond_earth.datagen;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;

class ModBlockLootTableProvider extends FabricBlockLootTableProvider implements ModBlocks {
	ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {

		for (Block block : blocks) {
			this.addDrop(block);
		}
	}
}