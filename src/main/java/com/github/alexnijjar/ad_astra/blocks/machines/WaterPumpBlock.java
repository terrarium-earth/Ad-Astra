package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.WaterPumpBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WaterPumpBlock extends AbstractMachineBlock {

	public static VoxelShape SOUTH_SHAPE = VoxelShapes.empty();
	public static VoxelShape NORTH_SHAPE = VoxelShapes.empty();
	public static VoxelShape EAST_SHAPE = VoxelShapes.empty();
	public static VoxelShape WEST_SHAPE = VoxelShapes.empty();

	public WaterPumpBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected boolean useFacing() {
		return true;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(FACING)) {
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case EAST -> EAST_SHAPE;
			case WEST -> WEST_SHAPE;
			default -> throw new IllegalStateException("Unexpected value: " + state.get(FACING));
		};
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = this.getDefaultState().with(POWERED, false);
		return this.useFacing() ? state.with(FACING, ctx.getPlayerFacing()) : state;
	}

	@Override
	public WaterPumpBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new WaterPumpBlockEntity(pos, state);
	}

	static {
		NORTH_SHAPE = VoxelShapes.combine(NORTH_SHAPE, VoxelShapes.cuboid(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanBiFunction.OR);
		NORTH_SHAPE = VoxelShapes.combine(NORTH_SHAPE, VoxelShapes.cuboid(0.343749, 0.34375, 0, 0.65625, 0.65625, 0.0625), BooleanBiFunction.OR);
		NORTH_SHAPE = VoxelShapes.combine(NORTH_SHAPE, VoxelShapes.cuboid(0.375, 0.375, 0.0625, 0.625, 0.625, 0.375), BooleanBiFunction.OR);
		NORTH_SHAPE = VoxelShapes.combine(NORTH_SHAPE, VoxelShapes.cuboid(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanBiFunction.OR);
		NORTH_SHAPE = VoxelShapes.combine(NORTH_SHAPE, VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanBiFunction.OR);

		SOUTH_SHAPE = VoxelShapes.combine(SOUTH_SHAPE, VoxelShapes.cuboid(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanBiFunction.OR);
		SOUTH_SHAPE = VoxelShapes.combine(SOUTH_SHAPE, VoxelShapes.cuboid(0.343749, 0.34375, 0.9375, 0.65625, 0.65625, 1), BooleanBiFunction.OR);
		SOUTH_SHAPE = VoxelShapes.combine(SOUTH_SHAPE, VoxelShapes.cuboid(0.375, 0.375, 0.625, 0.625, 0.625, 0.9375), BooleanBiFunction.OR);
		SOUTH_SHAPE = VoxelShapes.combine(SOUTH_SHAPE, VoxelShapes.cuboid(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanBiFunction.OR);
		SOUTH_SHAPE = VoxelShapes.combine(SOUTH_SHAPE, VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanBiFunction.OR);

		EAST_SHAPE = VoxelShapes.combine(EAST_SHAPE, VoxelShapes.cuboid(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanBiFunction.OR);
		EAST_SHAPE = VoxelShapes.combine(EAST_SHAPE, VoxelShapes.cuboid(0.9375, 0.34375, 0.343749, 1, 0.65625, 0.65625), BooleanBiFunction.OR);
		EAST_SHAPE = VoxelShapes.combine(EAST_SHAPE, VoxelShapes.cuboid(0.625, 0.375, 0.375, 0.9375, 0.625, 0.625), BooleanBiFunction.OR);
		EAST_SHAPE = VoxelShapes.combine(EAST_SHAPE, VoxelShapes.cuboid(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanBiFunction.OR);
		EAST_SHAPE = VoxelShapes.combine(EAST_SHAPE, VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanBiFunction.OR);

		WEST_SHAPE = VoxelShapes.combine(WEST_SHAPE, VoxelShapes.cuboid(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanBiFunction.OR);
		WEST_SHAPE = VoxelShapes.combine(WEST_SHAPE, VoxelShapes.cuboid(0, 0.34375, 0.343749, 0.0625, 0.65625, 0.65625), BooleanBiFunction.OR);
		WEST_SHAPE = VoxelShapes.combine(WEST_SHAPE, VoxelShapes.cuboid(0.0625, 0.375, 0.375, 0.375, 0.625, 0.625), BooleanBiFunction.OR);
		WEST_SHAPE = VoxelShapes.combine(WEST_SHAPE, VoxelShapes.cuboid(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanBiFunction.OR);
		WEST_SHAPE = VoxelShapes.combine(WEST_SHAPE, VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanBiFunction.OR);
	}
}