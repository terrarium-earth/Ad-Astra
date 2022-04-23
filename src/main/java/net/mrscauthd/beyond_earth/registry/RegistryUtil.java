package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

import java.util.function.Predicate;

public class RegistryUtil {

    public static <T> RegistryEntry<T> getEntry(Registry<T> registry, T value) {
        return registry.getEntry(registry.getKey(value).orElseThrow()).orElseThrow();
    }

    public static Predicate<BiomeSelectionContext> foundIn(RegistryKey<DimensionOptions> dimension) {
        return context -> context.canGenerateIn(dimension);
    }
}