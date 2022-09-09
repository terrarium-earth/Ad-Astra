package com.github.alexnijjar.ad_astra.datagen;

import java.util.List;
import java.util.function.Consumer;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModTags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;

class ModRecipeProvider extends FabricRecipeProvider {
	ModRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

		// Glacian Wood
		offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_GLACIAN_LOG, ModBlocks.GLACIAN_LOG);
		offerPlanksRecipe(exporter, ModBlocks.GLACIAN_PLANKS, ModTags.GLACIAN_LOGS);
		createStairsRecipe(ModBlocks.GLACIAN_STAIRS, Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS)).criterion(hasItem(ModBlocks.GLACIAN_PLANKS), conditionsFromItem(ModBlocks.GLACIAN_PLANKS)).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.GLACIAN_SLAB, ModBlocks.GLACIAN_PLANKS);
		createDoorRecipe(ModBlocks.GLACIAN_DOOR, Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS)).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.GLACIAN_TRAPDOOR, Ingredient.ofItems(ModItems.GLACIAN_PLANKS)).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.GLACIAN_FENCE, Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS)).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.GLACIAN_FENCE_GATE, Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS)).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		offerButtonRecipe(exporter, ModBlocks.GLACIAN_BUTTON, ModBlocks.GLACIAN_PLANKS);
		createPressurePlateRecipe(exporter, ModBlocks.GLACIAN_PRESSURE_PLATE, ModBlocks.GLACIAN_PLANKS);
		createSignRecipe(ModBlocks.GLACIAN_SIGN, Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS)).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);

		// Mushrooms
		offerPlanksRecipe(exporter, ModBlocks.AERONOS_PLANKS, ModTags.AERONOS_CAPS);
		createStairsRecipe(ModBlocks.AERONOS_STAIRS, Ingredient.ofItems(ModBlocks.AERONOS_PLANKS)).criterion(hasItem(ModBlocks.AERONOS_PLANKS), conditionsFromItem(ModBlocks.AERONOS_PLANKS)).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.AERONOS_SLAB, ModBlocks.AERONOS_PLANKS);
		createDoorRecipe(ModBlocks.AERONOS_DOOR, Ingredient.ofItems(ModBlocks.AERONOS_PLANKS)).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.AERONOS_TRAPDOOR, Ingredient.ofItems(ModItems.AERONOS_PLANKS)).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.AERONOS_FENCE, Ingredient.ofItems(ModBlocks.AERONOS_PLANKS)).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.AERONOS_FENCE_GATE, Ingredient.ofItems(ModBlocks.AERONOS_PLANKS)).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.AERONOS_CHEST).input('#', ModBlocks.AERONOS_PLANKS).pattern("###").pattern("# #").pattern("###")
				.criterion("has_lots_of_items", new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.atLeast(10), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new ItemPredicate[0])).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.AERONOS_LADDER, 6).input('#', ModItems.AERONOS_PLANKS).pattern("# #").pattern("###").pattern("# #").criterion("has_stick", conditionsFromItem(ModItems.AERONOS_PLANKS)).offerTo(exporter);

		offerPlanksRecipe(exporter, ModBlocks.STROPHAR_PLANKS, ModTags.STROPHAR_CAPS);
		createStairsRecipe(ModBlocks.STROPHAR_STAIRS, Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS)).criterion(hasItem(ModBlocks.STROPHAR_PLANKS), conditionsFromItem(ModBlocks.STROPHAR_PLANKS)).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.STROPHAR_SLAB, ModBlocks.STROPHAR_PLANKS);
		createDoorRecipe(ModBlocks.STROPHAR_DOOR, Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS)).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.STROPHAR_TRAPDOOR, Ingredient.ofItems(ModItems.STROPHAR_PLANKS)).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.STROPHAR_FENCE, Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS)).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.STROPHAR_FENCE_GATE, Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS)).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.STROPHAR_CHEST).input('#', ModBlocks.STROPHAR_PLANKS).pattern("###").pattern("# #").pattern("###")
				.criterion("has_lots_of_items", new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.atLeast(10), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new ItemPredicate[0])).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.STROPHAR_LADDER, 6).input('#', ModItems.STROPHAR_PLANKS).pattern("# #").pattern("###").pattern("# #").criterion("has_stick", conditionsFromItem(ModItems.STROPHAR_PLANKS)).offerTo(exporter);

		// Blasting
		offerBlasting(exporter, List.of(Items.IRON_INGOT), ModItems.STEEL_INGOT, 1.0f, 100, null);
		// Blasting + Smelting
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_DESH_ORE, ModBlocks.MOON_DESH_ORE, ModItems.RAW_DESH), ModItems.DESH_INGOT);
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_OSTRUM_ORE, ModBlocks.MARS_OSTRUM_ORE, ModItems.RAW_OSTRUM), ModItems.OSTRUM_INGOT);
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_CALORITE_ORE, ModBlocks.VENUS_CALORITE_ORE, ModItems.RAW_CALORITE), ModItems.CALORITE_INGOT);
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_ICE_SHARD_ORE, ModBlocks.MOON_ICE_SHARD_ORE, ModBlocks.MARS_ICE_SHARD_ORE, ModBlocks.GLACIO_ICE_SHARD_ORE), ModItems.ICE_SHARD);

		offerBlastingRecipe(exporter, ModBlocks.MOON_CHEESE_ORE, ModItems.CHEESE);
		offerBlastingRecipe(exporter, List.of(ModBlocks.VENUS_COAL_ORE, ModBlocks.GLACIO_COAL_ORE), Items.COAL);
		offerBlastingRecipe(exporter, ModBlocks.GLACIO_COPPER_ORE, Items.COPPER_INGOT);
		offerBlastingRecipe(exporter, List.of(ModBlocks.MOON_IRON_ORE, ModBlocks.MARS_IRON_ORE, ModBlocks.MERCURY_IRON_ORE, ModBlocks.GLACIO_IRON_ORE), Items.IRON_INGOT);
		offerBlastingRecipe(exporter, ModBlocks.VENUS_GOLD_ORE, Items.GOLD_INGOT);
		offerBlastingRecipe(exporter, ModBlocks.GLACIO_LAPIS_ORE, Items.LAPIS_LAZULI);
		offerBlastingRecipe(exporter, List.of(ModBlocks.MARS_DIAMOND_ORE, ModBlocks.VENUS_DIAMOND_ORE), Items.DIAMOND);

		// Stone
		offerSmelting(exporter, List.of(ModBlocks.MOON_COBBLESTONE), ModBlocks.MOON_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.MARS_COBBLESTONE), ModBlocks.MARS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.VENUS_COBBLESTONE), ModBlocks.VENUS_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.MERCURY_COBBLESTONE), ModBlocks.MERCURY_STONE, 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.GLACIO_COBBLESTONE), ModBlocks.GLACIO_STONE, 0.2f, 200, null);

		// Slabs
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_STONE_BRICK_SLAB, ModBlocks.MOON_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_SLAB, ModBlocks.CHISELED_MOON_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MOON_STONE_SLAB, ModBlocks.POLISHED_MOON_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_STONE_BRICK_SLAB, ModBlocks.MARS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_SLAB, ModBlocks.CHISELED_MARS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MARS_STONE_SLAB, ModBlocks.POLISHED_MARS_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_SLAB, ModBlocks.MERCURY_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_SLAB, ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE_SLAB, ModBlocks.POLISHED_MERCURY_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICK_SLAB, ModBlocks.VENUS_SANDSTONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_SLAB, ModBlocks.VENUS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_SLAB, ModBlocks.CHISELED_VENUS_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE_SLAB, ModBlocks.POLISHED_VENUS_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_SLAB, ModBlocks.GLACIO_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_SLAB, ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE_SLAB, ModBlocks.POLISHED_GLACIO_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.IRON_PLATING_SLAB, ModBlocks.IRON_PLATING);
		offerCustomSlabRecipe(exporter, ModBlocks.STEEL_PLATING_SLAB, ModBlocks.STEEL_PLATING);
		offerCustomSlabRecipe(exporter, ModBlocks.DESH_PLATING_SLAB, ModBlocks.DESH_PLATING);
		offerCustomSlabRecipe(exporter, ModBlocks.OSTRUM_PLATING_SLAB, ModBlocks.OSTRUM_PLATING);
		offerCustomSlabRecipe(exporter, ModBlocks.CALORITE_PLATING_SLAB, ModBlocks.CALORITE_PLATING);
		offerCustomSlabRecipe(exporter, ModBlocks.PERMAFROST_BRICK_SLAB, ModBlocks.PERMAFROST_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_PERMAFROST_SLAB, ModBlocks.POLISHED_PERMAFROST);
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB, ModBlocks.CHISELED_PERMAFROST_BRICKS);
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_COBBLESTONE_SLAB, ModBlocks.MOON_COBBLESTONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_COBBLESTONE_SLAB, ModBlocks.MARS_COBBLESTONE);
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_COBBLESTONE_SLAB, ModBlocks.VENUS_COBBLESTONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_COBBLESTONE_SLAB, ModBlocks.MERCURY_COBBLESTONE);
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_COBBLESTONE_SLAB, ModBlocks.GLACIO_COBBLESTONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_STONE_SLAB, ModBlocks.MOON_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_STONE_SLAB, ModBlocks.MARS_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_STONE_SLAB, ModBlocks.VENUS_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_STONE_SLAB, ModBlocks.MERCURY_STONE);
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_STONE_SLAB, ModBlocks.GLACIO_STONE);

		// Walls
		offerWallRecipe(exporter, ModBlocks.MOON_STONE_BRICK_WALL, ModBlocks.MOON_STONE_BRICKS);
		offerWallRecipe(exporter, ModBlocks.MARS_STONE_BRICK_WALL, ModBlocks.MARS_STONE_BRICKS);
		offerWallRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_WALL, ModBlocks.VENUS_STONE_BRICKS);
		offerWallRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_WALL, ModBlocks.MERCURY_STONE_BRICKS);
		offerWallRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_WALL, ModBlocks.GLACIO_STONE_BRICKS);
		offerWallRecipe(exporter, ModBlocks.PERMAFROST_BRICK_WALL, ModBlocks.PERMAFROST_BRICKS);

		// Stairs
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_STONE_BRICK_STAIRS, ModBlocks.MOON_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_STAIRS, ModBlocks.CHISELED_MOON_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MOON_STONE_STAIRS, ModBlocks.POLISHED_MOON_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_STONE_BRICK_STAIRS, ModBlocks.MARS_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_STAIRS, ModBlocks.CHISELED_MARS_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MARS_STONE_STAIRS, ModBlocks.POLISHED_MARS_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS, ModBlocks.VENUS_SANDSTONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_STAIRS, ModBlocks.VENUS_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_STAIRS, ModBlocks.CHISELED_VENUS_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE_STAIRS, ModBlocks.POLISHED_VENUS_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_STAIRS, ModBlocks.MERCURY_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_STAIRS, ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE_STAIRS, ModBlocks.POLISHED_MERCURY_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_STAIRS, ModBlocks.GLACIO_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_STAIRS, ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.IRON_PLATING_STAIRS, ModBlocks.IRON_PLATING);
		offerCustomStairsRecipe(exporter, ModBlocks.STEEL_PLATING_STAIRS, ModBlocks.STEEL_PLATING);
		offerCustomStairsRecipe(exporter, ModBlocks.DESH_PLATING_STAIRS, ModBlocks.DESH_PLATING);
		offerCustomStairsRecipe(exporter, ModBlocks.OSTRUM_PLATING_STAIRS, ModBlocks.OSTRUM_PLATING);
		offerCustomStairsRecipe(exporter, ModBlocks.CALORITE_PLATING_STAIRS, ModBlocks.CALORITE_PLATING);
		offerCustomStairsRecipe(exporter, ModBlocks.PERMAFROST_BRICK_STAIRS, ModBlocks.PERMAFROST_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_PERMAFROST_STAIRS, ModBlocks.POLISHED_PERMAFROST);
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICK_STAIRS, ModBlocks.CHISELED_PERMAFROST_BRICKS);
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_COBBLESTONE_STAIRS, ModBlocks.MOON_COBBLESTONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_COBBLESTONE_STAIRS, ModBlocks.MARS_COBBLESTONE);
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_COBBLESTONE_STAIRS, ModBlocks.VENUS_COBBLESTONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_COBBLESTONE_STAIRS, ModBlocks.MERCURY_COBBLESTONE);
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_COBBLESTONE_STAIRS, ModBlocks.GLACIO_COBBLESTONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_STONE_STAIRS, ModBlocks.MOON_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_STONE_STAIRS, ModBlocks.MARS_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_STONE_STAIRS, ModBlocks.VENUS_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_STONE_STAIRS, ModBlocks.MERCURY_STONE);
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_STONE_STAIRS, ModBlocks.GLACIO_STONE);

		// Buttons
		offerButtonRecipe(exporter, ModBlocks.IRON_PLATING_BUTTON, ModBlocks.IRON_PLATING);
		offerButtonRecipe(exporter, ModBlocks.STEEL_PLATING_BUTTON, ModBlocks.STEEL_PLATING);
		offerButtonRecipe(exporter, ModBlocks.DESH_PLATING_BUTTON, ModBlocks.DESH_PLATING);
		offerButtonRecipe(exporter, ModBlocks.OSTRUM_PLATING_BUTTON, ModBlocks.OSTRUM_PLATING);
		offerButtonRecipe(exporter, ModBlocks.CALORITE_PLATING_BUTTON, ModBlocks.CALORITE_PLATING);

		// Pressure Plates
		createPressurePlateRecipe(exporter, ModBlocks.IRON_PLATING_PRESSURE_PLATE, ModBlocks.IRON_PLATING);
		createPressurePlateRecipe(exporter, ModBlocks.STEEL_PLATING_PRESSURE_PLATE, ModBlocks.STEEL_PLATING);
		createPressurePlateRecipe(exporter, ModBlocks.DESH_PLATING_PRESSURE_PLATE, ModBlocks.DESH_PLATING);
		createPressurePlateRecipe(exporter, ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE, ModBlocks.OSTRUM_PLATING);
		createPressurePlateRecipe(exporter, ModBlocks.CALORITE_PLATING_PRESSURE_PLATE, ModBlocks.CALORITE_PLATING);

		// Stonecutting
		// --->
		// Bricks
		offerStonecuttingRecipe(exporter, ModBlocks.MOON_STONE_BRICKS, ModBlocks.POLISHED_MOON_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.MARS_STONE_BRICKS, ModBlocks.POLISHED_MARS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICKS, ModBlocks.VENUS_SANDSTONE);
		offerStonecuttingRecipe(exporter, ModBlocks.VENUS_STONE_BRICKS, ModBlocks.POLISHED_VENUS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.MERCURY_STONE_BRICKS, ModBlocks.POLISHED_MERCURY_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.GLACIO_STONE_BRICKS, ModBlocks.POLISHED_GLACIO_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.PERMAFROST_BRICKS, ModBlocks.PERMAFROST);

		// Chiseled
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS, ModBlocks.MOON_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS, ModBlocks.MARS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS, ModBlocks.VENUS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS, ModBlocks.MERCURY_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS, ModBlocks.GLACIO_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS, ModBlocks.PERMAFROST);

		// Polished
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MOON_STONE, ModBlocks.MOON_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MARS_STONE, ModBlocks.MARS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE, ModBlocks.VENUS_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE, ModBlocks.MERCURY_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE, ModBlocks.GLACIO_STONE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_CONGLOMERATE, ModBlocks.CONGLOMERATE);
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_PERMAFROST, ModBlocks.PERMAFROST);
		// <---

		// Pillars
		offerPillarRecipe(exporter, ModBlocks.MOON_PILLAR, ModBlocks.MOON_STONE_BRICKS);
		offerPillarRecipe(exporter, ModBlocks.MARS_PILLAR, ModBlocks.MARS_STONE_BRICKS);
		offerPillarRecipe(exporter, ModBlocks.VENUS_PILLAR, ModBlocks.VENUS_STONE_BRICKS);
		offerPillarRecipe(exporter, ModBlocks.MERCURY_PILLAR, ModBlocks.MERCURY_STONE_BRICKS);
		offerPillarRecipe(exporter, ModBlocks.GLACIO_PILLAR, ModBlocks.GLACIO_STONE_BRICKS);
		offerPillarRecipe(exporter, ModBlocks.IRON_PILLAR, ModBlocks.IRON_PLATING);
		offerPillarRecipe(exporter, ModBlocks.STEEL_PILLAR, ModBlocks.STEEL_PLATING);
		offerPillarRecipe(exporter, ModBlocks.DESH_PILLAR, ModBlocks.DESH_PLATING);
		offerPillarRecipe(exporter, ModBlocks.OSTRUM_PILLAR, ModBlocks.OSTRUM_PLATING);
		offerPillarRecipe(exporter, ModBlocks.CALORITE_PILLAR, ModBlocks.CALORITE_PLATING);
		offerPillarRecipe(exporter, ModBlocks.PERMAFROST_PILLAR, ModBlocks.PERMAFROST_BRICKS);

		// Compacting
		offerReversibleCompactingRecipes(exporter, ModItems.STEEL_INGOT, ModBlocks.STEEL_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.CHEESE, ModBlocks.CHEESE_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.DESH_INGOT, ModBlocks.DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.OSTRUM_INGOT, ModBlocks.OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.CALORITE_INGOT, ModBlocks.CALORITE_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_DESH, ModBlocks.RAW_DESH_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_OSTRUM, ModBlocks.RAW_OSTRUM_BLOCK);
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_CALORITE, ModBlocks.RAW_CALORITE_BLOCK);

		// Cracked
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MOON_STONE_BRICKS), ModBlocks.CRACKED_MOON_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MARS_STONE_BRICKS), ModBlocks.CRACKED_MARS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS), ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS), ModBlocks.CRACKED_VENUS_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MERCURY_STONE_BRICKS), ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.GLACIO_STONE_BRICKS), ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.PERMAFROST_BRICKS), ModBlocks.CRACKED_PERMAFROST_BRICKS);

		// Polished
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MOON_STONE, ModBlocks.MOON_STONE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MARS_STONE, ModBlocks.MARS_STONE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE, ModBlocks.VENUS_STONE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE, ModBlocks.MERCURY_STONE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE, ModBlocks.GLACIO_STONE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_CONGLOMERATE, ModBlocks.CONGLOMERATE);
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_PERMAFROST, ModBlocks.PERMAFROST);

		// Chiseled
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS, ModBlocks.MOON_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS, ModBlocks.MARS_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS, ModBlocks.VENUS_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS, ModBlocks.MERCURY_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS, ModBlocks.GLACIO_STONE_BRICK_SLAB);
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS, ModBlocks.PERMAFROST_BRICK_SLAB);

		// Bricks
		offerQuadRecipe(exporter, ModBlocks.MOON_STONE_BRICKS, ModBlocks.MOON_STONE);
		offerQuadRecipe(exporter, ModBlocks.MARS_STONE_BRICKS, ModBlocks.MARS_STONE);
		offerQuadRecipe(exporter, ModBlocks.VENUS_STONE_BRICKS, ModBlocks.VENUS_STONE);
		offerQuadRecipe(exporter, ModBlocks.MERCURY_STONE_BRICKS, ModBlocks.MERCURY_STONE);
		offerQuadRecipe(exporter, ModBlocks.GLACIO_STONE_BRICKS, ModBlocks.GLACIO_STONE);
		offerQuadRecipe(exporter, ModBlocks.PERMAFROST_BRICKS, ModBlocks.PERMAFROST);
		offerQuadRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICKS, ModBlocks.VENUS_SANDSTONE);

		offerQuadRecipe(exporter, ModBlocks.PERMAFROST_TILES, ModBlocks.PERMAFROST_BRICKS);

		// Sandstone
		offerQuadRecipe(exporter, ModItems.VENUS_SANDSTONE, ModItems.VENUS_SAND);

		// Ice
		offerQuadRecipe(exporter, 1, Blocks.ICE, ModItems.ICE_SHARD);

		// Rocket Launch Pad
		ShapedRecipeJsonFactory.create(ModItems.ROCKET_LAUNCH_PAD, 9).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).m_hadhiznl(Character.valueOf('|'), ModTags.IRON_PLATES).pattern("#|#").pattern("|#|").pattern("#|#")
				.group("rocket_launch_pad").criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Metals

		ShapedRecipeJsonFactory.create(ModItems.IRON_PLATING, 32).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).pattern("###").pattern("###").pattern("###").group("iron_plates").criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL))
				.offerTo(exporter);
		offerPlatingRecipe(exporter, ModItems.STEEL_PLATING, ModTags.COMPRESSED_STEEL);
		offerPlatingRecipe(exporter, ModItems.DESH_PLATING, ModTags.DESH_PLATES);
		offerPlatingRecipe(exporter, ModItems.OSTRUM_PLATING, ModTags.COMPRESSED_OSTRUM);
		offerPlatingRecipe(exporter, ModItems.CALORITE_PLATING, ModTags.COMPRESSED_CALORITE);

		createDoorRecipe(ModItems.STEEL_DOOR, Ingredient.ofItems(ModItems.COMPRESSED_STEEL)).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);
		createTrapdoorRecipe(ModItems.STEEL_TRAPDOOR, Ingredient.ofItems(ModItems.COMPRESSED_STEEL)).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Doors
		ShapedRecipeJsonFactory.create(ModItems.IRON_SLIDING_DOOR).input(Character.valueOf('#'), Items.IRON_BLOCK).input(Character.valueOf('C'), ModItems.IRON_PLATE).input(Character.valueOf('P'), Items.GLASS_PANE).pattern("CCC").pattern("P#P")
				.pattern("CCC").group("sliding_door").criterion("has_item", conditionsFromItem(Items.IRON_BLOCK)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.STEEL_SLIDING_DOOR).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_BLOCKS).input(Character.valueOf('C'), ModItems.COMPRESSED_STEEL).input(Character.valueOf('P'), Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.DESH_SLIDING_DOOR).m_hadhiznl(Character.valueOf('#'), ModTags.DESH_BLOCKS).input(Character.valueOf('C'), ModItems.DESH_PLATE).input(Character.valueOf('P'), Items.GLASS_PANE).pattern("CCC").pattern("P#P")
				.pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.DESH_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_SLIDING_DOOR).m_hadhiznl(Character.valueOf('#'), ModTags.OSTRUM_BLOCKS).input(Character.valueOf('C'), ModItems.COMPRESSED_OSTRUM).input(Character.valueOf('P'), Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.OSTRUM_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.CALORITE_SLIDING_DOOR).m_hadhiznl(Character.valueOf('#'), ModTags.CALORITE_BLOCKS).input(Character.valueOf('C'), ModItems.COMPRESSED_CALORITE).input(Character.valueOf('P'), Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.CALORITE_BLOCKS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.AIRLOCK).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_BLOCKS).input(Character.valueOf('C'), ModItems.COMPRESSED_STEEL).pattern("CC#").pattern("CCC").pattern("#CC").group("sliding_door")
				.criterion("has_tag", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.REINFORCED_DOOR).m_hadhiznl(Character.valueOf('#'), ModTags.STEEL_BLOCKS).input(Character.valueOf('O'), Blocks.OBSIDIAN).input(Character.valueOf('G'), Items.GLASS_PANE).pattern("O#O").pattern("G#G")
				.pattern("O#O").group("sliding_door").criterion("has_tag", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.BLUE_IRON_PILLAR, 6).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('G'), Blocks.GLOWSTONE).input(Character.valueOf('B'), Items.BLUE_DYE).pattern("#B#").pattern("#G#")
				.pattern("#B#").group("blue_iron_pillar").criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.MARKED_IRON_PILLAR, 6).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('Y'), Items.YELLOW_DYE).input(Character.valueOf('B'), Items.BLACK_DYE).pattern("#Y#").pattern("#B#")
				.pattern("#Y#").group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Machines
		// NASA Workbench
		ShapedRecipeJsonFactory.create(ModItems.NASA_WORKBENCH).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('C'), Blocks.CRAFTING_TABLE).m_hadhiznl(Character.valueOf('B'), ModTags.STEEL_BLOCKS)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('T'), Blocks.REDSTONE_TORCH).input(Character.valueOf('L'), Blocks.LEVER).pattern("TLT").pattern("#C#").pattern("RBR").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Solar Panel
		ShapedRecipeJsonFactory.create(ModItems.SOLAR_PANEL).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('S'), ModItems.COMPRESSED_STEEL).input(Character.valueOf('G'), Blocks.BLUE_STAINED_GLASS).pattern("GGG")
				.pattern("S#S").pattern("###").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Coal Generator
		ShapedRecipeJsonFactory.create(ModItems.COAL_GENERATOR).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('I'), Items.IRON_INGOT).input(Character.valueOf('F'), Blocks.FURNACE).pattern("#I#").pattern("#F#")
				.pattern("#I#").group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Compressor
		ShapedRecipeJsonFactory.create(ModItems.COMPRESSOR).m_hadhiznl(Character.valueOf('#'), ModTags.IRON_PLATES).input(Character.valueOf('P'), Blocks.PISTON).pattern("#P#").pattern("# #").pattern("#P#").group(null)
				.criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Fuel Refinery
		ShapedRecipeJsonFactory.create(ModItems.FUEL_REFINERY).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('F'), Blocks.FURNACE).input(Character.valueOf('B'), Items.BUCKET).pattern("###").pattern("BFB")
				.pattern("###").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		// Oxygen Loader
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_LOADER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), Blocks.LIGHTNING_ROD)
				.input(Character.valueOf('R'), Blocks.REDSTONE_BLOCK).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("#F#").pattern("TLT").pattern("#R#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL))
				.offerTo(exporter);

		// Oxygen Distributor
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_DISTRIBUTOR).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('T'), ModItems.OXYGEN_TANK).input(Character.valueOf('L'), ModItems.OXYGEN_LOADER)
				.input(Character.valueOf('F'), ModItems.ENGINE_FAN).input(Character.valueOf('G'), ModItems.OXYGEN_GEAR).pattern("FFF").pattern("TLT").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		// Water Pump
		ShapedRecipeJsonFactory.create(ModItems.WATER_PUMP).m_hadhiznl(Character.valueOf('#'), ModTags.DESH_PLATES).input(Character.valueOf('H'), Blocks.HOPPER).input(Character.valueOf('D'), Blocks.DISPENSER).pattern(" D ").pattern(" #D").pattern(" H ")
				.group(null).criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Energizer
		ShapedRecipeJsonFactory.create(ModItems.ENERGIZER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('D'), Blocks.DIAMOND_BLOCK).input(Character.valueOf('I'), Items.DIAMOND)
				.input(Character.valueOf('B'), ModItems.OSTRUM_BLOCK).pattern("#I#").pattern("#D#").pattern("B#B").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Cryo Freezer
		ShapedRecipeJsonFactory.create(ModItems.CRYO_FREEZER).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('T'), ModItems.OSTRUM_TANK)
				.input(Character.valueOf('B'), ModItems.OSTRUM_BLOCK).pattern("#O#").pattern("OTO").pattern("BOB").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Oxygen Sensor
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_SENSOR).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('O'), ModItems.OSTRUM_INGOT).input(Character.valueOf('V'), Items.OBSERVER)
				.input(Character.valueOf('L'), Items.REDSTONE_LAMP).input(Character.valueOf('F'), ModItems.ENGINE_FAN).pattern("#F#").pattern("OVO").pattern("#L#").group(null).criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Cables
		ShapedRecipeJsonFactory.create(ModItems.STEEL_CABLE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_STEEL).input(Character.valueOf('C'), Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_STEEL)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_CABLE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('C'), Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_FLUID_PIPE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_DESH).input(Character.valueOf('C'), Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_DESH)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_FLUID_PIPE, 4).m_hadhiznl(Character.valueOf('#'), ModTags.COMPRESSED_OSTRUM).input(Character.valueOf('C'), Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.COMPRESSED_OSTRUM)).offerTo(exporter);

		// Flags
		offerFlagRecipe(exporter, ModItems.FLAG, Blocks.WHITE_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_BLUE, Blocks.BLUE_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_BROWN, Blocks.BROWN_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_CYAN, Blocks.CYAN_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_GRAY, Blocks.GRAY_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_GREEN, Blocks.GREEN_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_LIME, Blocks.LIME_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_MAGENTA, Blocks.MAGENTA_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_ORANGE, Blocks.ORANGE_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_PINK, Blocks.PINK_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_PURPLE, Blocks.PURPLE_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_RED, Blocks.RED_WOOL);
		offerFlagRecipe(exporter, ModItems.FLAG_YELLOW, Blocks.YELLOW_WOOL);

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

		// Soul Torch
		ShapedRecipeJsonFactory.create(Items.SOUL_TORCH).input(Character.valueOf('#'), ModItems.COAL_TORCH).input(Character.valueOf('S'), Items.SOUL_SOIL).pattern("S").pattern("#")
				.criterion(hasItem(ModItems.COAL_TORCH), conditionsFromItem(ModItems.COAL_TORCH)).offerTo(exporter);
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
		ShapedRecipeJsonFactory.create(output, 8).input(Character.valueOf('#'), ModItems.IRON_PLATING).m_hadhiznl(Character.valueOf('|'), input).pattern("###").pattern("#|#").pattern("###").group("desh_plating").criterion("has_tag", method_10420(input))
				.offerTo(exporter);
	}

	public static void offerCustomSlabRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		offerSlabRecipe(exporter, output, input);
		offerStonecuttingRecipe(exporter, output, input, 2);
	}

	public void offerCustomStairsRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
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