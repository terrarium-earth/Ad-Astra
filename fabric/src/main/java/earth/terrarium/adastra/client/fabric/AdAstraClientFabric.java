package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.renderers.world.OxygenDistributorOverlayRenderer;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.renderer.RenderType;

public class AdAstraClientFabric {

    public static void init() {
        AdAstraClient.init();
        ClientTickEvents.START_CLIENT_TICK.register(AdAstraClient::clientTick);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);
        AdAstraClient.onRegisterParticles((particle, provider) -> ParticleFactoryRegistry.getInstance().register(particle, provider::create));
        AdAstraClient.onRegisterItemRenderers((item, renderer) -> BuiltinItemRendererRegistry.INSTANCE.register(item, renderer::renderByItem));
        AdAstraClient.onRegisterEntityLayers((location, definition) -> EntityModelLayerRegistry.registerModelLayer(location, definition::get));
        AdAstraClient.onRegisterHud(hud -> HudRenderCallback.EVENT.register(hud::renderHud));
        ModelLoadingPlugin.register(ctx -> AdAstraClient.onRegisterModels(ctx::addModels));
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx ->
            OxygenDistributorOverlayRenderer.render(ctx.matrixStack(), ctx.camera()));

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VENT.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOLAR_PANEL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WATER_PUMP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENERGIZER.get(), RenderType.cutout());
        ModBlocks.GLOBES.stream().forEach(globe -> BlockRenderLayerMap.INSTANCE.putBlock(globe.get(), RenderType.cutout()));
        ModBlocks.SLIDING_DOORS.stream().forEach(globe -> BlockRenderLayerMap.INSTANCE.putBlock(globe.get(), RenderType.cutout()));
    }
}
