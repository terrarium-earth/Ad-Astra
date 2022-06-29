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
            registry.register(new ModIdentifier("particles/venus_rain"));
            registry.register(new ModIdentifier("particles/large_flame"));
            registry.register(new ModIdentifier("particles/large_smoke"));
            registry.register(new ModIdentifier("particles/small_flame"));
            registry.register(new ModIdentifier("particles/small_smoke"));
        }));

        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.VENUS_RAIN, WaterSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LARGE_FLAME, LargeFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.LARGE_SMOKE, LargeFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SMALL_FLAME, SmallFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.SMALL_SMOKE, SmallFlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleTypes.OXYGEN_BUBBLE, OxygenBubbleParticle.Factory::new);
    }
}
