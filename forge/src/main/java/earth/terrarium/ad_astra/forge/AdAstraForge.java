package earth.terrarium.ad_astra.forge;

import dev.architectury.platform.forge.EventBuses;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import earth.terrarium.ad_astra.registry.ModCommands;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.registry.forge.ModRegistryHelpersImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {
    public AdAstraForge() {
        EventBuses.registerModEventBus(AdAstra.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AdAstra.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(AdAstraForge::onClientSetup);
        bus.addListener(AdAstraForge::commonSetup);
        bus.addListener(AdAstraForge::onAttributes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AdAstraClientForge::init);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onRegisterCommands);
        ModRegistryHelpersImpl.REGISTRIES.values().forEach(deferredRegister -> deferredRegister.register(bus));
    }

    public static void onServerReloadListeners(AddReloadListenerEvent event) {
        AdAstra.onRegisterReloadListeners((id, listener) -> event.addListener(listener));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        AdAstra.postInit();
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        AdAstraClient.initializeClient();
        AdAstraClientForge.postInit();
    }

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(command -> command.accept(event.getDispatcher()));
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }
}
