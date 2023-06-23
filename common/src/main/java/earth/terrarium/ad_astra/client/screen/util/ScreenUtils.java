package earth.terrarium.ad_astra.client.screen.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ScreenUtils {

    public static Component createText(String text) {
        return Component.translatable("gui." + AdAstra.MOD_ID + ".text." + text);
    }

    public static Component createText(ResourceLocation text) {
        return Component.translatable("gui." + text.getNamespace() + ".text." + text.getPath());
    }

    public static void addTexture(GuiGraphics graphics, int x, int y, int width, int height, ResourceLocation texture) {
        graphics.blit(texture, x, y, 0, 0, width, height, width, height);
    }

    public static void addRotatingTexture(PlanetSelectionScreen screen, GuiGraphics graphics, int x, int y, int width, int height, ResourceLocation texture, float speed) {

        double scale = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 400.0;

        x *= scale;
        y *= scale;
        x += 1;
        y += 1;

        width *= scale;
        height *= scale;

        graphics.pose().pushPose();

        graphics.pose().translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(screen.getGuiTime() * (speed / 3.0f)));

        addTexture(graphics, x, y, width, height, texture);

        graphics.pose().popPose();
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