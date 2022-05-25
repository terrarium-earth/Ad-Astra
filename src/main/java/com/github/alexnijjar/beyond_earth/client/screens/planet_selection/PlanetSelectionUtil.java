package com.github.alexnijjar.beyond_earth.client.screens.planet_selection;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class PlanetSelectionUtil {

    public static Text createText(String text) {
        return new TranslatableText("gui." + BeyondEarth.MOD_ID + ".planet_selection." + text);
    }

    public static void addTexture(MatrixStack matrices, int x, int y, int width, int height, Identifier texture) {
        RenderSystem.setShaderTexture(0, texture);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, width, height, width, height);
    }

    public static void addRotatingTexture(PlanetSelectionScreen screen, MatrixStack matrices, int x, int y, int width, int height, Identifier texture, float speed) {

        matrices.push();

        matrices.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(screen.getGuiTime() * (speed / 3.0f)));

        addTexture(matrices, x, y, width, height, texture);

        matrices.translate(-screen.width / 2.0f, -screen.height / 2.0f, 0);
        matrices.pop();
    }

    // Original source, modified for modern Minecraft:
    // https://forums.minecraftforge.net/topic/37625-189draw-a-simple-circle/?do=findComment&comment=200729
    public static void drawCircle(double x, double y, double radius, int sides) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        RenderSystem.setShaderColor(36 / 255.0f, 50 / 255.0f, 123 / 255.0f, 1.0f);
        RenderSystem.setShader(GameRenderer::getPositionShader);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION);

        double width = radius - 0.6;
        for (double i = width; i < radius - 0.5 + 1; i += 0.1) {
            for (int j = 0; j <= sides; j++) {
                double angle = (Math.PI * 2 * j / sides) + Math.toRadians(180);
                bufferBuilder.vertex(x + Math.sin(angle) * i, y + Math.cos(angle) * i, 0).next();
            }
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}