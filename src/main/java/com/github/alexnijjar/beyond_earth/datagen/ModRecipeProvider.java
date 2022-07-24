package com.github.alexnijjar.beyond_earth.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.github.alexnijjar.beyond_earth.registry.ModBlocks;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.registry.ModTags;
import com.google.common.collect.ImmutableList;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;

class ModRecipeProvider extends FabricRecipeProvider implements ModBlocks {
	ModRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

		// Blocks

		// Blasting + Smelting
		offerBlastingRecipe(exporter, Items.IRON_INGOT, ModItems.STEEL_INGOT);
		offerBlastingRecipe(exporter, ImmutableList.of(MOON_DESH_ORE, ModItems.RAW_DESH), ModItems.DESH_INGOT);
		offerBlastingRecipe(exporter, ImmutableList.of(MARS_OSTRUM_ORE, ModItems.RAW_OSTRUM), ModItems.OSTRUM_INGOT);
		offerBlastingRecipe(exporter, ImmutableList.of(VENUS_CALORITE_ORE, ModItems.RAW_CALORITE), ModItems.CALORITE_INGOT);
		offerBlastingRecipe(exporter, ImmutableList.of(MOON_ICE_SHARD_ORE, MARS_ICE_SHARD_ORE, GLACIO_ICE_SHARD_ORE), ModItems.ICE_SHARD);

		offerBlastingRecipe(exporter, MOON_CHEESE_ORE, ModItems.CHEESE);
		offerBlastingRecipe(exporter, ImmutableList.of(VENUS_COAL_ORE, GLACIO_COAL_ORE), Items.COAL);
		offerBlastingRecipe(exporter, GLACIO_COPPER_ORE, Items.COPPER_INGOT);
		offerBlastingRecipe(exporter, ImmutableList.of(MOON_IRON_ORE, MARS_IRON_ORE, MERCURY_IRON_ORE, GLACIO_IRON_ORE), Items.IRON_INGOT);
		offerBlastingRecipe(exporter, VENUS_GOLD_ORE, Items.GOLD_INGOT);
		offerBlastingRecipe(exporter, GLACIO_LAPIS_ORE, Items.LAPIS_LAZULI);
		offerBlastingRecipe(exporter, ImmutableList.of(MARS_DIAMOND_ORE, VENUS_DIAMOND_ORE), Items.DIAMOND);

		// Stone
		offerSmelting(exporter, ImmutableList.of(MOON_COBBLESTONE), MOON_STONE, 0.2f, 200, null);
		offerSmelting(exporter, ImmutableList.of(MARS_COBBLESTONE), MARS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, ImmutableList.of(VENUS_COBBLESTONE), VENUS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, ImmutableList.of(MERCURY_COBBLESTONE), MERCURY_STONE, 0.2f, 200, null);
		offerSmelting(exporter, ImmutableList.of(GLACIO_COBBLESTONE), GLACIO_STONE, 0.2f, 200, null);

