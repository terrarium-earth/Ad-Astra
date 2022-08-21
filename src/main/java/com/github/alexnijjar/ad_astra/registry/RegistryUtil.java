package com.github.alexnijjar.beyond_earth.registry;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

public class RegistryUtil {

    public static <T> RegistryEntry<T> getEntry(Registry<T> registry, T value) {
        return registry.getEntry(registry.getKey(value).orElseThrow()).orElseThrow();
    }

    public static Predicate<BiomeSelectionContext> foundIn(RegistryKey<DimensionOptions> dimension) {
        return context -> context.canGenerateIn(dimension);
    }
}