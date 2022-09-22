package com.github.alexnijjar.ad_astra.client.resourcepack;

import com.github.alexnijjar.ad_astra.data.ButtonColour;
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
