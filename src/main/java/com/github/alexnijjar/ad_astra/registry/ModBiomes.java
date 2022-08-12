package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomes {

	public static final RegistryKey<Biome> GLACIO = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("glacio"));
	public static final RegistryKey<Biome> GLACIO_ICE_SPIKES = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("glacio_ice_spikes"));
	public static final RegistryKey<Biome> INFERNAL_VENUS_BARRENS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("infernal_venus_barrens"));
	public static final RegistryKey<Biome> MARTIAN_DESERT = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("martian_desert"));
	public static final RegistryKey<Biome> MARTIAN_POLAR_CAPS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("martian_polar_caps"));
	public static final RegistryKey<Biome> ROCKY_MARTIAN_PLAINS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("rocky_martian_plains"));
	public static final RegistryKey<Biome> MERCURY = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("mercury"));
	public static final RegistryKey<Biome> LUNAR_DESERT = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("lunar_desert"));
	public static final RegistryKey<Biome> ORBIT = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("orbit"));
	public static final RegistryKey<Biome> VENUS_DESERT = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("venus_desert"));
}