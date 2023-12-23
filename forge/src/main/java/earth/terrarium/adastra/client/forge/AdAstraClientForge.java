package earth.terrarium.adastra.client.forge;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.renderers.world.OxygenDistributorOverlayRenderer;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdAstraClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(AdAstraClient::init);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRenderLevelStage);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRegisterClientHud);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onSetupItemColors);

        //noinspection deprecation
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.VENT.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void postInit(FMLClientSetupEvent event) {
    }

    @SubscribeEvent
    public static void onRegisterKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        AdAstraClient.onRegisterParticles((type, provider) -> event.registerSpriteSet(type, provider::create));
    }

    @SubscribeEvent
    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        AdAstraClient.onRegisterModels(event::register);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        AdAstraClient.onRegisterEntityLayers(event::registerLayerDefinition);
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            AdAstraClient.clientTick(Minecraft.getInstance());
        }
    }

    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            OxygenDistributorOverlayRenderer.render(event.getPoseStack(), event.getCamera());
        }
    }

    private static void onRegisterClientHud(RenderGuiEvent.Post event) {
        AdAstraClient.onRegisterHud(hud -> hud.renderHud(event.getGuiGraphics(), event.getPartialTick()));
    }

    private static void onSetupItemColors(RegisterColorHandlersEvent.Item event) {
        AdAstraClient.onAddItemColors(event::register);
    }
}
