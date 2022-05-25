package com.github.alexnijjar.beyond_earth.registry;

import java.util.List;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.world.features.MarsBlockBlobFeature;
import com.github.alexnijjar.beyond_earth.world.features.VenusDeltas;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

@SuppressWarnings("deprecation")
public class ModFeatures {

        public static final Identifier MARS_ROCK_ID = new ModIdentifier("mars_rock");
        public static final Identifier VENUS_DELTAS_ID = new ModIdentifier("venus_deltas");
        public static final Identifier VENUS_DELTAS_SMALL_ID = new ModIdentifier("venus_deltas_small");
        public static final Identifier VENUS_DELTAS_LARGE_ID = new ModIdentifier("venus_deltas_large");

        private static final Feature<SingleStateFeatureConfig> MARS_ROCK_FEATURE = new MarsBlockBlobFeature(SingleStateFeatureConfig.CODEC);
        public static final ConfiguredFeature<?, ?> MARS_ROCK_CONFIGURED_FEATURE = createFeatureConfiguredFeature(MARS_ROCK_ID, MARS_ROCK_FEATURE, new SingleStateFeatureConfig(Blocks.POLISHED_GRANITE.getDefaultState()));
        public static final PlacedFeature MARS_ROCK = new PlacedFeature(RegistryUtil.getEntry(BuiltinRegistries.CONFIGURED_FEATURE, MARS_ROCK_CONFIGURED_FEATURE),
                        List.of(CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()));

        private static final Feature<BasaltColumnsFeatureConfig> VENUS_DELTAS_FEATURE = new VenusDeltas(BasaltColumnsFeatureConfig.CODEC);

        // Small.
        public static final ConfiguredFeature<?, ?> VENUS_DELTAS_SMALL_CONFIGURED_FEATURE = createFeatureConfiguredFeature(VENUS_DELTAS_SMALL_ID, VENUS_DELTAS_FEATURE,
                        new BasaltColumnsFeatureConfig(ConstantIntProvider.create(1), UniformIntProvider.create(1, 4)));
        public static final PlacedFeature VENUS_DELTAS_SMALL = new PlacedFeature(RegistryUtil.getEntry(BuiltinRegistries.CONFIGURED_FEATURE, VENUS_DELTAS_SMALL_CONFIGURED_FEATURE),
                        List.of(CountMultilayerPlacementModifier.of(4), BiomePlacementModifier.of()));

        // Large.
        public static final ConfiguredFeature<?, ?> VENUS_DELTAS_LARGE_CONFIGURED_FEATURE = createFeatureConfiguredFeature(VENUS_DELTAS_LARGE_ID, VENUS_DELTAS_FEATURE,
                        new BasaltColumnsFeatureConfig(UniformIntProvider.create(2, 3), UniformIntProvider.create(5, 10)));
        public static final PlacedFeature VENUS_DELTAS_LARGE = new PlacedFeature(RegistryUtil.getEntry(BuiltinRegistries.CONFIGURED_FEATURE, VENUS_DELTAS_LARGE_CONFIGURED_FEATURE),
                        List.of(CountMultilayerPlacementModifier.of(2), BiomePlacementModifier.of()));

        public static void register() {
                Registry.register(Registry.FEATURE, MARS_ROCK_ID, MARS_ROCK_FEATURE);
                Registry.register(BuiltinRegistries.PLACED_FEATURE, MARS_ROCK_ID, MARS_ROCK);

                Registry.register(Registry.FEATURE, VENUS_DELTAS_ID, VENUS_DELTAS_FEATURE);
                Registry.register(BuiltinRegistries.PLACED_FEATURE, VENUS_DELTAS_SMALL_ID, VENUS_DELTAS_SMALL);
                Registry.register(BuiltinRegistries.PLACED_FEATURE, VENUS_DELTAS_LARGE_ID, VENUS_DELTAS_LARGE);

                // Add to biomes.
                BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.MARS_ROCKY_PLAINS), GenerationStep.Feature.SURFACE_STRUCTURES, BuiltinRegistries.PLACED_FEATURE.getKey(MARS_ROCK).get());
                BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFERNAL_VENUS_BARRENS), GenerationStep.Feature.SURFACE_STRUCTURES, BuiltinRegistries.PLACED_FEATURE.getKey(VENUS_DELTAS_SMALL).get());
                BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.INFERNAL_VENUS_BARRENS), GenerationStep.Feature.SURFACE_STRUCTURES, BuiltinRegistries.PLACED_FEATURE.getKey(VENUS_DELTAS_LARGE).get());
        }

        public static <FC extends FeatureConfig, F extends Feature<FC>> ConfiguredFeature<?, ?> createFeatureConfiguredFeature(Identifier id, F feature, FC config) {
                ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, config);
                Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configured);
                return configured;
        }
}