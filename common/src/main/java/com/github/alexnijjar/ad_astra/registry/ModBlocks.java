package com.github.alexnijjar.ad_astra.registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.door.SlidingDoorBlock;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlock;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlock;
import com.github.alexnijjar.ad_astra.blocks.launchpad.LaunchPad;
import com.github.alexnijjar.ad_astra.blocks.machines.CoalGeneratorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.CompressorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.CryoFreezerBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.EnergizerBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.FuelRefineryBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.NasaWorkbenchBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.OxygenDistributorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.OxygenLoaderBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.OxygenSensorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.SolarPanelBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.WaterPumpBlock;
import com.github.alexnijjar.ad_astra.blocks.pipes.CableBlock;
import com.github.alexnijjar.ad_astra.blocks.pipes.FluidPipeBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.ExtinguishedLanternBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.ExtinguishedTorchBlock;
import com.github.alexnijjar.ad_astra.blocks.torches.WallExtinguishedTorchBlock;
import com.github.alexnijjar.ad_astra.mixin.SignTypeInvoker;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.SignType;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(AdAstra.MOD_ID, Registry.BLOCK_KEY);
	public static final Set<Block> blocks = new HashSet<>();

	public static final Supplier<Block> LAUNCH_PAD = register("launch_pad", new LaunchPad(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)), true);

	// Flag Blocks
	public static final Supplier<Block> WHITE_FLAG = register("white_flag", new FlagBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f)));
	public static final Supplier<Block> BLACK_FLAG = register("black_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> BLUE_FLAG = register("blue_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> BROWN_FLAG = register("brown_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> CYAN_FLAG = register("cyan_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> GRAY_FLAG = register("gray_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> GREEN_FLAG = register("green_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> LIGHT_BLUE_FLAG = register("light_blue_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> LIGHT_GRAY_FLAG = register("light_gray_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> LIME_FLAG = register("lime_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> MAGENTA_FLAG = register("magenta_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> ORANGE_FLAG = register("orange_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> PINK_FLAG = register("pink_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> PURPLE_FLAG = register("purple_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> RED_FLAG = register("red_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));
	public static final Supplier<Block> YELLOW_FLAG = register("yellow_flag", new FlagBlock(AbstractBlock.Settings.copy(WHITE_FLAG.get())));

	// Globes
	public static final Supplier<Block> EARTH_GLOBE = register("earth_globe", new GlobeBlock(AbstractBlock.Settings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.5f).nonOpaque().requiresTool()));
	public static final Supplier<Block> MOON_GLOBE = register("moon_globe", new GlobeBlock(AbstractBlock.Settings.copy(EARTH_GLOBE.get())));
	public static final Supplier<Block> MARS_GLOBE = register("mars_globe", new GlobeBlock(AbstractBlock.Settings.copy(EARTH_GLOBE.get())));
	public static final Supplier<Block> MERCURY_GLOBE = register("mercury_globe", new GlobeBlock(AbstractBlock.Settings.copy(EARTH_GLOBE.get())));
	public static final Supplier<Block> VENUS_GLOBE = register("venus_globe", new GlobeBlock(AbstractBlock.Settings.copy(EARTH_GLOBE.get())));
	public static final Supplier<Block> GLACIO_GLOBE = register("glacio_globe", new GlobeBlock(AbstractBlock.Settings.copy(EARTH_GLOBE.get())));

	// Torch blocks
	public static final Supplier<Block> EXTINGUISHED_TORCH = register("extinguished_torch", new ExtinguishedTorchBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)));
	public static final Supplier<Block> WALL_EXTINGUISHED_TORCH = register("wall_extinguished_torch", new WallExtinguishedTorchBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)), true);
	public static final Supplier<Block> EXTINGUISHED_LANTERN = register("extinguished_lantern", new ExtinguishedLanternBlock(AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.LANTERN).strength(3.5f).nonOpaque()));

	public static final Supplier<Block> STEEL_CABLE = register("steel_cable", new CableBlock(64, 2, 0.344, AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));
	public static final Supplier<Block> DESH_CABLE = register("desh_cable", new CableBlock(256, 1, 0.312, AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));

	public static final Supplier<Block> DESH_FLUID_PIPE = register("desh_fluid_pipe", new FluidPipeBlock(FluidUtils.millibucketsToDroplets(25), 1, 0.312, AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));
	public static final Supplier<Block> OSTRUM_FLUID_PIPE = register("ostrum_fluid_pipe", new FluidPipeBlock(FluidUtils.millibucketsToDroplets(50), 1, 0.312, AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(1.0f, 1.0f)));

	// Machines
	public static final Supplier<Block> FUEL_REFINERY = register("fuel_refinery", new FuelRefineryBlock(AbstractBlock.Settings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool()));
	public static final Supplier<Block> COMPRESSOR = register("compressor", new CompressorBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> COAL_GENERATOR = register("coal_generator", new CoalGeneratorBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> OXYGEN_LOADER = register("oxygen_loader", new OxygenLoaderBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> SOLAR_PANEL = register("solar_panel", new SolarPanelBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> NASA_WORKBENCH = register("nasa_workbench", new NasaWorkbenchBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get()).nonOpaque()));
	public static final Supplier<Block> OXYGEN_DISTRIBUTOR = register("oxygen_distributor", new OxygenDistributorBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> WATER_PUMP = register("water_pump", new WaterPumpBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get()).nonOpaque()));
	public static final Supplier<Block> ENERGIZER = register("energizer", new EnergizerBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get()).nonOpaque()));
	public static final Supplier<Block> CRYO_FREEZER = register("cryo_freezer", new CryoFreezerBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));
	public static final Supplier<Block> OXYGEN_SENSOR = register("oxygen_sensor", new OxygenSensorBlock(AbstractBlock.Settings.copy(FUEL_REFINERY.get())));

	public static final Supplier<Block> IRON_SLIDING_DOOR = register("iron_sliding_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> STEEL_SLIDING_DOOR = register("steel_sliding_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> DESH_SLIDING_DOOR = register("desh_sliding_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> OSTRUM_SLIDING_DOOR = register("ostrum_sliding_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> CALORITE_SLIDING_DOOR = register("calorite_sliding_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> AIRLOCK = register("airlock", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> REINFORCED_DOOR = register("reinforced_door", new SlidingDoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR).strength(25.0f, 40.0f)), true);

	// Blocks
	public static final Supplier<Block> STEEL_BLOCK = register("steel_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
	public static final Supplier<Block> CHEESE_BLOCK = register("cheese_block", new Block(AbstractBlock.Settings.copy(Blocks.SPONGE).sounds(BlockSoundGroup.SLIME)));
	public static final Supplier<Block> DESH_BLOCK = register("desh_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
	public static final Supplier<Block> OSTRUM_BLOCK = register("ostrum_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
	public static final Supplier<Block> CALORITE_BLOCK = register("calorite_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)));
	public static final Supplier<Block> RAW_DESH_BLOCK = register("raw_desh_block", new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)));
	public static final Supplier<Block> RAW_OSTRUM_BLOCK = register("raw_ostrum_block", new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)));
	public static final Supplier<Block> RAW_CALORITE_BLOCK = register("raw_calorite_block", new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)));

	public static final Supplier<Block> IRON_PLATING = register("iron_plating", new Block(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> IRON_PLATING_STAIRS = register("iron_plating_stairs", new StairsBlock(IRON_PLATING.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> IRON_PLATING_SLAB = register("iron_plating_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> IRON_PILLAR = register("iron_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> IRON_PLATING_BUTTON = register("iron_plating_button", new StoneButtonBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
	public static final Supplier<Block> IRON_PLATING_PRESSURE_PLATE = register("iron_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)));
	public static final Supplier<Block> MARKED_IRON_PILLAR = register("marked_iron_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> GLOWING_IRON_PILLAR = register("glowing_iron_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

	public static final Supplier<Block> STEEL_PLATING = register("steel_plating", new Block(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> STEEL_PLATING_STAIRS = register("steel_plating_stairs", new StairsBlock(STEEL_PLATING.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> STEEL_PLATING_SLAB = register("steel_plating_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> STEEL_PILLAR = register("steel_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> STEEL_PLATING_BUTTON = register("steel_plating_button", new StoneButtonBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
	public static final Supplier<Block> STEEL_PLATING_PRESSURE_PLATE = register("steel_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)));
	public static final Supplier<Block> STEEL_DOOR = register("steel_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_DOOR)), true);
	public static final Supplier<Block> STEEL_TRAPDOOR = register("steel_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR)));
	public static final Supplier<Block> GLOWING_STEEL_PILLAR = register("glowing_steel_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

	public static final Supplier<Block> DESH_PLATING = register("desh_plating", new Block(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> DESH_PLATING_STAIRS = register("desh_plating_stairs", new StairsBlock(DESH_PLATING.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> DESH_PLATING_SLAB = register("desh_plating_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> DESH_PILLAR = register("desh_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> DESH_PLATING_BUTTON = register("desh_plating_button", new StoneButtonBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
	public static final Supplier<Block> DESH_PLATING_PRESSURE_PLATE = register("desh_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)));
	public static final Supplier<Block> GLOWING_DESH_PILLAR = register("glowing_desh_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

	public static final Supplier<Block> OSTRUM_PLATING = register("ostrum_plating", new Block(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> OSTRUM_PLATING_STAIRS = register("ostrum_plating_stairs", new StairsBlock(OSTRUM_PLATING.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> OSTRUM_PLATING_SLAB = register("ostrum_plating_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> OSTRUM_PILLAR = register("ostrum_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> OSTRUM_PLATING_BUTTON = register("ostrum_plating_button", new StoneButtonBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
	public static final Supplier<Block> OSTRUM_PLATING_PRESSURE_PLATE = register("ostrum_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)));
	public static final Supplier<Block> GLOWING_OSTRUM_PILLAR = register("glowing_ostrum_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

	public static final Supplier<Block> CALORITE_PLATING = register("calorite_plating", new Block(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> CALORITE_PLATING_STAIRS = register("calorite_plating_stairs", new StairsBlock(CALORITE_PLATING.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> CALORITE_PLATING_SLAB = register("calorite_plating_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> CALORITE_PILLAR = register("calorite_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER)));
	public static final Supplier<Block> CALORITE_PLATING_BUTTON = register("calorite_plating_button", new StoneButtonBlock(AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
	public static final Supplier<Block> CALORITE_PLATING_PRESSURE_PLATE = register("calorite_plating_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AbstractBlock.Settings.copy(Blocks.STONE_PRESSURE_PLATE)));
	public static final Supplier<Block> GLOWING_CALORITE_PILLAR = register("glowing_calorite_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.CUT_COPPER).luminance(state -> 15)));

	public static final Supplier<Block> SKY_STONE = register("sky_stone", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));

	// Moon Stones
	public static final Supplier<Block> MOON_SAND = register("moon_sand", new FallingBlock(AbstractBlock.Settings.of(Material.AGGREGATE, MapColor.GRAY).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
	public static final Supplier<Block> MOON_STONE = register("moon_stone", new Block(AbstractBlock.Settings.copy(Blocks.STONE)), true);
	public static final Supplier<Block> MOON_STONE_STAIRS = register("moon_stone_stairs", new StairsBlock(MOON_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MOON_STONE_SLAB = register("moon_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MOON_COBBLESTONE = register("moon_cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MOON_COBBLESTONE_STAIRS = register("moon_cobblestone_stairs", new StairsBlock(MOON_COBBLESTONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MOON_COBBLESTONE_SLAB = register("moon_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> CRACKED_MOON_STONE_BRICKS = register("cracked_moon_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MOON_STONE_BRICKS = register("moon_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MOON_STONE_BRICK_SLAB = register("moon_stone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MOON_STONE_BRICK_STAIRS = register("moon_stone_brick_stairs", new StairsBlock(MOON_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MOON_STONE_BRICKS = register("chiseled_moon_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MOON_STONE_STAIRS = register("chiseled_moon_stone_stairs", new StairsBlock(CHISELED_MOON_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MOON_STONE_SLAB = register("chiseled_moon_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> POLISHED_MOON_STONE = register("polished_moon_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MOON_STONE_STAIRS = register("polished_moon_stone_stairs", new StairsBlock(POLISHED_MOON_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MOON_STONE_SLAB = register("polished_moon_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> MOON_PILLAR = register("moon_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MOON_STONE_BRICK_WALL = register("moon_stone_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));

	// Mushrooms
	// Aeronos
	public static final Supplier<Block> AERONOS_MUSHROOM = register("aeronos_mushroom", new MushroomPlantBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> TreeConfiguredFeatures.HUGE_RED_MUSHROOM));
	public static final Supplier<Block> AERONOS_CAP = register("aeronos_cap", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_DOOR = register("aeronos_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_DOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)), true);
	public static final Supplier<Block> AERONOS_TRAPDOOR = register("aeronos_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_TRAPDOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_PLANKS = register("aeronos_planks", new Block(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_FENCE = register("aeronos_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_FENCE_GATE = register("aeronos_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE_GATE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_STAIRS = register("aeronos_stairs", new StairsBlock(AERONOS_PLANKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CRIMSON_STAIRS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_SLAB = register("aeronos_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_STEM = register("aeronos_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> AERONOS_CHEST = register("aeronos_chest", new ChestBlock(AbstractBlock.Settings.copy(Blocks.CHEST).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> BlockEntityType.CHEST));
	public static final Supplier<Block> AERONOS_LADDER = register("aeronos_ladder", new LadderBlock(AbstractBlock.Settings.copy(Blocks.LADDER).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));

	// Strophar
	public static final Supplier<Block> STROPHAR_MUSHROOM = register("strophar_mushroom",
			new MushroomPlantBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> TreeConfiguredFeatures.HUGE_RED_MUSHROOM));
	public static final Supplier<Block> STROPHAR_CAP = register("strophar_cap", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_DOOR = register("strophar_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_DOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)), true);
	public static final Supplier<Block> STROPHAR_TRAPDOOR = register("strophar_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_TRAPDOOR).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_PLANKS = register("strophar_planks", new Block(AbstractBlock.Settings.copy(Blocks.CRIMSON_PLANKS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_FENCE = register("strophar_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_FENCE_GATE = register("strophar_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_FENCE_GATE).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_STAIRS = register("strophar_stairs", new StairsBlock(STROPHAR_PLANKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CRIMSON_STAIRS).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_SLAB = register("strophar_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CRIMSON_SLAB).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_STEM = register("strophar_stem", new MushroomBlock(AbstractBlock.Settings.copy(Blocks.MUSHROOM_STEM).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));
	public static final Supplier<Block> STROPHAR_CHEST = register("strophar_chest", new ChestBlock(AbstractBlock.Settings.copy(Blocks.CHEST).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6), () -> BlockEntityType.CHEST));
	public static final Supplier<Block> STROPHAR_LADDER = register("strophar_ladder", new LadderBlock(AbstractBlock.Settings.copy(Blocks.LADDER).sounds(BlockSoundGroup.NETHER_STEM).luminance(state -> 6)));

	// Mars stones
	public static final Supplier<Block> MARS_SAND = register("mars_sand", new FallingBlock(AbstractBlock.Settings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
	public static final Supplier<Block> MARS_STONE = register("mars_stone", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
	public static final Supplier<Block> MARS_STONE_STAIRS = register("mars_stone_stairs", new StairsBlock(MARS_STONE.get().getDefaultState(), AbstractBlock.Settings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> MARS_STONE_SLAB = register("mars_stone_slab", new SlabBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> MARS_COBBLESTONE = register("mars_cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MARS_COBBLESTONE_STAIRS = register("mars_cobblestone_stairs", new StairsBlock(MARS_COBBLESTONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MARS_COBBLESTONE_SLAB = register("mars_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> CRACKED_MARS_STONE_BRICKS = register("cracked_mars_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MARS_STONE_BRICKS = register("mars_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MARS_STONE_BRICK_SLAB = register("mars_stone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MARS_STONE_BRICK_STAIRS = register("mars_stone_brick_stairs", new StairsBlock(MARS_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MARS_STONE_BRICKS = register("chiseled_mars_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MARS_STONE_STAIRS = register("chiseled_mars_stone_stairs", new StairsBlock(CHISELED_MARS_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MARS_STONE_SLAB = register("chiseled_mars_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> POLISHED_MARS_STONE = register("polished_mars_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MARS_STONE_STAIRS = register("polished_mars_stone_stairs", new StairsBlock(POLISHED_MARS_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MARS_STONE_SLAB = register("polished_mars_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> MARS_PILLAR = register("mars_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MARS_STONE_BRICK_WALL = register("mars_stone_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));
	public static final Supplier<Block> CONGLOMERATE = register("conglomerate", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> POLISHED_CONGLOMERATE = register("polished_conglomerate", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));

	// Mercury stones
	public static final Supplier<Block> MERCURY_STONE = register("mercury_stone", new Block(AbstractBlock.Settings.copy(Blocks.STONE)), true);
	public static final Supplier<Block> MERCURY_STONE_STAIRS = register("mercury_stone_stairs", new StairsBlock(MERCURY_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MERCURY_STONE_SLAB = register("mercury_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MERCURY_COBBLESTONE = register("mercury_cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MERCURY_COBBLESTONE_STAIRS = register("mercury_cobblestone_stairs", new StairsBlock(MERCURY_COBBLESTONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> MERCURY_COBBLESTONE_SLAB = register("mercury_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> CRACKED_MERCURY_STONE_BRICKS = register("cracked_mercury_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> MERCURY_STONE_BRICKS = register("mercury_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MERCURY_STONE_BRICK_SLAB = register("mercury_stone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MERCURY_STONE_BRICK_STAIRS = register("mercury_stone_brick_stairs", new StairsBlock(MERCURY_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MERCURY_STONE_BRICKS = register("chiseled_mercury_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MERCURY_STONE_STAIRS = register("chiseled_mercury_stone_stairs", new StairsBlock(CHISELED_MERCURY_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_MERCURY_STONE_SLAB = register("chiseled_mercury_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> POLISHED_MERCURY_STONE = register("polished_mercury_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MERCURY_STONE_STAIRS = register("polished_mercury_stone_stairs", new StairsBlock(POLISHED_MERCURY_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_MERCURY_STONE_SLAB = register("polished_mercury_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> MERCURY_PILLAR = register("mercury_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> MERCURY_STONE_BRICK_WALL = register("mercury_stone_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));

	// Venus stones
	public static final Supplier<Block> VENUS_SAND = register("venus_sand", new FallingBlock(AbstractBlock.Settings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
	public static final Supplier<Block> VENUS_STONE = register("venus_stone", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
	public static final Supplier<Block> VENUS_STONE_STAIRS = register("venus_stone_stairs",
			new StairsBlock(VENUS_STONE.get().getDefaultState(), AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> VENUS_STONE_SLAB = register("venus_stone_slab", new SlabBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> VENUS_COBBLESTONE = register("venus_cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> VENUS_COBBLESTONE_STAIRS = register("venus_cobblestone_stairs", new StairsBlock(VENUS_COBBLESTONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> VENUS_COBBLESTONE_SLAB = register("venus_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> CRACKED_VENUS_STONE_BRICKS = register("cracked_venus_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> VENUS_STONE_BRICKS = register("venus_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> VENUS_STONE_BRICK_SLAB = register("venus_stone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> VENUS_STONE_BRICK_STAIRS = register("venus_stone_brick_stairs", new StairsBlock(VENUS_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_VENUS_STONE_BRICKS = register("chiseled_venus_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_VENUS_STONE_STAIRS = register("chiseled_venus_stone_stairs", new StairsBlock(CHISELED_VENUS_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_VENUS_STONE_SLAB = register("chiseled_venus_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> POLISHED_VENUS_STONE = register("polished_venus_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_VENUS_STONE_STAIRS = register("polished_venus_stone_stairs", new StairsBlock(POLISHED_VENUS_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_VENUS_STONE_SLAB = register("polished_venus_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> VENUS_PILLAR = register("venus_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> VENUS_STONE_BRICK_WALL = register("venus_stone_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));

	// Venus sandstones
	public static final Supplier<Block> VENUS_SANDSTONE = register("venus_sandstone", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> CRACKED_VENUS_SANDSTONE_BRICKS = register("cracked_venus_sandstone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> VENUS_SANDSTONE_BRICKS = register("venus_sandstone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final Supplier<Block> VENUS_SANDSTONE_BRICK_SLAB = register("venus_sandstone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final Supplier<Block> VENUS_SANDSTONE_BRICK_STAIRS = register("venus_sandstone_brick_stairs", new StairsBlock(VENUS_SANDSTONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.SMOOTH_SANDSTONE)));
	public static final Supplier<Block> INFERNAL_SPIRE_BLOCK = register("infernal_spire_block", new Block(AbstractBlock.Settings.copy(Blocks.BASALT)));

	// Glacio stones
	public static final Supplier<Block> GLACIO_STONE = register("glacio_stone", new Block(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()), true);
	public static final Supplier<Block> GLACIO_STONE_STAIRS = register("glacio_stone_stairs",
			new StairsBlock(GLACIO_STONE.get().getDefaultState(), AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> GLACIO_STONE_SLAB = register("glacio_stone_slab", new SlabBlock(AbstractBlock.Settings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
	public static final Supplier<Block> GLACIO_COBBLESTONE = register("glacio_cobblestone", new Block(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> GLACIO_COBBLESTONE_STAIRS = register("glacio_cobblestone_stairs", new StairsBlock(GLACIO_COBBLESTONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> GLACIO_COBBLESTONE_SLAB = register("glacio_cobblestone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)));
	public static final Supplier<Block> CRACKED_GLACIO_STONE_BRICKS = register("cracked_glacio_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> GLACIO_STONE_BRICKS = register("glacio_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> GLACIO_STONE_BRICK_SLAB = register("glacio_stone_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> GLACIO_STONE_BRICK_STAIRS = register("glacio_stone_brick_stairs", new StairsBlock(GLACIO_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_GLACIO_STONE_BRICKS = register("chiseled_glacio_stone_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_GLACIO_STONE_STAIRS = register("chiseled_glacio_stone_stairs", new StairsBlock(CHISELED_GLACIO_STONE_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_GLACIO_STONE_SLAB = register("chiseled_glacio_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> POLISHED_GLACIO_STONE = register("polished_glacio_stone", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_GLACIO_STONE_STAIRS = register("polished_glacio_stone_stairs", new StairsBlock(POLISHED_GLACIO_STONE.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_GLACIO_STONE_SLAB = register("polished_glacio_stone_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> GLACIO_PILLAR = register("glacio_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> GLACIO_STONE_BRICK_WALL = register("glacio_stone_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));
	public static final Supplier<Block> PERMAFROST = register("permafrost", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> PERMAFROST_BRICKS = register("permafrost_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));
	public static final Supplier<Block> PERMAFROST_BRICK_STAIRS = register("permafrost_brick_stairs", new StairsBlock(PERMAFROST_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(PERMAFROST_BRICKS.get())));
	public static final Supplier<Block> PERMAFROST_BRICK_SLAB = register("permafrost_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(PERMAFROST_BRICKS.get())));
	public static final Supplier<Block> CRACKED_PERMAFROST_BRICKS = register("cracked_permafrost_bricks", new Block(AbstractBlock.Settings.copy(Blocks.STONE)));

	public static final Supplier<Block> PERMAFROST_TILES = register("permafrost_tiles", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_PERMAFROST_BRICKS = register("chiseled_permafrost_bricks", new Block(AbstractBlock.Settings.copy(Blocks.CHISELED_STONE_BRICKS)));
	public static final Supplier<Block> CHISELED_PERMAFROST_BRICK_STAIRS = register("chiseled_permafrost_brick_stairs", new StairsBlock(CHISELED_PERMAFROST_BRICKS.get().getDefaultState(), AbstractBlock.Settings.copy(CHISELED_PERMAFROST_BRICKS.get())));
	public static final Supplier<Block> CHISELED_PERMAFROST_BRICK_SLAB = register("chiseled_permafrost_brick_slab", new SlabBlock(AbstractBlock.Settings.copy(CHISELED_PERMAFROST_BRICKS.get())));
	public static final Supplier<Block> POLISHED_PERMAFROST = register("polished_permafrost", new Block(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> POLISHED_PERMAFROST_STAIRS = register("polished_permafrost_stairs", new StairsBlock(POLISHED_PERMAFROST.get().getDefaultState(), AbstractBlock.Settings.copy(POLISHED_PERMAFROST.get())));
	public static final Supplier<Block> POLISHED_PERMAFROST_SLAB = register("polished_permafrost_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.POLISHED_DIORITE)));
	public static final Supplier<Block> PERMAFROST_PILLAR = register("permafrost_pillar", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICKS)));
	public static final Supplier<Block> PERMAFROST_BRICK_WALL = register("permafrost_brick_wall", new WallBlock(AbstractBlock.Settings.copy(Blocks.STONE_BRICK_WALL)));

	// Sign
	public static final SignType GLACIAN = SignTypeInvoker.adastra_invokeRegister(SignTypeInvoker.adastra_init("glacian"));

	// Glacian Wood
	public static final Supplier<Block> GLACIAN_LOG = register("glacian_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).mapColor(MapColor.LIGHT_BLUE_GRAY).slipperiness(0.5f)));
	public static final Supplier<Block> STRIPPED_GLACIAN_LOG = register("stripped_glacian_log", new PillarBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.LIGHT_BLUE_GRAY).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_LEAVES = register("glacian_leaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).nonOpaque().slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_PLANKS = register("glacian_planks", new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_STAIRS = register("glacian_stairs", new StairsBlock(GLACIAN_PLANKS.get().getDefaultState(), AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_SLAB = register("glacian_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_DOOR = register("glacian_door", new DoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_DOOR).slipperiness(0.5f)), true);
	public static final Supplier<Block> GLACIAN_TRAPDOOR = register("glacian_trapdoor", new TrapdoorBlock(AbstractBlock.Settings.copy(Blocks.OAK_TRAPDOOR).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_FENCE = register("glacian_fence", new FenceBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE).slipperiness(0.5f).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_FENCE_GATE = register("glacian_fence_gate", new FenceGateBlock(AbstractBlock.Settings.copy(Blocks.OAK_FENCE_GATE).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_BUTTON = register("glacian_button", new WoodenButtonBlock(AbstractBlock.Settings.copy(Blocks.OAK_BUTTON)));
	public static final Supplier<Block> GLACIAN_PRESSURE_PLATE = register("glacian_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(Blocks.OAK_PRESSURE_PLATE).slipperiness(0.5f)));
	public static final Supplier<Block> GLACIAN_SIGN = register("glacian_sign", new SignBlock(AbstractBlock.Settings.copy(Blocks.OAK_SIGN), GLACIAN));
	public static final Supplier<Block> GLACIAN_WALL_SIGN = register("glacian_wall_sign", new WallSignBlock(AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN).dropsLike(GLACIAN_SIGN.get()), GLACIAN), true);
	public static final Supplier<Block> GLACIAN_FUR = register("glacian_fur", new Block(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL)));

	// Ores
	public static final Supplier<Block> MOON_CHEESE_ORE = register("moon_cheese_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
	public static final Supplier<Block> MOON_DESH_ORE = register("moon_desh_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> MOON_IRON_ORE = register("moon_iron_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> MOON_ICE_SHARD_ORE = register("moon_ice_shard_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
	public static final Supplier<Block> MARS_IRON_ORE = register("mars_iron_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> MARS_DIAMOND_ORE = register("mars_diamond_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
	public static final Supplier<Block> MARS_OSTRUM_ORE = register("mars_ostrum_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> MARS_ICE_SHARD_ORE = register("mars_ice_shard_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
	public static final Supplier<Block> MERCURY_IRON_ORE = register("mercury_iron_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> VENUS_COAL_ORE = register("venus_coal_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
	public static final Supplier<Block> VENUS_GOLD_ORE = register("venus_gold_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> VENUS_DIAMOND_ORE = register("venus_diamond_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
	public static final Supplier<Block> VENUS_CALORITE_ORE = register("venus_calorite_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> GLACIO_ICE_SHARD_ORE = register("glacio_ice_shard_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
	public static final Supplier<Block> GLACIO_COAL_ORE = register("glacio_coal_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
	public static final Supplier<Block> GLACIO_COPPER_ORE = register("glacio_copper_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> GLACIO_IRON_ORE = register("glacio_iron_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE)), true);
	public static final Supplier<Block> GLACIO_LAPIS_ORE = register("glacio_lapis_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);
	public static final Supplier<Block> DEEPSLATE_ICE_SHARD_ORE = register("deepslate_ice_shard_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE), UniformIntProvider.create(3, 6)), true);
	public static final Supplier<Block> DEEPSLATE_DESH_ORE = register("deepslate_desh_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);
	public static final Supplier<Block> DEEPSLATE_OSTRUM_ORE = register("deepslate_ostrum_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);
	public static final Supplier<Block> DEEPSLATE_CALORITE_ORE = register("deepslate_calorite_ore", new OreBlock(AbstractBlock.Settings.copy(Blocks.DEEPSLATE_IRON_ORE)), true);

	static Supplier<Block> register(String id, Block block) {
		return BLOCKS.register(id, () -> block);
	}

	static Supplier<Block> register(String id, Block block, boolean excludeFromLootTable) {
		Supplier<Block> registered = BLOCKS.register(id, () -> block);
		if (!excludeFromLootTable) {
			blocks.add(block);
		}
		return registered;
	}
}