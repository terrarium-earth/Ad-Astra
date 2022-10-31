package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    public static final ResourceKey<Biome> GLACIO_SNOWY_BARRENS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("glacio_snowy_barrens"));
    public static final ResourceKey<Biome> GLACIO_ICE_PEAKS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("glacio_ice_peaks"));
    public static final ResourceKey<Biome> INFERNAL_VENUS_BARRENS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("infernal_venus_barrens"));
    public static final ResourceKey<Biome> MARTIAN_WASTELANDS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("martian_wastelands"));
    public static final ResourceKey<Biome> MARTIAN_POLAR_CAPS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("martian_polar_caps"));
    public static final ResourceKey<Biome> MARTIAN_CANYON_CREEK = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("martian_canyon_creek"));
    public static final ResourceKey<Biome> MERCURY_DELTAS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("mercury_deltas"));
    public static final ResourceKey<Biome> LUNAR_WASTELANDS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("lunar_wastelands"));
    public static final ResourceKey<Biome> ORBIT = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("orbit"));
    public static final ResourceKey<Biome> VENUS_WASTELANDS = ResourceKey.create(Registry.BIOME_REGISTRY, new ModResourceLocation("venus_wastelands"));
}