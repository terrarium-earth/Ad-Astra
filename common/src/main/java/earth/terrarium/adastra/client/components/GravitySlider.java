package earth.terrarium.adastra.client.components;

import com.teamresourceful.resourcefulconfig.client.options.DoubleSlider;
import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.function.DoubleConsumer;

public class GravitySlider extends DoubleSlider {

    public GravitySlider(int x, int y, int width, int height, Component message, double value, double min, double max, DoubleConsumer newValue) {
        super(x, y, width, height, message, value, min, max, newValue);
    }

    @Override
    protected void updateMessage() {}

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        graphics.blit(GuiUtils.SLIDER, getX() + (int) (value * (double) (width - 8)), getY(), 0, 0, 5, 21, 5, 21);
    }
}
