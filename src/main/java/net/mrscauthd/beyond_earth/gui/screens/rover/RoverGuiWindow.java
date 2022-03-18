package net.mrscauthd.beyond_earth.gui.screens.rover;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.compat.CompatibleManager;
import net.mrscauthd.beyond_earth.entity.RoverEntity;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class RoverGuiWindow extends AbstractContainerScreen<RoverGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BeyondEarthMod.MODID, "textures/screens/rover.png");

	public RoverGuiWindow(RoverGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 176;
		this.inventoryLabelY = this.imageHeight - 93;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		List<Component> fuelToolTip = new ArrayList<Component>();

		int fuel = menu.rover.getEntityData().get(RoverEntity.FUEL);

		if (!CompatibleManager.JEI.isLoaded() && GuiHelper.isHover(this.getFluidBounds(), mouseX - this.leftPos, mouseY - this.topPos)) {
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE)), ChatFormatting.WHITE));
			this.renderComponentTooltip(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		IGaugeValue fuelGaugeValue = this.getFuelGaugeValue();
		FluidStack fluidStack = new FluidStack(ModInit.FUEL_BLOCK.get().getFluid(), fuelGaugeValue.getAmount());
		GuiHelper.drawFluidTank(ms, this.leftPos + 9, this.topPos + 11, fluidStack, fuelGaugeValue.getCapacity());
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		this.font.draw(ms, title.getString(), (float) (this.imageWidth / 2) - 14, (float) this.titleLabelY, 4210752);
		this.font.draw(ms, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
	}

	public Component getFuelGaugeComponent() {
		return GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(this.getFuelGaugeValue()), ChatFormatting.WHITE);
	}

	public IGaugeValue getFuelGaugeValue() {
		int fuel = menu.rover.getEntityData().get(RoverEntity.FUEL);
		return GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getFluidTankBounds(9, 11);
	}
}
