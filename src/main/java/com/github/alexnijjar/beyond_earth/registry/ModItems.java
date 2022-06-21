package com.github.alexnijjar.beyond_earth.registry;

import java.util.Collections;
import java.util.List;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.CoalGeneratorBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.beyond_earth.blocks.machines.entity.WaterPumpBlockEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.beyond_earth.items.FluidContainingItem.TankStorage;
import com.github.alexnijjar.beyond_earth.items.GuideBook;
import com.github.alexnijjar.beyond_earth.items.HammerItem;
import com.github.alexnijjar.beyond_earth.items.OxygenTankItem;
import com.github.alexnijjar.beyond_earth.items.SpacePaintingItem;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.items.vehicles.RocketItem;
import com.github.alexnijjar.beyond_earth.items.vehicles.RoverItem;
import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModItems {

        public static final ItemGroup ITEM_GROUP_NORMAL = FabricItemGroupBuilder.create(new ModIdentifier("tab_normal")).icon(() -> new ItemStack(ModItems.TIER_1_ROCKET)).appendItems(stacks -> {

                ItemStack tier1Rocket = ModItems.TIER_1_ROCKET.getDefaultStack();
                ((VehicleItem) tier1Rocket.getItem()).setAmount(tier1Rocket, ((VehicleItem) tier1Rocket.getItem()).getTankSize());
                ((VehicleItem) tier1Rocket.getItem()).setFluid(tier1Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

                ItemStack tier2Rocket = ModItems.TIER_2_ROCKET.getDefaultStack();
                ((VehicleItem) tier2Rocket.getItem()).setAmount(tier2Rocket, ((VehicleItem) tier2Rocket.getItem()).getTankSize());
                ((VehicleItem) tier2Rocket.getItem()).setFluid(tier2Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

                ItemStack tier3Rocket = ModItems.TIER_3_ROCKET.getDefaultStack();
                ((VehicleItem) tier3Rocket.getItem()).setAmount(tier3Rocket, ((VehicleItem) tier3Rocket.getItem()).getTankSize());
                ((VehicleItem) tier3Rocket.getItem()).setFluid(tier3Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

                ItemStack tier4Rocket = ModItems.TIER_4_ROCKET.getDefaultStack();
                ((VehicleItem) tier4Rocket.getItem()).setAmount(tier4Rocket, ((VehicleItem) tier4Rocket.getItem()).getTankSize());
                ((VehicleItem) tier4Rocket.getItem()).setFluid(tier4Rocket, FluidVariant.of(ModFluids.FUEL_STILL));

                ItemStack tier1Rover = ModItems.TIER_1_ROVER.getDefaultStack();
                ((VehicleItem) tier1Rover.getItem()).setAmount(tier1Rover, ((VehicleItem) tier1Rover.getItem()).getTankSize());
                ((VehicleItem) tier1Rover.getItem()).setFluid(tier1Rover, FluidVariant.of(ModFluids.FUEL_STILL));

                ItemStack spaceSuit = ModArmour.SPACE_SUIT.getDefaultStack();
                ((SpaceSuit) spaceSuit.getItem()).setAmount(spaceSuit, ((SpaceSuit) spaceSuit.getItem()).getTankSize());
                ((SpaceSuit) spaceSuit.getItem()).setFluid(spaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

                ItemStack netheriteSpaceSuit = ModArmour.NETHERITE_SPACE_SUIT.getDefaultStack();
                ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).setAmount(netheriteSpaceSuit, ((NetheriteSpaceSuit) netheriteSpaceSuit.getItem()).getTankSize());
                ((SpaceSuit) netheriteSpaceSuit.getItem()).setFluid(netheriteSpaceSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));

                ItemStack jetSuit = ModArmour.JET_SUIT.getDefaultStack();
                ((JetSuit) jetSuit.getItem()).setAmount(jetSuit, ((JetSuit) jetSuit.getItem()).getTankSize());
                ((SpaceSuit) jetSuit.getItem()).setFluid(jetSuit, FluidVariant.of(ModFluids.OXYGEN_STILL));
                ((JetSuit) jetSuit.getItem()).setStoredEnergy(jetSuit, JetSuit.MAX_ENERGY);

                ItemStack oxygenTank = ModItems.OXYGEN_TANK.getDefaultStack();
                ((OxygenTankItem) oxygenTank.getItem()).setAmount(oxygenTank, ((OxygenTankItem) oxygenTank.getItem()).getTankSize());
                ((OxygenTankItem) oxygenTank.getItem()).setFluid(oxygenTank, FluidVariant.of(ModFluids.OXYGEN_STILL));

                stacks.addAll(Collections.nCopies(54, ItemStack.EMPTY));

                stacks.set(0, tier1Rocket);
                stacks.set(1, tier2Rocket);
                stacks.set(2, tier3Rocket);
                stacks.set(3, tier4Rocket);

                stacks.set(9, ModItems.TIER_1_ROCKET.getDefaultStack());
                stacks.set(10, ModItems.TIER_2_ROCKET.getDefaultStack());
                stacks.set(11, ModItems.TIER_3_ROCKET.getDefaultStack());
                stacks.set(12, ModItems.TIER_4_ROCKET.getDefaultStack());

                stacks.set(19, tier1Rover);
                stacks.set(18, ModItems.TIER_1_ROVER.getDefaultStack());

                stacks.set(6, ModArmour.OXYGEN_MASK.getDefaultStack());
                stacks.set(15, spaceSuit);
                stacks.set(24, ModArmour.SPACE_SUIT.getDefaultStack());
                stacks.set(33, ModArmour.SPACE_PANTS.getDefaultStack());
                stacks.set(42, ModArmour.SPACE_BOOTS.getDefaultStack());

                stacks.set(7, ModArmour.NETHERITE_OXYGEN_MASK.getDefaultStack());
                stacks.set(16, netheriteSpaceSuit);
                stacks.set(25, ModArmour.NETHERITE_SPACE_SUIT.getDefaultStack());
                stacks.set(34, ModArmour.NETHERITE_SPACE_PANTS.getDefaultStack());
                stacks.set(43, ModArmour.NETHERITE_SPACE_BOOTS.getDefaultStack());

                stacks.set(8, ModArmour.JET_SUIT_OXYGEN_MASK.getDefaultStack());
                stacks.set(17, jetSuit);
                stacks.set(26, ModArmour.JET_SUIT.getDefaultStack());
                stacks.set(35, ModArmour.JET_SUIT_PANTS.getDefaultStack());
                stacks.set(44, ModArmour.JET_SUIT_BOOTS.getDefaultStack());

                stacks.set(27, ModItems.OXYGEN_TANK.getDefaultStack());
                stacks.set(28, oxygenTank);

                stacks.set(36, ModItems.GUIDE_BOOK.getDefaultStack());
                stacks.set(37, ModItems.SPACE_PAINTING.getDefaultStack());
                stacks.set(38, ModItems.CHEESE.getDefaultStack());
                stacks.set(39, ModItems.ROCKET_LAUNCH_PAD.getDefaultStack());

                stacks.set(45, ModFluids.OIL_BUCKET.getDefaultStack());
                stacks.set(46, ModFluids.FUEL_BUCKET.getDefaultStack());
                stacks.set(47, ModFluids.OXYGEN_BUCKET.getDefaultStack());

        }).build();

        public static final ItemGroup ITEM_GROUP_MACHINES = FabricItemGroupBuilder.build(new ModIdentifier("tab_machines"), () -> new ItemStack(ModBlocks.NASA_WORKBENCH));
        public static final ItemGroup ITEM_GROUP_BASICS = FabricItemGroupBuilder.build(new ModIdentifier("tab_basics"), () -> new ItemStack(ModItems.DESH_ENGINE));
        public static final ItemGroup ITEM_GROUP_MATERIALS = FabricItemGroupBuilder.build(new ModIdentifier("tab_materials"), () -> new ItemStack(ModItems.IRON_PLATE));
        public static final ItemGroup ITEM_GROUP_FLAGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_flags"), () -> new ItemStack(ModBlocks.FLAG_PURPLE));
        public static final ItemGroup ITEM_GROUP_GLOBES = FabricItemGroupBuilder.build(new ModIdentifier("tab_globes"), () -> new ItemStack(ModItems.GLACIO_GLOBE));
        public static final ItemGroup ITEM_GROUP_BLOCKS = FabricItemGroupBuilder.build(new ModIdentifier("tab_blocks"), () -> new ItemStack(ModBlocks.MOON_IRON_ORE));
        public static final ItemGroup ITEM_GROUP_SPAWN_EGGS = FabricItemGroupBuilder.build(new ModIdentifier("tab_spawn_eggs"), () -> new ItemStack(ModItems.ALIEN_SPAWN_EGG));

        public static final BlockItem ROCKET_LAUNCH_PAD = registerBlockItem(ModBlocks.ROCKET_LAUNCH_PAD, null);

        // Spawn eggs.
        public static final Item ALIEN_SPAWN_EGG = register("alien_spawn_egg", new SpawnEggItem(ModEntities.ALIEN, -13382401, -11650781, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item ALIEN_ZOMBIE_SPAWN_EGG = register("alien_zombie_spawn_egg", new SpawnEggItem(ModEntities.ALIEN_ZOMBIE, -14804199, -16740159, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item STAR_CRAWLER_SPAWN_EGG = register("star_crawler_spawn_egg", new SpawnEggItem(ModEntities.STAR_CRAWLER, -13421773, -16724788, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item MARTIAN_RAPTOR_SPAWN_EGG = register("martian_raptor_spawn_egg", new SpawnEggItem(ModEntities.MARTIAN_RAPTOR, 5349438, -13312, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item PYGRO_SPAWN_EGG = register("pygro_spawn_egg", new SpawnEggItem(ModEntities.PYGRO, -3381760, -6750208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item PYGRO_BRUTE_SPAWN_EGG = register("pygro_brute_spawn_egg", new SpawnEggItem(ModEntities.PYGRO_BRUTE, -3381760, -67208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item MOGLER_SPAWN_EGG = register("mogler_spawn_egg", new SpawnEggItem(ModEntities.MOGLER, -13312, -3407872, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item ZOMBIFIED_MOGLER_SPAWN_EGG = register("zombified_mogler_spawn_egg", new SpawnEggItem(ModEntities.ZOMBIFIED_MOGLER, 12537409, 7988821, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
        public static final Item ALIEN_WANDERING_TRADER_SPAWN_EGG = register("alien_wandering_trader_spawn_egg", new SpawnEggItem(ModEntities.ALIEN_WANDERING_TRADER, 5993415, 2868874, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));

        // Vehicles Items.
        public static final Item TIER_1_ROCKET = register("tier_1_rocket", new RocketItem<RocketEntityTier1>(ModEntities.ROCKET_TIER_1, 1, new FabricItemSettings().maxCount(1)));
        public static final Item TIER_2_ROCKET = register("tier_2_rocket", new RocketItem<RocketEntityTier2>(ModEntities.ROCKET_TIER_2, 2, new FabricItemSettings().maxCount(1)));
        public static final Item TIER_3_ROCKET = register("tier_3_rocket", new RocketItem<RocketEntityTier3>(ModEntities.ROCKET_TIER_3, 3, new FabricItemSettings().maxCount(1)));
        public static final Item TIER_4_ROCKET = register("tier_4_rocket", new RocketItem<RocketEntityTier4>(ModEntities.ROCKET_TIER_4, 4, new FabricItemSettings().maxCount(1)));
        public static final Item TIER_1_ROVER = register("tier_1_rover", new RoverItem(new FabricItemSettings().maxCount(1)));

        // Oxygen tank.
        public static final Item OXYGEN_TANK = register("oxygen_tank", new OxygenTankItem(new FabricItemSettings().maxCount(1)));

        // Guide book
        public static final Item GUIDE_BOOK = register("guide_book", new GuideBook(new FabricItemSettings().maxCount(1)));

        // Items.
        public static final Item CHEESE = register("cheese", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(4).saturationModifier(3.0f).build())));

        public static final Item SPACE_PAINTING = register("space_painting", new SpacePaintingItem(ModEntities.SPACE_PAINTING, new Item.Settings().rarity(Rarity.UNCOMMON)));

        public static final Item HAMMER = register("hammer", new HammerItem(new FabricItemSettings().group(ITEM_GROUP_BASICS).maxCount(1).maxDamage(9)));

        public static final Item IRON_STICK = registerItem("iron_stick", ITEM_GROUP_BASICS);
        public static final Item OXYGEN_GEAR = registerItem("oxygen_gear", ITEM_GROUP_BASICS);
        public static final Item WHEEL = registerItem("wheel", ITEM_GROUP_BASICS);
        public static final Item ENGINE_FRAME = registerItem("engine_frame", ITEM_GROUP_BASICS);
        public static final Item ENGINE_FAN = registerItem("engine_fan", ITEM_GROUP_BASICS);
        public static final Item ROCKET_NOSE_CONE = registerItem("rocket_nose_cone", ITEM_GROUP_BASICS);
        public static final Item STEEL_ENGINE = registerItem("steel_engine", ITEM_GROUP_BASICS);
        public static final Item DESH_ENGINE = registerItem("desh_engine", ITEM_GROUP_BASICS);
        public static final Item OSTRUM_ENGINE = registerItem("ostrum_engine", ITEM_GROUP_BASICS);
        public static final Item CALORITE_ENGINE = registerItem("calorite_engine", ITEM_GROUP_BASICS);
        public static final Item STEEL_TANK = registerItem("steel_tank", ITEM_GROUP_BASICS);
        public static final Item DESH_TANK = registerItem("desh_tank", ITEM_GROUP_BASICS);
        public static final Item OSTRUM_TANK = registerItem("ostrum_tank", ITEM_GROUP_BASICS);
        public static final Item CALORITE_TANK = registerItem("calorite_tank", ITEM_GROUP_BASICS);
        public static final Item ROCKET_FIN = registerItem("rocket_fin", ITEM_GROUP_BASICS);

        public static final Item STEEL_INGOT = registerItem("steel_ingot", ITEM_GROUP_MATERIALS);
        public static final Item DESH_INGOT = registerItem("desh_ingot", ITEM_GROUP_MATERIALS);
        public static final Item OSTRUM_INGOT = registerItem("ostrum_ingot", ITEM_GROUP_MATERIALS);
        public static final Item CALORITE_INGOT = registerItem("calorite_ingot", ITEM_GROUP_MATERIALS);

        public static final Item ICE_SHARD = registerItem("ice_shard", ITEM_GROUP_MATERIALS);

        public static final Item IRON_PLATE = registerItem("iron_plate", ITEM_GROUP_MATERIALS);
        public static final Item DESH_PLATE = registerItem("desh_plate", ITEM_GROUP_MATERIALS);

        public static final Item COMPRESSED_STEEL = registerItem("compressed_steel", ITEM_GROUP_MATERIALS);
        public static final Item COMPRESSED_DESH = registerItem("compressed_desh", ITEM_GROUP_MATERIALS);
        public static final Item COMPRESSED_OSTRUM = registerItem("compressed_ostrum", ITEM_GROUP_MATERIALS);
        public static final Item COMPRESSED_CALORITE = registerItem("compressed_calorite", ITEM_GROUP_MATERIALS);

        public static final Item STEEL_NUGGET = registerItem("steel_nugget", ITEM_GROUP_MATERIALS);
        public static final Item DESH_NUGGET = registerItem("desh_nugget", ITEM_GROUP_MATERIALS);
        public static final Item OSTRUM_NUGGET = registerItem("ostrum_nugget", ITEM_GROUP_MATERIALS);
        public static final Item CALORITE_NUGGET = registerItem("calorite_nugget", ITEM_GROUP_MATERIALS);

        public static final Item RAW_DESH = registerItem("raw_desh", ITEM_GROUP_MATERIALS);
        public static final Item RAW_OSTRUM = registerItem("raw_ostrum", ITEM_GROUP_MATERIALS);
        public static final Item RAW_CALORITE = registerItem("raw_calorite", ITEM_GROUP_MATERIALS);

        // Flag Items
        public static final BlockItem FLAG = registerFlag(ModBlocks.FLAG);
        public static final BlockItem FLAG_BLUE = registerFlag(ModBlocks.FLAG_BLUE);
        public static final BlockItem FLAG_BROWN = registerFlag(ModBlocks.FLAG_BROWN);
        public static final BlockItem FLAG_CYAN = registerFlag(ModBlocks.FLAG_CYAN);
        public static final BlockItem FLAG_GRAY = registerFlag(ModBlocks.FLAG_GRAY);
        public static final BlockItem FLAG_GREEN = registerFlag(ModBlocks.FLAG_GREEN);
        public static final BlockItem FLAG_LIGHT_BLUE = registerFlag(ModBlocks.FLAG_LIGHT_BLUE);
        public static final BlockItem FLAG_LIME = registerFlag(ModBlocks.FLAG_LIME);
        public static final BlockItem FLAG_MAGENTA = registerFlag(ModBlocks.FLAG_MAGENTA);
        public static final BlockItem FLAG_ORANGE = registerFlag(ModBlocks.FLAG_ORANGE);
        public static final BlockItem FLAG_PINK = registerFlag(ModBlocks.FLAG_PINK);
        public static final BlockItem FLAG_PURPLE = registerFlag(ModBlocks.FLAG_PURPLE);
        public static final BlockItem FLAG_RED = registerFlag(ModBlocks.FLAG_RED);
        public static final BlockItem FLAG_YELLOW = registerFlag(ModBlocks.FLAG_YELLOW);

        // Globes.
        public static final BlockItem EARTH_GLOBE = registerGlobe(ModBlocks.EARTH_GLOBE);
        public static final BlockItem MOON_GLOBE = registerGlobe(ModBlocks.MOON_GLOBE);
        public static final BlockItem MARS_GLOBE = registerGlobe(ModBlocks.MARS_GLOBE);
        public static final BlockItem MERCURY_GLOBE = registerGlobe(ModBlocks.MERCURY_GLOBE);
        public static final BlockItem VENUS_GLOBE = registerGlobe(ModBlocks.VENUS_GLOBE);
        public static final BlockItem GLACIO_GLOBE = registerGlobe(ModBlocks.GLACIO_GLOBE);

        // Torch items.
        public static final Item COAL_TORCH = register("coal_torch", new WallStandingBlockItem(ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH, new FabricItemSettings().group(ITEM_GROUP_BASICS)));
        public static final Item COAL_LANTERN = registerBlockItem(ModBlocks.COAL_LANTERN, ITEM_GROUP_BASICS);

        // Machines.
        public static final BlockItem NASA_WORKBENCH = registerBlockItem(ModBlocks.NASA_WORKBENCH, ITEM_GROUP_MACHINES);
        public static final BlockItem SOLAR_PANEL = register("solar_panel", new BlockItem(ModBlocks.SOLAR_PANEL, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((Text.translatable("item.beyond_earth.generator.tooltip", SolarPanelBlockEntity.ENERGY_PER_TICK).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        });
        public static final BlockItem COAL_GENERATOR = register("coal_generator", new BlockItem(ModBlocks.COAL_GENERATOR, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((Text.translatable("item.beyond_earth.generator.tooltip", CoalGeneratorBlockEntity.ENERGY_PER_TICK).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        });
        public static final BlockItem COMPRESSOR = registerBlockItem(ModBlocks.COMPRESSOR, ITEM_GROUP_MACHINES);
        public static final BlockItem FUEL_REFINERY = registerBlockItem(ModBlocks.FUEL_REFINERY, ITEM_GROUP_MACHINES);
        public static final BlockItem OXYGEN_LOADER = registerBlockItem(ModBlocks.OXYGEN_LOADER, ITEM_GROUP_MACHINES);
        public static final BlockItem OXYGEN_BUBBLE_DISTRIBUTOR = registerBlockItem(ModBlocks.OXYGEN_BUBBLE_DISTRIBUTOR, ITEM_GROUP_MACHINES);
        public static final BlockItem WATER_PUMP = register("water_pump", new BlockItem(ModBlocks.WATER_PUMP, new Item.Settings().group(ITEM_GROUP_MACHINES)) {
                @Override
                public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
                        tooltip.add((Text.translatable("item.beyond_earth.water_pump.tooltip", FluidUtils.dropletsToMillibuckets(WaterPumpBlockEntity.TRANSFER_PER_TICK)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
                }
        });

        // Block Items.
        public static final BlockItem STEEL_BLOCK = registerBlockItem(ModBlocks.STEEL_BLOCK);
        public static final BlockItem DESH_BLOCK = registerBlockItem(ModBlocks.DESH_BLOCK);
        public static final BlockItem OSTRUM_BLOCK = registerBlockItem(ModBlocks.OSTRUM_BLOCK);
        public static final BlockItem CALORITE_BLOCK = registerBlockItem(ModBlocks.CALORITE_BLOCK);
        public static final BlockItem RAW_DESH_BLOCK = registerBlockItem(ModBlocks.RAW_DESH_BLOCK);
        public static final BlockItem RAW_OSTRUM_BLOCK = registerBlockItem(ModBlocks.RAW_OSTRUM_BLOCK);
        public static final BlockItem RAW_CALORITE_BLOCK = registerBlockItem(ModBlocks.RAW_CALORITE_BLOCK);
        public static final BlockItem IRON_PLATING_BLOCK = registerBlockItem(ModBlocks.IRON_PLATING_BLOCK);
        public static final BlockItem RUSTED_IRON_PILLAR_BLOCK = registerBlockItem(ModBlocks.RUSTED_IRON_PILLAR_BLOCK);
        public static final BlockItem RUSTED_IRON_PLATING_BLOCK = registerBlockItem(ModBlocks.RUSTED_IRON_PLATING_BLOCK);
        public static final BlockItem BLUE_IRON_PLATING_BLOCK = registerBlockItem(ModBlocks.BLUE_IRON_PLATING_BLOCK);
        public static final BlockItem INFERNAL_SPIRE_BLOCK = registerBlockItem(ModBlocks.INFERNAL_SPIRE_BLOCK);
        public static final BlockItem IRON_MARK_BLOCK = registerBlockItem(ModBlocks.IRON_MARK_BLOCK);
        public static final BlockItem SKY_STONE = registerBlockItem(ModBlocks.SKY_STONE);

        public static final BlockItem MOON_STONE = registerBlockItem(ModBlocks.MOON_STONE);
        public static final BlockItem MOON_STONE_BRICKS = registerBlockItem(ModBlocks.MOON_STONE_BRICKS);
        public static final BlockItem CRACKED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS);
        public static final BlockItem MOON_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MOON_STONE_BRICK_SLAB);
        public static final BlockItem MOON_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS);
        public static final BlockItem CHISELED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_BRICKS);
        public static final BlockItem CHISELED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_STAIRS);
        public static final BlockItem CHISELED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_SLAB);
        public static final BlockItem POLISHED_MOON_STONE = registerBlockItem(ModBlocks.POLISHED_MOON_STONE);
        public static final BlockItem POLISHED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_STAIRS);
        public static final BlockItem POLISHED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_SLAB);
        public static final BlockItem MOON_PILLAR = registerBlockItem(ModBlocks.MOON_PILLAR);

        public static final BlockItem MARS_STONE = registerBlockItem(ModBlocks.MARS_STONE);
        public static final BlockItem MARS_STONE_BRICKS = registerBlockItem(ModBlocks.MARS_STONE_BRICKS);
        public static final BlockItem CRACKED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS);
        public static final BlockItem MARS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MARS_STONE_BRICK_SLAB);
        public static final BlockItem MARS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS);
        public static final BlockItem CHISELED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_BRICKS);
        public static final BlockItem CHISELED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_STAIRS);
        public static final BlockItem CHISELED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_SLAB);
        public static final BlockItem POLISHED_MARS_STONE = registerBlockItem(ModBlocks.POLISHED_MARS_STONE);
        public static final BlockItem POLISHED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_STAIRS);
        public static final BlockItem POLISHED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_SLAB);
        public static final BlockItem MARS_PILLAR = registerBlockItem(ModBlocks.MARS_PILLAR);

        public static final BlockItem MERCURY_STONE = registerBlockItem(ModBlocks.MERCURY_STONE);
        public static final BlockItem MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICKS);
        public static final BlockItem CRACKED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
        public static final BlockItem MERCURY_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB);
        public static final BlockItem MERCURY_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS);
        public static final BlockItem CHISELED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
        public static final BlockItem CHISELED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_STAIRS);
        public static final BlockItem CHISELED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_SLAB);
        public static final BlockItem POLISHED_MERCURY_STONE = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE);
        public static final BlockItem POLISHED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_STAIRS);
        public static final BlockItem POLISHED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_SLAB);
        public static final BlockItem MERCURY_PILLAR = registerBlockItem(ModBlocks.MERCURY_PILLAR);

        public static final BlockItem VENUS_SANDSTONE = registerBlockItem(ModBlocks.VENUS_SANDSTONE);
        public static final BlockItem VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICKS);
        public static final BlockItem CRACKED_VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
        public static final BlockItem VENUS_SANDSTONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_SLAB);
        public static final BlockItem VENUS_SANDSTONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS);

        public static final BlockItem VENUS_STONE = registerBlockItem(ModBlocks.VENUS_STONE);
        public static final BlockItem VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.VENUS_STONE_BRICKS);
        public static final BlockItem CRACKED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS);
        public static final BlockItem VENUS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB);
        public static final BlockItem VENUS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS);
        public static final BlockItem CHISELED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_BRICKS);
        public static final BlockItem CHISELED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_STAIRS);
        public static final BlockItem CHISELED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_SLAB);
        public static final BlockItem POLISHED_VENUS_STONE = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE);
        public static final BlockItem POLISHED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_STAIRS);
        public static final BlockItem POLISHED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_SLAB);
        public static final BlockItem VENUS_PILLAR = registerBlockItem(ModBlocks.VENUS_PILLAR);

        public static final BlockItem PERMAFROST_STONE = registerBlockItem(ModBlocks.PERMAFROST_STONE);
        public static final BlockItem GLACIO_STONE = registerBlockItem(ModBlocks.GLACIO_STONE);
        public static final BlockItem GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICKS);
        public static final BlockItem CRACKED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
        public static final BlockItem GLACIO_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB);
        public static final BlockItem GLACIO_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS);
        public static final BlockItem CHISELED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
        public static final BlockItem CHISELED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_STAIRS);
        public static final BlockItem CHISELED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_SLAB);
        public static final BlockItem POLISHED_GLACIO_STONE = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE);
        public static final BlockItem POLISHED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_STAIRS);
        public static final BlockItem POLISHED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_SLAB);
        public static final BlockItem GLACIO_PILLAR = registerBlockItem(ModBlocks.GLACIO_PILLAR);

        public static final BlockItem MOON_SAND = registerBlockItem(ModBlocks.MOON_SAND);
        public static final BlockItem MARS_SAND = registerBlockItem(ModBlocks.MARS_SAND);
        public static final BlockItem VENUS_SAND = registerBlockItem(ModBlocks.VENUS_SAND);

        public static final BlockItem MOON_CHEESE_ORE = registerBlockItem(ModBlocks.MOON_CHEESE_ORE);
        public static final BlockItem MOON_DESH_ORE = registerBlockItem(ModBlocks.MOON_DESH_ORE);
        public static final BlockItem MOON_IRON_ORE = registerBlockItem(ModBlocks.MOON_IRON_ORE);
        public static final BlockItem MOON_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MOON_ICE_SHARD_ORE);
        public static final BlockItem MARS_IRON_ORE = registerBlockItem(ModBlocks.MARS_IRON_ORE);
        public static final BlockItem MARS_DIAMOND_ORE = registerBlockItem(ModBlocks.MARS_DIAMOND_ORE);
        public static final BlockItem MARS_OSTRUM_ORE = registerBlockItem(ModBlocks.MARS_OSTRUM_ORE);
        public static final BlockItem MARS_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MARS_ICE_SHARD_ORE);
        public static final BlockItem MERCURY_IRON_ORE = registerBlockItem(ModBlocks.MERCURY_IRON_ORE);
        public static final BlockItem VENUS_COAL_ORE = registerBlockItem(ModBlocks.VENUS_COAL_ORE);
        public static final BlockItem VENUS_GOLD_ORE = registerBlockItem(ModBlocks.VENUS_GOLD_ORE);
        public static final BlockItem VENUS_DIAMOND_ORE = registerBlockItem(ModBlocks.VENUS_DIAMOND_ORE);
        public static final BlockItem VENUS_CALORITE_ORE = registerBlockItem(ModBlocks.VENUS_CALORITE_ORE);
        public static final BlockItem GLACIO_ICE_SHARD_ORE = registerBlockItem(ModBlocks.GLACIO_ICE_SHARD_ORE);
        public static final BlockItem GLACIO_COAL_ORE = registerBlockItem(ModBlocks.GLACIO_COAL_ORE);
        public static final BlockItem GLACIO_COPPER_ORE = registerBlockItem(ModBlocks.GLACIO_COPPER_ORE);
        public static final BlockItem GLACIO_IRON_ORE = registerBlockItem(ModBlocks.GLACIO_IRON_ORE);
        public static final BlockItem GLACIO_LAPIS_ORE = registerBlockItem(ModBlocks.GLACIO_LAPIS_ORE);

        public static void register() {
                registerTank(TIER_1_ROCKET);
                registerTank(TIER_2_ROCKET);
                registerTank(TIER_3_ROCKET);
                registerTank(TIER_4_ROCKET);
                registerTank(TIER_1_ROVER);
                registerTank(OXYGEN_TANK);
        }

        public static BlockItem registerFlag(Block flag) {
                TallBlockItem item = new TallBlockItem(flag, new Item.Settings().group(ITEM_GROUP_FLAGS));
                register(Registry.BLOCK.getId(flag), item);
                return item;
        }

        public static BlockItem registerGlobe(Block globe) {
                BlockItem item = new BlockItem(globe, new Item.Settings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
                register(Registry.BLOCK.getId(globe), item);
                return item;
        }

        public static BlockItem registerBlockItem(Block block) {
                return registerBlockItem(block, ITEM_GROUP_BLOCKS);
        }

        public static BlockItem registerBlockItem(Block block, ItemGroup group) {
                BlockItem item = new BlockItem(block, new Item.Settings().group(group));
                register(Registry.BLOCK.getId(block), item);
                return item;
        }

        public static Item registerItem(String id, ItemGroup group) {
                Item item = new Item(new Item.Settings().group(group));
                register(id, item);
                return item;
        }

        public static <T extends Item> T register(String id, T item) {
                return register(new ModIdentifier(id), item);
        }

        public static <T extends Item> T register(Identifier id, T item) {
                Registry.register(Registry.ITEM, id, item);
                return item;
        }

        public static void registerTank(Item tank) {
                FluidStorage.ITEM.registerForItems(TankStorage::new, tank);
        }
}