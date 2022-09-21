package com.github.alexnijjar.ad_astra.client.resourcepack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record PlanetRing(Identifier galaxy, Identifier solarSystem, Identifier texture, int speed, int scale,
						 double radius) {
}