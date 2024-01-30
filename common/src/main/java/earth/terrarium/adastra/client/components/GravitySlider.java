package earth.terrarium.adastra.client.components;

import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.DoubleConsumer;

public class GravitySlider extends AbstractSliderButton {

    private final DoubleConsumer setter;
    private final double min;
    private final double max;
    private double displayValue;

    public GravitySlider(int x, int y, int width, int height, Component message, double value, double min, double max, DoubleConsumer newValue) {
        super(x, y, width, height, message, (value - min) / (max - min));
        this.displayValue = value;
        this.min = min;
        this.max = max;
        this.setter = newValue;
        updateMessage();
    }

    @Override
    protected void updateMessage() {}

    @Override
    protected void applyValue() {
        displayValue = (double) Math.round(Mth.clampedLerp(min, max, value) * 100) / 100;
        setter.accept(displayValue);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blitSprite(GuiUtils.SLIDER, getX() + (int) (value * (double) (width - 8)), getY(), 5, 21);
    }
}
