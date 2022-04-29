package net.mrscauthd.beyond_earth.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.Identifier;

public class SolarSystemParser {

    public static SolarSystem parse(JsonObject jsonObject) {

        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
        SolarSystem.SunType sunType = SolarSystem.SunType.valueOf(jsonObject.get("sun_type").getAsString().toUpperCase());

        List<Pair<Identifier, Double>> planetaryRings = new LinkedList<>();
        jsonObject.get("planetary_rings").getAsJsonArray().forEach(ring -> {
            Identifier planet = new Identifier(ring.getAsJsonObject().get("planet").getAsString());
            double radius = ring.getAsJsonObject().get("radius").getAsDouble();
            planetaryRings.add(Pair.of(planet, radius));
        });

        return new SolarSystem(galaxy, solarSystem, sunType, planetaryRings);
    }
}
