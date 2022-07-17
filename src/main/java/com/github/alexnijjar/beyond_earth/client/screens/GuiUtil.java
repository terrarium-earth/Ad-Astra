package com.github.alexnijjar.beyond_earth.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class GuiUtil {

    public static final int FIRE_WIDTH = 14;
    public static final int FIRE_HEIGHT = 14;
    public static final int SNOWFLAKE_WIDTH = 13;
    public static final int SNOWFLAKE_HEIGHT = 13;
    public static final int ARROW_WIDTH = 24;
    public static final int ARROW_HEIGHT = 17;
    public static final int OXYGEN_TANK_WIDTH = 14;
    public static final int OXYGEN_TANK_HEIGHT = 48;
    public static final int ENERGY_WIDTH = 24;
    public static final int ENERGY_HEIGHT = 48;
    public static final int FUEL_WIDTH = 48;
    public static final int FUEL_HEIGHT = 48;
    public static final int FLUID_TANK_WIDTH = 14;
    public static final int FLUID_TANK_HEIGHT = 48;

    public static final Identifier FIRE_TEXTURE = new ModIdentifier("textures/gui/fire_on.png");
    public static final Identifier SNOWFLAKE_TEXTURE = new ModIdentifier("textures/gui/snowflake.png");
    public static final Identifier ARROW_TEXTURE = new ModIdentifier("textures/gui/animated_arrow_full.png");
    public static final Identifier OXYGEN_CONTENT_TEXTURE = new ModIdentifier("textures/gui/oxygen.png");
    public static final Identifier ENERGY_TEXTURE = new ModIdentifier("textures/gui/energy_full.png");
    public static final Identifier FLUID_TANK_TEXTURE = new ModIdentifier("textures/gui/fluid_tank.png");

    public static boolean isHovering(Rectangle bounds, double x, double y) {
        double left = bounds.getX();
        double right = left + bounds.getWidth();
        double top = bounds.getY();
        double bottom = top + bounds.getHeight();
        return left <= x && x < right && top <= y && y < bottom;
    }

    public static Rectangle getFluidTankBounds(int x, int y) {
        return new Rectangle(x, y, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT);
    }

    public static Rectangle getArrowBounds(int x, int y) {
        return new Rectangle(x, y, ARROW_WIDTH, ARROW_HEIGHT);
    }

    public static Rectangle getEnergyBounds(int x, int y) {
        return new Rectangle(x, y, ENERGY_WIDTH, ENERGY_HEIGHT);
    }

    public static Rectangle getFireBounds(int x, int y) {
        return new Rectangle(x, y, FIRE_WIDTH, FIRE_HEIGHT);
    }

    public static void drawEnergy(MatrixStack matrixStack, int x, int y, long energy, long maxEnergy) {
        double ratio = maxEnergy > 0 ? createRatio(energy, maxEnergy) : 0;
        drawVertical(matrixStack, x, y, ENERGY_WIDTH, ENERGY_HEIGHT, ENERGY_TEXTURE, ratio);
    }

    public static void drawFluidTank(MatrixStack matrices, int x, int y, long fluidAmount, long fluidCapacity, FluidVariant fluid) {
        double ratio = fluidCapacity > 0 ? createRatio(fluidAmount, fluidCapacity) : 0;
        drawFluidTank(matrices, x, y, ratio, fluid);
    }

    public static void drawFluidTank(MatrixStack matrices, int x, int y, double ratio, FluidVariant fluid) {
        // Draw the fluid.
        drawFluid(matrices, x, y, ratio, fluid);
        // Draw the fluid tank.
        drawVertical(matrices, x, y, FLUID_TANK_WIDTH, FLUID_TANK_HEIGHT, FLUID_TANK_TEXTURE, 1.0);
    }

    public static void drawAccentingFluidTank(MatrixStack matrices, int x, int y, double ratio, FluidVariant fluid) {
        drawFluidTank(matrices, x, y, 1.0 - ratio, fluid);
    }

    private static void drawFluid(MatrixStack matrices, int x, int y, double ratio, FluidVariant fluid) {

        if (fluid.isBlank()) {
            return;
        }

        Sprite sprite = FluidVariantRendering.getSprite(fluid);
        int colour = FluidVariantRendering.getColor(fluid);
        int spriteHeight = sprite.getHeight();

        RenderSystem.setShaderColor((colour >> 16 & 255) / 255.0f, (float) (colour >> 8 & 255) / 255.0f, (float) (colour & 255) / 255.0f, 1.0f);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);

        MinecraftClient client = MinecraftClient.getInstance();
        double scale = (int) client.getWindow().getScaleFactor();
        int screenHeight = client.getWindow().getScaledHeight();

        int scissorX = (int) (x * scale);
        int scissorY = (int) (((screenHeight - y - FLUID_TANK_HEIGHT)) * scale);
        int scissorWidth = (int) (FLUID_TANK_WIDTH * scale);
        int scissorHeight = (int) ((FLUID_TANK_HEIGHT - 1) * scale * ratio);

        // First, the sprite is rendered in full, and then it is masked based on how full the fluid tank is.
        RenderSystem.enableScissor(scissorX, scissorY, scissorWidth, scissorHeight);

        for (int i = 1; i < 4; i++) {
            DrawableHelper.drawSprite(matrices, x + 1, FLUID_TANK_HEIGHT + y - (spriteHeight * i) - 1, 0, FLUID_TANK_WIDTH - 2, spriteHeight, sprite);
        }

        RenderSystem.disableScissor();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawFire(MatrixStack matrixStack, int x, int y, short burnTime, short totalBurnTime) {
        double ratio = totalBurnTime > 0 ? createRatio(burnTime, totalBurnTime) : 0;
        drawVertical(matrixStack, x, y, FIRE_WIDTH, FIRE_HEIGHT, FIRE_TEXTURE, ratio);
    }

    public static void drawSnowflake(MatrixStack matrixStack, int x, int y, short burnTime, short totalBurnTime) {
        double ratio = totalBurnTime > 0 ? createRatio(burnTime, totalBurnTime) : 0;
        drawHorizontal(matrixStack, x, y, SNOWFLAKE_WIDTH, SNOWFLAKE_HEIGHT, SNOWFLAKE_TEXTURE, ratio);
    }

    public static void drawArrow(MatrixStack matrixStack, int x, int y, short burnTime, short totalBurnTime) {
        double ratio = totalBurnTime > 0 ? createRatio(burnTime, totalBurnTime) : 0;
        drawHorizontal(matrixStack, x, y, ARROW_WIDTH, ARROW_HEIGHT, ARROW_TEXTURE, ratio);
    }

    public static void drawVertical(MatrixStack matrixStack, int x, int y, int width, int height, Identifier resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        DrawableHelper.drawTexture(matrixStack, x, y + remainHeight, 0, remainHeight, width, ratioHeight, width, height);
    }

    public static void drawAccentingVertical(MatrixStack matrixStack, int x, int y, int width, int height, Identifier resource, double ratio) {
        ratio = 1.0 - ratio;
        drawVertical(matrixStack, x, y, width, height, resource, ratio);
    }

    public static void drawVerticalReverse(MatrixStack matrixStack, int x, int y, int width, int height, Identifier resource, double ratio) {
        int ratioHeight = (int) Math.ceil(height * ratio);
        int remainHeight = height - ratioHeight;
        RenderSystem.setShaderTexture(0, resource);
        DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, width, remainHeight, width, height);
    }

    public static void drawHorizontal(MatrixStack matrixStack, int x, int y, int width, int height, Identifier resource, double ratio) {
        int ratioWidth = (int) Math.ceil(width * ratio);

        RenderSystem.setShaderTexture(0, resource);
        DrawableHelper.drawTexture(matrixStack, x, y, 0, 0, ratioWidth, height, width, height);
    }

    public static double createRatio(long a, long b) {
        return (double) a / (double) b;
    }

    public static double createRatio(short a, short b) {
        return (float) a / (float) b;
    }

    public static class FloatDrawableHelper {
        public static void drawTexture(MatrixStack matrices, float x, float y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
            FloatDrawableHelper.drawTexture(matrices, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
        }

        public static void drawTexture(MatrixStack matrices, float x, float y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
            FloatDrawableHelper.drawTexture(matrices, x, x + width, y, y + height, 0, regionWidth, regionHeight, u, v, textureWidth, textureHeight);
        }
    
        private static void drawTexture(MatrixStack matrices, float x0, float x1, float y0, float y1, int z, int regionWidth, int regionHeight, float u, float v, int textureWidth, int textureHeight) {
            FloatDrawableHelper.drawTexturedQuad(matrices.peek().getPositionMatrix(), x0, x1, y0, y1, z, (u + 0.0f) / (float)textureWidth, (u + (float)regionWidth) / (float)textureWidth, (v + 0.0f) / (float)textureHeight, (v + (float)regionHeight) / (float)textureHeight);
        }
    
        private static void drawTexturedQuad(Matrix4f matrix, float x0, float x1, float y0, float y1, int z, float u0, float u1, float v0, float v1) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
            bufferBuilder.vertex(matrix, x0, y1, z).texture(u0, v1).next();
            bufferBuilder.vertex(matrix, x1, y1, z).texture(u1, v1).next();
            bufferBuilder.vertex(matrix, x1, y0, z).texture(u1, v0).next();
            bufferBuilder.vertex(matrix, x0, y0, z).texture(u0, v0).next();
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
        }
    }
}