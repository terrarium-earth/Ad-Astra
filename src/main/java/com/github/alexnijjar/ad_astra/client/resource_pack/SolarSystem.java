package com.github.alexnijjar.beyond_earth.client.resource_pack;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record SolarSystem(Identifier galaxy, Identifier solarSystem, SolarSystem.SunType sunType, List<Pair<Identifier, Double>> planetaryRings) {

    public enum SunType {
        SUN, BLUE_SUN, RED_SUN
    }
}