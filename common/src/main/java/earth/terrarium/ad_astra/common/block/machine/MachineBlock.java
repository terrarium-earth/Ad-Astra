package earth.terrarium.ad_astra.common.block.machine;

import com.teamresourceful.resourcefullib.common.caches.CacheableFunction;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import earth.terrarium.botarium.common.menu.MenuHooks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
public class MachineBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final CacheableFunction<Block, BlockEntityType<?>> BLOCK_TO_ENTITY = new CacheableFunction<>(block ->
            ModBlockEntityTypes.BLOCK_ENTITY_TYPES
                    .getEntries()
                    .stream()
                    .map(RegistryEntry::get)
                    .filter(type -> type.isValid(block.defaultBlockState()))
                    .findFirst()
                    .orElse(null)
    );

    private BlockEntityType<?> entity;

    public MachineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(LIT, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof ExtraDataMenuProvider provider) {
                MenuHooks.openMenu((ServerPlayer) player, provider);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (entityWorld, pos, entityState, blockEntity) -> {
            if (blockEntity instanceof MachineBlockEntity machine) {
                if (level.isClientSide()) {
                    machine.clientTick();
                } else {
                    machine.serverTick();
                }
            }
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (this.entity == null) {
            this.entity = BLOCK_TO_ENTITY.apply(state.getBlock());
        }
        return this.entity.create(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LIT);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof BasicContainer container) {
                if (container.getContainerSize() > 0) {
                    Containers.dropContents(level, pos, container);
                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = this.defaultBlockState().setValue(POWERED, false);
        return state.setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
