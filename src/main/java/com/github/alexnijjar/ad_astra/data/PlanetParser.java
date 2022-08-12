package com.github.alexnijjar.ad_astra.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class PlanetParser {

	public static Planet parse(JsonObject jsonObject) {

		String translation = jsonObject.get("translation").getAsString();
		Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
		Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
		RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("world").getAsString()));
		RegistryKey<World> orbitDimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("orbit_world").getAsString()));
		JsonElement optional = jsonObject.get("parent_world");
		RegistryKey<World> parentWorld = optional.isJsonNull() ? null : RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("parent_world").getAsString()));
		int rocketTier = jsonObject.get("rocket_tier").getAsInt();
		float gravity = jsonObject.get("gravity").getAsFloat();
		int daysInYear = jsonObject.get("days_in_year").getAsInt();
		float temperature = jsonObject.get("temperature").getAsFloat();
		boolean hasOxygen = jsonObject.get("has_oxygen").getAsBoolean();
		ButtonColour buttonColour = ButtonColour.stringToColour(jsonObject.get("button_color").getAsString());

		return new Planet(translation, galaxy, solarSystem, dimension, orbitDimension, parentWorld, rocketTier, gravity, daysInYear, temperature, hasOxygen, buttonColour);
	}
}
