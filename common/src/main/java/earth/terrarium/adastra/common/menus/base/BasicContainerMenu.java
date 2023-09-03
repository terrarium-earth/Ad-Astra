package earth.terrarium.adastra.common.menus.base;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.WorldUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
public abstract class BasicContainerMenu<T extends BlockEntity> extends AbstractContainerMenu {

    private static final int START_INDEX = 0;

    public static final ResourceLocation ARMOR_SLOT_HELMET = new ResourceLocation(AdAstra.MOD_ID, "item/icons/armor_slot_helmet");
    public static final ResourceLocation ARMOR_SLOT_CHESTPLATE = new ResourceLocation(AdAstra.MOD_ID, "item/icons/armor_slot_chestplate");
    public static final ResourceLocation ARMOR_SLOT_LEGGINGS = new ResourceLocation(AdAstra.MOD_ID, "item/icons/armor_slot_leggings");
    public static final ResourceLocation ARMOR_SLOT_BOOTS = new ResourceLocation(AdAstra.MOD_ID, "item/icons/armor_slot_boots");
    public static final ResourceLocation ARMOR_SLOT_SHIELD = new ResourceLocation(AdAstra.MOD_ID, "item/icons/armor_slot_shield");
    public static final ResourceLocation BATTERY_SLOT_ICON = new ResourceLocation(AdAstra.MOD_ID, "item/icons/battery_slot_icon");

    public static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{ARMOR_SLOT_BOOTS, ARMOR_SLOT_LEGGINGS, ARMOR_SLOT_CHESTPLATE, ARMOR_SLOT_HELMET};

    protected final T entity;
    protected final Inventory inventory;
    protected final Player player;
    protected final Level level;

    public BasicContainerMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity) {
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
    public boolean stillValid(Player player) {
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
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + i * 9 + 9, getPlayerInvXOffset() + j * 18, getPlayerInvYOffset() + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(inventory, k, getPlayerInvXOffset() + k * 18, getPlayerInvYOffset() + 58));
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

    protected static class ArmorSlot extends Slot {
        private final EquipmentSlot equipmentSlot;

        public ArmorSlot(Inventory inventory, int index, int xPosition, int yPosition, EquipmentSlot equipmentSlot) {
            super(inventory, index, xPosition, yPosition);
            this.equipmentSlot = equipmentSlot;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return equipmentSlot == Mob.getEquipmentSlotForItem(stack);
        }

        @Override
        public boolean mayPickup(@NotNull Player player) {
            ItemStack itemStack = this.getItem();
            return (itemStack.isEmpty() || player.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack)) && super.mayPickup(player);
        }

        @Override
        public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, TEXTURE_EMPTY_SLOTS[equipmentSlot.getIndex()]);
        }
    }
}
