package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.world.biome.CratersDensityFunction;
import earth.terrarium.ad_astra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.function.BiConsumer;

public class ModDensityFunctionProvider extends ModCodecProvider<DensityFunction> {
    public ModDensityFunctionProvider(PackOutput packOutput) {
        super(packOutput, CratersDensityFunction.DIRECT_CODEC, Registries.DENSITY_FUNCTION);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, DensityFunction> consumer) {
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "craters"), new CratersDensityFunction(1));
    }

    @Override
    public String getName() {
        return "Density Functions";
    }
}
