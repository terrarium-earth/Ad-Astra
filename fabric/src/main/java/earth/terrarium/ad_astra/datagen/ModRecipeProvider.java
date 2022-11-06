package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.registry.ModBlocks;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.registry.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.function.Consumer;

class ModRecipeProvider extends FabricRecipeProvider {
    ModRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    public static void offerQuadRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        polished(exporter, output, input);
    }

    public static void offerQuadRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, TagKey<Item> input) {
        polishedBuilder(output, Ingredient.of(input)).unlockedBy("item", has(input)).save(exporter);
    }

    public static void offerQuadRecipe(Consumer<FinishedRecipe> exporter, int amount, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(output, amount).define('S', input).pattern("SS").pattern("SS").unlockedBy(getHasName(Items.IRON_INGOT), has(input)).save(exporter);
    }

    public static void offerQuadRecipe(Consumer<FinishedRecipe> exporter, int amount, ItemLike output, TagKey<Item> input) {
        ShapedRecipeBuilder.shaped(output, amount).define('S', input).pattern("SS").pattern("SS").unlockedBy("has_tag", has(input)).save(exporter);
    }

    public static void offerPlatingRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, TagKey<Item> input) {
        ShapedRecipeBuilder.shaped(output, 8).define('#', ModItems.IRON_PLATING.get()).define('|', input).pattern("###").pattern("#|#").pattern("###").group("desh_plating").unlockedBy("has_tag", has(input))
                .save(exporter);
    }

    public static void offerCustomSlabRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        slab(exporter, output, input);
        stonecutterResultFromBase(exporter, output, input, 2);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {

        // Glacian Wood
        woodFromLogs(exporter, ModBlocks.STRIPPED_GLACIAN_LOG.get(), ModBlocks.GLACIAN_LOG.get());
        planksFromLogs(exporter, ModBlocks.GLACIAN_PLANKS.get(), ModTags.GLACIAN_LOGS);
        stairBuilder(ModBlocks.GLACIAN_STAIRS.get(), Ingredient.of(ModBlocks.GLACIAN_PLANKS.get())).unlockedBy(getHasName(ModBlocks.GLACIAN_PLANKS.get()), has(ModBlocks.GLACIAN_PLANKS.get())).save(exporter);
        slab(exporter, ModBlocks.GLACIAN_SLAB.get(), ModBlocks.GLACIAN_PLANKS.get());
        doorBuilder(ModBlocks.GLACIAN_DOOR.get(), Ingredient.of(ModBlocks.GLACIAN_PLANKS.get())).unlockedBy("has_tag", has(ModTags.GLACIAN_LOGS)).save(exporter);
        trapdoorBuilder(ModBlocks.GLACIAN_TRAPDOOR.get(), Ingredient.of(ModItems.GLACIAN_PLANKS.get())).unlockedBy("has_tag", has(ModTags.GLACIAN_LOGS)).save(exporter);
        fenceBuilder(ModBlocks.GLACIAN_FENCE.get(), Ingredient.of(ModBlocks.GLACIAN_PLANKS.get())).unlockedBy("has_tag", has(ModTags.GLACIAN_LOGS)).save(exporter);
        fenceGateBuilder(ModBlocks.GLACIAN_FENCE_GATE.get(), Ingredient.of(ModBlocks.GLACIAN_PLANKS.get())).unlockedBy("has_tag", has(ModTags.GLACIAN_LOGS)).save(exporter);
        offerButtonRecipe(exporter, ModBlocks.GLACIAN_BUTTON.get(), ModBlocks.GLACIAN_PLANKS.get());
        pressurePlate(exporter, ModBlocks.GLACIAN_PRESSURE_PLATE.get(), ModBlocks.GLACIAN_PLANKS.get());
        signBuilder(ModBlocks.GLACIAN_SIGN.get(), Ingredient.of(ModBlocks.GLACIAN_PLANKS.get())).unlockedBy("has_tag", has(ModTags.GLACIAN_LOGS)).save(exporter);

        // Mushrooms
        planksFromLogs(exporter, ModBlocks.AERONOS_PLANKS.get(), ModTags.AERONOS_CAPS);
        stairBuilder(ModBlocks.AERONOS_STAIRS.get(), Ingredient.of(ModBlocks.AERONOS_PLANKS.get())).unlockedBy(getHasName(ModBlocks.AERONOS_PLANKS.get()), has(ModBlocks.AERONOS_PLANKS.get())).save(exporter);
        slab(exporter, ModBlocks.AERONOS_SLAB.get(), ModBlocks.AERONOS_PLANKS.get());
        doorBuilder(ModBlocks.AERONOS_DOOR.get(), Ingredient.of(ModBlocks.AERONOS_PLANKS.get())).unlockedBy("has_tag", has(ModTags.AERONOS_CAPS)).save(exporter);
        trapdoorBuilder(ModBlocks.AERONOS_TRAPDOOR.get(), Ingredient.of(ModItems.AERONOS_PLANKS.get())).unlockedBy("has_tag", has(ModTags.AERONOS_CAPS)).save(exporter);
        fenceBuilder(ModBlocks.AERONOS_FENCE.get(), Ingredient.of(ModBlocks.AERONOS_PLANKS.get())).unlockedBy("has_tag", has(ModTags.AERONOS_CAPS)).save(exporter);
        fenceGateBuilder(ModBlocks.AERONOS_FENCE_GATE.get(), Ingredient.of(ModBlocks.AERONOS_PLANKS.get())).unlockedBy("has_tag", has(ModTags.AERONOS_CAPS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModBlocks.AERONOS_CHEST.get()).define('#', ModBlocks.AERONOS_PLANKS.get()).pattern("###").pattern("# #").pattern("###")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])).save(exporter);
        ShapedRecipeBuilder.shaped(ModBlocks.AERONOS_LADDER.get(), 6).define('#', ModItems.AERONOS_PLANKS.get()).pattern("# #").pattern("###").pattern("# #").unlockedBy("has_aeronos_planks", has(ModItems.AERONOS_PLANKS.get())).save(exporter);

        planksFromLogs(exporter, ModBlocks.STROPHAR_PLANKS.get(), ModTags.STROPHAR_CAPS);
        stairBuilder(ModBlocks.STROPHAR_STAIRS.get(), Ingredient.of(ModBlocks.STROPHAR_PLANKS.get())).unlockedBy(getHasName(ModBlocks.STROPHAR_PLANKS.get()), has(ModBlocks.STROPHAR_PLANKS.get())).save(exporter);
        slab(exporter, ModBlocks.STROPHAR_SLAB.get(), ModBlocks.STROPHAR_PLANKS.get());
        doorBuilder(ModBlocks.STROPHAR_DOOR.get(), Ingredient.of(ModBlocks.STROPHAR_PLANKS.get())).unlockedBy("has_tag", has(ModTags.STROPHAR_CAPS)).save(exporter);
        trapdoorBuilder(ModBlocks.STROPHAR_TRAPDOOR.get(), Ingredient.of(ModItems.STROPHAR_PLANKS.get())).unlockedBy("has_tag", has(ModTags.STROPHAR_CAPS)).save(exporter);
        fenceBuilder(ModBlocks.STROPHAR_FENCE.get(), Ingredient.of(ModBlocks.STROPHAR_PLANKS.get())).unlockedBy("has_tag", has(ModTags.STROPHAR_CAPS)).save(exporter);
        fenceGateBuilder(ModBlocks.STROPHAR_FENCE_GATE.get(), Ingredient.of(ModBlocks.STROPHAR_PLANKS.get())).unlockedBy("has_tag", has(ModTags.STROPHAR_CAPS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModBlocks.STROPHAR_CHEST.get()).define('#', ModBlocks.STROPHAR_PLANKS.get()).pattern("###").pattern("# #").pattern("###")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0])).save(exporter);
        ShapedRecipeBuilder.shaped(ModBlocks.STROPHAR_LADDER.get(), 6).define('#', ModItems.STROPHAR_PLANKS.get()).pattern("# #").pattern("###").pattern("# #").unlockedBy("has_rod", has(ModItems.STROPHAR_PLANKS.get())).save(exporter);

        // Blasting
        oreBlasting(exporter, List.of(Items.IRON_INGOT), ModItems.STEEL_INGOT.get(), 1.0f, 100, null);
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
        oreSmelting(exporter, List.of(ModBlocks.MOON_COBBLESTONE.get()), ModBlocks.MOON_STONE.get(), 0.2f, 200, null);
        oreSmelting(exporter, List.of(ModBlocks.MARS_COBBLESTONE.get()), ModBlocks.MARS_STONE.get(), 0.2f, 200, null);
        oreSmelting(exporter, List.of(ModBlocks.VENUS_COBBLESTONE.get()), ModBlocks.VENUS_STONE.get(), 0.2f, 200, null);
        oreSmelting(exporter, List.of(ModBlocks.MERCURY_COBBLESTONE.get()), ModBlocks.MERCURY_STONE.get(), 0.2f, 200, null);
        oreSmelting(exporter, List.of(ModBlocks.GLACIO_COBBLESTONE.get()), ModBlocks.GLACIO_STONE.get(), 0.2f, 200, null);

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
        wall(exporter, ModBlocks.MOON_STONE_BRICK_WALL.get(), ModBlocks.MOON_STONE_BRICKS.get());
        wall(exporter, ModBlocks.MARS_STONE_BRICK_WALL.get(), ModBlocks.MARS_STONE_BRICKS.get());
        wall(exporter, ModBlocks.VENUS_STONE_BRICK_WALL.get(), ModBlocks.VENUS_STONE_BRICKS.get());
        wall(exporter, ModBlocks.MERCURY_STONE_BRICK_WALL.get(), ModBlocks.MERCURY_STONE_BRICKS.get());
        wall(exporter, ModBlocks.GLACIO_STONE_BRICK_WALL.get(), ModBlocks.GLACIO_STONE_BRICKS.get());
        wall(exporter, ModBlocks.PERMAFROST_BRICK_WALL.get(), ModBlocks.PERMAFROST_BRICKS.get());

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
        pressurePlate(exporter, ModBlocks.IRON_PLATING_PRESSURE_PLATE.get(), ModBlocks.IRON_PLATING.get());
        pressurePlate(exporter, ModBlocks.STEEL_PLATING_PRESSURE_PLATE.get(), ModBlocks.STEEL_PLATING.get());
        pressurePlate(exporter, ModBlocks.DESH_PLATING_PRESSURE_PLATE.get(), ModBlocks.DESH_PLATING.get());
        pressurePlate(exporter, ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE.get(), ModBlocks.OSTRUM_PLATING.get());
        pressurePlate(exporter, ModBlocks.CALORITE_PLATING_PRESSURE_PLATE.get(), ModBlocks.CALORITE_PLATING.get());

        // Stonecutting
        // --->
        // Bricks
        stonecutterResultFromBase(exporter, ModBlocks.MOON_STONE_BRICKS.get(), ModBlocks.POLISHED_MOON_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.MARS_STONE_BRICKS.get(), ModBlocks.POLISHED_MARS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.VENUS_SANDSTONE_BRICKS.get(), ModBlocks.VENUS_SANDSTONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.VENUS_STONE_BRICKS.get(), ModBlocks.POLISHED_VENUS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.MERCURY_STONE_BRICKS.get(), ModBlocks.POLISHED_MERCURY_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.GLACIO_STONE_BRICKS.get(), ModBlocks.POLISHED_GLACIO_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST.get());

        // Chiseled
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS.get(), ModBlocks.MOON_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS.get(), ModBlocks.MARS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS.get(), ModBlocks.VENUS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get(), ModBlocks.MERCURY_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get(), ModBlocks.GLACIO_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST.get());

        // Polished
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_MOON_STONE.get(), ModBlocks.MOON_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_MARS_STONE.get(), ModBlocks.MARS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_VENUS_STONE.get(), ModBlocks.VENUS_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_MERCURY_STONE.get(), ModBlocks.MERCURY_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_GLACIO_STONE.get(), ModBlocks.GLACIO_STONE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_CONGLOMERATE.get(), ModBlocks.CONGLOMERATE.get());
        stonecutterResultFromBase(exporter, ModBlocks.POLISHED_PERMAFROST.get(), ModBlocks.PERMAFROST.get());
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
        nineBlockStorageRecipes(exporter, ModItems.STEEL_INGOT.get(), ModBlocks.STEEL_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.CHEESE.get(), ModBlocks.CHEESE_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.DESH_INGOT.get(), ModBlocks.DESH_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.OSTRUM_INGOT.get(), ModBlocks.OSTRUM_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.CALORITE_INGOT.get(), ModBlocks.CALORITE_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.RAW_DESH.get(), ModBlocks.RAW_DESH_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.RAW_OSTRUM.get(), ModBlocks.RAW_OSTRUM_BLOCK.get());
        nineBlockStorageRecipes(exporter, ModItems.RAW_CALORITE.get(), ModBlocks.RAW_CALORITE_BLOCK.get());

        // Cracked
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MOON_STONE_BRICKS.get()), ModBlocks.CRACKED_MOON_STONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MARS_STONE_BRICKS.get()), ModBlocks.CRACKED_MARS_STONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS.get()), ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.VENUS_STONE_BRICKS.get()), ModBlocks.CRACKED_VENUS_STONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.MERCURY_STONE_BRICKS.get()), ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.GLACIO_STONE_BRICKS.get()), ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get());
        offerCrackedBricksSmeltingRecipe(exporter, List.of(ModItems.PERMAFROST_BRICKS.get()), ModBlocks.CRACKED_PERMAFROST_BRICKS.get());

        // Polished
        polished(exporter, ModBlocks.POLISHED_MOON_STONE.get(), ModBlocks.MOON_STONE.get());
        polished(exporter, ModBlocks.POLISHED_MARS_STONE.get(), ModBlocks.MARS_STONE.get());
        polished(exporter, ModBlocks.POLISHED_VENUS_STONE.get(), ModBlocks.VENUS_STONE.get());
        polished(exporter, ModBlocks.POLISHED_MERCURY_STONE.get(), ModBlocks.MERCURY_STONE.get());
        polished(exporter, ModBlocks.POLISHED_GLACIO_STONE.get(), ModBlocks.GLACIO_STONE.get());
        polished(exporter, ModBlocks.POLISHED_CONGLOMERATE.get(), ModBlocks.CONGLOMERATE.get());
        polished(exporter, ModBlocks.POLISHED_PERMAFROST.get(), ModBlocks.PERMAFROST.get());

        // Chiseled
        chiseled(exporter, ModBlocks.CHISELED_MOON_STONE_BRICKS.get(), ModBlocks.MOON_STONE_BRICK_SLAB.get());
        chiseled(exporter, ModBlocks.CHISELED_MARS_STONE_BRICKS.get(), ModBlocks.MARS_STONE_BRICK_SLAB.get());
        chiseled(exporter, ModBlocks.CHISELED_VENUS_STONE_BRICKS.get(), ModBlocks.VENUS_STONE_BRICK_SLAB.get());
        chiseled(exporter, ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get(), ModBlocks.MERCURY_STONE_BRICK_SLAB.get());
        chiseled(exporter, ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get(), ModBlocks.GLACIO_STONE_BRICK_SLAB.get());
        chiseled(exporter, ModBlocks.CHISELED_PERMAFROST_BRICKS.get(), ModBlocks.PERMAFROST_BRICK_SLAB.get());

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
        ShapedRecipeBuilder.shaped(ModItems.LAUNCH_PAD.get()).define('#', ModTags.STEEL_PLATES).define('|', ModTags.IRON_PLATES).pattern("#|#").pattern("|#|").pattern("#|#").group("launch_pad")
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Metals

        ShapedRecipeBuilder.shaped(ModItems.IRON_PLATING.get(), 32).define('#', ModTags.IRON_PLATES).pattern("###").pattern("###").pattern("###").group("iron_plates").unlockedBy("has_tag", has(ModTags.STEEL_PLATES))
                .save(exporter);
        offerPlatingRecipe(exporter, ModItems.STEEL_PLATING.get(), ModTags.STEEL_PLATES);
        offerPlatingRecipe(exporter, ModItems.DESH_PLATING.get(), ModTags.DESH_PLATES);
        offerPlatingRecipe(exporter, ModItems.OSTRUM_PLATING.get(), ModTags.OSTRUM_PLATES);
        offerPlatingRecipe(exporter, ModItems.CALORITE_PLATING.get(), ModTags.CALORITE_PLATES);

        doorBuilder(ModItems.STEEL_DOOR.get(), Ingredient.of(ModTags.STEEL_PLATES)).unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);
        trapdoorBuilder(ModItems.STEEL_TRAPDOOR.get(), Ingredient.of(ModTags.STEEL_PLATES)).unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Doors
        ShapedRecipeBuilder.shaped(ModItems.IRON_SLIDING_DOOR.get()).define('#', Items.IRON_BLOCK).define('C', ModTags.IRON_PLATES).define('P', Items.GLASS_PANE).pattern("CCC").pattern("P#P")
                .pattern("CCC").group("sliding_door").unlockedBy("has_item", has(Items.IRON_BLOCK)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.STEEL_SLIDING_DOOR.get()).define('#', ModTags.STEEL_BLOCKS).define('C', ModTags.STEEL_PLATES).define('P', Items.GLASS_PANE).pattern("CCC")
                .pattern("P#P").pattern("CCC").group("sliding_door").unlockedBy("has_item", has(ModTags.STEEL_BLOCKS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.DESH_SLIDING_DOOR.get()).define('#', ModTags.DESH_BLOCKS).define('C', ModTags.DESH_PLATES).define('P', Items.GLASS_PANE).pattern("CCC").pattern("P#P")
                .pattern("CCC").group("sliding_door").unlockedBy("has_item", has(ModTags.DESH_BLOCKS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.OSTRUM_SLIDING_DOOR.get()).define('#', ModTags.OSTRUM_BLOCKS).define('C', ModTags.OSTRUM_PLATES).define('P', Items.GLASS_PANE).pattern("CCC")
                .pattern("P#P").pattern("CCC").group("sliding_door").unlockedBy("has_item", has(ModTags.OSTRUM_BLOCKS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.CALORITE_SLIDING_DOOR.get()).define('#', ModTags.CALORITE_BLOCKS).define('C', ModTags.CALORITE_PLATES).define('P', Items.GLASS_PANE).pattern("CCC")
                .pattern("P#P").pattern("CCC").group("sliding_door").unlockedBy("has_item", has(ModTags.CALORITE_BLOCKS)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.AIRLOCK.get()).define('#', ModTags.STEEL_BLOCKS).define('C', ModTags.STEEL_PLATES).pattern("CC#").pattern("CCC").pattern("#CC").group("sliding_door")
                .unlockedBy("has_tag", has(ModTags.STEEL_BLOCKS)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.REINFORCED_DOOR.get()).define('#', ModTags.STEEL_BLOCKS).define('O', Blocks.OBSIDIAN).define('G', Items.GLASS_PANE).pattern("O#O").pattern("G#G")
                .pattern("O#O").group("sliding_door").unlockedBy("has_tag", has(ModTags.STEEL_BLOCKS)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.GLOWING_IRON_PILLAR.get(), 6).define('#', ModTags.IRON_PLATES).define('G', Blocks.GLOWSTONE).define('B', Items.LIGHT_BLUE_DYE).pattern("#B#")
                .pattern("#G#").pattern("#B#").group("glowing_iron_pillar").unlockedBy("has_tag", has(ModTags.IRON_PLATES)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.GLOWING_STEEL_PILLAR.get(), 6).define('#', ModTags.STEEL_PLATES).define('G', Blocks.GLOWSTONE).define('B', Items.PURPLE_DYE).pattern("#B#").pattern("#G#")
                .pattern("#B#").group("glowing_steel_pillar").unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.GLOWING_DESH_PILLAR.get(), 6).define('#', ModTags.DESH_PLATES).define('G', Blocks.GLOWSTONE).define('B', Items.RED_DYE).pattern("#B#").pattern("#G#")
                .pattern("#B#").group("glowing_desh_pillar").unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.GLOWING_OSTRUM_PILLAR.get(), 6).define('#', ModTags.OSTRUM_PLATES).define('G', Blocks.GLOWSTONE).define('B', Items.LIME_DYE).pattern("#B#").pattern("#G#")
                .pattern("#B#").group("glowing_ostrum_pillar").unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);
        ShapedRecipeBuilder.shaped(ModItems.GLOWING_CALORITE_PILLAR.get(), 6).define('#', ModTags.CALORITE_PLATES).define('G', Blocks.GLOWSTONE).define('B', Items.ORANGE_DYE).pattern("#B#")
                .pattern("#G#").pattern("#B#").group("glowing_calorite_pillar").unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.MARKED_IRON_PILLAR.get(), 6).define('#', ModTags.IRON_PLATES).define('Y', Items.YELLOW_DYE).define('B', Items.BLACK_DYE).pattern("#Y#").pattern("#B#")
                .pattern("#Y#").group(null).unlockedBy("has_tag", has(ModTags.IRON_PLATES)).save(exporter);

        // Machines
        // NASA Workbench
        ShapedRecipeBuilder.shaped(ModItems.NASA_WORKBENCH.get()).define('#', ModTags.STEEL_PLATES).define('C', Blocks.CRAFTING_TABLE).define('B', ModTags.STEEL_BLOCKS)
                .define('R', Blocks.REDSTONE_BLOCK).define('T', Blocks.REDSTONE_TORCH).define('L', Blocks.LEVER).pattern("TLT").pattern("#C#").pattern("RBR").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Solar Panel
        ShapedRecipeBuilder.shaped(ModItems.SOLAR_PANEL.get()).define('#', ModTags.DESH_PLATES).define('S', ModTags.STEEL_PLATES).define('G', Blocks.BLUE_STAINED_GLASS).pattern("GGG")
                .pattern("S#S").pattern("###").group(null).unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        // Coal Generator
        ShapedRecipeBuilder.shaped(ModItems.COAL_GENERATOR.get()).define('#', ModTags.IRON_PLATES).define('I', Items.IRON_INGOT).define('F', Blocks.FURNACE).pattern("#I#").pattern("#F#")
                .pattern("#I#").group(null).unlockedBy("has_tag", has(ModTags.IRON_PLATES)).save(exporter);

        // Compressor
        ShapedRecipeBuilder.shaped(ModItems.COMPRESSOR.get()).define('#', ModTags.IRON_PLATES).define('P', Blocks.PISTON).pattern("#P#").pattern("# #").pattern("#P#").group(null)
                .unlockedBy("has_tag", has(ModTags.IRON_PLATES)).save(exporter);

        // Fuel Refinery
        ShapedRecipeBuilder.shaped(ModItems.FUEL_REFINERY.get()).define('#', ModTags.STEEL_PLATES).define('F', Blocks.FURNACE).define('B', Items.BUCKET).pattern("###").pattern("BFB").pattern("###")
                .group(null).unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Oxygen Loader
        ShapedRecipeBuilder.shaped(ModItems.OXYGEN_LOADER.get()).define('#', ModTags.STEEL_PLATES).define('T', ModItems.OXYGEN_TANK.get()).define('L', Blocks.LIGHTNING_ROD)
                .define('R', Blocks.REDSTONE_BLOCK).define('F', ModItems.ENGINE_FAN.get()).pattern("#F#").pattern("TLT").pattern("#R#").group(null).unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Oxygen Distributor
        ShapedRecipeBuilder.shaped(ModItems.OXYGEN_DISTRIBUTOR.get()).define('#', ModTags.DESH_PLATES).define('T', ModItems.OXYGEN_TANK.get()).define('L', ModItems.OXYGEN_LOADER.get())
                .define('F', ModItems.ENGINE_FAN.get()).define('G', ModItems.OXYGEN_GEAR.get()).pattern("FFF").pattern("TLT").pattern("#G#").group(null).unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        // Water Pump
        ShapedRecipeBuilder.shaped(ModItems.WATER_PUMP.get()).define('#', ModTags.DESH_PLATES).define('H', Blocks.HOPPER).define('D', Blocks.DISPENSER).pattern(" D ").pattern(" #D").pattern(" H ")
                .group(null).unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        // Energizer
        ShapedRecipeBuilder.shaped(ModItems.ENERGIZER.get()).define('#', ModTags.OSTRUM_PLATES).define('D', Blocks.DIAMOND_BLOCK).define('I', Items.DIAMOND)
                .define('B', ModItems.OSTRUM_BLOCK.get()).pattern("#I#").pattern("#D#").pattern("B#B").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        // Cryo Freezer
        ShapedRecipeBuilder.shaped(ModItems.CRYO_FREEZER.get()).define('#', ModTags.OSTRUM_PLATES).define('O', ModItems.OSTRUM_INGOT.get()).define('T', ModItems.OSTRUM_TANK.get())
                .define('B', ModItems.OSTRUM_BLOCK.get()).pattern("#O#").pattern("OTO").pattern("BOB").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        // Oxygen Sensor
        ShapedRecipeBuilder.shaped(ModItems.OXYGEN_SENSOR.get()).define('#', ModTags.OSTRUM_PLATES).define('O', ModItems.OSTRUM_INGOT.get()).define('V', Items.OBSERVER)
                .define('L', Items.REDSTONE_LAMP).define('F', ModItems.ENGINE_FAN.get()).pattern("#F#").pattern("OVO").pattern("#L#").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        // Cables
        ShapedRecipeBuilder.shaped(ModItems.STEEL_CABLE.get(), 4).define('#', ModTags.STEEL_PLATES).define('C', Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.DESH_CABLE.get(), 4).define('#', ModTags.DESH_PLATES).define('C', Items.COPPER_INGOT).pattern("###").pattern("CCC").pattern("###").group(null)
                .unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.DESH_FLUID_PIPE.get(), 4).define('#', ModTags.DESH_PLATES).define('C', Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
                .unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.OSTRUM_FLUID_PIPE.get(), 4).define('#', ModTags.OSTRUM_PLATES).define('C', Items.GLASS).pattern("###").pattern("CCC").pattern("###").group(null)
                .unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

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
        ShapedRecipeBuilder.shaped(ModItems.TIER_1_ROVER.get()).define('#', ModItems.WHEEL.get()).define('S', ModTags.STEEL_BLOCKS).define('|', ModTags.IRON_RODS)
                .define('D', ModTags.DESH_BLOCKS).define('P', ModTags.DESH_PLATES).define('E', ModItems.DESH_ENGINE.get()).pattern("D |").pattern("SDE").pattern("#P#").group(null)
                .unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        // Oxygen Tank
        ShapedRecipeBuilder.shaped(ModItems.OXYGEN_TANK.get()).define('#', ModTags.STEEL_PLATES).define('|', ModItems.IRON_ROD.get()).pattern("#| ").pattern("## ").pattern("## ").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Guide Book
        ShapedRecipeBuilder.shaped(ModItems.ASTRODUX.get()).define('#', Items.BOOK).define('S', ModItems.STEEL_INGOT.get()).pattern("SSS").pattern("S#S").pattern("SSS").group(null)
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK)).save(exporter);

        // Compacting nuggets
        nineBlockStorageRecipesWithCustomPacking(exporter, ModItems.STEEL_NUGGET.get(), ModItems.STEEL_INGOT.get(), "steel_ingot_from_nuggets", "steel_ingot");
        nineBlockStorageRecipesWithCustomPacking(exporter, ModItems.DESH_NUGGET.get(), ModItems.DESH_INGOT.get(), "desh_ingot_from_nuggets", "desh_ingot");
        nineBlockStorageRecipesWithCustomPacking(exporter, ModItems.OSTRUM_NUGGET.get(), ModItems.OSTRUM_INGOT.get(), "ostrum_ingot_from_nuggets", "ostrum_ingot");
        nineBlockStorageRecipesWithCustomPacking(exporter, ModItems.CALORITE_NUGGET.get(), ModItems.CALORITE_INGOT.get(), "calorite_ingot_from_nuggets", "calorite_ingot");

        ShapelessRecipeBuilder.shapeless(Items.FLINT_AND_STEEL).requires(ModTags.STEEL_INGOTS).requires(Items.FLINT).unlockedBy("has_flint", has(Items.FLINT)).unlockedBy("has_obsidian", has(Blocks.OBSIDIAN)).save(exporter);

        // Armour
        ShapedRecipeBuilder.shaped(ModItems.SPACE_HELMET.get()).define('#', ModTags.STEEL_INGOTS).define('G', Blocks.ORANGE_STAINED_GLASS_PANE).pattern("###").pattern("#G#").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_INGOTS)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.SPACE_SUIT.get()).define('#', ModTags.STEEL_INGOTS).define('W', Blocks.WHITE_WOOL).define('O', ModItems.OXYGEN_GEAR.get())
                .define('T', ModItems.OXYGEN_TANK.get()).pattern("# #").pattern("TOT").pattern("#W#").group(null).unlockedBy(getHasName(ModItems.STEEL_INGOT.get()), has(ModItems.STEEL_INGOT.get())).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.SPACE_PANTS.get()).define('#', ModTags.STEEL_INGOTS).define('W', Blocks.WHITE_WOOL).pattern("###").pattern("W W").pattern("# #").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_INGOTS)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.SPACE_BOOTS.get()).define('#', ModTags.STEEL_INGOTS).define('W', Blocks.WHITE_WOOL).pattern("W W").pattern("# #").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_INGOTS)).save(exporter);

        // Netherite
        ShapedRecipeBuilder.shaped(ModItems.NETHERITE_SPACE_HELMET.get()).define('#', ModTags.OSTRUM_PLATES).define('N', Items.NETHERITE_HELMET).define('G', Blocks.ORANGE_STAINED_GLASS_PANE)
                .pattern("#N#").pattern("#G#").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.NETHERITE_SPACE_SUIT.get()).define('#', ModTags.OSTRUM_PLATES).define('N', Items.NETHERITE_CHESTPLATE).define('O', ModItems.OXYGEN_GEAR.get())
                .define('T', ModItems.OXYGEN_TANK.get()).pattern("# #").pattern("TOT").pattern("#N#").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.NETHERITE_SPACE_PANTS.get()).define('#', ModTags.OSTRUM_PLATES).define('N', Items.NETHERITE_LEGGINGS).define('D', ModTags.DESH_PLATES).pattern("#N#")
                .pattern("D D").pattern("# #").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.NETHERITE_SPACE_BOOTS.get()).define('#', ModTags.OSTRUM_PLATES).define('N', Items.NETHERITE_BOOTS).define('D', ModTags.DESH_PLATES).pattern(" N ")
                .pattern("D D").pattern("# #").group(null).unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        // Jet Suit
        ShapedRecipeBuilder.shaped(ModItems.JET_SUIT_HELMET.get()).define('#', ModTags.CALORITE_PLATES).define('N', ModItems.NETHERITE_SPACE_HELMET.get()).define('G', Blocks.ORANGE_STAINED_GLASS)
                .pattern("#N#").pattern("#G#").group(null).unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.JET_SUIT.get()).define('#', ModTags.CALORITE_PLATES).define('B', ModTags.CALORITE_BLOCKS).define('N', ModItems.NETHERITE_SPACE_SUIT.get())
                .define('E', ModItems.CALORITE_ENGINE.get()).define('T', ModItems.CALORITE_TANK.get()).pattern("# #").pattern("TNT").pattern("BEB").group(null).unlockedBy("has_tag", has(ModTags.CALORITE_PLATES))
                .save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.JET_SUIT_PANTS.get()).define('#', ModTags.CALORITE_PLATES).define('N', ModItems.NETHERITE_SPACE_PANTS.get()).pattern("#N#").pattern("# #").pattern("# #").group(null)
                .unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.JET_SUIT_BOOTS.get()).define('#', ModTags.CALORITE_PLATES).define('B', ModTags.CALORITE_BLOCKS).define('N', ModItems.NETHERITE_SPACE_BOOTS.get())
                .pattern(" N ").pattern("# #").pattern("B B").group(null).unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        // Soul Torch
        ShapedRecipeBuilder.shaped(Items.SOUL_TORCH).define('#', ModItems.EXTINGUISHED_TORCH.get()).define('S', Items.SOUL_SOIL).pattern("S").pattern("#")
                .unlockedBy(getHasName(ModItems.EXTINGUISHED_TORCH.get()), has(ModItems.EXTINGUISHED_TORCH.get())).save(exporter);
        // Hammer
        ShapedRecipeBuilder.shaped(ModItems.HAMMER.get()).define('#', Items.IRON_INGOT).define('|', Items.STICK).pattern(" # ").pattern(" |#").pattern("|  ").group(null)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(exporter);

        // Iron Rod
        ShapedRecipeBuilder.shaped(ModItems.IRON_ROD.get(), 4).define('#', ModTags.IRON_PLATES).pattern("#").pattern("#").group("iron_rods").unlockedBy("has_tag", has(ModTags.IRON_PLATES)).save(exporter);

        // Oxygen Gear
        ShapedRecipeBuilder.shaped(ModItems.OXYGEN_GEAR.get()).define('#', ModTags.STEEL_PLATES).define('|', ModTags.IRON_RODS).pattern(" | ").pattern("#|#").pattern("#|#").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Wheel
        ShapedRecipeBuilder.shaped(ModItems.WHEEL.get()).define('#', ModTags.STEEL_PLATES).define('B', Items.BLACK_DYE).pattern(" B ").pattern("B#B").pattern(" B ").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Engine Frame
        ShapedRecipeBuilder.shaped(ModItems.ENGINE_FRAME.get()).define('#', ModTags.STEEL_PLATES).define('|', ModTags.IRON_RODS).pattern("|||").pattern("|#|").pattern("|||").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Engine Fan
        ShapedRecipeBuilder.shaped(ModItems.ENGINE_FAN.get()).define('#', ModTags.STEEL_PLATES).define('|', ModTags.IRON_RODS).pattern(" | ").pattern("|#|").pattern(" | ").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Nose Cone
        ShapedRecipeBuilder.shaped(ModItems.ROCKET_NOSE_CONE.get()).define('#', ModTags.STEEL_PLATES).define('|', Items.LIGHTNING_ROD).pattern(" | ").pattern(" # ").pattern("###").group(null)
                .unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        // Engines
        ShapedRecipeBuilder.shaped(ModItems.STEEL_ENGINE.get()).define('#', ModTags.STEEL_PLATES).define('E', ModItems.ENGINE_FRAME.get()).define('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
                .pattern(" F ").group("engines").unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.DESH_ENGINE.get()).define('#', ModTags.DESH_PLATES).define('E', ModItems.ENGINE_FRAME.get()).define('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
                .pattern(" F ").group("engines").unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.OSTRUM_ENGINE.get()).define('#', ModTags.OSTRUM_PLATES).define('E', ModItems.ENGINE_FRAME.get()).define('F', ModItems.ENGINE_FAN.get()).pattern("###").pattern(" E ")
                .pattern(" F ").group("engines").unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.CALORITE_ENGINE.get()).define('#', ModTags.CALORITE_PLATES).define('E', ModItems.ENGINE_FRAME.get()).define('F', ModItems.ENGINE_FAN.get()).pattern("###")
                .pattern(" E ").pattern(" F ").group("engines").unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        // Tanks
        ShapedRecipeBuilder.shaped(ModItems.STEEL_TANK.get()).define('#', ModTags.STEEL_PLATES).define('|', ModTags.IRON_RODS).define('U', Items.BUCKET).pattern("## ").pattern("#U|")
                .pattern("## ").group("tanks").unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.DESH_TANK.get()).define('#', ModTags.DESH_PLATES).define('|', ModTags.IRON_RODS).define('U', Items.BUCKET).pattern("## ").pattern("#U|")
                .pattern("## ").group("tanks").unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.OSTRUM_TANK.get()).define('#', ModTags.OSTRUM_PLATES).define('|', ModTags.IRON_RODS).define('U', Items.BUCKET).pattern("## ").pattern("#U|")
                .pattern("## ").group("tanks").unlockedBy("has_tag", has(ModTags.OSTRUM_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.CALORITE_TANK.get()).define('#', ModTags.CALORITE_PLATES).define('|', ModTags.IRON_RODS).define('U', Items.BUCKET).pattern("## ").pattern("#U|")
                .pattern("## ").group("tanks").unlockedBy("has_tag", has(ModTags.CALORITE_PLATES)).save(exporter);

        ShapedRecipeBuilder.shaped(ModItems.WRENCH.get()).define('#', ModTags.DESH_PLATES).pattern("# #").pattern(" # ").pattern(" # ").group(null).unlockedBy("has_tag", has(ModTags.DESH_PLATES)).save(exporter);

        // Fin
        ShapedRecipeBuilder.shaped(ModItems.ROCKET_FIN.get(), 4).define('#', ModTags.STEEL_PLATES).pattern(" # ").pattern("###").pattern("# #").group(null).unlockedBy("has_tag", has(ModTags.STEEL_PLATES)).save(exporter);

    }

    public void offerCustomStairsRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        stairBuilder(output, Ingredient.of(input)).unlockedBy(getHasName(input), has(input)).save(exporter);
        stonecutterResultFromBase(exporter, output, input);
    }

    public void offerBlastingRecipe(Consumer<FinishedRecipe> exporter, ItemLike input, ItemLike output) {
        offerBlastingRecipe(exporter, List.of(input), output);
    }

    public void offerBlastingRecipe(Consumer<FinishedRecipe> exporter, List<ItemLike> inputs, ItemLike output) {
        oreBlasting(exporter, inputs, output, 1.0f, 100, null);
        oreSmelting(exporter, inputs, output, 1.0f, 200, null);
    }

    public void offerCrackedBricksSmeltingRecipe(Consumer<FinishedRecipe> exporter, List<ItemLike> inputs, ItemLike output) {
        oreSmelting(exporter, inputs, output, 0.2f, 200, null);
    }

    public void offerPillarRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(output, 2).define('#', input).pattern("#").pattern("#").unlockedBy(getHasName(input), has(input)).save(exporter);
        stonecutterResultFromBase(exporter, output, input);
    }

    public void offerFlagRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        ShapedRecipeBuilder.shaped(output).define('#', input).define('|', ModTags.IRON_RODS).pattern("|##").pattern("|##").pattern("|  ").group("flag").unlockedBy(getHasName(input), has(input))
                .save(exporter);
    }

    public void offerButtonRecipe(Consumer<FinishedRecipe> exporter, ItemLike output, ItemLike input) {
        ShapelessRecipeBuilder.shapeless(output).requires(input).group("button").unlockedBy(getHasName(input), has(input)).save(exporter);
    }
}