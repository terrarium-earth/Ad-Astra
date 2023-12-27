package earth.terrarium.adastra.client.dimension;

import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;

public class ModDimensionRenderers {
    public static final ResourceLocation EARTH_ORBIT_EFFECTS = Planet.EARTH_ORBIT.location();
    public static final ResourceLocation MOON_ORBIT_EFFECTS = Planet.MOON_ORBIT.location();
    public static final ResourceLocation MARS_ORBIT_EFFECTS = Planet.MARS_ORBIT.location();
    public static final ResourceLocation VENUS_ORBIT_EFFECTS = Planet.VENUS_ORBIT.location();
    public static final ResourceLocation MERCURY_ORBIT_EFFECTS = Planet.MERCURY_ORBIT.location();
    public static final ResourceLocation GLACIO_ORBIT_EFFECTS = Planet.GLACIO_ORBIT.location();

    public static final ResourceLocation MOON_EFFECTS = Planet.MOON.location();
    public static final ResourceLocation MARS_EFFECTS = Planet.MARS.location();
    public static final ResourceLocation VENUS_EFFECTS = Planet.VENUS.location();
    public static final ResourceLocation MERCURY_EFFECTS = Planet.MERCURY.location();
    public static final ResourceLocation GLACIO_EFFECTS = Planet.GLACIO.location();

    public static final SimpleWeightedRandomList<Integer> SPACE_STAR_COLORS = SimpleWeightedRandomList.<Integer>builder()
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

    public static void onRegisterDimensionSpecialEffects(BiConsumer<ResourceKey<Level>, ModDimensionSpecialEffects> consumer) {
        consumer.accept(Planet.EARTH_ORBIT, orbit(DimensionUtils.EARTH, 0xff3c7cda, 10)
            .addSkyRenderables(SkyRenderable.create(DimensionUtils.MOON, 8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffafb8cc))
            .build());

        consumer.accept(Planet.MOON_ORBIT, orbit(DimensionUtils.MOON, 0xffafb8cc, 9)
            .addSkyRenderables(SkyRenderable.create(DimensionUtils.EARTH, 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda))
            .build());

        consumer.accept(Planet.MARS_ORBIT, orbit(DimensionUtils.MARS, 0xffb6552b, 7).build());

        consumer.accept(Planet.VENUS_ORBIT, orbit(DimensionUtils.VENUS, 0xfff3c476, 14).build());

        consumer.accept(Planet.MERCURY_ORBIT, orbit(DimensionUtils.MERCURY, 0xffab6989, 22).build());

        consumer.accept(Planet.GLACIO_ORBIT, orbit(DimensionUtils.GLACIO, 0xffced7ec, 9)
            .addSkyRenderables(SkyRenderable.create(DimensionUtils.VICINUS, 50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, false, 0xff974cb8))
            .build());

        consumer.accept(Planet.MOON, moon().build());
        consumer.accept(Planet.MARS, mars().build());
        consumer.accept(Planet.VENUS, venus().build());
        consumer.accept(Planet.MERCURY, mercury().build());
        consumer.accept(Planet.GLACIO, glacio().build());
    }

    public static ModDimensionSpecialEffects.Builder orbit(ResourceLocation planet, int backlightColor, int sunScale) {
        return new ModDimensionSpecialEffects.Builder()
            .customClouds()
            .customSky()
            .cloudLevel(Float.NaN)
            .noGround()
            .starBrightness((level, partialTicks) -> 0.6f)
            .fogColor((color, partialTicks) -> Vec3.ZERO)
            .stars(13000)
            .renderInRain()
            .starColors(SPACE_STAR_COLORS)
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.SUN, sunScale, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                SkyRenderable.create(planet, 80, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor)
            );
    }

    public static ModDimensionSpecialEffects.Builder moon() {
        return new ModDimensionSpecialEffects.Builder()
            .customClouds()
            .customSky()
            .cloudLevel(Float.NaN)
            .starBrightness((level, partialTicks) -> 0.6f)
            .fogColor((color, partialTicks) -> Vec3.ZERO)
            .stars(13000)
            .renderInRain()
            .starColors(SPACE_STAR_COLORS)
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.SUN, 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                SkyRenderable.create(DimensionUtils.EARTH, 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda)
            );
    }

    public static ModDimensionSpecialEffects.Builder mars() {
        return new ModDimensionSpecialEffects.Builder()
            .customClouds()
            .customSky()
            .cloudLevel(Float.NaN)
            .sunriseColor(0x1fbbff)
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.BLUE_SUN, 7, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffd0faf7),
                SkyRenderable.create(DimensionUtils.PHOBOS, 2, new Vec3(20, 20, 180), new Vec3(0, 0, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffb4908d),
                SkyRenderable.create(DimensionUtils.DEIMOS, 1.5f, new Vec3(0, 0, 180), new Vec3(0, 20, 0), MovementType.TIME_OF_DAY, true, 0xffe5d5ad),
                SkyRenderable.create(DimensionUtils.EARTH, 0.4f, new Vec3(-40, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xff3c7cda)
            );
    }

    public static ModDimensionSpecialEffects.Builder venus() {
        return new ModDimensionSpecialEffects.Builder()
            .customSky()
            .foggy()
            .sunriseColor(0xf9c21a)
            .sunriseAngle(180)
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.RED_SUN, 14, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY_REVERSED, true, 0xfff48c61),
                SkyRenderable.create(DimensionUtils.EARTH, 0.5f, new Vec3(-20, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xff3c7cda),
                SkyRenderable.create(DimensionUtils.MERCURY, 0.3f, new Vec3(10, 0, -10), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffab6989)
            );
    }

    public static ModDimensionSpecialEffects.Builder mercury() {
        return new ModDimensionSpecialEffects.Builder()
            .customClouds()
            .customSky()
            .cloudLevel(Float.NaN)
            .sunriseColor(0xd63a0b)
            .starBrightness((level, partialTicks) -> 0.5f)
            .stars(9000)
            .renderInRain()
            .starColors(SPACE_STAR_COLORS)
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.RED_SUN, 22, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xfff48c61),
                SkyRenderable.create(DimensionUtils.VENUS, 0.4f, new Vec3(-15, 0, 140), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xfff3c476)
            );
    }

    public static ModDimensionSpecialEffects.Builder glacio() {
        return new ModDimensionSpecialEffects.Builder()
            .customSky()
            .addSkyRenderables(
                SkyRenderable.create(DimensionUtils.SUN, 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9),
                SkyRenderable.create(DimensionUtils.VICINUS, 50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, false, 0xff974cb8)
            );
    }
}
