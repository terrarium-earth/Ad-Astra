package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock, EnergyBlock {
    protected InsertOnlyEnergyContainer energyContainer;

    private DoubleFluidTank tanks;

    public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public abstract long getInputTankCapacity();

    public abstract long getOutputTankCapacity();


    public FluidHolder getInputTank() {
        return getFluidContainer().getFluids().get(0);
    }

    public FluidHolder getOutputTank() {
        return getFluidContainer().getFluids().get(1);
    }

    public abstract Predicate<FluidHolder> getInputFilter();

    @Override
    public UpdatingFluidContainer getFluidContainer() {
        return tanks == null ? tanks = new DoubleFluidTank(this, getInputTankCapacity(), getOutputTankCapacity(), getInputFilter(), f -> true) : this.tanks;
    }

    public DoubleFluidTank getTanks() {
        return (DoubleFluidTank) getFluidContainer();
    }

    public abstract long getEnergyPerTick();

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public abstract InsertOnlyEnergyContainer getEnergyStorage();

    @Override
    public void update() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }
}