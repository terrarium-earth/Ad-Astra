package earth.terrarium.adastra.common.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.portal.PortalInfo;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class PlatformUtils {
    @ExpectPlatform
    public static Entity teleportToDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        throw new NotImplementedException();
    }
}
