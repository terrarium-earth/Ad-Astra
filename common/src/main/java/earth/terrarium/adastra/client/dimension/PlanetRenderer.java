package earth.terrarium.adastra.client.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record PlanetRenderer(
    ResourceKey<Level> dimension,
    boolean customClouds,
    boolean customSky,
    boolean customWeather,
    boolean hasThickFog,
    boolean hasFog,
    int sunriseColor,
    int stars,
    Optional<Float> starBrightness,
    int sunriseAngle,
    boolean renderInRain,
    WeightedRandomList<WeightedEntry.Wrapper<Integer>> starColors,
    List<SkyRenderable> skyRenderables
) {
    public static final Codec<PlanetRenderer> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(PlanetRenderer::dimension),
        Codec.BOOL.fieldOf("custom_clouds").forGetter(PlanetRenderer::customClouds),
        Codec.BOOL.fieldOf("custom_sky").forGetter(PlanetRenderer::customSky),
        Codec.BOOL.fieldOf("custom_weather").forGetter(PlanetRenderer::customWeather),
        Codec.BOOL.fieldOf("has_thick_fog").forGetter(PlanetRenderer::hasThickFog),
        Codec.BOOL.fieldOf("has_fog").forGetter(PlanetRenderer::hasFog),
        Codec.INT.fieldOf("sunrise_color").forGetter(PlanetRenderer::sunriseColor),
        Codec.INT.fieldOf("stars").forGetter(PlanetRenderer::stars),
        Codec.FLOAT.optionalFieldOf("star_brightness").forGetter(PlanetRenderer::starBrightness),
        Codec.INT.fieldOf("sunrise_angle").forGetter(PlanetRenderer::sunriseAngle),
        Codec.BOOL.fieldOf("render_in_rain").forGetter(PlanetRenderer::renderInRain),
        SimpleWeightedRandomList.codec(WeightedEntry.Wrapper.codec(Codec.INT)).fieldOf("star_colors").forGetter(PlanetRenderer::starColors),
        SkyRenderable.CODEC.listOf().fieldOf("sky_renderables").forGetter(PlanetRenderer::skyRenderables)
    ).apply(inst, PlanetRenderer::new));
}
