package earth.terrarium.adastra.common.items;

import earth.terrarium.adastra.common.utils.TooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipBlockItem extends BlockItem {
    private final Component description;

    public TooltipBlockItem(Block block, Component description, Properties properties) {
        super(block, properties);
        this.description = description;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        TooltipUtils.addDescriptionComponent(tooltipComponents, description);
    }
}
