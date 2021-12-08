package net.mrscauthd.boss_tools.machines;

import static net.mrscauthd.boss_tools.block.helper.VoxelShapeHelper.boxSimple;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrscauthd.boss_tools.machines.tile.NASAWorkbenchBlockEntity;

public class NASAWorkbenchBlock extends AbstractMachineBlock<NASAWorkbenchBlockEntity> implements SimpleWaterloggedBlock {

	public NASAWorkbenchBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected boolean useFacing() {
		return true;
	}

	@Override
	protected BlockState buildDefaultState() {
		return super.buildDefaultState().setValue(BlockStateProperties.WATERLOGGED, false);
	}

	@Override
	public boolean skipRendering(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(level, pos);
		switch ((Direction) state.getValue(FACING)) {
		case SOUTH:
		default:
			return Shapes.or(boxSimple(16, 0, 16, 0, 19.2, 0)).move(offset.x, offset.y, offset.z);
		case NORTH:
			return Shapes.or(boxSimple(0, 0, 0, 16, 19.2, 16)).move(offset.x, offset.y, offset.z);
		case EAST:
			return Shapes.or(boxSimple(16, 0, 0, 0, 19.2, 16)).move(offset.x, offset.y, offset.z);
		case WEST:
			return Shapes.or(boxSimple(0, 0, 16, 16, 19.2, 0)).move(offset.x, offset.y, offset.z);
		}
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, flag);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
	}

	@Override
	public NASAWorkbenchBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new NASAWorkbenchBlockEntity(pos, state);
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
		NASAWorkbenchBlockEntity blockEntity = (NASAWorkbenchBlockEntity) level.getBlockEntity(pos);
		return blockEntity.cacheRecipes() != null ? 15 : 0;
	}
}
