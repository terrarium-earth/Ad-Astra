package earth.terrarium.adastra.common.utils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.portal.DimensionTransition;
import net.msrandom.multiplatform.annotations.Expect;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.Supplier;

public class PlatformUtils {

    @Expect
    public static Entity teleportToDimension(Entity entity, ServerLevel level, DimensionTransition transition);

    @Expect
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties);

    @Expect
    public static int getBurnTime(ItemStack burnable);
}
