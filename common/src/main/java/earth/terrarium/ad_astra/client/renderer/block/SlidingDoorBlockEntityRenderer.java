package earth.terrarium.ad_astra.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.util.ClientPlatformUtils;
import earth.terrarium.ad_astra.common.block.slidingdoor.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.block.slidingdoor.SlidingDoorBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;

public class SlidingDoorBlockEntityRenderer implements BlockEntityRenderer<SlidingDoorBlockEntity> {
    public SlidingDoorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(SlidingDoorBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float slide = Mth.lerp(partialTicks, entity.getPreviousSlideTicks(), entity.getSlideTicks()) / 81.0f;

        BakedModel doorModel = ClientPlatformUtils.getModel(Minecraft.getInstance().getModelManager(), new ResourceLocation(AdAstra.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(entity.getBlockState().getBlock()).getPath()));
        BakedModel doorModelFlipped = ClientPlatformUtils.getModel(Minecraft.getInstance().getModelManager(), new ResourceLocation(AdAstra.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(entity.getBlockState().getBlock()).getPath() + "_flipped"));
        Block type = entity.getBlockState().getBlock();

        float offset = 0;
        if (type.equals(ModBlocks.AIRLOCK.get())) {
            slide -= 0.094f;
            offset = 0.155f;
        } else if (type.equals(ModBlocks.REINFORCED_DOOR.get())) {
            slide -= 0.094f;
            offset = 0.095f;
        }

        Direction degrees = entity.getBlockState().getValue(SlidingDoorBlock.FACING);
        boolean shouldNotFlip = degrees == Direction.WEST || degrees == Direction.EAST;

        try (var ignored = new CloseablePoseStack(poseStack)) {
            if (degrees == Direction.NORTH) {
                poseStack.translate(-1.5f, 1.0f, 0.42f);
                poseStack.translate(-slide, 0.0f, 0.0);
                poseStack.translate(offset, 0.0f, 0.0);
            } else if (degrees == Direction.EAST) {
                poseStack.translate(0.38f, 1.0f, 1.0f);
                poseStack.translate(0.0f, 0.0f, -slide);
                poseStack.translate(0.0f, 0.0f, -offset);
            } else if (degrees == Direction.SOUTH) {
                poseStack.translate(2.5f, 1.0f, 0.56f);
                poseStack.translate(slide, 0.0f, 0.0);
                poseStack.translate(-offset, 0.0f, 0.0f);
            } else if (degrees == Direction.WEST) {
                poseStack.translate(0.56f, 1.0f, 0.0f);
                poseStack.translate(0.0f, 0.0f, slide);
                poseStack.translate(0.0f, 0.0f, offset);
            }
            poseStack.mulPose(Axis.YP.rotationDegrees(degrees.getOpposite().toYRot()));
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), entity.getBlockState(), shouldNotFlip ? doorModel : doorModelFlipped, 1f, 1f, 1f, packedLight, packedOverlay);
        }

        try (var ignored = new CloseablePoseStack(poseStack)) {
            if (degrees == Direction.NORTH) {
                poseStack.translate(0.0f, 1.0f, 0.42f);
                poseStack.translate(slide, 0.0f, 0.0);
                poseStack.translate(offset, 0.0f, 0.0);
            } else if (degrees == Direction.EAST) {
                poseStack.translate(0.38f, 1.0f, 2.5f);
                poseStack.translate(0.0f, 0.0f, slide);
                poseStack.translate(0.0f, 0.0f, -offset);
            } else if (degrees == Direction.SOUTH) {
                poseStack.translate(1.0f, 1.0f, 0.56f);
                poseStack.translate(-slide, 0.0f, 0.0);
                poseStack.translate(-offset, 0.0f, 0.0f);
            } else if (degrees == Direction.WEST) {
                poseStack.translate(0.56f, 1.0f, -1.5f);
                poseStack.translate(0.0f, 0.0f, -slide);
                poseStack.translate(0.0f, 0.0f, offset);
            }
            poseStack.mulPose(Axis.YP.rotationDegrees(degrees.getOpposite().toYRot()));
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), entity.getBlockState(), shouldNotFlip ? doorModelFlipped : doorModel, 1f, 1f, 1f, packedLight, packedOverlay);
        }
    }
}
