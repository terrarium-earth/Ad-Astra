package com.github.alexnijjar.ad_astra.blocks.launchpad;

import com.github.alexnijjar.ad_astra.blocks.door.LocationState;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;

@SuppressWarnings("deprecation")
public class LaunchPad extends BlockWithEntity implements Waterloggable {

	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final EnumProperty<LocationState> LOCATION = EnumProperty.of("location", LocationState.class);

	public LaunchPad(Settings settings) {
		super(settings);
		this.setDefaultState(getDefaultState().with(WATERLOGGED, false).with(LOCATION, LocationState.CENTER));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d offset = state.getModelOffset(world, pos);
		return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 2, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, LOCATION);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) {
			return Fluids.WATER.getStill(false);
		}
		return super.getFluidState(state);
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
			for (Direction direction : new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST }) {
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

			// Bottom
			world.breakBlock(mainPos, drop);
			// Bottom Left
			world.breakBlock(mainPos.north(), false);
			// Bottom Right
			world.breakBlock(mainPos.east(), false);
			// Center
			world.breakBlock(mainPos.south(), false);
			// Left
			world.breakBlock(mainPos.west(), false);
			// Right
			world.breakBlock(mainPos.north().east(), false);
			// Top
			world.breakBlock(mainPos.north().west(), false);
			// Top Left
			world.breakBlock(mainPos.south().east(), false);
			// Top Right
			world.breakBlock(mainPos.south().west(), false);
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos mainPos = this.getMainPos(state, pos);

		return !(world.getBlockState(pos.down()).getBlock() instanceof LaunchPad) && world.getBlockState(mainPos).isAir() &&
		//
				world.getBlockState(mainPos.north()).isAir() &&
				//
				world.getBlockState(mainPos.east()).isAir() &&
				//
				world.getBlockState(mainPos.south()).isAir() &&
				//
				world.getBlockState(mainPos.west()).isAir() &&
				//
				world.getBlockState(mainPos.north().east()).isAir() &&
				//
				world.getBlockState(mainPos.north().west()).isAir() &&
				//
				world.getBlockState(mainPos.south().east()).isAir() &&
				//
				world.getBlockState(mainPos.south().west()).isAir();

	}

	public BlockPos getMainPos(BlockState state, BlockPos from) {
		BlockPos target = from;

		switch (state.get(LOCATION)) {
		case TOP_LEFT -> target = from.south().east();
		case TOP -> target = from.south();
		case TOP_RIGHT -> target = from.south().west();
		case RIGHT -> target = from.west();
		case CENTER -> target = from;
		case LEFT -> target = from.east();
		case BOTTOM -> target = from.north();
		case BOTTOM_LEFT -> target = from.north().east();
		case BOTTOM_RIGHT -> target = from.north().west();
		}
		return target;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return (entityWorld, pos, entityState, blockEntity) -> {
			if (blockEntity instanceof LaunchPadBlockEntity pad) {
				pad.tick();
			}
		};
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		if (state.get(LOCATION).equals(LocationState.CENTER)) {
			return new LaunchPadBlockEntity(pos, state);
		}
		return null;
	}
}