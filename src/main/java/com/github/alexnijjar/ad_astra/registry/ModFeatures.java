package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.world.features.InfernalSpireColumn;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ModFeatures {


        public static final Identifier INFERNAL_SPIRE_COLUMN_ID = new ModIdentifier("infernal_spire_column");
        private static final Feature<BasaltColumnsFeatureConfig> INFERNAL_SPIRE_COLUMN = new InfernalSpireColumn(BasaltColumnsFeatureConfig.CODEC);

        public static void register() {
                Registry.register(Registry.FEATURE, INFERNAL_SPIRE_COLUMN_ID, INFERNAL_SPIRE_COLUMN);
        }

        public static <FC extends FeatureConfig, F extends Feature<FC>> ConfiguredFeature<?, ?> createFeatureConfiguredFeature(Identifier id, F feature, FC config) {
                ConfiguredFeature<?, ?> configured = new ConfiguredFeature<>(feature, config);
                Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configured);
                return configured;
        }
}