package earth.terrarium.ad_astra.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticleTypes {

    public static final Supplier<SimpleParticleType> VENUS_RAIN = register("venus_rain", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> LARGE_FLAME = register("large_flame", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> LARGE_SMOKE = register("large_smoke", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> SMALL_FLAME = register("small_flame", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> SMALL_SMOKE = register("small_smoke", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> OXYGEN_BUBBLE = register("oxygen_bubble", () -> new SimpleParticleType(true) {
    });

    private static <T extends ParticleType<P>, P extends ParticleOptions> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.PARTICLE_TYPE, id, object);
    }

    public static void init() {
    }
}
