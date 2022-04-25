package net.mrscauthd.beyond_earth.data;

import java.util.List;

import net.minecraft.util.Identifier;

public record SolarSystem(Identifier galaxy, Identifier solarSystem, SunType sunType, List<Identifier> planetaryRings) {

    public enum SunType {
        SUN, BLUE_SUN, RED_SUN
    }
}