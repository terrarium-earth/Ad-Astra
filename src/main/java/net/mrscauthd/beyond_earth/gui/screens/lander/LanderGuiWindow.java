package net.mrscauthd.beyond_earth.gui.screens.lander;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

@OnlyIn(Dist.CLIENT)
public class LanderGuiWindow extends AbstractContainerScreen<LanderGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BeyondEarthMod.MODID,"textures/screens/lander_gui.png");

	public LanderGuiWindow(LanderGui.GuiContainer container, Inventory inventory, Component text) {
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
	}

	@Override
	protected void renderBg(PoseStack ms, float p_97788_, int p_97789_, int p_97790_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		RenderSystem.setShaderTexture(0, texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		this.font.draw(ms, title.getString(), (float) (this.imageWidth / 2) - 18, (float) this.titleLabelY, 4210752);

		this.font.draw(ms, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
	}
}
