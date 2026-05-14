package earth.terrarium.adastra.common.menus.base;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.SideConfigurable;
import earth.terrarium.adastra.common.menus.slots.BatterySlot;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class MachineMenu<T extends BlockEntity & SideConfigurable & Container> extends BaseConfigurableContainerMenu<T> {

    private Slot batterySlot;

    public MachineMenu(@Nullable MenuType<?> type, int id, Inventory inventory, T entity) {
        super(type, id, inventory, entity);
    }

    public Slot getBatterySlot() {
        return this.batterySlot;
    }

    @Override
    public int getPlayerInvXOffset() {
        return 8;
    }

    @Override
    protected void addMenuSlots() {
        this.batterySlot = addSlot(new BatterySlot(entity, 0));
    }
}
