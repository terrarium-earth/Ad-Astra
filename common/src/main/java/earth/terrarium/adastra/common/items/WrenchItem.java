package earth.terrarium.adastra.common.items;

import earth.terrarium.adastra.common.blocks.base.Wrenchable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WrenchItem extends Item {
    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.getBlockState(context.getClickedPos()).getBlock() instanceof Wrenchable block) {
            block.onWrench(level, context.getClickedPos(), level.getBlockState(context.getClickedPos()), context.getClickedFace(), context.getPlayer(), context.getClickLocation());
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
