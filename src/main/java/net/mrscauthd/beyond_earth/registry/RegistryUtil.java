package net.mrscauthd.beyond_earth.registry;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

public class RegistryUtil {

    public static <T> RegistryEntry<T> getEntry(Registry<T> registry, T value) {
        return registry.getEntry(registry.getKey(value).orElseThrow()).orElseThrow();
    }
}
