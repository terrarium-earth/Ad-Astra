package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.*;
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

    public static final RegistryEntry<Item> ETRIONIC_SOLAR_PANEL = MACHINES.register("etreonic_solar_panel", () -> new CustomGeoBlockItem(
        ModBlocks.ETRIONIC_SOLAR_PANEL.get(),
        new Item.Properties(),
        SolarPanelBlockEntity.CLOSE));

    public static final RegistryEntry<Item> VESNIUM_SOLAR_PANEL = MACHINES.register("vesnium_solar_panel", () -> new CustomGeoBlockItem(
        ModBlocks.VESNIUM_SOLAR_PANEL.get(),
        new Item.Properties(),
        SolarPanelBlockEntity.CLOSE));

    public static final RegistryEntry<Item> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new CustomGeoBlockItem(
        ModBlocks.HYDRAULIC_PRESS.get(),
        new Item.Properties(),
        HydraulicPressBlockEntity.BONK));

    public static final RegistryEntry<Item> STEAM_GENERATOR = MACHINES.register("steam_generator", () -> new CustomGeoBlockItem(
        ModBlocks.STEAM_GENERATOR.get(),
        new Item.Properties(),
        SteamGeneratorBlockEntity.IDLE));

    public static final RegistryEntry<Item> EMITTER = MACHINES.register("emitter", () -> new CustomGeoBlockItem(
        ModBlocks.EMITTER.get(),
        new Item.Properties(),
        EmitterBlockEntity.IDLE));

    public static final RegistryEntry<Item> RECEIVER = MACHINES.register("receiver", () -> new CustomGeoBlockItem(
        ModBlocks.RECEIVER.get(),
        new Item.Properties(),
        ReceiverBlockEntity.IDLE));

    public static final RegistryEntry<Item> BATTERY = MACHINES.register("battery", () -> new BlockItem(ModBlocks.BATTERY.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIONIC_BLAST_FURNACE = MACHINES.register("etreonic_blast_furnace", () -> new BlockItem(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> TI_69 = MACHINES.register("ti_69", () -> new Item(new Item.Properties()));
}
