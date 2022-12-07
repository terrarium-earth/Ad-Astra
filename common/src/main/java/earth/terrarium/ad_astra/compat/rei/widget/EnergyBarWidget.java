package earth.terrarium.ad_astra.compat.rei.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.client.screen.util.ScreenUtils;
import earth.terrarium.ad_astra.compat.rei.util.REIUtils;
import me.shedaniel.clothconfig2.api.animator.NumberAnimator;
import me.shedaniel.clothconfig2.api.animator.ValueAnimator;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;

public class EnergyBarWidget extends BurningFire {
	protected Rectangle bounds;
	protected final boolean increasing;
	protected double animationDuration = -1;
	protected final NumberAnimator<Float> darkBackgroundAlpha = ValueAnimator.ofFloat().withConvention(() -> REIRuntime.getInstance().isDarkThemeEnabled() ? 1.0f : 0.0f, ValueAnimator.typicalTransitionTime()).asFloat();

	public EnergyBarWidget(Point point, boolean increasing) {
		this.bounds = new Rectangle(new Rectangle(point.x, point.y, GuiUtil.ENERGY_WIDTH, GuiUtil.ENERGY_HEIGHT));
		this.increasing = increasing;
	}

	@Override
	public double getAnimationDuration() {
		return animationDuration;
	}

	@Override
	public void setAnimationDuration(double animationDurationMS) {
		this.animationDuration = animationDurationMS;
		if (this.animationDuration <= 0)
			this.animationDuration = -1;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		this.darkBackgroundAlpha.update(delta);
		this.renderBackground(poseStack, false, 1.0f);
		if (darkBackgroundAlpha.value() > 0) {
			renderBackground(poseStack, true, this.darkBackgroundAlpha.value());
		}
	}

	public void renderBackground(PoseStack poseStack, boolean dark, float alpha) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.blendFunc(770, 771);

		ScreenUtils.addTexture(poseStack, this.bounds.x - 2, this.bounds.y - 6, (int) (19 * 0.9), (int) (64 * 0.96), REIUtils.ENERGY_EMPTY_TEXTURE);

		double ratio = (this.bounds.height - Mth.ceil((System.currentTimeMillis() / (animationDuration / this.bounds.height) % this.bounds.height))) / (double) this.bounds.height;
		if (this.increasing) {
			GuiUtil.drawVertical(poseStack, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, GuiUtil.ENERGY_TEXTURE, 1.0f - ratio);
		} else {
			GuiUtil.drawVertical(poseStack, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, GuiUtil.ENERGY_TEXTURE, ratio);
		}

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public List<? extends GuiEventListener> children() {
		return Collections.emptyList();
	}
}
