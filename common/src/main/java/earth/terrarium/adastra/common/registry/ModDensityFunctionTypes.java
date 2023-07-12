package earth.terrarium.adastra.common.registry;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.world.biome.CratersDensityFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.DensityFunction;

public class ModDensityFunctionTypes {
    public static final ResourcefulRegistry<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = ResourcefulRegistries.create(BuiltInRegistries.DENSITY_FUNCTION_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<Codec<CratersDensityFunction>> CRATERS = DENSITY_FUNCTION_TYPES.register("craters", CratersDensityFunction.CODEC::codec);
}
