package com.github.alexnijjar.beyond_earth.client.resource_pack;

import com.github.alexnijjar.beyond_earth.data.ButtonColour;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record Galaxy(Identifier galaxy, Identifier texture, ButtonColour buttonColour, int scale) {
}