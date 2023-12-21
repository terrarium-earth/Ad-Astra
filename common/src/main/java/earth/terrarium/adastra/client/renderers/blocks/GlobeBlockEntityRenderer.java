package earth.terrarium.adastra.client.renderers.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import earth.terrarium.adastra.common.blockentities.GlobeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;


public class GlobeBlockEntityRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

    @Override
    public void render(GlobeBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float yRot = Mth.lerp(partialTick, entity.lastYRot(), entity.yRot());
        render(entity.getBlockState(), yRot, poseStack, buffer, packedLight, packedOverlay);
    }

    private static void render(BlockState state, float yRot, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
        BakedModel blockModel = ClientPlatformUtils.getModel(
            Minecraft.getInstance().getModelManager(),
            new ResourceLocation(AdAstra.MOD_ID, "block/%s_cube".formatted(blockId)));

        try (var ignored = new CloseablePoseStack(poseStack)) {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
            poseStack.translate(-0.5, 0, -0.5);
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                poseStack.last(),
                buffer.getBuffer(Sheets.cutoutBlockSheet()),
                state,
                blockModel,
                1f, 1f, 1f,
                packedLight, packedOverlay);
        }
    }

    public static class ItemRenderer extends BlockEntityWithoutLevelRenderer {
        private long lastTime;

        public ItemRenderer() {
            super(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        }

        @Override
        public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
            BlockState state = BuiltInRegistries.BLOCK.get(BuiltInRegistries.ITEM.getKey(stack.getItem())).defaultBlockState();

            var minecraft = Minecraft.getInstance();
            if (minecraft.level == null) return;
            float partialTick = minecraft.getFrameTime();
            float yRot = Mth.lerp(partialTick, lastTime, minecraft.level.getGameTime()) * -2.0f;
            lastTime = minecraft.level.getGameTime();

            try (var ignored = new CloseablePoseStack(poseStack)) {
                var model = minecraft.getBlockRenderer().getBlockModel(state);
                minecraft.getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
                    buffer.getBuffer(Sheets.cutoutBlockSheet()),
                    state,
                    model,
                    1f, 1f, 1f,
                    packedLight, packedOverlay);
                render(state, yRot, poseStack, buffer, packedLight, packedOverlay);
            }
        }
    }
}
