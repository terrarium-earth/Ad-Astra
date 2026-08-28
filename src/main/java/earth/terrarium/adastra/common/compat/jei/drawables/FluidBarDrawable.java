package earth.terrarium.adastra.common.compat.jei.drawables;

import earth.terrarium.adastra.client.utils.GuiUtils;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FluidBarDrawable implements IDrawable {

    private final int mouseX;
    private final int mouseY;
    private final boolean gain;
    private final long perTick;
    private final int cookTime;
    private final long capacity;
    private final Fluid fluid;

    public FluidBarDrawable(double mouseX, double mouseY, boolean generate, long capacity, int cookTime, ResourceStack<FluidResource> fluid) {
        this.mouseX = (int) mouseX;
        this.mouseY = (int) mouseY;
        this.gain = generate;
        this.perTick = fluid.amount();
        this.cookTime = cookTime;
        this.capacity = capacity;
        this.fluid = fluid.resource().getType();
    }

    @Override
    public int getWidth() {
        return GuiUtils.FLUID_BAR_WIDTH;
    }

    @Override
    public int getHeight() {
        return GuiUtils.FLUID_BAR_HEIGHT;
    }

    @Override
    public void draw(@NotNull GuiGraphics graphics, int xOffset, int yOffset) {
        long time = Objects.requireNonNull(Minecraft.getInstance().level).getGameTime();
        long amount = (time % (capacity / perTick) * perTick);
        long fluidAmount = gain ? amount : capacity - amount;

        GuiUtils.drawFluidBar(
            graphics,
            mouseX,
            mouseY,
            xOffset,
            yOffset,
            new ResourceStack<>(FluidResource.of(fluid), fluidAmount),
            capacity,
            TooltipUtils.getTicksPerIterationComponent(cookTime),
            gain ? TooltipUtils.getFluidGenerationPerIterationComponent(perTick) : TooltipUtils.getFluidUsePerIterationComponent(perTick)
        );
    }
}
