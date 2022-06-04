package com.github.alexnijjar.beyond_earth.mixin.coal_torches;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "litServerTick")
    private static void litServerTick(World world, BlockPos pos, BlockState state, CampfireBlockEntity campfire, CallbackInfo info) {
        // Extinguish the campfire in worlds with no oxygen.
        if (!state.getBlock().equals(Blocks.SOUL_CAMPFIRE)) {
            if (!ModUtils.worldHasOxygen(world)) {
                world.setBlockState(pos, state.with(CampfireBlock.LIT, false), 3);
                world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
            }
        }
    }
}