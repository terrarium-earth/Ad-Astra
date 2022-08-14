package com.github.alexnijjar.ad_astra.blocks.cables;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import team.reborn.energy.api.EnergyStorage;

@SuppressWarnings("deprecation")
public abstract class CableBlock extends BlockWithEntity implements Waterloggable {

    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public static final Map<Direction, BooleanProperty> DIRECTIONS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public CableBlock(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof CableBlockEntity cable) {
                cable.tick();
            }
        };
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(context.getBlockPos()).getFluid().equals(Fluids.WATER));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.updateOutlineShape(state);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        this.updateShape(world, pos, state);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        this.updateShape(world, pos, state);
    }

    // Expand to other cables and machines
    public void updateShape(World world, BlockPos pos, BlockState state) {
        for (Direction dir : Direction.values()) {
            BlockPos offset = pos.offset(dir);
            EnergyStorage storage = EnergyStorage.SIDED.find(world, offset, dir.getOpposite());

            world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(dir), storage != null));
            if (world.getBlockState(offset).getBlock().equals(this)) {
                world.setBlockState(offset, world.getBlockState(offset).with(DIRECTIONS.get(dir.getOpposite()), true));
            }
        }
    }

    // Expand the voxel shape to match the model
    public VoxelShape updateOutlineShape(BlockState state) {
        double size = this.getCableSize();
        VoxelShape shape = VoxelShapes.cuboid(size, size, size, 1 - size, 1 - size, 1 - size);
        
        if (state.get(UP)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1 - size, 1, 1 - size));
        }
        if (state.get(DOWN)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, 0, size, 1 - size, 1 - size, 1 - size));
        }
        if (state.get(NORTH)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, 0, 1 - size, 1 - size, 1 - size));
        }
        if (state.get(EAST)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1, 1 - size, 1 - size));
        }
        if (state.get(SOUTH)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(size, size, size, 1 - size, 1 - size, 1));
        }
        if (state.get(WEST)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, size, size, 1 - size, 1 - size, 1 - size));
        }

        return shape;
    }

    public abstract double getCableSize();

    public abstract int getEnergyDecay();

    public abstract int getEnergyTransfer();

    public abstract long getEnergyCapacity();
}