package net.mrscauthd.beyond_earth.data;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.Identifier;

public record SolarSystem(Identifier galaxy, Identifier solarSystem, SolarSystem.SunType sunType, List<Pair<Identifier, Double>> planetaryRings) {

    public enum SunType {
        SUN, BLUE_SUN, RED_SUN
    }
}