package com.github.alexnijjar.ad_astra.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
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

		// Blasting
		offerBlasting(exporter, List.of(Items.IRON_INGOT), ModItems.STEEL_INGOT, 1.0f, 100, null);
		// Blasting + Smelting
		offerBlastingRecipe(exporter, List.of(DEEPSLATE_DESH_ORE, MOON_DESH_ORE, ModItems.RAW_DESH), ModItems.DESH_INGOT);
		offerBlastingRecipe(exporter, List.of(DEEPSLATE_OSTRUM_ORE, MARS_OSTRUM_ORE, ModItems.RAW_OSTRUM), ModItems.OSTRUM_INGOT);
		offerBlastingRecipe(exporter, List.of(DEEPSLATE_CALORITE_ORE, VENUS_CALORITE_ORE, ModItems.RAW_CALORITE), ModItems.CALORITE_INGOT);
		offerBlastingRecipe(exporter, List.of(DEEPSLATE_ICE_SHARD_ORE, MOON_ICE_SHARD_ORE, MARS_ICE_SHARD_ORE, GLACIO_ICE_SHARD_ORE), ModItems.ICE_SHARD);

		offerBlastingRecipe(exporter, MOON_CHEESE_ORE, ModItems.CHEESE);
		offerBlastingRecipe(exporter, List.of(VENUS_COAL_ORE, GLACIO_COAL_ORE), Items.COAL);
		offerBlastingRecipe(exporter, GLACIO_COPPER_ORE, Items.COPPER_INGOT);
		offerBlastingRecipe(exporter, List.of(MOON_IRON_ORE, MARS_IRON_ORE, MERCURY_IRON_ORE, GLACIO_IRON_ORE), Items.IRON_INGOT);
		offerBlastingRecipe(exporter, VENUS_GOLD_ORE, Items.GOLD_INGOT);
		offerBlastingRecipe(exporter, GLACIO_LAPIS_ORE, Items.LAPIS_LAZULI);
		offerBlastingRecipe(exporter, List.of(MARS_DIAMOND_ORE, VENUS_DIAMOND_ORE), Items.DIAMOND);

		// Stone
		offerSmelting(exporter, List.of(MOON_COBBLESTONE), MOON_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(MARS_COBBLESTONE), MARS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(VENUS_COBBLESTONE), VENUS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(MERCURY_COBBLESTONE), MERCURY_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(GLACIO_COBBLESTONE), GLACIO_STONE, 0.2f, 200, null);

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
		offerCustomSlabRecipe(exporter, POLISHED_PERMAFROST_SLAB, POLISHED_PERMAFROST);
		offerCustomSlabRecipe(exporter, CHISELED_PERMAFROST_BRICK_SLAB, CHISELED_PERMAFROST_BRICKS);

		// Walls
		offerWallRecipe(exporter, MOON_STONE_BRICK_WALL, MOON_STONE_BRICKS);
		offerWallRecipe(exporter, MARS_STONE_BRICK_WALL, MARS_STONE_BRICKS);
		offerWallRecipe(exporter, VENUS_STONE_BRICK_WALL, VENUS_STONE_BRICKS);
		offerWallRecipe(exporter, MERCURY_STONE_BRICK_WALL, MERCURY_STONE_BRICKS);
		offerWallRecipe(exporter, GLACIO_STONE_BRICK_WALL, GLACIO_STONE_BRICKS);
		offerWallRecipe(exporter, PERMAFROST_BRICK_WALL, PERMAFROST_BRICKS);

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
		offerStairsRecipe(exporter, POLISHED_PERMAFROST_STAIRS, POLISHED_PERMAFROST);
		offerStairsRecipe(exporter, CHISELED_PERMAFROST_BRICK_STAIRS, CHISELED_PERMAFROST_BRICKS);

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
		offerStonecuttingRecipe(exporter, MOON_STONE_BRICKS, POLISHED_MOON_STONE);
		offerStonecuttingRecipe(exporter, MARS_STONE_BRICKS, POLISHED_MARS_STONE);
		offerStonecuttingRecipe(exporter, VENUS_SANDSTONE_BRICKS, VENUS_SANDSTONE);
		offerStonecuttingRecipe(exporter, VENUS_STONE_BRICKS, POLISHED_VENUS_STONE);
		offerStonecuttingRecipe(exporter, MERCURY_STONE_BRICKS, POLISHED_MERCURY_STONE);
		offerStonecuttingRecipe(exporter, GLACIO_STONE_BRICKS, POLISHED_GLACIO_STONE);
		offerStonecuttingRecipe(exporter, PERMAFROST_BRICKS, PERMAFROST);

		// Chiseled
		offerStonecuttingRecipe(exporter, CHISELED_MOON_STONE_BRICKS, MOON_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_MARS_STONE_BRICKS, MARS_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_VENUS_STONE_BRICKS, VENUS_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_MERCURY_STONE_BRICKS, MERCURY_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_GLACIO_STONE_BRICKS, GLACIO_STONE);
		offerStonecuttingRecipe(exporter, CHISELED_PERMAFROST_BRICKS, PERMAFROST);

		// Polished
		offerStonecuttingRecipe(exporter, POLISHED_MOON_STONE, MOON_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_MARS_STONE, MARS_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_VENUS_STONE, VENUS_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_MERCURY_STONE, MERCURY_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_GLACIO_STONE, GLACIO_STONE);
		offerStonecuttingRecipe(exporter, POLISHED_CONGLOMERATE, CONGLOMERATE);
		offerStonecuttingRecipe(exporter, POLISHED_PERMAFROST, PERMAFROST);
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
		offerPillarRecipe(exporter, PERMAFROST_PILLAR, PERMAFROST_BRICKS);

		// Compacting
		offerReversibleCompactingRecipes(exporter, ModItems.STEEL_INGOT, STEEL_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.CHEESE, CHEESE_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.DESH_INGOT, DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.OSTRUM_INGOT, OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.CALORITE_INGOT, CALORITE_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_DESH, RAW_DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_OSTRUM, RAW_OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_CALORITE, RAW_CALORITE_BLOCK);

		// Cracked
		offerCrackedBricksSmeltingRecipe(exporter, List.of(MOON_STONE_BRICKS), CRACKED_MOON_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(MARS_STONE_BRICKS), CRACKED_MARS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(VENUS_STONE_BRICKS), CRACKED_VENUS_SANDSTONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(VENUS_STONE_BRICKS), CRACKED_VENUS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(MERCURY_STONE_BRICKS), CRACKED_MERCURY_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(GLACIO_STONE_BRICKS), CRACKED_GLACIO_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(PERMAFROST_BRICKS), CRACKED_PERMAFROST_BRICKS);

		// Polished
		offerPolishedStoneRecipe(exporter, POLISHED_MOON_STONE, MOON_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_MARS_STONE, MARS_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_VENUS_STONE, VENUS_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_MERCURY_STONE, MERCURY_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_GLACIO_STONE, GLACIO_STONE);
		offerPolishedStoneRecipe(exporter, POLISHED_CONGLOMERATE, CONGLOMERATE);
		offerPolishedStoneRecipe(exporter, POLISHED_PERMAFROST, PERMAFROST);

		// Chiseled
		offerChiseledBlockRecipe(exporter, CHISELED_MOON_STONE_BRICKS, MOON_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, CHISELED_MARS_STONE_BRICKS, MARS_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, CHISELED_VENUS_STONE_BRICKS, VENUS_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, CHISELED_MERCURY_STONE_BRICKS, MERCURY_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, CHISELED_GLACIO_STONE_BRICKS, GLACIO_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, CHISELED_PERMAFROST_BRICKS, PERMAFROST_BRICK_SLAB);

		// Bricks
		offerQuadRecipe(exporter, MOON_STONE_BRICKS, MOON_STONE);
		offerQuadRecipe(exporter, MARS_STONE_BRICKS, MARS_STONE);
		offerQuadRecipe(exporter, VENUS_STONE_BRICKS, VENUS_STONE);
		offerQuadRecipe(exporter, MERCURY_STONE_BRICKS, MERCURY_STONE);
		offerQuadRecipe(exporter, GLACIO_STONE_BRICKS, GLACIO_STONE);
		offerQuadRecipe(exporter, PERMAFROST_BRICKS, PERMAFROST);
		offerQuadRecipe(exporter, VENUS_SANDSTONE_BRICKS, VENUS_SANDSTONE);

		offerQuadRecipe(exporter, PERMAFROST_TILES, PERMAFROST_BRICKS);

		// Sandstone
		offerQuadRecipe(exporter, VENUS_SANDSTONE, VENUS_SAND);

		// Ice
		offerQuadRecipe(exporter, 1, Blocks.ICE, ModItems.ICE_SHARD);

		// Rocket Launch Pad
		ShapedRecipeJsonFactory.create(ROCKET_LAUNCH_PAD, 9).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_PLATES).pattern("#|#").pattern("|#|").pattern("#|#").group("rocket_launch_pad")
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Metals

		ShapedRecipeJsonFactory.create(IRON_PLATING, 32).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).pattern("###").pattern("###").pattern("###").group("iron_plates").criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL))
				.offerTo(exporter);
		offerPlatingRecipe(exporter, STEEL_PLATING, ModTags.COMPRESSED_STEEL);
		offerPlatingRecipe(exporter, DESH_PLATING, ModTags.DESH_PLATES);
		offerPlatingRecipe(exporter, OSTRUM_PLATING, ModTags.COMPRESSED_OSTRUM);
		offerPlatingRecipe(exporter, CALORITE_PLATING, ModTags.COMPRESSED_CALORITE);

		createDoorRecipe(STEEL_DOOR, Ingredient.ofItems(ModItems.COMPRESSED_STEEL)).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		createTrapdoorRecipe(STEEL_TRAPDOOR, Ingredient.ofItems(ModItems.COMPRESSED_STEEL)).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(BLUE_IRON_PILLAR, 6).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('G'), Blocks.GLOWSTONE).input(Character.valueOf('B'), Items.BLUE_DYE).pattern("#B#").pattern("#G#").pattern("#B#")
				.group("blue_iron_pillar").criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(MARKED_IRON_PILLAR, 6).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('Y'), Items.YELLOW_DYE).input(Character.valueOf('B'), Items.BLACK_DYE).pattern("#Y#").pattern("#B#")
				.pattern("#Y#").group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Machines
		// NASA Workbench
		ShapedRecipeJsonFactory.create(NASA_WORKBENCH).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('C'), Blocks.CRAFTING_TABLE).m_hadhiznl(Character.valueOf('B'), ModTags.STEEL_BLOCKS)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('T'), Blocks.REDSTONE_TORCH).input(Character.valueOf('L'), Blocks.LEVER).pattern("TLT").pattern("#C#").pattern("RBR").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Solar Panel
		ShapedRecipeJsonFactory.create(SOLAR_PANEL).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('S'), ModItems.COMPRESSED_STEEL).input(Character.valueOf('G'), Blocks.BLUE_STAINED_GLASS).pattern("GGG").pattern("S#S")
				.pattern("###").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Coal Generator
		ShapedRecipeJsonFactory.create(COAL_GENERATOR).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('I'), Items.IRON_INGOT).input(Character.valueOf('F'), Blocks.FURNACE).pattern("#I#").pattern("#F#").pattern("#I#")
				.group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Compressor
		ShapedRecipeJsonFactory.create(COMPRESSOR).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('P'), Blocks.PISTON).pattern("#P#").pattern("# #").pattern("#P#").group(null)
				.criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Fuel Refinery
		ShapedRecipeJsonFactory.create(FUEL_REFINERY).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('F'), Blocks.FURNACE).input(Character.valueOf('B'), Items.BUCKET).pattern("###").pattern("BFB").pattern("###")
				.group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Oxygen Loader
		ShapedRecipeJsonFactory.create(OXYGEN_LOADER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), Blocks.LIGHTNING_ROD)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("#F#").pattern("TLT").pattern("#R#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL))
				.offerTo(exporter);

		// Oxygen Distributor
		ShapedRecipeJsonFactory.create(OXYGEN_DISTRIBUTOR).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), OXYGEN_LOADER)
				.input(Character.valueOf('F'), ModItems.ENGINE_FAN).input(Character.valueOf('G'), ModItems.OXYGEN_GEAR).pattern("FFF").pattern("TLT").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Water Pump
		ShapedRecipeJsonFactory.create(WATER_PUMP).m_hadhiznl(Character.valueOf('#'), ModTags.DESH_PLATES).input(Character.valueOf('H'), Blocks.HOPPER).input(Character.valueOf('D'), Blocks.DISPENSER).pattern(" D ").pattern(" #D").pattern(" H ").group(null)
				.criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Energizer
		ShapedRecipeJsonFactory.create(ENERGIZER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('D'), Blocks.DIAMOND_BLOCK).input(Character.valueOf('I'), Items.DIAMOND).input(Character.valueOf('B'), OSTRUM_BLOCK)
				.pattern("#I#").pattern("#D#").pattern("B#B").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Cryo Freezer
		ShapedRecipeJsonFactory.create(CRYO_FREEZER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('T'), ModItems.OSTRUM_TANK)
				.input(Character.valueOf('B'), OSTRUM_BLOCK).pattern("#O#").pattern("OTO").pattern("BOB").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Oxygen Sensor
		ShapedRecipeJsonFactory.create(OXYGEN_SENSOR).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('V'), Items.OBSERVER)
				.input(Character.valueOf('L'), Items.REDSTONE_LAMP).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("#F#").pattern("OVO").pattern("#L#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Cables
		ShapedRecipeJsonFactory.create(STEEL_CABLE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('C'), Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(DESH_CABLE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('C'), Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(DESH_FLUID_PIPE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('C'), Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(OSTRUM_FLUID_PIPE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('C'), Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

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
		ShapedRecipeJsonFactory.create(ModItems.TIER_1_ROVER).input(Character.valueOf('#'), ModItems.WHEEL).m_hadhiznl(Character.valueOf('S'), ModTags.STEEL_BLOCKS).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS)
				.m_hadhiznl(Character.valueOf('D'), ModTags.DESH_BLOCKS).input(Character.valueOf('P'), ModItems.DESH_PLATE).input(Character.valueOf('E'), ModItems.DESH_ENGINE).pattern("D |").pattern("SDE").pattern("#P#").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Oxygen Tank
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_TANK).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), Blocks.RED_WOOL).pattern("|| ").pattern("## ").pattern("## ").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Guide Book
		ShapedRecipeJsonFactory.create(ModItems.ASTRODUX).input(Character.valueOf('#'), Items.BOOK).input(Character.valueOf('S'), ModItems.STEEL_INGOT).pattern("SSS").pattern("S#S").pattern("SSS").group(null)
				.criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK)).offerTo(exporter);

		// Compacting nuggets
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.STEEL_NUGGET, ModItems.STEEL_INGOT, "steel_ingot_from_nuggets", "steel_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.DESH_NUGGET, ModItems.DESH_INGOT, "desh_ingot_from_nuggets", "desh_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.OSTRUM_NUGGET, ModItems.OSTRUM_INGOT, "ostrum_ingot_from_nuggets", "ostrum_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.CALORITE_NUGGET, ModItems.CALORITE_INGOT, "calorite_ingot_from_nuggets", "calorite_ingot");

		ShapelessRecipeJsonFactory.create(Items.FLINT_AND_STEEL).m_jrksubfg(ModTags.STEEL_INGOTS).input(Items.FLINT).criterion("has_flint", conditionsFromItem(Items.FLINT)).criterion("has_obsidian", conditionsFromItem(Blocks.OBSIDIAN)).offerTo(exporter);

		// Armour
		ShapedRecipeJsonFactory.create(ModItems.SPACE_HELMET).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS_PANE).pattern("###").pattern("#G#").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_SUIT).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).input(Character.valueOf('O'), ModItems.OXYGEN_GEAR)
				.input(Character.valueOf('T'), ModItems.OXYGEN_TANK).pattern("# #").pattern("TOT").pattern("#W#").group(null).criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_PANTS).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).pattern("###").pattern("W W").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_BOOTS).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_INGOTS).input(Character.valueOf('W'), Blocks.WHITE_WOOL).pattern("W W").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		// Netherite
		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_HELMET).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_HELMET).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS_PANE)
				.pattern("#N#").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_SUIT).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_CHESTPLATE).input(Character.valueOf('O'), ModItems.OXYGEN_GEAR)
				.input(Character.valueOf('T'), ModItems.OXYGEN_TANK).pattern("# #").pattern("TOT").pattern("#N#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_PANTS).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_LEGGINGS).input(Character.valueOf('D'), ModItems.COMPRESSED_DESH)
				.pattern("#N#").pattern("D D").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_BOOTS).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('N'), Items.NETHERITE_BOOTS).input(Character.valueOf('D'), ModItems.COMPRESSED_DESH).pattern(" N ")
				.pattern("D D").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Jet Suit
		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_HELMET).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_HELMET).input(Character.valueOf('G'), Blocks.ORANGE_STAINED_GLASS)
				.pattern("#N#").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).m_hadhiznl(Character.valueOf('B'), ModTags.CALORITE_BLOCKS).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_SUIT)
				.input(Character.valueOf('E'), ModItems.CALORITE_ENGINE).input(Character.valueOf('T'), ModItems.CALORITE_TANK).pattern("# #").pattern("TNT").pattern("BEB").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE))
				.offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_PANTS).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_PANTS).pattern("#N#").pattern("# #").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_BOOTS).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).m_hadhiznl(Character.valueOf('B'), ModTags.CALORITE_BLOCKS).input(Character.valueOf('N'), ModItems.NETHERITE_SPACE_BOOTS)
				.pattern(" N ").pattern("# #").pattern("B B").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		// Hammer
		ShapedRecipeJsonFactory.create(ModItems.HAMMER).input(Character.valueOf('#'), Items.IRON_INGOT).input(Character.valueOf('|'), Items.STICK).pattern(" # ").pattern(" |#").pattern("|  ").group(null)
				.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT)).offerTo(exporter);

		// Iron Stick
		ShapedRecipeJsonFactory.create(ModItems.IRON_STICK, 4).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).pattern("#").pattern("#").group("iron_sticks").criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Oxygen Gear
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_GEAR).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).pattern(" | ").pattern("#|#").pattern("#|#").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Wheel
		ShapedRecipeJsonFactory.create(ModItems.WHEEL).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('B'), Items.BLACK_DYE).pattern(" B ").pattern("B#B").pattern(" B ").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engine Frame
		ShapedRecipeJsonFactory.create(ModItems.ENGINE_FRAME).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).pattern("|||").pattern("|#|").pattern("|||").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engine Fan
		ShapedRecipeJsonFactory.create(ModItems.ENGINE_FAN).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).pattern(" | ").pattern("|#|").pattern(" | ").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Nose Cone
		ShapedRecipeJsonFactory.create(ModItems.ROCKET_NOSE_CONE).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('|'), Items.LIGHTNING_ROD).pattern(" | ").pattern(" # ").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Engines
		ShapedRecipeJsonFactory.create(ModItems.STEEL_ENGINE).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_ENGINE).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_ENGINE).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###")
				.pattern(" E ").pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.CALORITE_ENGINE).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).input(Character.valueOf('E'), ModItems.ENGINE_FRAME).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("###")
				.pattern(" E ").pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		// Tanks
		ShapedRecipeJsonFactory.create(ModItems.STEEL_TANK).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_TANK).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_TANK).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.CALORITE_TANK).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_CALORITE).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).input(Character.valueOf('U'), Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.COMPRESSED_CALORITE)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.WRENCH).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).pattern("# #").pattern(" # ").pattern(" # ").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Fin
		ShapedRecipeJsonFactory.create(ModItems.ROCKET_FIN, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).pattern(" # ").pattern("###").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL))
				.offerTo(exporter);

	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		offerPolishedStoneRecipe(exporter, output, input);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		createCondensingRecipe(output, Ingredient.ofTag(input)).criterion("item", method_10420(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonFactory.create(output, amount).input(Character.valueOf('S'), input).pattern("SS").pattern("SS").criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonFactory.create(output, amount).m_hadhiznl(Character.valueOf('S'), input).pattern("SS").pattern("SS").criterion("has_tag", method_10420(input)).offerTo(exporter);
	}

	public static void offerPlatingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonFactory.create(output, 8).input(Character.valueOf('#'), IRON_PLATING).m_hadhiznl(Character.valueOf('|'), input).pattern("###").pattern("#|#").pattern("###").group("desh_plating").criterion("has_tag", method_10420(input))
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
		offerBlastingRecipe(exporter, List.of(input), output);
	}

	public void offerBlastingRecipe(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, ItemConvertible output) {
		offerBlasting(exporter, inputs, output, 1.0f, 100, null);
		offerSmelting(exporter, inputs, output, 1.0f, 200, null);
	}

	public void offerCrackedBricksSmeltingRecipe(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, ItemConvertible output) {
		offerSmelting(exporter, inputs, output, 0.2f, 200, null);
	}

	public void offerPillarRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonFactory.create(output, 2).input('#', input).pattern("#").pattern("#").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
		offerStonecuttingRecipe(exporter, output, input);
	}

	public void offerFlagRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonFactory.create(output).input(Character.valueOf('#'), input).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_RODS).pattern("|##").pattern("|##").pattern("|  ").group("flag").criterion(hasItem(input), conditionsFromItem(input))
				.offerTo(exporter);
	}

	public void offerButtonRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapelessRecipeJsonFactory.create(output).input(input).group("button").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
	}
}