package earth.terrarium.adastra.datagen;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.datagen.provider.base.ModRegistryProvider;
import earth.terrarium.adastra.datagen.provider.client.ModBlockStateProvider;
import earth.terrarium.adastra.datagen.provider.client.ModItemModelProvider;
import earth.terrarium.adastra.datagen.provider.client.ModLangProvider;
import earth.terrarium.adastra.datagen.provider.server.ModPlanetProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = AdAstra.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AdAstraDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModRegistryProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModPlanetProvider(packOutput));
    }
}
