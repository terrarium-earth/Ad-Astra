package com.github.alexnijjar.ad_astra.registry;

import net.minecraft.util.Holder;
import net.minecraft.util.registry.Registry;

public class RegistryUtil {

	public static <T> Holder<T> getEntry(Registry<T> registry, T value) {
		return registry.getHolder(registry.getKey(value).orElseThrow()).orElseThrow();
	}
}