package earth.terrarium.adastra.common.compat.jei.drawables;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EtaDrawable implements IDrawable {
    private final int mouseX;
    private final int mouseY;
    private final int cookTime;
    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;

    public EtaDrawable(double mouseX, double mouseY, int cookTime, ResourceLocation texture, int textureWidth, int textureHeight) {
        this.mouseX = (int) mouseX;
        this.mouseY = (int) mouseY;
        this.cookTime = cookTime;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public int getWidth() {
        return textureWidth;
    }

    @Override
    public int getHeight() {
        return textureHeight;
    }

    @Override
    public void draw(@NotNull GuiGraphics graphics, int xOffset, int yOffset) {
        Font font = Minecraft.getInstance().font;

        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = time % cookTime;

        GuiUtils.drawHorizontalProgressBar(
            graphics,
            texture,
            mouseX, mouseY,
            font,
            xOffset, yOffset,
            textureWidth, textureHeight,
            (int) amount,
            cookTime,
            false,
            ComponentUtils.getProgressComponent((int) amount, cookTime),
            ComponentUtils.getEtaComponent((int) amount, cookTime, false)
        );
    }
}
