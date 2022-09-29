package com.github.alexnijjar.ad_astra.client.fabric;

import com.github.alexnijjar.ad_astra.client.AdAstraClient;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.ItemConvertible;
import net.minecraft.screen.PlayerScreenHandler;

public class AdAstraClientFabric {

    public static void init() {
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterChestSprites(registry::register));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterSprites(registry::register));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> AdAstraClient.onRegisterModels(out));
        AdAstraClient.onRegisterItemRenderers(AdAstraClientFabric::registerItemRenderer);
    }

    private static void registerItemRenderer(ItemConvertible item, BuiltinModelItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), renderer::render);
    }
}
