package net.mrscauthd.boss_tools.gui.screens.rocket;

import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RocketGuiWindow extends ContainerScreen<RocketGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId, "textures/screens/rocket_gui.png");

	public RocketGuiWindow(RocketGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 176;
		this.ySize = 167;
		this.playerInventoryTitleY = this.ySize - 93;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		int fuel = 0;

		if (container.rocket instanceof RocketTier1Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier1Entity.FUEL) / 3;
		}
		if (container.rocket instanceof RocketTier2Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier2Entity.FUEL) / 3;
		}
		if (container.rocket instanceof RocketTier3Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier3Entity.FUEL) / 3;
		}

		List<ITextComponent> fuelToolTip = new ArrayList<ITextComponent>();

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, 100)), TextFormatting.WHITE));
			this.func_243308_b(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		int fuel = 0;

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		this.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);


		if (container.rocket instanceof RocketTier1Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier1Entity.FUEL);
		}
		if (container.rocket instanceof RocketTier2Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier2Entity.FUEL);
		}
		if (container.rocket instanceof RocketTier3Entity) {
			fuel = container.rocket.getDataManager().get(RocketTier3Entity.FUEL);
		}

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), 1);
		GuiHelper.drawRocketFluidTank(ms, this.guiLeft + 67, this.guiTop + 22, fluidStack, 300, fuel);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		TranslationTextComponent title = new TranslationTextComponent("container." + container.rocket.getType());

		this.font.drawString(ms, title.getString(), (float) (this.xSize / 2) - 33, (float) this.titleY, 4210752);

		this.font.func_243248_b(ms, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, 4210752);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getRocketFluidTankBounds(this.guiLeft + 66, this.guiTop + 21);
	}
}
