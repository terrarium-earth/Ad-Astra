package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.WaterPumpBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.menus.configuration.FluidConfiguration;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;

public class WaterPumpMenu extends MachineMenu<WaterPumpBlockEntity> {

    public WaterPumpMenu(int id, Inventory inventory, WaterPumpBlockEntity entity) {
        super(ModMenus.WATER_PUMP.get(), id, inventory, entity);
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
    public int getPlayerInvYOffset() {
        return 109;
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new EnergyConfiguration(0, 146, 30, entity.getEnergyStorage()));
        addConfigSlot(new FluidConfiguration(1, 81, 31, entity.getFluidContainer(), 0));
    }
}
