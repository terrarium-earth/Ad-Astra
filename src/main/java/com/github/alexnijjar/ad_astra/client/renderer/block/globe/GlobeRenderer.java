package com.github.alexnijjar.ad_astra.client.renderer.block.globe;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class GlobeRenderer implements BlockEntityRenderer<GlobeBlockEntity> {

    public GlobeRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(GlobeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float yaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
        render(entity.getCachedState(), yaw, matrices, vertexConsumers, light, overlay);
    }

    private static void render(BlockState state, float yaw, MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, int packedOverlay) {
        BakedModel blockModel = BakedModelManagerHelper.getModel(MinecraftClient.getInstance().getBakedModelManager(), new Identifier(AdAstra.MOD_ID, "block/" + Registry.BLOCK.getId(state.getBlock()).getPath() + "_cube"));
        poseStack.push();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-yaw));
        poseStack.translate(-0.5, 0, -0.5);
        MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(poseStack.peek(), buffer.getBuffer(TexturedRenderLayers.getEntityCutout()), state, blockModel, 1f, 1f, 1f, packedLight, packedOverlay);
        poseStack.pop();
    }

    public static class ItemRenderer extends BuiltinModelItemRenderer {
        private long prevWorldTime;

        public ItemRenderer() {
            super(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());
        }

        @Override
        public void render(ItemStack stack, ModelTransformation.Mode transformType, MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, int packedOverlay) {
            BlockState state = Registry.BLOCK.get(Registry.ITEM.getId(stack.getItem())).getDefaultState();

            MinecraftClient minecraft = MinecraftClient.getInstance();
            if (minecraft.world == null) return;
            float partialTicks = minecraft.getTickDelta();
            float yaw = MathHelper.lerp(partialTicks, prevWorldTime, minecraft.world.getTime()) * -2.0f;
            prevWorldTime = minecraft.world.getTime();

            poseStack.push();
            BakedModel blockModel = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
            MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(poseStack.peek(), buffer.getBuffer(TexturedRenderLayers.getEntityCutout()), state, blockModel, 1f, 1f, 1f, packedLight, packedOverlay);
            GlobeRenderer.render(state, yaw, poseStack, buffer, packedLight, packedOverlay);
            poseStack.pop();
        }
    }
}