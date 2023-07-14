package earth.terrarium.ad_astra.common.block.door;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public class SlidingDoorBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.create("location", LocationState.class);

    protected static final VoxelShape X_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape Z_SHAPE = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    protected static final VoxelShape GIANT_X_SHAPE = Block.box(-16, -16, 0.0, 32.0, 32.0, 3.0);
    protected static final VoxelShape GIANT_Z_SHAPE = Block.box(0.0, -16, -16, 3.0, 32.0, 32.0);

    public SlidingDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OPEN, false).setValue(POWERED, false).setValue(LOCATION, LocationState.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, POWERED, LOCATION);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!level.isClientSide) {
            Direction direction = state.getValue(FACING);
            Direction offset = direction.getClockWise();

            // Bottom
            level.setBlock(pos, defaultBlockState().setValue(LOCATION, LocationState.BOTTOM).setValue(FACING, direction), 3);
            // Bottom Left
            level.setBlock(pos.relative(offset), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_LEFT).setValue(FACING, direction), 3);
            // Bottom Right
            level.setBlock(pos.relative(offset.getOpposite()), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_RIGHT).setValue(FACING, direction), 3);
            // Center
            level.setBlock(pos.above(), defaultBlockState().setValue(LOCATION, LocationState.CENTER).setValue(FACING, direction), 3);
            // Left
            level.setBlock(pos.above().relative(offset), defaultBlockState().setValue(LOCATION, LocationState.LEFT).setValue(FACING, direction), 3);
            // Right
            level.setBlock(pos.above().relative(offset.getOpposite()), defaultBlockState().setValue(LOCATION, LocationState.RIGHT).setValue(FACING, direction), 3);
            // Top
            level.setBlock(pos.above().above(), defaultBlockState().setValue(LOCATION, LocationState.TOP).setValue(FACING, direction), 3);
            // Top Left
            level.setBlock(pos.above().above().relative(offset), defaultBlockState().setValue(LOCATION, LocationState.TOP_LEFT).setValue(FACING, direction), 3);
            // Top Right
            level.setBlock(pos.above().above().relative(offset.getOpposite()), defaultBlockState().setValue(LOCATION, LocationState.TOP_RIGHT).setValue(FACING, direction), 3);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(level, pos, state, player);
        breakDoor(level, level.getBlockState(pos), pos, !player.isCreative());
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        for (Direction direction : Direction.values()) {
            BlockPos offset = pos.relative(direction);
            BlockState state = level.getBlockState(offset);
            if (state.getBlock().equals(this)) {
                breakDoor(level, state, offset, true);
                break;
            }
        }
        super.wasExploded(level, pos, explosion);
    }

    public void breakDoor(Level level, BlockState state, BlockPos pos, boolean drop) {
        if (!level.isClientSide && state.getBlock().equals(this)) {
            BlockPos mainPos = this.getMainPos(state, pos);
            if (level.getBlockState(mainPos).getBlock().equals(this)) {
                Direction direction = state.getValue(FACING).getCounterClockWise();

                // Bottom
                level.destroyBlock(mainPos, drop);
                // Bottom Left
                level.destroyBlock(mainPos.relative(direction), false);
                // Bottom Right
                level.destroyBlock(mainPos.relative(direction.getOpposite()), false);
                // Center
                level.destroyBlock(mainPos.above(), false);
                // Left
                level.destroyBlock(mainPos.above().relative(direction), false);
                // Right
                level.destroyBlock(mainPos.above().relative(direction.getOpposite()), false);
                // Top
                level.destroyBlock(mainPos.above().above(), false);
                // Top Left
                level.destroyBlock(mainPos.above().above().relative(direction), false);
                // Top Right
                level.destroyBlock(mainPos.above().above().relative(direction.getOpposite()), false);
            }
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return switch (type) {
            case LAND -> state.getValue(OPEN);
            case WATER -> false;
            case AIR -> state.getValue(OPEN);
        };
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockPos main = this.getMainPos(state, pos);
            if (level.getBlockEntity(this.getMainPos(state, pos)) instanceof SlidingDoorBlockEntity entity) {
                if (entity.getSlideTicks() > 0 && entity.getSlideTicks() < 100) {
                    return InteractionResult.PASS;
                } else {
                    level.setBlock(main, level.getBlockState(main).cycle(OPEN), 10);
                }
            }

        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        LocationState location = state.getValue(LOCATION);
        return switch (location) {
            case TOP -> getFacingOutlineShape(state, -1);
            case CENTER -> getFacingOutlineShape(state, 0);
            case BOTTOM -> getFacingOutlineShape(state, 1);
            default -> switch (state.getValue(FACING)) {
                case NORTH -> X_SHAPE.move(0, 0, 0.42);
                case EAST -> Z_SHAPE.move(-0.38, 0, 0);
                case SOUTH -> X_SHAPE.move(0, 0, 0.38);
                case WEST -> Z_SHAPE.move(-0.42, 0, 0);
                default -> Shapes.empty();
            };
        };
    }

    private VoxelShape getFacingOutlineShape(BlockState state, double offset) {
        return switch (state.getValue(FACING)) {
            case NORTH -> GIANT_X_SHAPE.move(0, offset, 0.42);
            case EAST -> GIANT_Z_SHAPE.move(0.38, offset, 0);
            case SOUTH -> GIANT_X_SHAPE.move(0, offset, 0.38);
            case WEST -> GIANT_Z_SHAPE.move(0.42, offset, 0);
            default -> Shapes.empty();
        };
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos mainPos = this.getMainPos(state, pos);
        Direction direction = state.getValue(FACING).getClockWise();

        // Bottom
        return level.getBlockState(mainPos).isAir() &&
            // Bottom Left
            level.getBlockState(mainPos.relative(direction)).isAir() &&
            // Bottom Right
            level.getBlockState(mainPos.relative(direction.getOpposite())).isAir() &&
            // Center
            level.getBlockState(mainPos.above()).isAir() &&
            // Left
            level.getBlockState(mainPos.above().relative(direction)).isAir() &&
            // Right
            level.getBlockState(mainPos.above().relative(direction.getOpposite())).isAir() &&
            // Top
            level.getBlockState(mainPos.above().above()).isAir() &&
            // Top Left
            level.getBlockState(mainPos.above().above().relative(direction)).isAir() &&
            // Top Right
            level.getBlockState(mainPos.above().above().relative(direction.getOpposite())).isAir();

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(POWERED, ctx.getLevel().hasNeighborSignal(ctx.getClickedPos())).setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborChanged(state, level, pos, block, fromPos, notify);

        if (!level.isClientSide) {
            level.setBlockAndUpdate(pos, state.setValue(POWERED, level.hasNeighborSignal(pos)));
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockState main = level.getBlockState(this.getMainPos(state, pos));
        if (main.hasProperty(OPEN) && (!main.getValue(OPEN) && !main.getValue(POWERED))) {
            Direction direction = state.getValue(FACING);
            return switch (direction) {
                case NORTH -> X_SHAPE.move(0, 0, 0.42);
                case EAST -> Z_SHAPE.move(-0.38, 0, 0);
                case SOUTH -> X_SHAPE.move(0, 0, 0.38);
                case WEST -> Z_SHAPE.move(-0.42, 0, 0);
                default -> Shapes.empty();
            };
        } else {
            return Shapes.empty();
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(LOCATION).equals(LocationState.BOTTOM)) {
            return new SlidingDoorBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (!(state.getValue(SlidingDoorBlock.LOCATION) == LocationState.BOTTOM)) return null;
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof SlidingDoorBlockEntity door) {
                door.tick();
            }
        };
    }

    public BlockPos getMainPos(BlockState state, BlockPos from) {
        BlockPos target = from;
        Direction facing = state.getValue(FACING).getCounterClockWise();
        if (state.getValue(LOCATION).equals(LocationState.TOP_LEFT)) {
            target = from.below().below().relative(facing);
        } else if (state.getValue(LOCATION).equals(LocationState.TOP)) {
            target = from.below().below();
        } else if (state.getValue(LOCATION).equals(LocationState.TOP_RIGHT)) {
            target = from.below().below().relative(facing.getOpposite());
        } else if (state.getValue(LOCATION).equals(LocationState.RIGHT)) {
            target = from.below().relative(facing.getOpposite());
        } else if (state.getValue(LOCATION).equals(LocationState.CENTER)) {
            target = from.below();
        } else if (state.getValue(LOCATION).equals(LocationState.LEFT)) {
            target = from.below().relative(facing);
        } else if (state.getValue(LOCATION).equals(LocationState.BOTTOM_LEFT)) {
            target = from.relative(facing);
        } else if (state.getValue(LOCATION).equals(LocationState.BOTTOM_RIGHT)) {
            target = from.relative(facing.getOpposite());
        }
        return target;
    }
}
