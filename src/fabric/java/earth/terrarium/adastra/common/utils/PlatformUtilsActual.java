package earth.terrarium.adastra.common.utils;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.portal.DimensionTransition;
import net.msrandom.multiplatform.annotations.Actual;

import java.util.function.Supplier;

public class PlatformUtilsActual {

    @Actual
    public static Entity teleportToDimension(Entity entity, ServerLevel level, DimensionTransition transition) {
        return entity.changeDimension(transition);
    }

    @Actual
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return EggUtil.createEgg(type, primaryColor, secondaryColor, properties);
    }

    @Actual
    public static int getBurnTime(ItemStack burnable) {
        Integer burnTime = FuelRegistry.INSTANCE.get(burnable.getItem());
        return burnTime == null ? 0 : burnTime;
    }
}
