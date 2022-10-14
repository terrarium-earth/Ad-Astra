package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.particles.LargeFlameParticle;
import com.github.alexnijjar.ad_astra.client.particles.OxygenBubbleParticle;
import com.github.alexnijjar.ad_astra.client.particles.SmallFlameParticle;

import dev.architectury.platform.Platform;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import net.minecraft.client.particle.WaterSplashParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;

public class ModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.PARTICLE_TYPE_KEY);

	public static final RegistrySupplier<DefaultParticleType> VENUS_RAIN = PARTICLE_TYPES.register("venus_rain", () -> new DefaultParticleType(true) {
	});
	public static final RegistrySupplier<DefaultParticleType> LARGE_FLAME = PARTICLE_TYPES.register("large_flame", () -> new DefaultParticleType(true) {
	});
	public static final RegistrySupplier<DefaultParticleType> LARGE_SMOKE = PARTICLE_TYPES.register("large_smoke", () -> new DefaultParticleType(true) {
	});
	public static final RegistrySupplier<DefaultParticleType> SMALL_FLAME = PARTICLE_TYPES.register("small_flame", () -> new DefaultParticleType(true) {
	});
	public static final RegistrySupplier<DefaultParticleType> SMALL_SMOKE = PARTICLE_TYPES.register("small_smoke", () -> new DefaultParticleType(true) {
	});
	public static final RegistrySupplier<DefaultParticleType> OXYGEN_BUBBLE = PARTICLE_TYPES.register("oxygen_bubble", () -> new DefaultParticleType(true) {
	});

	public static void register() {
		PARTICLE_TYPES.register();
		if (Platform.getEnvironment().equals(Env.CLIENT)) {
			ParticleProviderRegistry.register(ModParticleTypes.VENUS_RAIN.get(), WaterSplashParticle.Factory::new);
			ParticleProviderRegistry.register(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Factory::new);
			ParticleProviderRegistry.register(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Factory::new);
			ParticleProviderRegistry.register(ModParticleTypes.SMALL_FLAME.get(), SmallFlameParticle.Factory::new);
			ParticleProviderRegistry.register(ModParticleTypes.SMALL_SMOKE.get(), SmallFlameParticle.Factory::new);
			ParticleProviderRegistry.register(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Factory::new);
		}
	}
}
