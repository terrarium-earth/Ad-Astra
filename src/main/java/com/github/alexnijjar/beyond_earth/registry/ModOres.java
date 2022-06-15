package com.github.alexnijjar.beyond_earth.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ModOres {

        public static final RegistryKey<DimensionOptions> MOON_TAG = createKey("moon");
        public static final RegistryKey<DimensionOptions> MARS_TAG = createKey("mars");
        public static final RegistryKey<DimensionOptions> MERCURY_TAG = createKey("mercury");
        public static final RegistryKey<DimensionOptions> VENUS_TAG = createKey("venus");
        public static final RegistryKey<DimensionOptions> GLACIO_TAG = createKey("glacio");

        public static final RuleTest MOON_MATCH = createTest("moon_ore_replaceables");
        public static final RuleTest MARS_MATCH = createTest("mars_ore_replaceables");
        public static final RuleTest MERCURY_MATCH = createTest("mercury_ore_replaceables");
        public static final RuleTest VENUS_MATCH = createTest("venus_ore_replaceables");
        public static final RuleTest GLACIO_MATCH = createTest("glacio_ore_replaceables");

        public static void register() {

                register("moon_cheese_ore", ModBlocks.MOON_CHEESE_ORE, MOON_MATCH, MOON_TAG, 10, 20, -80, 192);
                register("moon_soul_soil", Blocks.SOUL_SOIL, MOON_MATCH, MOON_TAG, 60, 20, 0, 100);
                register("moon_ice_shard_ore", ModBlocks.MOON_ICE_SHARD_ORE, MOON_MATCH, MOON_TAG, 10, 8, -32, 32);
                register("moon_iron_ore", ModBlocks.MOON_IRON_ORE, MOON_MATCH, MOON_TAG, 11, 10, -24, 56);
                register("moon_desh_ore", ModBlocks.MOON_DESH_ORE, MOON_MATCH, MOON_TAG, 9, 7, -80, 80);

                register("mars_ice_shard_ore", ModBlocks.MARS_ICE_SHARD_ORE, MARS_MATCH, MARS_TAG, 10, 8, -32, 32);
                register("mars_iron_ore", ModBlocks.MARS_IRON_ORE, MARS_MATCH, MARS_TAG, 11, 10, -24, 56);
                register("mars_diamond_ore", ModBlocks.MARS_DIAMOND_ORE, MARS_MATCH, MARS_TAG, 7, 7, -80, 80);
                register("mars_ostrum_ore", ModBlocks.MARS_OSTRUM_ORE, MARS_MATCH, MARS_TAG, 8, 6, -80, 80);

                register("mercury_iron_ore", ModBlocks.MERCURY_IRON_ORE, MERCURY_MATCH, MERCURY_TAG, 8, 20, -80, 192);

                register("venus_coal_ore", ModBlocks.VENUS_COAL_ORE, VENUS_MATCH, VENUS_TAG, 17, 20, -80, 192);
                register("venus_gold_ore", ModBlocks.VENUS_GOLD_ORE, VENUS_MATCH, VENUS_TAG, 10, 4, -64, 32);
                register("venus_diamond_ore", ModBlocks.VENUS_DIAMOND_ORE, VENUS_MATCH, VENUS_TAG, 9, 7, -80, 80);
                register("venus_calorite_ore", ModBlocks.VENUS_CALORITE_ORE, VENUS_MATCH, VENUS_TAG, 8, 6, -80, 80);

                register("glacio_ice_shard_ore", ModBlocks.GLACIO_ICE_SHARD_ORE, GLACIO_MATCH, GLACIO_TAG, 10, 8, -32, 32);
                register("glacio_coal_ore", ModBlocks.GLACIO_COAL_ORE, GLACIO_MATCH, GLACIO_TAG, 17, 20, -80, 192);
                register("glacio_copper_ore", ModBlocks.GLACIO_COPPER_ORE, GLACIO_MATCH, GLACIO_TAG, 17, 16, -16, 112);
                register("glacio_iron_ore", ModBlocks.GLACIO_IRON_ORE, GLACIO_MATCH, GLACIO_TAG, 11, 10, -24, 56);
                register("glacio_lapis_ore", ModBlocks.GLACIO_LAPIS_ORE, GLACIO_MATCH, GLACIO_TAG, 9, 2, -32, 32);

                register("deepslate_coal_ore", Blocks.DEEPSLATE_COAL_ORE, OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GLACIO_TAG, 17, 20, -80, 192);
                register("deepslate_copper_ore", Blocks.DEEPSLATE_COPPER_ORE, OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GLACIO_TAG, 17, 16, -80, 112);
                register("deepslate_iron_ore", Blocks.DEEPSLATE_IRON_ORE, OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GLACIO_TAG, 12, 10, -80, 20);
                register("deepslate_lapis_ore", Blocks.DEEPSLATE_LAPIS_ORE, OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, GLACIO_TAG, 9, 2, -80, 10);
        }

        private static void register(String id, Block block, RuleTest test, RegistryKey<DimensionOptions> dimension, int veinSize, int veinsPerChunk, int min, int max) {
                Identifier newId = new ModIdentifier(id);
                ConfiguredFeature<?, ?> configured = createOreConfiguredFeature(newId, block, test, veinSize);
                PlacedFeature feature = createOreFeature(configured, veinsPerChunk, min, max);

                registerOre(newId, feature, RegistryUtil.foundIn(dimension));
        }

        public static void registerOre(Identifier id, PlacedFeature feature, Predicate<BiomeSelectionContext> biomes) {
                Registry.register(BuiltinRegistries.PLACED_FEATURE, id, feature);
                BiomeModifications.addFeature(biomes, GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, id));
        }

        public static ConfiguredFeature<?, ?> createOreConfiguredFeature(Identifier id, Block block, RuleTest test, int veinSize) {
                ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(test, block.getDefaultState(), veinSize));
                Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configured);
                return configured;
        }

        public static PlacedFeature createOreFeature(ConfiguredFeature<?, ?> configured, int veinsPerChunk, int min, int max) {

                List<PlacementModifier> placementModifiers = Arrays.asList(CountPlacementModifier.of(veinsPerChunk), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(min), YOffset.fixed(max)));

                return new PlacedFeature(RegistryUtil.getEntry(BuiltinRegistries.CONFIGURED_FEATURE, configured), placementModifiers);
        }

        public static RuleTest createTest(String id) {
                TagKey<Block> ore = TagKey.of(Registry.BLOCK_KEY, new ModIdentifier(id));
                return new TagMatchRuleTest(ore);
        }

        public static RegistryKey<DimensionOptions> createKey(String id) {
                return RegistryKey.of(Registry.DIMENSION_KEY, new ModIdentifier(id));
        }
}