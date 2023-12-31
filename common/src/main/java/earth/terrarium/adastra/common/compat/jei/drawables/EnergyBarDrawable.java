package earth.terrarium.adastra.common.compat.jei.drawables;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EnergyBarDrawable implements IDrawable {
    private final int mouseX;
    private final int mouseY;
    private final long perTick;
    private final long capacity;
    private final long maxIn;
    private final long maxOut;

    public EnergyBarDrawable(double mouseX, double mouseY, long perTick, long capacity, long maxIn, long maxOut) {
        this.mouseX = (int) mouseX;
        this.mouseY = (int) mouseY;
        this.perTick = perTick;
        this.capacity = capacity;
        this.maxIn = maxIn;
        this.maxOut = maxOut;
    }

    @Override
    public int getWidth() {
        return GuiUtils.ENERGY_BAR_WIDTH;
    }

    @Override
    public int getHeight() {
        return GuiUtils.ENERGY_BAR_HEIGHT;
    }

    @Override
    public void draw(@NotNull GuiGraphics graphics, int xOffset, int yOffset) {
        boolean generate = perTick > 0;
        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = time % (capacity / perTick) * -perTick;
        long energy = generate ? amount : capacity - amount;

        GuiUtils.drawEnergyBar(
            graphics,
            mouseX,
            mouseY,
            xOffset,
            yOffset,
            energy,
            capacity,
            generate ? TooltipUtils.getEnergyGenerationPerTickComponent(perTick) : TooltipUtils.getEnergyUsePerTickComponent(perTick),
            TooltipUtils.getMaxEnergyInComponent(maxIn),
            TooltipUtils.getMaxEnergyOutComponent(maxOut)
        );
    }
}
