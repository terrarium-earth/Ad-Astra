package earth.terrarium.ad_astra.common.item;

import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class SolarPanelBlockItem extends MachineBlockItem {

    public SolarPanelBlockItem(Block block, Properties properties, String... tooltips) {
        super(block, properties, tooltips);
    }

    // Prevents the player from placing solar panels directly adjacent to other solar panels.
    @Override
    public InteractionResult place(BlockPlaceContext context) {
        for (Direction dir : Direction.values()) {
            if (context.getLevel().getBlockState(context.getClickedPos().relative(dir)).is(ModBlocks.SOLAR_PANEL.get())) {
                return InteractionResult.PASS;
            }
        }
        return super.place(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
        tooltip.add((Component.translatable("item.ad_astra.generator_energy.tooltip", SolarPanelBlockEntity.getEnergyForDimension(level)).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE))));
        super.appendHoverText(stack, level, tooltip, context);
    }
}
