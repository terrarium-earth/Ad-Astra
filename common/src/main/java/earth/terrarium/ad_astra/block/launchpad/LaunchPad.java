package earth.terrarium.ad_astra.block.launchpad;

import earth.terrarium.ad_astra.block.door.LocationState;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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

import java.util.List;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public class LaunchPad extends Block implements SimpleWaterloggedBlock {

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
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
            for (Direction direction : Direction.Plane.HORIZONTAL) {
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
            getBlockPosAround(mainPos).forEach(blockPos -> level.destroyBlock(blockPos, false));
            level.destroyBlock(mainPos, drop);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos mainPos = this.getMainPos(state, pos);
        if (level.getBlockState(pos.below()).getBlock() instanceof LaunchPad) return false;
        return canReplace(level, mainPos) && getBlockPosAround(mainPos).stream().allMatch(blockPos -> canReplace(level, blockPos));
    }

    private boolean canReplace(BlockGetter world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isReplaceable();
    }

    public List<BlockPos> getBlockPosAround(BlockPos pos) {
        return List.of(pos, pos.north(), pos.east(), pos.south(), pos.west(), pos.north().east(), pos.north().west(), pos.south().east(), pos.south().west());
    }

    public BlockPos getMainPos(BlockState state, BlockPos from) {
        return switch (state.getValue(LOCATION)) {
            case TOP_LEFT -> from.south().east();
            case TOP -> from.south();
            case TOP_RIGHT -> from.south().west();
            case LEFT -> from.east();
            case CENTER -> from;
            case RIGHT -> from.west();
            case BOTTOM_LEFT -> from.north().east();
            case BOTTOM -> from.north();
            case BOTTOM_RIGHT -> from.north().west();
        };
    }
}