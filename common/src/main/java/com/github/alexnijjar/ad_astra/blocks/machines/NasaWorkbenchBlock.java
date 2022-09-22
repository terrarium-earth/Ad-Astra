package com.github.alexnijjar.ad_astra.blocks.machines;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.NasaWorkbenchBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class NasaWorkbenchBlock extends AbstractMachineBlock {

	public NasaWorkbenchBlock(Settings settings) {
		super(settings);
	}

	@Override
	protected boolean useFacing() {
		return true;
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		return true;
	}

	@Override
	public boolean removeOutput() {
		return true;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d offset = state.getModelOffset(world, pos);
		return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 19.2, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
	}

	@Override
	public NasaWorkbenchBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NasaWorkbenchBlockEntity(pos, state);
	}
}