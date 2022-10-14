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
		offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_GLACIAN_LOG.get(), ModBlocks.GLACIAN_LOG.get());
		offerPlanksRecipe(exporter, ModBlocks.GLACIAN_PLANKS.get(), ModTags.GLACIAN_LOGS);
		createStairsRecipe(ModBlocks.GLACIAN_STAIRS.get(), Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS.get())).criterion(hasItem(ModBlocks.GLACIAN_PLANKS.get()), conditionsFromItem(ModBlocks.GLACIAN_PLANKS.get())).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.GLACIAN_SLAB.get(), ModBlocks.GLACIAN_PLANKS.get());
		createDoorRecipe(ModBlocks.GLACIAN_DOOR.get(), Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS.get())).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.GLACIAN_TRAPDOOR.get(), Ingredient.ofItems(ModItems.GLACIAN_PLANKS.get())).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.GLACIAN_FENCE.get(), Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS.get())).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.GLACIAN_FENCE_GATE.get(), Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS.get())).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);
		offerButtonRecipe(exporter, ModBlocks.GLACIAN_BUTTON.get(), ModBlocks.GLACIAN_PLANKS.get());
		createPressurePlateRecipe(exporter, ModBlocks.GLACIAN_PRESSURE_PLATE.get(), ModBlocks.GLACIAN_PLANKS.get());
		createSignRecipe(ModBlocks.GLACIAN_SIGN.get(), Ingredient.ofItems(ModBlocks.GLACIAN_PLANKS.get())).criterion("has_tag", method_10420(ModTags.GLACIAN_LOGS)).offerTo(exporter);

		// Mushrooms
		offerPlanksRecipe(exporter, ModBlocks.AERONOS_PLANKS.get(), ModTags.AERONOS_CAPS);
		createStairsRecipe(ModBlocks.AERONOS_STAIRS.get(), Ingredient.ofItems(ModBlocks.AERONOS_PLANKS.get())).criterion(hasItem(ModBlocks.AERONOS_PLANKS.get()), conditionsFromItem(ModBlocks.AERONOS_PLANKS.get())).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.AERONOS_SLAB.get(), ModBlocks.AERONOS_PLANKS.get());
		createDoorRecipe(ModBlocks.AERONOS_DOOR.get(), Ingredient.ofItems(ModBlocks.AERONOS_PLANKS.get())).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.AERONOS_TRAPDOOR.get(), Ingredient.ofItems(ModItems.AERONOS_PLANKS.get())).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.AERONOS_FENCE.get(), Ingredient.ofItems(ModBlocks.AERONOS_PLANKS.get())).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.AERONOS_FENCE_GATE.get(), Ingredient.ofItems(ModBlocks.AERONOS_PLANKS.get())).criterion("has_tag", method_10420(ModTags.AERONOS_CAPS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.AERONOS_CHEST.get()).input('#', ModBlocks.AERONOS_PLANKS.get()).pattern("###").pattern("# #").pattern("###")
				.criterion("has_lots_of_items", new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.atLeast(10), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new ItemPredicate[0])).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.AERONOS_LADDER.get(), 6).input('#', ModItems.AERONOS_PLANKS.get()).pattern("# #").pattern("###").pattern("# #").criterion("has_aeronos_planks", conditionsFromItem(ModItems.AERONOS_PLANKS.get())).offerTo(exporter);

		offerPlanksRecipe(exporter, ModBlocks.STROPHAR_PLANKS.get(), ModTags.STROPHAR_CAPS);
		createStairsRecipe(ModBlocks.STROPHAR_STAIRS.get(), Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS.get())).criterion(hasItem(ModBlocks.STROPHAR_PLANKS.get()), conditionsFromItem(ModBlocks.STROPHAR_PLANKS.get())).offerTo(exporter);
		offerSlabRecipe(exporter, ModBlocks.STROPHAR_SLAB.get(), ModBlocks.STROPHAR_PLANKS.get());
		createDoorRecipe(ModBlocks.STROPHAR_DOOR.get(), Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS.get())).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createTrapdoorRecipe(ModBlocks.STROPHAR_TRAPDOOR.get(), Ingredient.ofItems(ModItems.STROPHAR_PLANKS.get())).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createFenceRecipe(ModBlocks.STROPHAR_FENCE.get(), Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS.get())).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		createFenceGateRecipe(ModBlocks.STROPHAR_FENCE_GATE.get(), Ingredient.ofItems(ModBlocks.STROPHAR_PLANKS.get())).criterion("has_tag", method_10420(ModTags.STROPHAR_CAPS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.STROPHAR_CHEST.get()).input('#', ModBlocks.STROPHAR_PLANKS.get()).pattern("###").pattern("# #").pattern("###")
				.criterion("has_lots_of_items", new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.atLeast(10), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, new ItemPredicate[0])).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModBlocks.STROPHAR_LADDER.get(), 6).input('#', ModItems.STROPHAR_PLANKS.get()).pattern("# #").pattern("###").pattern("# #").criterion("has_rod", conditionsFromItem(ModItems.STROPHAR_PLANKS.get())).offerTo(exporter);

		// Blasting
		offerBlasting(exporter, List.of(Items.IRON_INGOT), ModItems.STEEL_INGOT.get(), 1.0f, 100, null);
		// Blasting + Smelting
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_DESH_ORE.get(), ModBlocks.MOON_DESH_ORE.get(), ModItems.RAW_DESH.get()), ModItems.DESH_INGOT.get());
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_OSTRUM_ORE.get(), ModBlocks.MARS_OSTRUM_ORE.get(), ModItems.RAW_OSTRUM.get()), ModItems.OSTRUM_INGOT.get());
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_CALORITE_ORE.get(), ModBlocks.VENUS_CALORITE_ORE.get(), ModItems.RAW_CALORITE.get()), ModItems.CALORITE_INGOT.get());
		offerBlastingRecipe(exporter, List.of(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get(), ModBlocks.MOON_ICE_SHARD_ORE.get(), ModBlocks.MARS_ICE_SHARD_ORE.get(), ModBlocks.GLACIO_ICE_SHARD_ORE.get()), ModItems.ICE_SHARD.get());

		offerBlastingRecipe(exporter, ModBlocks.MOON_CHEESE_ORE.get(), ModItems.CHEESE.get());
		offerBlastingRecipe(exporter, List.of(ModBlocks.VENUS_COAL_ORE.get(), ModBlocks.GLACIO_COAL_ORE.get()), Items.COAL);
		offerBlastingRecipe(exporter, ModBlocks.GLACIO_COPPER_ORE.get(), Items.COPPER_INGOT);
		offerBlastingRecipe(exporter, List.of(ModBlocks.MOON_IRON_ORE.get(), ModBlocks.MARS_IRON_ORE.get(), ModBlocks.MERCURY_IRON_ORE.get(), ModBlocks.GLACIO_IRON_ORE.get()), Items.IRON_INGOT);
		offerBlastingRecipe(exporter, ModBlocks.VENUS_GOLD_ORE.get(), Items.GOLD_INGOT);
		offerBlastingRecipe(exporter, ModBlocks.GLACIO_LAPIS_ORE.get(), Items.LAPIS_LAZULI);
		offerBlastingRecipe(exporter, List.of(ModBlocks.MARS_DIAMOND_ORE.get(), ModBlocks.VENUS_DIAMOND_ORE.get()), Items.DIAMOND);

		// Stone
		offerSmelting(exporter, List.of(ModBlocks.MOON_COBBLESTONE.get()), ModBlocks.MOON_STONE.get(), 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.MARS_COBBLESTONE.get()), ModBlocks.MARS_STONE.get(), 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.VENUS_COBBLESTONE.get()), ModBlocks.VENUS_STONE.get(), 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.MERCURY_COBBLESTONE.get()), ModBlocks.MERCURY_STONE.get(), 0.2f, 200, null);
		offerSmelting(exporter, List.of(ModBlocks.GLACIO_COBBLESTONE.get()), ModBlocks.GLACIO_STONE.get(), 0.2f, 200, null);

		// Slabs
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_STONE_BRICK_SLAB.get(), ModBlocks.MOON_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_SLAB.get(), ModBlocks.CHISELED_MOON_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MOON_STONE_SLAB.get(), ModBlocks.POLISHED_MOON_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_STONE_BRICK_SLAB.get(), ModBlocks.MARS_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_SLAB.get(), ModBlocks.CHISELED_MARS_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MARS_STONE_SLAB.get(), ModBlocks.POLISHED_MARS_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_SLAB.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_SLAB.get(), ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE_SLAB.get(), ModBlocks.POLISHED_MERCURY_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICK_SLAB.get(), ModBlocks.VENUS_SANDSTONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_SLAB.get(), ModBlocks.VENUS_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_SLAB.get(), ModBlocks.CHISELED_VENUS_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE_SLAB.get(), ModBlocks.POLISHED_VENUS_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_SLAB.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_SLAB.get(), ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE_SLAB.get(), ModBlocks.POLISHED_GLACIO_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.IRON_PLATING_SLAB.get(), ModBlocks.IRON_PLATING.get());
		offerCustomSlabRecipe(exporter, ModBlocks.STEEL_PLATING_SLAB.get(), ModBlocks.STEEL_PLATING.get());
		offerCustomSlabRecipe(exporter, ModBlocks.DESH_PLATING_SLAB.get(), ModBlocks.DESH_PLATING.get());
		offerCustomSlabRecipe(exporter, ModBlocks.OSTRUM_PLATING_SLAB.get(), ModBlocks.OSTRUM_PLATING.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CALORITE_PLATING_SLAB.get(), ModBlocks.CALORITE_PLATING.get());
		offerCustomSlabRecipe(exporter, ModBlocks.PERMAFROST_BRICK_SLAB.get(), ModBlocks.PERMAFROST_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.POLISHED_PERMAFROST_SLAB.get(), ModBlocks.POLISHED_PERMAFROST.get());
		offerCustomSlabRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB.get(), ModBlocks.CHISELED_PERMAFROST_BRICKS.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_COBBLESTONE_SLAB.get(), ModBlocks.MOON_COBBLESTONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_COBBLESTONE_SLAB.get(), ModBlocks.MARS_COBBLESTONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_COBBLESTONE_SLAB.get(), ModBlocks.VENUS_COBBLESTONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_COBBLESTONE_SLAB.get(), ModBlocks.MERCURY_COBBLESTONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_COBBLESTONE_SLAB.get(), ModBlocks.GLACIO_COBBLESTONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MOON_STONE_SLAB.get(), ModBlocks.MOON_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MARS_STONE_SLAB.get(), ModBlocks.MARS_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.VENUS_STONE_SLAB.get(), ModBlocks.VENUS_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.MERCURY_STONE_SLAB.get(), ModBlocks.MERCURY_STONE.get());
		offerCustomSlabRecipe(exporter, ModBlocks.GLACIO_STONE_SLAB.get(), ModBlocks.GLACIO_STONE.get());

		// Walls
		offerWallRecipe(exporter, ModBlocks.MOON_STONE_BRICK_WALL.get(), ModBlocks.MOON_STONE_BRICKS.get());
		offerWallRecipe(exporter, ModBlocks.MARS_STONE_BRICK_WALL.get(), ModBlocks.MARS_STONE_BRICKS.get());
		offerWallRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_WALL.get(), ModBlocks.VENUS_STONE_BRICKS.get());
		offerWallRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_WALL.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
		offerWallRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_WALL.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
		offerWallRecipe(exporter, ModBlocks.PERMAFROST_BRICK_WALL.get(), ModBlocks.PERMAFROST_BRICKS.get());

		// Stairs
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_STONE_BRICK_STAIRS.get(), ModBlocks.MOON_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_STAIRS.get(), ModBlocks.CHISELED_MOON_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MOON_STONE_STAIRS.get(), ModBlocks.POLISHED_MOON_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_STONE_BRICK_STAIRS.get(), ModBlocks.MARS_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_STAIRS.get(), ModBlocks.CHISELED_MARS_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MARS_STONE_STAIRS.get(), ModBlocks.POLISHED_MARS_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS.get(), ModBlocks.VENUS_SANDSTONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_STONE_BRICK_STAIRS.get(), ModBlocks.VENUS_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_STAIRS.get(), ModBlocks.CHISELED_VENUS_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE_STAIRS.get(), ModBlocks.POLISHED_VENUS_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_STONE_BRICK_STAIRS.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_STAIRS.get(), ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE_STAIRS.get(), ModBlocks.POLISHED_MERCURY_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_STONE_BRICK_STAIRS.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_STAIRS.get(), ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.IRON_PLATING_STAIRS.get(), ModBlocks.IRON_PLATING.get());
		offerCustomStairsRecipe(exporter, ModBlocks.STEEL_PLATING_STAIRS.get(), ModBlocks.STEEL_PLATING.get());
		offerCustomStairsRecipe(exporter, ModBlocks.DESH_PLATING_STAIRS.get(), ModBlocks.DESH_PLATING.get());
		offerCustomStairsRecipe(exporter, ModBlocks.OSTRUM_PLATING_STAIRS.get(), ModBlocks.OSTRUM_PLATING.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CALORITE_PLATING_STAIRS.get(), ModBlocks.CALORITE_PLATING.get());
		offerCustomStairsRecipe(exporter, ModBlocks.PERMAFROST_BRICK_STAIRS.get(), ModBlocks.PERMAFROST_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.POLISHED_PERMAFROST_STAIRS.get(), ModBlocks.POLISHED_PERMAFROST.get());
		offerCustomStairsRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICK_STAIRS.get(), ModBlocks.CHISELED_PERMAFROST_BRICKS.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_COBBLESTONE_STAIRS.get(), ModBlocks.MOON_COBBLESTONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_COBBLESTONE_STAIRS.get(), ModBlocks.MARS_COBBLESTONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_COBBLESTONE_STAIRS.get(), ModBlocks.VENUS_COBBLESTONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_COBBLESTONE_STAIRS.get(), ModBlocks.MERCURY_COBBLESTONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_COBBLESTONE_STAIRS.get(), ModBlocks.GLACIO_COBBLESTONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MOON_STONE_STAIRS.get(), ModBlocks.MOON_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MARS_STONE_STAIRS.get(), ModBlocks.MARS_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.VENUS_STONE_STAIRS.get(), ModBlocks.VENUS_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.MERCURY_STONE_STAIRS.get(), ModBlocks.MERCURY_STONE.get());
		offerCustomStairsRecipe(exporter, ModBlocks.GLACIO_STONE_STAIRS.get(), ModBlocks.GLACIO_STONE.get());

		// Buttons
		offerButtonRecipe(exporter, ModBlocks.IRON_PLATING_BUTTON.get(), ModBlocks.IRON_PLATING.get());
		offerButtonRecipe(exporter, ModBlocks.STEEL_PLATING_BUTTON.get(), ModBlocks.STEEL_PLATING.get());
		offerButtonRecipe(exporter, ModBlocks.DESH_PLATING_BUTTON.get(), ModBlocks.DESH_PLATING.get());
		offerButtonRecipe(exporter, ModBlocks.OSTRUM_PLATING_BUTTON.get(), ModBlocks.OSTRUM_PLATING.get());
		offerButtonRecipe(exporter, ModBlocks.CALORITE_PLATING_BUTTON.get(), ModBlocks.CALORITE_PLATING.get());

		// Pressure Plates
		createPressurePlateRecipe(exporter, ModBlocks.IRON_PLATING_PRESSURE_PLATE.get(), ModBlocks.IRON_PLATING.get());
		createPressurePlateRecipe(exporter, ModBlocks.STEEL_PLATING_PRESSURE_PLATE.get(), ModBlocks.STEEL_PLATING.get());
		createPressurePlateRecipe(exporter, ModBlocks.DESH_PLATING_PRESSURE_PLATE.get(), ModBlocks.DESH_PLATING.get());
		createPressurePlateRecipe(exporter, ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE.get(), ModBlocks.OSTRUM_PLATING.get());
		createPressurePlateRecipe(exporter, ModBlocks.CALORITE_PLATING_PRESSURE_PLATE.get(), ModBlocks.CALORITE_PLATING.get());

		// Stonecutting
		// --->
		// Bricks
		offerStonecuttingRecipe(exporter, ModBlocks.MOON_STONE_BRICKS.get(), ModBlocks.POLISHED_MOON_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.MARS_STONE_BRICKS.get(), ModBlocks.POLISHED_MARS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICKS.get(), ModBlocks.VENUS_SANDSTONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.VENUS_STONE_BRICKS.get(), ModBlocks.POLISHED_VENUS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.MERCURY_STONE_BRICKS.get(), ModBlocks.POLISHED_MERCURY_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.GLACIO_STONE_BRICKS.get(), ModBlocks.POLISHED_GLACIO_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST.get());

		// Chiseled
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS.get(), ModBlocks.MOON_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS.get(), ModBlocks.MARS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS.get(), ModBlocks.VENUS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get(), ModBlocks.MERCURY_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get(), ModBlocks.GLACIO_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST.get());

		// Polished
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MOON_STONE.get(), ModBlocks.MOON_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MARS_STONE.get(), ModBlocks.MARS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE.get(), ModBlocks.VENUS_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE.get(), ModBlocks.MERCURY_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE.get(), ModBlocks.GLACIO_STONE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_CONGLOMERATE.get(), ModBlocks.CONGLOMERATE.get());
		offerStonecuttingRecipe(exporter, ModBlocks.POLISHED_PERMAFROST.get(), ModBlocks.PERMAFROST.get());
		// <---

		// Pillars
		offerPillarRecipe(exporter, ModBlocks.MOON_PILLAR.get(), ModBlocks.MOON_STONE_BRICKS.get());
		offerPillarRecipe(exporter, ModBlocks.MARS_PILLAR.get(), ModBlocks.MARS_STONE_BRICKS.get());
		offerPillarRecipe(exporter, ModBlocks.VENUS_PILLAR.get(), ModBlocks.VENUS_STONE_BRICKS.get());
		offerPillarRecipe(exporter, ModBlocks.MERCURY_PILLAR.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
		offerPillarRecipe(exporter, ModBlocks.GLACIO_PILLAR.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
		offerPillarRecipe(exporter, ModBlocks.IRON_PILLAR.get(), ModBlocks.IRON_PLATING.get());
		offerPillarRecipe(exporter, ModBlocks.STEEL_PILLAR.get(), ModBlocks.STEEL_PLATING.get());
		offerPillarRecipe(exporter, ModBlocks.DESH_PILLAR.get(), ModBlocks.DESH_PLATING.get());
		offerPillarRecipe(exporter, ModBlocks.OSTRUM_PILLAR.get(), ModBlocks.OSTRUM_PLATING.get());
		offerPillarRecipe(exporter, ModBlocks.CALORITE_PILLAR.get(), ModBlocks.CALORITE_PLATING.get());
		offerPillarRecipe(exporter, ModBlocks.PERMAFROST_PILLAR.get(), ModBlocks.PERMAFROST_BRICKS.get());

		// Compacting
		offerReversibleCompactingRecipes(exporter, ModItems.STEEL_INGOT.get(), ModBlocks.STEEL_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.CHEESE.get(), ModBlocks.CHEESE_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.DESH_INGOT.get(), ModBlocks.DESH_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.OSTRUM_INGOT.get(), ModBlocks.OSTRUM_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.CALORITE_INGOT.get(), ModBlocks.CALORITE_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_DESH.get(), ModBlocks.RAW_DESH_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_OSTRUM.get(), ModBlocks.RAW_OSTRUM_BLOCK.get());
		offerReversibleCompactingRecipes(exporter, ModItems.RAW_CALORITE.get(), ModBlocks.RAW_CALORITE_BLOCK.get());

		// Cracked
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MOON_STONE_BRICKS.get()), ModBlocks.CRACKED_MOON_STONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MARS_STONE_BRICKS.get()), ModBlocks.CRACKED_MARS_STONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS.get()), ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS.get()), ModBlocks.CRACKED_VENUS_STONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MERCURY_STONE_BRICKS.get()), ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.GLACIO_STONE_BRICKS.get()), ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get());
		offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.PERMAFROST_BRICKS.get()), ModBlocks.CRACKED_PERMAFROST_BRICKS.get());

		// Polished
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MOON_STONE.get(), ModBlocks.MOON_STONE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MARS_STONE.get(), ModBlocks.MARS_STONE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_VENUS_STONE.get(), ModBlocks.VENUS_STONE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_MERCURY_STONE.get(), ModBlocks.MERCURY_STONE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_GLACIO_STONE.get(), ModBlocks.GLACIO_STONE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_CONGLOMERATE.get(), ModBlocks.CONGLOMERATE.get());
		offerPolishedStoneRecipe(exporter, ModBlocks.POLISHED_PERMAFROST.get(), ModBlocks.PERMAFROST.get());

		// Chiseled
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS.get(), ModBlocks.MOON_STONE_BRICK_SLAB.get());
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS.get(), ModBlocks.MARS_STONE_BRICK_SLAB.get());
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS.get(), ModBlocks.VENUS_STONE_BRICK_SLAB.get());
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get(), ModBlocks.MERCURY_STONE_BRICK_SLAB.get());
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get(), ModBlocks.GLACIO_STONE_BRICK_SLAB.get());
		offerChiseledBlockRecipe(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST_BRICK_SLAB.get());

		// Bricks
		offerQuadRecipe(exporter, ModBlocks.MOON_STONE_BRICKS.get(), ModBlocks.MOON_STONE.get());
		offerQuadRecipe(exporter, ModBlocks.MARS_STONE_BRICKS.get(), ModBlocks.MARS_STONE.get());
		offerQuadRecipe(exporter, ModBlocks.VENUS_STONE_BRICKS.get(), ModBlocks.VENUS_STONE.get());
		offerQuadRecipe(exporter, ModBlocks.MERCURY_STONE_BRICKS.get(), ModBlocks.MERCURY_STONE.get());
		offerQuadRecipe(exporter, ModBlocks.GLACIO_STONE_BRICKS.get(), ModBlocks.GLACIO_STONE.get());
		offerQuadRecipe(exporter, ModBlocks.PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST.get());
		offerQuadRecipe(exporter, ModBlocks.VENUS_SANDSTONE_BRICKS.get(), ModBlocks.VENUS_SANDSTONE.get());

		offerQuadRecipe(exporter, ModBlocks.PERMAFROST_TILES.get(), ModBlocks.PERMAFROST_BRICKS.get());

		// Sandstone
		offerQuadRecipe(exporter, ModItems.VENUS_SANDSTONE.get(), ModItems.VENUS_SAND.get());

		// Ice
		offerQuadRecipe(exporter, 1, Blocks.ICE, ModItems.ICE_SHARD.get());

		// Launch Pad
		ShapedRecipeJsonFactory.create(ModItems.LAUNCH_PAD.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).m_hadhiznl('|', ModTags.IRON_PLATES).pattern("#|#").pattern("|#|").pattern("#|#").group("launch_pad")
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Metals

		ShapedRecipeJsonFactory.create(ModItems.IRON_PLATING.get(), 32).m_hadhiznl('#', ModTags.IRON_PLATES).pattern("###").pattern("###").pattern("###").group("iron_plates").criterion("has_tag", method_10420(ModTags.STEEL_PLATES))
				.offerTo(exporter);
		offerPlatingRecipe(exporter, ModItems.STEEL_PLATING.get(), ModTags.STEEL_PLATES);
		offerPlatingRecipe(exporter, ModItems.DESH_PLATING.get(), ModTags.DESH_PLATES);
		offerPlatingRecipe(exporter, ModItems.OSTRUM_PLATING.get(), ModTags.OSTRUM_PLATES);
		offerPlatingRecipe(exporter, ModItems.CALORITE_PLATING.get(), ModTags.CALORITE_PLATES);

		createDoorRecipe(ModItems.STEEL_DOOR.get(), Ingredient.ofTag(ModTags.STEEL_PLATES)).criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);
		createTrapdoorRecipe(ModItems.STEEL_TRAPDOOR.get(), Ingredient.ofTag(ModTags.STEEL_PLATES)).criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Doors
		ShapedRecipeJsonFactory.create(ModItems.IRON_SLIDING_DOOR.get()).input('#', Items.IRON_BLOCK).m_hadhiznl('C', ModTags.IRON_PLATES).input('P', Items.GLASS_PANE).pattern("CCC").pattern("P#P")
				.pattern("CCC").group("sliding_door").criterion("has_item", conditionsFromItem(Items.IRON_BLOCK)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.STEEL_SLIDING_DOOR.get()).m_hadhiznl('#', ModTags.STEEL_BLOCKS).m_hadhiznl('C', ModTags.STEEL_PLATES).input('P', Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.DESH_SLIDING_DOOR.get()).m_hadhiznl('#', ModTags.DESH_BLOCKS).m_hadhiznl('C', ModTags.DESH_PLATES).input('P', Items.GLASS_PANE).pattern("CCC").pattern("P#P")
				.pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.DESH_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_SLIDING_DOOR.get()).m_hadhiznl('#', ModTags.OSTRUM_BLOCKS).m_hadhiznl('C', ModTags.OSTRUM_PLATES).input('P', Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.OSTRUM_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.CALORITE_SLIDING_DOOR.get()).m_hadhiznl('#', ModTags.CALORITE_BLOCKS).m_hadhiznl('C', ModTags.CALORITE_PLATES).input('P', Items.GLASS_PANE).pattern("CCC")
				.pattern("P#P").pattern("CCC").group("sliding_door").criterion("has_item", method_10420(ModTags.CALORITE_BLOCKS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.AIRLOCK.get()).m_hadhiznl('#', ModTags.STEEL_BLOCKS).m_hadhiznl('C', ModTags.STEEL_PLATES).pattern("CC#").pattern("CCC").pattern("#CC").group("sliding_door")
				.criterion("has_tag", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.REINFORCED_DOOR.get()).m_hadhiznl('#', ModTags.STEEL_BLOCKS).input('O', Blocks.OBSIDIAN).input('G', Items.GLASS_PANE).pattern("O#O").pattern("G#G")
				.pattern("O#O").group("sliding_door").criterion("has_tag", method_10420(ModTags.STEEL_BLOCKS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.GLOWING_IRON_PILLAR.get(), 6).m_hadhiznl('#', ModTags.IRON_PLATES).input('G', Blocks.GLOWSTONE).input('B', Items.LIGHT_BLUE_DYE).pattern("#B#")
				.pattern("#G#").pattern("#B#").group("glowing_iron_pillar").criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.GLOWING_STEEL_PILLAR.get(), 6).m_hadhiznl('#', ModTags.STEEL_PLATES).input('G', Blocks.GLOWSTONE).input('B', Items.PURPLE_DYE).pattern("#B#").pattern("#G#")
				.pattern("#B#").group("glowing_steel_pillar").criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.GLOWING_DESH_PILLAR.get(), 6).m_hadhiznl('#', ModTags.DESH_PLATES).input('G', Blocks.GLOWSTONE).input('B', Items.RED_DYE).pattern("#B#").pattern("#G#")
				.pattern("#B#").group("glowing_desh_pillar").criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.GLOWING_OSTRUM_PILLAR.get(), 6).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('G', Blocks.GLOWSTONE).input('B', Items.LIME_DYE).pattern("#B#").pattern("#G#")
				.pattern("#B#").group("glowing_ostrum_pillar").criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);
		ShapedRecipeJsonFactory.create(ModItems.GLOWING_CALORITE_PILLAR.get(), 6).m_hadhiznl('#', ModTags.CALORITE_PLATES).input('G', Blocks.GLOWSTONE).input('B', Items.ORANGE_DYE).pattern("#B#")
				.pattern("#G#").pattern("#B#").group("glowing_calorite_pillar").criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.MARKED_IRON_PILLAR.get(), 6).m_hadhiznl('#', ModTags.IRON_PLATES).input('Y', Items.YELLOW_DYE).input('B', Items.BLACK_DYE).pattern("#Y#").pattern("#B#")
				.pattern("#Y#").group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Machines
		// NASA Workbench
		ShapedRecipeJsonFactory.create(ModItems.NASA_WORKBENCH.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('C', Blocks.CRAFTING_TABLE).m_hadhiznl('B', ModTags.STEEL_BLOCKS)
				.input('R', Blocks.REDSTONE_BLOCK).input('T', Blocks.REDSTONE_TORCH).input('L', Blocks.LEVER).pattern("TLT").pattern("#C#").pattern("RBR").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Solar Panel
		ShapedRecipeJsonFactory.create(ModItems.SOLAR_PANEL.get()).m_hadhiznl('#', ModTags.DESH_PLATES).m_hadhiznl('S', ModTags.STEEL_PLATES).input('G', Blocks.BLUE_STAINED_GLASS).pattern("GGG")
				.pattern("S#S").pattern("###").group(null).criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Coal Generator
		ShapedRecipeJsonFactory.create(ModItems.COAL_GENERATOR.get()).m_hadhiznl('#', ModTags.IRON_PLATES).input('I', Items.IRON_INGOT).input('F', Blocks.FURNACE).pattern("#I#").pattern("#F#")
				.pattern("#I#").group(null).criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Compressor
		ShapedRecipeJsonFactory.create(ModItems.COMPRESSOR.get()).m_hadhiznl('#', ModTags.IRON_PLATES).input('P', Blocks.PISTON).pattern("#P#").pattern("# #").pattern("#P#").group(null)
				.criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Fuel Refinery
		ShapedRecipeJsonFactory.create(ModItems.FUEL_REFINERY.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('F', Blocks.FURNACE).input('B', Items.BUCKET).pattern("###").pattern("BFB").pattern("###")
				.group(null).criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Oxygen Loader
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_LOADER.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('T', ModItems.OXYGEN_TANK.get()).input('L', Blocks.LIGHTNING_ROD)
				.input('R', Blocks.REDSTONE_BLOCK).input('F', ModItems.ENGINE_FAN.get()).pattern("#F#").pattern("TLT").pattern("#R#").group(null).criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Oxygen Distributor
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_DISTRIBUTOR.get()).m_hadhiznl('#', ModTags.DESH_PLATES).input('T', ModItems.OXYGEN_TANK.get()).input('L', ModItems.OXYGEN_LOADER.get())
				.input('F', ModItems.ENGINE_FAN.get()).input('G', ModItems.OXYGEN_GEAR.get()).pattern("FFF").pattern("TLT").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Water Pump
		ShapedRecipeJsonFactory.create(ModItems.WATER_PUMP.get()).m_hadhiznl('#', ModTags.DESH_PLATES).input('H', Blocks.HOPPER).input('D', Blocks.DISPENSER).pattern(" D ").pattern(" #D").pattern(" H ")
				.group(null).criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Energizer
		ShapedRecipeJsonFactory.create(ModItems.ENERGIZER.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('D', Blocks.DIAMOND_BLOCK).input('I', Items.DIAMOND)
				.input('B', ModItems.OSTRUM_BLOCK.get()).pattern("#I#").pattern("#D#").pattern("B#B").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		// Cryo Freezer
		ShapedRecipeJsonFactory.create(ModItems.CRYO_FREEZER.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('O', ModItems.OSTRUM_INGOT.get()).input('T', ModItems.OSTRUM_TANK.get())
				.input('B', ModItems.OSTRUM_BLOCK.get()).pattern("#O#").pattern("OTO").pattern("BOB").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		// Oxygen Sensor
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_SENSOR.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('O', ModItems.OSTRUM_INGOT.get()).input('V', Items.OBSERVER)
				.input('L', Items.REDSTONE_LAMP).input('F', ModItems.ENGINE_FAN.get()).pattern("#F#").pattern("OVO").pattern("#L#").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		// Cables
		ShapedRecipeJsonFactory.create(ModItems.STEEL_CABLE.get(), 4).m_hadhiznl('#', ModTags.STEEL_PLATES).input('C', Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_CABLE.get(), 4).m_hadhiznl('#', ModTags.DESH_PLATES).input('C', Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_FLUID_PIPE.get(), 4).m_hadhiznl('#', ModTags.DESH_PLATES).input('C', Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_FLUID_PIPE.get(), 4).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('C', Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		// Flags
		offerFlagRecipe(exporter, ModItems.WHITE_FLAG.get(), Blocks.WHITE_WOOL);
		offerFlagRecipe(exporter, ModItems.BLACK_FLAG.get(), Blocks.BLACK_WOOL);
		offerFlagRecipe(exporter, ModItems.BLUE_FLAG.get(), Blocks.BLUE_WOOL);
		offerFlagRecipe(exporter, ModItems.BROWN_FLAG.get(), Blocks.BROWN_WOOL);
		offerFlagRecipe(exporter, ModItems.CYAN_FLAG.get(), Blocks.CYAN_WOOL);
		offerFlagRecipe(exporter, ModItems.GRAY_FLAG.get(), Blocks.GRAY_WOOL);
		offerFlagRecipe(exporter, ModItems.GREEN_FLAG.get(), Blocks.GREEN_WOOL);
		offerFlagRecipe(exporter, ModItems.LIGHT_BLUE_FLAG.get(), Blocks.LIGHT_BLUE_WOOL);
		offerFlagRecipe(exporter, ModItems.LIGHT_GRAY_FLAG.get(), Blocks.LIGHT_GRAY_WOOL);
		offerFlagRecipe(exporter, ModItems.LIME_FLAG.get(), Blocks.LIME_WOOL);
		offerFlagRecipe(exporter, ModItems.MAGENTA_FLAG.get(), Blocks.MAGENTA_WOOL);
		offerFlagRecipe(exporter, ModItems.ORANGE_FLAG.get(), Blocks.ORANGE_WOOL);
		offerFlagRecipe(exporter, ModItems.PINK_FLAG.get(), Blocks.PINK_WOOL);
		offerFlagRecipe(exporter, ModItems.PURPLE_FLAG.get(), Blocks.PURPLE_WOOL);
		offerFlagRecipe(exporter, ModItems.RED_FLAG.get(), Blocks.RED_WOOL);
		offerFlagRecipe(exporter, ModItems.YELLOW_FLAG.get(), Blocks.YELLOW_WOOL);

		// Items

		// Rover
		ShapedRecipeJsonFactory.create(ModItems.TIER_1_ROVER.get()).input('#', ModItems.WHEEL.get()).m_hadhiznl('S', ModTags.STEEL_BLOCKS).m_hadhiznl('|', ModTags.IRON_RODS)
				.m_hadhiznl('D', ModTags.DESH_BLOCKS).m_hadhiznl('P', ModTags.DESH_PLATES).input('E', ModItems.DESH_ENGINE.get()).pattern("D |").pattern("SDE").pattern("#P#").group(null)
				.criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Oxygen Tank
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_TANK.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('|', ModItems.IRON_ROD.get()).pattern("#| ").pattern("## ").pattern("## ").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Guide Book
		ShapedRecipeJsonFactory.create(ModItems.ASTRODUX.get()).input('#', Items.BOOK).input('S', ModItems.STEEL_INGOT.get()).pattern("SSS").pattern("S#S").pattern("SSS").group(null)
				.criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK)).offerTo(exporter);

		// Compacting nuggets
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.STEEL_NUGGET.get(), ModItems.STEEL_INGOT.get(), "steel_ingot_from_nuggets", "steel_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.DESH_NUGGET.get(), ModItems.DESH_INGOT.get(), "desh_ingot_from_nuggets", "desh_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.OSTRUM_NUGGET.get(), ModItems.OSTRUM_INGOT.get(), "ostrum_ingot_from_nuggets", "ostrum_ingot");
		offerReversibleCompactingRecipesWithCompactedItemGroup(exporter, ModItems.CALORITE_NUGGET.get(), ModItems.CALORITE_INGOT.get(), "calorite_ingot_from_nuggets", "calorite_ingot");

		ShapelessRecipeJsonFactory.create(Items.FLINT_AND_STEEL).m_jrksubfg(ModTags.STEEL_INGOTS).input(Items.FLINT).criterion("has_flint", conditionsFromItem(Items.FLINT)).criterion("has_obsidian", conditionsFromItem(Blocks.OBSIDIAN)).offerTo(exporter);

		// Armour
		ShapedRecipeJsonFactory.create(ModItems.SPACE_HELMET.get()).m_hadhiznl('#', ModTags.STEEL_INGOTS).input('G', Blocks.ORANGE_STAINED_GLASS_PANE).pattern("###").pattern("#G#").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_SUIT.get()).m_hadhiznl('#', ModTags.STEEL_INGOTS).input('W', Blocks.WHITE_WOOL).input('O', ModItems.OXYGEN_GEAR.get())
				.input('T', ModItems.OXYGEN_TANK.get()).pattern("# #").pattern("TOT").pattern("#W#").group(null).criterion(hasItem(ModItems.STEEL_INGOT.get()), conditionsFromItem(ModItems.STEEL_INGOT.get())).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_PANTS.get()).m_hadhiznl('#', ModTags.STEEL_INGOTS).input('W', Blocks.WHITE_WOOL).pattern("###").pattern("W W").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.SPACE_BOOTS.get()).m_hadhiznl('#', ModTags.STEEL_INGOTS).input('W', Blocks.WHITE_WOOL).pattern("W W").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_INGOTS)).offerTo(exporter);

		// Netherite
		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_HELMET.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('N', Items.NETHERITE_HELMET).input('G', Blocks.ORANGE_STAINED_GLASS_PANE)
				.pattern("#N#").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_SUIT.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('N', Items.NETHERITE_CHESTPLATE).input('O', ModItems.OXYGEN_GEAR.get())
				.input('T', ModItems.OXYGEN_TANK.get()).pattern("# #").pattern("TOT").pattern("#N#").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_PANTS.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('N', Items.NETHERITE_LEGGINGS).m_hadhiznl('D', ModTags.DESH_PLATES).pattern("#N#")
				.pattern("D D").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.NETHERITE_SPACE_BOOTS.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('N', Items.NETHERITE_BOOTS).m_hadhiznl('D', ModTags.DESH_PLATES).pattern(" N ")
				.pattern("D D").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		// Jet Suit
		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_HELMET.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).input('N', ModItems.NETHERITE_SPACE_HELMET.get()).input('G', Blocks.ORANGE_STAINED_GLASS)
				.pattern("#N#").pattern("#G#").group(null).criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).m_hadhiznl('B', ModTags.CALORITE_BLOCKS).input('N', ModItems.NETHERITE_SPACE_SUIT.get())
				.input('E', ModItems.CALORITE_ENGINE.get()).input('T', ModItems.CALORITE_TANK.get()).pattern("# #").pattern("TNT").pattern("BEB").group(null).criterion("has_tag", method_10420(ModTags.CALORITE_PLATES))
				.offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_PANTS.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).input('N', ModItems.NETHERITE_SPACE_PANTS.get()).pattern("#N#").pattern("# #").pattern("# #").group(null)
				.criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.JET_SUIT_BOOTS.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).m_hadhiznl('B', ModTags.CALORITE_BLOCKS).input('N', ModItems.NETHERITE_SPACE_BOOTS.get())
				.pattern(" N ").pattern("# #").pattern("B B").group(null).criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		// Soul Torch
		ShapedRecipeJsonFactory.create(Items.SOUL_TORCH).input('#', ModItems.EXTINGUISHED_TORCH.get()).input('S', Items.SOUL_SOIL).pattern("S").pattern("#")
				.criterion(hasItem(ModItems.EXTINGUISHED_TORCH.get()), conditionsFromItem(ModItems.EXTINGUISHED_TORCH.get())).offerTo(exporter);
		// Hammer
		ShapedRecipeJsonFactory.create(ModItems.HAMMER.get()).input('#', Items.IRON_INGOT).input('|', Items.STICK).pattern(" # ").pattern(" |#").pattern("|  ").group(null)
				.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT)).offerTo(exporter);

		// Iron Rod
		ShapedRecipeJsonFactory.create(ModItems.IRON_ROD.get(), 4).m_hadhiznl('#', ModTags.IRON_PLATES).pattern("#").pattern("#").group("iron_rods").criterion("has_tag", method_10420(ModTags.IRON_PLATES)).offerTo(exporter);

		// Oxygen Gear
		ShapedRecipeJsonFactory.create(ModItems.OXYGEN_GEAR.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).pattern(" | ").pattern("#|#").pattern("#|#").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Wheel
		ShapedRecipeJsonFactory.create(ModItems.WHEEL.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('B', Items.BLACK_DYE).pattern(" B ").pattern("B#B").pattern(" B ").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Engine Frame
		ShapedRecipeJsonFactory.create(ModItems.ENGINE_FRAME.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).pattern("|||").pattern("|#|").pattern("|||").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Engine Fan
		ShapedRecipeJsonFactory.create(ModItems.ENGINE_FAN.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).pattern(" | ").pattern("|#|").pattern(" | ").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Nose Cone
		ShapedRecipeJsonFactory.create(ModItems.ROCKET_NOSE_CONE.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('|', Items.LIGHTNING_ROD).pattern(" | ").pattern(" # ").pattern("###").group(null)
				.criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		// Engines
		ShapedRecipeJsonFactory.create(ModItems.STEEL_ENGINE.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).input('E', ModItems.ENGINE_FRAME.get()).input('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_ENGINE.get()).m_hadhiznl('#', ModTags.DESH_PLATES).input('E', ModItems.ENGINE_FRAME.get()).input('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_ENGINE.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).input('E', ModItems.ENGINE_FRAME.get()).input('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
				.pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.CALORITE_ENGINE.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).input('E', ModItems.ENGINE_FRAME.get()).input('F', ModItems.ENGINE_FAN.get()).pattern("###")
				.pattern(" E ").pattern(" F ").group("engines").criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		// Tanks
		ShapedRecipeJsonFactory.create(ModItems.STEEL_TANK.get()).m_hadhiznl('#', ModTags.STEEL_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).input('U', Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.DESH_TANK.get()).m_hadhiznl('#', ModTags.DESH_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).input('U', Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.OSTRUM_TANK.get()).m_hadhiznl('#', ModTags.OSTRUM_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).input('U', Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.OSTRUM_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.CALORITE_TANK.get()).m_hadhiznl('#', ModTags.CALORITE_PLATES).m_hadhiznl('|', ModTags.IRON_RODS).input('U', Items.BUCKET).pattern("## ").pattern("#U|")
				.pattern("## ").group("tanks").criterion("has_tag", method_10420(ModTags.CALORITE_PLATES)).offerTo(exporter);

		ShapedRecipeJsonFactory.create(ModItems.WRENCH.get()).m_hadhiznl('#', ModTags.DESH_PLATES).pattern("# #").pattern(" # ").pattern(" # ").group(null).criterion("has_tag", method_10420(ModTags.DESH_PLATES)).offerTo(exporter);

		// Fin
		ShapedRecipeJsonFactory.create(ModItems.ROCKET_FIN.get(), 4).m_hadhiznl('#', ModTags.STEEL_PLATES).pattern(" # ").pattern("###").pattern("# #").group(null).criterion("has_tag", method_10420(ModTags.STEEL_PLATES)).offerTo(exporter);

	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		offerPolishedStoneRecipe(exporter, output, input);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		createCondensingRecipe(output, Ingredient.ofTag(input)).criterion("item", method_10420(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, ItemConvertible input) {
		ShapedRecipeJsonFactory.create(output, amount).input('S', input).pattern("SS").pattern("SS").criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(input)).offerTo(exporter);
	}

	public static void offerQuadRecipe(Consumer<RecipeJsonProvider> exporter, int amount, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonFactory.create(output, amount).m_hadhiznl('S', input).pattern("SS").pattern("SS").criterion("has_tag", method_10420(input)).offerTo(exporter);
	}

	public static void offerPlatingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, TagKey<Item> input) {
		ShapedRecipeJsonFactory.create(output, 8).input('#', ModItems.IRON_PLATING.get()).m_hadhiznl('|', input).pattern("###").pattern("#|#").pattern("###").group("desh_plating").criterion("has_tag", method_10420(input))
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
		ShapedRecipeJsonFactory.create(output).input('#', input).m_hadhiznl('|', ModTags.IRON_RODS).pattern("|##").pattern("|##").pattern("|  ").group("flag").criterion(hasItem(input), conditionsFromItem(input))
				.offerTo(exporter);
	}

	public void offerButtonRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
		ShapelessRecipeJsonFactory.create(output).input(input).group("button").criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter);
	}
}