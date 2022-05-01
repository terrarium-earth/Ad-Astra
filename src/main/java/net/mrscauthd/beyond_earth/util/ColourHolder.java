package net.mrscauthd.beyond_earth.util;

import net.minecraft.util.math.Vec3d;

public record ColourHolder(float r, float g, float b) {

    public static Vec3d toVector(ColourHolder colour) {
        return new Vec3d(colour.r(), colour.g(), colour.b());
    }
}