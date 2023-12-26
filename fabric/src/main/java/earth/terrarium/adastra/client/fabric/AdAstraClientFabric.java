package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.dimension.ModEffects;
import earth.terrarium.adastra.client.renderers.world.OxygenDistributorOverlayRenderer;
import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
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
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> OxygenDistributorOverlayRenderer.render(ctx.matrixStack(), ctx.camera()));
        AdAstraClient.onAddItemColors(ColorProviderRegistry.ITEM::register);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOLAR_PANEL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WATER_PUMP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENERGIZER.get(), RenderType.cutout());
        ModBlocks.GLOBES.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.INDUSTRIAL_LAMPS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.SMALL_INDUSTRIAL_LAMPS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));

        ModEffects.onRegisterDimensionSpecialEffects((dimension, effects) -> {
            DimensionRenderingRegistry.registerDimensionEffects(dimension.location(), effects);
            if (effects.hasCustomClouds()) {
                DimensionRenderingRegistry.registerCloudRenderer(dimension, context -> {
                    var camera = context.camera().getPosition();
                    effects.renderClouds(
                        context.world(),
                        DimensionUtils.getTicks(),
                        context.tickDelta(),
                        context.matrixStack(),
                        camera.x, camera.y, camera.z,
                        context.projectionMatrix());
                });
            }

            if (effects.hasCustomSky()) {
                DimensionRenderingRegistry.registerSkyRenderer(dimension, context -> effects.renderSky(
                    context.world(),
                    DimensionUtils.getTicks(),
                    context.tickDelta(),
                    context.matrixStack(),
                    context.camera(),
                    context.projectionMatrix(),
                    false,
                    () -> {}
                ));
            }

            if (effects.hasCustomWeather()) {
                DimensionRenderingRegistry.registerWeatherRenderer(dimension, context -> {
                    var camera = context.camera().getPosition();
                    effects.renderSnowAndRain(
                        context.world(),
                        DimensionUtils.getTicks(),
                        context.tickDelta(),
                        context.lightmapTextureManager(),
                        camera.x, camera.y, camera.z
                    );
                });
            }
        });
    }
}
