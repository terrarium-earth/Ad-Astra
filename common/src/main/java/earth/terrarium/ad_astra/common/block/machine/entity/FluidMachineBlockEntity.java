package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.container.DoubleFluidTank;
import earth.terrarium.botarium.common.energy.base.EnergyAttachment;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidAttachment.Block, EnergyAttachment.Block {
    protected WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer tanks;

    public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public abstract long getInputTankCapacity();

    public abstract long getOutputTankCapacity();


    public FluidHolder getInputTank() {
        return getFluidContainer(this).getFluids().get(0);
    }

    public FluidHolder getOutputTank() {
        return getFluidContainer(this).getFluids().get(1);
    }

    public abstract Predicate<FluidHolder> getInputFilter();

    @Override
    public WrappedBlockFluidContainer getFluidContainer(BlockEntity holder) {
        return tanks == null ? tanks = new WrappedBlockFluidContainer(this, new DoubleFluidTank(getInputTankCapacity(), getOutputTankCapacity(), getInputFilter(), f -> true)) : this.tanks;
    }

    public DoubleFluidTank getDoubleFluidTank() {
        return (DoubleFluidTank) getFluidContainer(this).container();
    }

    public abstract long getEnergyPerTick();

    public long getMaxCapacity() {
        return this.getEnergyStorage(this).getMaxCapacity();
    }

    @Override
    public abstract WrappedBlockEnergyContainer getEnergyStorage(BlockEntity holder);
}