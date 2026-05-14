package earth.terrarium.adastra.common.menus.slots;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CustomSlot extends Slot {

    private final boolean canPlace;
    private final boolean canTake;

    private CustomSlot(Container container, int slot, int x, int y, boolean canPlace, boolean canTake) {
        super(container, slot, x, y);
        this.canPlace = canPlace;
        this.canTake = canTake;
    }

    public static CustomSlot noPlace(Container container, int slot, int x, int y) {
        return new CustomSlot(container, slot, x, y, false, true);
    }

    public static CustomSlot noPlaceOrTake(Container container, int slot, int x, int y) {
        return new CustomSlot(container, slot, x, y, false, false);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.canPlace;
    }

    @Override
    public boolean mayPickup(Player player) {
        return this.canTake;
    }
}
