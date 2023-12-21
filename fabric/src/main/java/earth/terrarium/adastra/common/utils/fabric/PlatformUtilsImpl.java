package earth.terrarium.adastra.common.utils.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;

public class PlatformUtilsImpl {
    public static Entity teleportToDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        return FabricDimensions.teleport(entity, level, portalInfo);
    }
}
