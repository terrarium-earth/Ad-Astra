package com.github.alexnijjar.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient) {
            FluidBlock block = (FluidBlock) (Object) this;
            if (block.getFluidState(state).isIn(FluidTags.WATER) && !block.equals(ModFluids.CRYO_FUEL_BLOCK)) {
                if (!OxygenUtils.worldHasOxygen(world, pos)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    // Turn water fluids into ice upon contact with cryo fuel
    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    public void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> ci) {
        FluidBlock block = (FluidBlock) (Object) this;
        if (block.equals(Blocks.WATER)) {
            for (Direction direction : new Direction[] { Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST }) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).getBlockState().getBlock().equals(ModFluids.CRYO_FUEL_BLOCK)) {
                    world.setBlockState(pos, Blocks.ICE.getDefaultState());
                    ci.setReturnValue(false);
                }
            }
        }
    }
}
