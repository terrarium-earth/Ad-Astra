package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.LaunchPadBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

import java.util.function.Supplier;

public class ClientModBlockRenderers {

    public static void registerBlockRenderers(BlockRendererRegistry registry) {
        registry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);
        registry.register(ModBlockEntities.LAUNCH_PAD, LaunchPadBlockEntityRenderer::new);
        registry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeBlockEntityRenderer::new);
        registry.register(ModBlockEntities.ENERGIZER, EnergizerBlockEntityRenderer::new);
        registry.register(ModBlockEntities.SLIDING_DOOR, SlidingDoorBlockEntityRenderer::new);
    }

    public static abstract class BlockRendererRegistry {
        public abstract <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererFactory<T> factory);
    }
}
