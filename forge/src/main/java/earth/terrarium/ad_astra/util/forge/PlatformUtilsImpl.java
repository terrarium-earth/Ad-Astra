package earth.terrarium.ad_astra.util.forge;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.PortalInfo;

public class PlatformUtilsImpl {
    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return (T) entity.changeDimension(level, new AdAstraTeleporter(target));
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        AxeItemHooks.addStrippable(log, strippedLog);
    }
}
