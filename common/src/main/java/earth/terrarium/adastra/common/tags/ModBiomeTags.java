package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ModBiomeTags {
    public static final TagKey<Biome> HAS_ACID_RAIN = tag("has_acid_rain");

    public static final TagKey<Biome> OIL_WELL = tag("has_structure/oil_well");
    public static final TagKey<Biome> LUNARIAN_VILLAGE = tag("has_structure/lunarian_village");
    public static final TagKey<Biome> MOON_DUNGEON = tag("has_structure/moon_dungeon");
    public static final TagKey<Biome> MARS_TEMPLE = tag("has_structure/mars_temple");
    public static final TagKey<Biome> PYGRO_TOWER = tag("has_structure/pygro_tower");
    public static final TagKey<Biome> PYGRO_VILLAGE = tag("has_structure/pygro_village");
    public static final TagKey<Biome> VENUS_BULLET = tag("has_structure/venus_bullet");

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