		// Slabs
		offerCustomSlabRecipe(exporter, MOON_STONE_BRICK_SLAB, MOON_STONE);
		offerCustomSlabRecipe(exporter, CHISELED_MOON_STONE_SLAB, CHISELED_MOON_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, POLISHED_MOON_STONE_SLAB, POLISHED_MOON_STONE);
		offerCustomSlabRecipe(exporter, MARS_STONE_BRICK_SLAB, MARS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, CHISELED_MARS_STONE_SLAB, CHISELED_MARS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, POLISHED_MARS_STONE_SLAB, POLISHED_MARS_STONE);
		offerCustomSlabRecipe(exporter, MERCURY_STONE_BRICK_SLAB, MERCURY_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, CHISELED_MERCURY_STONE_SLAB, CHISELED_MERCURY_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, POLISHED_MERCURY_STONE_SLAB, POLISHED_MERCURY_STONE);
		offerCustomSlabRecipe(exporter, VENUS_SANDSTONE_BRICK_SLAB, VENUS_SANDSTONE_BRICKS);
		offerCustomSlabRecipe(exporter, VENUS_STONE_BRICK_SLAB, VENUS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, CHISELED_VENUS_STONE_SLAB, CHISELED_VENUS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, POLISHED_VENUS_STONE_SLAB, POLISHED_VENUS_STONE);
		offerCustomSlabRecipe(exporter, GLACIO_STONE_BRICK_SLAB, GLACIO_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, CHISELED_GLACIO_STONE_SLAB, CHISELED_GLACIO_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, POLISHED_GLACIO_STONE_SLAB, POLISHED_GLACIO_STONE);
		offerCustomSlabRecipe(exporter, IRON_PLATING_SLAB, IRON_PLATING);
		offerCustomSlabRecipe(exporter, STEEL_PLATING_SLAB, STEEL_PLATING);
		offerCustomSlabRecipe(exporter, DESH_PLATING_SLAB, DESH_PLATING);
		offerCustomSlabRecipe(exporter, OSTRUM_PLATING_SLAB, OSTRUM_PLATING);
		offerCustomSlabRecipe(exporter, CALORITE_PLATING_SLAB, CALORITE_PLATING);
		offerCustomSlabRecipe(exporter, PERMAFROST_BRICK_SLAB, PERMAFROST_BRICKS);

		// Walls
		offerWallRecipe(exporter, MOON_STONE_BRICK_WALL, MOON_STONE_BRICKS);
		offerWallRecipe(exporter, MARS_STONE_BRICK_WALL, MARS_STONE_BRICKS);
		offerWallRecipe(exporter, VENUS_STONE_BRICK_WALL, VENUS_STONE_BRICKS);
		offerWallRecipe(exporter, MERCURY_STONE_BRICK_WALL, MERCURY_STONE_BRICKS);
		offerWallRecipe(exporter, GLACIO_STONE_BRICK_WALL, GLACIO_STONE_BRICKS);

		// Stairs
		offerStairsRecipe(exporter, MOON_STONE_BRICK_STAIRS, MOON_STONE_BRICKS);
		offerStairsRecipe(exporter, CHISELED_MOON_STONE_STAIRS, CHISELED_MOON_STONE_BRICKS);
		offerStairsRecipe(exporter, POLISHED_MOON_STONE_STAIRS, POLISHED_MOON_STONE);
		offerStairsRecipe(exporter, MARS_STONE_BRICK_STAIRS, MARS_STONE_BRICKS);
		offerStairsRecipe(exporter, CHISELED_MARS_STONE_STAIRS, CHISELED_MARS_STONE_BRICKS);
		offerStairsRecipe(exporter, POLISHED_MARS_STONE_STAIRS, POLISHED_MARS_STONE);
		offerStairsRecipe(exporter, VENUS_SANDSTONE_BRICK_STAIRS, VENUS_SANDSTONE_BRICKS);
		offerStairsRecipe(exporter, VENUS_STONE_BRICK_STAIRS, VENUS_STONE_BRICKS);
		offerStairsRecipe(exporter, CHISELED_VENUS_STONE_STAIRS, CHISELED_VENUS_STONE_BRICKS);
		offerStairsRecipe(exporter, POLISHED_VENUS_STONE_STAIRS, POLISHED_VENUS_STONE);
		offerStairsRecipe(exporter, MERCURY_STONE_BRICK_STAIRS, MERCURY_STONE_BRICKS);
		offerStairsRecipe(exporter, CHISELED_MERCURY_STONE_STAIRS, CHISELED_MERCURY_STONE_BRICKS);
		offerStairsRecipe(exporter, POLISHED_MERCURY_STONE_STAIRS, POLISHED_MERCURY_STONE);
		offerStairsRecipe(exporter, GLACIO_STONE_BRICK_STAIRS, GLACIO_STONE_BRICKS);
		offerStairsRecipe(exporter, CHISELED_GLACIO_STONE_STAIRS, CHISELED_GLACIO_STONE_BRICKS);
		offerStairsRecipe(exporter, IRON_PLATING_STAIRS, IRON_PLATING);
		offerStairsRecipe(exporter, STEEL_PLATING_STAIRS, STEEL_PLATING);
		offerStairsRecipe(exporter, DESH_PLATING_STAIRS, DESH_PLATING);
		offerStairsRecipe(exporter, OSTRUM_PLATING_STAIRS, OSTRUM_PLATING);
		offerStairsRecipe(exporter, CALORITE_PLATING_STAIRS, CALORITE_PLATING);
		offerStairsRecipe(exporter, PERMAFROST_BRICK_STAIRS, PERMAFROST_BRICKS);

