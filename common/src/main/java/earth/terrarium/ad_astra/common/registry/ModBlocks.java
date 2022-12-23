package earth.terrarium.ad_astra.common.registry;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.door.SlidingDoorBlock;
import earth.terrarium.ad_astra.common.block.flag.FlagBlock;
import earth.terrarium.ad_astra.common.block.fluid.CryoFuelBlock;
import earth.terrarium.ad_astra.common.block.globe.GlobeBlock;
import earth.terrarium.ad_astra.common.block.launchpad.LaunchPad;
import earth.terrarium.ad_astra.common.block.machine.*;
import earth.terrarium.ad_astra.common.block.pipe.CableBlock;
import earth.terrarium.ad_astra.common.block.pipe.FluidPipeBlock;
import earth.terrarium.ad_astra.common.block.torch.ExtinguishedLanternBlock;
import earth.terrarium.ad_astra.common.block.torch.ExtinguishedTorchBlock;
import earth.terrarium.ad_astra.common.block.torch.WallExtinguishedTorchBlock;
import earth.terrarium.ad_astra.mixin.WoodTypeInvoker;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import earth.terrarium.botarium.api.registry.fluid.BotariumLiquidBlock;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryHolder<Block> BLOCKS = new RegistryHolder<>(Registry.BLOCK, AdAstra.MOD_ID);

    public static final Supplier<Block> LAUNCH_PAD = BLOCKS.register("launch_pad", () -> new LaunchPad(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));

    // Flag Blocks
    public static final BlockBehaviour.Properties FLAG_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.0f, 1.0f);
    public static final Supplier<Block> WHITE_FLAG = BLOCKS.register("white_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> BLACK_FLAG = BLOCKS.register("black_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> BLUE_FLAG = BLOCKS.register("blue_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> BROWN_FLAG = BLOCKS.register("brown_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> CYAN_FLAG = BLOCKS.register("cyan_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> GRAY_FLAG = BLOCKS.register("gray_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> GREEN_FLAG = BLOCKS.register("green_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> LIGHT_BLUE_FLAG = BLOCKS.register("light_blue_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> LIGHT_GRAY_FLAG = BLOCKS.register("light_gray_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> LIME_FLAG = BLOCKS.register("lime_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> MAGENTA_FLAG = BLOCKS.register("magenta_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> ORANGE_FLAG = BLOCKS.register("orange_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> PINK_FLAG = BLOCKS.register("pink_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> PURPLE_FLAG = BLOCKS.register("purple_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> RED_FLAG = BLOCKS.register("red_flag", () -> new FlagBlock(FLAG_PROPERTIES));
    public static final Supplier<Block> YELLOW_FLAG = BLOCKS.register("yellow_flag", () -> new FlagBlock(FLAG_PROPERTIES));

    // Globes
    public static final BlockBehaviour.Properties GLOBE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.5f).noOcclusion().requiresCorrectToolForDrops();
    public static final Supplier<Block> EARTH_GLOBE = BLOCKS.register("earth_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));
    public static final Supplier<Block> MOON_GLOBE = BLOCKS.register("moon_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));
    public static final Supplier<Block> MARS_GLOBE = BLOCKS.register("mars_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));
    public static final Supplier<Block> MERCURY_GLOBE = BLOCKS.register("mercury_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));
    public static final Supplier<Block> VENUS_GLOBE = BLOCKS.register("venus_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));
    public static final Supplier<Block> GLACIO_GLOBE = BLOCKS.register("glacio_globe", () -> new GlobeBlock(GLOBE_PROPERTIES));

    // Torch blocks
    public static final Supplier<Block> EXTINGUISHED_TORCH = BLOCKS.register("extinguished_torch", () -> new ExtinguishedTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final Supplier<Block> WALL_EXTINGUISHED_TORCH = BLOCKS.register("wall_extinguished_torch", () -> new WallExtinguishedTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().sound(SoundType.WOOD)));
    public static final Supplier<Block> EXTINGUISHED_LANTERN = BLOCKS.register("extinguished_lantern", () -> new ExtinguishedLanternBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.LANTERN).strength(3.5f).noOcclusion()));

    public static final Supplier<Block> STEEL_CABLE = BLOCKS.register("steel_cable", () -> new CableBlock(256, 2, 0.344, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));
    public static final Supplier<Block> DESH_CABLE = BLOCKS.register("desh_cable", () -> new CableBlock(1024, 1, 0.312, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));

    public static final Supplier<Block> DESH_FLUID_PIPE = BLOCKS.register("desh_fluid_pipe", () -> new FluidPipeBlock(FluidHooks.buckets(1) / 10, 1, 0.185, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));
    public static final Supplier<Block> OSTRUM_FLUID_PIPE = BLOCKS.register("ostrum_fluid_pipe", () -> new FluidPipeBlock(FluidHooks.buckets(1) / 5, 1, 0.185, BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.NETHERITE_BLOCK).strength(1.0f, 1.0f)));

    // Machines
    public static final BlockBehaviour.Properties MACHINE_PROPERTIES = BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0f, 1.0f).requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties MACHINE_PROPERTIES_NON_OPAQUE = BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0f, 1.0f).requiresCorrectToolForDrops().noOcclusion();
    public static final Supplier<Block> FUEL_REFINERY = BLOCKS.register("fuel_refinery", () -> new FuelRefineryBlock(BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5.0f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> COMPRESSOR = BLOCKS.register("compressor", () -> new CompressorBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> COAL_GENERATOR = BLOCKS.register("coal_generator", () -> new CoalGeneratorBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> OXYGEN_LOADER = BLOCKS.register("oxygen_loader", () -> new OxygenLoaderBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> SOLAR_PANEL = BLOCKS.register("solar_panel", () -> new SolarPanelBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> NASA_WORKBENCH = BLOCKS.register("nasa_workbench", () -> new NasaWorkbenchBlock(MACHINE_PROPERTIES_NON_OPAQUE));
    public static final Supplier<Block> OXYGEN_DISTRIBUTOR = BLOCKS.register("oxygen_distributor", () -> new OxygenDistributorBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> WATER_PUMP = BLOCKS.register("water_pump", () -> new WaterPumpBlock(MACHINE_PROPERTIES_NON_OPAQUE));
    public static final Supplier<Block> ENERGIZER = BLOCKS.register("energizer", () -> new EnergizerBlock(MACHINE_PROPERTIES_NON_OPAQUE));
    public static final Supplier<Block> CRYO_FREEZER = BLOCKS.register("cryo_freezer", () -> new CryoFreezerBlock(MACHINE_PROPERTIES));
    public static final Supplier<Block> OXYGEN_SENSOR = BLOCKS.register("oxygen_sensor", () -> new OxygenSensorBlock(MACHINE_PROPERTIES));

    public static final Supplier<Block> IRON_SLIDING_DOOR = BLOCKS.register("iron_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> STEEL_SLIDING_DOOR = BLOCKS.register("steel_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> DESH_SLIDING_DOOR = BLOCKS.register("desh_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> OSTRUM_SLIDING_DOOR = BLOCKS.register("ostrum_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> CALORITE_SLIDING_DOOR = BLOCKS.register("calorite_sliding_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> AIRLOCK = BLOCKS.register("airlock", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> REINFORCED_DOOR = BLOCKS.register("reinforced_door", () -> new SlidingDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).strength(25.0f, 40.0f)));

    // Blocks
    public static final Supplier<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Supplier<Block> CHEESE_BLOCK = BLOCKS.register("cheese_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPONGE).sound(SoundType.SLIME_BLOCK)));
    public static final Supplier<Block> DESH_BLOCK = BLOCKS.register("desh_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Supplier<Block> OSTRUM_BLOCK = BLOCKS.register("ostrum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Supplier<Block> CALORITE_BLOCK = BLOCKS.register("calorite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final Supplier<Block> RAW_DESH_BLOCK = BLOCKS.register("raw_desh_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final Supplier<Block> RAW_OSTRUM_BLOCK = BLOCKS.register("raw_ostrum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));
    public static final Supplier<Block> RAW_CALORITE_BLOCK = BLOCKS.register("raw_calorite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)));

    public static final Supplier<Block> IRON_PLATING = BLOCKS.register("iron_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> IRON_PLATING_STAIRS = BLOCKS.register("iron_plating_stairs", () -> new StairBlock(IRON_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> IRON_PLATING_SLAB = BLOCKS.register("iron_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> IRON_PILLAR = BLOCKS.register("iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> IRON_PLATING_BUTTON = BLOCKS.register("iron_plating_button", () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));
    public static final Supplier<Block> IRON_PLATING_PRESSURE_PLATE = BLOCKS.register("iron_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Supplier<Block> MARKED_IRON_PILLAR = BLOCKS.register("marked_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> GLOWING_IRON_PILLAR = BLOCKS.register("glowing_iron_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15)));

    public static final Supplier<Block> STEEL_PLATING = BLOCKS.register("steel_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> STEEL_PLATING_STAIRS = BLOCKS.register("steel_plating_stairs", () -> new StairBlock(STEEL_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> STEEL_PLATING_SLAB = BLOCKS.register("steel_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> STEEL_PILLAR = BLOCKS.register("steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> STEEL_PLATING_BUTTON = BLOCKS.register("steel_plating_button", () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));
    public static final Supplier<Block> STEEL_PLATING_PRESSURE_PLATE = BLOCKS.register("steel_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Supplier<Block> STEEL_DOOR = BLOCKS.register("steel_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR)));
    public static final Supplier<Block> STEEL_TRAPDOOR = BLOCKS.register("steel_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR)));
    public static final Supplier<Block> GLOWING_STEEL_PILLAR = BLOCKS.register("glowing_steel_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15)));

    public static final Supplier<Block> DESH_PLATING = BLOCKS.register("desh_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> DESH_PLATING_STAIRS = BLOCKS.register("desh_plating_stairs", () -> new StairBlock(DESH_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> DESH_PLATING_SLAB = BLOCKS.register("desh_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> DESH_PILLAR = BLOCKS.register("desh_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> DESH_PLATING_BUTTON = BLOCKS.register("desh_plating_button", () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));
    public static final Supplier<Block> DESH_PLATING_PRESSURE_PLATE = BLOCKS.register("desh_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Supplier<Block> GLOWING_DESH_PILLAR = BLOCKS.register("glowing_desh_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15)));

    public static final Supplier<Block> OSTRUM_PLATING = BLOCKS.register("ostrum_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> OSTRUM_PLATING_STAIRS = BLOCKS.register("ostrum_plating_stairs", () -> new StairBlock(OSTRUM_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> OSTRUM_PLATING_SLAB = BLOCKS.register("ostrum_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> OSTRUM_PILLAR = BLOCKS.register("ostrum_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> OSTRUM_PLATING_BUTTON = BLOCKS.register("ostrum_plating_button", () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));
    public static final Supplier<Block> OSTRUM_PLATING_PRESSURE_PLATE = BLOCKS.register("ostrum_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Supplier<Block> GLOWING_OSTRUM_PILLAR = BLOCKS.register("glowing_ostrum_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15)));

    public static final Supplier<Block> CALORITE_PLATING = BLOCKS.register("calorite_plating", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> CALORITE_PLATING_STAIRS = BLOCKS.register("calorite_plating_stairs", () -> new StairBlock(CALORITE_PLATING.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> CALORITE_PLATING_SLAB = BLOCKS.register("calorite_plating_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> CALORITE_PILLAR = BLOCKS.register("calorite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER)));
    public static final Supplier<Block> CALORITE_PLATING_BUTTON = BLOCKS.register("calorite_plating_button", () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BUTTON)));
    public static final Supplier<Block> CALORITE_PLATING_PRESSURE_PLATE = BLOCKS.register("calorite_plating_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Supplier<Block> GLOWING_CALORITE_PILLAR = BLOCKS.register("glowing_calorite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CUT_COPPER).lightLevel(state -> 15)));

    public static final Supplier<Block> SKY_STONE = BLOCKS.register("sky_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    // Moon Stones
    public static final Supplier<Block> MOON_SAND = BLOCKS.register("moon_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.COLOR_GRAY).sound(SoundType.SAND).strength(0.5f, 0.5f)));
    public static final Supplier<Block> MOON_STONE = BLOCKS.register("moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MOON_STONE_STAIRS = BLOCKS.register("moon_stone_stairs", () -> new StairBlock(MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MOON_STONE_SLAB = BLOCKS.register("moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MOON_COBBLESTONE = BLOCKS.register("moon_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MOON_COBBLESTONE_STAIRS = BLOCKS.register("moon_cobblestone_stairs", () -> new StairBlock(MOON_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MOON_COBBLESTONE_SLAB = BLOCKS.register("moon_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> CRACKED_MOON_STONE_BRICKS = BLOCKS.register("cracked_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MOON_STONE_BRICKS = BLOCKS.register("moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MOON_STONE_BRICK_STAIRS = BLOCKS.register("moon_stone_brick_stairs", () -> new StairBlock(MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MOON_STONE_BRICK_SLAB = BLOCKS.register("moon_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MOON_STONE_BRICKS = BLOCKS.register("chiseled_moon_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MOON_STONE_STAIRS = BLOCKS.register("chiseled_moon_stone_stairs", () -> new StairBlock(CHISELED_MOON_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MOON_STONE_SLAB = BLOCKS.register("chiseled_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> POLISHED_MOON_STONE = BLOCKS.register("polished_moon_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MOON_STONE_STAIRS = BLOCKS.register("polished_moon_stone_stairs", () -> new StairBlock(POLISHED_MOON_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MOON_STONE_SLAB = BLOCKS.register("polished_moon_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> MOON_PILLAR = BLOCKS.register("moon_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MOON_STONE_BRICK_WALL = BLOCKS.register("moon_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    // Mushrooms
    // Aeronos
    public static final Supplier<Block> AERONOS_MUSHROOM = BLOCKS.register("aeronos_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), () -> TreeFeatures.HUGE_RED_MUSHROOM));
    public static final Supplier<Block> AERONOS_CAP = BLOCKS.register("aeronos_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_DOOR = BLOCKS.register("aeronos_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_TRAPDOOR = BLOCKS.register("aeronos_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_PLANKS = BLOCKS.register("aeronos_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_STAIRS = BLOCKS.register("aeronos_stairs", () -> new StairBlock(AERONOS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_FENCE = BLOCKS.register("aeronos_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_FENCE_GATE = BLOCKS.register("aeronos_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_SLAB = BLOCKS.register("aeronos_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_STEM = BLOCKS.register("aeronos_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> AERONOS_CHEST = BLOCKS.register("aeronos_chest", () -> new ChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).sound(SoundType.STEM).lightLevel(state -> 6), () -> BlockEntityType.CHEST));
    public static final Supplier<Block> AERONOS_LADDER = BLOCKS.register("aeronos_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));

    // Strophar
    public static final Supplier<Block> STROPHAR_MUSHROOM = BLOCKS.register("strophar_mushroom", () -> new MushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).sound(SoundType.STEM).lightLevel(state -> 6), () -> TreeFeatures.HUGE_RED_MUSHROOM));
    public static final Supplier<Block> STROPHAR_CAP = BLOCKS.register("strophar_cap", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM_BLOCK).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_DOOR = BLOCKS.register("strophar_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_DOOR).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_TRAPDOOR = BLOCKS.register("strophar_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_TRAPDOOR).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_PLANKS = BLOCKS.register("strophar_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRIMSON_PLANKS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_STAIRS = BLOCKS.register("strophar_stairs", () -> new StairBlock(STROPHAR_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_STAIRS).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_FENCE = BLOCKS.register("strophar_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_FENCE_GATE = BLOCKS.register("strophar_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FENCE_GATE).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_SLAB = BLOCKS.register("strophar_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_SLAB).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_STEM = BLOCKS.register("strophar_stem", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.MUSHROOM_STEM).sound(SoundType.STEM).lightLevel(state -> 6)));
    public static final Supplier<Block> STROPHAR_CHEST = BLOCKS.register("strophar_chest", () -> new ChestBlock(BlockBehaviour.Properties.copy(Blocks.CHEST).sound(SoundType.STEM).lightLevel(state -> 6), () -> BlockEntityType.CHEST));
    public static final Supplier<Block> STROPHAR_LADDER = BLOCKS.register("strophar_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER).sound(SoundType.STEM).lightLevel(state -> 6)));

    // Mars stones
    public static final Supplier<Block> MARS_SAND = BLOCKS.register("mars_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.SAND).strength(0.5f, 0.5f)));
    public static final Supplier<Block> MARS_STONE = BLOCKS.register("mars_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> MARS_STONE_STAIRS = BLOCKS.register("mars_stone_stairs", () -> new StairBlock(MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> MARS_STONE_SLAB = BLOCKS.register("mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> MARS_COBBLESTONE = BLOCKS.register("mars_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MARS_COBBLESTONE_STAIRS = BLOCKS.register("mars_cobblestone_stairs", () -> new StairBlock(MARS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MARS_COBBLESTONE_SLAB = BLOCKS.register("mars_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> CRACKED_MARS_STONE_BRICKS = BLOCKS.register("cracked_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MARS_STONE_BRICKS = BLOCKS.register("mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MARS_STONE_BRICK_STAIRS = BLOCKS.register("mars_stone_brick_stairs", () -> new StairBlock(MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MARS_STONE_BRICK_SLAB = BLOCKS.register("mars_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MARS_STONE_BRICKS = BLOCKS.register("chiseled_mars_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MARS_STONE_STAIRS = BLOCKS.register("chiseled_mars_stone_stairs", () -> new StairBlock(CHISELED_MARS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MARS_STONE_SLAB = BLOCKS.register("chiseled_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> POLISHED_MARS_STONE = BLOCKS.register("polished_mars_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MARS_STONE_STAIRS = BLOCKS.register("polished_mars_stone_stairs", () -> new StairBlock(POLISHED_MARS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MARS_STONE_SLAB = BLOCKS.register("polished_mars_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> MARS_PILLAR = BLOCKS.register("mars_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MARS_STONE_BRICK_WALL = BLOCKS.register("mars_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final Supplier<Block> CONGLOMERATE = BLOCKS.register("conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> POLISHED_CONGLOMERATE = BLOCKS.register("polished_conglomerate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    // Mercury stones
    public static final Supplier<Block> MERCURY_STONE = BLOCKS.register("mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MERCURY_STONE_STAIRS = BLOCKS.register("mercury_stone_stairs", () -> new StairBlock(MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MERCURY_STONE_SLAB = BLOCKS.register("mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MERCURY_COBBLESTONE = BLOCKS.register("mercury_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MERCURY_COBBLESTONE_STAIRS = BLOCKS.register("mercury_cobblestone_stairs", () -> new StairBlock(MERCURY_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> MERCURY_COBBLESTONE_SLAB = BLOCKS.register("mercury_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> CRACKED_MERCURY_STONE_BRICKS = BLOCKS.register("cracked_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> MERCURY_STONE_BRICKS = BLOCKS.register("mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MERCURY_STONE_BRICK_STAIRS = BLOCKS.register("mercury_stone_brick_stairs", () -> new StairBlock(MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MERCURY_STONE_BRICK_SLAB = BLOCKS.register("mercury_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MERCURY_STONE_BRICKS = BLOCKS.register("chiseled_mercury_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MERCURY_STONE_STAIRS = BLOCKS.register("chiseled_mercury_stone_stairs", () -> new StairBlock(CHISELED_MERCURY_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_MERCURY_STONE_SLAB = BLOCKS.register("chiseled_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> POLISHED_MERCURY_STONE = BLOCKS.register("polished_mercury_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MERCURY_STONE_STAIRS = BLOCKS.register("polished_mercury_stone_stairs", () -> new StairBlock(POLISHED_MERCURY_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_MERCURY_STONE_SLAB = BLOCKS.register("polished_mercury_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> MERCURY_PILLAR = BLOCKS.register("mercury_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> MERCURY_STONE_BRICK_WALL = BLOCKS.register("mercury_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    // Venus stones
    public static final Supplier<Block> VENUS_SAND = BLOCKS.register("venus_sand", () -> new FallingBlock(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.SAND).strength(0.5f, 0.5f)));
    public static final Supplier<Block> VENUS_STONE = BLOCKS.register("venus_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> VENUS_STONE_STAIRS = BLOCKS.register("venus_stone_stairs", () -> new StairBlock(VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> VENUS_STONE_SLAB = BLOCKS.register("venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> VENUS_COBBLESTONE = BLOCKS.register("venus_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> VENUS_COBBLESTONE_STAIRS = BLOCKS.register("venus_cobblestone_stairs", () -> new StairBlock(VENUS_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> VENUS_COBBLESTONE_SLAB = BLOCKS.register("venus_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> CRACKED_VENUS_STONE_BRICKS = BLOCKS.register("cracked_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> VENUS_STONE_BRICKS = BLOCKS.register("venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> VENUS_STONE_BRICK_STAIRS = BLOCKS.register("venus_stone_brick_stairs", () -> new StairBlock(VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> VENUS_STONE_BRICK_SLAB = BLOCKS.register("venus_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_VENUS_STONE_BRICKS = BLOCKS.register("chiseled_venus_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_VENUS_STONE_STAIRS = BLOCKS.register("chiseled_venus_stone_stairs", () -> new StairBlock(CHISELED_VENUS_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_VENUS_STONE_SLAB = BLOCKS.register("chiseled_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> POLISHED_VENUS_STONE = BLOCKS.register("polished_venus_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_VENUS_STONE_STAIRS = BLOCKS.register("polished_venus_stone_stairs", () -> new StairBlock(POLISHED_VENUS_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_VENUS_STONE_SLAB = BLOCKS.register("polished_venus_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> VENUS_PILLAR = BLOCKS.register("venus_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> VENUS_STONE_BRICK_WALL = BLOCKS.register("venus_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    // Venus sandstones
    public static final Supplier<Block> VENUS_SANDSTONE = BLOCKS.register("venus_sandstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> CRACKED_VENUS_SANDSTONE_BRICKS = BLOCKS.register("cracked_venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> VENUS_SANDSTONE_BRICKS = BLOCKS.register("venus_sandstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Supplier<Block> VENUS_SANDSTONE_BRICK_STAIRS = BLOCKS.register("venus_sandstone_brick_stairs", () -> new StairBlock(VENUS_SANDSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Supplier<Block> VENUS_SANDSTONE_BRICK_SLAB = BLOCKS.register("venus_sandstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Supplier<Block> INFERNAL_SPIRE_BLOCK = BLOCKS.register("infernal_spire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BASALT)));

    // Glacio stones
    public static final Supplier<Block> GLACIO_STONE = BLOCKS.register("glacio_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> GLACIO_STONE_STAIRS = BLOCKS.register("glacio_stone_stairs", () -> new StairBlock(GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> GLACIO_STONE_SLAB = BLOCKS.register("glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_ORANGE).sound(SoundType.STONE).strength(1.5f, 1.0f).requiresCorrectToolForDrops()));
    public static final Supplier<Block> GLACIO_COBBLESTONE = BLOCKS.register("glacio_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> GLACIO_COBBLESTONE_STAIRS = BLOCKS.register("glacio_cobblestone_stairs", () -> new StairBlock(GLACIO_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> GLACIO_COBBLESTONE_SLAB = BLOCKS.register("glacio_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final Supplier<Block> CRACKED_GLACIO_STONE_BRICKS = BLOCKS.register("cracked_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> GLACIO_STONE_BRICKS = BLOCKS.register("glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> GLACIO_STONE_BRICK_STAIRS = BLOCKS.register("glacio_stone_brick_stairs", () -> new StairBlock(GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> GLACIO_STONE_BRICK_SLAB = BLOCKS.register("glacio_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_GLACIO_STONE_BRICKS = BLOCKS.register("chiseled_glacio_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_GLACIO_STONE_STAIRS = BLOCKS.register("chiseled_glacio_stone_stairs", () -> new StairBlock(CHISELED_GLACIO_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_GLACIO_STONE_SLAB = BLOCKS.register("chiseled_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> POLISHED_GLACIO_STONE = BLOCKS.register("polished_glacio_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_GLACIO_STONE_STAIRS = BLOCKS.register("polished_glacio_stone_stairs", () -> new StairBlock(POLISHED_GLACIO_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_GLACIO_STONE_SLAB = BLOCKS.register("polished_glacio_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> GLACIO_PILLAR = BLOCKS.register("glacio_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> GLACIO_STONE_BRICK_WALL = BLOCKS.register("glacio_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));
    public static final Supplier<Block> PERMAFROST = BLOCKS.register("permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> PERMAFROST_BRICKS = BLOCKS.register("permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<Block> PERMAFROST_BRICK_STAIRS = BLOCKS.register("permafrost_brick_stairs", () -> new StairBlock(PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get())));
    public static final Supplier<Block> PERMAFROST_BRICK_SLAB = BLOCKS.register("permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(PERMAFROST_BRICKS.get())));
    public static final Supplier<Block> CRACKED_PERMAFROST_BRICKS = BLOCKS.register("cracked_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final Supplier<Block> PERMAFROST_TILES = BLOCKS.register("permafrost_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_PERMAFROST_BRICKS = BLOCKS.register("chiseled_permafrost_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Supplier<Block> CHISELED_PERMAFROST_BRICK_STAIRS = BLOCKS.register("chiseled_permafrost_brick_stairs", () -> new StairBlock(CHISELED_PERMAFROST_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get())));
    public static final Supplier<Block> CHISELED_PERMAFROST_BRICK_SLAB = BLOCKS.register("chiseled_permafrost_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHISELED_PERMAFROST_BRICKS.get())));
    public static final Supplier<Block> POLISHED_PERMAFROST = BLOCKS.register("polished_permafrost", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> POLISHED_PERMAFROST_STAIRS = BLOCKS.register("polished_permafrost_stairs", () -> new StairBlock(POLISHED_PERMAFROST.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_PERMAFROST.get())));
    public static final Supplier<Block> POLISHED_PERMAFROST_SLAB = BLOCKS.register("polished_permafrost_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DIORITE)));
    public static final Supplier<Block> PERMAFROST_PILLAR = BLOCKS.register("permafrost_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final Supplier<Block> PERMAFROST_BRICK_WALL = BLOCKS.register("permafrost_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_WALL)));

    // Sign
    public static final WoodType GLACIAN = WoodTypeInvoker.adastra_invokeRegister(WoodTypeInvoker.adastra_init(ArchitecturyTarget.getCurrentTarget().equals("forge") ? "ad_astra:glacian" : "glacian"));

    // Glacian Wood
    public static final Supplier<Block> GLACIAN_LOG = BLOCKS.register("glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.CLAY).friction(0.5f)));
    public static final Supplier<Block> STRIPPED_GLACIAN_LOG = BLOCKS.register("stripped_glacian_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).color(MaterialColor.CLAY).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_LEAVES = BLOCKS.register("glacian_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).noOcclusion().friction(0.5f)));
    public static final Supplier<Block> GLACIAN_PLANKS = BLOCKS.register("glacian_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_STAIRS = BLOCKS.register("glacian_stairs", () -> new StairBlock(GLACIAN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_SLAB = BLOCKS.register("glacian_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_DOOR = BLOCKS.register("glacian_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_TRAPDOOR = BLOCKS.register("glacian_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_FENCE = BLOCKS.register("glacian_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).friction(0.5f).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_FENCE_GATE = BLOCKS.register("glacian_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_BUTTON = BLOCKS.register("glacian_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final Supplier<Block> GLACIAN_PRESSURE_PLATE = BLOCKS.register("glacian_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).friction(0.5f)));
    public static final Supplier<Block> GLACIAN_SIGN = BLOCKS.register("glacian_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), GLACIAN));
    public static final Supplier<Block> GLACIAN_WALL_SIGN = BLOCKS.register("glacian_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).dropsLike(GLACIAN_SIGN.get()), GLACIAN));
    public static final Supplier<Block> GLACIAN_FUR = BLOCKS.register("glacian_fur", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));

    // Ores
    public static final Supplier<Block> MOON_CHEESE_ORE = BLOCKS.register("moon_cheese_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final Supplier<Block> MOON_DESH_ORE = BLOCKS.register("moon_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> MOON_IRON_ORE = BLOCKS.register("moon_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> MOON_ICE_SHARD_ORE = BLOCKS.register("moon_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final Supplier<Block> MARS_IRON_ORE = BLOCKS.register("mars_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> MARS_DIAMOND_ORE = BLOCKS.register("mars_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final Supplier<Block> MARS_OSTRUM_ORE = BLOCKS.register("mars_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> MARS_ICE_SHARD_ORE = BLOCKS.register("mars_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final Supplier<Block> MERCURY_IRON_ORE = BLOCKS.register("mercury_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> VENUS_COAL_ORE = BLOCKS.register("venus_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final Supplier<Block> VENUS_GOLD_ORE = BLOCKS.register("venus_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> VENUS_DIAMOND_ORE = BLOCKS.register("venus_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(3, 7)));
    public static final Supplier<Block> VENUS_CALORITE_ORE = BLOCKS.register("venus_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> GLACIO_ICE_SHARD_ORE = BLOCKS.register("glacio_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final Supplier<Block> GLACIO_COAL_ORE = BLOCKS.register("glacio_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(0, 2)));
    public static final Supplier<Block> GLACIO_COPPER_ORE = BLOCKS.register("glacio_copper_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> GLACIO_IRON_ORE = BLOCKS.register("glacio_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final Supplier<Block> GLACIO_LAPIS_ORE = BLOCKS.register("glacio_lapis_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE), UniformInt.of(2, 5)));
    public static final Supplier<Block> DEEPSLATE_ICE_SHARD_ORE = BLOCKS.register("deepslate_ice_shard_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE), UniformInt.of(3, 6)));
    public static final Supplier<Block> DEEPSLATE_DESH_ORE = BLOCKS.register("deepslate_desh_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final Supplier<Block> DEEPSLATE_OSTRUM_ORE = BLOCKS.register("deepslate_ostrum_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));
    public static final Supplier<Block> DEEPSLATE_CALORITE_ORE = BLOCKS.register("deepslate_calorite_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)));

    public static final Supplier<Block> OIL_BLOCK = ModBlocks.BLOCKS.register("oil", () -> new BotariumLiquidBlock(ModFluidProperties.OIL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final Supplier<Block> FUEL_BLOCK = ModBlocks.BLOCKS.register("fuel", () -> new BotariumLiquidBlock(ModFluidProperties.FUEL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final Supplier<Block> CRYO_FUEL_BLOCK = ModBlocks.BLOCKS.register("cryo_fuel", () -> new CryoFuelBlock(ModFluidProperties.CRYO_FUEL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final Supplier<Block> OXYGEN_BLOCK = ModBlocks.BLOCKS.register("oxygen", () -> new BotariumLiquidBlock(ModFluidProperties.OXYGEN_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
}