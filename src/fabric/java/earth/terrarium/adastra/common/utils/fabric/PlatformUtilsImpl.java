package earth.terrarium.adastra.common.utils.fabric;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.portal.DimensionTransition;

import java.util.function.Supplier;

public class PlatformUtilsImpl {

    public static Entity teleportToDimension(Entity entity, ServerLevel level, DimensionTransition portalInfo) {
        return entity.changeDimension(portalInfo);
    }

    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new SpawnEggItem(type.get(), primaryColor, secondaryColor, properties);
    }
}
