package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.blocks.door.DoorState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Items;

class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {

		for (Block block : ModBlocks.blocks) {
			this.addDrop(block);
		}

		this.addDrop(ModBlocks.WALL_EXTINGUISHED_TORCH, ModItems.EXTINGUISHED_TORCH);
		this.addDrop(ModBlocks.GLACIAN_WALL_SIGN, ModItems.GLACIAN_SIGN);
		this.addDrop(ModBlocks.STEEL_DOOR, BlockLootTableGenerator::addDoorDrop);
		this.addDrop(ModBlocks.GLACIAN_DOOR, BlockLootTableGenerator::addDoorDrop);

		this.addDrop(ModBlocks.IRON_SLIDING_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.IRON_SLIDING_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.STEEL_SLIDING_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.STEEL_SLIDING_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.DESH_SLIDING_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.DESH_SLIDING_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.OSTRUM_SLIDING_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.OSTRUM_SLIDING_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.CALORITE_SLIDING_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.CALORITE_SLIDING_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.AIRLOCK, BlockLootTableGenerator.dropsWithProperty(ModBlocks.AIRLOCK, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));
		this.addDrop(ModBlocks.REINFORCED_DOOR, BlockLootTableGenerator.dropsWithProperty(ModBlocks.REINFORCED_DOOR, SlidingDoorBlock.LOCATION, DoorState.BOTTOM));

		this.addDrop(ModBlocks.MOON_STONE, block -> BlockLootTableGenerator.drops(block, ModBlocks.MOON_COBBLESTONE));
		this.addDrop(ModBlocks.MARS_STONE, block -> BlockLootTableGenerator.drops(block, ModBlocks.MARS_COBBLESTONE));
		this.addDrop(ModBlocks.VENUS_STONE, block -> BlockLootTableGenerator.drops(block, ModBlocks.VENUS_COBBLESTONE));
		this.addDrop(ModBlocks.MERCURY_STONE, block -> BlockLootTableGenerator.drops(block, ModBlocks.MERCURY_COBBLESTONE));
		this.addDrop(ModBlocks.GLACIO_STONE, block -> BlockLootTableGenerator.drops(block, ModBlocks.GLACIO_COBBLESTONE));

		this.addDrop(ModBlocks.MOON_CHEESE_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.CHEESE));
		this.addDrop(ModBlocks.MOON_DESH_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_DESH));
		this.addDrop(ModBlocks.MOON_IRON_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.MOON_ICE_SHARD_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD));
		this.addDrop(ModBlocks.MARS_IRON_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.MARS_ICE_SHARD_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD));
		this.addDrop(ModBlocks.MARS_DIAMOND_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.DIAMOND));
		this.addDrop(ModBlocks.MARS_OSTRUM_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_OSTRUM));
		this.addDrop(ModBlocks.MARS_ICE_SHARD_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD));
		this.addDrop(ModBlocks.MERCURY_IRON_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.VENUS_COAL_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.COAL));
		this.addDrop(ModBlocks.VENUS_GOLD_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_GOLD));
		this.addDrop(ModBlocks.VENUS_DIAMOND_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.DIAMOND));
		this.addDrop(ModBlocks.VENUS_CALORITE_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_CALORITE));
		this.addDrop(ModBlocks.GLACIO_ICE_SHARD_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD));
		this.addDrop(ModBlocks.GLACIO_COAL_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.COAL));
		this.addDrop(ModBlocks.GLACIO_COPPER_ORE, BlockLootTableGenerator::copperOreDrops);
		this.addDrop(ModBlocks.GLACIO_IRON_ORE, block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.GLACIO_LAPIS_ORE, BlockLootTableGenerator::lapisOreDrops);
		this.addDrop(ModBlocks.DEEPSLATE_ICE_SHARD_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD));
		this.addDrop(ModBlocks.DEEPSLATE_DESH_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_DESH));
		this.addDrop(ModBlocks.DEEPSLATE_OSTRUM_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_OSTRUM));
		this.addDrop(ModBlocks.DEEPSLATE_CALORITE_ORE, block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_CALORITE));
	}
}