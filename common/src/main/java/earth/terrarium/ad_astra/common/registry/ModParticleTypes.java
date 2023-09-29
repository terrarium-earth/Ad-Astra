package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
    public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<SimpleParticleType> VENUS_RAIN = PARTICLE_TYPES.register("venus_rain", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> LARGE_FLAME = PARTICLE_TYPES.register("large_flame", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> LARGE_SMOKE = PARTICLE_TYPES.register("large_smoke", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> SMALL_FLAME = PARTICLE_TYPES.register("small_flame", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> SMALL_SMOKE = PARTICLE_TYPES.register("small_smoke", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> OXYGEN_BUBBLE = PARTICLE_TYPES.register("oxygen_bubble", () -> new SimpleParticleType(true) {});
}
