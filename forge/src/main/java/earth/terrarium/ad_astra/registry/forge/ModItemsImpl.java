package earth.terrarium.ad_astra.registry.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static Supplier<SpawnEggItem> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties settings) {
        return () -> new ForgeSpawnEggItem(type, primaryColor, secondaryColor, settings);
    }
}
