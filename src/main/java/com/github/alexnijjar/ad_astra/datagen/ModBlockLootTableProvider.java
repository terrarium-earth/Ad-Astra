package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Items;

class ModBlockLootTableProvider extends FabricBlockLootTableProvider implements ModBlocks {
	ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {

		for (Block block : blocks) {
			this.addDrop(block);
		}

		this.addDrop(WALL_COAL_TORCH, ModItems.COAL_TORCH);

		this.addDrop(MOON_CHEESE_ORE, ModItems.CHEESE);
		this.addDrop(MOON_DESH_ORE, ModItems.RAW_DESH);
		this.addDrop(MOON_IRON_ORE, Items.RAW_IRON);
		this.addDrop(MOON_ICE_SHARD_ORE, ModItems.ICE_SHARD);
		this.addDrop(MARS_IRON_ORE, Items.RAW_IRON);
		this.addDrop(MARS_ICE_SHARD_ORE, ModItems.ICE_SHARD);
		this.addDrop(MARS_DIAMOND_ORE, Items.DIAMOND);
		this.addDrop(MARS_OSTRUM_ORE, ModItems.RAW_OSTRUM);
		this.addDrop(MARS_ICE_SHARD_ORE, ModItems.ICE_SHARD);
		this.addDrop(MERCURY_IRON_ORE, Items.RAW_IRON);
		this.addDrop(VENUS_COAL_ORE, Items.COAL);
		this.addDrop(VENUS_GOLD_ORE, Items.RAW_GOLD);
		this.addDrop(VENUS_DIAMOND_ORE, Items.DIAMOND);
		this.addDrop(VENUS_CALORITE_ORE, ModItems.RAW_CALORITE);
		this.addDrop(GLACIO_ICE_SHARD_ORE, ModItems.ICE_SHARD);
		this.addDrop(GLACIO_COAL_ORE, Items.COAL);
		this.addDrop(GLACIO_COPPER_ORE, Items.RAW_COPPER);
		this.addDrop(GLACIO_IRON_ORE, Items.RAW_IRON);
		this.addDrop(GLACIO_LAPIS_ORE, Items.LAPIS_LAZULI);
	}
}