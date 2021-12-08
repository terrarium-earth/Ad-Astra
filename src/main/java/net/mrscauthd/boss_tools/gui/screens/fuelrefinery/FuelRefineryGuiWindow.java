package net.mrscauthd.boss_tools.gui.screens.fuelrefinery;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.tile.FuelRefineryBlockEntity;
import net.mrscauthd.boss_tools.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class FuelRefineryGuiWindow extends AbstractContainerScreen<FuelRefineryGui.GuiContainer> {

	public static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId, "textures/screens/fuel_refinery_gui.png");

	public static final int INPUT_TANK_LEFT = 9;
	public static final int INPUT_TANK_TOP = 21;

	public static final int OUTPUT_TANK_LEFT = 75;
	public static final int OUTPUT_TANK_TOP = 21;

	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	public static final int ARROW_LEFT = 48;
	public static final int ARROW_TOP = 36;

	public FuelRefineryGuiWindow(FuelRefineryGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 177;
		this.imageHeight = 172;
		this.inventoryLabelY = this.imageHeight - 92;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		FuelRefineryBlockEntity blockEntity = (FuelRefineryBlockEntity) this.getMenu().getBlockEntity();

		if (GuiHelper.isHover(this.getInputTankBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getInputTank())).build(), mouseX, mouseY);
		} else if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getOutputTank())).build(), mouseX, mouseY);
		} else if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(blockEntity)).build(), mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		FuelRefineryBlockEntity blockEntity = this.getMenu().getBlockEntity();
		GuiHelper.drawEnergy(ms, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, blockEntity.getPrimaryEnergyStorage());
		GuiHelper.drawFluidTank(ms, this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP, blockEntity.getInputTank());
		GuiHelper.drawFluidTank(ms, this.leftPos + OUTPUT_TANK_LEFT, this.topPos + OUTPUT_TANK_TOP, blockEntity.getOutputTank());
	}

	public Rectangle2d getInputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.leftPos + INPUT_TANK_LEFT, this.topPos + INPUT_TANK_TOP);
	}

	public Rectangle2d getOutputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.leftPos + OUTPUT_TANK_LEFT, this.topPos + OUTPUT_TANK_TOP);
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
	}
}