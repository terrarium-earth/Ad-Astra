package earth.terrarium.ad_astra.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineBlockItem extends BlockItem {
    private final String[] tooltips;

    public MachineBlockItem(Block block, Properties properties, String... tooltips) {
        super(block, properties);
        this.tooltips = tooltips;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        if (Screen.hasShiftDown()) {
            for (String text : tooltips) {
                tooltip.add((Component.translatable(text).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
            }
        } else {
            tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
        }
    }
}
