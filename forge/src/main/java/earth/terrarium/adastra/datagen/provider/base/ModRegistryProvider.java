package earth.terrarium.adastra.datagen.provider.base;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.datagen.provider.server.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModRegistryProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.DAMAGE_TYPE, ModDamageTypeProvider::bootstrap)
        .add(Registries.DIMENSION_TYPE, ModDimensionTypeProvider::bootstrap)
        .add(Registries.BIOME, ModBiomeDataProvider::bootstrap)
        .add(Registries.NOISE_SETTINGS, ModNoiseGeneratorSettingsProvider::bootstrap)
        .add(Registries.LEVEL_STEM, ModDimensionProvider::bootstrap);

    public ModRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(AdAstra.MOD_ID));
    }
}
