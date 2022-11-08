package earth.terrarium.ad_astra.client.screens.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.AdAstra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class ScreenUtils {

    public static Component createText(String text) {
        return Component.translatable("gui." + AdAstra.MOD_ID + ".text." + text);
    }

    public static Component createText(ResourceLocation text) {
        return Component.translatable("gui." + text.getNamespace() + ".text." + text.getPath());
    }

    public static void addTexture(PoseStack poseStack, int x, int y, int width, int height, ResourceLocation texture) {
        RenderSystem.setShaderTexture(0, texture);
        GuiComponent.blit(poseStack, x, y, 0, 0, width, height, width, height);
    }

    public static void addRotatingTexture(PlanetSelectionScreen screen, PoseStack poseStack, int x, int y, int width, int height, ResourceLocation texture, float speed) {

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;

        x *= scale;
        y *= scale;
        x += 1;
        y += 1;

        width *= scale;
        height *= scale;

        poseStack.pushPose();

        poseStack.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(screen.getGuiTime() * (speed / 3.0f)));

        addTexture(poseStack, x, y, width, height, texture);

        poseStack.popPose();
    }

    public static void drawCircle(double x, double y, double radius, int sides, Color ringColour) {

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;
        radius *= scale;

        double width = radius - 0.6;
        for (double i = width; i < radius - 0.5 + 1; i += 0.1) {
            bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
            for (int j = 0; j <= sides; j++) {
                double angle = (Math.PI * 2 * j / sides) + Math.toRadians(180);
                bufferBuilder.vertex(x + Math.sin(angle) * i, y + Math.cos(angle) * i, 0)
                        .color(ringColour.getIntRed(), ringColour.getIntGreen(), ringColour.getIntBlue(), ringColour.getIntAlpha())
                        .endVertex();
            }
            tessellator.end();
        }
    }

    public static void drawLine(int x1, int x2, int y1, int y2, int colour) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bufferBuilder.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(x1, y1, 0).color((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f).endVertex();
        bufferBuilder.vertex(x2, y2, 0).color((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f).endVertex();
        tessellator.end();
    }
}