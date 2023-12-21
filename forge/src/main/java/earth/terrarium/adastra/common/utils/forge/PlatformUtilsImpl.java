package earth.terrarium.adastra.common.utils.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class PlatformUtilsImpl {
    public static Entity teleportToDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        return entity.changeDimension(level, new AdAstraTeleporter(portalInfo));
    }

    private record AdAstraTeleporter(PortalInfo target) implements ITeleporter {

        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return target;
        }

        @Override
        public boolean isVanilla() {
            return false;
        }

        @Override
        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
            return false;
        }

        @Override
        public Entity placeEntity(Entity entity, ServerLevel currentLevel, ServerLevel destLevel, float yaw, Function<Boolean, Entity> repositionEntity) {
            return repositionEntity.apply(false);
        }
    }
}
