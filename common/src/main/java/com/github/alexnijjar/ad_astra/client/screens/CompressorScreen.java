package com.github.alexnijjar.ad_astra.client.screens;

import java.awt.Rectangle;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.CompressorBlockEntity;
import com.github.alexnijjar.ad_astra.screen.handler.CompressorScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CompressorScreen extends AbstractMachineScreen<CompressorScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/compressor.png");

	public static final int ENERGY_LEFT = 147;
	public static final int ENERGY_TOP = 30;
	public static final int HAMMER_LEFT = 67;
	public static final int HAMMER_TOP = 63;

	public CompressorScreen(CompressorScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 177;
		this.backgroundHeight = 196;
		this.playerInventoryTitleY = this.backgroundHeight - 93;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		super.drawBackground(matrices, delta, mouseX, mouseY);

		CompressorBlockEntity entity = (CompressorBlockEntity) blockEntity;

		GuiUtil.drawHammer(matrices, this.x + HAMMER_LEFT, this.y + HAMMER_TOP, entity.getCookTime(), entity.getCookTimeTotal());
		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		CompressorBlockEntity entity = (CompressorBlockEntity) blockEntity;

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
			GuiUtil.drawEnergyTooltip(this, matrices, entity, mouseX, mouseY);
		}

		// Burn time tooltip.
		if (GuiUtil.isHovering(this.getHammerBounds(), mouseX, mouseY)) {
			this.renderTooltip(matrices, Text.translatable("gauge.ad_astra.cook_time", entity.getCookTime(), entity.getCookTimeTotal()), mouseX, mouseY);
		}
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	public Rectangle getHammerBounds() {
		return GuiUtil.getHammerBounds(this.x + HAMMER_LEFT, this.y + HAMMER_TOP);
	}
}