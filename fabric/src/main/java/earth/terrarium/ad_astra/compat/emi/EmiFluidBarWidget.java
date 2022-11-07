package earth.terrarium.ad_astra.compat.emi;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.botarium.api.fluid.ClientFluidHooks;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;

public class EmiFluidBarWidget extends Widget {
	private final int x;
	private final int y;
	private final FluidVariant fluid;
	private final int speed;
	private final boolean increase;

	public EmiFluidBarWidget(int x, int y, FluidVariant fluid, int speed, boolean increase) {
		this.x = x;
		this.y = y;
		this.fluid = fluid;
		this.speed = speed;
		this.increase = increase;

	}

	@Override
	public Bounds getBounds() {
		return new Bounds(0, 0, 0, 0);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {

		int tankX = x + 5;
		int tankY = y + 3;

		double ratio = (GuiUtil.FLUID_TANK_HEIGHT - Mth.ceil((System.currentTimeMillis() / (speed / (float)GuiUtil.FLUID_TANK_HEIGHT) % GuiUtil.FLUID_TANK_HEIGHT))) / (double) GuiUtil.FLUID_TANK_HEIGHT;

		// TODO: Fix scissor not using local matrix, causing the fluid in the tank not to render.
		if (increase) {
			GuiUtil.drawFluidTank (poseStack, tankX, tankY, 1.0 - ratio, FluidHooks.newFluidHolder(fluid.getFluid(), FluidHooks.buckets(1), null));
		} else {
			GuiUtil.drawFluidTank (poseStack, tankX, tankY, ratio, FluidHooks.newFluidHolder(fluid.getFluid(), FluidHooks.buckets(1), null));
		}

		// Draw the lines texture.
		RenderSystem.enableBlend();
		GuiUtil.drawVertical(new PoseStack(), tankX, tankY, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT, GuiUtil.FLUID_TANK_TEXTURE, 1.0);
		RenderSystem.disableBlend();
	}
}
