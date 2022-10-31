package earth.terrarium.ad_astra.client.screens.utils;

import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record Category(ResourceLocation id, Category parent) {

    public static final Category BACK = new Category(new ModResourceLocation("back"), null);

    public static final Category GALAXY_CATEGORY = new Category(new ModResourceLocation("galaxy"), null);
    public static final Category MILKY_WAY_CATEGORY = new Category(new ModResourceLocation("milky_way"), GALAXY_CATEGORY);
    public static final Category SOLAR_SYSTEM_CATEGORY = new Category(new ModResourceLocation("solar_system"), MILKY_WAY_CATEGORY);
    public static final Category EARTH_CATEGORY = new Category(new ModResourceLocation("earth"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MARS_CATEGORY = new Category(new ModResourceLocation("mars"), SOLAR_SYSTEM_CATEGORY);
    public static final Category VENUS_CATEGORY = new Category(new ModResourceLocation("venus"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MERCURY_CATEGORY = new Category(new ModResourceLocation("mercury"), SOLAR_SYSTEM_CATEGORY);
    public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(new ModResourceLocation("proxima_centauri"), MILKY_WAY_CATEGORY);
    public static final Category GLACIO_CATEGORY = new Category(new ModResourceLocation("glacio"), PROXIMA_CENTAURI_CATEGORY);
}