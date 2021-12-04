package net.mrscauthd.boss_tools.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RocketLaunchPad extends Block implements IWaterLoggable  {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty STAGE = BlockStateProperties.LIT;

    public RocketLaunchPad(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(STAGE, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, flag);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, STAGE);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() != this.getDefaultState().getBlock()) {
            return true;
        }
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(world, pos);
        if (state.get(STAGE)) {
            return VoxelShapes.or(makeCuboidShape(0, 0, 0, 16, 4, 16)).withOffset(offset.x, offset.y, offset.z);
        } else {
            return VoxelShapes.or(makeCuboidShape(0, 0, 0, 16, 3, 16)).withOffset(offset.x, offset.y, offset.z);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onBlockAdded(state, world, pos, oldState, moving);
        world.getPendingBlockTicks().scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<Integer[]> x = new ArrayList<Integer[]>();
        x.add(new Integer[]{0,1});
        x.add(new Integer[]{1,1});
        x.add(new Integer[]{1,0});
        x.add(new Integer[]{-1,0});
        x.add(new Integer[]{-1,-1});
        x.add(new Integer[]{0,-1});
        x.add(new Integer[]{1,-1});
        x.add(new Integer[]{-1,1});

        List<Integer[]> y = new ArrayList<Integer[]>();
        y.add(new Integer[]{0,2});
        y.add(new Integer[]{0,-2});
        y.add(new Integer[]{2,0});
        y.add(new Integer[]{-2,0});
        y.add(new Integer[]{-2,1});
        y.add(new Integer[]{-2,-1});
        y.add(new Integer[]{2,1});
        y.add(new Integer[]{1,-2});
        y.add(new Integer[]{2,-2});
        y.add(new Integer[]{-2,-2});
        y.add(new Integer[]{-1,-1});
        y.add(new Integer[]{3,1});
        y.add(new Integer[]{1,2});
        y.add(new Integer[]{2,2});

        boolean canEdit = true;

        for (Integer[] value : x) {
            BlockPos bp = new BlockPos(pos.getX()+value[0],pos.getY(),pos.getZ()+value[1]);

            if (!(world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && !world.getBlockState(bp).get(STAGE))) {
                canEdit = false;
            }
        }

        for (Integer[] val : y) {
            BlockPos bp = new BlockPos(pos.getX()+val[0],pos.getY(),pos.getZ()+val[1]);

        if (!(!(world.getBlockState(bp).getBlock() instanceof RocketLaunchPad) || world.getBlockState(bp).getBlock() instanceof RocketLaunchPad && !world.getBlockState(bp).get(STAGE))) {
                canEdit = false;
            }
        }

        if (canEdit) {
            world.setBlockState(pos, state.with(STAGE, true), 2);
            world.updateComparatorOutputLevel(pos, this);
        } else if (state.get(STAGE)){
            world.setBlockState(pos, state.with(STAGE, false), 2);
            world.updateComparatorOutputLevel(pos, this);
        }

        world.getPendingBlockTicks().scheduleTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

}
