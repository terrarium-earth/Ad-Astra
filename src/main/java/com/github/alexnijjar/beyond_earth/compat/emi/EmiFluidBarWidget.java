package com.github.alexnijjar.beyond_earth.compat.emi;

import com.github.alexnijjar.beyond_earth.client.screens.GuiUtil;
import com.mojang.blaze3d.systems.RenderSystem;

import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.MathHelper;

public class EmiFluidBarWidget extends Widget {
    private int x, y;
    private FluidVariant fluid;
    private int speed;
    private boolean increase;

	public EmiFluidBarWidget(int x, int y,  FluidVariant fluid, int speed, boolean increase) {
		this.x = x;
        this.y = y;
        this.fluid = fluid;
        this.speed = speed;
        this.increase = increase;
        
	}

	@Override
	public Bounds getBounds() {
		return new Bounds(0, 0, 0, 0);
	}

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        int tankX = x + 5;
        int tankY = y + 3;

        double ratio = (GuiUtil.FLUID_TANK_HEIGHT - MathHelper.ceil((System.currentTimeMillis() / (speed / GuiUtil.FLUID_TANK_HEIGHT) % GuiUtil.FLUID_TANK_HEIGHT))) / (double) GuiUtil.FLUID_TANK_HEIGHT;
        
        if (increase) {
            drawFluid(matrices, tankX, tankY, 1.0 - ratio, fluid);
        } else {
            drawFluid(matrices, tankX, tankY, ratio, fluid);
        }

        RenderSystem.enableBlend();
        GuiUtil.drawVertical(new MatrixStack(), tankX, tankY, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT, GuiUtil.FLUID_TANK_TEXTURE, 1.0);
        RenderSystem.disableBlend();
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
        int scissorY = (int) (((screenHeight - y - GuiUtil.FLUID_TANK_HEIGHT)) * scale);
        int scissorWidth = (int) (GuiUtil.FLUID_TANK_WIDTH * scale);
        int scissorHeight = (int) ((GuiUtil.FLUID_TANK_HEIGHT - 1) * scale * ratio);

        RenderSystem.enableScissor(scissorX, scissorY, scissorWidth, scissorHeight);

        for (int i = 1; i < 4; i++) {
            DrawableHelper.drawSprite(matrices, x + 1, GuiUtil.FLUID_TANK_HEIGHT + y - (spriteHeight * i), 0, GuiUtil.FLUID_TANK_WIDTH - 2, spriteHeight, sprite);
            // DrawableHelper.drawSprite(matrices, x - 400 + 1, GuiUtil.FLUID_TANK_HEIGHT + y - 200 - (spriteHeight * i), 0, GuiUtil.FLUID_TANK_WIDTH * 40 - 2, spriteHeight * 40, sprite);
        }

        RenderSystem.disableScissor();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
