package com.github.alexnijjar.ad_astra.client.resourcepack;

import java.util.List;

import com.github.alexnijjar.ad_astra.util.ColourHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public record SkyRenderer(RegistryKey<World> dimension, SkyRenderer.StarsRenderer starsRenderer, SkyRenderer.SunsetColour sunsetColour, SkyRenderer.DimensionEffects effects, SkyRenderer.CloudEffects cloudEffects, SkyRenderer.WeatherEffects weatherEffects,
        int horizonAngle, List<SkyRenderer.SkyObject> skyObjects) {

    public record StarsRenderer(int fancyStars, int fastStars, boolean colouredStars, boolean daylightVisible) {
    }

    public record SkyObject(Identifier texture, boolean blending, RenderType renderType, float scale, ColourHolder colour, Vec3f rotation) {

    }

    public record DimensionEffects(DimensionEffectType type, ColourHolder colour) {

    }

    // Post-rendering.
    public enum RenderType {
        STATIC, // Never moves.
        DYNAMIC, // Moves based on the time of day.
        SCALING, // Scales based on the position away from the player.
        DEBUG // Only for testing while in a debug environment without restarting Minecraft.
    }

    // Changes the colour of the sunset and sunrise.
    public enum SunsetColour {
        VANILLA, // Vanilla.
        MARS // Custom pink-ish red hue.
    }

    public enum CloudEffects {
        NONE, VANILLA, VENUS
    }

    public enum WeatherEffects {
        NONE, VENUS
    }

    public enum DimensionEffectType {
        SIMPLE, NONE, FOGGY_REVERSED, FOGGY, COLORED_HORIZON
    }
}