package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.client.dimension.renderer.StarInformation;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;

@Environment(EnvType.CLIENT)
public record PlanetSkyRenderer(ResourceKey<Level> dimension, PlanetSkyRenderer.StarsRenderer starsRenderer,
                                PlanetSkyRenderer.SunsetColour sunsetColour, PlanetSkyRenderer.DimensionEffects effects,
                                PlanetSkyRenderer.CloudEffects cloudEffects,
                                PlanetSkyRenderer.WeatherEffects weatherEffects, int horizonAngle,
                                List<PlanetSkyRenderer.SkyObject> skyObjects) {

    public static final Codec<PlanetSkyRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceKey.codec(Registry.DIMENSION_REGISTRY).fieldOf("world").forGetter(PlanetSkyRenderer::dimension),
        StarsRenderer.CODEC.fieldOf("stars").forGetter(PlanetSkyRenderer::starsRenderer),
        SunsetColour.CODEC.fieldOf("sunset_color").forGetter(PlanetSkyRenderer::sunsetColour),
        DimensionEffects.CODEC.fieldOf("dimension_effects").forGetter(PlanetSkyRenderer::effects),
        CloudEffects.CODEC.fieldOf("cloud_effects").forGetter(PlanetSkyRenderer::cloudEffects),
        WeatherEffects.CODEC.fieldOf("weather_effects").forGetter(PlanetSkyRenderer::weatherEffects),
        Codec.INT.fieldOf("horizon_angle").forGetter(PlanetSkyRenderer::horizonAngle),
        SkyObject.CODEC.listOf().fieldOf("sky_objects").forGetter(PlanetSkyRenderer::skyObjects)
    ).apply(instance, PlanetSkyRenderer::new));


    // Post-rendering.
    public enum RenderType {
        STATIC, // Never moves.
        DYNAMIC, // Moves based on the time of day.
        SCALING, // Scales based on the position away from the player.
        DEBUG; // Only for testing while in a debug environment without restarting Minecraft.

        public static final Codec<RenderType> CODEC = ModUtils.createEnumCodec(RenderType.class);
    }

    // Changes the colour of the sunset and sunrise.
    public enum SunsetColour {
        VANILLA, // Vanilla.
        MARS; // Custom pink-ish red hue.

        public static final Codec<SunsetColour> CODEC = ModUtils.createEnumCodec(SunsetColour.class);
    }

    public enum CloudEffects {
        NONE, VANILLA, VENUS;

        public static final Codec<CloudEffects> CODEC = ModUtils.createEnumCodec(CloudEffects.class);
    }

    public enum
    WeatherEffects {
        NONE, VANILLA, VENUS;

        public static final Codec<WeatherEffects> CODEC = ModUtils.createEnumCodec(WeatherEffects.class);
    }

    public enum DimensionEffectType {
        SIMPLE, NONE, FOGGY_REVERSED, FOGGY, COLORED_HORIZON;

        public static final Codec<DimensionEffectType> CODEC = ModUtils.createEnumCodec(DimensionEffectType.class);

        public boolean isFoggy() {
            return this == FOGGY || this == FOGGY_REVERSED;
        }
    }

    public record StarsRenderer(int fancyStars, int fastStars, boolean colouredStars, boolean daylightVisible) {

        public static final Codec<StarsRenderer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("fancy_count").forGetter(StarsRenderer::fancyStars),
            Codec.INT.fieldOf("fast_count").forGetter(StarsRenderer::fastStars),
            Codec.BOOL.fieldOf("colored_stars").forGetter(StarsRenderer::colouredStars),
            Codec.BOOL.fieldOf("daylight_visible").forGetter(StarsRenderer::daylightVisible)
        ).apply(instance, StarsRenderer::new));
    }

    public record SkyObject(ResourceLocation texture, boolean blending, RenderType renderType, float scale,
                            Color colour,
                            Vector3f rotation) {

        public static final Codec<SkyObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SkyObject::texture),
            Codec.BOOL.fieldOf("blending").forGetter(SkyObject::blending),
            RenderType.CODEC.fieldOf("render_type").forGetter(SkyObject::renderType),
            Codec.FLOAT.fieldOf("scale").forGetter(SkyObject::scale),
            Color.CODEC.fieldOf("color").orElse(StarInformation.BASE_COLOUR).forGetter(SkyObject::colour),
            Vector3f.CODEC.fieldOf("rotation").forGetter(SkyObject::rotation)
        ).apply(instance, SkyObject::new));
    }

    public record DimensionEffects(DimensionEffectType type, Color colour) {

        public static final Codec<DimensionEffects> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            DimensionEffectType.CODEC.fieldOf("type").forGetter(DimensionEffects::type),
            Color.CODEC.fieldOf("color").orElse(StarInformation.BASE_COLOUR).forGetter(DimensionEffects::colour)
        ).apply(instance, DimensionEffects::new));
    }
}