		// Buttons
		offerButtonRecipe(exporter, IRON_PLATING_BUTTON, IRON_PLATING);
		offerButtonRecipe(exporter, STEEL_PLATING_BUTTON, STEEL_PLATING);
		offerButtonRecipe(exporter, DESH_PLATING_BUTTON, DESH_PLATING);
		offerButtonRecipe(exporter, OSTRUM_PLATING_BUTTON, OSTRUM_PLATING);
		offerButtonRecipe(exporter, CALORITE_PLATING_BUTTON, CALORITE_PLATING);

		// Pressure Plates
		createPressurePlateRecipe(exporter, IRON_PLATING_PRESSURE_PLATE, IRON_PLATING);
		createPressurePlateRecipe(exporter, STEEL_PLATING_PRESSURE_PLATE, STEEL_PLATING);
		createPressurePlateRecipe(exporter, DESH_PLATING_PRESSURE_PLATE, DESH_PLATING);
		createPressurePlateRecipe(exporter, OSTRUM_PLATING_PRESSURE_PLATE, OSTRUM_PLATING);
		createPressurePlateRecipe(exporter, CALORITE_PLATING_PRESSURE_PLATE, CALORITE_PLATING);

		// Stonecutting
		// --->
		// Bricks
		offerStonecuttingRecipe(exporter, MOON_STONE_BRICKS, MOON_STONE);
		offerStonecuttingRecipe(exporter, MARS_STONE_BRICKS, MARS_STONE);
		offerStonecuttingRecipe(exporter, VENUS_SANDSTONE_BRICKS, VENUS_SANDSTONE);
		offerStonecuttingRecipe(exporter, VENUS_STONE_BRICKS, VENUS_STONE);
		offerStonecuttingRecipe(exporter, MERCURY_STONE_BRICKS, MERCURY_STONE);
		offerStonecuttingRecipe(exporter, GLACIO_STONE_BRICKS, GLACIO_STONE);
		offerStonecuttingRecipe(exporter, PERMAFROST_BRICKS, PERMAFROST);

		// Chiseled
		offerStonecuttingRecipe(exporter, CHISELED_MOON_STONE_BRICKS, MOON_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_MARS_STONE_BRICKS, MARS_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_VENUS_STONE_BRICKS, VENUS_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_MERCURY_STONE_BRICKS, MERCURY_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_GLACIO_STONE_BRICKS, GLACIO_STONE);

		// Polished
		offerStonecuttingRecipe(exporter, POLISHED_MOON_STONE, MOON_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_MARS_STONE, MARS_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_VENUS_STONE, VENUS_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_MERCURY_STONE, MERCURY_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_GLACIO_STONE, GLACIO_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_CONGLOMERATE, CONGLOMERATE);
		// <---

		// Pillars
		offerPillarRecipe(exporter, MOON_PILLAR, MOON_STONE_BRICKS);
		offerPillarRecipe(exporter, MARS_PILLAR, MARS_STONE_BRICKS);
		offerPillarRecipe(exporter, VENUS_PILLAR, VENUS_STONE_BRICKS);
		offerPillarRecipe(exporter, MERCURY_PILLAR, MERCURY_STONE_BRICKS);
		offerPillarRecipe(exporter, GLACIO_PILLAR, GLACIO_STONE_BRICKS);
		offerPillarRecipe(exporter, IRON_PILLAR, IRON_PLATING);
		offerPillarRecipe(exporter, STEEL_PILLAR, STEEL_PLATING);
		offerPillarRecipe(exporter, DESH_PILLAR, DESH_PLATING);
		offerPillarRecipe(exporter, OSTRUM_PILLAR, OSTRUM_PLATING);
		offerPillarRecipe(exporter, CALORITE_PILLAR, CALORITE_PLATING);

