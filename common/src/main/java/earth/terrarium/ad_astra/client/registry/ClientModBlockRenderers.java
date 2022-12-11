package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import earth.terrarium.ad_astra.common.registry.ModBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ClientModBlockRenderers {

    public static void registerBlockRenderers(BlockRendererRegistry registry) {
        registry.register(ModBlockEntities.FLAG_BLOCK_ENTITY, FlagBlockEntityRenderer::new);
        registry.register(ModBlockEntities.GLOBE_BLOCK_ENTITY, GlobeBlockEntityRenderer::new);
        registry.register(ModBlockEntities.ENERGIZER, EnergizerBlockEntityRenderer::new);
        registry.register(ModBlockEntities.SLIDING_DOOR, SlidingDoorBlockEntityRenderer::new);
    }

    public static abstract class BlockRendererRegistry {
        public abstract <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> factory);
    }
}
