package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.util.ModIdentifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class ModBiomes {
    public static final RegistryKey<Biome> GLACIO_SNOWY_BARRENS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("glacio_snowy_barrens"));
    public static final RegistryKey<Biome> GLACIO_ICE_PEAKS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("glacio_ice_peaks"));
    public static final RegistryKey<Biome> INFERNAL_VENUS_BARRENS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("infernal_venus_barrens"));
    public static final RegistryKey<Biome> MARTIAN_WASTELANDS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("martian_wastelands"));
    public static final RegistryKey<Biome> MARTIAN_POLAR_CAPS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("martian_polar_caps"));
    public static final RegistryKey<Biome> MARTIAN_CANYON_CREEK = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("martian_canyon_creek"));
    public static final RegistryKey<Biome> MERCURY_DELTAS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("mercury_deltas"));
    public static final RegistryKey<Biome> LUNAR_WASTELANDS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("lunar_wastelands"));
    public static final RegistryKey<Biome> ORBIT = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("orbit"));
    public static final RegistryKey<Biome> VENUS_WASTELANDS = RegistryKey.of(Registry.BIOME_KEY, new ModIdentifier("venus_wastelands"));
}