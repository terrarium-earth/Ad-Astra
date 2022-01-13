package net.mrscauthd.beyond_earth.gui.screens.rocket;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.compat.CompatibleManager;
import net.mrscauthd.beyond_earth.entity.RocketTier1Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier2Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier3Entity;
import net.mrscauthd.beyond_earth.entity.RocketTier4Entity;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class RocketGuiWindow extends AbstractContainerScreen<RocketGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BeyondEarthMod.MODID, "textures/screens/rocket_gui.png");

	public RocketGuiWindow(RocketGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 167;
		this.inventoryLabelY = this.imageHeight - 93;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		if (!CompatibleManager.JEI.isLoaded() && GuiHelper.isHover(this.getFluidBounds(), mouseX - this.leftPos, mouseY - this.topPos)) {
			List<Component> fuelToolTip = new ArrayList<Component>();
			fuelToolTip.add(this.getFuelGaugeComponent());
			this.renderComponentTooltip(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_98414_, int p_98415_, int p_98416_) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		IGaugeValue fuelGaugeValue = this.getFuelGaugeValue();

		FluidStack fluidStack = new FluidStack(ModInit.FUEL_BLOCK.get().getFluid(), fuelGaugeValue.getAmount());
		GuiHelper.drawRocketFluidTank(ms, this.leftPos + 67, this.topPos + 22, fluidStack, fuelGaugeValue.getCapacity());
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		TranslatableComponent title = new TranslatableComponent("container." + menu.rocket.getType());

		this.font.draw(ms, title.getString(), (float) (this.imageWidth / 2) - 33, (float) this.titleLabelY, 4210752);

		this.font.draw(ms, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
	}

	public Component getFuelGaugeComponent() {
		return GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(this.getFuelGaugeValue()), ChatFormatting.WHITE);
	}

	public IGaugeValue getFuelGaugeValue() {
		int fuel = 0;

		if (menu.rocket instanceof RocketTier1Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier1Entity.FUEL);
		}
		if (menu.rocket instanceof RocketTier2Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier2Entity.FUEL);
		}
		if (menu.rocket instanceof RocketTier3Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier3Entity.FUEL);
		}
		if (menu.rocket instanceof RocketTier4Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier4Entity.FUEL);
		}

		return GaugeValueHelper.getFuel(fuel / 3, 100);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getRocketFluidTankBounds(66, 21);
	}
}
