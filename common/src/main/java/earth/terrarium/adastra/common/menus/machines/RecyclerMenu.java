package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.RecyclerBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class RecyclerMenu extends BasicContainerMenu<RecyclerBlockEntity> {

    public RecyclerMenu(int id, Inventory inventory, RecyclerBlockEntity entity) {
        super(ModMenus.RECYCLER.get(), id, inventory, entity);
    }

    public RecyclerMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.RECYCLER.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, RecyclerBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 11;
    }

    @Override
    protected int getInventoryStart() {
        return 11;
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

        addSlot(new Slot(entity, 1, 18, 93));

        addSlot(new FurnaceResultSlot(player, entity, 2, 76, 75));
        addSlot(new FurnaceResultSlot(player, entity, 3, 94, 75));
        addSlot(new FurnaceResultSlot(player, entity, 4, 112, 75));
        addSlot(new FurnaceResultSlot(player, entity, 5, 76, 93));
        addSlot(new FurnaceResultSlot(player, entity, 6, 94, 93));
        addSlot(new FurnaceResultSlot(player, entity, 7, 112, 93));
        addSlot(new FurnaceResultSlot(player, entity, 8, 76, 111));
        addSlot(new FurnaceResultSlot(player, entity, 9, 94, 111));
        addSlot(new FurnaceResultSlot(player, entity, 10, 112, 111));
    }
}
