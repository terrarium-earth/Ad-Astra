package net.mrscauthd.beyond_earth.world.configurator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class JigsawConfigurator extends StructurePoolFeatureConfig {
    public JigsawConfigurator(RegistryEntry<StructurePool> startPool, int size) {
        super(startPool, size);
    }

    // Increases the max start pool size to 30 instead of 7.
    public static final Codec<StructurePoolFeatureConfig> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(StructurePoolFeatureConfig::getStartPool),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(StructurePoolFeatureConfig::getSize)).apply(instance, StructurePoolFeatureConfig::new));
}