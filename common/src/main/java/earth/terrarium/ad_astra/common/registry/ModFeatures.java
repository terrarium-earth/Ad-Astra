package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;

public class ModFeatures {
    public static final ResourcefulRegistry<Feature<?>> FEATURES = ResourcefulRegistries.create(BuiltInRegistries.FEATURE, AdAstra.MOD_ID);
}