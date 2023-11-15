package earth.terrarium.ad_astra.common.block.launchpad;

import earth.terrarium.ad_astra.common.block.door.LocationState;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public class LaunchPad extends Block implements SimpleWaterloggedBlock {

    public static final VoxelShape SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 2, 16));
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<LocationState> LOCATION = EnumProperty.create("location", LocationState.class);

    public LaunchPad(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState()
            .setValue(LOCATION, LocationState.CENTER)
            .setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LOCATION, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (level.isClientSide) return;
        level.setBlock(pos.north(), defaultBlockState().setValue(LOCATION, LocationState.TOP), 3);
        level.setBlock(pos.east(), defaultBlockState().setValue(LOCATION, LocationState.RIGHT), 3);
        level.setBlock(pos.south(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM), 3);
        level.setBlock(pos.west(), defaultBlockState().setValue(LOCATION, LocationState.LEFT), 3);
        level.setBlock(pos.north().east(), defaultBlockState().setValue(LOCATION, LocationState.TOP_RIGHT), 3);
        level.setBlock(pos.north().west(), defaultBlockState().setValue(LOCATION, LocationState.TOP_LEFT), 3);
        level.setBlock(pos.south().east(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_RIGHT), 3);
        level.setBlock(pos.south().west(), defaultBlockState().setValue(LOCATION, LocationState.BOTTOM_LEFT), 3);
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
            getPositionsAround(mainPos).forEach(blockPos -> level.destroyBlock(blockPos, drop));
            level.destroyBlock(mainPos, drop);
        }
    }

    public List<BlockPos> getPositionsAround(BlockPos pos) {
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