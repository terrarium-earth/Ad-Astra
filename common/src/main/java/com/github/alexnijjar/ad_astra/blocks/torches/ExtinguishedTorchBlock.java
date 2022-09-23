package com.github.alexnijjar.ad_astra.blocks.torches;

import com.github.alexnijjar.ad_astra.registry.ModBlocks;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class ExtinguishedTorchBlock extends Block {

	public ExtinguishedTorchBlock(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			ItemStack itemstack = player.getStackInHand(hand);

			if (OxygenUtils.posHasOxygen(world, pos)) {
				if (itemstack.getItem() instanceof FlintAndSteelItem || itemstack.getItem() instanceof FireChargeItem) {

					if (world.getBlockState(pos).getBlock().equals(ModBlocks.EXTINGUISHED_TORCH.get())) {
						world.setBlockState(pos, Blocks.TORCH.getDefaultState(), 3);
					} else {
						world.setBlockState(pos, Blocks.WALL_TORCH.getStateWithProperties(state), 3);
					}

					itemstack.getItem().use(world, player, hand);

					boolean hasFlint = itemstack.getItem() instanceof FlintAndSteelItem;

					if (hasFlint) {
						world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1);
					} else {
						world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);
					}

					if (!player.isCreative()) {
						if (hasFlint) {
							itemstack.damage(1, world.random, (ServerPlayerEntity) player);
						} else {
							itemstack.decrement(1);
						}
					}
				}
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Blocks.TORCH.getOutlineShape(state, world, pos, context);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction.equals(Direction.DOWN) && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}
}