package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.world.biome.CratersDensityFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;

public class ModDensityFunctionProvider {

    public static final ResourceKey<DensityFunction> CRATERS = register("craters");

    private static ResourceKey<DensityFunction> register(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        context.register(CRATERS, new CratersDensityFunction(1));
    }
}
