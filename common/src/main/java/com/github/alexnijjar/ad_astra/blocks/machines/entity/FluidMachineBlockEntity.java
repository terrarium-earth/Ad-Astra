package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import com.github.alexnijjar.ad_astra.container.DoubleFluidTank;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

	public final DoubleFluidTank tanks = new DoubleFluidTank(this, getInputTankCapacity(), getOutputTankCapacity(), this::canInsertFluid, this::canExtractFluid);

	public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public abstract long getInputTankCapacity();

	public abstract long getOutputTankCapacity();

	protected boolean canInsertFluid(FluidHolder fluid) {
		return true;
	}

	protected boolean canExtractFluid(FluidHolder fluid) {
		return true;
	}

	public FluidHolder getInputTank() {
		return tanks.getFluids().get(0);
	}

	public FluidHolder getOutputTank() {
		return tanks.getFluids().get(1);
	}

	@Override
	public UpdatingFluidContainer getFluidContainer() {
		return tanks;
	}
}