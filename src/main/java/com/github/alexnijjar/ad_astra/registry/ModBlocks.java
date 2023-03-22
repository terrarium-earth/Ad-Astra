package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlock;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlock;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.github.alexnijjar.ad_astra.blocks.machines.*;
import com.github.alexnijjar.ad_astra.blocks.pipes.CableBlock;
import com.github.alexnijjar.ad_astra.blocks.pipes.FluidPipeBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.CoalLanternBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.CoalTorchBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.WallCoalTorchBlock;
import com.github.alexnijjar.ad_astra.mixin.SignTypeInvoker;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.SignType;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks {
    public static final Set<Block> blocks = new HashSet<>();

    public static final Block LAUNCH_PAD = register("launch_pad", new LaunchPad(FabricBlockSettings.copy(Blocks.CUT_COPPER).nonOpaque()), true);

    // Flag Blocks
    public static final Block WHITE_FLAG = register("white_flag", new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1)));
    public static final Block BLACK_FLAG = register("black_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block BLUE_FLAG = register("blue_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block BROWN_FLAG = register("brown_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block CYAN_FLAG = register("cyan_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block GRAY_FLAG = register("gray_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block GREEN_FLAG = register("green_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block LIGHT_BLUE_FLAG = register("light_blue_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block LIGHT_GRAY_FLAG = register("light_gray_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block LIME_FLAG = register("lime_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block MAGENTA_FLAG = register("magenta_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block ORANGE_FLAG = register("orange_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block PINK_FLAG = register("pink_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block PURPLE_FLAG = register("purple_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block RED_FLAG = register("red_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));
    public static final Block YELLOW_FLAG = register("yellow_flag", new FlagBlock(FabricBlockSettings.copy(WHITE_FLAG)));

    // Globes
    public static final Block EARTH_GLOBE = register("earth_globe", new GlobeBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.5f).nonOpaque().requiresTool()));
    public static final Block MOON_GLOBE = register("moon_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
    public static final Block MARS_GLOBE = register("mars_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
    public static final Block MERCURY_GLOBE = register("mercury_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
    public static final Block VENUS_GLOBE = register("venus_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
    public static final Block GLACIO_GLOBE = register("glacio_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));

    // Torch blocks
    public static final Block COAL_TORCH = register("extinguished_torch", new CoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block WALL_COAL_TORCH = register("wall_extinguished_torch", new WallCoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)), true);
    public static final Block COAL_LANTERN = register("extinguished_lantern", new CoalLanternBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.LANTERN).strength(3.5f).nonOpaque()));

    public static final Block STEEL_CABLE = register("steel_cable", new CableBlock(256, 2, 0.344, FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));
    public static final Block DESH_CABLE = register("desh_cable", new CableBlock(1024, 1, 0.312, FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));

    public static final Block DESH_FLUID_PIPE = register("desh_fluid_pipe", new FluidPipeBlock(FluidUtils.millibucketsToDroplets(100), 1, 0.312, FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));
    public static final Block OSTRUM_FLUID_PIPE = register("ostrum_fluid_pipe", new FluidPipeBlock(FluidUtils.millibucketsToDroplets(200), 1, 0.312, FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));

    // Machines
    public static final Block FUEL_REFINERY = register("fuel_refinery", new FuelRefineryBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool()));
    public static final Block COMPRESSOR = register("compressor", new CompressorBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block COAL_GENERATOR = register("coal_generator", new CoalGeneratorBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block OXYGEN_LOADER = register("oxygen_loader", new OxygenLoaderBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block SOLAR_PANEL = register("solar_panel", new SolarPanelBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block NASA_WORKBENCH = register("nasa_workbench", new NasaWorkbenchBlock(FabricBlockSettings.copy(FUEL_REFINERY).nonOpaque()));
    public static final Block OXYGEN_DISTRIBUTOR = register("oxygen_distributor", new OxygenDistributorBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block WATER_PUMP = register("water_pump", new WaterPumpBlock(FabricBlockSettings.copy(FUEL_REFINERY).nonOpaque()));
    public static final Block ENERGIZER = register("energizer", new EnergizerBlock(FabricBlockSettings.copy(FUEL_REFINERY).nonOpaque()));
    public static final Block CRYO_FREEZER = register("cryo_freezer", new CryoFreezerBlock(FabricBlockSettings.copy(FUEL_REFINERY)));
    public static final Block OXYGEN_SENSOR = register("oxygen_sensor", new OxygenSensorBlock(FabricBlockSettings.copy(FUEL_REFINERY)));

    public static final Block IRON_SLIDING_DOOR = register("iron_sliding_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block STEEL_SLIDING_DOOR = register("steel_sliding_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block DESH_SLIDING_DOOR = register("desh_sliding_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block OSTRUM_SLIDING_DOOR = register("ostrum_sliding_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block CALORITE_SLIDING_DOOR = register("calorite_sliding_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block AIRLOCK = register("airlock", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block REINFORCED_DOOR = register("reinforced_door", new SlidingDoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR).strength(25.0f, 40.0f)), true);

    // Blocks
    public static final Block STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
    public static final Block CHEESE_BLOCK = register("cheese_block", new Block(FabricBlockSettings.copy(Blocks.SPONGE).sounds(BlockSoundGroup.SLIME)));
    public static final Block DESH_BLOCK = register("desh_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
    public static final Block OSTRUM_BLOCK = register("ostrum_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
    public static final Block CALORITE_BLOCK = register("calorite_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
    public static final Block RAW_DESH_BLOCK = register("raw_desh_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));
    public static final Block RAW_OSTRUM_BLOCK = register("raw_ostrum_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));
    public static final Block RAW_CALORITE_BLOCK = register("raw_calorite_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));

    public static final Block VENT = register("vent", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER).nonOpaque()));
    public static final Block IRON_PLATING = register("iron_plating", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block IRON_PLATING_STAIRS = register("iron_plating_stairs", new StairsBlock(IRON_PLATING.getDefaultState(), FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block IRON_PLATING_SLAB = register("iron_plating_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block IRON_PILLAR = register("iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block IRON_PLATING_BUTTON = register("iron_plating_button", new StoneButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON)));
    public static final Block IRON_PLATING_PRESSURE_PLATE = register("iron_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Block MARKED_IRON_PILLAR = register("marked_iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block GLOWING_IRON_PILLAR = register("glowing_iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

    public static final Block STEEL_PLATING = register("steel_plating", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block STEEL_PLATING_STAIRS = register("steel_plating_stairs", new StairsBlock(STEEL_PLATING.getDefaultState(), FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block STEEL_PLATING_SLAB = register("steel_plating_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block STEEL_PILLAR = register("steel_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block STEEL_PLATING_BUTTON = register("steel_plating_button", new StoneButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON)));
    public static final Block STEEL_PLATING_PRESSURE_PLATE = register("steel_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Block STEEL_DOOR = register("steel_door", new DoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR)), true);
    public static final Block STEEL_TRAPDOOR = register("steel_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR)));
    public static final Block GLOWING_STEEL_PILLAR = register("glowing_steel_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

    public static final Block DESH_PLATING = register("desh_plating", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block DESH_PLATING_STAIRS = register("desh_plating_stairs", new StairsBlock(DESH_PLATING.getDefaultState(), FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block DESH_PLATING_SLAB = register("desh_plating_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block DESH_PILLAR = register("desh_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block DESH_PLATING_BUTTON = register("desh_plating_button", new StoneButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON)));
    public static final Block DESH_PLATING_PRESSURE_PLATE = register("desh_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Block GLOWING_DESH_PILLAR = register("glowing_desh_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

    public static final Block OSTRUM_PLATING = register("ostrum_plating", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block OSTRUM_PLATING_STAIRS = register("ostrum_plating_stairs", new StairsBlock(OSTRUM_PLATING.getDefaultState(), FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block OSTRUM_PLATING_SLAB = register("ostrum_plating_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block OSTRUM_PILLAR = register("ostrum_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block OSTRUM_PLATING_BUTTON = register("ostrum_plating_button", new StoneButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON)));
    public static final Block OSTRUM_PLATING_PRESSURE_PLATE = register("ostrum_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Block GLOWING_OSTRUM_PILLAR = register("glowing_ostrum_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

    public static final Block CALORITE_PLATING = register("calorite_plating", new Block(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block CALORITE_PLATING_STAIRS = register("calorite_plating_stairs", new StairsBlock(CALORITE_PLATING.getDefaultState(), FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block CALORITE_PLATING_SLAB = register("calorite_plating_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block CALORITE_PILLAR = register("calorite_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER)));
    public static final Block CALORITE_PLATING_BUTTON = register("calorite_plating_button", new StoneButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON)));
    public static final Block CALORITE_PLATING_PRESSURE_PLATE = register("calorite_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.STONE_PRESSURE_PLATE)));
    public static final Block GLOWING_CALORITE_PILLAR = register("glowing_calorite_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

    public static final Block SKY_STONE = register("sky_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)));

    // Moon Stones
    public static final Block MOON_SAND = register("moon_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.GRAY).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
    public static final Block MOON_STONE = register("moon_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)), true);
    public static final Block MOON_STONE_STAIRS = register("moon_stone_stairs", new StairsBlock(MOON_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MOON_STONE_SLAB = register("moon_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MOON_COBBLESTONE = register("moon_cobblestone", new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MOON_COBBLESTONE_STAIRS = register("moon_cobblestone_stairs", new StairsBlock(MOON_COBBLESTONE.getDefaultState(), FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MOON_COBBLESTONE_SLAB = register("moon_cobblestone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block CRACKED_MOON_STONE_BRICKS = register("cracked_moon_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MOON_STONE_BRICKS = register("moon_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOON_STONE_BRICK_SLAB = register("moon_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOON_STONE_BRICK_STAIRS = register("moon_stone_brick_stairs", new StairsBlock(MOON_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block CHISELED_MOON_STONE_BRICKS = register("chiseled_moon_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MOON_STONE_STAIRS = register("chiseled_moon_stone_stairs", new StairsBlock(CHISELED_MOON_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MOON_STONE_SLAB = register("chiseled_moon_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block POLISHED_MOON_STONE = register("polished_moon_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MOON_STONE_STAIRS = register("polished_moon_stone_stairs", new StairsBlock(POLISHED_MOON_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MOON_STONE_SLAB = register("polished_moon_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block MOON_PILLAR = register("moon_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOON_STONE_BRICK_WALL = register("moon_stone_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));

    // Mushrooms
    // Aeronos
    public static final Block AERONOS_MUSHROOM = register("aeronos_mushroom", new MushroomPlantBlock(FabricBlockSettings.copy(Blocks.RED_MUSHROOM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> TreeConfiguredFeatures.HUGE_RED_MUSHROOM));
    public static final Block AERONOS_CAP = register("aeronos_cap", new MushroomBlock(FabricBlockSettings.copy(Blocks.RED_MUSHROOM_BLOCK).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_DOOR = register("aeronos_door", new DoorBlock(FabricBlockSettings.copy(Blocks.CRIMSON_DOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)), true);
    public static final Block AERONOS_TRAPDOOR = register("aeronos_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.CRIMSON_TRAPDOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_PLANKS = register("aeronos_planks", new Block(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_FENCE = register("aeronos_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.CRIMSON_FENCE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_FENCE_GATE = register("aeronos_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.CRIMSON_FENCE_GATE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_STAIRS = register("aeronos_stairs", new StairsBlock(AERONOS_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CRIMSON_STAIRS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_SLAB = register("aeronos_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_STEM = register("aeronos_stem", new MushroomBlock(FabricBlockSettings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block AERONOS_CHEST = register("aeronos_chest", new ChestBlock(FabricBlockSettings.copy(Blocks.CHEST).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> BlockEntityType.CHEST));
    public static final Block AERONOS_LADDER = register("aeronos_ladder", new LadderBlock(FabricBlockSettings.copy(Blocks.LADDER).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));

    // Strophar
    public static final Block STROPHAR_MUSHROOM = register("strophar_mushroom", new MushroomPlantBlock(FabricBlockSettings.copy(Blocks.RED_MUSHROOM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> TreeConfiguredFeatures.HUGE_RED_MUSHROOM));
    public static final Block STROPHAR_CAP = register("strophar_cap", new MushroomBlock(FabricBlockSettings.copy(Blocks.RED_MUSHROOM_BLOCK).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_DOOR = register("strophar_door", new DoorBlock(FabricBlockSettings.copy(Blocks.CRIMSON_DOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)), true);
    public static final Block STROPHAR_TRAPDOOR = register("strophar_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.CRIMSON_TRAPDOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_PLANKS = register("strophar_planks", new Block(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_FENCE = register("strophar_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.CRIMSON_FENCE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_FENCE_GATE = register("strophar_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.CRIMSON_FENCE_GATE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_STAIRS = register("strophar_stairs", new StairsBlock(STROPHAR_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CRIMSON_STAIRS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_SLAB = register("strophar_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_STEM = register("strophar_stem", new MushroomBlock(FabricBlockSettings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
    public static final Block STROPHAR_CHEST = register("strophar_chest", new ChestBlock(FabricBlockSettings.copy(Blocks.CHEST).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> BlockEntityType.CHEST));
    public static final Block STROPHAR_LADDER = register("strophar_ladder", new LadderBlock(FabricBlockSettings.copy(Blocks.LADDER).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));

    // Mars stones
    public static final Block MARS_SAND = register("mars_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
    public static final Block MARS_STONE = register("mars_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
    public static final Block MARS_STONE_STAIRS = register("mars_stone_stairs", new StairsBlock(MARS_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block MARS_STONE_SLAB = register("mars_stone_slab", new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block MARS_COBBLESTONE = register("mars_cobblestone", new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MARS_COBBLESTONE_STAIRS = register("mars_cobblestone_stairs", new StairsBlock(MARS_COBBLESTONE.getDefaultState(), FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MARS_COBBLESTONE_SLAB = register("mars_cobblestone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block CRACKED_MARS_STONE_BRICKS = register("cracked_mars_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MARS_STONE_BRICKS = register("mars_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MARS_STONE_BRICK_SLAB = register("mars_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MARS_STONE_BRICK_STAIRS = register("mars_stone_brick_stairs", new StairsBlock(MARS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block CHISELED_MARS_STONE_BRICKS = register("chiseled_mars_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MARS_STONE_STAIRS = register("chiseled_mars_stone_stairs", new StairsBlock(CHISELED_MARS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MARS_STONE_SLAB = register("chiseled_mars_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block POLISHED_MARS_STONE = register("polished_mars_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MARS_STONE_STAIRS = register("polished_mars_stone_stairs", new StairsBlock(POLISHED_MARS_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MARS_STONE_SLAB = register("polished_mars_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block MARS_PILLAR = register("mars_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MARS_STONE_BRICK_WALL = register("mars_stone_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));
    public static final Block CONGLOMERATE = register("conglomerate", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block POLISHED_CONGLOMERATE = register("polished_conglomerate", new Block(FabricBlockSettings.copy(Blocks.STONE)));

    // Mercury stones
    public static final Block MERCURY_STONE = register("mercury_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)), true);
    public static final Block MERCURY_STONE_STAIRS = register("mercury_stone_stairs", new StairsBlock(MERCURY_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MERCURY_STONE_SLAB = register("mercury_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MERCURY_COBBLESTONE = register("mercury_cobblestone", new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MERCURY_COBBLESTONE_STAIRS = register("mercury_cobblestone_stairs", new StairsBlock(MERCURY_COBBLESTONE.getDefaultState(), FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block MERCURY_COBBLESTONE_SLAB = register("mercury_cobblestone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block CRACKED_MERCURY_STONE_BRICKS = register("cracked_mercury_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block MERCURY_STONE_BRICKS = register("mercury_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MERCURY_STONE_BRICK_SLAB = register("mercury_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MERCURY_STONE_BRICK_STAIRS = register("mercury_stone_brick_stairs", new StairsBlock(MERCURY_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block CHISELED_MERCURY_STONE_BRICKS = register("chiseled_mercury_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MERCURY_STONE_STAIRS = register("chiseled_mercury_stone_stairs", new StairsBlock(CHISELED_MERCURY_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_MERCURY_STONE_SLAB = register("chiseled_mercury_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block POLISHED_MERCURY_STONE = register("polished_mercury_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MERCURY_STONE_STAIRS = register("polished_mercury_stone_stairs", new StairsBlock(POLISHED_MERCURY_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_MERCURY_STONE_SLAB = register("polished_mercury_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block MERCURY_PILLAR = register("mercury_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MERCURY_STONE_BRICK_WALL = register("mercury_stone_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));

    // Venus stones
    public static final Block VENUS_SAND = register("venus_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
    public static final Block VENUS_STONE = register("venus_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
    public static final Block VENUS_STONE_STAIRS = register("venus_stone_stairs", new StairsBlock(VENUS_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block VENUS_STONE_SLAB = register("venus_stone_slab", new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block VENUS_COBBLESTONE = register("venus_cobblestone", new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block VENUS_COBBLESTONE_STAIRS = register("venus_cobblestone_stairs", new StairsBlock(VENUS_COBBLESTONE.getDefaultState(), FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block VENUS_COBBLESTONE_SLAB = register("venus_cobblestone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block CRACKED_VENUS_STONE_BRICKS = register("cracked_venus_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block VENUS_STONE_BRICKS = register("venus_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block VENUS_STONE_BRICK_SLAB = register("venus_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block VENUS_STONE_BRICK_STAIRS = register("venus_stone_brick_stairs", new StairsBlock(VENUS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block CHISELED_VENUS_STONE_BRICKS = register("chiseled_venus_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_VENUS_STONE_STAIRS = register("chiseled_venus_stone_stairs", new StairsBlock(CHISELED_VENUS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_VENUS_STONE_SLAB = register("chiseled_venus_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block POLISHED_VENUS_STONE = register("polished_venus_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_VENUS_STONE_STAIRS = register("polished_venus_stone_stairs", new StairsBlock(POLISHED_VENUS_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_VENUS_STONE_SLAB = register("polished_venus_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block VENUS_PILLAR = register("venus_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block VENUS_STONE_BRICK_WALL = register("venus_stone_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));

    // Venus sandstones
    public static final Block VENUS_SANDSTONE = register("venus_sandstone", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block CRACKED_VENUS_SANDSTONE_BRICKS = register("cracked_venus_sandstone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block VENUS_SANDSTONE_BRICKS = register("venus_sandstone_bricks", new Block(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block VENUS_SANDSTONE_BRICK_SLAB = register("venus_sandstone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block VENUS_SANDSTONE_BRICK_STAIRS = register("venus_sandstone_brick_stairs", new StairsBlock(VENUS_SANDSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block INFERNAL_SPIRE_BLOCK = register("infernal_spire_block", new Block(FabricBlockSettings.copy(Blocks.BASALT)));

    // Glacio stones
    public static final Block GLACIO_STONE = register("glacio_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
    public static final Block GLACIO_STONE_STAIRS = register("glacio_stone_stairs", new StairsBlock(GLACIO_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block GLACIO_STONE_SLAB = register("glacio_stone_slab", new SlabBlock(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
    public static final Block GLACIO_COBBLESTONE = register("glacio_cobblestone", new Block(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block GLACIO_COBBLESTONE_STAIRS = register("glacio_cobblestone_stairs", new StairsBlock(GLACIO_COBBLESTONE.getDefaultState(), FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block GLACIO_COBBLESTONE_SLAB = register("glacio_cobblestone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE)));
    public static final Block CRACKED_GLACIO_STONE_BRICKS = register("cracked_glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block GLACIO_STONE_BRICKS = register("glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block GLACIO_STONE_BRICK_SLAB = register("glacio_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block GLACIO_STONE_BRICK_STAIRS = register("glacio_stone_brick_stairs", new StairsBlock(GLACIO_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block CHISELED_GLACIO_STONE_BRICKS = register("chiseled_glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_GLACIO_STONE_STAIRS = register("chiseled_glacio_stone_stairs", new StairsBlock(CHISELED_GLACIO_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_GLACIO_STONE_SLAB = register("chiseled_glacio_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block POLISHED_GLACIO_STONE = register("polished_glacio_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_GLACIO_STONE_STAIRS = register("polished_glacio_stone_stairs", new StairsBlock(POLISHED_GLACIO_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_GLACIO_STONE_SLAB = register("polished_glacio_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block GLACIO_PILLAR = register("glacio_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block GLACIO_STONE_BRICK_WALL = register("glacio_stone_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));
    public static final Block PERMAFROST = register("permafrost", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block PERMAFROST_BRICKS = register("permafrost_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
    public static final Block PERMAFROST_BRICK_STAIRS = register("permafrost_brick_stairs", new StairsBlock(PERMAFROST_BRICKS.getDefaultState(), FabricBlockSettings.copy(PERMAFROST_BRICKS)));
    public static final Block PERMAFROST_BRICK_SLAB = register("permafrost_brick_slab", new SlabBlock(FabricBlockSettings.copy(PERMAFROST_BRICKS)));
    public static final Block CRACKED_PERMAFROST_BRICKS = register("cracked_permafrost_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));

    public static final Block PERMAFROST_TILES = register("permafrost_tiles", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_PERMAFROST_BRICKS = register("chiseled_permafrost_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final Block CHISELED_PERMAFROST_BRICK_STAIRS = register("chiseled_permafrost_brick_stairs", new StairsBlock(CHISELED_PERMAFROST_BRICKS.getDefaultState(), FabricBlockSettings.copy(CHISELED_PERMAFROST_BRICKS)));
    public static final Block CHISELED_PERMAFROST_BRICK_SLAB = register("chiseled_permafrost_brick_slab", new SlabBlock(FabricBlockSettings.copy(CHISELED_PERMAFROST_BRICKS)));
    public static final Block POLISHED_PERMAFROST = register("polished_permafrost", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block POLISHED_PERMAFROST_STAIRS = register("polished_permafrost_stairs", new StairsBlock(POLISHED_PERMAFROST.getDefaultState(), FabricBlockSettings.copy(POLISHED_PERMAFROST)));
    public static final Block POLISHED_PERMAFROST_SLAB = register("polished_permafrost_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
    public static final Block PERMAFROST_PILLAR = register("permafrost_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block PERMAFROST_BRICK_WALL = register("permafrost_brick_wall", new WallBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)));

    // Sign
    public static final SignType GLACIAN = SignTypeInvoker.adastra_invokeRegister(SignTypeInvoker.adastra_init("glacian"));

    // Glacian Wood
    public static final Block GLACIAN_LOG = register("glacian_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).mapColor(MapColor.LIGHT_BLUE_GRAY).slipperiness(0.5f)));
    public static final Block STRIPPED_GLACIAN_LOG = register("stripped_glacian_log", new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.LIGHT_BLUE_GRAY).slipperiness(0.5f)));
    public static final Block GLACIAN_LEAVES = register("glacian_leaves", new LeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES).nonOpaque().slipperiness(0.5f)));
    public static final Block GLACIAN_PLANKS = register("glacian_planks", new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f).slipperiness(0.5f)));
    public static final Block GLACIAN_STAIRS = register("glacian_stairs", new StairsBlock(GLACIAN_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f)));
    public static final Block GLACIAN_SLAB = register("glacian_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f)));
    public static final Block GLACIAN_DOOR = register("glacian_door", new DoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).slipperiness(0.5f)), true);
    public static final Block GLACIAN_TRAPDOOR = register("glacian_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).slipperiness(0.5f)));
    public static final Block GLACIAN_FENCE = register("glacian_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE).slipperiness(0.5f).slipperiness(0.5f)));
    public static final Block GLACIAN_FENCE_GATE = register("glacian_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE).slipperiness(0.5f)));
    public static final Block GLACIAN_BUTTON = register("glacian_button", new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON)));
    public static final Block GLACIAN_PRESSURE_PLATE = register("glacian_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE).slipperiness(0.5f)));
    public static final Block GLACIAN_SIGN = register("glacian_sign", new SignBlock(FabricBlockSettings.copy(Blocks.OAK_SIGN), GLACIAN));
    public static final Block GLACIAN_WALL_SIGN = register("glacian_wall_sign", new WallSignBlock(FabricBlockSettings.copy(Blocks.OAK_WALL_SIGN).dropsLike(GLACIAN_SIGN), GLACIAN), true);
    public static final Block GLACIAN_FUR = register("glacian_fur", new Block(FabricBlockSettings.copy(Blocks.WHITE_WOOL)));

    // Ores
    public static final Block MOON_CHEESE_ORE = register("moon_cheese_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
    public static final Block MOON_DESH_ORE = register("moon_desh_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block MOON_IRON_ORE = register("moon_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block MOON_ICE_SHARD_ORE = register("moon_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
    public static final Block MARS_IRON_ORE = register("mars_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block MARS_DIAMOND_ORE = register("mars_diamond_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
    public static final Block MARS_OSTRUM_ORE = register("mars_ostrum_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block MARS_ICE_SHARD_ORE = register("mars_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
    public static final Block MERCURY_IRON_ORE = register("mercury_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block VENUS_COAL_ORE = register("venus_coal_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
    public static final Block VENUS_GOLD_ORE = register("venus_gold_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block VENUS_DIAMOND_ORE = register("venus_diamond_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
    public static final Block VENUS_CALORITE_ORE = register("venus_calorite_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block GLACIO_ICE_SHARD_ORE = register("glacio_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
    public static final Block GLACIO_COAL_ORE = register("glacio_coal_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
    public static final Block GLACIO_COPPER_ORE = register("glacio_copper_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block GLACIO_IRON_ORE = register("glacio_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
    public static final Block GLACIO_LAPIS_ORE = register("glacio_lapis_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
    public static final Block DEEPSLATE_ICE_SHARD_ORE = register("deepslate_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE), UniformIntProvider.create(3, 6)), true);
    public static final Block DEEPSLATE_DESH_ORE = register("deepslate_desh_ore", new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);
    public static final Block DEEPSLATE_OSTRUM_ORE = register("deepslate_ostrum_ore", new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);
    public static final Block DEEPSLATE_CALORITE_ORE = register("deepslate_calorite_ore", new OreBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);

    static Block register(String id, Block block) {
        return register(id, block, false);
    }

    static Block register(String id, Block block, boolean excludeFromLootTable) {
        Registry.register(Registry.BLOCK, new ModIdentifier(id), block);
        if (!excludeFromLootTable) {
            blocks.add(block);
        }
        return block;
    }
}