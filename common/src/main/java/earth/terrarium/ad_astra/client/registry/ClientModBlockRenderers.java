package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.renderer.block.EnergizerBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.SlidingDoorBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.flag.FlagBlockEntityRenderer;
import earth.terrarium.ad_astra.client.renderer.block.globe.GlobeBlockEntityRenderer;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ClientModBlockRenderers {

    public static void registerBlockRenderers(BlockRendererRegistry registry) {
        registry.register(ModBlockEntityTypes.FLAG, FlagBlockEntityRenderer::new);
        registry.register(ModBlockEntityTypes.GLOBE, GlobeBlockEntityRenderer::new);
        registry.register(ModBlockEntityTypes.ENERGIZER, EnergizerBlockEntityRenderer::new);
        registry.register(ModBlockEntityTypes.SLIDING_DOOR, SlidingDoorBlockEntityRenderer::new);
    }

    public static abstract class BlockRendererRegistry {
        public abstract <T extends BlockEntity> void register(Supplier<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> factory);
    }
}
