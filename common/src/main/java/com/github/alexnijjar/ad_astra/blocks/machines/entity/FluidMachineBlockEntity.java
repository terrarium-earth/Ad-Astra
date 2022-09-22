package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

	public final SimpleUpdatingFluidContainer tanks = new SimpleUpdatingFluidContainer(this, integer -> switch (integer) {
	case 0 -> getInputSize();
	case 1 -> getOutputSize();
	default -> getInputSize();
	}, 2, (amount, fluid) -> true);

	public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public abstract long getInputSize();

	public abstract long getOutputSize();

	@Override
	public void tick() {
		// Ensure that the tanks don't have a variant when there is no fluid in them.
		if (tanks.getFluids().get(0).getFluidAmount() == 0) {
			tanks.getFluids().get(0).setFluid(Fluids.EMPTY);
		}
		if (tanks.getFluids().get(1).getFluidAmount() == 0) {
			tanks.getFluids().get(0).setFluid(Fluids.EMPTY);
		}
	}

	@Override
	public UpdatingFluidContainer getFluidContainer() {
		// TODO
		return new FluidContainer();
	}
}