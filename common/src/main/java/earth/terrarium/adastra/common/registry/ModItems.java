package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.OxygenDistributorBlockEntity;
import earth.terrarium.adastra.common.items.base.CustomGeoBlockItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, AdAstra.MOD_ID);
    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(AdAstra.MOD_ID, "main"))
        .setItemIcon(() -> Items.DIRT)
        .addRegistry(ITEMS)
        .build();

    public static final ResourcefulRegistry<Item> MACHINES = ResourcefulRegistries.create(ITEMS);

    public static final RegistryEntry<Item> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new CustomGeoBlockItem(
        ModBlocks.OXYGEN_DISTRIBUTOR.get(),
        new Item.Properties(),
        OxygenDistributorBlockEntity.SPIN));

    public static final RegistryEntry<Item> BATTERY = MACHINES.register("battery", () -> new BlockItem(ModBlocks.BATTERY.get(), new Item.Properties()));
}
