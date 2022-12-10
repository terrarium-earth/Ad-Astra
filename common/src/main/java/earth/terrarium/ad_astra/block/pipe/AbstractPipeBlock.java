package earth.terrarium.ad_astra.block.pipe;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

import earth.terrarium.ad_astra.registry.ModTags;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public abstract class AbstractPipeBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, Wrenchable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected final long transferRate;
    protected final int decay;
    protected double size;

    public AbstractPipeBlock(long transferRate, int decay, double size, Properties settings) {
        super(settings);
        this.transferRate = transferRate;
        this.decay = decay;
        this.size = size;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack item = player.getItemInHand(hand);

        if (item.is(ModTags.WRENCHES)) {
            this.handleWrench(level, pos, state, hit.getDirection(), player, hit.getLocation());
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof InteractablePipe<?> pipe) {
                pipe.pipeTick();
            }
        };
    }

    public long getTransferRate() {
        return this.transferRate;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType().equals(Fluids.WATER));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // Extend the pipe when something around it is updated.
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        this.updateShape((Level) level, pos, state, direction);

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    // Extend the pipe when it is first placed.
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);
        this.updateShape(level, pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.updateOutlineShape(state);
    }

    public void updateShape(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide) {
            for (Direction direction : Direction.values()) {
                this.updateShape(level, pos, state, direction);
            }
        }
    }

    public abstract void updateShape(Level level, BlockPos pos, BlockState state, Direction direction);

    // Expand the voxel shape to match the model.
    public abstract VoxelShape updateOutlineShape(BlockState state);

    public static Optional<Direction> getDirectionByVec(Vec3 hit, BlockPos pos) {
        var relativePos = hit.add(-pos.getX(), -pos.getY(), -pos.getZ());
        if (relativePos.x < (2f / 16f)) return Optional.of(Direction.WEST);
        else if (relativePos.x > (14f / 16f)) return Optional.of(Direction.EAST);
        else if (relativePos.z < (2f / 16f)) return Optional.of(Direction.NORTH);
        else if (relativePos.z > (14f / 16f)) return Optional.of(Direction.SOUTH);
        else if (relativePos.y < (2f / 16f)) return Optional.of(Direction.DOWN);
        else if (relativePos.y > (14f / 16f)) return Optional.of(Direction.UP);
        return Optional.empty();
    }
}