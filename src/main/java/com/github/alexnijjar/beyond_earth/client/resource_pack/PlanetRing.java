package com.github.alexnijjar.beyond_earth.client.resource_pack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record PlanetRing(Identifier galaxy, Identifier solarSystem, Identifier texture, int speed, int scale, double radius) {
}