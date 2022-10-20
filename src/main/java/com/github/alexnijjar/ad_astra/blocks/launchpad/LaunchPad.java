package com.github.alexnijjar.ad_astra.blocks.launchpad;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

@SuppressWarnings("deprecation")
public class LaunchPad extends Block implements Waterloggable {

	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final EnumProperty<LocationState> LOCATION = EnumProperty.of("location", LocationState.class);

	public LaunchPad(Settings settings) {
		super(settings);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, false).with(LOCATION, LocationState.CENTER));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d offset = state.getModelOffset(world, pos);
		return Block.createCuboidShape(0, 0, 0, 16, 2, 16).offset(offset.getX(), offset.getY(), offset.getZ());
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, LOCATION);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.BLOCK;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient) {
			world.setBlockState(pos.north(), getDefaultState().with(LOCATION, LocationState.TOP), 3);
			world.setBlockState(pos.east(), getDefaultState().with(LOCATION, LocationState.RIGHT), 3);
			world.setBlockState(pos.south(), getDefaultState().with(LOCATION, LocationState.BOTTOM), 3);
			world.setBlockState(pos.west(), getDefaultState().with(LOCATION, LocationState.LEFT), 3);
			world.setBlockState(pos.north().east(), getDefaultState().with(LOCATION, LocationState.TOP_RIGHT), 3);
			world.setBlockState(pos.north().west(), getDefaultState().with(LOCATION, LocationState.TOP_LEFT), 3);
			world.setBlockState(pos.south().east(), getDefaultState().with(LOCATION, LocationState.BOTTOM_RIGHT), 3);
			world.setBlockState(pos.south().west(), getDefaultState().with(LOCATION, LocationState.BOTTOM_LEFT), 3);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);
		breakPad(world, world.getBlockState(pos), pos, !player.isCreative());
	}

	@Override
	public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (!world.isClient) {
			for (Direction direction : Direction.Type.HORIZONTAL) {
				BlockPos offset = pos.offset(direction);
				BlockState state = world.getBlockState(offset);
				if (state.getBlock().equals(this)) {
					breakPad(world, state, offset, true);
					break;
				}
			}
		}
		super.onDestroyedByExplosion(world, pos, explosion);
	}

	public void breakPad(World world, BlockState state, BlockPos pos, boolean drop) {
		if (!world.isClient && state.getBlock().equals(this)) {
			BlockPos mainPos = this.getMainPos(state, pos);
			world.breakBlock(mainPos, drop);
			getBlockPosAround(mainPos).forEach(blockPos -> world.breakBlock(blockPos, false));
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos mainPos = this.getMainPos(state, pos);
		if (world.getBlockState(pos.down()).getBlock() instanceof LaunchPad) return false;
		return canReplace(world, mainPos) && getBlockPosAround(mainPos).stream().allMatch(blockPos -> canReplace(world, blockPos));
	}

	private boolean canReplace(BlockView world, BlockPos pos) {
		return world.getBlockState(pos).getMaterial().isReplaceable();
	}

	public List<BlockPos> getBlockPosAround(BlockPos pos) {
		return List.of(pos, pos.north(), pos.east(), pos.south(), pos.west(), pos.north().east(), pos.north().west(), pos.south().east(), pos.south().west());
	}

	public BlockPos getMainPos(BlockState state, BlockPos from) {
		return switch (state.get(LOCATION)) {
			case TOP_LEFT -> from.south().east();
			case TOP -> from.south();
			case TOP_RIGHT -> from.south().west();
			case LEFT -> from.east();
			case CENTER -> from;
			case RIGHT -> from.west();
			case BOTTOM_LEFT -> from.north().east();
			case BOTTOM -> from.north();
			case BOTTOM_RIGHT -> from.north().west();
		};
	}
}