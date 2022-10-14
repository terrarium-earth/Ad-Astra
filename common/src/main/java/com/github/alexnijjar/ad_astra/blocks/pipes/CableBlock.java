package com.github.alexnijjar.ad_astra.blocks.pipes;

import java.util.HashMap;
import java.util.Map;

import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;

import earth.terrarium.botarium.api.energy.EnergyHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class CableBlock extends AbstractPipeBlock {

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

    public CableBlock(long transferRate, int decay, double size, Settings settings) {
        super(transferRate, decay, size, settings);
        this.setDefaultState(getDefaultState().with(UP, false).with(DOWN, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public void updateShape(World world, BlockPos pos, BlockState state, Direction direction) {
        if (!world.isClient) {
            BlockPos offset = pos.offset(direction);
            boolean connect = world.getBlockState(offset).getBlock() instanceof CableBlock || EnergyHooks.safeGetBlockEnergyManager(world.getBlockEntity(pos.offset(direction)), direction).orElse(null) != null;

            if (connect) {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), true), Block.NOTIFY_ALL);
                if (world.getBlockState(offset).getBlock().equals(this)) {
                    world.setBlockState(offset, world.getBlockState(offset).with(DIRECTIONS.get(direction.getOpposite()), true), Block.NOTIFY_ALL);
                }
            } else {
                world.setBlockState(pos, world.getBlockState(pos).with(DIRECTIONS.get(direction), false), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
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

    @Override
    public void handleWrench(World world, BlockPos pos, BlockState state, Direction side, PlayerEntity user) {
        if (!world.isClient) {
            BooleanProperty property = DIRECTIONS.get(user.isSneaking() ? user.getMovementDirection() : side);
            int pitch = (int) user.getPitch();
            if (pitch > 60) {
                property = DIRECTIONS.get(Direction.DOWN);
            } else if (pitch < -60) {
                property = DIRECTIONS.get(Direction.UP);
            }
            world.setBlockState(pos, state.with(property, !state.get(property)), Block.NOTIFY_ALL, 0);
            world.playSound(null, pos, ModSoundEvents.WRENCH.get(), SoundCategory.BLOCKS, 1, 1);
        }
    }
}