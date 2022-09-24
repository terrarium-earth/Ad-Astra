package com.github.alexnijjar.ad_astra.client.forge;

import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

public class AdAstraClientForge {

    private static final Map<Item, BuiltinModelItemRenderer> ITEM_RENDERERS = new HashMap<>();

    public static BuiltinModelItemRenderer getItemRenderer(ItemConvertible item) {
        return ITEM_RENDERERS.get(item.asItem());
    }

    private static void registerItemRenderer(ItemConvertible item, BuiltinModelItemRenderer renderer) {
        ITEM_RENDERERS.put(item.asItem(), renderer);
    }

    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        AdAstraClient.onRegisterModels(event::register);
    }

    public static void chestSpriteLoading(TextureStitchEvent.Pre event) {
        if (event.getAtlas().getId().equals(TexturedRenderLayers.CHEST_ATLAS_TEXTURE)) {
            AdAstraClient.onRegisterChestSprites(event::addSprite);
        }
    }

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdAstraClientForge::modelLoading);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdAstraClientForge::chestSpriteLoading);

        AdAstraClient.onRegisterItemRenderers(AdAstraClientForge::registerItemRenderer);
    }
}
