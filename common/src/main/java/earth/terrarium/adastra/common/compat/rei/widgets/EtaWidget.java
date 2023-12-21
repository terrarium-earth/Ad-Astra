package earth.terrarium.adastra.common.compat.rei.widgets;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.ComponentUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class EtaWidget extends BurningFire {
    private final Rectangle bounds;
    private final int cookTime;
    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;

    public EtaWidget(Point point, int cookTime, ResourceLocation texture, int textureWidth, int textureHeight) {
        this.bounds = new Rectangle(new Rectangle(point.x, point.y, GuiUtils.ENERGY_BAR_WIDTH, GuiUtils.ENERGY_BAR_HEIGHT));
        this.cookTime = cookTime;
        this.texture = texture;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public double getAnimationDuration() {
        return 0;
    }

    @Override
    public void setAnimationDuration(double animationDurationMS) {}

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = time % cookTime;

        GuiUtils.drawHorizontalProgressBar(
            graphics,
            texture,
            mouseX, mouseY,
            font,
            bounds.x, bounds.y,
            textureWidth, textureHeight,
            (int) amount,
            cookTime,
            false,
            ComponentUtils.getProgressComponent((int) amount, cookTime),
            ComponentUtils.getEtaComponent((int) amount, cookTime, false)
        );
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
