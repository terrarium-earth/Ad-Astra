package com.github.alexnijjar.ad_astra.registry;

import java.util.HashSet;
import java.util.Set;

import com.github.alexnijjar.ad_astra.blocks.coal_torches.CoalLanternBlock;
import com.github.alexnijjar.ad_astra.blocks.coal_torches.CoalTorchBlock;
import com.github.alexnijjar.ad_astra.blocks.coal_torches.WallCoalTorchBlock;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlock;
import com.github.alexnijjar.ad_astra.blocks.globes.GlobeBlock;
import com.github.alexnijjar.ad_astra.blocks.launch_pad.RocketLaunchPad;
import com.github.alexnijjar.ad_astra.blocks.machines.CoalGeneratorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.CompressorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.EnergizerBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.FuelRefineryBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.NasaWorkbenchBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.OxygenDistributorBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.OxygenLoaderBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.SolarPanelBlock;
import com.github.alexnijjar.ad_astra.blocks.machines.WaterPumpBlock;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public interface ModBlocks {

        public static Set<Block> blocks = new HashSet<>();

        // Rocket Launch Pad
        public static final Block ROCKET_LAUNCH_PAD = register("rocket_launch_pad", new RocketLaunchPad(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));

        // Flag Blocks
        public static final Block FLAG = register("flag", new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1)));
        public static final Block FLAG_BLUE = register("flag_blue", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_BROWN = register("flag_brown", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_CYAN = register("flag_cyan", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_GRAY = register("flag_gray", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_GREEN = register("flag_green", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_LIGHT_BLUE = register("flag_light_blue", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_LIME = register("flag_lime", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_MAGENTA = register("flag_magenta", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_ORANGE = register("flag_orange", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_PINK = register("flag_pink", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_PURPLE = register("flag_purple", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_RED = register("flag_red", new FlagBlock(FabricBlockSettings.copy(FLAG)));
        public static final Block FLAG_YELLOW = register("flag_yellow", new FlagBlock(FabricBlockSettings.copy(FLAG)));

        // Globes
        public static final Block EARTH_GLOBE = register("earth_globe", new GlobeBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.5f).nonOpaque().requiresTool()));
        public static final Block MOON_GLOBE = register("moon_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
        public static final Block MARS_GLOBE = register("mars_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
        public static final Block MERCURY_GLOBE = register("mercury_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
        public static final Block VENUS_GLOBE = register("venus_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));
        public static final Block GLACIO_GLOBE = register("glacio_globe", new GlobeBlock(FabricBlockSettings.copy(EARTH_GLOBE)));

        // Torch blocks
        public static final Block COAL_TORCH = register("coal_torch", new CoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)));
        public static final Block WALL_COAL_TORCH = register("wall_coal_torch", new WallCoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD)), true);
        public static final Block COAL_LANTERN = register("coal_lantern", new CoalLanternBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.LANTERN).strength(3.5f).nonOpaque()));

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

        // Blocks
        public static final Block STEEL_BLOCK = register("steel_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block DESH_BLOCK = register("desh_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block OSTRUM_BLOCK = register("ostrum_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block CALORITE_BLOCK = register("calorite_block", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block RAW_DESH_BLOCK = register("raw_desh_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));
        public static final Block RAW_OSTRUM_BLOCK = register("raw_ostrum_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));
        public static final Block RAW_CALORITE_BLOCK = register("raw_calorite_block", new Block(FabricBlockSettings.copy(Blocks.RAW_IRON_BLOCK)));

        public static final Block IRON_PLATING = register("iron_plating", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block IRON_PILLAR = register("iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block MARKED_IRON_PILLAR = register("marked_iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block BLUE_IRON_PILLAR = register("blue_iron_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).luminance(state -> 15)));
        public static final Block STEEL_PLATING = register("steel_plating", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block STEEL_PILLAR = register("steel_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block DESH_PLATING = register("desh_plating", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block DESH_PILLAR = register("desh_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block OSTRUM_PLATING = register("ostrum_plating", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block OSTRUM_PILLAR = register("ostrum_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block CALORITE_PLATING = register("calorite_plating", new Block(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));
        public static final Block CALORITE_PILLAR = register("calorite_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)));

        public static final Block SKY_STONE = register("sky_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)));

        // Moon stones
        public static final Block MOON_STONE = register("moon_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)));
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

        // Mars stones
        public static final Block MARS_STONE = register("mars_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
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

        // Mercury stones
        public static final Block MERCURY_STONE = register("mercury_stone", new Block(FabricBlockSettings.copy(Blocks.STONE)));
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

        // Venus sandstones
        public static final Block VENUS_SANDSTONE = register("venus_sandstone", new Block(FabricBlockSettings.copy(Blocks.STONE)));
        public static final Block CRACKED_VENUS_SANDSTONE_BRICKS = register("cracked_venus_sandstone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
        public static final Block VENUS_SANDSTONE_BRICKS = register("venus_sandstone_bricks", new Block(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
        public static final Block VENUS_SANDSTONE_BRICK_SLAB = register("venus_sandstone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
        public static final Block VENUS_SANDSTONE_BRICK_STAIRS = register("venus_sandstone_brick_stairs", new StairsBlock(VENUS_SANDSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));

        // Venus stones
        public static final Block VENUS_STONE = register("venus_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
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

        // Glacio stones
        public static final Block GLACIO_STONE = register("glacio_stone", new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool()));
        public static final Block PERMAFROST_STONE = register("permafrost", new Block(FabricBlockSettings.copy(Blocks.STONE)));
        public static final Block CRACKED_GLACIO_STONE_BRICKS = register("cracked_glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE)));
        public static final Block GLACIO_STONE_BRICKS = register("glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
        public static final Block GLACIO_STONE_BRICK_SLAB = register("glacio_stone_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
        public static final Block GLACIO_STONE_BRICK_STAIRS = register("glacio_stone_brick_stairs", new StairsBlock(VENUS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
        public static final Block CHISELED_GLACIO_STONE_BRICKS = register("chiseled_glacio_stone_bricks", new Block(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
        public static final Block CHISELED_GLACIO_STONE_STAIRS = register("chiseled_glacio_stone_stairs", new StairsBlock(CHISELED_GLACIO_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
        public static final Block CHISELED_GLACIO_STONE_SLAB = register("chiseled_glacio_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.CHISELED_STONE_BRICKS)));
        public static final Block POLISHED_GLACIO_STONE = register("polished_glacio_stone", new Block(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
        public static final Block POLISHED_GLACIO_STONE_STAIRS = register("polished_glacio_stone_stairs", new StairsBlock(POLISHED_GLACIO_STONE.getDefaultState(), FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
        public static final Block POLISHED_GLACIO_STONE_SLAB = register("polished_glacio_stone_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.POLISHED_DIORITE)));
        public static final Block GLACIO_PILLAR = register("glacio_pillar", new PillarBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));

        public static final Block INFERNAL_SPIRE_BLOCK = register("infernal_spire_block", new Block(FabricBlockSettings.copy(Blocks.STONE)));

        // Falling Block
        public static final Block MOON_SAND = register("moon_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.GRAY).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
        public static final Block MARS_SAND = register("mars_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));
        public static final Block VENUS_SAND = register("venus_sand", new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f)));

        // Ores
        public static final Block MOON_CHEESE_ORE = register("moon_cheese_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block MOON_DESH_ORE = register("moon_desh_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block MOON_IRON_ORE = register("moon_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block MOON_ICE_SHARD_ORE = register("moon_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block MARS_IRON_ORE = register("mars_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block MARS_DIAMOND_ORE = register("mars_diamond_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
        public static final Block MARS_OSTRUM_ORE = register("mars_ostrum_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block MARS_ICE_SHARD_ORE = register("mars_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block MERCURY_IRON_ORE = register("mercury_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block VENUS_COAL_ORE = register("venus_coal_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block VENUS_GOLD_ORE = register("venus_gold_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block VENUS_DIAMOND_ORE = register("venus_diamond_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(3, 7)), true);
        public static final Block VENUS_CALORITE_ORE = register("venus_calorite_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block GLACIO_ICE_SHARD_ORE = register("glacio_ice_shard_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block GLACIO_COAL_ORE = register("glacio_coal_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(0, 2)), true);
        public static final Block GLACIO_COPPER_ORE = register("glacio_copper_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block GLACIO_IRON_ORE = register("glacio_iron_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE)), true);
        public static final Block GLACIO_LAPIS_ORE = register("glacio_lapis_ore", new OreBlock(FabricBlockSettings.copy(Blocks.IRON_ORE), UniformIntProvider.create(2, 5)), true);

        public static Block register(String id, Block block) {
                return register(id, block, false);
        }

        public static Block register(String id, Block block, boolean exclude) {
                Registry.register(Registry.BLOCK, new ModIdentifier(id), block);
                if (!exclude) {
                        blocks.add(block);
                }
                return block;
        }
}