package earth.terrarium.adastra.common.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import org.apache.commons.lang3.NotImplementedException;

public class PlatformUtils {
    @ExpectPlatform
    public static Entity teleportToDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        throw new NotImplementedException();
    }
}
