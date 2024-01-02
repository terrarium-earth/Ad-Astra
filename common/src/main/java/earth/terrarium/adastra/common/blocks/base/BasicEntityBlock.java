package earth.terrarium.adastra.common.blocks.base;

import com.teamresourceful.resourcefullib.common.caches.CacheableFunction;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.common.blockentities.base.TickableBlockEntity;
import earth.terrarium.adastra.common.registry.ModBlockEntityTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public abstract class BasicEntityBlock extends BaseEntityBlock {
    private static final CacheableFunction<Block, BlockEntityType<?>> BLOCK_TO_ENTITY = new CacheableFunction<>(block ->
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES
            .stream()
            .map(RegistryEntry::get)
            .filter(type -> type.isValid(block.defaultBlockState()))
            .findFirst()
            .orElse(null)
    );
    private BlockEntityType<?> entity;
    private final boolean shouldTick;

    public BasicEntityBlock(Properties properties, boolean shouldTick) {
        super(properties);
        this.shouldTick = shouldTick;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return entity(state).create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public BlockEntityType<?> entity(BlockState state) {
        if (entity == null) entity = BLOCK_TO_ENTITY.apply(state.getBlock());
        return this.entity;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return !shouldTick ? null : (entityLevel, pos, blockState, blockEntity) -> {
            if (blockEntity instanceof TickableBlockEntity tickable) {
                long time = level.getGameTime() - pos.hashCode();
                tickable.tick(entityLevel, time, blockState, pos);
                if (level.isClientSide()) {
                    tickable.clientTick((ClientLevel) level, time, state, pos);
                } else {
                    tickable.serverTick((ServerLevel) level, time, state, pos);
                    tickable.internalServerTick((ServerLevel) level, time, state, pos);
                }
                if (!tickable.isInitialized()) tickable.firstTick(level, pos, state);
            }
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof TickableBlockEntity tickable) {
                tickable.onRemoved();
            }
            super.onRemove(state, level, pos, newState, moved);
        }
    }

    public static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) != DoubleBlockHalf.UPPER) return;
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.is(state.getBlock()) && belowState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(belowPos, blockState2, 35);
            level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, belowPos, Block.getId(belowState));
        }
    }
}
