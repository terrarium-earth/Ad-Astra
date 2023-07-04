package earth.terrarium.adastra.client.renderers.blocks.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.blocks.OxygenDistributorBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class OxygenDistributorRenderer extends GeoBlockRenderer<OxygenDistributorBlockEntity> {
    public static final ResourceLocation MODEL = new ResourceLocation(AdAstra.MOD_ID, "oxygen_distributor");

    public OxygenDistributorRenderer() {
        super(new DefaultedBlockGeoModel<>(MODEL));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, OxygenDistributorBlockEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        BlockState blockState = animatable.getBlockState();
        AttachFace face = blockState.getValue(OxygenDistributorBlock.FACE);
        Direction direction = blockState.getValue(OxygenDistributorBlock.FACING);

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
