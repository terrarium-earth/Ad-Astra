package earth.terrarium.adastra.common.menus.base;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.WorldUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class was largely inspired by or taken from the Resourceful Bees repository with
 * the expressed permission from @ThatGravyBoat.
 *
 * @author Team Resourceful
 */
public abstract class BaseContainerMenu<T extends BlockEntity> extends AbstractContainerMenu {

    private static final int START_INDEX = 0;

    public static final ResourceLocation BATTERY_SLOT_ICON = new ResourceLocation(AdAstra.MOD_ID, "item/icons/battery_slot_icon");

    protected final T entity;
    protected final Inventory inventory;
    protected final Player player;
    protected final Level level;

    public BaseContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity) {
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

    public void togglePlayerInvSlots() {
        slots.stream().filter(slot -> slot instanceof InventorySlot).forEach(slot -> ((InventorySlot) slot).toggleActive());
    }

    protected void addPlayerInvSlots() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new InventorySlot(inventory, j + i * 9 + 9, getPlayerInvXOffset() + j * 18, getPlayerInvYOffset() + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlot(new InventorySlot(inventory, i, getPlayerInvXOffset() + i * 18, getPlayerInvYOffset() + 58));
        }
    }

    @Override
    public void clicked(int slotIndex, int button, @NotNull ClickType actionType, @NotNull Player player) {
        super.clicked(slotIndex, button, actionType, player);
        broadcastFullState();
    }

    protected static <T extends BlockEntity> T getBlockEntityFromBuf(Level level, FriendlyByteBuf buf, Class<T> type) {
        if (buf == null) return null;
        if (!level.isClientSide) return null;
        return WorldUtils.getTileEntity(type, level, buf.readBlockPos());
    }

    protected static class ImageSlot extends Slot {
        private final ResourceLocation icon;

        public ImageSlot(Container container, int slot, int xPosition, int yPosition, ResourceLocation icon) {
            super(container, slot, xPosition, yPosition);
            this.icon = icon;
        }

        @Override
        public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, icon);
        }
    }

    private static class InventorySlot extends Slot {
        private boolean active = true;

        public InventorySlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Override
        public boolean isActive() {
            return active;
        }

        public void toggleActive() {
            this.active = !this.active;
        }
    }
}
