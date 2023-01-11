package earth.terrarium.ad_astra.common.util.fabric;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;

import java.util.function.Supplier;

public class PlatformUtilsImpl {

    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return FabricDimensions.teleport(entity, level, target);
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        StrippableBlockRegistry.register(log, strippedLog);
    }

    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new SpawnEggItem(type.get(), primaryColor, secondaryColor, properties);
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
    }
}
