package net.mrscauthd.beyond_earth.util;

import net.minecraft.entity.Entity;

public class GravityUtil {

    // Source: https://nssdc.gsfc.nasa.gov/planetary/factsheet/planet_table_ratio.html
    public static final float EARTH_GRAVITY = 1.0f;
    public static final float MOON_GRAVITY = 0.166f;
    public static final float MARS_GRAVITY = 0.377f;
    public static final float VENUS_GRAVITY = 0.907f;
    public static final float MERCURY_GRAVITY = 0.378f;
    public static final float GLACIO_GRAVITY = 1.0f;

    public static final float ORBIT_GRAVITY = 0.5f;

    public static double getMixinGravity(double value, Object mixin) {
        Entity entity = (Entity) mixin;
        return value * ModUtils.getPlanetGravity(entity.world.getRegistryKey());
    }

    public static float getMixinGravity(float value, Object mixin) {
        return (float) GravityUtil.getMixinGravity((double) value, mixin);
    }
}
