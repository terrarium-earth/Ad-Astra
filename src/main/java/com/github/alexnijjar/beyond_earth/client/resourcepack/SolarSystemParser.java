package com.github.alexnijjar.beyond_earth.client.resourcepack;

import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.util.ColourHolder;
import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SolarSystemParser {

    public static SolarSystem parse(JsonObject jsonObject) {

        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier solarSystem = new Identifier(jsonObject.get("solar_system").getAsString());
        Identifier sun = new Identifier(jsonObject.get("sun").getAsString());
        int sunScale = jsonObject.get("sun_scale").getAsInt();
        ButtonColour buttonColour = ButtonColour.stringToColour(jsonObject.get("button_color").getAsString());
        JsonObject ringColours = jsonObject.get("ring_color").getAsJsonObject();
        int ringR = ringColours.get("r").getAsInt();
        int ringG = ringColours.get("g").getAsInt();
        int ringB = ringColours.get("b").getAsInt();
        int ringA = ringColours.get("a").getAsInt();

        return new SolarSystem(galaxy, solarSystem, sun, sunScale, buttonColour, new ColourHolder(ringR, ringG, ringB, ringA));
    }
}
