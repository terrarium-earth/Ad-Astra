package earth.terrarium.ad_astra.client.fabric;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.registry.ClientModBlockRenderers;
import earth.terrarium.ad_astra.client.registry.ClientModEntities;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
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
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class AdAstraClientFabric {

    public static void initializeClient() {
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterChestSprites(registry::register));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterSprites(registry::register));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> AdAstraClient.onRegisterModels(out));
        AdAstraClient.onRegisterItemRenderers(AdAstraClientFabric::registerItemRenderer);
        registerRenderers();
        AdAstraClient.onRegisterReloadListeners((id, listener) -> ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return id;
            }

            @Override
            public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
                return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        }));
    }

    private static void registerItemRenderer(ItemConvertible item, BuiltinModelItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), renderer::render);
    }

    private static void registerRenderers() {
        ClientModEntities.registerEntityLayers(new ClientModEntities.LayerDefinitionRegistry() {
            @Override
            public void register(EntityModelLayer location, Supplier<TexturedModelData> definition) {
                EntityModelLayerRegistry.registerModelLayer(location, definition::get);
            }
        });
        ClientModEntities.registerEntityRenderers(new ClientModEntities.EntityRendererRegistry() {
            @Override
            protected <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererFactory<T> factory) {
                EntityRendererRegistry.register(type.get(), factory);
            }
        });
        ClientModBlockRenderers.registerBlockRenderers(new ClientModBlockRenderers.BlockRendererRegistry() {
            @Override
            public <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererFactory<T> factory) {
                BlockEntityRendererRegistry.register(type.get(), factory);
            }
        });
    }
}
