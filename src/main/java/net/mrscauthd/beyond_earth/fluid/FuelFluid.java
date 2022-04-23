package net.mrscauthd.beyond_earth.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.mrscauthd.beyond_earth.registry.ModFluids;

public abstract class FuelFluid extends Fluid {
    @Override
    public net.minecraft.fluid.Fluid getFlowing() {
        return ModFluids.FLOWING_FUEL;
    }

    @Override
    public net.minecraft.fluid.Fluid getStill() {
        return ModFluids.FUEL_STILL;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.FUEL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.FUEL_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends FuelFluid {
        @Override
        protected void appendProperties(StateManager.Builder<net.minecraft.fluid.Fluid, FluidState> builder) {
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

    public static class Still extends FuelFluid {
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