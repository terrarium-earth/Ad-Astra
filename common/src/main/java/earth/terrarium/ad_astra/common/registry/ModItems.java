package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.item.*;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.registry.fluid.FluidBucketItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Item> CUBES = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SANDS = ResourcefulRegistries.create(CUBES);
    public static final ResourcefulRegistry<Item> STONE_BRICKS = ResourcefulRegistries.create(CUBES);
    public static final ResourcefulRegistry<Item> PILLARS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> STAIRS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SLABS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> WALLS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> BUTTONS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> PRESSURE_PLATES = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> MACHINES = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> FLAGS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> GLOBES = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SLIDING_DOORS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> BASIC_SLIDING_DOORS = ResourcefulRegistries.create(SLIDING_DOORS);
    public static final ResourcefulRegistry<Item> SPECIAL_SLIDING_DOORS = ResourcefulRegistries.create(SLIDING_DOORS);
    public static final ResourcefulRegistry<Item> BUCKETS = ResourcefulRegistries.create(ITEMS);

    public static final RegistryEntry<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> OXYGEN_DETECTOR = ITEMS.register("oxygen_detector", () -> new OxygenDetectorItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> TEMPERATURE_DETECTOR = ITEMS.register("temperature_detector", () -> new TemperatureDetectorItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> GRAVITY_DETECTOR = ITEMS.register("gravity_detector", () -> new GravityDetectorItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> SPACE_PAINTING = ITEMS.register("space_painting", () -> new CustomPaintingItem(new Item.Properties(), ModPaintings.EARTH, ModTags.Paintings.PAINTINGS));

    public static final RegistryEntry<Item> ZIP_GUN = ITEMS.register("zip_gun", () -> new ZipGunItem(2, FluidHooks.buckets(3), new Item.Properties().stacksTo(1)));

    public static final RegistryEntry<Item> ETRIUM_CABLE = ITEMS.register("etrium_cable", () -> new BlockItem(ModBlocks.ETRIUM_CABLE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_FLUID_PIPE = ITEMS.register("desmium_fluid_pipe", () -> new BlockItem(ModBlocks.DESMIUM_FLUID_PIPE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> CABLE_DUCT = ITEMS.register("cable_duct", () -> new BlockItem(ModBlocks.CABLE_DUCT.get(), new Item.Properties()));
    public static final RegistryEntry<Item> FLUID_PIPE_DUCT = ITEMS.register("fluid_pipe_duct", () -> new BlockItem(ModBlocks.FLUID_PIPE_DUCT.get(), new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_GENERATOR = MACHINES.register("etrionic_generator", () -> new BlockItem(ModBlocks.ETRIONIC_GENERATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> COMBUSTION_GENERATOR = MACHINES.register("combustion_generator", () -> new BlockItem(ModBlocks.COMBUSTION_GENERATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIONIC_SOLAR_PANEL = MACHINES.register("etrionic_solar_panel", () -> new BlockItem(ModBlocks.ETRIONIC_SOLAR_PANEL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_SOLAR_PANEL = MACHINES.register("vesnium_solar_panel", () -> new BlockItem(ModBlocks.VESNIUM_SOLAR_PANEL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GEOTHERMAL_GENERATOR = MACHINES.register("geothermal_generator", () -> new BlockItem(ModBlocks.GEOTHERMAL_GENERATOR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_BLAST_FURNACE = MACHINES.register("etrionic_blast_furnace", () -> new BlockItem(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new BlockItem(ModBlocks.HYDRAULIC_PRESS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ELECTROLYZER = MACHINES.register("electrolyzer", () -> new BlockItem(ModBlocks.ELECTROLYZER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OXYGEN_SENSOR = MACHINES.register("oxygen_sensor", () -> new BlockItem(ModBlocks.OXYGEN_SENSOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OIL_REFINERY = MACHINES.register("oil_refinery", () -> new BlockItem(ModBlocks.OIL_REFINERY.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRYOGENIC_FREEZER = MACHINES.register("cryogenic_freezer", () -> new BlockItem(ModBlocks.CRYOGENIC_FREEZER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> RECYCLER = MACHINES.register("recycler", () -> new BlockItem(ModBlocks.RECYCLER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new BlockItem(ModBlocks.OXYGEN_DISTRIBUTOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> TEMPERATURE_REGULATOR = MACHINES.register("temperature_regulator", () -> new BlockItem(ModBlocks.TEMPERATURE_REGULATOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GRAVITY_NORMALIZER = MACHINES.register("gravity_normalizer", () -> new BlockItem(ModBlocks.GRAVITY_NORMALIZER.get(), new Item.Properties()));
    public static final RegistryEntry<Item> WATER_PUMP = MACHINES.register("water_pump", () -> new BlockItem(ModBlocks.WATER_PUMP.get(), new Item.Properties()));

    public static final RegistryEntry<Item> IRON_PLATE = ITEMS.register("iron_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> IRON_ROD = ITEMS.register("iron_rod", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> ETRIUM_NUGGET = ITEMS.register("etrium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_INGOT = ITEMS.register("etrium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PLATE = ITEMS.register("etrium_plate", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PLATE = ITEMS.register("steel_plate", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_ROD = ITEMS.register("steel_rod", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> RAW_DESMIUM = ITEMS.register("raw_desmium", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_NUGGET = ITEMS.register("desmium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_INGOT = ITEMS.register("desmium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PLATE = ITEMS.register("desmium_plate", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> RAW_THERMALYTE = ITEMS.register("raw_thermalyte", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_NUGGET = ITEMS.register("thermalyte_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_INGOT = ITEMS.register("thermalyte_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PLATE = ITEMS.register("thermalyte_plate", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> VESNIUM = ITEMS.register("vesnium", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> RAW_AEROLYTE = ITEMS.register("raw_aerolyte", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_NUGGET = ITEMS.register("aerolyte_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_INGOT = ITEMS.register("aerolyte_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PLATE = ITEMS.register("aerolyte_plate", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> OXYGEN_BUCKET = BUCKETS.register("oxygen_bucket", () -> new FluidBucketItem(ModFluidProperties.OXYGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> HYDROGEN_BUCKET = BUCKETS.register("hydrogen_bucket", () -> new FluidBucketItem(ModFluidProperties.HYDROGEN, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> CRUDE_OIL_BUCKET = BUCKETS.register("crude_oil_bucket", () -> new FluidBucketItem(ModFluidProperties.CRUDE_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> KEROSENE_BUCKET = BUCKETS.register("kerosene_bucket", () -> new FluidBucketItem(ModFluidProperties.KEROSENE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> ETRIONIC_FUEL_BUCKET = BUCKETS.register("etrionic_fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.ETRIONIC_FUEL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryEntry<Item> CRYOGENIC_FUEL_BUCKET = BUCKETS.register("cryogenic_fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.CRYOGENIC_FUEL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryEntry<Item> PHOTOVOLTAIC_ETRIUM_CELL = ITEMS.register("photovoltaic_etrium_cell", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> PHOTOVOLTAIC_VESNIUM_CELL = ITEMS.register("photovoltaic_vesnium_cell", () -> new Item(new Item.Properties()));

    public static final RegistryEntry<Item> ETRIONIC_CORE = ITEMS.register("etrionic_core", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> ETRIONIC_CAPACITOR = ITEMS.register("etrionic_capacitor", () -> new EtrionicCapacitorItem(new Item.Properties()));

    public static final RegistryEntry<Item> WHITE_FLAG = FLAGS.register("white_flag", () -> new BlockItem(ModBlocks.WHITE_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ORANGE_FLAG = FLAGS.register("orange_flag", () -> new BlockItem(ModBlocks.ORANGE_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MAGENTA_FLAG = FLAGS.register("magenta_flag", () -> new BlockItem(ModBlocks.MAGENTA_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> LIGHT_BLUE_FLAG = FLAGS.register("light_blue_flag", () -> new BlockItem(ModBlocks.LIGHT_BLUE_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> YELLOW_FLAG = FLAGS.register("yellow_flag", () -> new BlockItem(ModBlocks.YELLOW_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> LIME_FLAG = FLAGS.register("lime_flag", () -> new BlockItem(ModBlocks.LIME_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> PINK_FLAG = FLAGS.register("pink_flag", () -> new BlockItem(ModBlocks.PINK_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GRAY_FLAG = FLAGS.register("gray_flag", () -> new BlockItem(ModBlocks.GRAY_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> LIGHT_GRAY_FLAG = FLAGS.register("light_gray_flag", () -> new BlockItem(ModBlocks.LIGHT_GRAY_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CYAN_FLAG = FLAGS.register("cyan_flag", () -> new BlockItem(ModBlocks.CYAN_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> PURPLE_FLAG = FLAGS.register("purple_flag", () -> new BlockItem(ModBlocks.PURPLE_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> BLUE_FLAG = FLAGS.register("blue_flag", () -> new BlockItem(ModBlocks.BLUE_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> BROWN_FLAG = FLAGS.register("brown_flag", () -> new BlockItem(ModBlocks.BROWN_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GREEN_FLAG = FLAGS.register("green_flag", () -> new BlockItem(ModBlocks.GREEN_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> RED_FLAG = FLAGS.register("red_flag", () -> new BlockItem(ModBlocks.RED_FLAG.get(), new Item.Properties()));
    public static final RegistryEntry<Item> BLACK_FLAG = FLAGS.register("black_flag", () -> new BlockItem(ModBlocks.BLACK_FLAG.get(), new Item.Properties()));

    public static final RegistryEntry<Item> EARTH_GLOBE = GLOBES.register("earth_globe", () -> new RenderedBlockItem(ModBlocks.EARTH_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> MOON_GLOBE = GLOBES.register("moon_globe", () -> new RenderedBlockItem(ModBlocks.MOON_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> MARS_GLOBE = GLOBES.register("mars_globe", () -> new RenderedBlockItem(ModBlocks.MARS_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> VENUS_GLOBE = GLOBES.register("venus_globe", () -> new RenderedBlockItem(ModBlocks.VENUS_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> MERCURY_GLOBE = GLOBES.register("mercury_globe", () -> new RenderedBlockItem(ModBlocks.MERCURY_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryEntry<Item> GLACIO_GLOBE = GLOBES.register("glacio_globe", () -> new RenderedBlockItem(ModBlocks.GLACIO_GLOBE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    public static final RegistryEntry<Item> IRON_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("iron_sliding_door", () -> new BlockItem(ModBlocks.IRON_SLIDING_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("steel_sliding_door", () -> new BlockItem(ModBlocks.STEEL_SLIDING_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AIRLOCK = SPECIAL_SLIDING_DOORS.register("airlock", () -> new BlockItem(ModBlocks.AIRLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> REINFORCED_DOOR = SPECIAL_SLIDING_DOORS.register("reinforced_door", () -> new BlockItem(ModBlocks.REINFORCED_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("etrium_sliding_door", () -> new BlockItem(ModBlocks.ETRIUM_SLIDING_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("desmium_sliding_door", () -> new BlockItem(ModBlocks.DESMIUM_SLIDING_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("thermalyte_sliding_door", () -> new BlockItem(ModBlocks.THERMALYTE_SLIDING_DOOR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("aerolyte_sliding_door", () -> new BlockItem(ModBlocks.AEROLYTE_SLIDING_DOOR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> IRON_PLATING = CUBES.register("iron_plating", () -> new BlockItem(ModBlocks.IRON_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> IRON_PLATING_STAIRS = STAIRS.register("iron_plating_stairs", () -> new BlockItem(ModBlocks.IRON_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> IRON_PLATING_SLAB = SLABS.register("iron_plating_slab", () -> new BlockItem(ModBlocks.IRON_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> IRON_PILLAR = PILLARS.register("iron_pillar", () -> new BlockItem(ModBlocks.IRON_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CROSS_IRON_PILLAR = PILLARS.register("cross_iron_pillar", () -> new BlockItem(ModBlocks.CROSS_IRON_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_IRON_PILLAR = PILLARS.register("glowing_iron_pillar", () -> new BlockItem(ModBlocks.GLOWING_IRON_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENT = CUBES.register("vent", () -> new BlockItem(ModBlocks.VENT.get(), new Item.Properties()));

    public static final RegistryEntry<Item> STEEL_BLOCK = CUBES.register("steel_block", () -> new BlockItem(ModBlocks.STEEL_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PLATING = CUBES.register("steel_plating", () -> new BlockItem(ModBlocks.STEEL_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PLATING_STAIRS = STAIRS.register("steel_plating_stairs", () -> new BlockItem(ModBlocks.STEEL_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PLATING_SLAB = SLABS.register("steel_plating_slab", () -> new BlockItem(ModBlocks.STEEL_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PILLAR = PILLARS.register("steel_pillar", () -> new BlockItem(ModBlocks.STEEL_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_STEEL_PILLAR = PILLARS.register("glowing_steel_pillar", () -> new BlockItem(ModBlocks.GLOWING_STEEL_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_BUTTON = BUTTONS.register("steel_button", () -> new BlockItem(ModBlocks.STEEL_BUTTON.get(), new Item.Properties()));
    public static final RegistryEntry<Item> STEEL_PRESSURE_PLATE = PRESSURE_PLATES.register("steel_pressure_plate", () -> new BlockItem(ModBlocks.STEEL_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> ETRIUM_BLOCK = CUBES.register("etrium_block", () -> new BlockItem(ModBlocks.ETRIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PLATING = CUBES.register("etrium_plating", () -> new BlockItem(ModBlocks.ETRIUM_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PLATING_STAIRS = STAIRS.register("etrium_plating_stairs", () -> new BlockItem(ModBlocks.ETRIUM_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PLATING_SLAB = SLABS.register("etrium_plating_slab", () -> new BlockItem(ModBlocks.ETRIUM_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PILLAR = PILLARS.register("etrium_pillar", () -> new BlockItem(ModBlocks.ETRIUM_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_ETRIUM_PILLAR = PILLARS.register("glowing_etrium_pillar", () -> new BlockItem(ModBlocks.GLOWING_ETRIUM_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_BUTTON = BUTTONS.register("etrium_button", () -> new BlockItem(ModBlocks.ETRIUM_BUTTON.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_PRESSURE_PLATE = PRESSURE_PLATES.register("etrium_pressure_plate", () -> new BlockItem(ModBlocks.ETRIUM_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> RAW_DESMIUM_BLOCK = CUBES.register("raw_desmium_block", () -> new BlockItem(ModBlocks.RAW_DESMIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_BLOCK = CUBES.register("desmium_block", () -> new BlockItem(ModBlocks.DESMIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PLATING = CUBES.register("desmium_plating", () -> new BlockItem(ModBlocks.DESMIUM_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PLATING_STAIRS = STAIRS.register("desmium_plating_stairs", () -> new BlockItem(ModBlocks.DESMIUM_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PLATING_SLAB = SLABS.register("desmium_plating_slab", () -> new BlockItem(ModBlocks.DESMIUM_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PILLAR = PILLARS.register("desmium_pillar", () -> new BlockItem(ModBlocks.DESMIUM_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_DESMIUM_PILLAR = PILLARS.register("glowing_desmium_pillar", () -> new BlockItem(ModBlocks.GLOWING_DESMIUM_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_BUTTON = BUTTONS.register("desmium_button", () -> new BlockItem(ModBlocks.DESMIUM_BUTTON.get(), new Item.Properties()));
    public static final RegistryEntry<Item> DESMIUM_PRESSURE_PLATE = PRESSURE_PLATES.register("desmium_pressure_plate", () -> new BlockItem(ModBlocks.DESMIUM_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> RAW_THERMALYTE_BLOCK = CUBES.register("raw_thermalyte_block", () -> new BlockItem(ModBlocks.RAW_THERMALYTE_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_BLOCK = CUBES.register("thermalyte_block", () -> new BlockItem(ModBlocks.THERMALYTE_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PLATING = CUBES.register("thermalyte_plating", () -> new BlockItem(ModBlocks.THERMALYTE_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PLATING_STAIRS = STAIRS.register("thermalyte_plating_stairs", () -> new BlockItem(ModBlocks.THERMALYTE_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PLATING_SLAB = SLABS.register("thermalyte_plating_slab", () -> new BlockItem(ModBlocks.THERMALYTE_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PILLAR = PILLARS.register("thermalyte_pillar", () -> new BlockItem(ModBlocks.THERMALYTE_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_THERMALYTE_PILLAR = PILLARS.register("glowing_thermalyte_pillar", () -> new BlockItem(ModBlocks.GLOWING_THERMALYTE_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_BUTTON = BUTTONS.register("thermalyte_button", () -> new BlockItem(ModBlocks.THERMALYTE_BUTTON.get(), new Item.Properties()));
    public static final RegistryEntry<Item> THERMALYTE_PRESSURE_PLATE = PRESSURE_PLATES.register("thermalyte_pressure_plate", () -> new BlockItem(ModBlocks.THERMALYTE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> VESNIUM_BLOCK = CUBES.register("vesnium_block", () -> new BlockItem(ModBlocks.VESNIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_CRYSTAL_BLOCK = CUBES.register("vesnium_crystal_block", () -> new BlockItem(ModBlocks.VESNIUM_CRYSTAL_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_STAIRS = STAIRS.register("vesnium_crystal_block_stairs", () -> new BlockItem(ModBlocks.VESNIUM_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_SLAB = SLABS.register("vesnium_crystal_block_slab", () -> new BlockItem(ModBlocks.VESNIUM_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_BRICKS = CUBES.register("vesnium_bricks", () -> new BlockItem(ModBlocks.VESNIUM_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_BRICK_STAIRS = STAIRS.register("vesnium_brick_stairs", () -> new BlockItem(ModBlocks.VESNIUM_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_BRICK_SLAB = SLABS.register("vesnium_brick_slab", () -> new BlockItem(ModBlocks.VESNIUM_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VESNIUM_PILLAR = PILLARS.register("vesnium_pillar", () -> new BlockItem(ModBlocks.VESNIUM_PILLAR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> RAW_AEROLYTE_BLOCK = CUBES.register("raw_aerolyte_block", () -> new BlockItem(ModBlocks.RAW_AEROLYTE_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_BLOCK = CUBES.register("aerolyte_block", () -> new BlockItem(ModBlocks.AEROLYTE_BLOCK.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PLATING = CUBES.register("aerolyte_plating", () -> new BlockItem(ModBlocks.AEROLYTE_PLATING.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PLATING_STAIRS = STAIRS.register("aerolyte_plating_stairs", () -> new BlockItem(ModBlocks.AEROLYTE_PLATING_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PLATING_SLAB = SLABS.register("aerolyte_plating_slab", () -> new BlockItem(ModBlocks.AEROLYTE_PLATING_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PILLAR = PILLARS.register("aerolyte_pillar", () -> new BlockItem(ModBlocks.AEROLYTE_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLOWING_AEROLYTE_PILLAR = PILLARS.register("glowing_aerolyte_pillar", () -> new BlockItem(ModBlocks.GLOWING_AEROLYTE_PILLAR.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_BUTTON = BUTTONS.register("aerolyte_button", () -> new BlockItem(ModBlocks.AEROLYTE_BUTTON.get(), new Item.Properties()));
    public static final RegistryEntry<Item> AEROLYTE_PRESSURE_PLATE = PRESSURE_PLATES.register("aerolyte_pressure_plate", () -> new BlockItem(ModBlocks.AEROLYTE_PRESSURE_PLATE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> SKY_STONE = CUBES.register("sky_stone", () -> new BlockItem(ModBlocks.SKY_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> SKY_STONE_STAIRS = STAIRS.register("sky_stone_stairs", () -> new BlockItem(ModBlocks.SKY_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> SKY_STONE_SLAB = SLABS.register("sky_stone_slab", () -> new BlockItem(ModBlocks.SKY_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> ETRIUM_ORE = CUBES.register("etrium_ore", () -> new BlockItem(ModBlocks.ETRIUM_ORE.get(), new Item.Properties()));

    public static final RegistryEntry<Item> MOON_SAND = SANDS.register("moon_sand", () -> new BlockItem(ModBlocks.MOON_SAND.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_COBBLESTONE = CUBES.register("moon_cobblestone", () -> new BlockItem(ModBlocks.MOON_COBBLESTONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_COBBLESTONE_STAIRS = STAIRS.register("moon_cobblestone_stairs", () -> new BlockItem(ModBlocks.MOON_COBBLESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_COBBLESTONE_SLAB = SLABS.register("moon_cobblestone_slab", () -> new BlockItem(ModBlocks.MOON_COBBLESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_COBBLESTONE_WALL = WALLS.register("moon_cobblestone_wall", () -> new BlockItem(ModBlocks.MOON_COBBLESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE = CUBES.register("moon_stone", () -> new BlockItem(ModBlocks.MOON_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE_STAIRS = STAIRS.register("moon_stone_stairs", () -> new BlockItem(ModBlocks.MOON_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE_SLAB = SLABS.register("moon_stone_slab", () -> new BlockItem(ModBlocks.MOON_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MOON_STONE = CUBES.register("polished_moon_stone", () -> new BlockItem(ModBlocks.POLISHED_MOON_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MOON_STONE_STAIRS = STAIRS.register("polished_moon_stone_stairs", () -> new BlockItem(ModBlocks.POLISHED_MOON_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MOON_STONE_SLAB = SLABS.register("polished_moon_stone_slab", () -> new BlockItem(ModBlocks.POLISHED_MOON_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MOON_STONE_WALL = WALLS.register("polished_moon_stone_wall", () -> new BlockItem(ModBlocks.POLISHED_MOON_STONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE_BRICKS = STONE_BRICKS.register("moon_stone_bricks", () -> new BlockItem(ModBlocks.MOON_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE_BRICK_STAIRS = STAIRS.register("moon_stone_brick_stairs", () -> new BlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_STONE_BRICK_SLAB = SLABS.register("moon_stone_brick_slab", () -> new BlockItem(ModBlocks.MOON_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRACKED_MOON_STONE_BRICKS = STONE_BRICKS.register("cracked_moon_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CHISELED_MOON_STONE_BRICKS = STONE_BRICKS.register("chiseled_moon_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_MOON_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MOON_PILLAR = PILLARS.register("moon_pillar", () -> new BlockItem(ModBlocks.MOON_PILLAR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> MARS_SAND = SANDS.register("mars_sand", () -> new BlockItem(ModBlocks.MARS_SAND.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_COBBLESTONE = CUBES.register("mars_cobblestone", () -> new BlockItem(ModBlocks.MARS_COBBLESTONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_COBBLESTONE_STAIRS = STAIRS.register("mars_cobblestone_stairs", () -> new BlockItem(ModBlocks.MARS_COBBLESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_COBBLESTONE_SLAB = SLABS.register("mars_cobblestone_slab", () -> new BlockItem(ModBlocks.MARS_COBBLESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_COBBLESTONE_WALL = WALLS.register("mars_cobblestone_wall", () -> new BlockItem(ModBlocks.MARS_COBBLESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE = CUBES.register("mars_stone", () -> new BlockItem(ModBlocks.MARS_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE_STAIRS = STAIRS.register("mars_stone_stairs", () -> new BlockItem(ModBlocks.MARS_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE_SLAB = SLABS.register("mars_stone_slab", () -> new BlockItem(ModBlocks.MARS_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MARS_STONE = CUBES.register("polished_mars_stone", () -> new BlockItem(ModBlocks.POLISHED_MARS_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MARS_STONE_STAIRS = STAIRS.register("polished_mars_stone_stairs", () -> new BlockItem(ModBlocks.POLISHED_MARS_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MARS_STONE_SLAB = SLABS.register("polished_mars_stone_slab", () -> new BlockItem(ModBlocks.POLISHED_MARS_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MARS_STONE_WALL = WALLS.register("polished_mars_stone_wall", () -> new BlockItem(ModBlocks.POLISHED_MARS_STONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE_BRICKS = STONE_BRICKS.register("mars_stone_bricks", () -> new BlockItem(ModBlocks.MARS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE_BRICK_STAIRS = STAIRS.register("mars_stone_brick_stairs", () -> new BlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_STONE_BRICK_SLAB = SLABS.register("mars_stone_brick_slab", () -> new BlockItem(ModBlocks.MARS_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRACKED_MARS_STONE_BRICKS = STONE_BRICKS.register("cracked_mars_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CHISELED_MARS_STONE_BRICKS = STONE_BRICKS.register("chiseled_mars_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_MARS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MARS_PILLAR = PILLARS.register("mars_pillar", () -> new BlockItem(ModBlocks.MARS_PILLAR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> VENUS_SAND = SANDS.register("venus_sand", () -> new BlockItem(ModBlocks.VENUS_SAND.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_COBBLESTONE = CUBES.register("venus_cobblestone", () -> new BlockItem(ModBlocks.VENUS_COBBLESTONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_COBBLESTONE_STAIRS = STAIRS.register("venus_cobblestone_stairs", () -> new BlockItem(ModBlocks.VENUS_COBBLESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_COBBLESTONE_SLAB = SLABS.register("venus_cobblestone_slab", () -> new BlockItem(ModBlocks.VENUS_COBBLESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_COBBLESTONE_WALL = WALLS.register("venus_cobblestone_wall", () -> new BlockItem(ModBlocks.VENUS_COBBLESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE = PILLARS.register("venus_stone", () -> new BlockItem(ModBlocks.VENUS_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE_STAIRS = STAIRS.register("venus_stone_stairs", () -> new BlockItem(ModBlocks.VENUS_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE_SLAB = SLABS.register("venus_stone_slab", () -> new BlockItem(ModBlocks.VENUS_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_VENUS_STONE = CUBES.register("polished_venus_stone", () -> new BlockItem(ModBlocks.POLISHED_VENUS_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_VENUS_STONE_STAIRS = STAIRS.register("polished_venus_stone_stairs", () -> new BlockItem(ModBlocks.POLISHED_VENUS_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_VENUS_STONE_SLAB = SLABS.register("polished_venus_stone_slab", () -> new BlockItem(ModBlocks.POLISHED_VENUS_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_VENUS_STONE_WALL = WALLS.register("polished_venus_stone_wall", () -> new BlockItem(ModBlocks.POLISHED_VENUS_STONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE_BRICKS = STONE_BRICKS.register("venus_stone_bricks", () -> new BlockItem(ModBlocks.VENUS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE_BRICK_STAIRS = STAIRS.register("venus_stone_brick_stairs", () -> new BlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_STONE_BRICK_SLAB = SLABS.register("venus_stone_brick_slab", () -> new BlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRACKED_VENUS_STONE_BRICKS = STONE_BRICKS.register("cracked_venus_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CHISELED_VENUS_STONE_BRICKS = STONE_BRICKS.register("chiseled_venus_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_VENUS_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> VENUS_PILLAR = PILLARS.register("venus_pillar", () -> new BlockItem(ModBlocks.VENUS_PILLAR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> MERCURY_COBBLESTONE = CUBES.register("mercury_cobblestone", () -> new BlockItem(ModBlocks.MERCURY_COBBLESTONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_COBBLESTONE_STAIRS = STAIRS.register("mercury_cobblestone_stairs", () -> new BlockItem(ModBlocks.MERCURY_COBBLESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_COBBLESTONE_SLAB = SLABS.register("mercury_cobblestone_slab", () -> new BlockItem(ModBlocks.MERCURY_COBBLESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_COBBLESTONE_WALL = WALLS.register("mercury_cobblestone_wall", () -> new BlockItem(ModBlocks.MERCURY_COBBLESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE = CUBES.register("mercury_stone", () -> new BlockItem(ModBlocks.MERCURY_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE_STAIRS = STAIRS.register("mercury_stone_stairs", () -> new BlockItem(ModBlocks.MERCURY_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE_SLAB = SLABS.register("mercury_stone_slab", () -> new BlockItem(ModBlocks.MERCURY_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MERCURY_STONE = CUBES.register("polished_mercury_stone", () -> new BlockItem(ModBlocks.POLISHED_MERCURY_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MERCURY_STONE_STAIRS = STAIRS.register("polished_mercury_stone_stairs", () -> new BlockItem(ModBlocks.POLISHED_MERCURY_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MERCURY_STONE_SLAB = SLABS.register("polished_mercury_stone_slab", () -> new BlockItem(ModBlocks.POLISHED_MERCURY_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_MERCURY_STONE_WALL = WALLS.register("polished_mercury_stone_wall", () -> new BlockItem(ModBlocks.POLISHED_MERCURY_STONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE_BRICKS = STONE_BRICKS.register("mercury_stone_bricks", () -> new BlockItem(ModBlocks.MERCURY_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE_BRICK_STAIRS = STAIRS.register("mercury_stone_brick_stairs", () -> new BlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_STONE_BRICK_SLAB = SLABS.register("mercury_stone_brick_slab", () -> new BlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRACKED_MERCURY_STONE_BRICKS = STONE_BRICKS.register("cracked_mercury_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CHISELED_MERCURY_STONE_BRICKS = STONE_BRICKS.register("chiseled_mercury_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> MERCURY_PILLAR = PILLARS.register("mercury_pillar", () -> new BlockItem(ModBlocks.MERCURY_PILLAR.get(), new Item.Properties()));

    public static final RegistryEntry<Item> GLACIO_COBBLESTONE = CUBES.register("glacio_cobblestone", () -> new BlockItem(ModBlocks.GLACIO_COBBLESTONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_COBBLESTONE_STAIRS = STAIRS.register("glacio_cobblestone_stairs", () -> new BlockItem(ModBlocks.GLACIO_COBBLESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_COBBLESTONE_SLAB = SLABS.register("glacio_cobblestone_slab", () -> new BlockItem(ModBlocks.GLACIO_COBBLESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_COBBLESTONE_WALL = WALLS.register("glacio_cobblestone_wall", () -> new BlockItem(ModBlocks.GLACIO_COBBLESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE = CUBES.register("glacio_stone", () -> new BlockItem(ModBlocks.GLACIO_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE_STAIRS = STAIRS.register("glacio_stone_stairs", () -> new BlockItem(ModBlocks.GLACIO_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE_SLAB = SLABS.register("glacio_stone_slab", () -> new BlockItem(ModBlocks.GLACIO_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_GLACIO_STONE = CUBES.register("polished_glacio_stone", () -> new BlockItem(ModBlocks.POLISHED_GLACIO_STONE.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_GLACIO_STONE_STAIRS = STAIRS.register("polished_glacio_stone_stairs", () -> new BlockItem(ModBlocks.POLISHED_GLACIO_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_GLACIO_STONE_SLAB = SLABS.register("polished_glacio_stone_slab", () -> new BlockItem(ModBlocks.POLISHED_GLACIO_STONE_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> POLISHED_GLACIO_STONE_WALL = WALLS.register("polished_glacio_stone_wall", () -> new BlockItem(ModBlocks.POLISHED_GLACIO_STONE_WALL.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE_BRICKS = STONE_BRICKS.register("glacio_stone_bricks", () -> new BlockItem(ModBlocks.GLACIO_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE_BRICK_STAIRS = STAIRS.register("glacio_stone_brick_stairs", () -> new BlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_STONE_BRICK_SLAB = SLABS.register("glacio_stone_brick_slab", () -> new BlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CRACKED_GLACIO_STONE_BRICKS = STONE_BRICKS.register("cracked_glacio_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> CHISELED_GLACIO_STONE_BRICKS = STONE_BRICKS.register("chiseled_glacio_stone_bricks", () -> new BlockItem(ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryEntry<Item> GLACIO_PILLAR = PILLARS.register("glacio_pillar", () -> new BlockItem(ModBlocks.GLACIO_PILLAR.get(), new Item.Properties()));

    public static void onRegisterCreativeTabs(TriConsumer<ResourceLocation, RegistryEntry<Item>, List<Item>> consumer) {
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "main"), ETRIUM_NUGGET, BuiltInRegistries.ITEM.stream().filter(i -> BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(AdAstra.MOD_ID)).toList());
    }
}
