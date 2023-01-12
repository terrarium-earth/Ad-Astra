package earth.terrarium.ad_astra.client.fabric;

import earth.terrarium.ad_astra.client.AdAstraClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class AdAstraClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AdAstraClient.init();
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> AdAstraClient.onRegisterModels(out));
        AdAstraClient.onRegisterFluidRenderTypes(AdAstraClientFabric::registerFluidRenderTypes);
        AdAstraClient.onRegisterBlockRenderTypes(AdAstraClientFabric::registerBlockRenderTypes);
        AdAstraClient.onRegisterItemRenderers(AdAstraClientFabric::registerItemRenderer);
    }

    private static void registerItemRenderer(ItemLike item, BlockEntityWithoutLevelRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item.asItem(), renderer::renderByItem);
    }

    private static void registerFluidRenderTypes(RenderType type, Fluid fluid) {
        BlockRenderLayerMap.INSTANCE.putFluids(type, fluid);
    }

    private static void registerBlockRenderTypes(RenderType type, List<Block> blocks) {
        BlockRenderLayerMap.INSTANCE.putBlocks(type, blocks.toArray(new Block[0]));
    }
}
