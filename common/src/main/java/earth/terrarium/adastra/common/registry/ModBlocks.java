package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blocks.*;
import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import earth.terrarium.adastra.common.blocks.fluids.CryoFuelLiquidBlock;
import earth.terrarium.adastra.common.blocks.lamps.IndustrialLampBlock;
import earth.terrarium.adastra.common.blocks.lamps.SmallIndustrialLampBlock;
import earth.terrarium.adastra.common.blocks.machines.*;
import earth.terrarium.adastra.common.blocks.pipes.PipeBlock;
import earth.terrarium.adastra.common.blocks.pipes.PipeDuctBlock;
import earth.terrarium.adastra.common.blocks.pipes.TransferablePipe;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> MACHINES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> FLUIDS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CUBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CTM_CUBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CUBE_COLUMNS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> PILLARS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> STAIRS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SLABS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> BUTTONS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> PRESSURE_PLATES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> WALLS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SLIDING_DOORS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SIMPLE_SLIDING_DOORS = ResourcefulRegistries.create(SLIDING_DOORS);
    public static final ResourcefulRegistry<Block> GLOBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> FLAGS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> PIPES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> CABLES = ResourcefulRegistries.create(PIPES);
    public static final ResourcefulRegistry<Block> FLUID_PIPES = ResourcefulRegistries.create(PIPES);
    public static final ResourcefulRegistry<Block> INDUSTRIAL_LAMPS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SMALL_INDUSTRIAL_LAMPS = ResourcefulRegistries.create(BLOCKS);

    public static final BlockBehaviour.Properties IRON_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(5, 6)
        .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties STEEL_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(5, 12)
        .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties ETRIUM_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(5, 5)
        .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties DESH_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(5, 9)
        .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties OSTRUM_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(5, 16)
        .sound(SoundType.COPPER);

    public static final BlockBehaviour.Properties CALORITE_PROPERTIES = BlockBehaviour.Properties.of()
        .mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
        .requiresCorrectToolForDrops()
        .strength(7, 22)
        .sound(SoundType.NETHERITE_BLOCK);

    public static final RegistryEntry<Block> LAUNCH_PAD = BLOCKS.register("launch_pad", () -> new LaunchPadBlock(STEEL_PROPERTIES.pushReaction(PushReaction.BLOCK)));

    public static final RegistryEntry<Block> STEEL_CABLE = CABLES.register("steel_cable", () -> new PipeBlock(128, PipeBlock.Type.ENERGY, 0.344, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 12).noOcclusion()));
    public static final RegistryEntry<Block> DESH_CABLE = CABLES.register("desh_cable", () -> new PipeBlock(512, PipeBlock.Type.ENERGY, 0.344, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 9).noOcclusion()));
    public static final RegistryEntry<Block> DESH_FLUID_PIPE = FLUID_PIPES.register("desh_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.128f), PipeBlock.Type.FLUID, 0.185, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 9).noOcclusion()));
    public static final RegistryEntry<Block> OSTRUM_FLUID_PIPE = FLUID_PIPES.register("ostrum_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.512f), PipeBlock.Type.FLUID, 0.185, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 16).noOcclusion()));

    public static final RegistryEntry<Block> CABLE_DUCT = CABLES.register("cable_duct", () -> new PipeDuctBlock(256, TransferablePipe.Type.ENERGY, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 12f)));
    public static final RegistryEntry<Block> FLUID_PIPE_DUCT = FLUID_PIPES.register("fluid_pipe_duct", () -> new PipeDuctBlock(FluidHooks.buckets(0.256f), TransferablePipe.Type.FLUID, BlockBehaviour.Properties.of().mapColor(MapColor.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 9)));

    public static final RegistryEntry<Block> COAL_GENERATOR = MACHINES.register("coal_generator", () -> new MachineBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> COMPRESSOR = MACHINES.register("compressor", () -> new MachineBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> ETRIONIC_BLAST_FURNACE = MACHINES.register("etrionic_blast_furnace", () -> new EtrionicBlastFurnaceBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> NASA_WORKBENCH = MACHINES.register("nasa_workbench", () -> new MachineBlock(STEEL_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> FUEL_REFINERY = MACHINES.register("fuel_refinery", () -> new MachineBlock(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> OXYGEN_LOADER = MACHINES.register("oxygen_loader", () -> new MachineBlock(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> SOLAR_PANEL = MACHINES.register("solar_panel", () -> new MachineBlock(DESH_PROPERTIES));
    public static final RegistryEntry<Block> WATER_PUMP = MACHINES.register("water_pump", () -> new WaterPumpBlock(DESH_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = MACHINES.register("oxygen_distributor", () -> new OxygenDistributorBlock(DESH_PROPERTIES));
    public static final RegistryEntry<Block> GRAVITY_NORMALIZER = MACHINES.register("gravity_normalizer", () -> new GravityNormalizerBlock(DESH_PROPERTIES));
    public static final RegistryEntry<Block> ENERGIZER = MACHINES.register("energizer", () -> new EnergizerBlock(OSTRUM_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> CRYO_FREEZER = MACHINES.register("cryo_freezer", () -> new MachineBlock(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OXYGEN_SENSOR = MACHINES.register("oxygen_sensor", () -> new DetectorBlock(OSTRUM_PROPERTIES));

    public static final RegistryEntry<Block> OXYGEN = FLUIDS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> HYDROGEN = FLUIDS.register("hydrogen", () -> new BotariumLiquidBlock(ModFluidProperties.HYDROGEN, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> OIL = FLUIDS.register("oil", () -> new BotariumLiquidBlock(ModFluidProperties.OIL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> FUEL = FLUIDS.register("fuel", () -> new BotariumLiquidBlock(ModFluidProperties.FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRYO_FUEL = FLUIDS.register("cryo_fuel", () -> new CryoFuelLiquidBlock(ModFluidProperties.CRYO_FUEL, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryEntry<Block> WHITE_FLAG = FLAGS.register("white_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> BLACK_FLAG = FLAGS.register("black_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> BLUE_FLAG = FLAGS.register("blue_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> BROWN_FLAG = FLAGS.register("brown_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> CYAN_FLAG = FLAGS.register("cyan_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> GRAY_FLAG = FLAGS.register("gray_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> GREEN_FLAG = FLAGS.register("green_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> LIGHT_BLUE_FLAG = FLAGS.register("light_blue_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> LIGHT_GRAY_FLAG = FLAGS.register("light_gray_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> LIME_FLAG = FLAGS.register("lime_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> MAGENTA_FLAG = FLAGS.register("magenta_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> ORANGE_FLAG = FLAGS.register("orange_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> PINK_FLAG = FLAGS.register("pink_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> PURPLE_FLAG = FLAGS.register("purple_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> RED_FLAG = FLAGS.register("red_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));
    public static final RegistryEntry<Block> YELLOW_FLAG = FLAGS.register("yellow_flag", () -> new FlagBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));

    public static final RegistryEntry<Block> EARTH_GLOBE = GLOBES.register("earth_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> MOON_GLOBE = GLOBES.register("moon_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> MARS_GLOBE = GLOBES.register("mars_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> MERCURY_GLOBE = GLOBES.register("mercury_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> VENUS_GLOBE = GLOBES.register("venus_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> GLACIO_GLOBE = GLOBES.register("glacio_globe", () -> new GlobeBlock(IRON_PROPERTIES.noOcclusion()));

    public static final RegistryEntry<Block> RADIO = BLOCKS.register("radio", () -> new RadioBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1)));

    public static final RegistryEntry<Block> AERONOS_MUSHROOM = BLOCKS.register("aeronos_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), TreeFeatures.HUGE_RED_MUSHROOM));
    public static final RegistryEntry<Block> STROPHAR_MUSHROOM = BLOCKS.register("strophar_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), TreeFeatures.HUGE_RED_MUSHROOM));

    public static final RegistryEntry<Block> CHEESE_BLOCK = CUBES.register("cheese_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPONGE).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryEntry<Block> SKY_STONE = CUBES.register("sky_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> VENT = CUBES.register("vent", () -> new GlassBlock(IRON_PROPERTIES.noOcclusion()));
    public static final RegistryEntry<Block> IRON_FACTORY_BLOCK = CTM_CUBES.register("iron_factory_block", () -> new Block(IRON_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_IRON_BLOCK = CUBES.register("encased_iron_block", () -> new Block(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PLATEBLOCK = CUBES.register("iron_plateblock", () -> new Block(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PANEL = CUBES.register("iron_panel", () -> new Block(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PLATING = CUBES.register("iron_plating", () -> new Block(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PLATING_STAIRS = STAIRS.register("iron_plating_stairs", () -> new StairBlock(IRON_PLATING.get().defaultBlockState(), IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PLATING_SLAB = SLABS.register("iron_plating_slab", () -> new SlabBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PILLAR = PILLARS.register("iron_pillar", () -> new RotatedPillarBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> GLOWING_IRON_PILLAR = PILLARS.register("glowing_iron_pillar", () -> new RotatedPillarBlock(IRON_PROPERTIES.lightLevel(state -> 15).mapColor(MapColor.METAL)));
    public static final RegistryEntry<Block> MARKED_IRON_PILLAR = PILLARS.register("marked_iron_pillar", () -> new RotatedPillarBlock(IRON_PROPERTIES));
    public static final RegistryEntry<Block> IRON_PLATING_BUTTON = BUTTONS.register("iron_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).mapColor(MapColor.METAL), BlockSetType.IRON, 20, false) {});
    public static final RegistryEntry<Block> IRON_PLATING_PRESSURE_PLATE = PRESSURE_PLATES.register("iron_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.METAL), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> IRON_SLIDING_DOOR = SIMPLE_SLIDING_DOORS.register("iron_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(6).mapColor(MapColor.METAL)));

    public static final RegistryEntry<Block> STEEL_FACTORY_BLOCK = CTM_CUBES.register("steel_factory_block", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_STEEL_BLOCK = CUBES.register("encased_steel_block", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PLATEBLOCK = CUBES.register("steel_plateblock", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PANEL = CUBES.register("steel_panel", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_BLOCK = CUBES.register("steel_block", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PLATING = CUBES.register("steel_plating", () -> new Block(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PLATING_STAIRS = STAIRS.register("steel_plating_stairs", () -> new StairBlock(STEEL_PLATING.get().defaultBlockState(), STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PLATING_SLAB = SLABS.register("steel_plating_slab", () -> new SlabBlock(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> STEEL_PILLAR = PILLARS.register("steel_pillar", () -> new RotatedPillarBlock(STEEL_PROPERTIES));
    public static final RegistryEntry<Block> GLOWING_STEEL_PILLAR = PILLARS.register("glowing_steel_pillar", () -> new RotatedPillarBlock(STEEL_PROPERTIES.lightLevel(state -> 15).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PLATING_BUTTON = BUTTONS.register("steel_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).mapColor(MapColor.COLOR_GRAY), BlockSetType.IRON, 20, false) {});
    public static final RegistryEntry<Block> STEEL_PLATING_PRESSURE_PLATE = PRESSURE_PLATES.register("steel_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.COLOR_GRAY), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> STEEL_SLIDING_DOOR = SIMPLE_SLIDING_DOORS.register("steel_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(12).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> AIRLOCK = SLIDING_DOORS.register("airlock", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(18).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> REINFORCED_DOOR = SLIDING_DOORS.register("reinforced_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).strength(25, 40).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_DOOR = BLOCKS.register("steel_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).mapColor(MapColor.COLOR_GRAY), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> STEEL_TRAPDOOR = BLOCKS.register("steel_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).mapColor(MapColor.COLOR_GRAY), BlockSetType.IRON) {});

    public static final RegistryEntry<Block> ETRIUM_FACTORY_BLOCK = CTM_CUBES.register("etrium_factory_block", () -> new Block(ETRIUM_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_ETRIUM_BLOCK = CUBES.register("encased_etrium_block", () -> new Block(ETRIUM_PROPERTIES));
    public static final RegistryEntry<Block> ETRIUM_PLATEBLOCK = CUBES.register("etrium_plateblock", () -> new Block(ETRIUM_PROPERTIES));
    public static final RegistryEntry<Block> ETRIUM_PANEL = CUBES.register("etrium_panel", () -> new Block(ETRIUM_PROPERTIES));
    public static final RegistryEntry<Block> ETRIUM_BLOCK = CUBES.register("etrium_block", () -> new Block(ETRIUM_PROPERTIES));

    public static final RegistryEntry<Block> DESH_FACTORY_BLOCK = CTM_CUBES.register("desh_factory_block", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_DESH_BLOCK = CUBES.register("encased_desh_block", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_PLATEBLOCK = CUBES.register("desh_plateblock", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_PANEL = CUBES.register("desh_panel", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_BLOCK = CUBES.register("desh_block", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> RAW_DESH_BLOCK = CUBES.register("raw_desh_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING = CUBES.register("desh_plating", () -> new Block(DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_PLATING_STAIRS = STAIRS.register("desh_plating_stairs", () -> new StairBlock(DESH_PLATING.get().defaultBlockState(), DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_PLATING_SLAB = SLABS.register("desh_plating_slab", () -> new SlabBlock(DESH_PROPERTIES));
    public static final RegistryEntry<Block> DESH_PILLAR = PILLARS.register("desh_pillar", () -> new RotatedPillarBlock(DESH_PROPERTIES));
    public static final RegistryEntry<Block> GLOWING_DESH_PILLAR = PILLARS.register("glowing_desh_pillar", () -> new RotatedPillarBlock(DESH_PROPERTIES.lightLevel(state -> 15).mapColor(MapColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING_BUTTON = BUTTONS.register("desh_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).mapColor(MapColor.COLOR_ORANGE), BlockSetType.IRON, 20, false) {});
    public static final RegistryEntry<Block> DESH_PLATING_PRESSURE_PLATE = PRESSURE_PLATES.register("desh_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.COLOR_ORANGE), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> DESH_SLIDING_DOOR = SIMPLE_SLIDING_DOORS.register("desh_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(9).mapColor(MapColor.COLOR_ORANGE)));

    public static final RegistryEntry<Block> OSTRUM_FACTORY_BLOCK = CTM_CUBES.register("ostrum_factory_block", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_OSTRUM_BLOCK = CUBES.register("encased_ostrum_block", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_PLATEBLOCK = CUBES.register("ostrum_plateblock", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_PANEL = CUBES.register("ostrum_panel", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_BLOCK = CUBES.register("ostrum_block", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> RAW_OSTRUM_BLOCK = CUBES.register("raw_ostrum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING = CUBES.register("ostrum_plating", () -> new Block(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_PLATING_STAIRS = STAIRS.register("ostrum_plating_stairs", () -> new StairBlock(OSTRUM_PLATING.get().defaultBlockState(), OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_PLATING_SLAB = SLABS.register("ostrum_plating_slab", () -> new SlabBlock(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> OSTRUM_PILLAR = PILLARS.register("ostrum_pillar", () -> new RotatedPillarBlock(OSTRUM_PROPERTIES));
    public static final RegistryEntry<Block> GLOWING_OSTRUM_PILLAR = PILLARS.register("glowing_ostrum_pillar", () -> new RotatedPillarBlock(OSTRUM_PROPERTIES.lightLevel(state -> 15).mapColor(MapColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING_BUTTON = BUTTONS.register("ostrum_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).mapColor(MapColor.COLOR_PURPLE), BlockSetType.IRON, 20, false) {});
    public static final RegistryEntry<Block> OSTRUM_PLATING_PRESSURE_PLATE = PRESSURE_PLATES.register("ostrum_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.COLOR_PURPLE), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> OSTRUM_SLIDING_DOOR = SIMPLE_SLIDING_DOORS.register("ostrum_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(16).mapColor(MapColor.COLOR_PURPLE)));

    public static final RegistryEntry<Block> CALORITE_FACTORY_BLOCK = CTM_CUBES.register("calorite_factory_block", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> ENCASED_CALORITE_BLOCK = CUBES.register("encased_calorite_block", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_PLATEBLOCK = CUBES.register("calorite_plateblock", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_PANEL = CUBES.register("calorite_panel", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_BLOCK = CUBES.register("calorite_block", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> RAW_CALORITE_BLOCK = CUBES.register("raw_calorite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).mapColor(MapColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING = CUBES.register("calorite_plating", () -> new Block(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_PLATING_STAIRS = STAIRS.register("calorite_plating_stairs", () -> new StairBlock(CALORITE_PLATING.get().defaultBlockState(), CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_PLATING_SLAB = SLABS.register("calorite_plating_slab", () -> new SlabBlock(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> CALORITE_PILLAR = PILLARS.register("calorite_pillar", () -> new RotatedPillarBlock(CALORITE_PROPERTIES));
    public static final RegistryEntry<Block> GLOWING_CALORITE_PILLAR = PILLARS.register("glowing_calorite_pillar", () -> new RotatedPillarBlock(CALORITE_PROPERTIES.lightLevel(state -> 15).mapColor(MapColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING_BUTTON = BUTTONS.register("calorite_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).mapColor(MapColor.COLOR_RED), BlockSetType.IRON, 20, false) {});
    public static final RegistryEntry<Block> CALORITE_PLATING_PRESSURE_PLATE = PRESSURE_PLATES.register("calorite_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.COLOR_RED), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> CALORITE_SLIDING_DOOR = SIMPLE_SLIDING_DOORS.register("calorite_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).explosionResistance(22).mapColor(MapColor.COLOR_RED)));

    public static final RegistryEntry<Block> BLACK_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("black_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> BLUE_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("blue_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> BROWN_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("brown_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> CYAN_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("cyan_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> GRAY_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("gray_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> GREEN_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("green_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> LIGHT_BLUE_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("light_blue_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> LIGHT_GRAY_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("light_gray_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> LIME_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("lime_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> MAGENTA_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("magenta_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> ORANGE_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("orange_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> PINK_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("pink_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> PURPLE_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("purple_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> RED_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("red_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> WHITE_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("white_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));
    public static final RegistryEntry<Block> YELLOW_INDUSTRIAL_LAMP = INDUSTRIAL_LAMPS.register("yellow_industrial_lamp", () -> new IndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 12)));

    public static final RegistryEntry<Block> SMALL_BLACK_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_black_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_BLUE_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_blue_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_BROWN_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_brown_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_CYAN_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_cyan_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_GRAY_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_gray_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_GREEN_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_green_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_LIGHT_BLUE_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_light_blue_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_LIGHT_GRAY_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_light_gray_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_LIME_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_lime_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_MAGENTA_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_magenta_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_ORANGE_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_orange_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_PINK_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_pink_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_PURPLE_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_purple_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_RED_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_red_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_WHITE_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_white_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));
    public static final RegistryEntry<Block> SMALL_YELLOW_INDUSTRIAL_LAMP = SMALL_INDUSTRIAL_LAMPS.register("small_yellow_industrial_lamp", () -> new SmallIndustrialLampBlock(STEEL_PROPERTIES.lightLevel(state -> 8)));

    public static final RegistryEntry<Block> MOON_SAND = CUBES.register("moon_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE = CUBES.register("moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_DEEPSLATE = CUBE_COLUMNS.register("moon_deepslate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));
    public static final RegistryEntry<Block> MOON_STONE_STAIRS = STAIRS.register("moon_stone_stairs", () -> new StairBlock(MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_SLAB = SLABS.register("moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE = CUBES.register("moon_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_STAIRS = STAIRS.register("moon_cobblestone_stairs", () -> new StairBlock(MOON_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_SLAB = SLABS.register("moon_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICKS = CUBES.register("moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_STAIRS = STAIRS.register("moon_stone_brick_stairs", () -> new StairBlock(MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_SLAB = SLABS.register("moon_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CRACKED_MOON_STONE_BRICKS = CUBES.register("cracked_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_BRICKS = CUBES.register("chiseled_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_STAIRS = STAIRS.register("chiseled_moon_stone_stairs", () -> new StairBlock(CHISELED_MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_SLAB = SLABS.register("chiseled_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE = CUBES.register("polished_moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_STAIRS = STAIRS.register("polished_moon_stone_stairs", () -> new StairBlock(POLISHED_MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_SLAB = SLABS.register("polished_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_PILLAR = PILLARS.register("moon_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_WALL = WALLS.register("moon_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));

    public static final RegistryEntry<Block> MOON_CHEESE_ORE = CUBES.register("moon_cheese_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> MOON_DESH_ORE = CUBES.register("moon_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_DESH_ORE = CUBES.register("deepslate_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final RegistryEntry<Block> MOON_IRON_ORE = CUBES.register("moon_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> MOON_ICE_SHARD_ORE = CUBES.register("moon_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final RegistryEntry<Block> DEEPSLATE_ICE_SHARD_ORE = CUBES.register("deepslate_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE), UniformInt.of(3, 6)));

    public static final RegistryEntry<Block> AERONOS_CAP = CUBES.register("aeronos_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_STEM = BLOCKS.register("aeronos_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_PLANKS = CUBES.register("aeronos_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_STAIRS = STAIRS.register("aeronos_stairs", () -> new StairBlock(AERONOS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_FENCE = BLOCKS.register("aeronos_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_FENCE_GATE = BLOCKS.register("aeronos_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6), WoodType.OAK));
    public static final RegistryEntry<Block> AERONOS_SLAB = SLABS.register("aeronos_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_LADDER = BLOCKS.register("aeronos_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_DOOR = BLOCKS.register("aeronos_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6), BlockSetType.OAK) {});
    public static final RegistryEntry<Block> AERONOS_TRAPDOOR = BLOCKS.register("aeronos_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6), BlockSetType.OAK) {});

    public static final RegistryEntry<Block> STROPHAR_CAP = CUBES.register("strophar_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_STEM = BLOCKS.register("strophar_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_PLANKS = CUBES.register("strophar_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_STAIRS = STAIRS.register("strophar_stairs", () -> new StairBlock(STROPHAR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_FENCE = BLOCKS.register("strophar_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_FENCE_GATE = BLOCKS.register("strophar_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6), WoodType.OAK));
    public static final RegistryEntry<Block> STROPHAR_SLAB = SLABS.register("strophar_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_LADDER = BLOCKS.register("strophar_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_DOOR = BLOCKS.register("strophar_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6), BlockSetType.OAK) {});
    public static final RegistryEntry<Block> STROPHAR_TRAPDOOR = BLOCKS.register("strophar_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6), BlockSetType.OAK) {});

    public static final RegistryEntry<Block> MARS_SAND = CUBES.register("mars_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE = CUBES.register("mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_STAIRS = STAIRS.register("mars_stone_stairs", () -> new StairBlock(MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_SLAB = SLABS.register("mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE = CUBES.register("mars_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_STAIRS = STAIRS.register("mars_cobblestone_stairs", () -> new StairBlock(MARS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_SLAB = SLABS.register("mars_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICKS = CUBES.register("mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_STAIRS = STAIRS.register("mars_stone_brick_stairs", () -> new StairBlock(MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_SLAB = SLABS.register("mars_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CRACKED_MARS_STONE_BRICKS = CUBES.register("cracked_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_BRICKS = CUBES.register("chiseled_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_STAIRS = STAIRS.register("chiseled_mars_stone_stairs", () -> new StairBlock(CHISELED_MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_SLAB = SLABS.register("chiseled_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE = CUBES.register("polished_mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_STAIRS = STAIRS.register("polished_mars_stone_stairs", () -> new StairBlock(POLISHED_MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_SLAB = SLABS.register("polished_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_PILLAR = PILLARS.register("mars_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_WALL = WALLS.register("mars_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_RED)));

    public static final RegistryEntry<Block> CONGLOMERATE = CUBES.register("conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_CONGLOMERATE = CUBES.register("polished_conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_RED)));

    public static final RegistryEntry<Block> MARS_IRON_ORE = CUBES.register("mars_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> MARS_DIAMOND_ORE = CUBES.register("mars_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final RegistryEntry<Block> MARS_OSTRUM_ORE = CUBES.register("mars_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_OSTRUM_ORE = CUBES.register("deepslate_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final RegistryEntry<Block> MARS_ICE_SHARD_ORE = CUBES.register("mars_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));

    public static final RegistryEntry<Block> MERCURY_STONE = CUBES.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_STAIRS = STAIRS.register("mercury_stone_stairs", () -> new StairBlock(MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_SLAB = SLABS.register("mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE = CUBES.register("mercury_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_STAIRS = STAIRS.register("mercury_cobblestone_stairs", () -> new StairBlock(MERCURY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_SLAB = SLABS.register("mercury_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICKS = CUBES.register("mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_STAIRS = STAIRS.register("mercury_stone_brick_stairs", () -> new StairBlock(MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_SLAB = SLABS.register("mercury_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CRACKED_MERCURY_STONE_BRICKS = CUBES.register("cracked_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_BRICKS = CUBES.register("chiseled_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_STAIRS = STAIRS.register("chiseled_mercury_stone_stairs", () -> new StairBlock(CHISELED_MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_SLAB = SLABS.register("chiseled_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE = CUBES.register("polished_mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_STAIRS = STAIRS.register("polished_mercury_stone_stairs", () -> new StairBlock(POLISHED_MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_SLAB = SLABS.register("polished_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_PILLAR = PILLARS.register("mercury_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_WALL = WALLS.register("mercury_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static final RegistryEntry<Block> MERCURY_IRON_ORE = CUBES.register("mercury_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryEntry<Block> VENUS_SAND = CUBES.register("venus_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE = CUBES.register("venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_STAIRS = STAIRS.register("venus_stone_stairs", () -> new StairBlock(VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_SLAB = SLABS.register("venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE = CUBES.register("venus_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_STAIRS = STAIRS.register("venus_cobblestone_stairs", () -> new StairBlock(VENUS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_SLAB = SLABS.register("venus_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICKS = CUBES.register("venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_STAIRS = STAIRS.register("venus_stone_brick_stairs", () -> new StairBlock(VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_SLAB = SLABS.register("venus_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CRACKED_VENUS_STONE_BRICKS = CUBES.register("cracked_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_BRICKS = CUBES.register("chiseled_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_STAIRS = STAIRS.register("chiseled_venus_stone_stairs", () -> new StairBlock(CHISELED_VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_SLAB = SLABS.register("chiseled_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE = CUBES.register("polished_venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_STAIRS = STAIRS.register("polished_venus_stone_stairs", () -> new StairBlock(POLISHED_VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_SLAB = SLABS.register("polished_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_PILLAR = PILLARS.register("venus_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_WALL = WALLS.register("venus_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.TERRACOTTA_ORANGE)));

    public static final RegistryEntry<Block> VENUS_SANDSTONE = CUBES.register("venus_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICKS = CUBES.register("venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICK_STAIRS = STAIRS.register("venus_sandstone_brick_stairs", () -> new StairBlock(VENUS_SANDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICK_SLAB = SLABS.register("venus_sandstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CRACKED_VENUS_SANDSTONE_BRICKS = CUBES.register("cracked_venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_ORANGE)));

    public static final RegistryEntry<Block> INFERNAL_SPIRE_BLOCK = CUBE_COLUMNS.register("infernal_spire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT).mapColor(MapColor.COLOR_RED)));

    public static final RegistryEntry<Block> VENUS_COAL_ORE = CUBES.register("venus_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> VENUS_GOLD_ORE = CUBES.register("venus_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> VENUS_DIAMOND_ORE = CUBES.register("venus_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final RegistryEntry<Block> VENUS_CALORITE_ORE = CUBES.register("venus_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_CALORITE_ORE = CUBES.register("deepslate_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));

    public static final RegistryEntry<Block> GLACIO_STONE = CUBES.register("glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_STAIRS = STAIRS.register("glacio_stone_stairs", () -> new StairBlock(GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_SLAB = SLABS.register("glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE = CUBES.register("glacio_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_STAIRS = STAIRS.register("glacio_cobblestone_stairs", () -> new StairBlock(GLACIO_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_SLAB = SLABS.register("glacio_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICKS = CUBES.register("glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_STAIRS = STAIRS.register("glacio_stone_brick_stairs", () -> new StairBlock(GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_SLAB = SLABS.register("glacio_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> CRACKED_GLACIO_STONE_BRICKS = CUBES.register("cracked_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_BRICKS = CUBES.register("chiseled_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_STAIRS = STAIRS.register("chiseled_glacio_stone_stairs", () -> new StairBlock(CHISELED_GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_SLAB = SLABS.register("chiseled_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE = CUBES.register("polished_glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_STAIRS = STAIRS.register("polished_glacio_stone_stairs", () -> new StairBlock(POLISHED_GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_SLAB = SLABS.register("polished_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_PILLAR = PILLARS.register("glacio_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_WALL = WALLS.register("glacio_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.ICE)));

    public static final RegistryEntry<Block> PERMAFROST = CUBE_COLUMNS.register("permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICKS = CUBES.register("permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_STAIRS = STAIRS.register("permafrost_brick_stairs", () -> new StairBlock(PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_SLAB = SLABS.register("permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CRACKED_PERMAFROST_BRICKS = CUBES.register("cracked_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_TILES = CUBES.register("permafrost_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICKS = CUBES.register("chiseled_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICK_STAIRS = STAIRS.register("chiseled_permafrost_brick_stairs", () -> new StairBlock(CHISELED_PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICK_SLAB = SLABS.register("chiseled_permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST = CUBES.register("polished_permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST_STAIRS = STAIRS.register("polished_permafrost_stairs", () -> new StairBlock(POLISHED_PERMAFROST.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_PERMAFROST.get()).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST_SLAB = SLABS.register("polished_permafrost_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_PILLAR = PILLARS.register("permafrost_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_WALL = WALLS.register("permafrost_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryEntry<Block> GLACIO_ICE_SHARD_ORE = CUBES.register("glacio_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final RegistryEntry<Block> GLACIO_COAL_ORE = CUBES.register("glacio_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> GLACIO_COPPER_ORE = CUBES.register("glacio_copper_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> GLACIO_IRON_ORE = CUBES.register("glacio_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> GLACIO_LAPIS_ORE = CUBES.register("glacio_lapis_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));

    public static final RegistryEntry<Block> GLACIAN_LOG = PILLARS.register("glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.CLAY).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> STRIPPED_GLACIAN_LOG = PILLARS.register("stripped_glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.CLAY).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_LEAVES = CUBES.register("glacian_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion().friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_PLANKS = CUBES.register("glacian_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_STAIRS = STAIRS.register("glacian_stairs", () -> new StairBlock(GLACIAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_SLAB = SLABS.register("glacian_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_DOOR = BLOCKS.register("glacian_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).friction(0.5f).mapColor(MapColor.COLOR_PINK), BlockSetType.OAK) {});
    public static final RegistryEntry<Block> GLACIAN_TRAPDOOR = BLOCKS.register("glacian_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).friction(0.5f).mapColor(MapColor.COLOR_PINK), BlockSetType.OAK) {});
    public static final RegistryEntry<Block> GLACIAN_FENCE = BLOCKS.register("glacian_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).friction(0.5f).friction(0.5f).mapColor(MapColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_FENCE_GATE = BLOCKS.register("glacian_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).friction(0.5f).mapColor(MapColor.COLOR_PINK), WoodType.OAK) {});
    public static final RegistryEntry<Block> GLACIAN_BUTTON = BUTTONS.register("glacian_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).mapColor(MapColor.COLOR_PINK), BlockSetType.OAK, 30, true) {});
    public static final RegistryEntry<Block> GLACIAN_PRESSURE_PLATE = PRESSURE_PLATES.register("glacian_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).friction(0.5f).mapColor(MapColor.COLOR_PINK), BlockSetType.IRON) {});
    public static final RegistryEntry<Block> GLACIAN_FUR = CUBES.register("glacian_fur", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).mapColor(MapColor.COLOR_PINK)));
}