		// Compacting
		offerReversibleCompactingRecipes(exporter, ModItems.STEEL_INGOT, STEEL_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.DESH_INGOT, DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.OSTRUM_INGOT, OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.CALORITE_INGOT, CALORITE_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_DESH, RAW_DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_OSTRUM, RAW_OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_CALORITE, RAW_CALORITE_BLOCK);

		// Cracked
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(MOON_STONE_BRICKS), CRACKED_MOON_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(MARS_STONE_BRICKS), CRACKED_MARS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(CRACKED_VENUS_STONE_BRICKS), CRACKED_VENUS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(VENUS_STONE_BRICKS), CRACKED_VENUS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(MERCURY_STONE_BRICKS), CRACKED_MERCURY_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(GLACIO_STONE_BRICKS), CRACKED_GLACIO_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, ImmutableList.of(PERMAFROST_BRICKS), CRACKED_PERMAFROST_BRICKS);

		// Polished
		offerPolishedStoneRecipe(exporter, POLISHED_MOON_STONE, MOON_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_MARS_STONE, MARS_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_VENUS_STONE, VENUS_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_MERCURY_STONE, MERCURY_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_GLACIO_STONE, GLACIO_STONE);

		// Chiseled
		offerChiseledBlockRecipe(exporter, CHISELED_MOON_STONE_BRICKS, MOON_STONE_BRICKS);
		offerChiseledBlockRecipe(exporter, CHISELED_MARS_STONE_BRICKS, MARS_STONE_BRICKS);
		offerChiseledBlockRecipe(exporter, CHISELED_VENUS_STONE_BRICKS, VENUS_STONE_BRICKS);
		offerChiseledBlockRecipe(exporter, CHISELED_MERCURY_STONE_BRICKS, MERCURY_STONE_BRICKS);
		offerChiseledBlockRecipe(exporter, CHISELED_GLACIO_STONE_BRICKS, GLACIO_STONE_BRICKS);

		// Bricks
		offerQuadRecipe(exporter, MOON_STONE_BRICKS, MOON_STONE);
		offerQuadRecipe(exporter, MARS_STONE_BRICKS, MARS_STONE);
		offerQuadRecipe(exporter, VENUS_STONE_BRICKS, VENUS_STONE);
		offerQuadRecipe(exporter, MERCURY_STONE_BRICKS, MERCURY_STONE);
		offerQuadRecipe(exporter, GLACIO_STONE_BRICKS, GLACIO_STONE);
		offerQuadRecipe(exporter, PERMAFROST_BRICKS, PERMAFROST);

		// Sandstone
		offerQuadRecipe(exporter, VENUS_SANDSTONE, VENUS_SAND);

		// Ice
		offerQuadRecipe(exporter, 1, Blocks.ICE, ModItems.ICE_SHARD);

		// Rocket Launch Pad
		ShapedRecipeJsonBuilder.create(ROCKET_LAUNCH_PAD, 9).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), ModTags.IRON_PLATES).pattern("#|#").pattern("|#|").pattern("#|#").group("rocket_launch_pad")
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Metals

		offerQuadRecipe(exporter, 16, IRON_PLATING, ModTags.IRON_PLATES);
		offerPlatingRecipe(exporter, STEEL_PLATING, ModTags.COMPRESSED_STEEL);
		offerPlatingRecipe(exporter, DESH_PLATING, ModTags.DESH_PLATES);
		offerPlatingRecipe(exporter, OSTRUM_PLATING, ModTags.COMPRESSED_OSTRUM);
		offerPlatingRecipe(exporter, CALORITE_PLATING, ModTags.COMPRESSED_CALORITE);

		RecipeProvider.createDoorRecipe(STEEL_DOOR, Ingredient.ofItems(ModItems.COMPRESSED_STEEL)).criterion(RecipeProvider.hasItem(ModItems.COMPRESSED_STEEL), RecipeProvider.conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(BLUE_IRON_PILLAR, 6).input(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('G'), Blocks.GLOWSTONE).input(Character.valueOf('B'), Items.BLUE_DYE).pattern("#B#").pattern("#G#").pattern("#B#")
				.group("blue_iron_pillar").criterion(hasItem(ModItems.IRON_PLATE), conditionsFromTag(ModTags.IRON_PLATES)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(MARKED_IRON_PILLAR, 6).input(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('Y'), Items.YELLOW_DYE).input(Character.valueOf('B'), Items.BLACK_DYE).pattern("#Y#").pattern("#B#").pattern("#Y#")
				.group(null).criterion(hasItem(ModItems.IRON_PLATE), conditionsFromTag(ModTags.IRON_PLATES)).offerTo(exporter);

		// Machines
		// NASA Workbench
		ShapedRecipeJsonBuilder.create(NASA_WORKBENCH).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('C'), Blocks.CRAFTING_TABLE).input(Character.valueOf('B'), ModTags.STEEL_BLOCKS)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('T'), Blocks.REDSTONE_TORCH).input(Character.valueOf('L'), Blocks.LEVER).pattern("TLT").pattern("#C#").pattern("RBR").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Solar Panel
		ShapedRecipeJsonBuilder.create(SOLAR_PANEL).input(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('S'), ModItems.COMPRESSED_STEEL).input(Character.valueOf('G'), Blocks.BLUE_STAINED_GLASS).pattern("GGG").pattern("S#S")
				.pattern("###").group(null).criterion(hasItem(ModItems.COMPRESSED_DESH), conditionsFromTag(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Coal Generator
		ShapedRecipeJsonBuilder.create(COAL_GENERATOR).input(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('I'), Items.IRON_INGOT).input(Character.valueOf('F'), Blocks.FURNACE).pattern("#I#").pattern("#F#").pattern("#I#").group(null)
				.criterion(hasItem(ModItems.IRON_PLATE), conditionsFromTag(ModTags.IRON_PLATES)).offerTo(exporter);

		// Compressor
		ShapedRecipeJsonBuilder.create(COMPRESSOR).input(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('P'), Blocks.PISTON).pattern("#P#").pattern("# #").pattern("#P#").group(null)
				.criterion(hasItem(ModItems.IRON_PLATE), conditionsFromTag(ModTags.IRON_PLATES)).offerTo(exporter);

		// Fuel Refinery
		ShapedRecipeJsonBuilder.create(FUEL_REFINERY).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('F'), Blocks.FURNACE).input(Character.valueOf('B'), Items.BUCKET).pattern("###").pattern("BFB").pattern("###").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Oxygen Loader
		ShapedRecipeJsonBuilder.create(OXYGEN_LOADER).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), Blocks.LIGHTNING_ROD)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("#F#").pattern("TLT").pattern("#R#").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Oxygen Distributor
		ShapedRecipeJsonBuilder.create(OXYGEN_DISTRIBUTOR).input(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), OXYGEN_LOADER)
				.input(Character.valueOf('F'), ModItems.ENGINE_FAN).input(Character.valueOf('G'), ModItems.OXYGEN_GEAR).pattern("FFF").pattern("TLT").pattern("#G#").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_DESH), conditionsFromTag(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Water Pump
		ShapedRecipeJsonBuilder.create(WATER_PUMP).input(Character.valueOf('#'), ModTags.DESH_PLATES).input(Character.valueOf('H'), Blocks.HOPPER).input(Character.valueOf('D'), Blocks.DISPENSER).pattern(" D ").pattern(" #D").pattern(" H ").group(null)
				.criterion(hasItem(ModItems.DESH_PLATE), conditionsFromTag(ModTags.DESH_PLATES)).offerTo(exporter);

		// Energizer
		ShapedRecipeJsonBuilder.create(ENERGIZER).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('D'), Blocks.DIAMOND_BLOCK).input(Character.valueOf('I'), Items.DIAMOND).input(Character.valueOf('B'), OSTRUM_BLOCK)
				.pattern("#I#").pattern("#D#").pattern("B#B").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Cryo Freezer
		ShapedRecipeJsonBuilder.create(CRYO_FREEZER).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('T'), ModItems.OSTRUM_TANK)
				.input(Character.valueOf('B'), OSTRUM_BLOCK).pattern("#O#").pattern("OTO").pattern("BOB").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Oxygen Sensor
		ShapedRecipeJsonBuilder.create(OXYGEN_SENSOR).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('V'), Items.OBSERVER).input(Character.valueOf('L'), Items.REDSTONE_LAMP).input(Character.valueOf('F'), ModItems.ENGINE_FAN)
		.pattern("#F#").pattern("OVO").pattern("#L#").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Flags
		offerFlagRecipe(exporter, FLAG, Blocks.WHITE_WOOL);
		offerFlagRecipe(exporter, FLAG_BLUE, Blocks.BLUE_WOOL);
		offerFlagRecipe(exporter, FLAG_BROWN, Blocks.BROWN_WOOL);
		offerFlagRecipe(exporter, FLAG_CYAN, Blocks.CYAN_WOOL);
		offerFlagRecipe(exporter, FLAG_GRAY, Blocks.GRAY_WOOL);
		offerFlagRecipe(exporter, FLAG_GREEN, Blocks.GREEN_WOOL);
		offerFlagRecipe(exporter, FLAG_LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
		offerFlagRecipe(exporter, FLAG_LIME, Blocks.LIME_WOOL);
		offerFlagRecipe(exporter, FLAG_MAGENTA, Blocks.MAGENTA_WOOL);
		offerFlagRecipe(exporter, FLAG_ORANGE, Blocks.ORANGE_WOOL);
		offerFlagRecipe(exporter, FLAG_PINK, Blocks.PINK_WOOL);
		offerFlagRecipe(exporter, FLAG_PURPLE, Blocks.PURPLE_WOOL);
		offerFlagRecipe(exporter, FLAG_RED, Blocks.RED_WOOL);
		offerFlagRecipe(exporter, FLAG_YELLOW, Blocks.YELLOW_WOOL);

		// Items

		// Rover
		ShapedRecipeJsonBuilder.create(ModItems.TIER_1_ROVER).input(Character.valueOf('#'), ModItems.WHEEL).input(Character.valueOf('S'), ModTags.STEEL_BLOCKS).input(Character.valueOf('|'), ModTags.IRON_RODS)
				.input(Character.valueOf('D'), ModTags.DESH_BLOCKS).input(Character.valueOf('P'), ModItems.DESH_PLATE).input(Character.valueOf('E'), ModItems.DESH_ENGINE).pattern("D |").pattern("SDE").pattern("#P#").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_DESH), conditionsFromTag(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Oxygen Tank
		ShapedRecipeJsonBuilder.create(ModItems.OXYGEN_TANK).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), Blocks.RED_WOOL).pattern("|| ").pattern("## ").pattern("## ").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Guide Book
		ShapedRecipeJsonBuilder.create(ModItems.ASTRODUX).input(Character.valueOf('#'), Items.BOOK).input(Character.valueOf('S'), ModItems.STEEL_INGOT).pattern("SSS").pattern("S#S").pattern("SSS").group(null)
				.criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK)).offerTo(exporter);

		// Compacting nuggets
		offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ModItems.STEEL_NUGGET, ModItems.STEEL_INGOT, "steel_ingot_from_nuggets", "steel_ingot");
		offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ModItems.DESH_NUGGET, ModItems.DESH_INGOT, "desh_ingot_from_nuggets", "desh_ingot");
		offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ModItems.OSTRUM_NUGGET, ModItems.OSTRUM_INGOT, "ostrum_ingot_from_nuggets", "ostrum_ingot");
		offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter, ModItems.CALORITE_NUGGET, ModItems.CALORITE_INGOT, "calorite_ingot_from_nuggets", "calorite_ingot");

		// Armour
		ShapedRecipeJsonBuilder.create(ModItems.SPACE_HELMET).input(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS_PANE).pattern("###").pattern("#G#").group(null)
				.criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromTag(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.SPACE_SUIT).input(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).input(Character.valueOf('O'), ModItems.OXYGEN_GEAR)
				.input(Character.valueOf('T'), ModItems.OXYGEN_TANK).pattern("# #").pattern("TOT").pattern("#W#").group(null).criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.SPACE_PANTS).input(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).pattern("###").pattern("W W").pattern("# #").group(null)
				.criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromTag(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.SPACE_BOOTS).input(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).pattern("W W").pattern("# #").group(null)
				.criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromTag(ModTags.STEEL_INGOTS)).offerTo(exporter);

		// Netherite
		ShapedRecipeJsonBuilder.create(ModItems.NETHERITE_SPACE_HELMET).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_HELMET).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS_PANE)
				.pattern("#N#").pattern("#G#").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.NETHERITE_SPACE_SUIT).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_CHESTPLATE).input(Character.valueOf('O'), ModItems.OXYGEN_GEAR)
				.input(Character.valueOf('T'), ModItems.OXYGEN_TANK).pattern("# #").pattern("TOT").pattern("#N#").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.NETHERITE_SPACE_PANTS).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_LEGGINGS).input(Character.valueOf('D'), ModItems.COMPRESSED_DESH).pattern("#N#")
				.pattern("D D").pattern("# #").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.NETHERITE_SPACE_BOOTS).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_BOOTS).input(Character.valueOf('D'), ModItems.COMPRESSED_DESH).pattern(" N ")
				.pattern("D D").pattern("# #").group(null).criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Jet Suit
		ShapedRecipeJsonBuilder.create(ModItems.JET_SUIT_HELMET).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_HELMET).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS)
				.pattern("#N#").pattern("#G#").group(null).criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.JET_SUIT).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('B'), ModTags.CALORITE_BLOCKS).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_SUIT)
				.input(Character.valueOf('E'), ModItems.CALORITE_ENGINE).input(Character.valueOf('T'), ModItems.CALORITE_TANK).pattern("# #").pattern("TNT").pattern("BEB").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.JET_SUIT_PANTS).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_PANTS).pattern("#N#").pattern("# #").pattern("# #").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.JET_SUIT_BOOTS).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('B'), ModTags.CALORITE_BLOCKS).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_BOOTS).pattern(" N ")
				.pattern("# #").pattern("B B").group(null).criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		// Hammer
		ShapedRecipeJsonBuilder.create(ModItems.HAMMER).input(Character.valueOf('#'), Items.IRON_INGOT).input(Character.valueOf('|'), Items.STICK).pattern(" # ").pattern(" |#").pattern("|  ").group(null)
				.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT)).offerTo(exporter);

		// Iron Stick
		ShapedRecipeJsonBuilder.create(ModItems.IRON_STICK, 4).input(Character.valueOf('#'), ModTags.IRON_PLATES).pattern("#").pattern("#").group("iron_sticks").criterion(hasItem(ModItems.IRON_PLATE), conditionsFromTag(ModTags.IRON_PLATES))
				.offerTo(exporter);

		// Oxygen Gear
		ShapedRecipeJsonBuilder.create(ModItems.OXYGEN_GEAR).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), ModTags.IRON_RODS).pattern(" | ").pattern("#|#").pattern("#|#").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Wheel
		ShapedRecipeJsonBuilder.create(ModItems.WHEEL).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('B'), Items.BLACK_DYE).pattern(" B ").pattern("B#B").pattern(" B ").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engine Frame
		ShapedRecipeJsonBuilder.create(ModItems.ENGINE_FRAME).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), ModTags.IRON_RODS).pattern("|||").pattern("|#|").pattern("|||").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engine Fan
		ShapedRecipeJsonBuilder.create(ModItems.ENGINE_FAN).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), ModTags.IRON_RODS).pattern(" | ").pattern("|#|").pattern(" | ").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Nose Cone
		ShapedRecipeJsonBuilder.create(ModItems.ROCKET_NOSE_CONE).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), Items.LIGHTNING_ROD).pattern(" | ").pattern(" # ").pattern("###").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engines
		ShapedRecipeJsonBuilder.create(ModItems.STEEL_ENGINE).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.DESH_ENGINE).input(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion(hasItem(ModItems.COMPRESSED_DESH), conditionsFromTag(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.OSTRUM_ENGINE).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.CALORITE_ENGINE).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###")
				.pattern(" E ").pattern(" F ").group("engines").criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		// Tanks
		ShapedRecipeJsonBuilder.create(ModItems.STEEL_TANK).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|").pattern("## ")
				.group("tanks").criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.DESH_TANK).input(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|").pattern("## ")
				.group("tanks").criterion(hasItem(ModItems.COMPRESSED_DESH), conditionsFromTag(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.OSTRUM_TANK).input(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|").pattern("## ")
				.group("tanks").criterion(hasItem(ModItems.COMPRESSED_OSTRUM), conditionsFromTag(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonBuilder.create(ModItems.CALORITE_TANK).input(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion(hasItem(ModItems.COMPRESSED_CALORITE), conditionsFromTag(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		// Fin
		ShapedRecipeJsonBuilder.create(ModItems.ROCKET_FIN, 4).input(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).pattern(" # ").pattern("###").pattern("# #").group(null)
				.criterion(hasItem(ModItems.COMPRESSED_STEEL), conditionsFromTag(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		offerPolishedStoneRecipe(exporter, output, input);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		RecipeProvider.createCondensingRecipe(output, Ingredient.fromTag(input)).criterion("item", conditionsFromTag(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonBuilder.create(output, amount).input(Character.valueOf('S'), input).pattern("SS").pattern("SS").criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonBuilder.create(output, amount).input(Character.valueOf('S'), input).pattern("SS").pattern("SS").criterion(hasItem(Items.IRON_INGOT), conditionsFromTag(input)).offerTo(exporter);
	}

	public static void offerPlatingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonBuilder.create(output, 8).input(Character.valueOf('#'), IRON_PLATING).input(Character.valueOf('|'), input).pattern("###").pattern("#|#").pattern("###").group("desh_plating").criterion(hasItem(IRON_PLATING), conditionsFromTag(input))
				.offerTo(exporter);
	}

	public static void offerCustomSlabRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		offerSlabRecipe(exporter, output, input);
		offerStonecuttingRecipe(exporter, output, input, 2);
	}

	public void offerStairsRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		createStairsRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
		offerStonecuttingRecipe(exporter, output, input);
	}

	public void offerBlastingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible input, ItemConvertible output) {
		offerBlastingRecipe(exporter, ImmutableList.of(input), output);
	}

	public void offerBlastingRecipe(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, ItemConvertible output) {
		offerBlasting(exporter, inputs, output, 1.0f, 100, null);
		offerSmelting(exporter, inputs, output, 1.0f, 200, null);
	}

	public void offerCrackedBricksSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, ItemConvertible output) {
		offerSmelting(exporter, inputs, output, 0.2f, 200, null);
	}

	public void offerPillarRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonBuilder.create(output, 2).input('#', input).pattern("#").pattern("#").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
		offerStonecuttingRecipe(exporter, output, input);
	}

	public void offerFlagRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonBuilder.create(output).input(Character.valueOf('#'), input).input(Character.valueOf('|'), ModTags.IRON_RODS).pattern("|##").pattern("|##").pattern("|  ").group("flag").criterion(hasItem(input), conditionsFromItem(input))
				.offerTo(exporter);
	}

	public void offerButtonRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapelessRecipeJsonBuilder.create(output).input(input).group("button").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
	}
}