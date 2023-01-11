package earth.terrarium.ad_astra.common.compat.rei.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.client.screen.util.ScreenUtils;
import earth.terrarium.ad_astra.common.compat.rei.util.REIUtils;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import net.minecraft.util.Mth;

public class FluidBarWidget extends EnergyBarWidget {

	protected FluidHolder fluid;

	public FluidBarWidget(Point point, boolean increasing, FluidHolder fluid) {
		super(point, increasing);
		this.bounds = new Rectangle(new Rectangle(point.x, point.y, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT));
		this.fluid = fluid;
	}

	@Override
	public void renderBackground(PoseStack poseStack, boolean dark, float alpha) {
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.blendFunc(770, 771);

		ScreenUtils.addTexture(poseStack, this.bounds.x - 5, this.bounds.y - 3, 24, 59, REIUtils.FLUID_TANK_BACK_TEXTURE);

		double ratio = (this.bounds.height - Mth.ceil((System.currentTimeMillis() / (animationDuration / this.bounds.height) % this.bounds.height))) / (double) this.bounds.height;
		if (this.increasing) {
			GuiUtil.drawFluidTank(poseStack, this.bounds.x, this.bounds.y, 1.0f - ratio, fluid);
		} else {
			GuiUtil.drawFluidTank(poseStack, this.bounds.x, this.bounds.y, ratio, fluid);
		}

		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
