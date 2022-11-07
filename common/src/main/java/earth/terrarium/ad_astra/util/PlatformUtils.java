package earth.terrarium.ad_astra.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.portal.PortalInfo;
import org.apache.commons.lang3.NotImplementedException;

public class PlatformUtils {

    @ExpectPlatform
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void registerStrippedLog(Block log, Block strippedLog) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static int getBurnTime(ItemStack stack) {
        throw new NotImplementedException();
    }
}
