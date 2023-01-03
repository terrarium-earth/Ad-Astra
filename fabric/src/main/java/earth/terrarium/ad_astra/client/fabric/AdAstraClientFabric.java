package earth.terrarium.ad_astra.client.fabric;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.registry.ClientModBlockRenderers;
import earth.terrarium.ad_astra.client.registry.ClientModEntities;
import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;
import earth.terrarium.ad_astra.client.registry.ClientModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class AdAstraClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AdAstraClient.init();
        ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterChestSprites(registry::register));
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((spriteAtlasTexture, registry) -> AdAstraClient.onRegisterSprites(registry::register));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> AdAstraClient.onRegisterModels(out));
        AdAstraClient.onRegisterHud(AdAstraClientFabric::registerHud);
        AdAstraClient.onRegisterFluidRenderTypes(AdAstraClientFabric::registerFluidRenderTypes);
        AdAstraClient.onRegisterBlockRenderTypes(AdAstraClientFabric::registerBlockRenderTypes);
        AdAstraClient.onRegisterItemRenderers(AdAstraClientFabric::registerItemRenderer);
        ClientModParticles.onRegisterParticles(AdAstraClientFabric::registerParticles);
        registerRenderers();
        initEvents();
        AdAstraClient.onRegisterReloadListeners((id, listener) -> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier synchronizer, ResourceManager manager, ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
                return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        }));
    }

    public static void initEvents() {
        ClientTickEvents.START_CLIENT_TICK.register(ClientModKeybindings::onStartTick);
    }

    private static void registerHud(AdAstraClient.RenderHud overlay) {
        HudRenderCallback.EVENT.register(overlay::renderHud);
    }

    private static void registerParticles(ParticleType<SimpleParticleType> particle, ClientModParticles.SpriteParticleRegistration<SimpleParticleType> provider) {
        ParticleFactoryRegistry.getInstance().register(particle, provider::create);
    }

    private static void registerItemRenderer(ItemLike item, BlockEntityWithoutLevelRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), renderer::renderByItem);
    }

    private static void registerFluidRenderTypes(RenderType type, Fluid fluid1, Fluid fluid2) {
        BlockRenderLayerMap.INSTANCE.putFluids(type, fluid1, fluid2);
    }

    private static void registerBlockRenderTypes(RenderType type, List<Block> blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(type, blocks.toArray(new Block[0]));
    }

    private static void registerRenderers() {
        ClientModEntities.registerEntityLayers(new ClientModEntities.LayerDefinitionRegistry() {
            @Override
            public void register(ModelLayerLocation location, Supplier<LayerDefinition> definition) {
                EntityModelLayerRegistry.registerModelLayer(location, definition::get);
            }
        });
        ClientModBlockRenderers.registerBlockRenderers(new ClientModBlockRenderers.BlockRendererRegistry() {
            @Override
            public <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> factory) {
                BlockEntityRendererRegistry.register(type.get(), factory);
            }
        });
    }
}
