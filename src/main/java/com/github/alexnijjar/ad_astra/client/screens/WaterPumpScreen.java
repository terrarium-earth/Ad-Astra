package com.github.alexnijjar.ad_astra.client.screens;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.screen.handler.WaterPumpScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class WaterPumpScreen extends AbstractMachineScreen<WaterPumpScreenHandler> {

	private static final Identifier TEXTURE = new ModIdentifier("textures/gui/screens/water_pump.png");

	public static final int INPUT_TANK_LEFT = 80;
	public static final int INPUT_TANK_TOP = 30;

	public static final int ENERGY_LEFT = 146;
	public static final int ENERGY_TOP = 30;

	public WaterPumpScreen(WaterPumpScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title, TEXTURE);
		this.backgroundWidth = 177;
		this.backgroundHeight = 180;
		this.playerInventoryTitleY = this.backgroundHeight - 92;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		super.drawBackground(matrices, delta, mouseX, mouseY);

		FluidMachineBlockEntity entity = (FluidMachineBlockEntity) blockEntity;

		GuiUtil.drawEnergy(matrices, this.x + ENERGY_LEFT, this.y + ENERGY_TOP, this.blockEntity.getEnergy(), this.blockEntity.getMaxGeneration());
		GuiUtil.drawFluidTank(matrices, this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP, entity.inputTank.getAmount(), entity.inputTank.getCapacity(), entity.inputTank.getResource());
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
	}

	public Rectangle getInputTankBounds() {
		return GuiUtil.getFluidTankBounds(this.x + INPUT_TANK_LEFT, this.y + INPUT_TANK_TOP);
	}

	public Rectangle getEnergyBounds() {
		return GuiUtil.getEnergyBounds(this.x + ENERGY_LEFT, this.y + ENERGY_TOP);
	}

	@Override
	public int getTextColour() {
		return 0x2C282E;
	}
}