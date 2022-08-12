package com.github.alexnijjar.ad_astra.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        Block block = (Block) (Object) this;
        if (block instanceof PlantBlock || block instanceof CactusBlock) {
            if (!OxygenUtils.worldHasOxygen(world, pos)) {
                world.breakBlock(pos, true);
            }
        }

        if (block instanceof GrassBlock) {
            if (!OxygenUtils.worldHasOxygen(world, pos)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        }

        if (block instanceof LeavesBlock) {
            if (!OxygenUtils.worldHasOxygen(world, pos)) {
                world.breakBlock(pos, false);
            }
        }

        if (block instanceof VineBlock) {
            if (!OxygenUtils.worldHasOxygen(world, pos)) {
                world.breakBlock(pos, false);
            }
        }
    }
}
