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
import net.minecraft.particle.ParticleEffect;
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
		if (Platform.getEnvironment() == Env.CLIENT) {
			register(ModParticleTypes.VENUS_RAIN, WaterSplashParticle.Factory::new);
			register(ModParticleTypes.LARGE_FLAME, LargeFlameParticle.Factory::new);
			register(ModParticleTypes.LARGE_SMOKE, LargeFlameParticle.Factory::new);
			register(ModParticleTypes.SMALL_FLAME, SmallFlameParticle.Factory::new);
			register(ModParticleTypes.SMALL_SMOKE, SmallFlameParticle.Factory::new);
			register(ModParticleTypes.OXYGEN_BUBBLE, OxygenBubbleParticle.Factory::new);
		}
	}

	public static <T extends ParticleEffect > void register(RegistrySupplier<? extends ParticleType<T>> supplier, ParticleProviderRegistry.DeferredParticleProvider<T> provider) {
		supplier.listen(it -> ParticleProviderRegistry.register(it, provider));
	}
}
