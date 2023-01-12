package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticleTypes {
    public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, AdAstra.MOD_ID);
}
