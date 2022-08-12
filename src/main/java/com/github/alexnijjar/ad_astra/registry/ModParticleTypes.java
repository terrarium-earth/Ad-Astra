package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class ModParticleTypes {

    public static final DefaultParticleType VENUS_RAIN = FabricParticleTypes.simple();
    public static final DefaultParticleType LARGE_FLAME = FabricParticleTypes.simple(true);
    public static final DefaultParticleType LARGE_SMOKE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType SMALL_FLAME = FabricParticleTypes.simple(true);
    public static final DefaultParticleType SMALL_SMOKE = FabricParticleTypes.simple(true);
    public static final DefaultParticleType OXYGEN_BUBBLE = FabricParticleTypes.simple(true);

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("venus_rain"), VENUS_RAIN);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("large_flame"), LARGE_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("large_smoke"), LARGE_SMOKE);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("small_flame"), SMALL_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("small_smoke"), SMALL_SMOKE);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("oxygen_bubble"), OXYGEN_BUBBLE);
    }
}
