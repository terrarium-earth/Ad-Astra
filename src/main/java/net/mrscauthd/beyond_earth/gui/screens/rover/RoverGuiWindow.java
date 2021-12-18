package net.mrscauthd.beyond_earth.gui.screens.rover;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInnet;
import net.mrscauthd.beyond_earth.entity.RoverEntity;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;
import net.mrscauthd.beyond_earth.util.Rectangle2d;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RoverGuiWindow extends AbstractContainerScreen<RoverGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BeyondEarthMod.MODID, "textures/screens/rover_gui.png");

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

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE)), ChatFormatting.WHITE));
			this.renderComponentTooltip(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		int fuel = menu.rover.getEntityData().get(RoverEntity.FUEL);

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		RenderSystem.setShaderTexture(0, texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), fuel);
		GuiHelper.drawFluidTank(ms, this.leftPos + 9, this.topPos + 11, fluidStack, 3000);
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		this.font.draw(ms, title.getString(), (float) (this.imageWidth / 2) - 14, (float) this.titleLabelY, 4210752);
		this.font.draw(ms, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getFluidTankBounds(this.leftPos + 9, this.topPos + 11);
	}
}
