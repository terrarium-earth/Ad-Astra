package earth.terrarium.ad_astra.screen;

import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * A slot that prevents placing items with an inventory. For example, a rocket with an inventory or a shulker box. This is to prevent cheating with infinite slots.
 */
public class NoInventorySlot extends Slot {

    public NoInventorySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        Item item = stack.getItem();
        return !(item instanceof VehicleItem) && !stack.getOrCreateTag().contains("Items") && !(item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof ShulkerBoxBlock);
    }
}