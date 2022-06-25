package com.github.alexnijjar.beyond_earth.client.rei.widgets;

import java.util.Objects;

import com.github.alexnijjar.beyond_earth.client.rei.utils.REIUtils;
import com.github.alexnijjar.beyond_earth.client.screens.GuiUtil;
import com.github.alexnijjar.beyond_earth.client.screens.utils.ScreenUtils;
import com.mojang.blaze3d.systems.RenderSystem;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class FluidBarWidget extends EnergyBarWidget {

    protected FluidVariant fluid;

    public FluidBarWidget(Point point, boolean increasing, FluidVariant fluid) {
        super(point, increasing);
        this.bounds = new Rectangle(Objects.requireNonNull(new Rectangle(point.x, point.y, GuiUtil.FLUID_TANK_WIDTH, GuiUtil.FLUID_TANK_HEIGHT)));
        this.fluid = fluid;
    }

    public void renderBackground(MatrixStack matrices, boolean dark, float alpha) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);

        ScreenUtils.addTexture(matrices, this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height, REIUtils.FLUID_TANK_BACK_TEXTURE);

        double ratio = (this.bounds.height - MathHelper.ceil((System.currentTimeMillis() / (animationDuration / this.bounds.height) % this.bounds.height))) / (double) this.bounds.height;
        if (this.increasing) {
            GuiUtil.drawAccentingFluidTank(matrices, this.bounds.x, this.bounds.y, ratio, fluid);
        } else {
            GuiUtil.drawFluidTank(matrices, this.bounds.x, this.bounds.y, ratio, fluid);
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
