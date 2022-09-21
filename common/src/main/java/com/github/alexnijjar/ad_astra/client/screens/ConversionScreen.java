package com.github.alexnijjar.ad_astra.client.screens;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.screen.handler.ConversionScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class ConversionScreen extends AbstractMachineScreen<ConversionScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/conversion.png");

	public static final int INPUT_TANK_LEFT = 42;
	public static final int INPUT_TANK_TOP = 21;

	public static final int OUTPUT_TANK_LEFT = 99;
	public static final int OUTPUT_TANK_TOP = 21;

	public static final int ENERGY_LEFT = 150;
	public static final int ENERGY_TOP = 22;

	public ConversionScreen(ConversionScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 177;
		this.backgroundHeight = 184;
		this.playerInventoryTitleY = this.backgroundHeight - 92;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

		super.drawBackground(matrices, delta, mouseX, mouseY);

		FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
		GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.inputTank.getAmount(), entity.inputTank.getCapacity(), entity.inputTank.getResource());
		GuiUtil.drawFluidTank(matrices, this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP, entity.outputTank.getAmount(), entity.outputTank.getCapacity(), entity.outputTank.getResource());
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

		if (GuiUtil.isHovering(this.getEnergyBounds(), mouseX, mouseY)) {
			GuiUtil.drawEnergyTooltip(this, matrices, entity, mouseX, mouseY);
		}

		if (GuiUtil.isHovering(this.getInputTankBounds(), mouseX, mouseY)) {
			GuiUtil.drawTankTooltip(this, matrices, entity.inputTank, mouseX, mouseY);
		}

		if (GuiUtil.isHovering(this.getOutputTankBounds(), mouseX, mouseY)) {
			GuiUtil.drawTankTooltip(this, matrices, entity.outputTank, mouseX, mouseY);
		}
	}

	public Rectangle getInputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
	}

	public Rectangle getOutputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + OUTPUT_TANK_LEFT, this.y + OUTPUT_TANK_TOP);
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	@Override
	public int getTextColour() {
		return 0x2C282E;
	}
}