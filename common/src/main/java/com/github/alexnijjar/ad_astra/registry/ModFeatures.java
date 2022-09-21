package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.world.features.InfernalSpireColumn;
import com.github.alexnijjar.ad_astra.world.features.ModifiedBlockBlobFeature;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

public class ModFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(AdAstra.MOD_ID, Registry.FEATURE_KEY);

	public static void register() {
		FEATURES.register("infernal_spire_column", () -> new InfernalSpireColumn(BasaltColumnsFeatureConfig.CODEC));
		FEATURES.register("modified_block_blob", () -> new ModifiedBlockBlobFeature(SingleStateFeatureConfig.CODEC));
		FEATURES.register();
	}
}