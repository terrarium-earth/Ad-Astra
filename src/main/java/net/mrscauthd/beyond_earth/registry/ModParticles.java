package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModParticles {

    public static final DefaultParticleType LARGE_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType LARGE_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_SMOKE = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("large_flame"), LARGE_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("large_smoke"), LARGE_SMOKE);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("small_flame"), SMALL_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new ModIdentifier("small_smoke"), SMALL_SMOKE);
    }
}
