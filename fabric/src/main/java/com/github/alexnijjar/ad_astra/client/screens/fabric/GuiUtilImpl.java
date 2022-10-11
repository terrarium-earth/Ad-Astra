package com.github.alexnijjar.ad_astra.client.screens.fabric;

import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;

public class GuiUtilImpl {
    public static Sprite getFluidSprite(Fluid fluid) {
        return FluidVariantRendering.getSprite(FluidVariant.of(fluid));
    }

    public static int getFluidColor(Fluid fluid) {
        return FluidVariantRendering.getColor(FluidVariant.of(fluid));
    }
}
