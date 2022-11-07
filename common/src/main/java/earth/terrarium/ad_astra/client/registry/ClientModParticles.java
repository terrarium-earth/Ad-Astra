package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.particles.LargeFlameParticle;
import earth.terrarium.ad_astra.client.particles.OxygenBubbleParticle;
import earth.terrarium.ad_astra.client.particles.SmallFlameParticle;
import earth.terrarium.ad_astra.registry.ModParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public class ClientModParticles {

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, ClientModParticles.SpriteParticleRegistration<SimpleParticleType> > register) {
        register.accept(ModParticleTypes.VENUS_RAIN.get(), SplashParticle.Provider::new);
        register.accept(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Provider::new);
        register.accept(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Provider::new);
        register.accept(ModParticleTypes.SMALL_FLAME.get(), SmallFlameParticle.Provider::new);
        register.accept(ModParticleTypes.SMALL_SMOKE.get(), SmallFlameParticle.Provider::new);
        register.accept(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Provider::new);
    }

    @FunctionalInterface
    @Environment(EnvType.CLIENT)
    public interface SpriteParticleRegistration<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}
