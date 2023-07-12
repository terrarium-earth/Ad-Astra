package earth.terrarium.adastra.common.registry;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.world.biome.CratersBiomeSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.BiomeSource;

public class ModBiomeSources {
    public static final ResourcefulRegistry<Codec<? extends BiomeSource>> BIOME_SOURCES = ResourcefulRegistries.create(BuiltInRegistries.BIOME_SOURCE, AdAstra.MOD_ID);

    public static final RegistryEntry<Codec<CratersBiomeSource>> CRATERS = BIOME_SOURCES.register("craters", () -> CratersBiomeSource.CODEC);
}
