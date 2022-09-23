package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.particles.LargeFlameParticle;
import com.github.alexnijjar.ad_astra.client.particles.OxygenBubbleParticle;
import com.github.alexnijjar.ad_astra.client.particles.SmallFlameParticle;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.screen.PlayerScreenHandler;

@Environment(EnvType.CLIENT)
public class ClientModParticles {

	public static void register() {

		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(new ModIdentifier("particle/flame_1"));
			registry.register(new ModIdentifier("particle/flame_2"));
			registry.register(new ModIdentifier("particle/flame_3"));
			registry.register(new ModIdentifier("particle/flame_4"));
			registry.register(new ModIdentifier("particle/venus_rain_1"));
			registry.register(new ModIdentifier("particle/venus_rain_2"));
			registry.register(new ModIdentifier("particle/venus_rain_3"));
			registry.register(new ModIdentifier("particle/venus_rain_4"));
		}));

		ParticleProviderRegistry.register(ModParticleTypes.VENUS_RAIN.get(), WaterSplashParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.SMALL_FLAME.get(), SmallFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.SMALL_SMOKE.get(), SmallFlameParticle.Factory::new);
		ParticleProviderRegistry.register(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Factory::new);
	}
}
