package com.github.alexnijjar.beyond_earth.client.resource_pack;

import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GalaxyParser {

    public static Galaxy parse(JsonObject jsonObject) {

        Identifier galaxy = new Identifier(jsonObject.get("galaxy").getAsString());
        Identifier texture = new Identifier(jsonObject.get("texture").getAsString());
        ButtonColour buttonColour = ButtonColour.stringToColour(jsonObject.get("button_color").getAsString());
        int scale = jsonObject.get("scale").getAsInt();

        return new Galaxy(galaxy, texture, buttonColour, scale);
    }
}
