package earth.terrarium.adastra.client.ti69;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.ti69.client.Ti69App;
import earth.terrarium.adastra.api.ti69.client.Ti69AppApi;
import earth.terrarium.adastra.common.items.Ti69Item;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Matrix4f;

public class Ti69Renderer {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/ti-69/ti-69.png");
    public static final ResourceLocation SCREEN = new ResourceLocation(AdAstra.MOD_ID, "textures/ti-69/screen.png");
    public static final ResourceLocation OVERLAY = new ResourceLocation(AdAstra.MOD_ID, "textures/ti-69/overlay.png");
    public static final ResourceLocation ICONS = new ResourceLocation(AdAstra.MOD_ID, "textures/ti-69/icons.png");

    public static void renderTi69(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float equippedProgress, HumanoidArm hand, float swingProgress, ArmRenderer armRenderer) {
        boolean rightHanded = hand == HumanoidArm.RIGHT;
        float f = rightHanded ? 1.0F : -1.0F;
        poseStack.translate(f * 0.125F, -0.125F, 0.0F);
        assert Minecraft.getInstance().player != null;
        if (!Minecraft.getInstance().player.isInvisible()) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.ZP.rotationDegrees(f * 10.0F));
            armRenderer.renderPlayerArm(poseStack, buffer, combinedLight, equippedProgress, swingProgress, hand);
            poseStack.popPose();
        }

        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.translate(f * 0.51F, -0.1F + equippedProgress * -1.2F, -0.75F);
            float g = Mth.sqrt(swingProgress);
            float h = Mth.sin(g * 3.1415927F);
            float i = -0.5F * h;
            float j = 0.4F * Mth.sin(g * 6.2831855F);
            float k = -0.3F * Mth.sin(swingProgress * 3.1415927F);
            pose.translate(f * i, j * h, k);
            pose.mulPose(Axis.XP.rotationDegrees(h * -45.0F));
            pose.mulPose(Axis.YP.rotationDegrees(f * h * -30.0F));
            float moveAmount = hand == HumanoidArm.RIGHT ? 0.1F : 0.09F;
            pose.translate(moveAmount, 0.0F, 0.0F);
            renderTi69(pose, buffer, combinedLight, rightHanded);
        }
    }

    public static void renderTi69(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, boolean rightHanded) {
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.scale(0.5F, 0.5F, 0.5F);
        poseStack.translate(-0.5F, -0.75F, 0.0F);
        poseStack.scale(0.0078125F, 0.0078125F, 0.0078125F);
        poseStack.scale(0.6f, 1.06f, 1);

        VertexConsumer vertex = buffer.getBuffer(RenderType.text(TEXTURE));
        Matrix4f matrix4f = poseStack.last().pose();
        vertex.vertex(matrix4f, -7.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(combinedLight).endVertex();
        vertex.vertex(matrix4f, 135.0F, 135.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(combinedLight).endVertex();
        vertex.vertex(matrix4f, 135.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(combinedLight).endVertex();
        vertex.vertex(matrix4f, -7.0F, -7.0F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(combinedLight).endVertex();

        Ti69App app = Ti69AppApi.API.get(Ti69Item.selectedApp);
        texture(poseStack, buffer, app.color(), 0.01f, SCREEN);
        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.scale(1.2f, 0.7f, 0.7f);
            pose.translate(15.0f, 25.0f, 0.0f);
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) return;
            app.render(pose, buffer, pose.last().pose(), Minecraft.getInstance().font, level, rightHanded);
        }
        texture(poseStack, buffer, 0xFFFFFFFF, -0.01f, OVERLAY);
    }

    private static void texture(PoseStack poseStack, MultiBufferSource buffer, int color, float z, ResourceLocation overlay) {
        try (var pose = new CloseablePoseStack(poseStack)) {
            pose.scale(0.95f, 0.392f, 1.0f);
            pose.translate(21.0f, 48.0f, z);
            VertexConsumer screenVertex = buffer.getBuffer(RenderType.text(overlay));
            Matrix4f matrix4f = pose.last().pose();
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;
            screenVertex.vertex(matrix4f, -7.0F, 100.0F, 0.0F).color(red, green, blue, 255).uv(0.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
            screenVertex.vertex(matrix4f, 100.0F, 100.0F, 0.0F).color(red, green, blue, 255).uv(1.0F, 1.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
            screenVertex.vertex(matrix4f, 100.0F, -7.0F, 0.0F).color(red, green, blue, 255).uv(1.0F, 0.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
            screenVertex.vertex(matrix4f, -7.0F, -7.0F, 0.0F).color(red, green, blue, 255).uv(0.0F, 0.0F).uv2(LightTexture.FULL_BRIGHT).endVertex();
        }
    }

    @FunctionalInterface
    public interface ArmRenderer {
        void renderPlayerArm(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, float equippedProgress, float swingProgress, HumanoidArm side);
    }
}