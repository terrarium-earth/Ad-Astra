//package earth.terrarium.adastra.client.renderers.blocks;
//
//import earth.terrarium.adastra.common.blockentities.SlidingDoorBlockEntity;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.world.phys.AABB;
//import net.msrandom.classextensions.ClassExtension;
//import net.msrandom.classextensions.ExtensionInject;
//
//@ClassExtension(SlidingDoorBlockEntityRenderer.class)
//public abstract class SlidingDoorBlockEntityRendererExtension implements BlockEntityRenderer<SlidingDoorBlockEntity> {
//
//    @ExtensionInject
//    @Override
//    public AABB getRenderBoundingBox(SlidingDoorBlockEntity blockEntity) {
//        return new AABB(blockEntity.getBlockPos()).inflate(3);
//    }
//}