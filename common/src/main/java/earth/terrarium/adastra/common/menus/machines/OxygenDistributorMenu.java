package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class OxygenDistributorMenu extends BaseContainerMenu<OxygenDistributorBlockEntity> {

    public OxygenDistributorMenu(int id, Inventory inventory, OxygenDistributorBlockEntity entity) {
        super(ModMenus.OXYGEN_DISTRIBUTOR.get(), id, inventory, entity);
    }

    public OxygenDistributorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.OXYGEN_DISTRIBUTOR.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, OxygenDistributorBlockEntity.class));
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
        return 162;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, 12, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 9, 82));
        addSlot(new FurnaceResultSlot(player, entity, 2, 9, 112));
    }
}
