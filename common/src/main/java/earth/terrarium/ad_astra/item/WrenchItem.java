package earth.terrarium.ad_astra.item;

import earth.terrarium.ad_astra.block.pipe.Wrenchable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class WrenchItem extends Item {

    public WrenchItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.getBlockState(context.getClickedPos()).getBlock() instanceof Wrenchable block) {
            block.handleWrench(level, context.getClickedPos(), level.getBlockState(context.getClickedPos()), context.getClickedFace(), context.getPlayer(), context.getClickLocation());
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

}
