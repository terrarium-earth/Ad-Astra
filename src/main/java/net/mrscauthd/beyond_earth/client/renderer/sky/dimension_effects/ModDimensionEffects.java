package net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ModDimensionEffects extends DimensionEffects {

    public ModDimensionEffects() {
        super(192, true, DimensionEffects.SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color;
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }
}