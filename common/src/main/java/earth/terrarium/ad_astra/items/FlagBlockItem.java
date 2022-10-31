package earth.terrarium.ad_astra.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FlagBlockItem extends ModRenderedBlockItem {
    public FlagBlockItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos().above();
        BlockState blockState = level.isWaterAt(blockPos) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        level.setBlock(blockPos, blockState, 27);
        return super.placeBlock(context, state);
    }
}
