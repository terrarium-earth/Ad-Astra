package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.item.EtrionicCapacitorItem;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Item> MACHINES = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SOLAR_PANELS = ResourcefulRegistries.create(MACHINES);
    public static final ResourcefulRegistry<Item> BUCKETS = ResourcefulRegistries.create(ITEMS);

    public static final RegistryEntry<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> ETRIUM_CABLE = MACHINES.register("etrium_cable", () -> new BlockItem(ModBlocks.ETRIUM_CABLE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_FLUID_PIPE = MACHINES.register("desmium_fluid_pipe", () -> new BlockItem(ModBlocks.DESMIUM_FLUID_PIPE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_GENERATOR = MACHINES.register("etrionic_generator", () -> new BlockItem(ModBlocks.ETRIONIC_GENERATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> COMBUSTION_GENERATOR = MACHINES.register("combustion_generator", () -> new BlockItem(ModBlocks.COMBUSTION_GENERATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIONIC_SOLAR_PANEL = SOLAR_PANELS.register("etrionic_solar_panel", () -> new BlockItem(ModBlocks.ETRIONIC_SOLAR_PANEL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_SOLAR_PANEL = SOLAR_PANELS.register("vesnium_solar_panel", () -> new BlockItem(ModBlocks.VESNIUM_SOLAR_PANEL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GEOTHERMAL_GENERATOR = SOLAR_PANELS.register("geothermal_generator", () -> new BlockItem(ModBlocks.GEOTHERMAL_GENERATOR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_BLAST_FURNACE = MACHINES.register("etrionic_blast_furnace", () -> new BlockItem(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new BlockItem(ModBlocks.HYDRAULIC_PRESS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ELECTROLYZER = MACHINES.register("electrolyzer", () -> new BlockItem(ModBlocks.ELECTROLYZER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OXYGEN_SENSOR = MACHINES.register("oxygen_sensor", () -> new BlockItem(ModBlocks.OXYGEN_SENSOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OIL_REFINERY = MACHINES.register("oil_refinery", () -> new BlockItem(ModBlocks.OIL_REFINERY.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRYOGENIC_FREEZER = MACHINES.register("cryogenic_freezer", () -> new BlockItem(ModBlocks.CRYOGENIC_FREEZER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> RECYCLER = MACHINES.register("recycler", () -> new BlockItem(ModBlocks.RECYCLER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new BlockItem(ModBlocks.OXYGEN_DISTRIBUTOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> TEMPERATURE_REGULATOR = MACHINES.register("temperature_regulator", () -> new BlockItem(ModBlocks.TEMPERATURE_REGULATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> WATER_PUMP = MACHINES.register("water_pump", () -> new BlockItem(ModBlocks.WATER_PUMP.get(), new Item.Properties()));

    public static final RegistryEntry<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> IRON_ROD = ITEMS.register("iron_rod", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> ETRIUM_INGOT = ITEMS.register("etrium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_NUGGET = ITEMS.register("etrium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PLATE = ITEMS.register("etrium_plate", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> PHOTOVOLTAIC_ETRIUM_CELL = ITEMS.register("photovoltaic_etrium_cell", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> PHOTOVOLTAIC_VESNIUM_CELL = ITEMS.register("photovoltaic_vesnium_cell", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_CORE = ITEMS.register("etrionic_core", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIONIC_CAPACITOR = ITEMS.register("etrionic_capacitor", () -> new EtrionicCapacitorItem(new Item.Properties()));

    public static final RegistryEntry<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PLATE = ITEMS.register("steel_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_ROD = ITEMS.register("steel_rod", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> DESMIUM_INGOT = ITEMS.register("desmium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_NUGGET = ITEMS.register("desmium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PLATE = ITEMS.register("desmium_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> RAW_DESMIUM = ITEMS.register("raw_desmium", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> XEBRIUM_INGOT = ITEMS.register("xebrium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> XEBRIUM_NUGGET = ITEMS.register("xebrium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> XEBRIUM_PLATE = ITEMS.register("xebrium_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> RAW_XEBRIUM = ITEMS.register("raw_xebrium", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> VESNIUM = ITEMS.register("vesnium", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> AEROLYTE_INGOT = ITEMS.register("aerolyte_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_NUGGET = ITEMS.register("aerolyte_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PLATE = ITEMS.register("aerolyte_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> RAW_AEROLYTE = ITEMS.register("raw_aerolyte", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> SKY_STONE = ITEMS.register("sky_stone", () -> new BlockItem(ModBlocks.SKY_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_ORE = ITEMS.register("etrium_ore", () -> new BlockItem(ModBlocks.ETRIUM_ORE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> OXYGEN_BUCKET = BUCKETS.register("oxygen_bucket", () -> new FluidBucketItem(ModFluidProperties.OXYGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> HYDROGEN_BUCKET = BUCKETS.register("hydrogen_bucket", () -> new FluidBucketItem(ModFluidProperties.HYDROGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> CRUDE_OIL_BUCKET = BUCKETS.register("crude_oil_bucket", () -> new FluidBucketItem(ModFluidProperties.CRUDE_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> KEROSENE_BUCKET = BUCKETS.register("kerosene_bucket", () -> new FluidBucketItem(ModFluidProperties.KEROSENE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> ETRIONIC_FUEL_BUCKET = BUCKETS.register("etrionic_fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.ETRIONIC_FUEL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> CRYOGENIC_FUEL_BUCKET = BUCKETS.register("cryogenic_fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.CRYOGENIC_FUEL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static void onRegisterCreativeTabs(TriConsumer<ResourceLocation, RegistryEntry<Item>, List<Item>> consumer) {
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "main"), ModItems.ETRIUM_NUGGET, BuiltInRegistries.ITEM.stream().filter(i -> BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(AdAstra.MOD_ID)).toList());
    }
}
