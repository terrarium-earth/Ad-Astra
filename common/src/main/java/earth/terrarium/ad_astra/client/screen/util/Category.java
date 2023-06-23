package earth.terrarium.ad_astra.client.screen.util;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.resources.ResourceLocation;

public record Category(ResourceLocation id, Category parent) {

    public static final Category BACK = new Category(new ResourceLocation(AdAstra.MOD_ID, "back"), null);

    public static final Category GALAXY_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "galaxy"), null);
    public static final Category MILKY_WAY_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "milky_way"), GALAXY_CATEGORY);
    public static final Category SOLAR_SYSTEM_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "solar_system"), MILKY_WAY_CATEGORY);
    public static final Category EARTH_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "earth"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MARS_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "mars"), SOLAR_SYSTEM_CATEGORY);
    public static final Category VENUS_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "venus"), SOLAR_SYSTEM_CATEGORY);
    public static final Category MERCURY_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "mercury"), SOLAR_SYSTEM_CATEGORY);
    public static final Category PROXIMA_CENTAURI_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "proxima_centauri"), MILKY_WAY_CATEGORY);
    public static final Category GLACIO_CATEGORY = new Category(new ResourceLocation(AdAstra.MOD_ID, "glacio"), PROXIMA_CENTAURI_CATEGORY);
}