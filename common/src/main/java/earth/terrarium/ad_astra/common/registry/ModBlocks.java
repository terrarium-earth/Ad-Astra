package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.chest.CustomChestBlock;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.block.flag.FlagBlock;
import earth.terrarium.ad_astra.common.block.fluid.CryoFuelLiquidBlock;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.launchpad.LaunchPad;
import earth.terrarium.ad_astra.common.block.machine.*;
import earth.terrarium.ad_astra.common.block.pipe.PipeBlock;
import earth.terrarium.ad_astra.common.block.pipe.PipeDuctBlock;
import earth.terrarium.ad_astra.common.block.sign.CustomStandingSignBlock;
import earth.terrarium.ad_astra.common.block.sign.CustomWallSignBlock;
import earth.terrarium.ad_astra.common.block.torch.ExtinguishedLanternBlock;
import earth.terrarium.ad_astra.common.block.torch.ExtinguishedTorchBlock;
import earth.terrarium.ad_astra.common.block.torch.WallExtinguishedTorchBlock;
import earth.terrarium.ad_astra.mixin.WoodTypeInvoker;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ModBlocks {
    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, AdAstra.MOD_ID);

    public static final ResourcefulRegistry<Block> FLAGS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> GLOBES = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SLIDING_DOORS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> SIGNS = ResourcefulRegistries.create(BLOCKS);
    public static final ResourcefulRegistry<Block> STANDING_SIGNS = ResourcefulRegistries.create(SIGNS);
    public static final ResourcefulRegistry<Block> WALL_SIGNS = ResourcefulRegistries.create(SIGNS);
    public static final ResourcefulRegistry<Block> CHESTS = ResourcefulRegistries.create(BLOCKS);

    public static final WoodType GLACIAN_SIGN_TYPE = WoodTypeInvoker.adastra_invokeRegister(WoodTypeInvoker.adastra_init(ArchitecturyTarget.getCurrentTarget().equals("forge") ? "ad_astra:glacian" : "glacian"));

    public static final RegistryEntry<Block> LAUNCH_PAD = BLOCKS.register("launch_pad", () -> new LaunchPad(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));

    public static final RegistryEntry<Block> STEEL_CABLE = BLOCKS.register("steel_cable", () -> new PipeBlock(1012, 0.344, PipeBlock.PipeType.CABLE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));
    public static final RegistryEntry<Block> DESH_CABLE = BLOCKS.register("desh_cable", () -> new PipeBlock(4096, 0.344, PipeBlock.PipeType.CABLE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));
    public static final RegistryEntry<Block> DESH_FLUID_PIPE = BLOCKS.register("desh_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.128f), 0.185, PipeBlock.PipeType.FLUID_PIPE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));
    public static final RegistryEntry<Block> OSTRUM_FLUID_PIPE = BLOCKS.register("ostrum_fluid_pipe", () -> new PipeBlock(FluidHooks.buckets(0.256f), 0.185, PipeBlock.PipeType.FLUID_PIPE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));

    public static final RegistryEntry<Block> CABLE_DUCT = BLOCKS.register("cable_duct", () -> new PipeDuctBlock(1012, PipeBlock.PipeType.CABLE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));
    public static final RegistryEntry<Block> FLUID_PIPE_DUCT = BLOCKS.register("fluid_pipe_duct", () -> new PipeDuctBlock(FluidHooks.buckets(0.256f), PipeBlock.PipeType.FLUID_PIPE, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(0.5f, 0.5f)));

    public static final RegistryEntry<Block> FUEL_REFINERY = BLOCKS.register("fuel_refinery", () -> new FuelRefineryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> COMPRESSOR = BLOCKS.register("compressor", () -> new CompressorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> COAL_GENERATOR = BLOCKS.register("coal_generator", () -> new CoalGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_LOADER = BLOCKS.register("oxygen_loader", () -> new OxygenLoaderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> SOLAR_PANEL = BLOCKS.register("solar_panel", () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> NASA_WORKBENCH = BLOCKS.register("nasa_workbench", () -> new NasaWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> OXYGEN_DISTRIBUTOR = BLOCKS.register("oxygen_distributor", () -> new OxygenDistributorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> WATER_PUMP = BLOCKS.register("water_pump", () -> new WaterPumpBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> ENERGIZER = BLOCKS.register("energizer", () -> new EnergizerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> CRYO_FREEZER = BLOCKS.register("cryo_freezer", () -> new CryoFreezerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryEntry<Block> OXYGEN_SENSOR = BLOCKS.register("oxygen_sensor", () -> new OxygenSensorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

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
    public static final RegistryEntry<Block> MERCURY_GLOBE = GLOBES.register("mercury_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> VENUS_GLOBE = GLOBES.register("venus_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));
    public static final RegistryEntry<Block> GLACIO_GLOBE = GLOBES.register("glacio_globe", () -> new GlobeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion()));

    public static final RegistryEntry<Block> AERONOS_MUSHROOM = BLOCKS.register("aeronos_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), TreeFeatures.HUGE_RED_MUSHROOM));
    public static final RegistryEntry<Block> STROPHAR_MUSHROOM = BLOCKS.register("strophar_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), TreeFeatures.HUGE_RED_MUSHROOM));

    public static final RegistryEntry<Block> EXTINGUISHED_TORCH = BLOCKS.register("extinguished_torch", () -> new ExtinguishedTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH)));
    public static final RegistryEntry<Block> WALL_EXTINGUISHED_TORCH = BLOCKS.register("wall_extinguished_torch", () -> new WallExtinguishedTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH)));
    public static final RegistryEntry<Block> EXTINGUISHED_LANTERN = BLOCKS.register("extinguished_lantern", () -> new ExtinguishedLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));

    public static final RegistryEntry<Block> CHEESE_BLOCK = BLOCKS.register("cheese_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPONGE).sound(SoundType.SLIME_BLOCK)));
    public static final RegistryEntry<Block> SKY_STONE = BLOCKS.register("sky_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryEntry<Block> IRON_PLATING = BLOCKS.register("iron_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> IRON_PLATING_STAIRS = BLOCKS.register("iron_plating_stairs", () -> new StairBlock(IRON_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> IRON_PLATING_SLAB = BLOCKS.register("iron_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> IRON_PILLAR = BLOCKS.register("iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> GLOWING_IRON_PILLAR = BLOCKS.register("glowing_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> MARKED_IRON_PILLAR = BLOCKS.register("marked_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.METAL)));
    public static final RegistryEntry<Block> IRON_PLATING_BUTTON = BLOCKS.register("iron_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).color(MaterialColor.METAL), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> IRON_PLATING_PRESSURE_PLATE = BLOCKS.register("iron_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).color(MaterialColor.METAL), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> IRON_SLIDING_DOOR = SLIDING_DOORS.register("iron_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.METAL)));

    public static final RegistryEntry<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PLATING = BLOCKS.register("steel_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PLATING_STAIRS = BLOCKS.register("steel_plating_stairs", () -> new StairBlock(STEEL_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PLATING_SLAB = BLOCKS.register("steel_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PILLAR = BLOCKS.register("steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> GLOWING_STEEL_PILLAR = BLOCKS.register("glowing_steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_PLATING_BUTTON = BLOCKS.register("steel_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).color(MaterialColor.COLOR_GRAY), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> STEEL_PLATING_PRESSURE_PLATE = BLOCKS.register("steel_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).color(MaterialColor.COLOR_GRAY), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> STEEL_SLIDING_DOOR = SLIDING_DOORS.register("steel_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> AIRLOCK = SLIDING_DOORS.register("airlock", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> REINFORCED_DOOR = SLIDING_DOORS.register("reinforced_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).strength(25.0f, 40.0f).color(MaterialColor.COLOR_GRAY)));
    public static final RegistryEntry<Block> STEEL_DOOR = BLOCKS.register("steel_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_GRAY), SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN));
    public static final RegistryEntry<Block> STEEL_TRAPDOOR = BLOCKS.register("steel_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR).color(MaterialColor.COLOR_GRAY), SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN));

    public static final RegistryEntry<Block> DESH_BLOCK = BLOCKS.register("desh_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> RAW_DESH_BLOCK = BLOCKS.register("raw_desh_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING = BLOCKS.register("desh_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING_STAIRS = BLOCKS.register("desh_plating_stairs", () -> new StairBlock(DESH_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING_SLAB = BLOCKS.register("desh_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PILLAR = BLOCKS.register("desh_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> GLOWING_DESH_PILLAR = BLOCKS.register("glowing_desh_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryEntry<Block> DESH_PLATING_BUTTON = BLOCKS.register("desh_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).color(MaterialColor.COLOR_ORANGE), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> DESH_PLATING_PRESSURE_PLATE = BLOCKS.register("desh_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).color(MaterialColor.COLOR_ORANGE), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> DESH_SLIDING_DOOR = SLIDING_DOORS.register("desh_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_ORANGE)));

    public static final RegistryEntry<Block> OSTRUM_BLOCK = BLOCKS.register("ostrum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> RAW_OSTRUM_BLOCK = BLOCKS.register("raw_ostrum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING = BLOCKS.register("ostrum_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING_STAIRS = BLOCKS.register("ostrum_plating_stairs", () -> new StairBlock(OSTRUM_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING_SLAB = BLOCKS.register("ostrum_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PILLAR = BLOCKS.register("ostrum_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> GLOWING_OSTRUM_PILLAR = BLOCKS.register("glowing_ostrum_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryEntry<Block> OSTRUM_PLATING_BUTTON = BLOCKS.register("ostrum_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).color(MaterialColor.COLOR_PURPLE), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> OSTRUM_PLATING_PRESSURE_PLATE = BLOCKS.register("ostrum_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).color(MaterialColor.COLOR_PURPLE), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> OSTRUM_SLIDING_DOOR = SLIDING_DOORS.register("ostrum_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_PURPLE)));

    public static final RegistryEntry<Block> CALORITE_BLOCK = BLOCKS.register("calorite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> RAW_CALORITE_BLOCK = BLOCKS.register("raw_calorite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING = BLOCKS.register("calorite_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING_STAIRS = BLOCKS.register("calorite_plating_stairs", () -> new StairBlock(CALORITE_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING_SLAB = BLOCKS.register("calorite_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PILLAR = BLOCKS.register("calorite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> GLOWING_CALORITE_PILLAR = BLOCKS.register("glowing_calorite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15).color(MaterialColor.COLOR_RED)));
    public static final RegistryEntry<Block> CALORITE_PLATING_BUTTON = BLOCKS.register("calorite_plating_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON).color(MaterialColor.COLOR_RED), 20, false, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> CALORITE_PLATING_PRESSURE_PLATE = BLOCKS.register("calorite_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).color(MaterialColor.COLOR_RED), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> CALORITE_SLIDING_DOOR = SLIDING_DOORS.register("calorite_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).color(MaterialColor.COLOR_RED)));

    public static final RegistryEntry<Block> MOON_SAND = BLOCKS.register("moon_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE = BLOCKS.register("moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_STAIRS = BLOCKS.register("moon_stone_stairs", () -> new StairBlock(MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_SLAB = BLOCKS.register("moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE = BLOCKS.register("moon_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_STAIRS = BLOCKS.register("moon_cobblestone_stairs", () -> new StairBlock(MOON_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_COBBLESTONE_SLAB = BLOCKS.register("moon_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICKS = BLOCKS.register("moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_STAIRS = BLOCKS.register("moon_stone_brick_stairs", () -> new StairBlock(MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_SLAB = BLOCKS.register("moon_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CRACKED_MOON_STONE_BRICKS = BLOCKS.register("cracked_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_BRICKS = BLOCKS.register("chiseled_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_STAIRS = BLOCKS.register("chiseled_moon_stone_stairs", () -> new StairBlock(CHISELED_MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> CHISELED_MOON_STONE_SLAB = BLOCKS.register("chiseled_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE = BLOCKS.register("polished_moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_STAIRS = BLOCKS.register("polished_moon_stone_stairs", () -> new StairBlock(POLISHED_MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> POLISHED_MOON_STONE_SLAB = BLOCKS.register("polished_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_PILLAR = BLOCKS.register("moon_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));
    public static final RegistryEntry<Block> MOON_STONE_BRICK_WALL = BLOCKS.register("moon_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.TERRACOTTA_LIGHT_GRAY)));

    public static final RegistryEntry<Block> MOON_CHEESE_ORE = BLOCKS.register("moon_cheese_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> MOON_DESH_ORE = BLOCKS.register("moon_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_DESH_ORE = BLOCKS.register("deepslate_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final RegistryEntry<Block> MOON_IRON_ORE = BLOCKS.register("moon_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> MOON_ICE_SHARD_ORE = BLOCKS.register("moon_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final RegistryEntry<Block> DEEPSLATE_ICE_SHARD_ORE = BLOCKS.register("deepslate_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE), UniformInt.of(3, 6)));

    public static final RegistryEntry<Block> AERONOS_CAP = BLOCKS.register("aeronos_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_STEM = BLOCKS.register("aeronos_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_PLANKS = BLOCKS.register("aeronos_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_STAIRS = BLOCKS.register("aeronos_stairs", () -> new StairBlock(AERONOS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_FENCE = BLOCKS.register("aeronos_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_FENCE_GATE = BLOCKS.register("aeronos_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryEntry<Block> AERONOS_SLAB = BLOCKS.register("aeronos_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_LADDER = BLOCKS.register("aeronos_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_CHEST = CHESTS.register("aeronos_chest", () -> new CustomChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> AERONOS_DOOR = BLOCKS.register("aeronos_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));
    public static final RegistryEntry<Block> AERONOS_TRAPDOOR = BLOCKS.register("aeronos_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));

    public static final RegistryEntry<Block> STROPHAR_CAP = BLOCKS.register("strophar_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_STEM = BLOCKS.register("strophar_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_PLANKS = BLOCKS.register("strophar_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_STAIRS = BLOCKS.register("strophar_stairs", () -> new StairBlock(STROPHAR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_FENCE = BLOCKS.register("strophar_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_FENCE_GATE = BLOCKS.register("strophar_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryEntry<Block> STROPHAR_SLAB = BLOCKS.register("strophar_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_LADDER = BLOCKS.register("strophar_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_CHEST = CHESTS.register("strophar_chest", () -> new CustomChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final RegistryEntry<Block> STROPHAR_DOOR = BLOCKS.register("strophar_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));
    public static final RegistryEntry<Block> STROPHAR_TRAPDOOR = BLOCKS.register("strophar_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));

    public static final RegistryEntry<Block> MARS_SAND = BLOCKS.register("mars_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE = BLOCKS.register("mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_STAIRS = BLOCKS.register("mars_stone_stairs", () -> new StairBlock(MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_SLAB = BLOCKS.register("mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE = BLOCKS.register("mars_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_STAIRS = BLOCKS.register("mars_cobblestone_stairs", () -> new StairBlock(MARS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_COBBLESTONE_SLAB = BLOCKS.register("mars_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICKS = BLOCKS.register("mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_STAIRS = BLOCKS.register("mars_stone_brick_stairs", () -> new StairBlock(MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_SLAB = BLOCKS.register("mars_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CRACKED_MARS_STONE_BRICKS = BLOCKS.register("cracked_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_BRICKS = BLOCKS.register("chiseled_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_STAIRS = BLOCKS.register("chiseled_mars_stone_stairs", () -> new StairBlock(CHISELED_MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> CHISELED_MARS_STONE_SLAB = BLOCKS.register("chiseled_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE = BLOCKS.register("polished_mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_STAIRS = BLOCKS.register("polished_mars_stone_stairs", () -> new StairBlock(POLISHED_MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_MARS_STONE_SLAB = BLOCKS.register("polished_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_PILLAR = BLOCKS.register("mars_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> MARS_STONE_BRICK_WALL = BLOCKS.register("mars_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.TERRACOTTA_RED)));

    public static final RegistryEntry<Block> CONGLOMERATE = BLOCKS.register("conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));
    public static final RegistryEntry<Block> POLISHED_CONGLOMERATE = BLOCKS.register("polished_conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_RED)));

    public static final RegistryEntry<Block> MARS_IRON_ORE = BLOCKS.register("mars_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> MARS_DIAMOND_ORE = BLOCKS.register("mars_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final RegistryEntry<Block> MARS_OSTRUM_ORE = BLOCKS.register("mars_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_OSTRUM_ORE = BLOCKS.register("deepslate_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final RegistryEntry<Block> MARS_ICE_SHARD_ORE = BLOCKS.register("mars_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));

    public static final RegistryEntry<Block> MERCURY_STONE = BLOCKS.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_STAIRS = BLOCKS.register("mercury_stone_stairs", () -> new StairBlock(MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_SLAB = BLOCKS.register("mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE = BLOCKS.register("mercury_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_STAIRS = BLOCKS.register("mercury_cobblestone_stairs", () -> new StairBlock(MERCURY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_COBBLESTONE_SLAB = BLOCKS.register("mercury_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICKS = BLOCKS.register("mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_STAIRS = BLOCKS.register("mercury_stone_brick_stairs", () -> new StairBlock(MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_SLAB = BLOCKS.register("mercury_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CRACKED_MERCURY_STONE_BRICKS = BLOCKS.register("cracked_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_BRICKS = BLOCKS.register("chiseled_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_STAIRS = BLOCKS.register("chiseled_mercury_stone_stairs", () -> new StairBlock(CHISELED_MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> CHISELED_MERCURY_STONE_SLAB = BLOCKS.register("chiseled_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE = BLOCKS.register("polished_mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_STAIRS = BLOCKS.register("polished_mercury_stone_stairs", () -> new StairBlock(POLISHED_MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> POLISHED_MERCURY_STONE_SLAB = BLOCKS.register("polished_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_PILLAR = BLOCKS.register("mercury_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_PURPLE)));
    public static final RegistryEntry<Block> MERCURY_STONE_BRICK_WALL = BLOCKS.register("mercury_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.TERRACOTTA_PURPLE)));

    public static final RegistryEntry<Block> MERCURY_IRON_ORE = BLOCKS.register("mercury_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));

    public static final RegistryEntry<Block> VENUS_SAND = BLOCKS.register("venus_sand", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE = BLOCKS.register("venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_STAIRS = BLOCKS.register("venus_stone_stairs", () -> new StairBlock(VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_SLAB = BLOCKS.register("venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE = BLOCKS.register("venus_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_STAIRS = BLOCKS.register("venus_cobblestone_stairs", () -> new StairBlock(VENUS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_COBBLESTONE_SLAB = BLOCKS.register("venus_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICKS = BLOCKS.register("venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_STAIRS = BLOCKS.register("venus_stone_brick_stairs", () -> new StairBlock(VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_SLAB = BLOCKS.register("venus_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CRACKED_VENUS_STONE_BRICKS = BLOCKS.register("cracked_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_BRICKS = BLOCKS.register("chiseled_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_STAIRS = BLOCKS.register("chiseled_venus_stone_stairs", () -> new StairBlock(CHISELED_VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CHISELED_VENUS_STONE_SLAB = BLOCKS.register("chiseled_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE = BLOCKS.register("polished_venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_STAIRS = BLOCKS.register("polished_venus_stone_stairs", () -> new StairBlock(POLISHED_VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> POLISHED_VENUS_STONE_SLAB = BLOCKS.register("polished_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_PILLAR = BLOCKS.register("venus_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_STONE_BRICK_WALL = BLOCKS.register("venus_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.TERRACOTTA_ORANGE)));

    public static final RegistryEntry<Block> VENUS_SANDSTONE = BLOCKS.register("venus_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICKS = BLOCKS.register("venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICK_STAIRS = BLOCKS.register("venus_sandstone_brick_stairs", () -> new StairBlock(VENUS_SANDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> VENUS_SANDSTONE_BRICK_SLAB = BLOCKS.register("venus_sandstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE).color(MaterialColor.TERRACOTTA_ORANGE)));
    public static final RegistryEntry<Block> CRACKED_VENUS_SANDSTONE_BRICKS = BLOCKS.register("cracked_venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.TERRACOTTA_ORANGE)));

    public static final RegistryEntry<Block> INFERNAL_SPIRE_BLOCK = BLOCKS.register("infernal_spire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT).color(MaterialColor.COLOR_RED)));

    public static final RegistryEntry<Block> VENUS_COAL_ORE = BLOCKS.register("venus_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> VENUS_GOLD_ORE = BLOCKS.register("venus_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> VENUS_DIAMOND_ORE = BLOCKS.register("venus_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final RegistryEntry<Block> VENUS_CALORITE_ORE = BLOCKS.register("venus_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> DEEPSLATE_CALORITE_ORE = BLOCKS.register("deepslate_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));

    public static final RegistryEntry<Block> GLACIO_STONE = BLOCKS.register("glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_STAIRS = BLOCKS.register("glacio_stone_stairs", () -> new StairBlock(GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_SLAB = BLOCKS.register("glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE = BLOCKS.register("glacio_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_STAIRS = BLOCKS.register("glacio_cobblestone_stairs", () -> new StairBlock(GLACIO_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_COBBLESTONE_SLAB = BLOCKS.register("glacio_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICKS = BLOCKS.register("glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_STAIRS = BLOCKS.register("glacio_stone_brick_stairs", () -> new StairBlock(GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_SLAB = BLOCKS.register("glacio_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> CRACKED_GLACIO_STONE_BRICKS = BLOCKS.register("cracked_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_BRICKS = BLOCKS.register("chiseled_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_STAIRS = BLOCKS.register("chiseled_glacio_stone_stairs", () -> new StairBlock(CHISELED_GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> CHISELED_GLACIO_STONE_SLAB = BLOCKS.register("chiseled_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE = BLOCKS.register("polished_glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_STAIRS = BLOCKS.register("polished_glacio_stone_stairs", () -> new StairBlock(POLISHED_GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> POLISHED_GLACIO_STONE_SLAB = BLOCKS.register("polished_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_PILLAR = BLOCKS.register("glacio_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.ICE)));
    public static final RegistryEntry<Block> GLACIO_STONE_BRICK_WALL = BLOCKS.register("glacio_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.ICE)));

    public static final RegistryEntry<Block> PERMAFROST = BLOCKS.register("permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICKS = BLOCKS.register("permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_STAIRS = BLOCKS.register("permafrost_brick_stairs", () -> new StairBlock(PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_SLAB = BLOCKS.register("permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CRACKED_PERMAFROST_BRICKS = BLOCKS.register("cracked_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_TILES = BLOCKS.register("permafrost_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICKS = BLOCKS.register("chiseled_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICK_STAIRS = BLOCKS.register("chiseled_permafrost_brick_stairs", () -> new StairBlock(CHISELED_PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> CHISELED_PERMAFROST_BRICK_SLAB = BLOCKS.register("chiseled_permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST = BLOCKS.register("polished_permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST_STAIRS = BLOCKS.register("polished_permafrost_stairs", () -> new StairBlock(POLISHED_PERMAFROST.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_PERMAFROST.get()).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> POLISHED_PERMAFROST_SLAB = BLOCKS.register("polished_permafrost_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_PILLAR = BLOCKS.register("permafrost_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).color(MaterialColor.COLOR_LIGHT_BLUE)));
    public static final RegistryEntry<Block> PERMAFROST_BRICK_WALL = BLOCKS.register("permafrost_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL).color(MaterialColor.COLOR_LIGHT_BLUE)));

    public static final RegistryEntry<Block> GLACIO_ICE_SHARD_ORE = BLOCKS.register("glacio_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final RegistryEntry<Block> GLACIO_COAL_ORE = BLOCKS.register("glacio_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final RegistryEntry<Block> GLACIO_COPPER_ORE = BLOCKS.register("glacio_copper_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> GLACIO_IRON_ORE = BLOCKS.register("glacio_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryEntry<Block> GLACIO_LAPIS_ORE = BLOCKS.register("glacio_lapis_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));

    public static final RegistryEntry<Block> GLACIAN_LOG = BLOCKS.register("glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.CLAY).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> STRIPPED_GLACIAN_LOG = BLOCKS.register("stripped_glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).color(MaterialColor.CLAY).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_LEAVES = BLOCKS.register("glacian_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion().friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_PLANKS = BLOCKS.register("glacian_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_STAIRS = BLOCKS.register("glacian_stairs", () -> new StairBlock(GLACIAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_SLAB = BLOCKS.register("glacian_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_DOOR = BLOCKS.register("glacian_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).friction(0.5f).color(MaterialColor.COLOR_PINK), SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN));
    public static final RegistryEntry<Block> GLACIAN_TRAPDOOR = BLOCKS.register("glacian_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).friction(0.5f).color(MaterialColor.COLOR_PINK), SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN));
    public static final RegistryEntry<Block> GLACIAN_FENCE = BLOCKS.register("glacian_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).friction(0.5f).friction(0.5f).color(MaterialColor.COLOR_PINK)));
    public static final RegistryEntry<Block> GLACIAN_FENCE_GATE = BLOCKS.register("glacian_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).friction(0.5f).color(MaterialColor.COLOR_PINK), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryEntry<Block> GLACIAN_BUTTON = BLOCKS.register("glacian_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).color(MaterialColor.COLOR_PINK), 30, true, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON));
    public static final RegistryEntry<Block> GLACIAN_PRESSURE_PLATE = BLOCKS.register("glacian_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).friction(0.5f).color(MaterialColor.COLOR_PINK), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON));
    public static final RegistryEntry<Block> GLACIAN_SIGN = STANDING_SIGNS.register("glacian_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).color(MaterialColor.COLOR_PINK), GLACIAN_SIGN_TYPE));
    public static final RegistryEntry<Block> GLACIAN_WALL_SIGN = WALL_SIGNS.register("glacian_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).dropsLike(GLACIAN_SIGN.get()).color(MaterialColor.COLOR_PINK), GLACIAN_SIGN_TYPE));
    public static final RegistryEntry<Block> GLACIAN_FUR = BLOCKS.register("glacian_fur", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).color(MaterialColor.COLOR_PINK)));

    public static final RegistryEntry<Block> OIL_BLOCK = ModBlocks.BLOCKS.register("oil", () -> new BotariumLiquidBlock(ModFluidProperties.OIL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> FUEL_BLOCK = ModBlocks.BLOCKS.register("fuel", () -> new BotariumLiquidBlock(ModFluidProperties.FUEL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> CRYO_FUEL_BLOCK = ModBlocks.BLOCKS.register("cryo_fuel", () -> new CryoFuelLiquidBlock(ModFluidProperties.CRYO_FUEL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryEntry<Block> OXYGEN_BLOCK = ModBlocks.BLOCKS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
}