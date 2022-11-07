package earth.terrarium.ad_astra.util.forge;

import earth.terrarium.ad_astra.mixin.forge.AxeItemAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.ForgeHooks;

import java.util.HashMap;
import java.util.Map;

public class PlatformUtilsImpl {
    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return (T) entity.changeDimension(level, new AdAstraTeleporter(target));
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        Map<Block, Block> strippedBlocks = new HashMap<>(AxeItemAccessor.adastra_getStrippables());
        strippedBlocks.put(log, strippedLog);
        AxeItemAccessor.adastra_setStrippables(strippedBlocks);
    }

    public static int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }
}
