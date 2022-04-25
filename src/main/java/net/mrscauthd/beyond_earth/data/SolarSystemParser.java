package net.mrscauthd.beyond_earth.data;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.util.Identifier;

public class SolarSystemParser {

    public static SolarSystem parse(JsonObject jsonObject) {

        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
        SolarSystem.SunType sunType = SolarSystem.SunType.valueOf(jsonObject.get("sun_type").getAsString().toUpperCase());

        List<Identifier> planetaryRings = new LinkedList<>();
        jsonObject.get("planetary_rings").getAsJsonArray().forEach(id -> planetaryRings.add(new Identifier(id.getAsString())));

        return new SolarSystem(galaxy, solarSystem, sunType, planetaryRings);
    }
}
