package earth.terrarium.ad_astra.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

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
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        throw new NotImplementedException();
    }
}
