package net.mrscauthd.beyond_earth.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.gui.screens.energyrouter.EnergyRouterGui;
import net.mrscauthd.beyond_earth.machines.tile.*;

import java.util.HashMap;
import java.util.List;

public class EnergyCableTileEntity extends AbstractMachineBlockEntity {

    protected static HashMap<List<BlockPos>, List<String>> network = new HashMap<>();

    public EnergyCableTileEntity(BlockPos pos, BlockState state) {
        super(ModInit.ENERGY_CABLE.get(), pos, state);
    }

    @Override
    protected void tickProcessing() {
        BlockPos pos = this.getBlockPos();

    }

    @Override
    public boolean hasSpaceInOutput() {
        return false;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new EnergyRouterGui.GuiContainer(id, inventory, this);
    }

    @Override
    protected void createPowerSystems(PowerSystemRegistry map) {
        super.createPowerSystems(map);

        map.put(new PowerSystemEnergyCommon(this) {
            @Override
            public int getBasePowerForOperation() {
                return EnergyCableTileEntity.this.getBasePowerForOperation();
            }
        });
    }

    @Override
    protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
        super.createEnergyStorages(registry);
        registry.put(this.createEnergyStorageCommon());
    }

    public int getBasePowerForOperation() {
        return 1;
    }
}
