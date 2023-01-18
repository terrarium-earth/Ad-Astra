package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.common.config.forge.ForgeMenuConfig;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

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
        bus.addListener(AdAstraClientForge::onClientReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRegisterClientHud);
        ForgeMenuConfig.register();
    }

    public static void postInit() {
        AdAstraClient.onRegisterItemRenderers(AdAstraClientForge::registerItemRenderer);
        AdAstraClient.onRegisterFluidRenderTypes(AdAstraClientForge::onRegisterFluidRenderTypes);
        hasInitializedRenderers = true;
    }

    private static void onRegisterClientHud(RenderGuiEvent.Post event) {
        AdAstraClient.onRegisterHud(hud -> hud.renderHud(event.getPoseStack(), event.getPartialTick()));

    }

    private static void onRegisterFluidRenderTypes(RenderType type, Fluid fluid) {
        ItemBlockRenderTypes.setRenderLayer(fluid, type);
    }

    public static void onClientReloadListeners(RegisterClientReloadListenersEvent event) {
        AdAstraClient.onRegisterReloadListeners((id, listener) -> event.registerReloadListener(listener));
    }

    public static boolean hasInitializedRenderers() {
        return hasInitializedRenderers;
    }
}
