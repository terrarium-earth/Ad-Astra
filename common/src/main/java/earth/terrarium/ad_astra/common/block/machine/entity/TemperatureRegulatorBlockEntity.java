package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.block.machine.ContainerMachineBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.screen.machine.TemperatureRegulatorMenu;
import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.ad_astra.common.util.algorithm.FloodFiller3D;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

@MethodsReturnNonnullByDefault
public class TemperatureRegulatorBlockEntity extends ContainerMachineBlockEntity implements EnergyAttachment.Block, FluidAttachment.Block {
    private WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer fluidContainer;
    private boolean showTemperature = false;
    private int temperatureTarget = TemperatureSystem.TARGET_TEMPERATURE;

    public TemperatureRegulatorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityTypes.TEMPERATURE_REGULATOR.get(), blockPos, blockState, 2);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.showTemperature = tag.getBoolean("ShowTemperature");
        this.temperatureTarget = tag.getInt("TemperatureTarget");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("ShowTemperature", this.showTemperature);
        tag.putInt("TemperatureTarget", this.temperatureTarget);
    }

    @Override
    public void serverTick() {
        if (level == null) return;
        if (!getFluidContainer().isEmpty()) {
            if (level.getGameTime() % 40 == 0) {
                int energyCost = 1000; // TODO: Calculate energy and fluid costs
                int oxygenCost = 50;

                if (this.getEnergyStorage().internalExtract(energyCost, true) >= energyCost) {
                    if (getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), oxygenCost, null), true).getFluidAmount() > 0) {
                        this.getEnergyStorage().internalExtract(energyCost, false);

                        getFluidContainer().extractFluid(FluidHooks.newFluidHolder(getFluidContainer().getFluids().get(0).getFluid(), oxygenCost, null), false);
                        this.craft();
                    }
                }
            }
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TemperatureRegulatorMenu(i, inventory, this);
    }

    @Override
    public WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder) {
        return energyContainer == null ? energyContainer = new WrappedBlockEnergyContainer(this, new InsertOnlyEnergyContainer(200000)) : this.energyContainer;
    }

    public WrappedBlockEnergyContainer getEnergyStorage() {
        return getEnergyStorage(this);
    }

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return fluidContainer == null ? fluidContainer = new WrappedBlockFluidContainer(this, new SimpleFluidContainer(i -> FluidHooks.buckets(10f), 1, (tank, fluid) -> fluid.getFluid().is(ModTags.Fluids.HYDROGEN))) : this.fluidContainer;
    }

    public WrappedBlockFluidContainer getFluidContainer() {
        return getFluidContainer(this);
    }

    @Override
    public void update() {
        this.updateFluidSlots();
    }

    private void craft() {
        if (level == null) return;
        Set<BlockPos> positions = FloodFiller3D.run(level, getBlockPos().above());
        TemperatureSystem.modifyTemperatureSources(level, positions, 10, this.temperatureTarget);
        update();
    }

    @Override
    public void updateFluidSlots() {
        FluidUtils.insertItemFluidToTank(this, this, 0, 1, f -> f.is(ModTags.Fluids.HYDROGEN)); // TODO: Use common tag
        FluidUtils.extractTankFluidToItem(this, this, 0, 1, 0, f -> true);
    }
}
