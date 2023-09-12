package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.SeparatorBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class SeparatorMenu extends BasicContainerMenu<SeparatorBlockEntity> {

    public SeparatorMenu(int id, Inventory inventory, SeparatorBlockEntity entity) {
        super(ModMenus.SEPARATOR.get(), id, inventory, entity);
    }

    public SeparatorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.SEPARATOR.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, SeparatorBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 7;
    }

    @Override
    protected int getInventoryStart() {
        return 7;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 4;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 169;
    }

    @Override
    protected void addMenuSlots() {
        // Battery
        addSlot(new ImageSlot(entity, 0, 153, 7, BATTERY_SLOT_ICON));

        // Bucket slots
        addSlot(new Slot(entity, 1, 51, 138));
        addSlot(new FurnaceResultSlot(player, entity, 2, 69, 138));
        addSlot(new Slot(entity, 3, 11, 138));
        addSlot(new FurnaceResultSlot(player, entity, 4, 29, 138));
        addSlot(new Slot(entity, 5, 91, 138));
        addSlot(new FurnaceResultSlot(player, entity, 6, 109, 138));
    }
}
