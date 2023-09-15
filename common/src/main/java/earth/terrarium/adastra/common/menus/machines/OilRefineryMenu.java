package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.OilRefineryBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class OilRefineryMenu extends BasicContainerMenu<OilRefineryBlockEntity> {

    public OilRefineryMenu(int id, Inventory inventory, OilRefineryBlockEntity entity) {
        super(ModMenus.OIL_REFINERY.get(), id, inventory, entity);
    }

    public OilRefineryMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.OIL_REFINERY.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, OilRefineryBlockEntity.class));
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
        return 4;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 169;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 153, 7, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 11, 138));
        addSlot(new FurnaceResultSlot(player, entity, 2, 29, 138));
        addSlot(new Slot(entity, 3, 91, 138));
        addSlot(new FurnaceResultSlot(player, entity, 4, 109, 138));
    }
}
