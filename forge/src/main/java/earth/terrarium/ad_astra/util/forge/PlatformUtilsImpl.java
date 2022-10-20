package earth.terrarium.ad_astra.util.forge;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.TeleportTarget;

public class PlatformUtilsImpl {
    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerWorld world, TeleportTarget target) {
        return (T)entity.changeDimension(world, new AdAstraTeleporter(target));
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        AxeItemHooks.addStrippable(log, strippedLog);
    }
}
