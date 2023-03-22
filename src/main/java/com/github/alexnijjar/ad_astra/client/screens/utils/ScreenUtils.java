package com.github.alexnijjar.ad_astra.client.screens.utils;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ColourHolder;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ScreenUtils {

	public static Text createText(String text) {
		return new TranslatableText("gui." + AdAstra.MOD_ID + ".text." + text);
	}

	public static Text createText(Identifier text) {
		return new TranslatableText("gui." + text.getNamespace() + ".text." + text.getPath());
	}

	public static void addTexture(MatrixStack matrices, int x, int y, int width, int height, Identifier texture) {
		RenderSystem.setShaderTexture(0, texture);
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, width, height, width, height);
	}

	public static void addRotatingTexture(PlanetSelectionScreen screen, MatrixStack matrices, int x, int y, int width, int height, Identifier texture, float speed) {

		double scale = MinecraftClient.getInstance().getWindow().getScaledHeight() / 400.0;
		
		x *= scale;
		y *= scale;
		x += 1;
		y += 1;

		width *= scale;
		height *= scale;

		matrices.push();

		matrices.translate(screen.width / 2.0f, screen.height / 2.0f, 0);
		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(screen.getGuiTime() * (speed / 3.0f)));

		addTexture(matrices, x, y, width, height, texture);

		matrices.pop();
	}

	public static void drawCircle(double x, double y, double radius, int sides, ColourHolder ringColour) {

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);


		double scale = MinecraftClient.getInstance().getWindow().getScaledHeight() / 400.0;
		radius *= scale;

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

	public static void drawLine(int x1, int x2, int y1, int y2, int colour) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x1, y1, 0).color((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f).next();
        bufferBuilder.vertex(x2, y2, 0).color((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f).next();
        tessellator.draw();
    }
}