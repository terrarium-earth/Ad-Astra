package net.mrscauthd.astrocraft.gui.screens.waterpump;

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
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.gauge.GaugeTextHelper;
import net.mrscauthd.astrocraft.gauge.GaugeValueHelper;
import net.mrscauthd.astrocraft.gui.helper.GuiHelper;
import net.mrscauthd.astrocraft.machines.tile.WaterPumpBlockEntity;
import net.mrscauthd.astrocraft.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class WaterPumpGuiWindow extends AbstractContainerScreen<WaterPumpGui.GuiContainer> {

	public static final ResourceLocation texture = new ResourceLocation(AstroCraftMod.MODID, "textures/screens/water_pump_gui.png");

	public static final int WATER_TANK_LEFT = 75;
	public static final int WATER_TANK_TOP = 21;

	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;

	public WaterPumpGuiWindow(WaterPumpGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 177;
		this.imageHeight = 172;
		this.inventoryLabelY = this.imageHeight - 92;
	}

	public Rectangle2d getOutputTankBounds() {
		return GuiHelper.getFluidTankBounds(this.leftPos + WATER_TANK_LEFT, this.topPos + WATER_TANK_TOP);
	}

	public Rectangle2d getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP);
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		WaterPumpBlockEntity blockEntity = (WaterPumpBlockEntity) this.getMenu().getBlockEntity();

		if (GuiHelper.isHover(this.getOutputTankBounds(), mouseX, mouseY)) {

			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getFluid(blockEntity.getWaterTank())).build(), mouseX, mouseY);
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

		WaterPumpBlockEntity blockEntity = this.getMenu().getBlockEntity();
		GuiHelper.drawEnergy(ms, this.leftPos + ENERGY_LEFT, this.topPos + ENERGY_TOP, blockEntity.getPrimaryEnergyStorage());
		GuiHelper.drawFluidTank(ms, this.leftPos + WATER_TANK_LEFT, this.topPos + WATER_TANK_TOP, blockEntity.getWaterTank());
	}

}