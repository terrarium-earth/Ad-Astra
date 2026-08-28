package earth.terrarium.adastra.datagen;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModPaintingVariants;
import earth.terrarium.adastra.datagen.provider.base.StructureUpdater;
import earth.terrarium.adastra.datagen.provider.client.*;
import earth.terrarium.adastra.datagen.provider.server.ModAdvancementProvider;
import earth.terrarium.adastra.datagen.provider.server.ModLootTableProvider;
import earth.terrarium.adastra.datagen.provider.server.ModPlanetProvider;
import earth.terrarium.adastra.datagen.provider.server.ModRecipeProvider;
import earth.terrarium.adastra.datagen.provider.server.registry.*;
import earth.terrarium.adastra.datagen.provider.server.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = AdAstra.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class AdAstraDataGenerator {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.DAMAGE_TYPE, ModDamageTypeProvider::bootstrap)
        .add(Registries.DIMENSION_TYPE, ModDimensionTypeProvider::bootstrap)
        .add(Registries.BIOME, ModBiomeDataProvider::bootstrap)
        .add(Registries.NOISE_SETTINGS, ModNoiseGeneratorSettingsProvider::bootstrap)
        .add(Registries.LEVEL_STEM, ModDimensionProvider::bootstrap)
        .add(Registries.CONFIGURED_CARVER, ModConfiguredCarverProvider::bootstrap)
        .add(Registries.DENSITY_FUNCTION, ModDensityFunctionProvider::bootstrap)
        .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatureProvider::bootstrap)
        .add(Registries.PLACED_FEATURE, ModPlacedFeatureProvider::bootstrap)
        .add(Registries.PAINTING_VARIANT, ModPaintingVariants::bootstrap);

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.createDatapackRegistryObjects(BUILDER);

        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModHighlightBlockStateProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModPlanetRendererProvider(packOutput));

        generator.addProvider(event.includeServer(), new ModPlanetProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModLootTableProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModFluidTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPaintingVariantTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModDamageSourceTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(true, new StructureUpdater("structures", AdAstra.MOD_ID, existingFileHelper, packOutput));
    }
}
