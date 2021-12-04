package net.mrscauthd.boss_tools.gui.screens.rover;

import net.minecraft.client.renderer.Rectangle2d;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RoverEntity;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class RoverGuiWindow extends ContainerScreen<RoverGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId, "textures/screens/rover_gui.png");

	public RoverGuiWindow(RoverGui.GuiContainer container, PlayerInventory inventory, ITextComponent text) {
		super(container, inventory, text);
		this.xSize = 176;
		this.ySize = 176;
		this.playerInventoryTitleY = this.ySize - 93;
	}

	@Override
	public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderHoveredTooltip(ms, mouseX, mouseY);

		List<ITextComponent> fuelToolTip = new ArrayList<ITextComponent>();

		int fuel = container.rover.getDataManager().get(RoverEntity.FUEL);

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getStorageText(GaugeValueHelper.getFuel(fuel, RoverEntity.FUEL_BUCKETS * FluidUtil2.BUCKET_SIZE)), TextFormatting.WHITE));
			this.func_243308_b(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack ms, float par1, int par2, int par3) {
		int fuel = container.rover.getDataManager().get(RoverEntity.FUEL);

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getInstance().getTextureManager().bindTexture(texture);
		this.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), fuel);
		GuiHelper.drawFluidTank(ms, this.guiLeft + 9, this.guiTop + 11, fluidStack, 3000);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
		this.font.drawString(ms, title.getString(), (float) (this.xSize / 2) - 14, (float) this.titleY, 4210752);
		this.font.func_243248_b(ms, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY, 4210752);
	}

	public Rectangle2d getFluidBounds() {
		return GuiHelper.getFluidTankBounds(this.guiLeft + 9, this.guiTop + 11);
	}
}
