package com.github.alexnijjar.ad_astra.client.screens;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.ad_astra.screen.handler.CoalGeneratorScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class CoalGeneratorScreen extends AbstractMachineScreen<CoalGeneratorScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/coal_generator.png");

	public static final int FIRE_LEFT = 81;
	public static final int FIRE_TOP = 57;

	public static final int ENERGY_LEFT = 146;
	public static final int ENERGY_TOP = 28;

	public CoalGeneratorScreen(CoalGeneratorScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 176;
		this.backgroundHeight = 177;
		this.playerInventoryTitleY = this.backgroundHeight - 93;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		super.drawBackground(matrices, delta, mouseX, mouseY);

		CoalGeneratorBlockEntity entity = (CoalGeneratorBlockEntity) blockEntity;

		GuiUtil.drawFire(matrices, this.x + FIRE_LEFT, this.y + FIRE_TOP, entity.getCookTime(), entity.getCookTimeTotal());
		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		CoalGeneratorBlockEntity entity = (CoalGeneratorBlockEntity) blockEntity;

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
			GuiUtil.drawEnergyTooltip(this, matrices, entity, mouseX, mouseY);
		}

		// Burn time tooltip.
		if (GuiUtil.isHovering(this.getFireBounds(), mouseX, mouseY)) {
			this.renderTooltip(matrices, new TranslatableText("gauge.ad_astra.burn_time", entity.getCookTime(), entity.getCookTimeTotal()), mouseX, mouseY);
		}
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	public Rectangle getFireBounds() {
		return GuiUtil.getFireBounds(this.x + FIRE_LEFT, this.y + FIRE_TOP);
	}
}