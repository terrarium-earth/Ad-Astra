package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.level.feature.InfernalSpireColumn;
import earth.terrarium.ad_astra.level.feature.ModifiedBlockBlobFeature;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.function.Supplier;

public class ModFeatures {

    public static final Supplier<Feature<BlockStateConfiguration>> MODIFIED_BLOCK_BLOB = register("modified_block_blob", () -> new ModifiedBlockBlobFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<Feature<ColumnFeatureConfiguration>> INFERNAL_SPIRE_COLUMN = register("infernal_spire_column", () -> new InfernalSpireColumn(ColumnFeatureConfiguration.CODEC));

    private static <T extends Feature<FC>, FC extends FeatureConfiguration> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.FEATURE, id, object);
    }

    public static void init() {
    }
}