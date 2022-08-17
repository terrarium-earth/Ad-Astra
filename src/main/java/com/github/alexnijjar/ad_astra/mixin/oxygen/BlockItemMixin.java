package com.github.alexnijjar.ad_astra.mixin.oxygen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockItem.class)
public class BlockItemMixin {

	@Unique
	private static void playFireExtinguish(BlockPos pos, World world) {
		world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
	}

	@Inject(method = "place", at = @At(value = "TAIL"))
	public void place(ItemPlacementContext context, CallbackInfoReturnable<ActionResult> info) {
		if (!AdAstra.CONFIG.general.doOxygen) {
			return;
		}
		// Extinguish fire items in dimensions with no oxygen.
		World world = context.getWorld();
		if (!world.isClient) {
			BlockPos pos = context.getBlockPos();
			if (!OxygenUtils.worldHasOxygen(world, pos)) {
				BlockState blockstate = world.getBlockState(context.getBlockPos());
				Block block = blockstate.getBlock();

				boolean playSound = false;

				// Wall Torch.
				if (block instanceof WallTorchBlock && !block.equals(Blocks.SOUL_WALL_TORCH)) {
					world.setBlockState(pos, ModBlocks.WALL_COAL_TORCH.getDefaultState().with(WallTorchBlock.FACING, blockstate.get(WallTorchBlock.FACING)), 3);
					playSound = true;
				}

				// Torch.
				else if (block instanceof TorchBlock && !block.equals(Blocks.SOUL_TORCH) && !block.equals(Blocks.SOUL_WALL_TORCH)) {
					if (!block.equals(Blocks.REDSTONE_TORCH) && !block.equals(Blocks.REDSTONE_WALL_TORCH)) {
						world.setBlockState(pos, ModBlocks.COAL_TORCH.getDefaultState(), 3);
						playSound = true;
					}
				}

				// Lantern.
				else if (block instanceof LanternBlock && !block.equals(Blocks.SOUL_LANTERN)) {
					world.setBlockState(pos, ModBlocks.COAL_LANTERN.getDefaultState().with(LanternBlock.HANGING, blockstate.get(LanternBlock.HANGING)), 3);
					playSound = true;
				}

				// Campfire.
				else if ((block instanceof CampfireBlock && !block.equals(Blocks.SOUL_CAMPFIRE)) && blockstate.get(CampfireBlock.LIT)) {
					world.setBlockState(pos, blockstate.with(CampfireBlock.LIT, false), 3);
					playSound = true;
				}

				if (playSound) {
					playFireExtinguish(pos, world);
				}
			}
		}
	}
}