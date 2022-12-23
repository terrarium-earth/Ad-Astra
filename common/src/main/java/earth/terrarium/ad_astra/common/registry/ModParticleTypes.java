package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.Supplier;

public class ModParticleTypes {
    public static final RegistryHolder<ParticleType<?>> PARTICLE_TYPES = new RegistryHolder<>(Registry.PARTICLE_TYPE, AdAstra.MOD_ID);

    public static final Supplier<SimpleParticleType> VENUS_RAIN = PARTICLE_TYPES.register("venus_rain", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> LARGE_FLAME = PARTICLE_TYPES.register("large_flame", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> LARGE_SMOKE = PARTICLE_TYPES.register("large_smoke", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> SMALL_FLAME = PARTICLE_TYPES.register("small_flame", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> SMALL_SMOKE = PARTICLE_TYPES.register("small_smoke", () -> new SimpleParticleType(true) {
    });
    public static final Supplier<SimpleParticleType> OXYGEN_BUBBLE = PARTICLE_TYPES.register("oxygen_bubble", () -> new SimpleParticleType(true) {
    });
}
