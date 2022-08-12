package com.github.alexnijjar.ad_astra.client.resourcepack;

import com.github.alexnijjar.ad_astra.data.ButtonColour;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record Galaxy(Identifier galaxy, Identifier texture, ButtonColour buttonColour, int scale) {
}