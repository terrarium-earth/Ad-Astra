package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class CryoFreezerMenu extends BaseContainerMenu<CryoFreezerBlockEntity> {

    public CryoFreezerMenu(int id, Inventory inventory, CryoFreezerBlockEntity entity) {
        super(ModMenus.CRYO_FREEZER.get(), id, inventory, entity);
    }

    public CryoFreezerMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.CRYO_FREEZER.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, CryoFreezerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 4;
    }

    @Override
    protected int getInventoryStart() {
        return 4;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 0;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 99;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 18, 70));

        addSlot(new Slot(entity, 2, 105, 42));
        addSlot(new FurnaceResultSlot(player, entity, 3, 105, 70));
    }
}
