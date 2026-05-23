package earth.terrarium.adastra.client.renderers.blocks;

import earth.terrarium.adastra.common.blockentities.flag.FlagBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.AABB;
import net.msrandom.classextensions.ClassExtension;
import net.msrandom.classextensions.ExtensionInject;

@ClassExtension(FlagBlockEntityRenderer.class)
public abstract class FlagBlockEntityRendererExtension implements BlockEntityRenderer<FlagBlockEntity> {

    @ExtensionInject
    @Override
    public AABB getRenderBoundingBox(FlagBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(2);
    }
}
