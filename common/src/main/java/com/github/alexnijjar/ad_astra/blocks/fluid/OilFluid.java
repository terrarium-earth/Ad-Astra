package com.github.alexnijjar.ad_astra.blocks.fluid;

import com.github.alexnijjar.ad_astra.registry.ModFluids;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class OilFluid extends ModFluid {
	@Override
	public Fluid getFlowing() {
		return ModFluids.FLOWING_OIL;
	}

	@Override
	public Fluid getStill() {
		return ModFluids.OIL_STILL;
	}

	@Override
	public Item getBucketItem() {
		return ModItems.OIL_BUCKET.get();
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return ModFluids.OIL_BLOCK.get().getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
	}

	public static class Flowing extends OilFluid {
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
		public boolean isSource(FluidState fluidState) {
			return false;
		}
	}

	public static class Still extends OilFluid {
		@Override
		public int getLevel(FluidState fluidState) {
			return 8;
		}

		@Override
		public boolean isSource(FluidState fluidState) {
			return true;
		}
	}
}