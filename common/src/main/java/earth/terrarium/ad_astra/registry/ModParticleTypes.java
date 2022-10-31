package earth.terrarium.ad_astra.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.particles.LargeFlameParticle;
import earth.terrarium.ad_astra.client.particles.OxygenBubbleParticle;
import earth.terrarium.ad_astra.client.particles.SmallFlameParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.PARTICLE_TYPE_REGISTRY);

    public static final RegistrySupplier<SimpleParticleType> VENUS_RAIN = PARTICLE_TYPES.register("venus_rain", () -> new SimpleParticleType(true) {
    });
    public static final RegistrySupplier<SimpleParticleType> LARGE_FLAME = PARTICLE_TYPES.register("large_flame", () -> new SimpleParticleType(true) {
    });
    public static final RegistrySupplier<SimpleParticleType> LARGE_SMOKE = PARTICLE_TYPES.register("large_smoke", () -> new SimpleParticleType(true) {
    });
    public static final RegistrySupplier<SimpleParticleType> SMALL_FLAME = PARTICLE_TYPES.register("small_flame", () -> new SimpleParticleType(true) {
    });
    public static final RegistrySupplier<SimpleParticleType> SMALL_SMOKE = PARTICLE_TYPES.register("small_smoke", () -> new SimpleParticleType(true) {
    });
    public static final RegistrySupplier<SimpleParticleType> OXYGEN_BUBBLE = PARTICLE_TYPES.register("oxygen_bubble", () -> new SimpleParticleType(true) {
    });

    public static void register() {
        PARTICLE_TYPES.register();
        if (Platform.getEnvironment() == Env.CLIENT) {
            register(ModParticleTypes.VENUS_RAIN, SplashParticle.Provider::new);
            register(ModParticleTypes.LARGE_FLAME, LargeFlameParticle.Provider::new);
            register(ModParticleTypes.LARGE_SMOKE, LargeFlameParticle.Provider::new);
            register(ModParticleTypes.SMALL_FLAME, SmallFlameParticle.Provider::new);
            register(ModParticleTypes.SMALL_SMOKE, SmallFlameParticle.Provider::new);
            register(ModParticleTypes.OXYGEN_BUBBLE, OxygenBubbleParticle.Provider::new);
        }
    }

    public static <T extends ParticleOptions> void register(RegistrySupplier<? extends ParticleType<T>> supplier, ParticleProviderRegistry.DeferredParticleProvider<T> provider) {
        supplier.listen(it -> ParticleProviderRegistry.register(it, provider));
    }
}
