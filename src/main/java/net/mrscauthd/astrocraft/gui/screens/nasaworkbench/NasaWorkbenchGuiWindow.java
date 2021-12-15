package net.mrscauthd.astrocraft.gui.screens.nasaworkbench;

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

@OnlyIn(Dist.CLIENT)
public class NasaWorkbenchGuiWindow extends AbstractContainerScreen<NasaWorkbenchGui.GuiContainer> {

	public static final ResourceLocation texture = new ResourceLocation(AstroCraftMod.MODID, "textures/screens/nasa_workbench_gui.png");

	public NasaWorkbenchGuiWindow(NasaWorkbenchGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 224;
		this.inventoryLabelY = this.imageHeight - 92;
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
}
