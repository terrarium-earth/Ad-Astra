package earth.terrarium.adastra.common.utils.neoforge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.portal.DimensionTransition;
import net.msrandom.multiplatform.annotations.Actual;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

public class PlatformUtilsImpl {

    @Actual
    public static Entity teleportToDimension(Entity entity, ServerLevel level, DimensionTransition transition) {
        return entity.changeDimension(transition);
    }

    @Actual
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new DeferredSpawnEggItem(type, primaryColor, secondaryColor, properties);
    }
}
