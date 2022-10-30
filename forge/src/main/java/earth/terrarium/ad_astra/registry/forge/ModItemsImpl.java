package earth.terrarium.ad_astra.registry.forge;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static Supplier<SpawnEggItem> createSpawnEggItem(Supplier<? extends EntityType<? extends MobEntity>> type, int primaryColor, int secondaryColor, Item.Settings settings) {
        return () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, settings);
    }

    public static Supplier<Item> createBucketItem(Supplier<? extends Fluid> fluid, Item.Settings settings) {
        return () -> new BucketItem(fluid, settings);
    }
}
