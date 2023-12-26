package earth.terrarium.adastra.client.dimension;

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

    public static void onRegisterDimensionSpecialEffects(BiConsumer<ResourceKey<Level>, ModDimensionSpecialEffects> consumer) {
        consumer.accept(Planet.EARTH_ORBIT, orbit().build());
        consumer.accept(Planet.MOON_ORBIT, orbit().build());
        consumer.accept(Planet.MARS_ORBIT, orbit().build());
        consumer.accept(Planet.VENUS_ORBIT, orbit().build());
        consumer.accept(Planet.MERCURY_ORBIT, orbit().build());
        consumer.accept(Planet.GLACIO_ORBIT, orbit().build());
    }

    public static ModDimensionSpecialEffects.Builder orbit() {
        return new ModDimensionSpecialEffects.Builder()
            .cloudLevel(Float.NaN)
            .hasGround(false)
            .skyType(DimensionSpecialEffects.SkyType.NONE)
            .forceBrightLightmap(false)
            .constantAmbientLight(false)
            .starBrightness((level, partialTicks) -> 0.6f)
            .fogColor((color, partialTicks) -> Vec3.ZERO)
            .isFoggyAt((x, y) -> false)
            .stars(13000)
            .starColors(SimpleWeightedRandomList.<Integer>builder()
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
                .build()
            );
    }
}
