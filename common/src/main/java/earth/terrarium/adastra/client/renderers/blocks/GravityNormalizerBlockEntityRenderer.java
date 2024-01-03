package earth.terrarium.adastra.client.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import earth.terrarium.adastra.common.blockentities.machines.GravityNormalizerBlockEntity;
import earth.terrarium.adastra.common.blocks.base.SidedMachineBlock;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import org.joml.Quaternionf;

public class GravityNormalizerBlockEntityRenderer implements BlockEntityRenderer<GravityNormalizerBlockEntity> {
    public static final ResourceLocation TOP = new ResourceLocation(AdAstra.MOD_ID, "block/gravity_normalizer_top");
    public static final ResourceLocation TOE = new ResourceLocation(AdAstra.MOD_ID, "block/gravity_normalizer_toe");

    private static final float SIN_45 = (float) Math.sin(Math.PI / 4);

    @Override
    public void render(GravityNormalizerBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        AttachFace face = entity.getBlockState().getValue(SidedMachineBlock.FACE);
        Direction direction = entity.getBlockState().getValue(SidedMachineBlock.FACING);

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

            float animation = Mth.lerp(partialTick, entity.lastAnimation(), entity.animation());
            render(entity.getBlockState(), animation, poseStack, buffer, packedLight, packedOverlay);
        }
    }

    private static void render(BlockState state, float animation, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BakedModel blockModel = ClientPlatformUtils.getModel(Minecraft.getInstance().getModelManager(), TOP);
        BakedModel toeModel = ClientPlatformUtils.getModel(Minecraft.getInstance().getModelManager(), TOE);

        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.pushPose();
            pose.translate(0.5, 0.7, 0.5);

            pose.mulPose(Axis.XP.rotationDegrees(animation));
            pose.mulPose(Axis.YP.rotationDegrees(animation));
            pose.mulPose(Axis.ZP.rotationDegrees(animation));

            float yRot = animation / 1.2f;
            pose.mulPose(Axis.YP.rotationDegrees(yRot));
            pose.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0, SIN_45));
            pose.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0, SIN_45));
            pose.mulPose(Axis.YP.rotationDegrees(yRot));
            pose.mulPose(new Quaternionf().setAngleAxis((float) (Math.PI / 3), SIN_45, 0, SIN_45));
            pose.mulPose(Axis.YP.rotationDegrees(yRot));

            pose.translate(-0.5, -0.7, -0.5);

            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                pose.last(),
                buffer.getBuffer(Sheets.cutoutBlockSheet()),
                state,
                blockModel,
                1, 1, 1,
                packedLight, packedOverlay);

            pose.popPose();

            for (int i = 0; i < 4; i++) {
                poseStack.pushPose();

                pose.translate(0.5, 0, 0.5);
                pose.mulPose(Axis.YP.rotationDegrees(90 * i));
                pose.translate(-0.5, 0, -0.5);

                pose.translate(0.27, 0.27, 0.27);
                pose.mulPose(Axis.XP.rotationDegrees(Mth.sin(animation / 50 + i) * 10));
                pose.translate(-0.27, -0.27, -0.27);

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                    pose.last(),
                    buffer.getBuffer(Sheets.cutoutBlockSheet()),
                    state,
                    toeModel,
                    1, 1, 1,
                    packedLight, packedOverlay);

                poseStack.popPose();
            }
        }
    }

    // Taken from geckolib
    protected void rotateBlock(Direction facing, PoseStack poseStack) {
        switch (facing) {
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case NORTH -> poseStack.mulPose(Axis.YP.rotationDegrees(0));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(270));
            case UP -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case DOWN -> poseStack.mulPose(Axis.XN.rotationDegrees(90));
        }
    }

    public static class ItemRenderer extends BlockEntityWithoutLevelRenderer {
        public ItemRenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
            BlockState state = BuiltInRegistries.BLOCK.get(BuiltInRegistries.ITEM.getKey(stack.getItem())).defaultBlockState();

            var minecraft = Minecraft.getInstance();
            float yRot = Util.getMillis() / 5f;

            try (var ignored = new CloseablePoseStack(poseStack)) {
                var model = minecraft.getBlockRenderer().getBlockModel(state);
                minecraft.getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
                    buffer.getBuffer(Sheets.cutoutBlockSheet()),
                    state,
                    model,
                    1, 1, 1,
                    packedLight, packedOverlay);
                render(state, yRot, poseStack, buffer, packedLight, packedOverlay);
            }
        }
    }
}
