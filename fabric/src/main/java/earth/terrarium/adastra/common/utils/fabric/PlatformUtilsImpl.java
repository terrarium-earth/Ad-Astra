package earth.terrarium.adastra.common.utils.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.portal.PortalInfo;

import java.util.function.Supplier;

public class PlatformUtilsImpl {
    public static Entity teleportToDimension(Entity entity, ServerLevel level, PortalInfo portalInfo) {
        return FabricDimensions.teleport(entity, level, portalInfo);
    }

    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new SpawnEggItem(type.get(), primaryColor, secondaryColor, properties);
    }
}
