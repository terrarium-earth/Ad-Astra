package net.mrscauthd.boss_tools.gui.screens.nasaworkbench;

import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.machines.NASAWorkbenchBlock.CustomTileEntity;

@OnlyIn(Dist.CLIENT)
public class NasaWorkbenchGuiWindow extends AbstractContainerScreen<NasaWorkbenchGui.GuiContainer> {

	public static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId,"textures/screens/nasa_workbench_gui.png");

	private CustomTileEntity tileEntity;

	public NasaWorkbenchGuiWindow(NasaWorkbenchGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.tileEntity = container.getTileEntity();
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
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		Minecraft.getInstance().getTextureManager().bindForSetup(texture);
		GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
	}

	public CustomTileEntity getTileEntity() {
		return this.tileEntity;
	}
}
