package net.mrscauthd.boss_tools.gui.screens.rocket;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.entity.RocketTier1Entity;
import net.mrscauthd.boss_tools.entity.RocketTier2Entity;
import net.mrscauthd.boss_tools.entity.RocketTier3Entity;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import org.lwjgl.opengl.GL11;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RocketGuiWindow extends AbstractContainerScreen<RocketGui.GuiContainer> {

	private static final ResourceLocation texture = new ResourceLocation(BossToolsMod.ModId, "textures/screens/rocket_gui.png");

	public RocketGuiWindow(RocketGui.GuiContainer container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.imageWidth = 176;
		this.imageHeight = 167;
		this.inventoryLabelY = this.inventoryLabelY - 93;
	}

	@Override
	public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);

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

		fuel = fuel / 3;

		List<Component> fuelToolTip = new ArrayList<Component>();

		if (GuiHelper.isHover(this.getFluidBounds(), mouseX, mouseY)) {
			fuelToolTip.add(GaugeTextHelper.buildBlockTooltip(GaugeTextHelper.getPercentText(GaugeValueHelper.getFuel(fuel, 100)), ChatFormatting.WHITE));
			this.renderComponentTooltip(ms, fuelToolTip, mouseX, mouseY);
		}
	}

	@Override
	protected void renderBg(PoseStack ms, float p_98414_, int p_98415_, int p_98416_) {
		int fuel = 0;

		GL11.glColor4f(1, 1, 1, 1);

		Minecraft.getInstance().getTextureManager().bindForSetup(texture);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);


		if (menu.rocket instanceof RocketTier1Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier1Entity.FUEL);
		}
		if (menu.rocket instanceof RocketTier2Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier2Entity.FUEL);
		}
		if (menu.rocket instanceof RocketTier3Entity) {
			fuel = menu.rocket.getEntityData().get(RocketTier3Entity.FUEL);
		}

		FluidStack fluidStack = new FluidStack(ModInnet.FUEL_BLOCK.get().getFluid(), 1);
		GuiHelper.drawRocketFluidTank(ms, this.leftPos + 67, this.topPos + 22, fluidStack, 300, fuel);
	}

	@Override
	protected void renderLabels(PoseStack ms, int p_97809_, int p_97810_) {
		TranslatableComponent title = new TranslatableComponent("container." + menu.rocket.getType());

		this.font.draw(ms, title.getString(), (float) (this.imageWidth / 2) - 33, (float) this.imageHeight, 4210752);

		this.font.draw(ms, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
	}

	public Rect2i getFluidBounds() {
		return GuiHelper.getRocketFluidTankBounds(this.leftPos + 66, this.topPos + 21);
	}
}
