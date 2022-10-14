package com.github.alexnijjar.ad_astra.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(IceBlock.class)
public class IceBlockMixin {

	@Inject(method = "afterBreak", at = @At("TAIL"))
	public void adastra_afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
		if (!OxygenUtils.posHasOxygen(world, pos) && ModUtils.getWorldTemperature(world) < 0) {
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Inject(method = "melt", at = @At("HEAD"), cancellable = true)
	public void adastra_melt(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		if (!OxygenUtils.posHasOxygen(world, pos) && ModUtils.getWorldTemperature(world) < 0) {
			ci.cancel();
		}
	}
}
