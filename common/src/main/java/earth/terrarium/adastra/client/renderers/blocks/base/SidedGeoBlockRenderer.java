package earth.terrarium.adastra.client.renderers.blocks.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.common.blocks.base.SidedMachineBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class SidedGeoBlockRenderer<T extends BlockEntity & GeoAnimatable> extends CustomGeoBlockRenderer<T> {

    public SidedGeoBlockRenderer(RegistryEntry<Block> block) {
        super(block);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        BlockState blockState = animatable.getBlockState();
        AttachFace face = blockState.getValue(SidedMachineBlock.FACE);
        Direction direction = blockState.getValue(SidedMachineBlock.FACING);

        try (var pose = new CloseablePoseStack(poseStack)) {
            if (face == AttachFace.CEILING) {
                pose.translate(0, 1, 1);
                pose.mulPose(Axis.XP.rotationDegrees(180));
            } else if (face == AttachFace.FLOOR) {
                pose.mulPose(Axis.XP.rotationDegrees(0));
            } else {
                if (direction == Direction.NORTH) {
                    pose.translate(0, 0, 1);
                } else if (direction == Direction.SOUTH) {
                    pose.translate(1, 0, 0);
                } else if (direction == Direction.WEST) {
                    pose.translate(1, 0, 1);
                }
                rotateBlock(direction, pose);
                pose.mulPose(Axis.XP.rotationDegrees(-90));
            }
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}
