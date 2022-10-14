package com.github.alexnijjar.ad_astra.datagen;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Items;

class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {

		ModBlocks.BLOCKS.iterator().forEachRemaining(block -> this.addDrop(block.get()));


		this.addDrop(ModBlocks.WALL_EXTINGUISHED_TORCH.get(), ModItems.EXTINGUISHED_TORCH.get());
		this.addDrop(ModBlocks.GLACIAN_WALL_SIGN.get(), ModItems.GLACIAN_SIGN.get());
		this.addDrop(ModBlocks.STEEL_DOOR.get(), BlockLootTableGenerator::addDoorDrop);
		this.addDrop(ModBlocks.GLACIAN_DOOR.get(), BlockLootTableGenerator::addDoorDrop);
		
		this.addDrop(ModBlocks.LAUNCH_PAD.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.LAUNCH_PAD.get(), SlidingDoorBlock.LOCATION, LocationState.CENTER));

		this.addDrop(ModBlocks.IRON_SLIDING_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.IRON_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.STEEL_SLIDING_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.STEEL_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.DESH_SLIDING_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.DESH_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.OSTRUM_SLIDING_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.OSTRUM_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.CALORITE_SLIDING_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.CALORITE_SLIDING_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.AIRLOCK.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.AIRLOCK.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));
		this.addDrop(ModBlocks.REINFORCED_DOOR.get(), BlockLootTableGenerator.dropsWithProperty(ModBlocks.REINFORCED_DOOR.get(), SlidingDoorBlock.LOCATION, LocationState.BOTTOM));

		this.addDrop(ModBlocks.MOON_STONE.get(), block -> BlockLootTableGenerator.drops(block, ModBlocks.MOON_COBBLESTONE.get()));
		this.addDrop(ModBlocks.MARS_STONE.get(), block -> BlockLootTableGenerator.drops(block, ModBlocks.MARS_COBBLESTONE.get()));
		this.addDrop(ModBlocks.VENUS_STONE.get(), block -> BlockLootTableGenerator.drops(block, ModBlocks.VENUS_COBBLESTONE.get()));
		this.addDrop(ModBlocks.MERCURY_STONE.get(), block -> BlockLootTableGenerator.drops(block, ModBlocks.MERCURY_COBBLESTONE.get()));
		this.addDrop(ModBlocks.GLACIO_STONE.get(), block -> BlockLootTableGenerator.drops(block, ModBlocks.GLACIO_COBBLESTONE.get()));

		this.addDrop(ModBlocks.MOON_CHEESE_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.CHEESE.get()));
		this.addDrop(ModBlocks.MOON_DESH_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_DESH.get()));
		this.addDrop(ModBlocks.MOON_IRON_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.MOON_ICE_SHARD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD.get()));
		this.addDrop(ModBlocks.MARS_IRON_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.MARS_ICE_SHARD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD.get()));
		this.addDrop(ModBlocks.MARS_DIAMOND_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.DIAMOND));
		this.addDrop(ModBlocks.MARS_OSTRUM_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_OSTRUM.get()));
		this.addDrop(ModBlocks.MARS_ICE_SHARD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD.get()));
		this.addDrop(ModBlocks.MERCURY_IRON_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.VENUS_COAL_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.COAL));
		this.addDrop(ModBlocks.VENUS_GOLD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_GOLD));
		this.addDrop(ModBlocks.VENUS_DIAMOND_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.DIAMOND));
		this.addDrop(ModBlocks.VENUS_CALORITE_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_CALORITE.get()));
		this.addDrop(ModBlocks.GLACIO_ICE_SHARD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD.get()));
		this.addDrop(ModBlocks.GLACIO_COAL_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.COAL));
		this.addDrop(ModBlocks.GLACIO_COPPER_ORE.get(), BlockLootTableGenerator::copperOreDrops);
		this.addDrop(ModBlocks.GLACIO_IRON_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, Items.RAW_IRON));
		this.addDrop(ModBlocks.GLACIO_LAPIS_ORE.get(), BlockLootTableGenerator::lapisOreDrops);
		this.addDrop(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.ICE_SHARD.get()));
		this.addDrop(ModBlocks.DEEPSLATE_DESH_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_DESH.get()));
		this.addDrop(ModBlocks.DEEPSLATE_OSTRUM_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_OSTRUM.get()));
		this.addDrop(ModBlocks.DEEPSLATE_CALORITE_ORE.get(), block -> BlockLootTableGenerator.oreDrops(block, ModItems.RAW_CALORITE.get()));
	}
}