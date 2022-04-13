package net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class FoggyDimensionEffects extends DimensionEffects {

    public FoggyDimensionEffects() {
        super(192, true, SkyType.NORMAL, false, true);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply(sunHeight * 0.94f + 0.06f, sunHeight * 0.94f + 0.06f, sunHeight * 0.91f + 0.09f);
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return true;
    }
}
