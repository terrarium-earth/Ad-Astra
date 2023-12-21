package earth.terrarium.adastra.common.blocks;

import earth.terrarium.adastra.client.screens.blocks.FlagUrlScreen;
import earth.terrarium.adastra.common.blockentities.FlagBlockEntity;
import earth.terrarium.adastra.common.blocks.base.BasicEntityBlock;
import earth.terrarium.adastra.common.blocks.base.DoubleMachineBlock;
import earth.terrarium.adastra.common.blocks.properties.EightDirectionProperty;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FlagBlock extends BasicEntityBlock implements SimpleWaterloggedBlock {
    public static final EightDirectionProperty FACING = new EightDirectionProperty();
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE_BOTTOM = Shapes.join(
        Block.box(7, 8, 7, 9, 16, 9),
        Block.box(6, 0, 6, 10, 8, 10), BooleanOp.OR);

    private static final VoxelShape SHAPE_TOP = Block.box(7, 0, 7, 9, 24, 9);

    public FlagBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(HALF, DoubleBlockHalf.LOWER)
            .setValue(FACING, EightDirectionProperty.Direction.NORTH)
            .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, WATERLOGGED, HALF);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide() && (AdAstraConfig.allowFlagImages || player.canUseGameMasterBlocks())) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                return action(level, pos.above(), player);
            } else return action(level, pos, player);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private InteractionResult action(Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof FlagBlockEntity entity) {
            if (entity.getOwner() != null && player.getUUID().equals(entity.getOwner().getId())) {
                FlagUrlScreen.open(pos);
            } else {
                player.displayClientMessage(ConstantComponents.NOT_THE_OWNER, true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportRigidBlock(level, pos.below()) && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && player.isCreative()) {
            DoubleMachineBlock.preventCreativeDropFromBottomPart(level, pos, state, player);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? SHAPE_BOTTOM : SHAPE_TOP;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE; // Rendering is done in the BER
    }

    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && half == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(this) && neighborState.getValue(HALF) != half ? state.setValue(FACING, neighborState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return half == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context)) {
            var direction = EightDirectionProperty.Direction.VALUES[Mth.floor((double) (context.getRotation() * 8.0F / 360.0F) + 0.5D) & 7];
            return this.defaultBlockState().setValue(FACING, direction);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        level.setBlockAndUpdate(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER));
        if (placer instanceof Player player && level.getBlockEntity(pos.above()) instanceof FlagBlockEntity flagEntity) {
            flagEntity.setOwner(player.getGameProfile());
        }
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }
}
