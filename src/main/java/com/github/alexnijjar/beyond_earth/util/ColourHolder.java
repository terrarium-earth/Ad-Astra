package com.github.alexnijjar.beyond_earth.util;

import net.minecraft.util.math.Vec3d;

public record ColourHolder(float r, float g, float b, float a) {

    public static final ColourHolder WHITE = new ColourHolder(1.0f, 1.0f, 1.0f, 1.0f);

    public static Vec3d toVector(ColourHolder colour) {
        return new Vec3d(colour.r(), colour.g(), colour.b());
    }

    public static ColourHolder fromVector(Vec3d colour) {
        return new ColourHolder((float) colour.getX(), (float) colour.getY(), (float) colour.getZ(), 1.0f);
    }
}