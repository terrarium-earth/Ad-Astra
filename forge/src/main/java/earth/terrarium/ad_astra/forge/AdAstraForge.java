package earth.terrarium.ad_astra.forge;

import com.mojang.brigadier.CommandDispatcher;
import dev.architectury.platform.forge.EventBuses;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.forge.AdAstraClientForge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {
    public static final List<Consumer<CommandDispatcher<CommandSourceStack>>> COMMANDS = new ArrayList<>();

    public AdAstraForge() {
        EventBuses.registerModEventBus(AdAstra.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AdAstra.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(AdAstraForge::onClientSetup);
        modEventBus.addListener(AdAstraForge::commonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> AdAstraClientForge::init);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onServerReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(AdAstraForge::onRegisterCommands);
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
        COMMANDS.forEach(command -> command.accept(event.getDispatcher()));
    }
}
