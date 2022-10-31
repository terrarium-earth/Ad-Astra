package earth.terrarium.ad_astra.blocks.launchpad;

import earth.terrarium.ad_astra.blocks.door.LocationState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class LaunchPad extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.create("location", LocationState.class);

    public LaunchPad(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(LOCATION, LocationState.CENTER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return Shapes.or(Block.box(0, 0, 0, 16, 2, 16)).move(offset.x(), offset.y(), offset.z());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, LOCATION);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!level.isClientSide) {
            level.setBlock(pos.north(), defaultBlockState().setValue(LOCATION, LocationState.TOP), 3);
            level.setBlock(pos.east(), defaultBlockState().setValue(LOCATION, LocationState.RIGHT), 3);
            level.setBlock(pos.south(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM), 3);
            level.setBlock(pos.west(), defaultBlockState().setValue(LOCATION, LocationState.LEFT), 3);
            level.setBlock(pos.north().east(), defaultBlockState().setValue(LOCATION, LocationState.TOP_RIGHT), 3);
            level.setBlock(pos.north().west(), defaultBlockState().setValue(LOCATION, LocationState.TOP_LEFT), 3);
            level.setBlock(pos.south().east(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_RIGHT), 3);
            level.setBlock(pos.south().west(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_LEFT), 3);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(level, pos, state, player);
        breakPad(level, level.getBlockState(pos), pos, !player.isCreative());
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        if (!level.isClientSide) {
            for (Direction direction : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
                BlockPos offset = pos.relative(direction);
                BlockState state = level.getBlockState(offset);
                if (state.getBlock().equals(this)) {
                    breakPad(level, state, offset, true);
                    break;
                }
            }
        }
        super.wasExploded(level, pos, explosion);
    }

    public void breakPad(Level level, BlockState state, BlockPos pos, boolean drop) {
        if (!level.isClientSide && state.getBlock().equals(this)) {
            BlockPos mainPos = this.getMainPos(state, pos);

            // Bottom
            level.destroyBlock(mainPos, drop);
            // Bottom Left
            level.destroyBlock(mainPos.north(), false);
            // Bottom Right
            level.destroyBlock(mainPos.east(), false);
            // Center
            level.destroyBlock(mainPos.south(), false);
            // Left
            level.destroyBlock(mainPos.west(), false);
            // Right
            level.destroyBlock(mainPos.north().east(), false);
            // Top
            level.destroyBlock(mainPos.north().west(), false);
            // Top Left
            level.destroyBlock(mainPos.south().east(), false);
            // Top Right
            level.destroyBlock(mainPos.south().west(), false);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos mainPos = this.getMainPos(state, pos);

        return !(level.getBlockState(pos.below()).getBlock() instanceof LaunchPad) && level.getBlockState(mainPos).isAir() &&
                //
                level.getBlockState(mainPos.north()).isAir() &&
                //
                level.getBlockState(mainPos.east()).isAir() &&
                //
                level.getBlockState(mainPos.south()).isAir() &&
                //
                level.getBlockState(mainPos.west()).isAir() &&
                //
                level.getBlockState(mainPos.north().east()).isAir() &&
                //
                level.getBlockState(mainPos.north().west()).isAir() &&
                //
                level.getBlockState(mainPos.south().east()).isAir() &&
                //
                level.getBlockState(mainPos.south().west()).isAir();

    }

    public BlockPos getMainPos(BlockState state, BlockPos from) {
        BlockPos target = from;

        switch (state.getValue(LOCATION)) {
            case TOP_LEFT -> target = from.south().east();
            case TOP -> target = from.south();
            case TOP_RIGHT -> target = from.south().west();
            case RIGHT -> target = from.west();
            case CENTER -> target = from;
            case LEFT -> target = from.east();
            case BOTTOM -> target = from.north();
            case BOTTOM_LEFT -> target = from.north().east();
            case BOTTOM_RIGHT -> target = from.north().west();
        }
        return target;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof LaunchPadBlockEntity pad) {
                pad.tick();
            }
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(LOCATION).equals(LocationState.CENTER)) {
            return new LaunchPadBlockEntity(pos, state);
        }
        return null;
    }
}