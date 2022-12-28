package earth.terrarium.ad_astra.common.screen.menu;

import earth.terrarium.ad_astra.common.block.machine.entity.ProcessingMachineBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public abstract class ProcessingMachineMenu<T extends ProcessingMachineBlockEntity> extends AbstractMachineMenu<T> {

    private final DataSlot cookTime;
    private final DataSlot cookTimeTotal;

    public ProcessingMachineMenu(MenuType<?> type, int syncId, Inventory inventory, T entity, Slot[] slots) {
        super(type, syncId, inventory, entity, slots);
        this.cookTime = this.addDataSlot(DataSlot.standalone());
        this.cookTimeTotal = this.addDataSlot(DataSlot.standalone());
    }

    @Override
    public void syncClientScreen() {
        this.cookTime.set(this.machine.getCookTime());
        this.cookTimeTotal.set(this.machine.getCookTimeTotal());
    }

    public int getCookTime() {
        return this.cookTime.get();
    }

    public int getCookTimeTotal() {
        return this.cookTimeTotal.get();
    }
}
