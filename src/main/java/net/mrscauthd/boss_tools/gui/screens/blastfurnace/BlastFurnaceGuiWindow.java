package net.mrscauthd.boss_tools.gui.screens.blastfurnace;

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
import net.mrscauthd.boss_tools.machines.BlastingFurnaceBlock.BlastingFurnaceBlockEntity;
import net.mrscauthd.boss_tools.util.Rectangle2d;

@OnlyIn(Dist.CLIENT)
public class BlastFurnaceGuiWindow extends AbstractContainerScreen<BlastFurnaceGui.GuiContainer> {

	public static final ResourceLocation TEXTURE = new ResourceLocation(BossToolsMod.ModId,"textures/screens/blast_furnace_gui.png");
	public static final int FIRE_LEFT = 53;
	public static final int FIRE_TOP = 39;
	public static final int ARROW_LEFT = 74;
	public static final int ARROW_TOP = 37;

	public BlastFurnaceGuiWindow(BlastFurnaceGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 177;
		this.imageHeight = 172;
		this.inventoryLabelY = this.imageHeight - 95;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

		if (GuiHelper.isHover(this.getFireBounds(), mouseX, mouseY)) {
			this.renderTooltip(ms, GaugeTextHelper.getStorageText(GaugeValueHelper.getBurnTime(this.getMenu().getBlockEntity().getPowerSystemBurnTime())).build(), mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		BlastingFurnaceBlockEntity blockEntity = this.getMenu().getBlockEntity();
		GuiHelper.drawFire(ms, this.leftPos + FIRE_LEFT, this.topPos + FIRE_TOP, blockEntity.getPowerSystemBurnTime().getStoredRatio());
		GuiHelper.drawArrow(ms, this.leftPos + ARROW_LEFT, this.topPos + ARROW_TOP, blockEntity.getTimerRatio());
	}

	public Rectangle2d getFireBounds() {
		return GuiHelper.getFireBounds(this.leftPos + FIRE_LEFT, this.topPos + FIRE_TOP);
	}

}
