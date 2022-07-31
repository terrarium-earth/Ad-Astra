package com.github.alexnijjar.beyond_earth.blocks.pads;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

@SuppressWarnings("deprecation")
public class RocketLaunchPad extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty STAGE = Properties.LIT;

    public RocketLaunchPad(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(WATERLOGGED, false).with(STAGE, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d offset = state.getModelOffset(world, pos);

        if (state.get(STAGE)) {
            return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 4, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
        } else {
            return VoxelShapes.union(Block.createCuboidShape(0, 0, 0, 16, 3, 16)).offset(offset.getX(), offset.getY(), offset.getZ());
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
        builder.add(WATERLOGGED, STAGE);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (!world.isClient) {
            boolean raise = checkInRadius(true, pos, world) && checkInRadius(false, pos, world);
            world.setBlockState(pos, state.with(STAGE, raise));
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        // Update all the neighbors in a 3x3 area.
        if (!world.isClient) {
            boolean raise = checkInRadius(true, pos, world) && checkInRadius(false, pos, world);
            if (raise) {
                world.setBlockState(pos, state.with(STAGE, raise));
            } else {
                world.updateNeighbor(pos.north().east(), this, pos);
                world.updateNeighbor(pos.north().west(), this, pos);
                world.updateNeighbor(pos.south().east(), this, pos);
                world.updateNeighbor(pos.south().west(), this, pos);
                for (Direction dir : Direction.values()) {
                    world.updateNeighbor(pos.offset(dir), this, pos);
                }
            }
        }
        super.onBlockAdded(state, world, pos, oldState, notify);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient) {
            for (Direction dir : Direction.values()) {
                world.updateNeighbor(pos.offset(dir), this, pos);
            }
            world.updateNeighbor(pos.north().east(), this, pos);
            world.updateNeighbor(pos.north().west(), this, pos);
            world.updateNeighbor(pos.south().east(), this, pos);
            world.updateNeighbor(pos.south().west(), this, pos);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

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

    private static boolean checkInRadius(boolean lookForPlatforms, BlockPos pos, World world) {
        for (int i = 0; i < (lookForPlatforms ? 3 : 5); i++) {
            for (int j = 0; j < (lookForPlatforms ? 3 : 5); j++) {
                BlockPos padPos = new BlockPos(pos.getX() + i - (lookForPlatforms ? 1 : 2), pos.getY(), pos.getZ() + j - (lookForPlatforms ? 1 : 2));
                BlockState state = world.getBlockState(padPos);
                boolean isRocket = state.getBlock() instanceof RocketLaunchPad;

                // Skip for self.
                if (padPos.equals(pos)) {
                    continue;
                }

                if (lookForPlatforms) {
                    // Checks if every block in a 3x3 radius is a rocket launch pad.
                    if (!isRocket) {
                        return false;
                    }
                } else {
                    // Checks if every block in a 5x5 radius is not a raised rocket launch pad.
                    if (isRocket && state.get(RocketLaunchPad.STAGE).equals(true)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}