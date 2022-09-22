package com.github.alexnijjar.ad_astra.mixin.oxygen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.entity.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(FireBlock.class)
public class FireBlockMixin {

	@Inject(method = "onBlockAdded", at = @At(value = "HEAD"))
	public void adastra_onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
		if (!AdAstra.CONFIG.general.doOxygen) {
			return;
		}
		// Extinguish the fire in dimensions with no oxygen.
		if (!state.getBlock().equals(Blocks.SOUL_FIRE)) {
			if (!OxygenUtils.posHasOxygen(world, pos)) {
				world.removeBlock(pos, false);
				world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
			}
		}
	}
}