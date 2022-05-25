package com.github.alexnijjar.beyond_earth.fluid;

import com.github.alexnijjar.beyond_earth.registry.ModFluids;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class OilFluid extends Fluid {
    @Override
    public net.minecraft.fluid.Fluid getFlowing() {
        return ModFluids.FLOWING_OIL;
    }

    @Override
    public net.minecraft.fluid.Fluid getStill() {
        return ModFluids.OIL_STILL;
    }

    @Override
    public Item getBucketItem() {
        return ModFluids.OIL_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return ModFluids.OIL_BLOCK.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends OilFluid {
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

    public static class Still extends OilFluid {
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