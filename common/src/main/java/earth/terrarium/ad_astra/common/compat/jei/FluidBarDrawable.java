package earth.terrarium.ad_astra.common.compat.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.client.screen.util.ScreenUtils;
import earth.terrarium.ad_astra.common.compat.rei.util.REIUtils;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class FluidBarDrawable implements IDrawable {

    private final FluidHolder fluid;
    private final boolean increasing;
    private final int animationDuration;

    public FluidBarDrawable(FluidHolder fluid, boolean increasing, int animationDuration) {
        this.fluid = fluid;
        this.increasing = increasing;
        this.animationDuration = animationDuration;
    }

    public int getWidth() {
        return GuiUtil.FLUID_TANK_WIDTH;
    }

    public int getHeight() {
        return GuiUtil.FLUID_TANK_HEIGHT;
    }

    @Override
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);

        ScreenUtils.addTexture(graphics, xOffset - 5, yOffset - 3, 24, 59, REIUtils.FLUID_TANK_BACK_TEXTURE);

        double ratio = (getHeight() - Mth.ceil((System.currentTimeMillis() / (animationDuration / getHeight()) % getHeight()))) / (double) getHeight();
        if (this.increasing) {
            GuiUtil.drawFluidTank(graphics, xOffset, yOffset, 1.0f - ratio, fluid);
        } else {
            GuiUtil.drawFluidTank(graphics, xOffset, yOffset, ratio, fluid);
        }

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
