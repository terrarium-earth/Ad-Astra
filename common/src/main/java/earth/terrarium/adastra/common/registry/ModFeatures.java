package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.world.features.InfernalSpireColumnFeature;
import earth.terrarium.adastra.common.world.features.MarsBlockBlobFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

public class ModFeatures {
    public static final ResourcefulRegistry<Feature<?>> FEATURES = ResourcefulRegistries.create(BuiltInRegistries.FEATURE, AdAstra.MOD_ID);

    public static final RegistryEntry<Feature<ColumnFeatureConfiguration>> INFERNAL_SPIRE_COLUMN = FEATURES.register("infernal_spire_column", () -> new InfernalSpireColumnFeature(ColumnFeatureConfiguration.CODEC));
    public static final RegistryEntry<Feature<BlockStateConfiguration>> MARS_BLOCK_BLOB_FEATURE = FEATURES.register("mars_block_blob", () -> new MarsBlockBlobFeature(BlockStateConfiguration.CODEC));
}