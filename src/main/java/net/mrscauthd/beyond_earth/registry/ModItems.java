package net.mrscauthd.beyond_earth.registry;

import java.util.List;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import net.mrscauthd.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import net.mrscauthd.beyond_earth.blocks.machines.entity.WaterPumpBlockEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier1;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier2;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier3;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntityTier4;
import net.mrscauthd.beyond_earth.items.GuideBook;
import net.mrscauthd.beyond_earth.items.HammerItem;
import net.mrscauthd.beyond_earth.items.vehicles.RocketItem;
import net.mrscauthd.beyond_earth.items.vehicles.RoverItem;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModItems {

        public static final ItemGroup ITEM_GROUP_NORMAL = FabricItemGroupBuilder.build(new ModIdentifier("tab_normal"), () -> new ItemStack(ModItems.TIER_1_ROCKET));
        public static final ItemGroup ITEM_GROUP_MACHINES = FabricItemGroupBuilder.build(new ModIdentifier("tab_machines"), () -> new ItemStack(ModBlocks.NASA_WORKBENCH));
        public static final ItemGroup ITEM_GROUP_BASICS = FabricItemGroupBuilder.build(new ModIdentifier("tab_basics"), () -> new ItemStack(ModItems.DESH_ENGINE));
        public static final ItemGroup ITEM_GROUP_MATERIALS = FabricItemGroupBuilder.build(new ModIdentifier("tab_materials"), () -> new ItemStack(ModItems.IRON_PLATE));
        public static final ItemGroup ITEM_GROUP_FLAGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_flags"), () -> new ItemStack(ModBlocks.FLAG_PURPLE));
        public static final ItemGroup ITEM_GROUP_GLOBES = FabricItemGroupBuilder.build(new ModIdentifier("tab_globes"), () -> new ItemStack(ModItems.GLACIO_GLOBE));
        public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(new ModIdentifier("tab_blocks"), () -> new ItemStack(ModBlocks.MOON_IRON_ORE));
        public static final ItemGroup ITEM_GROUP_SPAWN_EGGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_spawn_eggs"), () -> new ItemStack(ModItems.ALIEN_SPAWN_EGG));

        // Spawn eggs.
        public static final Item ALIEN_SPAWN_EGG = new SpawnEggItem(ModEntities.ALIEN, -13382401, -11650781, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item ALIEN_ZOMBIE_SPAWN_EGG = new SpawnEggItem(ModEntities.ALIEN_ZOMBIE, -14804199, -16740159, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item STAR_CRAWLER_SPAWN_EGG = new SpawnEggItem(ModEntities.STAR_CRAWLER, -13421773, -16724788, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item PYGRO_SPAWN_EGG = new SpawnEggItem(ModEntities.PYGRO, -3381760, -6750208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item PYGRO_BRUTE_SPAWN_EGG = new SpawnEggItem(ModEntities.PYGRO_BRUTE, -3381760, -67208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item MOGLER_SPAWN_EGG = new SpawnEggItem(ModEntities.MOGLER, -13312, -3407872, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));
        public static final Item MARTIAN_RAPTOR_SPAWN_EGG = new SpawnEggItem(ModEntities.MARTIAN_RAPTOR, 5349438, -13312, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS));

        // Vehicles Items.
        public static final Item TIER_1_ROCKET = new RocketItem<RocketEntityTier1>(ModEntities.TIER_1_ROCKET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));
        public static final Item TIER_2_ROCKET = new RocketItem<RocketEntityTier2>(ModEntities.TIER_2_ROCKET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));
        public static final Item TIER_3_ROCKET = new RocketItem<RocketEntityTier3>(ModEntities.TIER_3_ROCKET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));
        public static final Item TIER_4_ROCKET = new RocketItem<RocketEntityTier4>(ModEntities.TIER_4_ROCKET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));
        public static final Item ROVER = new RoverItem(new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));

        // Guide book
        public static final Item GUIDE_BOOK = new GuideBook(new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1));

        // Items.
        public static final Item CHEESE = new Item(new FabricItemSettings().group(ITEM_GROUP_NORMAL).food(new FoodComponent.Builder().hunger(4).saturationModifier(3.0f).build()));
        public static final Item HAMMER = new HammerItem(new FabricItemSettings().group(ITEM_GROUP_BASICS).maxCount(1).maxDamage(9));
        public static final Item IRON_STICK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item OXYGEN_GEAR = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item OXYGEN_TANK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item WHEEL = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item ENGINE_FRAME = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item ENGINE_FAN = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item ROCKET_NOSE_CONE = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item STEEL_ENGINE = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item DESH_ENGINE = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item OSTRUM_ENGINE = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item CALORITE_ENGINE = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item STEEL_TANK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item DESH_TANK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item OSTRUM_TANK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item CALORITE_TANK = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item ROCKET_FIN = new Item(new FabricItemSettings().group(ITEM_GROUP_BASICS));

        public static final Item STEEL_INGOT = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item DESH_INGOT = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item OSTRUM_INGOT = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item CALORITE_INGOT = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        public static final Item ICE_SHARD = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        public static final Item IRON_PLATE = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item DESH_PLATE = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        public static final Item COMPRESSED_STEEL = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item COMPRESSED_DESH = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item COMPRESSED_OSTRUM = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item COMPRESSED_CALORITE = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        public static final Item STEEL_NUGGET = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item DESH_NUGGET = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item OSTRUM_NUGGET = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item CALORITE_NUGGET = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        public static final Item RAW_DESH = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item RAW_OSTRUM = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));
        public static final Item RAW_CALORITE = new Item(new FabricItemSettings().group(ITEM_GROUP_MATERIALS));

        // Flag Items
        public static final BlockItem FLAG = new TallBlockItem(ModBlocks.FLAG, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_BLUE = new TallBlockItem(ModBlocks.FLAG_BLUE, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_BROWN = new TallBlockItem(ModBlocks.FLAG_BROWN, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_CYAN = new TallBlockItem(ModBlocks.FLAG_CYAN, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_GRAY = new TallBlockItem(ModBlocks.FLAG_GRAY, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_GREEN = new TallBlockItem(ModBlocks.FLAG_GREEN, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_LIGHT_BLUE = new TallBlockItem(ModBlocks.FLAG_LIGHT_BLUE, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_LIME = new TallBlockItem(ModBlocks.FLAG_LIME, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_MAGENTA = new TallBlockItem(ModBlocks.FLAG_MAGENTA, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_ORANGE = new TallBlockItem(ModBlocks.FLAG_ORANGE, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_PINK = new TallBlockItem(ModBlocks.FLAG_PINK, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_PURPLE = new TallBlockItem(ModBlocks.FLAG_PURPLE, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_RED = new TallBlockItem(ModBlocks.FLAG_RED, new Item.Settings().group(ITEM_GROUP_FLAGS));
        public static final BlockItem FLAG_YELLOW = new TallBlockItem(ModBlocks.FLAG_YELLOW, new Item.Settings().group(ITEM_GROUP_FLAGS));

        // Globes.
        public static final BlockItem EARTH_GLOBE = new BlockItem(ModBlocks.EARTH_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
        public static final BlockItem MOON_GLOBE = new BlockItem(ModBlocks.MOON_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
        public static final BlockItem MARS_GLOBE = new BlockItem(ModBlocks.MARS_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
        public static final BlockItem MERCURY_GLOBE = new BlockItem(ModBlocks.MERCURY_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
        public static final BlockItem VENUS_GLOBE = new BlockItem(ModBlocks.VENUS_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
        public static final BlockItem GLACIO_GLOBE = new BlockItem(ModBlocks.GLACIO_GLOBE, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));

        // Torch items.
        public static final Item COAL_TORCH = new WallStandingBlockItem(ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH, new FabricItemSettings().group(ITEM_GROUP_BASICS));
        public static final Item COAL_LANTERN = new BlockItem(ModBlocks.COAL_LANTERN, new Item.Settings().group(ITEM_GROUP_BASICS));

        // Machines.
        public static final BlockItem NASA_WORKBENCH = new BlockItem(ModBlocks.NASA_WORKBENCH, new Item.Settings().group(ITEM_GROUP_MACHINES).rarity(Rarity.UNCOMMON));
        public static final BlockItem SOLAR_PANEL = new BlockItem(ModBlocks.SOLAR_PANEL, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((new TranslatableText("item.beyond_earth.generator.tooltip", SolarPanelBlockEntity.ENERGY_PER_TICK).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        };
        public static final BlockItem COAL_GENERATOR = new BlockItem(ModBlocks.COAL_GENERATOR, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((new TranslatableText("item.beyond_earth.generator.tooltip", CoalGeneratorBlockEntity.ENERGY_PER_TICK).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        };
        public static final BlockItem COMPRESSOR = new BlockItem(ModBlocks.COMPRESSOR, new Item.Settings().group(ITEM_GROUP_MACHINES));
        public static final BlockItem FUEL_REFINERY = new BlockItem(ModBlocks.FUEL_REFINERY, new Item.Settings().group(ITEM_GROUP_MACHINES));
        public static final BlockItem OXYGEN_LOADER = new BlockItem(ModBlocks.OXYGEN_LOADER, new Item.Settings().group(ITEM_GROUP_MACHINES));
        public static final BlockItem OXYGEN_BUBBLE_DISTRIBUTOR = new BlockItem(ModBlocks.OXYGEN_BUBBLE_DISTRIBUTOR, new Item.Settings().group(ITEM_GROUP_MACHINES));
        public static final BlockItem WATER_PUMP = new BlockItem(ModBlocks.WATER_PUMP, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((new TranslatableText("item.beyond_earth.water_pump.tooltip", WaterPumpBlockEntity.TRANSFER_PER_TICK).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        };

        // Block Items.
        public static final BlockItem STEEL_BLOCK = new BlockItem(ModBlocks.STEEL_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem DESH_BLOCK = new BlockItem(ModBlocks.DESH_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem OSTRUM_BLOCK = new BlockItem(ModBlocks.OSTRUM_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CALORITE_BLOCK = new BlockItem(ModBlocks.CALORITE_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem RAW_DESH_BLOCK = new BlockItem(ModBlocks.RAW_DESH_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem RAW_OSTRUM_BLOCK = new BlockItem(ModBlocks.RAW_OSTRUM_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem RAW_CALORITE_BLOCK = new BlockItem(ModBlocks.RAW_CALORITE_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem IRON_PLATING_BLOCK = new BlockItem(ModBlocks.IRON_PLATING_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem RUSTED_IRON_PILLAR_BLOCK = new BlockItem(ModBlocks.RUSTED_IRON_PILLAR_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem RUSTED_IRON_PLATING_BLOCK = new BlockItem(ModBlocks.RUSTED_IRON_PLATING_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem BLUE_IRON_PLATING_BLOCK = new BlockItem(ModBlocks.BLUE_IRON_PLATING_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem INFERNAL_SPIRE_BLOCK = new BlockItem(ModBlocks.INFERNAL_SPIRE_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem IRON_MARK_BLOCK = new BlockItem(ModBlocks.IRON_MARK_BLOCK, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem SKY_STONE = new BlockItem(ModBlocks.SKY_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem MOON_STONE = new BlockItem(ModBlocks.MOON_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_STONE_BRICKS = new BlockItem(ModBlocks.MOON_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_MOON_STONE_BRICKS = new BlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_STONE_BRICK_SLAB = new BlockItem(ModBlocks.MOON_STONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_STONE_BRICK_STAIRS = new BlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem MARS_STONE = new BlockItem(ModBlocks.MARS_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_STONE_BRICKS = new BlockItem(ModBlocks.MARS_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_MARS_STONE_BRICKS = new BlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_STONE_BRICK_SLAB = new BlockItem(ModBlocks.MARS_STONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_STONE_BRICK_STAIRS = new BlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem MERCURY_STONE = new BlockItem(ModBlocks.MERCURY_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MERCURY_STONE_BRICKS = new BlockItem(ModBlocks.MERCURY_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_MERCURY_STONE_BRICKS = new BlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MERCURY_STONE_BRICK_SLAB = new BlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MERCURY_STONE_BRICK_STAIRS = new BlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem VENUS_STONE = new BlockItem(ModBlocks.VENUS_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_STONE_BRICKS = new BlockItem(ModBlocks.VENUS_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_VENUS_STONE_BRICKS = new BlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_STONE_BRICK_SLAB = new BlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_STONE_BRICK_STAIRS = new BlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem VENUS_SANDSTONE = new BlockItem(ModBlocks.VENUS_SANDSTONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_SANDSTONE_BRICKS = new BlockItem(ModBlocks.VENUS_SANDSTONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_VENUS_SANDSTONE_BRICKS = new BlockItem(ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_SANDSTONE_BRICK_SLAB = new BlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_SANDSTONE_BRICK_STAIRS = new BlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem PERMAFROST = new BlockItem(ModBlocks.PERMAFROST_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_STONE = new BlockItem(ModBlocks.GLACIO_STONE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_STONE_BRICKS = new BlockItem(ModBlocks.GLACIO_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem CRACKED_GLACIO_STONE_BRICKS = new BlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_STONE_BRICK_SLAB = new BlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_STONE_BRICK_STAIRS = new BlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem ROCKET_LAUNCH_PAD = new BlockItem(ModBlocks.ROCKET_LAUNCH_PAD, new Item.Settings().group(ITEM_GROUP_NORMAL));

        public static final BlockItem MOON_SAND = new BlockItem(ModBlocks.MOON_SAND, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_SAND = new BlockItem(ModBlocks.MARS_SAND, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_SAND = new BlockItem(ModBlocks.VENUS_SAND, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static final BlockItem MOON_CHEESE_ORE = new BlockItem(ModBlocks.MOON_CHEESE_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_DESH_ORE = new BlockItem(ModBlocks.MOON_DESH_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_IRON_ORE = new BlockItem(ModBlocks.MOON_IRON_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MOON_ICE_SHARD = new BlockItem(ModBlocks.MOON_ICE_SHARD_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_IRON_ORE = new BlockItem(ModBlocks.MARS_IRON_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_DIAMOND_ORE = new BlockItem(ModBlocks.MARS_DIAMOND_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_OSTRUM_ORE = new BlockItem(ModBlocks.MARS_OSTRUM_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MARS_ICE_SHARD_ORE = new BlockItem(ModBlocks.MARS_ICE_SHARD_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem MERCURY_IRON_ORE = new BlockItem(ModBlocks.MERCURY_IRON_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_COAL_ORE = new BlockItem(ModBlocks.VENUS_COAL_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_GOLD_ORE = new BlockItem(ModBlocks.VENUS_GOLD_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_DIAMOND_ORE = new BlockItem(ModBlocks.VENUS_DIAMOND_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem VENUS_CALORITE_ORE = new BlockItem(ModBlocks.VENUS_CALORITE_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_ICE_SHARD_ORE = new BlockItem(ModBlocks.GLACIO_ICE_SHARD_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_COAL_ORE = new BlockItem(ModBlocks.GLACIO_COAL_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_COPPER_ORE = new BlockItem(ModBlocks.GLACIO_COPPER_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_IRON_ORE = new BlockItem(ModBlocks.GLACIO_IRON_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));
        public static final BlockItem GLACIO_LAPIS_ORE = new BlockItem(ModBlocks.GLACIO_LAPIS_ORE, new Item.Settings().group(ITEM_GROUP_BLOCKS));

        public static void register() {

                // Spawn eggs.
                register("alien_spawn_egg", ALIEN_SPAWN_EGG);
                register("alien_zombie_spawn_egg", ALIEN_ZOMBIE_SPAWN_EGG);
                register("star_crawler_spawn_egg", STAR_CRAWLER_SPAWN_EGG);
                register("pygro_spawn_egg", PYGRO_SPAWN_EGG);
                register("pygro_brute_spawn_egg", PYGRO_BRUTE_SPAWN_EGG);
                register("mogler_spawn_egg", MOGLER_SPAWN_EGG);
                register("martian_raptor_spawn_egg", MARTIAN_RAPTOR_SPAWN_EGG);

                // Vehicles Items.
                register("rocket_t1", TIER_1_ROCKET);
                register("rocket_t2", TIER_2_ROCKET);
                register("rocket_t3", TIER_3_ROCKET);
                register("rocket_t4", TIER_4_ROCKET);
                register("rover", ROVER);

                // Guide book.
                register("guide_book", GUIDE_BOOK);

                // Items.
                register("cheese", CHEESE);
                register("hammer", HAMMER);
                register("iron_stick", IRON_STICK);
                register("oxygen_gear", OXYGEN_GEAR);
                register("oxygen_tank", OXYGEN_TANK);
                register("wheel", WHEEL);
                register("engine_frame", ENGINE_FRAME);
                register("engine_fan", ENGINE_FAN);
                register("rocket_nose_cone", ROCKET_NOSE_CONE);
                register("steel_engine", STEEL_ENGINE);
                register("desh_engine", DESH_ENGINE);
                register("ostrum_engine", OSTRUM_ENGINE);
                register("calorite_engine", CALORITE_ENGINE);
                register("steel_tank", STEEL_TANK);
                register("desh_tank", DESH_TANK);
                register("ostrum_tank", OSTRUM_TANK);
                register("calorite_tank", CALORITE_TANK);
                register("rocket_fin", ROCKET_FIN);

                register("steel_ingot", STEEL_INGOT);
                register("desh_ingot", DESH_INGOT);
                register("ostrum_ingot", OSTRUM_INGOT);
                register("calorite_ingot", CALORITE_INGOT);

                register("ice_shard", ICE_SHARD);

                register("iron_plate", IRON_PLATE);
                register("desh_plate", DESH_PLATE);

                register("compressed_steel", COMPRESSED_STEEL);
                register("compressed_desh", COMPRESSED_DESH);
                register("compressed_ostrum", COMPRESSED_OSTRUM);
                register("compressed_calorite", COMPRESSED_CALORITE);

                register("steel_nugget", STEEL_NUGGET);
                register("desh_nugget", DESH_NUGGET);
                register("ostrum_nugget", OSTRUM_NUGGET);
                register("calorite_nugget", CALORITE_NUGGET);

                register("raw_desh", RAW_DESH);
                register("raw_ostrum", RAW_OSTRUM);
                register("raw_calorite", RAW_CALORITE);

                // Flag Items.
                register("flag", FLAG);
                register("flag_blue", FLAG_BLUE);
                register("flag_brown", FLAG_BROWN);
                register("flag_cyan", FLAG_CYAN);
                register("flag_gray", FLAG_GRAY);
                register("flag_green", FLAG_GREEN);
                register("flag_light_blue", FLAG_LIGHT_BLUE);
                register("flag_lime", FLAG_LIME);
                register("flag_magenta", FLAG_MAGENTA);
                register("flag_orange", FLAG_ORANGE);
                register("flag_pink", FLAG_PINK);
                register("flag_purple", FLAG_PURPLE);
                register("flag_red", FLAG_RED);
                register("flag_yellow", FLAG_YELLOW);

                // Globes.
                register("earth_globe", EARTH_GLOBE);
                register("moon_globe", MOON_GLOBE);
                register("mars_globe", MARS_GLOBE);
                register("mercury_globe", MERCURY_GLOBE);
                register("venus_globe", VENUS_GLOBE);
                register("glacio_globe", GLACIO_GLOBE);

                // Rocket Launch Pad.
                register("rocket_launch_pad", ROCKET_LAUNCH_PAD);

                // Torch items.
                register("coal_torch", COAL_TORCH);
                register("coal_lantern", COAL_LANTERN);

                // Machines.
                register("nasa_workbench", NASA_WORKBENCH);
                register("solar_panel", SOLAR_PANEL);
                register("coal_generator", COAL_GENERATOR);
                register("compressor", COMPRESSOR);
                register("fuel_refinery", FUEL_REFINERY);
                register("oxygen_loader", OXYGEN_LOADER);
                register("oxygen_bubble_distributor", OXYGEN_BUBBLE_DISTRIBUTOR);
                register("water_pump", WATER_PUMP);

                // Block Items.
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
                register("infernal_spire_block", INFERNAL_SPIRE_BLOCK);
                register("iron_mark_block", IRON_MARK_BLOCK);
                register("sky_stone", SKY_STONE);

                register("moon_stone", MOON_STONE);
                register("moon_stone_bricks", MOON_STONE_BRICKS);
                register("cracked_moon_stone_bricks", CRACKED_MOON_STONE_BRICKS);
                register("moon_stone_brick_slab", MOON_STONE_BRICK_SLAB);
                register("moon_stone_brick_stairs", MOON_STONE_BRICK_STAIRS);

                register("mars_stone", MARS_STONE);
                register("mars_stone_bricks", MARS_STONE_BRICKS);
                register("cracked_mars_stone_bricks", CRACKED_MARS_STONE_BRICKS);
                register("mars_stone_brick_slab", MARS_STONE_BRICK_SLAB);
                register("mars_stone_brick_stairs", MARS_STONE_BRICK_STAIRS);

                register("mercury_stone", MERCURY_STONE);
                register("mercury_stone_bricks", MERCURY_STONE_BRICKS);
                register("cracked_mercury_stone_bricks", CRACKED_MERCURY_STONE_BRICKS);
                register("mercury_stone_brick_slab", MERCURY_STONE_BRICK_SLAB);
                register("mercury_stone_brick_stairs", MERCURY_STONE_BRICK_STAIRS);

                register("venus_stone", VENUS_STONE);
                register("venus_stone_bricks", VENUS_STONE_BRICKS);
                register("cracked_venus_stone_bricks", CRACKED_VENUS_STONE_BRICKS);
                register("venus_stone_brick_slab", VENUS_STONE_BRICK_SLAB);
                register("venus_stone_brick_stairs", VENUS_STONE_BRICK_STAIRS);

                register("venus_sandstone", VENUS_SANDSTONE);
                register("venus_sandstone_bricks", VENUS_SANDSTONE_BRICKS);
                register("cracked_venus_sandstone_bricks", CRACKED_VENUS_SANDSTONE_BRICKS);
                register("venus_sandstone_brick_slab", VENUS_SANDSTONE_BRICK_SLAB);
                register("venus_sandstone_brick_stairs", VENUS_SANDSTONE_BRICK_STAIRS);

                register("permafrost", PERMAFROST);
                register("glacio_stone", GLACIO_STONE);
                register("glacio_stone_bricks", GLACIO_STONE_BRICKS);
                register("cracked_glacio_stone_bricks", CRACKED_GLACIO_STONE_BRICKS);
                register("glacio_stone_brick_slab", GLACIO_STONE_BRICK_SLAB);
                register("glacio_stone_brick_stairs", GLACIO_STONE_BRICK_STAIRS);

                register("moon_sand", MOON_SAND);
                register("mars_sand", MARS_SAND);
                register("venus_sand", VENUS_SAND);

                register("moon_cheese_ore", MOON_CHEESE_ORE);
                register("moon_desh_ore", MOON_DESH_ORE);
                register("moon_iron_ore", MOON_IRON_ORE);
                register("moon_ice_shard_ore", MOON_ICE_SHARD);
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

        public static void register(String id, Item item) {
                Registry.register(Registry.ITEM, new ModIdentifier(id), item);
        }
}