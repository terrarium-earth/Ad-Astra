package net.mrscauthd.beyond_earth.client.resource_pack;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ColourHolder;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public record SkyRenderer(RegistryKey<World> dimension, StarsRenderer starsRenderer, SunsetColour sunsetColour, DimensionEffects effects, CloudEffects cloudEffects, WeatherEffects weatherEffects, int horizonAngle, List<SkyObject> skyObjects) {

    public record StarsRenderer(int fancyStars, int fastStars, boolean daylightVisible) {
    }

    public record SkyObject(Identifier texture, boolean blending, RenderType renderType, float scale, Vec3f rotation) {

    }

    public record DimensionEffects(DimensionEffectType type, ColourHolder colour) {

    }

    public enum TextureType {
        SUN("textures/sky/no_a_sun.png"),
        BLUE_SUN("textures/sky/blue_sun.png"),
        RED_SUN("textures/sky/red_sun.png"),
        EARTH_LIGHT("textures/sky/earth_light.png"), 
        EARTH("textures/sky/earth.png"),
        MOON("textures/sky/moon.png"),
        MARS("textures/sky/mars.png"),
        VENUS("textures/sky/venus.png"),
        MERCURY("textures/sky/mercury.png"),
        GLACIO("textures/sky/glacio.png"),
        PHOBOS("textures/sky/phobos.png"),
        DEIMOS("textures/sky/deimos.png"),
        VICINUS("textures/sky/vicinus.png"),
        VICINUS_LIGHT("textures/sky/vicinus_light.png");

        private final Identifier texture;

        private TextureType(String texture) {
            this.texture = new ModIdentifier(texture);
        }

        public Identifier getTexture() {
            return this.texture;
        }
    }

    // Post-rendering after the Renderable object has been created.
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
        SIMPLE, 
        FOGGY,
        COLORED_HORIZON
    }
}