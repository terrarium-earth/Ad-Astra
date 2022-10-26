package earth.terrarium.ad_astra.blocks.machines.entity;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

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

    @Override
    public UpdatingFluidContainer getFluidContainer() {
        return tanks == null ? tanks = new DoubleFluidTank(this, getInputTankCapacity(), getOutputTankCapacity(), f -> true, f -> true) : this.tanks;
    }
}