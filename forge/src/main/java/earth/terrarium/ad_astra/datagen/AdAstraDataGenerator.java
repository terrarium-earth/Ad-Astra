package earth.terrarium.ad_astra.datagen;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.datagen.provider.client.ModBlockStateProvider;
import earth.terrarium.ad_astra.datagen.provider.client.ModItemModelProvider;
import earth.terrarium.ad_astra.datagen.provider.client.ModLangProvider;
import earth.terrarium.ad_astra.datagen.provider.server.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdAstra.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AdAstraDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        // Client
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator.getPackOutput(), existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(generator.getPackOutput(), existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLangProvider(generator.getPackOutput()));

        // Server
        generator.addProvider(event.includeServer(), new ModLootTableProvider(generator.getPackOutput()));
//        generator.addProvider(event.includeServer(), new ModRecipeProvider(generator));
//        ModBlockTagProvider blockTagProvider = new ModBlockTagProvider(generator, existingFileHelper);
//        generator.addProvider(event.includeServer(), blockTagProvider);
//        generator.addProvider(event.includeServer(), new ModItemTagProvider(generator, blockTagProvider, existingFileHelper));
    }
}
