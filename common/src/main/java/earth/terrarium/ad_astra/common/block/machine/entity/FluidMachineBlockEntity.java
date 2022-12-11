package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.container.DoubleFluidTank;
import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.InsertOnlyEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
    public DoubleFluidTank getFluidContainer() {
        return tanks == null ? tanks = new DoubleFluidTank(this, getInputTankCapacity(), getOutputTankCapacity(), getInputFilter(), f -> true) : this.tanks;
    }

    public abstract long getEnergyPerTick();

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public abstract InsertOnlyEnergyContainer getEnergyStorage();

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }
}