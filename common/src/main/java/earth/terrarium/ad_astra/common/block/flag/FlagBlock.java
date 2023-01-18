package earth.terrarium.ad_astra.common.block.flag;

import earth.terrarium.ad_astra.client.screen.FlagUrlScreen;
import earth.terrarium.ad_astra.common.block.BasicEntityBlock;
import earth.terrarium.ad_astra.common.util.LangUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FlagBlock extends BasicEntityBlock {
    public static final VoxelShape POLE = Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625);
    public static final VoxelShape BASE = Shapes.or(Shapes.box(0.375, 0, 0.375, 0.625, 0.5, 0.625), POLE);
    public static final EightDirectionProperty FACING = new EightDirectionProperty();
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FlagBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide && (player.canUseGameMasterBlocks())) {
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
                player.displayClientMessage(Component.translatable(LangUtils.NOT_FLAG_OWNER), true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);
        boolean waterAbove = level.getFluidState(pos.above()).is(Fluids.WATER);
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, waterAbove), Block.UPDATE_ALL);

        if (placer instanceof Player player && level.getBlockEntity(pos.above()) instanceof FlagBlockEntity flagEntity) {
            flagEntity.setOwner(player.getGameProfile());
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        if (!level.isClientSide) {
            if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.LOWER) && level.getBlockState(pos.above()).isAir()) {
                level.destroyBlock(pos, true);
            } else if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.UPPER) && level.getBlockState(pos.below()).isAir()) {
                level.destroyBlock(pos, true);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? BASE : POLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF);
    }

    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportRigidBlock(level, pos.below()) && level.getBlockState(pos.above()).isAir();
    }

    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
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
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        var value = EightDirectionProperty.Direction.VALUES[Mth.floor((double) (context.getRotation() * 8.0F / 360.0F) + 0.5D) & 7];
        return this.defaultBlockState().setValue(FACING, value).setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
