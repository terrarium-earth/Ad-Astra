package earth.terrarium.adastra.client.screens.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.Slot;

public interface AbstractContainerScreenExtension {

    default void adastra$renderPreSlot(GuiGraphics graphics, Slot slot) {
    }
}
