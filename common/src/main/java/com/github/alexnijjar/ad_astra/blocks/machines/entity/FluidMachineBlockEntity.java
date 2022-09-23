package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

	public final SimpleUpdatingFluidContainer tanks = new SimpleUpdatingFluidContainer(this, integer -> switch (integer) {
	case 0 -> getInputTankCapacity();
	case 1 -> getOutputTankCapacity();
	default -> 0L;
	}, 2, (amount, fluid) -> true);

	public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public abstract long getInputTankCapacity();

	public abstract long getOutputTankCapacity();

	@Override
	public void tick() {
		// Ensure that the tanks don't have a variant when there is no fluid in them.
		if (getInputTank().getFluidAmount() == 0) {
			getInputTank().setFluid(Fluids.EMPTY);
		}
		if (getOutputTank().getFluidAmount() == 0) {
			getInputTank().setFluid(Fluids.EMPTY);
		}
	}

	public FluidHolder getInputTank() {
		return tanks.getFluids().get(0);
	}

	public FluidHolder getOutputTank() {
		return getOutputTank();
	}

	@Override
	public UpdatingFluidContainer getFluidContainer() {
		return tanks;
	}
}