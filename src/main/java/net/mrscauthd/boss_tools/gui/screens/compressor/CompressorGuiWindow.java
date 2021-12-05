package net.mrscauthd.boss_tools.gui.screens.compressor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.boss_tools.BossToolsMod;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.machines.CompressorBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class CompressorGuiWindow extends AbstractContainerScreen<CompressorGui.GuiContainer> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(BossToolsMod.ModId,"textures/screens/compressor_gui.png");

	public static final int ENERGY_LEFT = 144;
	public static final int ENERGY_TOP = 21;
	public static final int ARROW_LEFT = 62;
	public static final int ARROW_TOP = 36;

	private CustomTileEntity tileEntity;

	public CompressorGuiWindow(CompressorGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
		this.imageWidth = 177;
		this.imageHeight = 168;
		this.inventoryLabelY = this.imageHeight - 92;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		if (GuiHelper.isHover(this.getEnergyBounds(), mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getEnergy(this.getTileEntity())).build(), mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		CustomTileEntity tileEntity = this.getTileEntity();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.minecraft.getTextureManager().bindForSetup(TEXTURE);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		GuiHelper.drawEnergy(ms, this.leftPos + ENERGY_LEFT, this.leftPos + ENERGY_TOP, tileEntity.getPrimaryEnergyStorage());
		GuiHelper.drawArrow(ms, this.leftPos + ARROW_LEFT, this.leftPos + ARROW_TOP, tileEntity.getTimerRatio());
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}

	public Rect2i getEnergyBounds() {
		return GuiHelper.getEnergyBounds(this.leftPos + ENERGY_LEFT, this.leftPos + ENERGY_TOP);
	}
}
