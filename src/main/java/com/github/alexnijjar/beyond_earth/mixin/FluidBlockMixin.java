package com.github.alexnijjar.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (!world.isClient) {
            FluidBlock block = (FluidBlock) (Object) this;
            if (block.getFluidState(state).isIn(FluidTags.WATER)) {
                if (!ModUtils.worldHasOxygen(world, pos)) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
    }
}
