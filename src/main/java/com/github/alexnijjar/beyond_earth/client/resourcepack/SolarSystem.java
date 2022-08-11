package com.github.alexnijjar.beyond_earth.client.resourcepack;

import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.util.ColourHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record SolarSystem(Identifier galaxy, Identifier solarSystem, Identifier sun, int sunScale, ButtonColour buttonColour, ColourHolder ringColour) {
}