package com.github.alexnijjar.ad_astra.registry;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionOptions;

public class RegistryUtil {

    public static <T> Holder<T> getEntry(Registry<T> registry, T value) {
        return registry.getHolder(registry.getKey(value).orElseThrow()).orElseThrow();
    }

    public static Predicate<BiomeSelectionContext> foundIn(RegistryKey<DimensionOptions> dimension) {
        return context -> context.canGenerateIn(dimension);
    }
}