package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.menus.configuration.SlotConfiguration;
import earth.terrarium.adastra.common.menus.slots.CustomSlot;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class OxygenDistributorMenu extends MachineMenu<OxygenDistributorBlockEntity> {

    public OxygenDistributorMenu(int id, Inventory inventory, OxygenDistributorBlockEntity entity) {
        super(ModMenus.OXYGEN_DISTRIBUTOR.get(), id, inventory, entity);
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
    public int getPlayerInvYOffset() {
        return 162;
    }

    @Override
    protected void addMenuSlots() {
        super.addMenuSlots();
        addSlot(new Slot(entity, 1, 17, 82));
        addSlot(CustomSlot.noPlace(entity, 2, 17, 112));
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new SlotConfiguration(0, 17, 82));
        addConfigSlot(new SlotConfiguration(1, 17, 112));

        addConfigSlot(new EnergyConfiguration(2, 147, 82, entity.getEnergyStorage()));
        addConfigSlot(new FluidConfiguration(3, 51, 82, entity.getFluidContainer(), 0));
        addConfigSlot(new FluidConfiguration(4, 116, 82, entity.getFluidContainer(), 1));
    }
}
