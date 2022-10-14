package com.github.alexnijjar.ad_astra.blocks.flags;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FlagBlock extends BlockWithEntity implements Waterloggable {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public FlagBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

		if (state.get(HALF).equals(DoubleBlockHalf.LOWER)) {
			switch (state.get(FACING)) {
			case NORTH -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.5, 0.625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
				return shape;
			}
			case EAST -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, -0.5, 0.375, 0.625, 0, 0.625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, -0.5, 0.4375, 0.5625, 0.5, 0.5625));
				return shape;
			}
			case SOUTH -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.5, 0.625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
				return shape;
			}
			case WEST -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.375, 0, 0.375, 0.625, 0.5, 0.625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
				return shape;
			}
			default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
			}

		} else {
			switch (state.get(FACING)) {
			case NORTH -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(-0.9375, 0.4375, 0.46875, 0.4375, 1.4375, 0.53125));
				return shape;
			}
			case EAST -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.4375, -0.9375, 0.53125, 1.4375, 0.4375));
				return shape;
			}
			case SOUTH -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.5625, 0.4375, 0.46875, 1.9375, 1.4375, 0.53125));
				return shape;
			}
			case WEST -> {
				VoxelShape shape = VoxelShapes.empty();
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
				shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.46875, 0.4375, 0.5625, 0.53125, 1.4375, 1.9375));
				return shape;
			}
			default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
			}
		}
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		super.neighborUpdate(state, world, pos, block, fromPos, notify);
		if (!world.isClient) {
			if (world.getBlockState(pos).get(HALF).equals(DoubleBlockHalf.LOWER) && world.getBlockState(pos.up()).isAir()) {
				world.breakBlock(pos, false);
			} else if (world.getBlockState(pos).get(HALF).equals(DoubleBlockHalf.UPPER) && world.getBlockState(pos.down()).isAir()) {
				world.breakBlock(pos, false);
			}
		}
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);

		boolean waterAbove = world.getFluidState(pos.up()).isOf(Fluids.WATER);
		world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, waterAbove), 3);

		BlockEntity blockEntity = world.getBlockEntity(pos.up());

		if (placer instanceof PlayerEntity player) {
			if (blockEntity instanceof FlagBlockEntity flagEntity) {
				GameProfile profile = player.getGameProfile();
				flagEntity.setOwner(profile);
				flagEntity.toNbt();
			}
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FlagBlockEntity(pos, state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.DESTROY;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public long getRenderingSeed(BlockState state, BlockPos pos) {
		return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF).equals(DoubleBlockHalf.LOWER) ? 0 : 1).getY(), pos.getZ());
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED, HALF);
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return Block.hasTopRim(world, pos.down());
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) {
			return Fluids.WATER.getStill(false);
		}
		return super.getFluidState(state);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(WATERLOGGED, fluidState.getFluid().equals(Fluids.WATER));
	}
}