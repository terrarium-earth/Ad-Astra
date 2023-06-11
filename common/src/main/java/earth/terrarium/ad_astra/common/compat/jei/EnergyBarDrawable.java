package earth.terrarium.ad_astra.common.compat.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.client.screen.util.ScreenUtils;
import earth.terrarium.ad_astra.common.compat.rei.util.REIUtils;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
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
    public void draw(GuiGraphics graphics, int xOffset, int yOffset) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendFunc(770, 771);

        ScreenUtils.addTexture(graphics, xOffset - 2, yOffset - 6, (int) (19 * 0.9), (int) (64 * 0.96), REIUtils.ENERGY_EMPTY_TEXTURE);

        double ratio = (getHeight() - Mth.ceil((System.currentTimeMillis() / (animationDuration / getHeight()) % getHeight()))) / (double) getHeight();
        if (this.increasing) {
            GuiUtil.drawVertical(graphics, xOffset, yOffset, getWidth(), getHeight(), GuiUtil.ENERGY_TEXTURE, 1.0f - ratio);
        } else {
            GuiUtil.drawVertical(graphics, xOffset, yOffset, getWidth(), getHeight(), GuiUtil.ENERGY_TEXTURE, ratio);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
