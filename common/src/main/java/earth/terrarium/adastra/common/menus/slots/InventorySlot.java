package earth.terrarium.adastra.common.menus.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class InventorySlot extends Slot {
    private boolean active = true;

    public InventorySlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}