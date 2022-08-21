package com.github.alexnijjar.beyond_earth.client.registry;

import com.github.alexnijjar.beyond_earth.client.particles.LargeFlameParticle;
import com.github.alexnijjar.beyond_earth.client.particles.OxygenBubbleParticle;
import com.github.alexnijjar.beyond_earth.client.particles.SmallFlameParticle;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
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

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.VENUS_RAIN, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LARGE_FLAME, LargeFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LARGE_SMOKE, LargeFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SMALL_FLAME, SmallFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SMALL_SMOKE, SmallFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.OXYGEN_BUBBLE, OxygenBubbleParticle.Factory::new);
    }
}
