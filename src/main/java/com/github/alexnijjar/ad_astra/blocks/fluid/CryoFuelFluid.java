package com.github.alexnijjar.ad_astra.blocks.fluid;

import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.world.WorldView;

public abstract class CryoFuelFluid extends ModFluid {
	@Override
	public Fluid getFlowing() {
		return ModFluids.FLOWING_CRYO_FUEL;
	}

	@Override
	public Fluid getStill() {
		return ModFluids.CRYO_FUEL_STILL;
	}

	@Override
	public Item getBucketItem() {
		return ModItems.CRYO_FUEL_BUCKET;
	}

	@Override
	protected int getFlowSpeed(WorldView worldView) {
		return 2;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return ModFluids.CRYO_FUEL_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
	}

	public static class Flowing extends CryoFuelFluid {
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
			super.appendProperties(builder);
			builder.add(LEVEL);
		}

		@Override
		public int getLevel(FluidState fluidState) {
			return fluidState.get(LEVEL);
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return false;
		}
	}

	public static class Still extends CryoFuelFluid {
		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isStill(FluidState fluidState) {
			return true;
		}
	}
}