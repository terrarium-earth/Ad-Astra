package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.world.biome.CratersDensityFunction;
import earth.terrarium.ad_astra.datagen.AdAstraDataGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ModDensityFunctionProvider implements DataProvider {
    private final PackOutput packOutput;

    public ModDensityFunctionProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return AdAstraDataGenerator.saveCodecValue(
                output,
                new ResourceLocation(AdAstra.MOD_ID, "craters"),
                new CratersDensityFunction(1f),
                CratersDensityFunction.DIRECT_CODEC,
                Registries.DENSITY_FUNCTION,
                packOutput
        );
    }

    @Override
    public String getName() {
        return "Density Functions";
    }
}
