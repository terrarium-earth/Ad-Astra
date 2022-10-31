package earth.terrarium.ad_astra.items;

import earth.terrarium.ad_astra.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class SolarPanelBlockItem extends MachineBlockItem {

    public SolarPanelBlockItem(Block block, Properties settings) {
        super(block, settings);
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

}
