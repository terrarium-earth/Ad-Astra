package net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class HorizonDimensionEffects extends ModDimensionEffects {

    Vec3d colour;

    public HorizonDimensionEffects(Vec3d colour) {
        this.colour = colour;
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return this.colour;
    }
}