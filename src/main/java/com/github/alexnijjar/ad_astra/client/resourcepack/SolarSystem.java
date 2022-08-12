package com.github.alexnijjar.ad_astra.client.resourcepack;

import com.github.alexnijjar.ad_astra.data.ButtonColour;
import com.github.alexnijjar.ad_astra.util.ColourHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record SolarSystem(Identifier galaxy, Identifier solarSystem, Identifier sun, int sunScale, ButtonColour buttonColour, ColourHolder ringColour) {
}