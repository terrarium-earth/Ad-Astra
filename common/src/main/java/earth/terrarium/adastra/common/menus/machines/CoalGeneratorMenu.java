package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CoalGeneratorBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class CoalGeneratorMenu extends MachineMenu<CoalGeneratorBlockEntity> {

    public CoalGeneratorMenu(int id, Inventory inventory, CoalGeneratorBlockEntity entity) {
        super(ModMenus.COAL_GENERATOR.get(), id, inventory, entity);
    }

    @Override
    protected int getContainerInputEnd() {
        return 2;
    }

    @Override
    protected int getInventoryStart() {
        return 2;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 107;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 1, 77, 71));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 77, 71));
        addConfigSlot(new EnergyConfiguration(1, 146, 32, entity.getEnergyStorage()));
    }
}
