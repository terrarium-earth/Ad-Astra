package earth.terrarium.adastra.datagen.provider.client;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.dimension.MovementType;
import earth.terrarium.adastra.client.dimension.PlanetRenderer;
import earth.terrarium.adastra.client.dimension.SkyRenderable;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


public class ModPlanetRendererProvider extends ModCodecProvider<PlanetRenderer> {
    public static final ResourceKey<Registry<PlanetRenderer>> PLANET_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(AdAstra.MOD_ID, "planet_renderers"));

    public static final int DEFAULT_SUNRISE_COLOR = 0xd85f33;

    public static final SimpleWeightedRandomList<Integer> COLORED_STARS = SimpleWeightedRandomList.<Integer>builder()
        .add(0xA9BCDFFF, 3)   // Blue
        .add(0xBBD7FFFF, 5)   // Blue-White,
        .add(0xFFF4E8FF, 100) // Yellow-White
        .add(0xFFD1A0FF, 80)  // Orange
        .add(0xFF8A8AFF, 150) // Red
        .add(0xFF4500FF, 10)  // Orange-Red
        .add(0xFFFFF4FF, 60)  // White
        .add(0xFFF8E7FF, 40)  // Pale Yellow
        .add(0xFFFFFF00, 20)  // Very Pale Yellow
        .add(0xFFFF0000, 1)   // Bright Red
        .build();

    public static final SimpleWeightedRandomList<Integer> DEFAULT_STARS = SimpleWeightedRandomList.<Integer>builder()
        .add(0xffffffff, 1)
        .build();

    public ModPlanetRendererProvider(PackOutput packOutput) {
        super(packOutput, PlanetRenderer.CODEC, PLANET_REGISTRY, PackOutput.Target.RESOURCE_PACK);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, PlanetRenderer> consumer) {
        orbit(consumer, Planet.EARTH_ORBIT, DimensionRenderingUtils.EARTH, 0xff3c7cda, 10,
            new SkyRenderable(DimensionRenderingUtils.MOON, 8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffafb8cc));

        orbit(consumer, Planet.MOON_ORBIT, DimensionRenderingUtils.MOON, 0xffafb8cc, 9,
            new SkyRenderable(DimensionRenderingUtils.EARTH, 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda));

        orbit(consumer, Planet.MARS_ORBIT, DimensionRenderingUtils.MARS, 0xffb6552b, 7);

        orbit(consumer, Planet.VENUS_ORBIT, DimensionRenderingUtils.VENUS, 0xfff3c476, 14);

        orbit(consumer, Planet.MERCURY_ORBIT, DimensionRenderingUtils.MERCURY, 0xffab6989, 22);

        orbit(consumer, Planet.GLACIO_ORBIT, DimensionRenderingUtils.GLACIO, 0xffced7ec, 9,
            new SkyRenderable(DimensionRenderingUtils.VICINUS, 50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, false, 0xff974cb8));

        consumer.accept(Planet.MOON.location(), new PlanetRenderer(
            Planet.MOON,
            true,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            13000,
            Optional.of(0.6f),
            0,
            true,
            COLORED_STARS,
            List.of(
                new SkyRenderable(DimensionRenderingUtils.SUN, 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                new SkyRenderable(DimensionRenderingUtils.EARTH, 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda)
            )));

        consumer.accept(Planet.MARS.location(), new PlanetRenderer(
            Planet.MARS,
            true,
            true,
            false,
            false,
            true,
            0x1fbbff,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(DimensionRenderingUtils.BLUE_SUN, 7, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffd0faf7),
                new SkyRenderable(DimensionRenderingUtils.PHOBOS, 2, new Vec3(20, 20, 180), new Vec3(0, 0, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffb4908d),
                new SkyRenderable(DimensionRenderingUtils.DEIMOS, 1.5f, new Vec3(0, 0, 180), new Vec3(0, 20, 0), MovementType.TIME_OF_DAY, true, 0xffe5d5ad),
                new SkyRenderable(DimensionRenderingUtils.EARTH, 0.4f, new Vec3(-40, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xff3c7cda)
            )));

        consumer.accept(Planet.VENUS.location(), new PlanetRenderer(
            Planet.VENUS,
            false,
            true,
            false,
            true,
            true,
            0xf9c21a,
            1500,
            Optional.empty(),
            180,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(DimensionRenderingUtils.RED_SUN, 14, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY_REVERSED, true, 0xfff48c61),
                new SkyRenderable(DimensionRenderingUtils.EARTH, 0.5f, new Vec3(-20, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xff3c7cda),
                new SkyRenderable(DimensionRenderingUtils.MERCURY, 0.3f, new Vec3(10, 0, -10), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffab6989)
            )));

        consumer.accept(Planet.MERCURY.location(), new PlanetRenderer(
            Planet.MERCURY,
            true,
            true,
            false,
            false,
            true,
            0xd63a0b,
            9000,
            Optional.of(0.6f),
            0,
            true,
            COLORED_STARS,
            List.of(
                new SkyRenderable(DimensionRenderingUtils.RED_SUN, 22, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xfff48c61),
                new SkyRenderable(DimensionRenderingUtils.VENUS, 0.4f, new Vec3(-15, 0, 140), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xfff3c476)
            )));

        consumer.accept(Planet.GLACIO.location(), new PlanetRenderer(
            Planet.GLACIO,
            false,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(DimensionRenderingUtils.SUN, 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9),
                new SkyRenderable(DimensionRenderingUtils.VICINUS, 50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, false, 0xff974cb8)
            )));
    }

    private static void orbit(BiConsumer<ResourceLocation, PlanetRenderer> consumer, ResourceKey<Level> planet, ResourceLocation planetTexture, int backlightColor, int sunScale, SkyRenderable... additionalRenderables) {
        List<SkyRenderable> renderables = new ArrayList<>();
        renderables.add(new SkyRenderable(DimensionRenderingUtils.SUN, sunScale, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9));
        renderables.add(new SkyRenderable(planetTexture, 80, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor));
        renderables.addAll(List.of(additionalRenderables));
        consumer.accept(
            planet.location(),
            new PlanetRenderer(
                planet,
                true,
                true,
                false,
                false,
                false,
                DEFAULT_SUNRISE_COLOR,
                13000,
                Optional.of(0.6f),
                0,
                true,
                COLORED_STARS,
                renderables
            ));
    }

    @Override
    public @NotNull String getName() {
        return "Planet Renderers";
    }
}
