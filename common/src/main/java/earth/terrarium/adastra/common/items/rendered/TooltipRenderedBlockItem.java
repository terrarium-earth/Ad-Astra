package earth.terrarium.adastra.common.items.rendered;

import earth.terrarium.adastra.common.items.TooltipBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;

public class TooltipRenderedBlockItem extends TooltipBlockItem {
    public TooltipRenderedBlockItem(Block block, Component description, Properties properties) {
        super(block, description, properties);
    }
}
