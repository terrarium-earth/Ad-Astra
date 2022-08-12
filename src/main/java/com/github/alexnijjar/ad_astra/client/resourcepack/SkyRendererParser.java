package com.github.alexnijjar.ad_astra.client.resourcepack;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.ad_astra.util.ColourHolder;
import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class SkyRendererParser {

    public static SkyRenderer parse(JsonObject jsonObject) {

        RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("world").getAsString()));

        JsonObject starsObject = jsonObject.get("stars").getAsJsonObject();
        int fancyStars = starsObject.get("fancy_count").getAsInt();
        int fastStars = starsObject.get("fast_count").getAsInt();
        boolean colouredStars = starsObject.get("colored_stars").getAsBoolean();
        boolean daylightVisible = starsObject.get("daylight_visible").getAsBoolean();
        SkyRenderer.StarsRenderer starsRenderer = new SkyRenderer.StarsRenderer(fancyStars, fastStars, colouredStars, daylightVisible);

        SkyRenderer.SunsetColour sunsetColour = SkyRenderer.SunsetColour.valueOf(jsonObject.get("sunset_color").getAsString().toUpperCase());

        JsonObject dimensionEffectsObject = jsonObject.get("dimension_effects").getAsJsonObject();
        SkyRenderer.DimensionEffectType dimensionEffectType = SkyRenderer.DimensionEffectType.valueOf(dimensionEffectsObject.get("type").getAsString().toUpperCase());
        int starR = dimensionEffectsObject.has("r") ? dimensionEffectsObject.get("r").getAsInt() : 255;
        int starG = dimensionEffectsObject.has("g") ? dimensionEffectsObject.get("g").getAsInt() : 255;
        int starB = dimensionEffectsObject.has("b") ? dimensionEffectsObject.get("b").getAsInt() : 255;
        SkyRenderer.DimensionEffects effects = new SkyRenderer.DimensionEffects(dimensionEffectType, new ColourHolder(starR / 255.0f, starG / 255.0f, starB / 255.0f, 1.0f));

        SkyRenderer.CloudEffects cloudEffects = SkyRenderer.CloudEffects.valueOf(jsonObject.get("cloud_effects").getAsString().toUpperCase());
        SkyRenderer.WeatherEffects weatherEffects = SkyRenderer.WeatherEffects.valueOf(jsonObject.get("weather_effects").getAsString().toUpperCase());

        int horizonAngle = jsonObject.get("horizon_angle").getAsInt();
        List<SkyRenderer.SkyObject> skyObjects = new LinkedList<>();
        jsonObject.get("sky_objects").getAsJsonArray().forEach(skyElement -> {
            JsonObject skyObject = skyElement.getAsJsonObject();
            Identifier texture = new Identifier(skyObject.get("texture").getAsString());
            SkyRenderer.RenderType renderType = SkyRenderer.RenderType.valueOf(skyObject.get("render_type").getAsString().toUpperCase());
            boolean blending = skyObject.get("blending").getAsBoolean();
            float scale = skyObject.get("scale").getAsFloat();

            ColourHolder colour;
            if (skyObject.has("color")) {
                JsonObject colourObject = skyObject.get("color").getAsJsonObject();
                int r = colourObject.has("r") ? colourObject.get("r").getAsInt() : 255;
                int g = colourObject.has("g") ? colourObject.get("g").getAsInt() : 255;
                int b = colourObject.has("b") ? colourObject.get("b").getAsInt() : 255;
                colour = new ColourHolder(r, g, b, 255);
            } else {
                colour = new ColourHolder(255, 255, 255, 255);
            }

            JsonObject rotationObject = skyObject.get("rotation").getAsJsonObject();
            float x = rotationObject.get("x").getAsFloat();
            float y = rotationObject.get("y").getAsFloat();
            float z = rotationObject.get("z").getAsFloat();
            Vec3f rotation = new Vec3f(x, y, z);

            skyObjects.add(new SkyRenderer.SkyObject(texture, blending, renderType, scale, colour, rotation));
        });

        return new SkyRenderer(dimension, starsRenderer, sunsetColour, effects, cloudEffects, weatherEffects, horizonAngle, skyObjects);
    }
}
