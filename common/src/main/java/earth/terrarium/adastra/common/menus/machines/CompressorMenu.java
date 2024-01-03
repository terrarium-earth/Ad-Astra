package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CompressorBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CompressorMenu extends MachineMenu<CompressorBlockEntity> {

    public CompressorMenu(int id, Inventory inventory, CompressorBlockEntity entity) {
        super(ModMenus.COMPRESSOR.get(), id, inventory, entity);
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
    public int getPlayerInvYOffset() {
        return 114;
    }

    @Override
    protected void addMenuSlots() {
        super.addMenuSlots();
        addSlot(new Slot(entity, 1, 53, 56));
        addSlot(CustomSlot.noPlace(entity, 2, 100, 56));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 53, 56));
        addConfigSlot(new SlotConfiguration(1, 100, 56));

        addConfigSlot(new EnergyConfiguration(2, 147, 30, entity.getEnergyStorage()));
    }
}
