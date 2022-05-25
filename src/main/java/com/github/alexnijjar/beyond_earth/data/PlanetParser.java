package com.github.alexnijjar.beyond_earth.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class PlanetParser {

    public static Planet parse(JsonObject jsonObject) {

        String name = jsonObject.get("name").getAsString();
        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
        RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("dimension").getAsString()));
        RegistryKey<World> orbitDimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("orbit_dimension").getAsString()));
        JsonElement optional = jsonObject.get("parent_dimension");
        RegistryKey<World> parentDimension = optional.isJsonNull() ? null : RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("parent_dimension").getAsString()));
        int rocketTier = jsonObject.get("rocket_tier").getAsInt();
        float gravity = jsonObject.get("gravity").getAsFloat();
        int daysInYear = jsonObject.get("days_in_year").getAsInt();
        float temperature = jsonObject.get("temperature").getAsFloat();
        boolean hasOxygen = jsonObject.get("has_oxygen").getAsBoolean();
        int atmosphereStart = jsonObject.get("atmosphere_start").getAsInt();
        ButtonColour buttonColour = ButtonColour.stringToColour(jsonObject.get("button_color").getAsString());

        return new Planet(name, galaxy, solarSystem, dimension, orbitDimension, parentDimension, rocketTier, gravity, daysInYear, temperature, hasOxygen, atmosphereStart, buttonColour);
    }
}
