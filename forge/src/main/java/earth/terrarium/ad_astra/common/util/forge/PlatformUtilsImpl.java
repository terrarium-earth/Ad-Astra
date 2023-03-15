package earth.terrarium.ad_astra.common.util.forge;

import earth.terrarium.ad_astra.mixin.forge.AxeItemAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PlatformUtilsImpl {

    @SuppressWarnings("unchecked")
    public static <T extends Entity> T teleportToDimension(T entity, ServerLevel level, PortalInfo target) {
        return (T) entity.changeDimension(level, new AdAstraTeleporter(target));
    }

    public static void registerStrippedLog(Block log, Block strippedLog) {
        Map<Block, Block> strippedBlocks = new HashMap<>(AxeItemAccessor.ad_astra$getStrippables());
        strippedBlocks.put(log, strippedLog);
        AxeItemAccessor.ad_astra$setStrippables(strippedBlocks);
    }

    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, properties);
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
    }
}
