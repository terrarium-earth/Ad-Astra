package earth.terrarium.ad_astra.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        tooltip.add((Component.translatable("item.ad_astra.flag.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA))));
    }
}
