package earth.terrarium.ad_astra.compat.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.screens.GuiUtil;
import earth.terrarium.ad_astra.client.screens.utils.ScreenUtils;
import earth.terrarium.ad_astra.compat.rei.utils.REIUtils;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.util.Mth;

public class EnergyBarDrawable implements IDrawable {

    private final boolean increasing;
    private final int animationDuration;

    public EnergyBarDrawable(boolean increasing, int animationDuration) {
        this.increasing = increasing;
        this.animationDuration = animationDuration;
    }

    public int getWidth() {
        return GuiUtil.ENERGY_WIDTH;
    }

    public int getHeight() {
        return GuiUtil.ENERGY_HEIGHT;
    }

    @Override
    public void draw(PoseStack poseStack, int xOffset, int yOffset) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);

        ScreenUtils.addTexture(poseStack, xOffset - 2, yOffset - 6, (int) (19 * 0.9), (int) (64 * 0.96), REIUtils.ENERGY_EMPTY_TEXTURE);

        double ratio = (getHeight() - Mth.ceil((System.currentTimeMillis() / (animationDuration / getHeight()) % getHeight()))) / (double) getHeight();
        if (this.increasing) {
            GuiUtil.drawVertical(poseStack, xOffset, yOffset, getWidth(), getHeight(), GuiUtil.ENERGY_TEXTURE, 1.0f - ratio);
        } else {
            GuiUtil.drawVertical(poseStack, xOffset, yOffset, getWidth(), getHeight(), GuiUtil.ENERGY_TEXTURE, ratio);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
