package com.github.alexnijjar.ad_astra.client.forge;

import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.client.registry.ClientModBlockRenderers;
import com.github.alexnijjar.ad_astra.client.registry.ClientModEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

    public static void spriteLoading(TextureStitchEvent.Pre event) {
        if (event.getAtlas().getId().equals(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE)) {
            AdAstraClient.onRegisterSprites(event::addSprite);
        }
    }

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(AdAstraClientForge::modelLoading);
        modEventBus.addListener(AdAstraClientForge::spriteLoading);
        modEventBus.addListener(AdAstraClientForge::chestSpriteLoading);
        modEventBus.addListener(AdAstraClientForge::onRegisterRenderers);
        modEventBus.addListener(AdAstraClientForge::onRegisterLayerDefinitions);
    }

    public static void postInit() {
        AdAstraClient.onRegisterItemRenderers(AdAstraClientForge::registerItemRenderer);
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientModEntities.registerEntityRenderers(new ClientModEntities.EntityRendererRegistry() {
            @Override
            protected <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererFactory<T> factory) {
                event.registerEntityRenderer(type.get(), factory);
            }
        });
        ClientModBlockRenderers.registerBlockRenderers(new ClientModBlockRenderers.BlockRendererRegistry() {
            @Override
            public <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererFactory<T> factory) {
                event.registerBlockEntityRenderer(type.get(), factory);
            }
        });
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ClientModEntities.registerEntityLayers(new ClientModEntities.LayerDefinitionRegistry() {
            @Override
            public void register(EntityModelLayer location, Supplier<TexturedModelData> definition) {
                event.registerLayerDefinition(location, definition);
            }
        });
    }
}
