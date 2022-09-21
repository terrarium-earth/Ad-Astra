package com.github.alexnijjar.ad_astra.blocks.fluid;

import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

// This fluid is not mean to be placable.
public abstract class OxygenFluid extends ModFluid {
	@Override
	public Fluid getFlowing() {
		return Fluids.FLOWING_WATER;
	}

	@Override
	public Fluid getStill() {
		return ModFluids.OXYGEN_STILL;
	}

	@Override
	public Item getBucketItem() {
		return ModItems.OXYGEN_BUCKET.get();
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return ModFluids.OXYGEN_BLOCK.get().getDefaultState();
	}

	public static class Still extends OxygenFluid {
		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}

	@Override
	protected boolean canFlow(BlockView world, BlockPos fluidPos, BlockState fluidBlockState, Direction flowDirection, BlockPos flowTo, BlockState flowToBlockState, FluidState fluidState, Fluid fluid) {
		return false;
	}

	@Override
	protected int getFlowSpeed(WorldView worldView) {
		return 0;
	}

	@Override
	protected int getLevelDecreasePerBlock(WorldView worldView) {
		return 0;
	}
}