package earth.terrarium.ad_astra.common.block.flag;


import earth.terrarium.ad_astra.client.screen.FlagUrlScreen;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public class FlagBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final EightDirectionProperty FACING = new EightDirectionProperty();
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FlagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide && (AdAstraConfig.allowFlagImages || player.canUseGameMasterBlocks())) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                return action(level, pos.above(), player);
            } else {
                return action(level, pos, player);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private InteractionResult action(Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof FlagBlockEntity flagBlock) {
            if (flagBlock.getOwner() != null && player.getUUID().equals(flagBlock.getOwner().getId())) {
                FlagUrlScreen.open(pos);
            } else {
                player.displayClientMessage(Component.translatable("message.ad_astra.flag.not_owner"), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape pole = Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625);
        if (state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            return Shapes.or(Shapes.box(0.375, 0, 0.375, 0.625, 0.5, 0.625), pole);
        }
        return pole;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborChanged(state, level, pos, block, fromPos, notify);
        if (!level.isClientSide) {
            if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.LOWER) && level.getBlockState(pos.above()).isAir()) {
                level.destroyBlock(pos, false);
            } else if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.UPPER) && level.getBlockState(pos.below()).isAir()) {
                level.destroyBlock(pos, false);
            }
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);

        boolean waterAbove = level.getFluidState(pos.above()).is(Fluids.WATER);
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, waterAbove), 3);

        BlockEntity blockEntity = level.getBlockEntity(pos.above());

        if (placer instanceof Player player && blockEntity instanceof FlagBlockEntity flagEntity) {
            flagEntity.setOwner(player.getGameProfile());
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FlagBlockEntity(pos, state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, state.getValue(FACING).rotate(rotation));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, state.getValue(FACING).mirror(mirror));
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF).equals(DoubleBlockHalf.LOWER) ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportRigidBlock(level, pos.below());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        var value = EightDirectionProperty.Direction.VALUES[Mth.floor((double) (ctx.getRotation() * 8.0F / 360.0F) + 0.5D) & 7];
        return this.defaultBlockState().setValue(FACING, value).setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER));
    }
}