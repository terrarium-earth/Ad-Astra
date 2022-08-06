package com.github.alexnijjar.beyond_earth.client.resource_pack;

import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PlanetRingParser {

    public static PlanetRing parse(JsonObject jsonObject) {

        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
        Identifier texture = new Identifier(jsonObject.get("texture").getAsString());
        int speed = jsonObject.get("speed").getAsInt();
        int scale = jsonObject.get("scale").getAsInt();
        double radius = jsonObject.get("radius").getAsDouble();

        return new PlanetRing(galaxy, solarSystem, texture, speed, scale, radius);
    }
}
