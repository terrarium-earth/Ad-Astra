package com.github.alexnijjar.beyond_earth.client.screens.utils;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ColourHolder;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
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
public class ScreenUtils {

    public static Text createText(String text) {
        return new TranslatableText("gui." + BeyondEarth.MOD_ID + ".text." + text);
    }

    public static Text createText(Identifier text) {
        return new TranslatableText("gui." + text.getNamespace() + ".text." + text.getPath());
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

    public static void drawCircle(double x, double y, double radius, int sides, ColourHolder ringColour) {

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        double width = radius - 0.6;
        for (double i = width; i < radius - 0.5 + 1; i += 0.1) {
            bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
            for (int j = 0; j <= sides; j++) {
                double angle = (Math.PI * 2 * j / sides) + Math.toRadians(180);
                bufferBuilder.vertex(x + Math.sin(angle) * i, y + Math.cos(angle) * i, 0).color((int) ringColour.r(), (int) ringColour.g(), (int) ringColour.b(), (int) ringColour.a()).next();
            }
            tessellator.draw();
        }
    }
}