package earth.terrarium.adastra.common.menus.machines;

import earth.terrarium.adastra.common.blockentities.machines.GravityNormalizerBlockEntity;
import earth.terrarium.adastra.common.menus.base.MachineMenu;
import earth.terrarium.adastra.common.menus.configuration.EnergyConfiguration;
import earth.terrarium.adastra.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;

public class GravityNormalizerMenu extends MachineMenu<GravityNormalizerBlockEntity> {

    public GravityNormalizerMenu(int id, Inventory inventory, GravityNormalizerBlockEntity entity) {
        super(ModMenus.GRAVITY_NORMALIZER.get(), id, inventory, entity);
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
        return 12;
    }

    @Override
    public int getPlayerInvYOffset() {
        return 130;
    }

    @Override
    protected void addConfigSlots() {
        addConfigSlot(new EnergyConfiguration(2, 151, 39, entity.getEnergyStorage()));
    }
}
