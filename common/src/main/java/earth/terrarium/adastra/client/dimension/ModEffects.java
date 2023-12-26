package earth.terrarium.adastra.client.dimension;

import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.BiConsumer;

public class ModEffects {
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
        consumer.accept(Planet.EARTH_ORBIT, orbit(DimensionUtils.EARTH, 0xff3c7cda).build());
        consumer.accept(Planet.MOON_ORBIT, orbit(DimensionUtils.MOON, 0xffafb8cc).build());
        consumer.accept(Planet.MARS_ORBIT, orbit(DimensionUtils.MARS, 0xffb6552b).build());
        consumer.accept(Planet.VENUS_ORBIT, orbit(DimensionUtils.VENUS, 0xfff3c476).build());
        consumer.accept(Planet.MERCURY_ORBIT, orbit(DimensionUtils.MERCURY, 0xffab6989).build());
        consumer.accept(Planet.GLACIO_ORBIT, orbit(DimensionUtils.GLACIO, 0xffced7ec).build());
    }

    public static ModDimensionSpecialEffects.Builder orbit(ResourceLocation planet, int backlightColor) {
        return new ModDimensionSpecialEffects.Builder()
            .customClouds()
            .customSky()
            .cloudLevel(Float.NaN)
            .hasGround(false)
            .skyType(DimensionSpecialEffects.SkyType.NONE)
            .forceBrightLightmap(false)
            .constantAmbientLight(false)
            .starBrightness((level, partialTicks) -> 0.6f)
            .fogColor((color, partialTicks) -> Vec3.ZERO)
            .isFoggyAt((x, y) -> false)
            .stars(13000)
            .starColors(SPACE_STAR_COLORS)
            .addSkyRenderables(
                SkyRenderable.createWithBackLight(DimensionUtils.SUN, 15, new Vec3(0, 0, 90), Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                SkyRenderable.createWithBackLight(planet, 60, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor)
            );
    }
}
