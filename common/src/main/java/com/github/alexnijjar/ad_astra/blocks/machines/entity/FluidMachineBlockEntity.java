package com.github.alexnijjar.ad_astra.blocks.machines.entity;

import com.github.alexnijjar.ad_astra.util.FluidUtils;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class FluidMachineBlockEntity extends AbstractMachineBlockEntity implements FluidHoldingBlock {

	public final SingleVariantStorage<FluidHolder> inputTank = FluidUtils.createTank(this, getInputSize(), true, true);
	public final SingleVariantStorage<FluidHolder> outputTank = FluidUtils.createTank(this, getOutputSize(), true, true);

	public FluidMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
	}

	public abstract long getInputSize();

	public abstract long getOutputSize();

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		inputTank.variant = FluidHolder.fromNbt(nbt.getCompound("inputFluid"));
		inputTank.amount = nbt.getLong("inputAmount");

		outputTank.variant = FluidHolder.fromNbt(nbt.getCompound("outputFluid"));
		outputTank.amount = nbt.getLong("outputAmount");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		nbt.put("inputFluid", inputTank.variant.toNbt());
		nbt.putLong("inputAmount", inputTank.amount);

		nbt.put("outputFluid", outputTank.variant.toNbt());
		nbt.putLong("outputAmount", outputTank.amount);
		super.writeNbt(nbt);
	}

	@Override
	public void tick() {
		// Ensure that the tanks don't have a variant when there is no fluid in them.
		if (this.inputTank.amount == 0) {
			this.inputTank.variant = FluidHolder.of(Fluids.EMPTY);
		}
		if (this.outputTank.amount == 0) {
			this.outputTank.variant = FluidHolder.of(Fluids.EMPTY);
		}
	}

	@Override
	public UpdatingFluidContainer getFluidContainer() {
		return new FluidContainer
	}
}