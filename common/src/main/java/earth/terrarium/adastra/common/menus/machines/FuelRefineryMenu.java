package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.FuelRefineryBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class FuelRefineryMenu extends BaseContainerMenu<FuelRefineryBlockEntity> {

    public FuelRefineryMenu(int id, Inventory inventory, FuelRefineryBlockEntity entity) {
        super(ModMenus.FUEL_REFINERY.get(), id, inventory, entity);
    }

    public FuelRefineryMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.FUEL_REFINERY.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, FuelRefineryBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 5;
    }

    @Override
    protected int getInventoryStart() {
        return 5;
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
        return 102;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 4, 22));
        addSlot(new FurnaceResultSlot(player, entity, 2, 4, 52));

        addSlot(new Slot(entity, 3, 119, 22));
        addSlot(new FurnaceResultSlot(player, entity, 4, 119, 52));
    }
}
