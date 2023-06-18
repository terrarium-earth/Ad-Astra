package earth.terrarium.ad_astra.common.block.machine.entity;

import earth.terrarium.ad_astra.common.container.DoubleFluidTank;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.impl.WrappedBlockEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements BotariumFluidBlock<WrappedBlockFluidContainer>, BotariumEnergyBlock<WrappedBlockEnergyContainer> {
    protected WrappedBlockEnergyContainer energyContainer;
    private WrappedBlockFluidContainer tanks;

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
    public WrappedBlockFluidContainer getFluidContainer() {
        return tanks == null ? tanks = new WrappedBlockFluidContainer(this, new DoubleFluidTank(getInputTankCapacity(), getOutputTankCapacity(), getInputFilter(), f -> true)) : this.tanks;
    }

    public DoubleFluidTank getDoubleFluidTank() {
        return (DoubleFluidTank) getFluidContainer().container();
    }

    public abstract long getEnergyPerTick();

    public long getMaxCapacity() {
        return this.getEnergyStorage().getMaxCapacity();
    }

    @Override
    public abstract WrappedBlockEnergyContainer getEnergyStorage();
}