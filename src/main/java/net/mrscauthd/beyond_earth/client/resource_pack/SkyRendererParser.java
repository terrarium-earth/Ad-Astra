package net.mrscauthd.beyond_earth.client.resource_pack;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ColourHolder;

@Environment(EnvType.CLIENT)
public class SkyRendererParser {

    public static SkyRenderer parse(JsonObject jsonObject) {

        RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, new Identifier(jsonObject.get("dimension").getAsString()));

        JsonObject starsObject = jsonObject.get("stars").getAsJsonObject();
        int fancyStars = starsObject.get("fancy_count").getAsInt();
        int fastStars = starsObject.get("fast_count").getAsInt();
        boolean daylightVisible = starsObject.get("daylight_visible").getAsBoolean();
        SkyRenderer.StarsRenderer starsRenderer = new SkyRenderer.StarsRenderer(fancyStars, fastStars, daylightVisible);

        SkyRenderer.SunsetColour sunsetColour = SkyRenderer.SunsetColour.valueOf(jsonObject.get("sunset_color").getAsString().toUpperCase());

        JsonObject dimensionEffectsObject = jsonObject.get("dimension_effects").getAsJsonObject();
        SkyRenderer.DimensionEffectType dimensionEffectType = SkyRenderer.DimensionEffectType.valueOf(dimensionEffectsObject.get("type").getAsString().toUpperCase());
        int r = 255;
        int g = 255;
        int b = 255;
        if (dimensionEffectsObject.has("r")) {
            r = dimensionEffectsObject.get("r").getAsInt();
        }
        if (dimensionEffectsObject.has("g")) {
            g = dimensionEffectsObject.get("g").getAsInt();
        }
        if (dimensionEffectsObject.has("b")) {
            b = dimensionEffectsObject.get("b").getAsInt();
        }
        SkyRenderer.DimensionEffects effects = new SkyRenderer.DimensionEffects(dimensionEffectType, new ColourHolder(r / 255.0f, g / 255.0f, b / 255.0f));

        SkyRenderer.CloudEffects cloudEffects = SkyRenderer.CloudEffects.valueOf(jsonObject.get("cloud_effects").getAsString().toUpperCase());
        SkyRenderer.WeatherEffects weatherEffects = SkyRenderer.WeatherEffects.valueOf(jsonObject.get("weather_effects").getAsString().toUpperCase());

        int horizonAngle = jsonObject.get("horizon_angle").getAsInt();
        List<SkyRenderer.SkyObject> skyObjects = new LinkedList<>();
        jsonObject.get("sky_objects").getAsJsonArray().forEach(skyElement -> {
            JsonObject skyObject = skyElement.getAsJsonObject();
            Identifier texture = null;
            String textureString = skyObject.get("texture").getAsString();
            for (SkyRenderer.TextureType textureType : SkyRenderer.TextureType.values()) {
                if (textureType.toString().toLowerCase().equals(textureString)) {
                    texture = textureType.getTexture();
                    break;
                }
            }
            if (texture == null) {
                texture = new Identifier(textureString);
            }
            SkyRenderer.RenderType renderType = SkyRenderer.RenderType.valueOf(skyObject.get("render_type").getAsString().toUpperCase());
            boolean blending = skyObject.get("blending").getAsBoolean();
            float scale = skyObject.get("scale").getAsFloat();

            JsonObject rotationObject = skyObject.get("rotation").getAsJsonObject();
            float x = rotationObject.get("x").getAsFloat();
            float y = rotationObject.get("y").getAsFloat();
            float z = rotationObject.get("z").getAsFloat();
            Vec3f rotation = new Vec3f(x, y, z);

            skyObjects.add(new SkyRenderer.SkyObject(texture, blending, renderType, scale, rotation));
        });

        return new SkyRenderer(dimension, starsRenderer, sunsetColour, effects, cloudEffects, weatherEffects, horizonAngle, skyObjects);
    }
}
