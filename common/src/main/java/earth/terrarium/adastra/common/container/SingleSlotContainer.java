package earth.terrarium.adastra.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SingleSlotContainer implements Container {

    private final int slot;
    private ItemStack stack = ItemStack.EMPTY;

    public SingleSlotContainer(int slot) {
        this.slot = slot;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return slot == this.slot ? stack : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        if (slot == this.slot) {
            ItemStack stack = this.stack.split(amount);
            if (this.stack.isEmpty()) {
                setItem(ItemStack.EMPTY);
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        if (slot == this.slot) {
            ItemStack stack = this.stack;
            setItem(ItemStack.EMPTY);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot == this.slot) {
            setItem(stack);
        }
    }

    public void setItem(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
        setItem(ItemStack.EMPTY);
    }
}
