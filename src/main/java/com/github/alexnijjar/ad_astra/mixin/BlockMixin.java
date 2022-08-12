package com.github.alexnijjar.ad_astra.mixin;

import com.github.alexnijjar.ad_astra.util.OxygenUtils;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

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
