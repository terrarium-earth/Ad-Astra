package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.world.features.InfernalSpireColumn;
import earth.terrarium.ad_astra.world.features.ModifiedBlockBlobFeature;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(AdAstra.MOD_ID, Registry.FEATURE_REGISTRY);

    public static void register() {
        FEATURES.register("infernal_spire_column", () -> new InfernalSpireColumn(ColumnFeatureConfiguration.CODEC));
        FEATURES.register("modified_block_blob", () -> new ModifiedBlockBlobFeature(BlockStateConfiguration.CODEC));
        FEATURES.register();
    }
}