package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.particles.LargeFlameParticle;
import com.github.alexnijjar.ad_astra.client.particles.OxygenBubbleParticle;
import com.github.alexnijjar.ad_astra.client.particles.SmallFlameParticle;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.WaterSplashParticle;

@Environment(EnvType.CLIENT)
public class ClientModParticles {

	public static void register() {
		ParticleProviderRegistry.register(ModParticleTypes.VENUS_RAIN.get(), WaterSplashParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.SMALL_FLAME.get(), SmallFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.SMALL_SMOKE.get(), SmallFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Factory::new);
	}
}
