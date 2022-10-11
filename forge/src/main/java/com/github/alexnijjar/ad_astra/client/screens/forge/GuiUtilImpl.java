package com.github.alexnijjar.ad_astra.client.screens.forge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

public class GuiUtilImpl {
    public static Sprite getFluidSprite(Fluid fluid) {
        return MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
    }

    public static int getFluidColor(Fluid fluid) {
        return IClientFluidTypeExtensions.of(fluid).getTintColor();
    }
}
