package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.WaterPumpBlockEntity;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class WaterPumpMenu extends BaseContainerMenu<WaterPumpBlockEntity> {

    public WaterPumpMenu(int id, Inventory inventory, WaterPumpBlockEntity entity) {
        super(ModMenus.WATER_PUMP.get(), id, inventory, entity);
    }

    public WaterPumpMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenus.WATER_PUMP.get(), id, inventory, getBlockEntityFromBuf(inventory.player.level(), buf, WaterPumpBlockEntity.class));
    }

    @Override
    protected int getContainerInputEnd() {
        return 1;
    }

    @Override
    protected int getInventoryStart() {
        return 1;
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
        return 98;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new ImageSlot(entity, 0, 146, -25, BATTERY_SLOT_ICON));
    }
}
