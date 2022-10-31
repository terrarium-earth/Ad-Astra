package earth.terrarium.ad_astra.blocks.pipes;

import earth.terrarium.ad_astra.registry.ModSoundEvents;
import earth.terrarium.botarium.api.energy.EnergyHooks;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CableBlock extends AbstractPipeBlock {

    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final Map<Direction, BooleanProperty> DIRECTIONS = Util.make(new HashMap<>(), map -> {
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
    });

    public CableBlock(long transferRate, int decay, double size, Properties settings) {
        super(transferRate, decay, size, settings);
        this.registerDefaultState(defaultBlockState().setValue(UP, false).setValue(DOWN, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Override
    public void updateShape(Level level, BlockPos pos, BlockState state, Direction direction) {
        if (!level.isClientSide) {
            BlockPos offset = pos.relative(direction);
            BlockEntity entity = level.getBlockEntity(offset);
            if (entity != null && (level.getBlockState(offset).getBlock() instanceof CableBlock || EnergyHooks.safeGetBlockEnergyManager(entity, direction).orElse(null) != null)) {
                level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), true), Block.UPDATE_ALL);
                if (level.getBlockState(offset).getBlock().equals(this)) {
                    level.setBlock(offset, level.getBlockState(offset).setValue(DIRECTIONS.get(direction.getOpposite()), true), Block.UPDATE_ALL);
                }
            } else {
                level.setBlock(pos, level.getBlockState(pos).setValue(DIRECTIONS.get(direction), false), Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public VoxelShape updateOutlineShape(BlockState state) {
        VoxelShape shape = Shapes.box(size, size, size, 1 - size, 1 - size, 1 - size);

        if (state.getValue(UP)) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1, 1 - size));
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, Shapes.box(size, 0, size, 1 - size, 1 - size, 1 - size));
        }
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, Shapes.box(size, size, 0, 1 - size, 1 - size, 1 - size));
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1, 1 - size, 1 - size));
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, Shapes.box(size, size, size, 1 - size, 1 - size, 1));
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, Shapes.box(0, size, size, 1 - size, 1 - size, 1 - size));
        }

        return shape;
    }

    @Override
    public void handleWrench(Level level, BlockPos pos, BlockState state, Direction side, Player user) {
        if (!level.isClientSide) {
            BooleanProperty property = DIRECTIONS.get(user.isShiftKeyDown() ? user.getMotionDirection() : side);
            int pitch = (int) user.getXRot();
            if (pitch > 60) {
                property = DIRECTIONS.get(Direction.DOWN);
            } else if (pitch < -60) {
                property = DIRECTIONS.get(Direction.UP);
            }
            level.setBlock(pos, state.setValue(property, !state.getValue(property)), Block.UPDATE_ALL, 0);
            level.playSound(null, pos, ModSoundEvents.WRENCH.get(), SoundSource.BLOCKS, 1, 1);
        }
    }
}