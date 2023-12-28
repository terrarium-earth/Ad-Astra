package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.mixins.client.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class DimensionUtils {
    public static final ResourceLocation BACKLIGHT = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/backlight.png");

    public static final ResourceLocation SUN = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/sun.png");
    public static final ResourceLocation BLUE_SUN = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/blue_sun.png");
    public static final ResourceLocation RED_SUN = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/red_sun.png");

    public static final ResourceLocation EARTH = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png");
    public static final ResourceLocation MOON = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/moon.png");
    public static final ResourceLocation MARS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mars.png");
    public static final ResourceLocation VENUS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/venus.png");
    public static final ResourceLocation MERCURY = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mercury.png");
    public static final ResourceLocation GLACIO = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/glacio.png");

    public static final ResourceLocation PHOBOS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/phobos.png");
    public static final ResourceLocation DEIMOS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/deimos.png");
    public static final ResourceLocation VICINUS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/vicinus.png");

    public static final ResourceLocation ACID_RAIN = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/acid_rain.png");
    public static final ResourceLocation VENUS_CLOUDS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/venus_clouds.png");

    public static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
