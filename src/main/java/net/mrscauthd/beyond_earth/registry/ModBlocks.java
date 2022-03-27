package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.blocks.coal_torches.CoalLanternBlock;
import net.mrscauthd.beyond_earth.blocks.coal_torches.CoalTorchBlock;
import net.mrscauthd.beyond_earth.blocks.launch_pad.RocketLaunchPad;
import net.mrscauthd.beyond_earth.blocks.coal_torches.WallCoalTorchBlock;
import net.mrscauthd.beyond_earth.blocks.flags.FlagBlock;
import net.mrscauthd.beyond_earth.blocks.machines.*;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModBlocks {

    // Rocket Launch Pad.
    public static final Block ROCKET_LAUNCH_PAD = new RocketLaunchPad(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool()); // Temp

    // Flag Blocks.
    public static final Block FLAG_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_BLUE_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_BROWN_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_CYAN_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_GRAY_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_GREEN_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_LIGHT_BLUE_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_LIME_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_MAGENTA_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_ORANGE_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_PINK_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_PURPLE_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_RED_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));
    public static final Block FLAG_YELLOW_BLOCK = new FlagBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.0f, 1.0f).luminance(1));

    // Torch blocks.
    public static final Block COAL_TORCH = new CoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD));
    public static final Block WALL_COAL_TORCH = new WallCoalTorchBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOD));
    public static final Block COAL_LANTERN = new CoalLanternBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.LANTERN).strength(3.5f).nonOpaque());

    // Machines.
    public static final Block FUEL_REFINERY = new FuelRefineryBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block COMPRESSOR = new CompressorBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block COAL_GENERATOR = new CoalGeneratorBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block OXYGEN_LOADER = new OxygenLoaderBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block SOLAR_PANEL = new SolarPanelBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block NASA_WORKBENCH = new NasaWorkbenchBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block OXYGEN_BUBBLE_DISTRIBUTOR = new OxygenBubbleDistributorBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).requiresTool());
    public static final Block WATER_PUMP = new WaterPump(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 1.0f).nonOpaque().requiresTool());

    // Blocks.
    public static final Block STEEL_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block DESH_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block OSTRUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block CALORITE_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(5.0f, 2.5f).requiresTool());
    public static final Block RAW_DESH_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block RAW_OSTRUM_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block RAW_CALORITE_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(1.5f, 1.0f).requiresTool());
    public static final Block IRON_PLATING_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block RUSTED_IRON_PILLAR_BLOCK = new PillarBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block RUSTED_IRON_PLATING_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block BLUE_IRON_PLATING_BLOCK = new PillarBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());
    public static final Block IRON_MARK_BLOCK = new PillarBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 2.5f).requiresTool());

    public static final Block MOON_STONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_MOON_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MOON_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MOON_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MOON_STONE_BRICK_STAIRS = new StairsBlock(MOON_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(MOON_STONE_BRICKS));

    public static final Block SKY_STONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());

    public static final Block MARS_STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_MARS_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MARS_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MARS_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MARS_STONE_BRICK_STAIRS = new StairsBlock(MARS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(MARS_STONE_BRICKS));

    public static final Block MERCURY_STONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_MERCURY_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MERCURY_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MERCURY_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block MERCURY_STONE_BRICK_STAIRS = new StairsBlock(MERCURY_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(MERCURY_STONE_BRICKS));

    public static final Block VENUS_SANDSTONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_VENUS_SANDSTONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_SANDSTONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_SANDSTONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_SANDSTONE_BRICK_STAIRS = new StairsBlock(VENUS_SANDSTONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(VENUS_SANDSTONE_BRICKS));

    public static final Block VENUS_STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_VENUS_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block VENUS_STONE_BRICK_STAIRS = new StairsBlock(VENUS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(VENUS_STONE_BRICKS));

    public static final Block GLACIO_STONE = new Block(FabricBlockSettings.of(Material.STONE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block PERMAFROST_STONE = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block CRACKED_GLACIO_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block GLACIO_STONE_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block GLACIO_STONE_BRICK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());
    public static final Block GLACIO_STONE_BRICK_STAIRS = new StairsBlock(VENUS_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copy(VENUS_STONE_BRICKS));

    public static final Block INFERNAL_SPIRE_BLOCK = new Block(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 1.0f).requiresTool());

    // Falling Block.
    public static final Block MOON_SAND = new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.GRAY).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f));
    public static final Block MARS_SAND = new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f));
    public static final Block VENUS_SAND = new FallingBlock(FabricBlockSettings.of(Material.AGGREGATE, MapColor.TERRACOTTA_ORANGE).sounds(BlockSoundGroup.SAND).strength(0.5f, 0.5f));

    // Ores.
    public static final Block MOON_CHEESE_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block MOON_DESH_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block MOON_IRON_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block MOON_ICE_SHARD_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(0, 2));
    public static final Block MARS_IRON_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block MARS_DIAMOND_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(3, 7));
    public static final Block MARS_OSTRUM_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block MARS_ICE_SHARD_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(0, 2));
    public static final Block MERCURY_IRON_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block VENUS_COAL_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(0, 2));
    public static final Block VENUS_GOLD_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block VENUS_DIAMOND_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(3, 7));
    public static final Block VENUS_CALORITE_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block GLACIO_ICE_SHARD_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(0, 2));
    public static final Block GLACIO_COAL_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(0, 2));
    public static final Block GLACIO_COPPER_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block GLACIO_IRON_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool());
    public static final Block GLACIO_LAPIS_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(2, 5));

    public static void register() {

        // Rocket Launch Pad.
        register("rocket_launch_pad", ROCKET_LAUNCH_PAD);

        // Flag Blocks.
        register("flag", FLAG_BLOCK);
        register("flag_blue", FLAG_BLUE_BLOCK);
        register("flag_brown", FLAG_BROWN_BLOCK);
        register("flag_cyan", FLAG_CYAN_BLOCK);
        register("flag_gray", FLAG_GRAY_BLOCK);
        register("flag_green", FLAG_GREEN_BLOCK);
        register("flag_light_blue", FLAG_LIGHT_BLUE_BLOCK);
        register("flag_lime", FLAG_LIME_BLOCK);
        register("flag_magenta", FLAG_MAGENTA_BLOCK);
        register("flag_orange", FLAG_ORANGE_BLOCK);
        register("flag_pink", FLAG_PINK_BLOCK);
        register("flag_purple", FLAG_PURPLE_BLOCK);
        register("flag_red", FLAG_RED_BLOCK);
        register("flag_yellow", FLAG_YELLOW_BLOCK);

        // Torch blocks.
        register("coal_torch", COAL_TORCH);
        register("wall_coal_torch", WALL_COAL_TORCH);
        register("coal_lantern", COAL_LANTERN);

        // Machines.
        register("fuel_refinery", FUEL_REFINERY);
        register("compressor", COMPRESSOR);
        register("coal_generator", COAL_GENERATOR);
        register("oxygen_loader", OXYGEN_LOADER);
        register("solar_panel", SOLAR_PANEL);
        register("nasa_workbench", NASA_WORKBENCH);
        register("oxygen_bubble_distributor", OXYGEN_BUBBLE_DISTRIBUTOR);
        register("water_pump", WATER_PUMP);

        // Blocks.
        register("steel_block", STEEL_BLOCK);
        register("desh_block", DESH_BLOCK);
        register("ostrum_block", OSTRUM_BLOCK);
        register("calorite_block", CALORITE_BLOCK);
        register("raw_desh_block", RAW_DESH_BLOCK);
        register("raw_ostrum_block", RAW_OSTRUM_BLOCK);
        register("raw_calorite_block", RAW_CALORITE_BLOCK);
        register("iron_plating_block", IRON_PLATING_BLOCK);
        register("rusted_iron_pillar_block", RUSTED_IRON_PILLAR_BLOCK);
        register("rusted_iron_plating_block", RUSTED_IRON_PLATING_BLOCK);
        register("blue_iron_plating_block", BLUE_IRON_PLATING_BLOCK);
        register("iron_mark_block", IRON_MARK_BLOCK);

        register("moon_stone", MOON_STONE);
        register("cracked_moon_stone_bricks", CRACKED_MOON_STONE_BRICKS);
        register("moon_stone_bricks", MOON_STONE_BRICKS);
        register("moon_stone_brick_slab", MOON_STONE_BRICK_SLAB);
        register("moon_stone_brick_stairs", MOON_STONE_BRICK_STAIRS);

        register("sky_stone", SKY_STONE);

        register("mars_stone", MARS_STONE);
        register("cracked_mars_stone_bricks", CRACKED_MARS_STONE_BRICKS);
        register("mars_stone_bricks", MARS_STONE_BRICKS);
        register("mars_stone_brick_slab", MARS_STONE_BRICK_SLAB);
        register("mars_stone_brick_stairs", MARS_STONE_BRICK_STAIRS);

        register("mercury_stone", MERCURY_STONE);
        register("cracked_mercury_stone_bricks", CRACKED_MERCURY_STONE_BRICKS);
        register("mercury_stone_bricks", MERCURY_STONE_BRICKS);
        register("mercury_stone_brick_slab", MERCURY_STONE_BRICK_SLAB);
        register("mercury_stone_brick_stairs", MERCURY_STONE_BRICK_STAIRS);

        register("venus_sandstone", VENUS_SANDSTONE);
        register("cracked_venus_sandstone_bricks", CRACKED_VENUS_SANDSTONE_BRICKS);
        register("venus_sandstone_bricks", VENUS_SANDSTONE_BRICKS);
        register("venus_sandstone_brick_slab", VENUS_SANDSTONE_BRICK_SLAB);
        register("venus_sandstone_brick_stairs", VENUS_SANDSTONE_BRICK_STAIRS);

        register("venus_stone", VENUS_STONE);
        register("cracked_venus_stone_bricks", CRACKED_VENUS_STONE_BRICKS);
        register("venus_stone_bricks", VENUS_STONE_BRICKS);
        register("venus_stone_brick_slab", VENUS_STONE_BRICK_SLAB);
        register("venus_stone_brick_stairs", VENUS_STONE_BRICK_STAIRS);

        register("glacio_stone", GLACIO_STONE);
        register("permafrost", PERMAFROST_STONE);
        register("cracked_glacio_stone_bricks", CRACKED_GLACIO_STONE_BRICKS);
        register("glacio_stone_bricks", GLACIO_STONE_BRICKS);
        register("glacio_stone_brick_slab", GLACIO_STONE_BRICK_SLAB);
        register("glacio_stone_brick_stairs", GLACIO_STONE_BRICK_STAIRS);

        register("infernal_spire_block", INFERNAL_SPIRE_BLOCK);

        // Falling Block.
        register("moon_sand", MOON_SAND);
        register("mars_sand", MARS_SAND);
        register("venus_sand", VENUS_SAND);

        // Ores.
        register("moon_cheese_ore", MOON_CHEESE_ORE);
        register("moon_desh_ore", MOON_DESH_ORE);
        register("moon_iron_ore", MOON_IRON_ORE);
        register("moon_ice_shard_ore", MOON_ICE_SHARD_ORE);
        register("mars_iron_ore", MARS_IRON_ORE);
        register("mars_diamond_ore", MARS_DIAMOND_ORE);
        register("mars_ostrum_ore", MARS_OSTRUM_ORE);
        register("mars_ice_shard_ore", MARS_ICE_SHARD_ORE);
        register("mercury_iron_ore", MERCURY_IRON_ORE);
        register("venus_coal_ore", VENUS_COAL_ORE);
        register("venus_gold_ore", VENUS_GOLD_ORE);
        register("venus_diamond_ore", VENUS_DIAMOND_ORE);
        register("venus_calorite_ore", VENUS_CALORITE_ORE);
        register("glacio_ice_shard_ore", GLACIO_ICE_SHARD_ORE);
        register("glacio_coal_ore", GLACIO_COAL_ORE);
        register("glacio_copper_ore", GLACIO_COPPER_ORE);
        register("glacio_iron_ore", GLACIO_IRON_ORE);
        register("glacio_lapis_ore", GLACIO_LAPIS_ORE);
    }

    public static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new ModIdentifier(id), block);
    }
}
