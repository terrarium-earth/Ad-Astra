package net.mrscauthd.beyond_earth.data;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public record Planet(String name, Identifier galaxy, Identifier solarSystem, RegistryKey<World> dimension, RegistryKey<World> orbitDimension,  RegistryKey<World> parentDimension, int rocketTier, float gravity, int daysInYear, float temperature,
                boolean oxygen, int atmosphereStart, ButtonColour buttonColour) {
}