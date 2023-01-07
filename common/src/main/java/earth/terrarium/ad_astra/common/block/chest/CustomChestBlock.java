package earth.terrarium.ad_astra.common.block.chest;

import earth.terrarium.ad_astra.common.registry.ModBlockEntityTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiPredicate;

@MethodsReturnNonnullByDefault
public class CustomChestBlock extends ChestBlock {
    public CustomChestBlock(Properties properties) {
        super(properties, null);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomChestBlockEntity(pos, state);
    }

    @Override
    public BlockEntityType<? extends ChestBlockEntity> blockEntityType() {
        return ModBlockEntityTypes.CHEST.get();
    }

    @Override
    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState state, Level level, BlockPos pos, boolean override) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate;
        if (override) {
            biPredicate = (levelAccessor, blockPos) -> false;
        } else {
            biPredicate = ChestBlock::isChestBlockedAt;
        }

        return DoubleBlockCombiner.combineWithNeigbour(ModBlockEntityTypes.CHEST.get(), ChestBlock::getBlockType, ChestBlock::getConnectedDirection, FACING, state, level, pos, biPredicate);
    }
}
