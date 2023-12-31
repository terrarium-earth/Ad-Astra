package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.CryoFreezerBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.Slot;

public class CryoFreezerMenu extends MachineMenu<CryoFreezerBlockEntity> {

    public CryoFreezerMenu(int id, Inventory inventory, CryoFreezerBlockEntity entity) {
        super(ModMenus.CRYO_FREEZER.get(), id, inventory, entity);
    }

    @Override
    protected int getContainerInputEnd() {
        return 4;
    }

    @Override
    protected int getInventoryStart() {
        return 4;
    }

    @Override
    protected int startIndex() {
        return 1;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 102;
    }

    @Override
    protected void addMenuSlots() {
        addSlot(new Slot(entity, 1, 26, 70));

        addSlot(new Slot(entity, 2, 113, 42));
        addSlot(new FurnaceResultSlot(player, entity, 3, 113, 70));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 26, 70));
        addConfigSlot(new SlotConfiguration(1, 113, 42));
        addConfigSlot(new SlotConfiguration(2, 113, 70));

        addConfigSlot(new EnergyConfiguration(3, 149, 27, entity.getEnergyStorage()));
        addConfigSlot(new FluidConfiguration(4, 86, 38, entity.getFluidContainer(), 0));
    }
}
