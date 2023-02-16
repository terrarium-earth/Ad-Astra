package earth.terrarium.ad_astra.forge;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.data.energy.EnergyNetworkManager;
import earth.terrarium.ad_astra.common.data.energy.EnergyNetworkVisibility;
import earth.terrarium.ad_astra.common.data.energy.forge.EnergyNetworkManagerImpl;
import earth.terrarium.ad_astra.common.registry.ModCommands;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {
    public AdAstraForge() {
        AdAstra.init();

        DeferredRegister<EnergyNetworkVisibility> visibilities = DeferredRegister.create(EnergyNetworkManager.VISIBILITY_REGISTRY_ID, AdAstra.MOD_ID);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        visibilities.makeRegistry(() -> new RegistryBuilder<EnergyNetworkVisibility>().setDefaultKey(EnergyNetworkManager.PRIVATE_NETWORK_ID));
        visibilities.register(EnergyNetworkManager.PRIVATE_NETWORK_ID.getPath(), () -> EnergyNetworkManager.PRIVATE_NETWORK);
        visibilities.register(EnergyNetworkManager.PUBLIC_NETWORK_ID.getPath(), () -> EnergyNetworkManager.PUBLIC_NETWORK);
        visibilities.register(bus);

        bus.addListener(AdAstraForge::onClientSetup);
        bus.addListener(AdAstraForge::commonSetup);
        bus.addListener(AdAstraForge::onAttributes);
        bus.addListener(AdAstraForge::onRegisterCreativeTabs);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AdAstraClientForge::init);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onRegisterCommands);
    }

    public static void onServerReloadListeners(AddReloadListenerEvent event) {
        PlanetData.onRegisterReloadListeners((id, listener) -> event.addListener(listener));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        AdAstra.postInit();
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        AdAstraClient.init();
        AdAstraClientForge.postInit();
    }

    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.registerCommands(command -> command.accept(event.getDispatcher()));
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void onRegisterCreativeTabs(CreativeModeTabEvent.Register event) {
        ModItems.onRegisterCreativeTabs((loc, item, items) -> event.registerCreativeModeTab(loc, b -> b
                .title(Component.translatable("itemGroup." + loc.getNamespace() + "." + loc.getPath()))
                .icon(() -> item.get().getDefaultInstance())
                .displayItems((featureFlagSet, output, bl) -> items.forEach(output::accept))
                .build()));
    }
}
