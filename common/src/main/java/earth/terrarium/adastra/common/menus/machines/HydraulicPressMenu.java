package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.HydraulicPressBlockEntity;
import earth.terrarium.adastra.common.menus.base.BasicContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class HydraulicPressMenu extends BasicContainerMenu<HydraulicPressBlockEntity> {

    public HydraulicPressMenu(int id, Inventory inventory, HydraulicPressBlockEntity entity) {
        super(ModMenus.HYDRAULIC_PRESS.get(), id, inventory, entity);
    }

    public HydraulicPressMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.HYDRAULIC_PRESS.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, HydraulicPressBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 3;
    }

    @Override
    protected int getInventoryStart() {
        return 3;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 35;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 145;
    }

    @Override
    protected void addMenuSlots() {
        // Battery
        addSlot(new ImageSlot(entity, 0, 184, 7, BATTERY_SLOT_ICON));

        addSlot(new Slot(entity, 1, 61, 86));
        addSlot(new Slot(entity, 2, 119, 86));
    }
}
