package earth.terrarium.ad_astra.client.forge;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.registry.ClientModBlockRenderers;
import earth.terrarium.ad_astra.client.registry.ClientModEntities;
import earth.terrarium.ad_astra.config.forge.ForgeMenuConfig;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
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


    public static void chestSpriteLoading(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
            AdAstraClient.onRegisterChestSprites(event::addSprite);
        }
    }

    public static void spriteLoading(TextureStitchEvent.Pre event) {
        if (event.getAtlas().location().equals(InventoryMenu.BLOCK_ATLAS)) {
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
        modEventBus.addListener(AdAstraClientForge::onClientReloadListeners);
        ForgeMenuConfig.register();
    }

    public static void onClientReloadListeners(RegisterClientReloadListenersEvent event) {
        AdAstraClient.onRegisterReloadListeners((id, listener) -> event.registerReloadListener(listener));
    }

    public static void postInit() {
        AdAstraClient.onRegisterItemRenderers(AdAstraClientForge::registerItemRenderer);
        hasInitializedRenderers = true;
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ClientModEntities.registerEntityRenderers(new ClientModEntities.EntityRendererRegistry() {
            @Override
            protected <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory) {
                event.registerEntityRenderer(type.get(), factory);
            }
        });
        ClientModBlockRenderers.registerBlockRenderers(new ClientModBlockRenderers.BlockRendererRegistry() {
            @Override
            public <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> factory) {
                event.registerBlockEntityRenderer(type.get(), factory);
            }
        });
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        ClientModEntities.registerEntityLayers(new ClientModEntities.LayerDefinitionRegistry() {
            @Override
            public void register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
                event.registerLayerDefinition(location, definition);
            }
        });
    }

    public static boolean hasInitializedRenderers() {
        return hasInitializedRenderers;
    }
}
