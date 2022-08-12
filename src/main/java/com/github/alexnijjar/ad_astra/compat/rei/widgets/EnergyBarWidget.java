package com.github.alexnijjar.ad_astra.compat.rei.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.github.alexnijjar.ad_astra.client.screens.GuiUtil;
import com.github.alexnijjar.ad_astra.client.screens.utils.ScreenUtils;
import com.github.alexnijjar.ad_astra.compat.rei.utils.REIUtils;
import com.mojang.blaze3d.systems.RenderSystem;

import me.shedaniel.clothconfig2.api.animator.NumberAnimator;
import me.shedaniel.clothconfig2.api.animator.ValueAnimator;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class EnergyBarWidget extends BurningFire {
    protected Rectangle bounds;
    protected final boolean increasing;
    protected double animationDuration = -1;
    protected final NumberAnimator<Float> darkBackgroundAlpha = ValueAnimator.ofFloat().withConvention(() -> REIRuntime.getInstance().isDarkThemeEnabled() ? 1.0f : 0.0f, ValueAnimator.typicalTransitionTime()).asFloat();

    public EnergyBarWidget(Point point, boolean increasing) {
        this.bounds = new Rectangle(Objects.requireNonNull(new Rectangle(point.x, point.y, GuiUtil.ENERGY_WIDTH, GuiUtil.ENERGY_HEIGHT)));
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.darkBackgroundAlpha.update(delta);
        this.renderBackground(matrices, false, 1.0f);
        if (darkBackgroundAlpha.value() > 0) {
            renderBackground(matrices, true, this.darkBackgroundAlpha.value());
        }
    }

    public void renderBackground(MatrixStack matrices, boolean dark, float alpha) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);

        ScreenUtils.addTexture(matrices, this.bounds.x - 2, this.bounds.y - 6, (int)(19 * 0.9), (int)(64 * 0.96), REIUtils.ENERGY_EMPTY_TEXTURE);

        double ratio = (this.bounds.height - MathHelper.ceil((System.currentTimeMillis() / (animationDuration / this.bounds.height) % this.bounds.height))) / (double) this.bounds.height;
        if (this.increasing) {
            GuiUtil.drawAccentingVertical(matrices, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, GuiUtil.ENERGY_TEXTURE, ratio);
        } else {
            GuiUtil.drawVertical(matrices, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, GuiUtil.ENERGY_TEXTURE, ratio);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public List<? extends Element> children() {
        return Collections.emptyList();
    }
}
