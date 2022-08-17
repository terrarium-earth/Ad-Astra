package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.HashMap;
import java.util.Map;

import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
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
public class CableBlock extends BlockWithEntity implements Waterloggable, Wrenchable {

    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private long transferRate;
    private int decay;
    private double size;

    public static final Map<Direction, BooleanProperty> DIRECTIONS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public CableBlock(long transferRate, int decay, double size, Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
        this.transferRate = transferRate;
        this.decay = decay;
        this.size = size;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof InteractablePipe<?> pipe) {
                pipe.pipeTick();
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

        EnergyStorage storage = EnergyStorage.SIDED.find((World)world, neighborPos, null);
        boolean connect = storage != null || world.getBlockState(neighborPos).getBlock() instanceof CableBlock;
        if (connect) {
            world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), connect), 0);
            if (world.getBlockState(neighborPos).getBlock().equals(this)) {
                world.setBlockState(neighborPos, world.getBlockState(neighborPos).with(DIRECTIONS.get(direction.getOpposite()), true), 0);
            }
        } else {
            world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), false), 0);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.updateOutlineShape(state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        this.updateShape(world, pos, state);
    }

    // Expand to other cables and machines
    public void updateShape(World world, BlockPos pos, BlockState state) {
        if (!world.isClient) {
            for (Direction dir : Direction.values()) {
                BlockPos offset = pos.offset(dir);
                EnergyStorage storage = EnergyStorage.SIDED.find(world, offset, null);
                boolean connect = storage != null || world.getBlockState(offset).getBlock() instanceof CableBlock;

                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(dir), connect));
                if (world.getBlockState(offset).getBlock().equals(this)) {

                    world.setBlockState(offset, world.getBlockState(offset).with(DIRECTIONS.get(dir.getOpposite()), true));
                }
            }
        }
    }

    // Expand the voxel shape to match the model
    public VoxelShape updateOutlineShape(BlockState state) {
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

    public long getTransferRate() {
        return this.transferRate;
    }

    public int getDecay() {
        return this.decay;
    }

    @Override
    public void handleWrench(World world, BlockPos pos, BlockState state, Direction dir, PlayerEntity user) {
        if (!world.isClient) {
            BooleanProperty property = DIRECTIONS.get(user.getMovementDirection());
            int pitch = (int) user.getPitch();
            if (pitch > 60) {
                property = DIRECTIONS.get(Direction.DOWN);
            } else if (pitch < -60) {
                property = DIRECTIONS.get(Direction.UP);
            }
            world.setBlockState(pos, state.with(property, !state.get(property)));
            world.playSound(null, pos, ModSoundEvents.WRENCH, SoundCategory.BLOCKS, 1, 1);
        }
    }
}