package com.github.alexnijjar.beyond_earth.util;

public class MathUtil {

    // Thanks Unity for idea.
    public static float invLerp(float delta, float start, float end) {
        return (delta - end) / (start - end);
    }
}
