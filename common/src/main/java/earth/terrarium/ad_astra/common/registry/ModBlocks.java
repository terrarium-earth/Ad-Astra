package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.flag.FlagBlock;
import earth.terrarium.ad_astra.common.block.fluid.CryogenicFuelLiquidBlock;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.machine.AnimatedMachineBlock;
import earth.terrarium.ad_astra.common.block.machine.MachineBlock;
import earth.terrarium.ad_astra.common.block.machine.SolarPanelBlock;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.ad_astra.common.block.pipe.PipeDuctBlock;
import earth.terrarium.ad_astra.common.block.slidingdoor.SlidingDoorBlock;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> CUBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SANDS = ResourcefulRegistries.create(CUBES);
    public static final ResourcefulRegistry<Block> STONE_BRICKS = ResourcefulRegistries.create(CUBES);
    public static final ResourcefulRegistry<Block> PILLARS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> STAIRS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SLABS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> WALLS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> BUTTONS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> PRESSURE_PLATES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> MACHINES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> FLAGS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> GLOBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SLIDING_DOORS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> BASIC_SLIDING_DOORS = ResourcefulRegistries.create(SLIDING_DOORS);
    public static final ResourcefulRegistry<Block> SPECIAL_SLIDING_DOORS = ResourcefulRegistries.create(SLIDING_DOORS);
    public static final ResourcefulRegistry<Block> FLUIDS = ResourcefulRegistries.create(BLOCKS);

    public static final RegistryEntry<Block> ETRIUM_CABLE = BLOCKS.register("etrium_cable", () -> new PipeBlock(4096, 0.344, PipeBlock.PipeType.CABLE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));
    public static final RegistryEntry<Block> DESMIUM_FLUID_PIPE = BLOCKS.register("desmium_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.256f), 0.185, PipeBlock.PipeType.FLUID_PIPE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));

    public static final RegistryEntry<Block> CABLE_DUCT = CUBES.register("cable_duct", () -> new PipeDuctBlock(4096, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));
    public static final RegistryEntry<Block> FLUID_PIPE_DUCT = CUBES.register("fluid_pipe_duct", () -> new PipeDuctBlock(FluidHooks.buckets(0.256f), BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));

    public static final RegistryEntry<Block> ETRIONIC_GENERATOR = MACHINES.register("etrionic_generator", () -> new AnimatedMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> COMBUSTION_GENERATOR = MACHINES.register("combustion_generator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> ETRIONIC_SOLAR_PANEL = MACHINES.register("etrionic_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 16, 10000));
    public static final RegistryEntry<Block> VESNIUM_SOLAR_PANEL = MACHINES.register("vesnium_solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK), 64, 1000000));
    public static final RegistryEntry<Block> GEOTHERMAL_GENERATOR = MACHINES.register("geothermal_generator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryEntry<Block> ETRIONIC_BLAST_FURNACE = MACHINES.register("etrionic_blast_furnace", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> HYDRAULIC_PRESS = MACHINES.register("hydraulic_press", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> ELECTROLYZER = MACHINES.register("electrolyzer", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_SENSOR = MACHINES.register("oxygen_sensor", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OIL_REFINERY = MACHINES.register("oil_refinery", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> CRYOGENIC_FREEZER = MACHINES.register("cryogenic_freezer", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> RECYCLER = MACHINES.register("recycler", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> TEMPERATURE_REGULATOR = MACHINES.register("temperature_regulator", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> GRAVITY_NORMALIZER = MACHINES.register("gravity_normalizer", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> WATER_PUMP = MACHINES.register("water_pump", () -> new MachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryEntry<Block> WHITE_FLAG = FLAGS.register("white_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> BLACK_FLAG = FLAGS.register("black_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> BLUE_FLAG = FLAGS.register("blue_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> BROWN_FLAG = FLAGS.register("brown_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> CYAN_FLAG = FLAGS.register("cyan_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> GRAY_FLAG = FLAGS.register("gray_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> GREEN_FLAG = FLAGS.register("green_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> LIGHT_BLUE_FLAG = FLAGS.register("light_blue_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> LIGHT_GRAY_FLAG = FLAGS.register("light_gray_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> LIME_FLAG = FLAGS.register("lime_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> MAGENTA_FLAG = FLAGS.register("magenta_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> ORANGE_FLAG = FLAGS.register("orange_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> PINK_FLAG = FLAGS.register("pink_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> PURPLE_FLAG = FLAGS.register("purple_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> RED_FLAG = FLAGS.register("red_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));
    public static final RegistryEntry<Block> YELLOW_FLAG = FLAGS.register("yellow_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_BANNER)));

    public static final RegistryEntry<Block> EARTH_GLOBE = GLOBES.register("earth_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> MOON_GLOBE = GLOBES.register("moon_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> MARS_GLOBE = GLOBES.register("mars_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> VENUS_GLOBE = GLOBES.register("venus_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> MERCURY_GLOBE = GLOBES.register("mercury_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> GLACIO_GLOBE = GLOBES.register("glacio_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));

    public static final RegistryEntry<Block> IRON_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("iron_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> STEEL_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("steel_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AIRLOCK = SPECIAL_SLIDING_DOORS.register("airlock", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> REINFORCED_DOOR = SPECIAL_SLIDING_DOORS.register("reinforced_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> ETRIUM_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("etrium_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("desmium_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("thermalyte_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_SLIDING_DOOR = BASIC_SLIDING_DOORS.register("aerolyte_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));

    public static final RegistryEntry<Block> IRON_PLATING = CUBES.register("iron_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> IRON_PLATING_STAIRS = STAIRS.register("iron_plating_stairs", () -> new StairBlock(IRON_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> IRON_PLATING_SLAB = SLABS.register("iron_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> IRON_PILLAR = PILLARS.register("iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> CROSS_IRON_PILLAR = PILLARS.register("cross_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_IRON_PILLAR = PILLARS.register("glowing_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));

    public static final RegistryEntry<Block> STEEL_BLOCK = CUBES.register("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> STEEL_PLATING = CUBES.register("steel_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> STEEL_PLATING_STAIRS = STAIRS.register("steel_plating_stairs", () -> new StairBlock(STEEL_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> STEEL_PLATING_SLAB = SLABS.register("steel_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> STEEL_PILLAR = PILLARS.register("steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_STEEL_PILLAR = PILLARS.register("glowing_steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));
    public static final RegistryEntry<Block> STEEL_BUTTON = BUTTONS.register("steel_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.COPPER), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> STEEL_PRESSURE_PLATE = PRESSURE_PLATES.register("steel_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.COPPER), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));

    public static final RegistryEntry<Block> ETRIUM_BLOCK = CUBES.register("etrium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> ETRIUM_PLATING = CUBES.register("etrium_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> ETRIUM_PLATING_STAIRS = STAIRS.register("etrium_plating_stairs", () -> new StairBlock(ETRIUM_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> ETRIUM_PLATING_SLAB = SLABS.register("etrium_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> ETRIUM_PILLAR = PILLARS.register("etrium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_ETRIUM_PILLAR = PILLARS.register("glowing_etrium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));
    public static final RegistryEntry<Block> ETRIUM_BUTTON = BUTTONS.register("etrium_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.COPPER), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> ETRIUM_PRESSURE_PLATE = PRESSURE_PLATES.register("etrium_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.COPPER), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));

    public static final RegistryEntry<Block> RAW_DESMIUM_BLOCK = CUBES.register("raw_desmium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_BLOCK = CUBES.register("desmium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_PLATING = CUBES.register("desmium_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_PLATING_STAIRS = STAIRS.register("desmium_plating_stairs", () -> new StairBlock(DESMIUM_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_PLATING_SLAB = SLABS.register("desmium_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> DESMIUM_PILLAR = PILLARS.register("desmium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_DESMIUM_PILLAR = PILLARS.register("glowing_desmium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));
    public static final RegistryEntry<Block> DESMIUM_BUTTON = BUTTONS.register("desmium_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.COPPER), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> DESMIUM_PRESSURE_PLATE = PRESSURE_PLATES.register("desmium_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.COPPER), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));

    public static final RegistryEntry<Block> RAW_THERMALYTE_BLOCK = CUBES.register("raw_thermalyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_BLOCK = CUBES.register("thermalyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_PLATING = CUBES.register("thermalyte_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_PLATING_STAIRS = STAIRS.register("thermalyte_plating_stairs", () -> new StairBlock(THERMALYTE_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_PLATING_SLAB = SLABS.register("thermalyte_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> THERMALYTE_PILLAR = PILLARS.register("thermalyte_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_THERMALYTE_PILLAR = PILLARS.register("glowing_thermalyte_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));
    public static final RegistryEntry<Block> THERMALYTE_BUTTON = BUTTONS.register("thermalyte_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.COPPER), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> THERMALYTE_PRESSURE_PLATE = PRESSURE_PLATES.register("thermalyte_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.COPPER), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));

    public static final RegistryEntry<Block> VESNIUM_BLOCK = CUBES.register("vesnium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.TERRACOTTA_GRAY)));
    public static final RegistryEntry<Block> VESNIUM_CRYSTAL_BLOCK = CUBES.register("vesnium_crystal_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).color(MaterialColor.TERRACOTTA_GRAY)));
    public static final RegistryEntry<Block> VESNIUM_STAIRS = STAIRS.register("vesnium_crystal_block_stairs", () -> new StairBlock(VESNIUM_CRYSTAL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_SLAB = SLABS.register("vesnium_crystal_block_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_BRICKS = CUBES.register("vesnium_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_BRICK_STAIRS = STAIRS.register("vesnium_brick_stairs", () -> new StairBlock(VESNIUM_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_BRICK_SLAB = SLABS.register("vesnium_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryEntry<Block> VESNIUM_PILLAR = PILLARS.register("vesnium_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));

    public static final RegistryEntry<Block> RAW_AEROLYTE_BLOCK = CUBES.register("raw_aerolyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_BLOCK = CUBES.register("aerolyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_PLATING = CUBES.register("aerolyte_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_PLATING_STAIRS = STAIRS.register("aerolyte_plating_stairs", () -> new StairBlock(AEROLYTE_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_PLATING_SLAB = SLABS.register("aerolyte_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> AEROLYTE_PILLAR = PILLARS.register("aerolyte_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final RegistryEntry<Block> GLOWING_AEROLYTE_PILLAR = PILLARS.register("glowing_aerolyte_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel((state) -> 15)));
    public static final RegistryEntry<Block> AEROLYTE_BUTTON = BUTTONS.register("aerolyte_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).sound(SoundType.COPPER), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> AEROLYTE_PRESSURE_PLATE = PRESSURE_PLATES.register("aerolyte_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).sound(SoundType.COPPER), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));

    public static final RegistryEntry<Block> SKY_STONE = CUBES.register("sky_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> SKY_STONE_STAIRS = STAIRS.register("sky_stone_stairs", () -> new StairBlock(SKY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> SKY_STONE_SLAB = SLABS.register("sky_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> ETRIUM_ORE = CUBES.register("etrium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE), UniformInt.of(5, 12)));

    public static final RegistryEntry<Block> MOON_SAND = SANDS.register("moon_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE = CUBES.register("moon_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_STAIRS = STAIRS.register("moon_cobblestone_stairs", () -> new StairBlock(MOON_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_SLAB = SLABS.register("moon_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_WALL = WALLS.register("moon_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_STONE = CUBES.register("moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_STAIRS = STAIRS.register("moon_stone_stairs", () -> new StairBlock(MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_STONE_SLAB = SLABS.register("moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE = CUBES.register("polished_moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_STAIRS = STAIRS.register("polished_moon_stone_stairs", () -> new StairBlock(POLISHED_MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_SLAB = SLABS.register("polished_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_WALL = WALLS.register("polished_moon_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_STONE_BRICKS = STONE_BRICKS.register("moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_STAIRS = STAIRS.register("moon_stone_brick_stairs", () -> new StairBlock(MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_SLAB = SLABS.register("moon_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CRACKED_MOON_STONE_BRICKS = STONE_BRICKS.register("cracked_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_BRICKS = STONE_BRICKS.register("chiseled_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MOON_PILLAR = PILLARS.register("moon_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> MARS_SAND = SANDS.register("mars_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE = CUBES.register("mars_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_STAIRS = STAIRS.register("mars_cobblestone_stairs", () -> new StairBlock(MARS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_SLAB = SLABS.register("mars_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_WALL = WALLS.register("mars_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_STONE = CUBES.register("mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_STAIRS = STAIRS.register("mars_stone_stairs", () -> new StairBlock(MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_STONE_SLAB = SLABS.register("mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE = CUBES.register("polished_mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_STAIRS = STAIRS.register("polished_mars_stone_stairs", () -> new StairBlock(POLISHED_MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_SLAB = SLABS.register("polished_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_WALL = WALLS.register("polished_mars_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_STONE_BRICKS = STONE_BRICKS.register("mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_STAIRS = STAIRS.register("mars_stone_brick_stairs", () -> new StairBlock(MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_SLAB = SLABS.register("mars_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CRACKED_MARS_STONE_BRICKS = STONE_BRICKS.register("cracked_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_BRICKS = STONE_BRICKS.register("chiseled_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MARS_PILLAR = PILLARS.register("mars_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> VENUS_SAND = SANDS.register("venus_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE = CUBES.register("venus_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_STAIRS = STAIRS.register("venus_cobblestone_stairs", () -> new StairBlock(VENUS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_SLAB = SLABS.register("venus_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_WALL = WALLS.register("venus_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_STONE = PILLARS.register("venus_stone", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_STAIRS = STAIRS.register("venus_stone_stairs", () -> new StairBlock(VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_STONE_SLAB = SLABS.register("venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE = CUBES.register("polished_venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_STAIRS = STAIRS.register("polished_venus_stone_stairs", () -> new StairBlock(POLISHED_VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_SLAB = SLABS.register("polished_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_WALL = WALLS.register("polished_venus_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICKS = STONE_BRICKS.register("venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_STAIRS = STAIRS.register("venus_stone_brick_stairs", () -> new StairBlock(VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_SLAB = SLABS.register("venus_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CRACKED_VENUS_STONE_BRICKS = STONE_BRICKS.register("cracked_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_BRICKS = STONE_BRICKS.register("chiseled_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> VENUS_PILLAR = PILLARS.register("venus_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> MERCURY_COBBLESTONE = CUBES.register("mercury_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_STAIRS = STAIRS.register("mercury_cobblestone_stairs", () -> new StairBlock(MERCURY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_SLAB = SLABS.register("mercury_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_WALL = WALLS.register("mercury_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_STONE = CUBES.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> MERCURY_STONE_STAIRS = STAIRS.register("mercury_stone_stairs", () -> new StairBlock(MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_STONE_SLAB = SLABS.register("mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE = CUBES.register("polished_mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_STAIRS = STAIRS.register("polished_mercury_stone_stairs", () -> new StairBlock(POLISHED_MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_SLAB = SLABS.register("polished_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_WALL = WALLS.register("polished_mercury_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICKS = STONE_BRICKS.register("mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_STAIRS = STAIRS.register("mercury_stone_brick_stairs", () -> new StairBlock(MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_SLAB = SLABS.register("mercury_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CRACKED_MERCURY_STONE_BRICKS = STONE_BRICKS.register("cracked_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_BRICKS = STONE_BRICKS.register("chiseled_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> MERCURY_PILLAR = PILLARS.register("mercury_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> GLACIO_COBBLESTONE = CUBES.register("glacio_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_STAIRS = STAIRS.register("glacio_cobblestone_stairs", () -> new StairBlock(GLACIO_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_SLAB = SLABS.register("glacio_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_WALL = WALLS.register("glacio_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_STONE = CUBES.register("glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_BLUE)));
    public static final RegistryEntry<Block> GLACIO_STONE_STAIRS = STAIRS.register("glacio_stone_stairs", () -> new StairBlock(GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_STONE_SLAB = SLABS.register("glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE = CUBES.register("polished_glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_STAIRS = STAIRS.register("polished_glacio_stone_stairs", () -> new StairBlock(POLISHED_GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_SLAB = SLABS.register("polished_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_WALL = WALLS.register("polished_glacio_stone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICKS = STONE_BRICKS.register("glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_STAIRS = STAIRS.register("glacio_stone_brick_stairs", () -> new StairBlock(GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_SLAB = SLABS.register("glacio_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CRACKED_GLACIO_STONE_BRICKS = STONE_BRICKS.register("cracked_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_BRICKS = STONE_BRICKS.register("chiseled_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryEntry<Block> GLACIO_PILLAR = PILLARS.register("glacio_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumLiquidBlock(ModFluidProperties.HYDROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRUDE_OIL = FLUIDS.register("crude_oil", () -> new BotariumLiquidBlock(ModFluidProperties.CRUDE_OIL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> KEROSENE = FLUIDS.register("kerosene", () -> new BotariumLiquidBlock(ModFluidProperties.KEROSENE, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> ETRIONIC_FUEL = FLUIDS.register("etrionic_fuel", () -> new BotariumLiquidBlock(ModFluidProperties.ETRIONIC_FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRYOGENIC_FUEL = FLUIDS.register("cryogenic_fuel", () -> new CryogenicFuelLiquidBlock(ModFluidProperties.CRYOGENIC_FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));
}
