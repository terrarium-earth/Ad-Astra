package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.world.feature.InfernalSpireColumn;
import earth.terrarium.ad_astra.common.world.feature.ModifiedBlockBlobFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

import java.util.function.Supplier;

public class ModFeatures {
    public static final ResourcefulRegistry<Feature<?>> FEATURES = ResourcefulRegistries.create(BuiltInRegistries.FEATURE, AdAstra.MOD_ID);

    public static final Supplier<Feature<BlockStateConfiguration>> MODIFIED_BLOCK_BLOB = FEATURES.register("modified_block_blob", () -> new ModifiedBlockBlobFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<Feature<ColumnFeatureConfiguration>> INFERNAL_SPIRE_COLUMN = FEATURES.register("infernal_spire_column", () -> new InfernalSpireColumn(ColumnFeatureConfiguration.CODEC));
}