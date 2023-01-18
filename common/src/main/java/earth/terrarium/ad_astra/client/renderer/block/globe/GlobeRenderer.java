package earth.terrarium.ad_astra.client.renderer.block.globe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.client.util.ClientPlatformUtils;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GlobeRenderer implements BlockEntityRenderer<GlobeBlockEntity> {
    public GlobeRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(GlobeBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float yaw = Mth.lerp(partialTicks, entity.prevYaw, entity.getYaw());
        render(entity.getBlockState().getValue(GlobeBlock.FACING), entity.getBlockState(), yaw, poseStack, buffer, packedLight, packedOverlay);
    }

    public static class ItemRenderer extends BlockEntityWithoutLevelRenderer {
        private long prevWorldTime;

        public ItemRenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        }

        @Override
        public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
            BlockState state = BuiltInRegistries.BLOCK.get(BuiltInRegistries.ITEM.getKey(stack.getItem())).defaultBlockState();

            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.level == null) return;
            float partialTicks = minecraft.getFrameTime();
            float yaw = Mth.lerp(partialTicks, prevWorldTime, minecraft.level.getGameTime()) * -2.0f;
            prevWorldTime = minecraft.level.getGameTime();

            try (var ignored = new CloseablePoseStack(poseStack)) {
                BakedModel blockModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), state, blockModel, 1f, 1f, 1f, packedLight, packedOverlay);
                render(Direction.NORTH, state, yaw, poseStack, buffer, packedLight, packedOverlay);
            }
        }
    }

    private static void render(Direction dir, BlockState state, float yaw, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BakedModel blockModel = ClientPlatformUtils.getModel(Minecraft.getInstance().getModelManager(), new ResourceLocation(AdAstra.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath() + "_cube"));
        try (var ignored = new CloseablePoseStack(poseStack)) {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
            poseStack.translate(-0.5, 0, -0.5);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(Sheets.cutoutBlockSheet()), state, blockModel, 1f, 1f, 1f, packedLight, packedOverlay);
        }
    }
}
