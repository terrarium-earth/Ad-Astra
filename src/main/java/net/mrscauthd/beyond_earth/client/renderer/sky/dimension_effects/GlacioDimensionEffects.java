package net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects;

import net.minecraft.util.math.Vec3d;

public class GlacioDimensionEffects extends ModDimensionEffects {

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply(sunHeight * 0.94 + 0.06, sunHeight * 0.94 + 0.06, sunHeight * 0.91 + 0.09);
    }
}