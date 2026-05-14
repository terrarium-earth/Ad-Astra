package earth.terrarium.adastra.common.menus.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseEntityContainerMenu<T extends Entity> extends AbstractContainerMenu {

    private static final int START_INDEX = 0;

    protected final T entity;
    protected final Inventory inventory;
    protected final Player player;
    protected final Level level;

    public BaseEntityContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity) {
        super(type, id);
        this.entity = entity;
        this.inventory = inventory;
        player = inventory.player;
        level = player.level();
        if (entity == null) return;
        addMenuSlots();
        addPlayerInvSlots();
    }

    public T getEntity() {
        return entity;
    }

    protected abstract int getContainerInputEnd();

    protected abstract int getInventoryStart();

    protected int startIndex() {
        return START_INDEX;
    }

    public abstract int getPlayerInvXOffset();

    public abstract int getPlayerInvYOffset();

    protected abstract void addMenuSlots();

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            itemStack = slotItem.copy();

            if (index < getInventoryStart()) {
                if (!moveItemStackTo(slotItem, getInventoryStart(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotItem, startIndex(), getContainerInputEnd(), false)) {
                return ItemStack.EMPTY;
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    protected void addPlayerInvSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, j + i * 9 + 9, getPlayerInvXOffset() + j * 18, getPlayerInvYOffset() + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, getPlayerInvXOffset() + i * 18, getPlayerInvYOffset() + 58));
        }
    }
}
