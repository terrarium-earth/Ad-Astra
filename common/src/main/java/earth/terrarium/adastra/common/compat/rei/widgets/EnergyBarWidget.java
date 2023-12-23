package earth.terrarium.adastra.common.compat.rei.widgets;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.BurningFire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class EnergyBarWidget extends BurningFire {
    private final Rectangle bounds;
    private final long perTick;
    private final long capacity;
    private final long maxIn;
    private final long maxOut;

    public EnergyBarWidget(Point point, long perTick, long capacity, long maxIn, long maxOut) {
        this.bounds = new Rectangle(new Rectangle(point.x, point.y, GuiUtils.ENERGY_BAR_WIDTH, GuiUtils.ENERGY_BAR_HEIGHT));
        this.perTick = perTick;
        this.capacity = capacity;
        this.maxIn = maxIn;
        this.maxOut = maxOut;
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
        boolean generate = perTick > 0;
        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = time % (capacity / perTick) * -perTick;
        long energy = generate ? amount : capacity - amount;

        GuiUtils.drawEnergyBar(
            graphics,
            mouseX,
            mouseY,
            font,
            this.bounds.x,
            this.bounds.y,
            energy,
            capacity,
            generate ? TooltipUtils.getEnergyGenerationPerTickComponent(perTick) : TooltipUtils.getEnergyUsePerTickComponent(perTick),
            TooltipUtils.getMaxEnergyInComponent(maxIn),
            TooltipUtils.getMaxEnergyOutComponent(maxOut)
        );
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }
}
