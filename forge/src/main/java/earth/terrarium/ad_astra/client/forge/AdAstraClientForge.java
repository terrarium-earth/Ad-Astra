package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.registry.ClientModEntities;
import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;
import earth.terrarium.ad_astra.client.registry.ClientModParticles;
import earth.terrarium.ad_astra.common.config.forge.ForgeMenuConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AdAstraClientForge {

    private static final Map<Item, BlockEntityWithoutLevelRenderer> ITEM_RENDERERS = new HashMap<>();
    private static boolean hasInitializedRenderers = false;

    public static BlockEntityWithoutLevelRenderer getItemRenderer(ItemLike item) {
        return ITEM_RENDERERS.get(item.asItem());
    }

    private static void registerItemRenderer(ItemLike item, BlockEntityWithoutLevelRenderer renderer) {
        ITEM_RENDERERS.put(item.asItem(), renderer);
    }

    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        AdAstraClient.onRegisterModels(event::register);
    }

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(AdAstraClientForge::modelLoading);
        bus.addListener(AdAstraClientForge::onRegisterParticles);
        bus.addListener(AdAstraClientForge::onRegisterLayerDefinitions);
        bus.addListener(AdAstraClientForge::onClientReloadListeners);
        bus.addListener(AdAstraClientForge::onSetupItemColors);
        bus.addListener(AdAstraClientForge::onSetupBlockColors);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRegisterClientHud);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onClientTick);
        ForgeMenuConfig.register();
    }

    public static void postInit() {
        AdAstraClient.onRegisterItemRenderers(AdAstraClientForge::registerItemRenderer);
        AdAstraClient.onRegisterFluidRenderTypes(AdAstraClientForge::onRegisterFluidRenderTypes);
        AdAstraClient.onRegisterBlockRenderTypes(AdAstraClientForge::onRegisterBlockRenderTypes);
        hasInitializedRenderers = true;
    }

    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            ClientModKeybindings.onStartTick(Minecraft.getInstance());
        }
    }

    private static void onRegisterClientHud(RenderGuiEvent.Post event) {
        AdAstraClient.onRegisterHud(hud -> hud.renderHud(event.getPoseStack(), event.getPartialTick()));

    }

    private static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        ClientModParticles.onRegisterParticles((type, provider) -> event.register(type, provider::create));
    }

    private static void onRegisterFluidRenderTypes(RenderType type, Fluid fluid1, Fluid fluid2) {
        ItemBlockRenderTypes.setRenderLayer(fluid1, type);
        ItemBlockRenderTypes.setRenderLayer(fluid2, type);
    }

    private static void onRegisterBlockRenderTypes(RenderType type, List<Block> blocks) {
        blocks.forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, type));
    }

    public static void onClientReloadListeners(RegisterClientReloadListenersEvent event) {
        AdAstraClient.onRegisterReloadListeners((id, listener) -> event.registerReloadListener(listener));
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ClientModEntities.registerEntityLayers(new ClientModEntities.LayerDefinitionRegistry() {
            @Override
            public void register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
                event.registerLayerDefinition(location, definition);
            }
        });
    }

    public static void onSetupItemColors(RegisterColorHandlersEvent.Item event) {
        AdAstraClient.onAddItemColors(event::register);
    }

    public static void onSetupBlockColors(RegisterColorHandlersEvent.Block event) {
        AdAstraClient.onAddBlockColors(event::register);
    }

    public static boolean hasInitializedRenderers() {
        return hasInitializedRenderers;
    }
}
