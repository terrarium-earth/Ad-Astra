package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier1;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier2;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier3;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntityTier4;
import com.github.alexnijjar.ad_astra.items.*;
import com.github.alexnijjar.ad_astra.items.FluidContainingItem.TankStorage;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.RocketItem;
import com.github.alexnijjar.ad_astra.items.vehicles.RoverItem;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ModItems extends ModItemGroups {

	Set<Item> items = new HashSet<>();

	// Vehicles Items
	Item TIER_1_ROCKET = register("tier_1_rocket", new RocketItem<RocketEntityTier1>(ModEntityTypes.ROCKET_TIER_1, 1, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	Item TIER_2_ROCKET = register("tier_2_rocket", new RocketItem<RocketEntityTier2>(ModEntityTypes.ROCKET_TIER_2, 2, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	Item TIER_3_ROCKET = register("tier_3_rocket", new RocketItem<RocketEntityTier3>(ModEntityTypes.ROCKET_TIER_3, 3, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	Item TIER_4_ROCKET = register("tier_4_rocket", new RocketItem<RocketEntityTier4>(ModEntityTypes.ROCKET_TIER_4, 4, new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	Item TIER_1_ROVER = register("tier_1_rover", new RoverItem(new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1).fireproof()));

	// Oxygen tank
	Item OXYGEN_TANK = register("oxygen_tank", new OxygenTankItem(new FabricItemSettings().group(ITEM_GROUP_NORMAL).maxCount(1)));

	// Guide book
	Item ASTRODUX = register("astrodux", new AstroduxItem(new FabricItemSettings().group(ITEM_GROUP_NORMAL)));

	Item SPACE_PAINTING = register("space_painting", new SpacePaintingItem(ModEntityTypes.SPACE_PAINTING, new FabricItemSettings().group(ITEM_GROUP_NORMAL).rarity(Rarity.UNCOMMON)));

	Item CHEESE = register("cheese", new Item(new FabricItemSettings().group(ITEM_GROUP_NORMAL).food(new FoodComponent.Builder().hunger(4).saturationModifier(1.0f).build())));

	// Launch pad
	BlockItem ROCKET_LAUNCH_PAD = registerBlockItem(ModBlocks.ROCKET_LAUNCH_PAD, ITEM_GROUP_NORMAL);

	// Buckets.
	Item OIL_BUCKET = register("oil_bucket", new BucketItem(ModFluids.OIL_STILL, new FabricItemSettings().group(ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	Item FUEL_BUCKET = register("fuel_bucket", new BucketItem(ModFluids.FUEL_STILL, new FabricItemSettings().group(ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	Item CRYO_FUEL_BUCKET = register("cryo_fuel_bucket", new BucketItem(ModFluids.CRYO_FUEL_STILL, new FabricItemSettings().group(ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	Item OXYGEN_BUCKET = register("oxygen_bucket", new BucketItem(ModFluids.OXYGEN_STILL, new FabricItemSettings().group(ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)) {
		@Override
		public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
			ItemStack itemStack = user.getStackInHand(hand);
			return TypedActionResult.fail(itemStack);
		}
	});

	// Spacesuit
	SpaceSuit SPACE_HELMET = register("space_helmet", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ITEM_GROUP_NORMAL)));
	SpaceSuit SPACE_SUIT = register("space_suit", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ITEM_GROUP_NORMAL)));
	SpaceSuit SPACE_PANTS = register("space_pants", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ITEM_GROUP_NORMAL)));
	SpaceSuit SPACE_BOOTS = register("space_boots", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ITEM_GROUP_NORMAL)));

	// Netherite Spacesuit
	NetheriteSpaceSuit NETHERITE_SPACE_HELMET = register("netherite_space_helmet",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	NetheriteSpaceSuit NETHERITE_SPACE_SUIT = register("netherite_space_suit",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	NetheriteSpaceSuit NETHERITE_SPACE_PANTS = register("netherite_space_pants",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	NetheriteSpaceSuit NETHERITE_SPACE_BOOTS = register("netherite_space_boots",
			new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));

	// Jet Suit
	JetSuit JET_SUIT_HELMET = register("jet_suit_helmet", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	JetSuit JET_SUIT = register("jet_suit", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	JetSuit JET_SUIT_PANTS = register("jet_suit_pants", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));
	JetSuit JET_SUIT_BOOTS = register("jet_suit_boots", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new FabricItemSettings().group(ITEM_GROUP_NORMAL).fireproof()));

	// Machines
	BlockItem COAL_GENERATOR = register("coal_generator", new MachineBlockItem(ModBlocks.COAL_GENERATOR, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.generator_energy.tooltip", AdAstra.CONFIG.coalGenerator.energyPerTick).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.coal_generator.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem COMPRESSOR = register("compressor", new MachineBlockItem(ModBlocks.COMPRESSOR, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.compressor.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem NASA_WORKBENCH = register("nasa_workbench", new MachineBlockItem(ModBlocks.NASA_WORKBENCH, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.nasa_workbench.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem FUEL_REFINERY = register("fuel_refinery", new MachineBlockItem(ModBlocks.FUEL_REFINERY, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.fuel_refinery.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem OXYGEN_LOADER = register("oxygen_loader", new MachineBlockItem(ModBlocks.OXYGEN_LOADER, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_loader.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_loader.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem SOLAR_PANEL = register("solar_panel", new SolarPanelBlockItem(ModBlocks.SOLAR_PANEL, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.generator_energy.tooltip", AdAstra.CONFIG.solarPanel.energyPerTick).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.solar_panel.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.solar_panel.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem OXYGEN_DISTRIBUTOR = register("oxygen_distributor", new MachineBlockItem(ModBlocks.OXYGEN_DISTRIBUTOR, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_distributor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_distributor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem WATER_PUMP = register("water_pump", new MachineBlockItem(ModBlocks.WATER_PUMP, new FabricItemSettings().group(ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((new TranslatableText("item.ad_astra.fluid_transfer_rate.tooltip", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.water_pump.tooltip[0]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.water_pump.tooltip[1]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem ENERGIZER = register("energizer", new EnergizerBlockItem(ModBlocks.ENERGIZER, new FabricItemSettings().group(ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			super.appendTooltip(stack, world, tooltip, context);
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.energizer.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.energizer.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem CRYO_FREEZER = register("cryo_freezer", new MachineBlockItem(ModBlocks.CRYO_FREEZER, new FabricItemSettings().group(ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.cryo_freezer.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	BlockItem OXYGEN_SENSOR = register("oxygen_sensor", new MachineBlockItem(ModBlocks.OXYGEN_SENSOR, new FabricItemSettings().group(ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_sensor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((new TranslatableText("item.ad_astra.oxygen_sensor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((new TranslatableText("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});


	Item HAMMER = register("hammer", new HammerItem(new FabricItemSettings().group(ITEM_GROUP_BASICS).maxCount(1).maxDamage(AdAstra.CONFIG.general.hammerDurability)));

	Item IRON_STICK = registerItem("iron_stick", ITEM_GROUP_BASICS);
	Item OXYGEN_GEAR = registerItem("oxygen_gear", ITEM_GROUP_BASICS);
	Item WHEEL = registerItem("wheel", ITEM_GROUP_BASICS);
	Item ENGINE_FRAME = registerItem("engine_frame", ITEM_GROUP_BASICS);
	Item ENGINE_FAN = registerItem("engine_fan", ITEM_GROUP_BASICS);
	Item ROCKET_NOSE_CONE = registerItem("rocket_nose_cone", ITEM_GROUP_BASICS);
	Item STEEL_ENGINE = registerItem("steel_engine", ITEM_GROUP_BASICS);
	Item DESH_ENGINE = registerItem("desh_engine", ITEM_GROUP_BASICS);
	Item OSTRUM_ENGINE = registerItem("ostrum_engine", ITEM_GROUP_BASICS);
	Item CALORITE_ENGINE = registerItem("calorite_engine", ITEM_GROUP_BASICS);
	Item STEEL_TANK = registerItem("steel_tank", ITEM_GROUP_BASICS);
	Item DESH_TANK = registerItem("desh_tank", ITEM_GROUP_BASICS);
	Item OSTRUM_TANK = registerItem("ostrum_tank", ITEM_GROUP_BASICS);
	Item CALORITE_TANK = registerItem("calorite_tank", ITEM_GROUP_BASICS);
	Item ROCKET_FIN = registerItem("rocket_fin", ITEM_GROUP_BASICS);

	// Torch items
	Item COAL_TORCH = register("coal_torch", new WallStandingBlockItem(ModBlocks.COAL_TORCH, ModBlocks.WALL_COAL_TORCH, new FabricItemSettings().group(ITEM_GROUP_BASICS)));
	Item COAL_LANTERN = registerBlockItem(ModBlocks.COAL_LANTERN, ITEM_GROUP_BASICS);

	Item STEEL_INGOT = registerItem("steel_ingot", ITEM_GROUP_MATERIALS);
	Item DESH_INGOT = registerItem("desh_ingot", ITEM_GROUP_MATERIALS);
	Item OSTRUM_INGOT = registerItem("ostrum_ingot", ITEM_GROUP_MATERIALS);
	Item CALORITE_INGOT = registerItem("calorite_ingot", ITEM_GROUP_MATERIALS);

	Item ICE_SHARD = registerItem("ice_shard", ITEM_GROUP_MATERIALS);

	Item IRON_PLATE = registerItem("iron_plate", ITEM_GROUP_MATERIALS);
	Item DESH_PLATE = registerItem("desh_plate", ITEM_GROUP_MATERIALS);

	Item COMPRESSED_STEEL = registerItem("compressed_steel", ITEM_GROUP_MATERIALS);
	Item COMPRESSED_DESH = registerItem("compressed_desh", ITEM_GROUP_MATERIALS);
	Item COMPRESSED_OSTRUM = registerItem("compressed_ostrum", ITEM_GROUP_MATERIALS);
	Item COMPRESSED_CALORITE = registerItem("compressed_calorite", ITEM_GROUP_MATERIALS);

	Item STEEL_NUGGET = registerItem("steel_nugget", ITEM_GROUP_MATERIALS);
	Item DESH_NUGGET = registerItem("desh_nugget", ITEM_GROUP_MATERIALS);
	Item OSTRUM_NUGGET = registerItem("ostrum_nugget", ITEM_GROUP_MATERIALS);
	Item CALORITE_NUGGET = registerItem("calorite_nugget", ITEM_GROUP_MATERIALS);

	Item RAW_DESH = registerItem("raw_desh", ITEM_GROUP_MATERIALS);
	Item RAW_OSTRUM = registerItem("raw_ostrum", ITEM_GROUP_MATERIALS);
	Item RAW_CALORITE = registerItem("raw_calorite", ITEM_GROUP_MATERIALS);

	// Flag Items
	BlockItem FLAG = registerFlag(ModBlocks.FLAG);
	BlockItem FLAG_BLUE = registerFlag(ModBlocks.FLAG_BLUE);
	BlockItem FLAG_BROWN = registerFlag(ModBlocks.FLAG_BROWN);
	BlockItem FLAG_CYAN = registerFlag(ModBlocks.FLAG_CYAN);
	BlockItem FLAG_GRAY = registerFlag(ModBlocks.FLAG_GRAY);
	BlockItem FLAG_GREEN = registerFlag(ModBlocks.FLAG_GREEN);
	BlockItem FLAG_LIGHT_BLUE = registerFlag(ModBlocks.FLAG_LIGHT_BLUE);
	BlockItem FLAG_LIME = registerFlag(ModBlocks.FLAG_LIME);
	BlockItem FLAG_MAGENTA = registerFlag(ModBlocks.FLAG_MAGENTA);
	BlockItem FLAG_ORANGE = registerFlag(ModBlocks.FLAG_ORANGE);
	BlockItem FLAG_PINK = registerFlag(ModBlocks.FLAG_PINK);
	BlockItem FLAG_PURPLE = registerFlag(ModBlocks.FLAG_PURPLE);
	BlockItem FLAG_RED = registerFlag(ModBlocks.FLAG_RED);
	BlockItem FLAG_YELLOW = registerFlag(ModBlocks.FLAG_YELLOW);

	// Globes
	BlockItem EARTH_GLOBE = registerGlobe(ModBlocks.EARTH_GLOBE);
	BlockItem MOON_GLOBE = registerGlobe(ModBlocks.MOON_GLOBE);
	BlockItem MARS_GLOBE = registerGlobe(ModBlocks.MARS_GLOBE);
	BlockItem MERCURY_GLOBE = registerGlobe(ModBlocks.MERCURY_GLOBE);
	BlockItem VENUS_GLOBE = registerGlobe(ModBlocks.VENUS_GLOBE);
	BlockItem GLACIO_GLOBE = registerGlobe(ModBlocks.GLACIO_GLOBE);

	// public static final BlockItem STEEL_CABLE = register("steel_cable", new BlockItem(ModBlocks.STEEL_CABLE, new FabricItemSettings().group(ITEM_GROUP_MACHINES)));
	// public static final BlockItem DESH_CABLE = register("desh_cable", new BlockItem(ModBlocks.DESH_CABLE, new FabricItemSettings().group(ITEM_GROUP_MACHINES)));

	// Block Items
	BlockItem STEEL_BLOCK = registerBlockItem(ModBlocks.STEEL_BLOCK);
	BlockItem CHEESE_BLOCK = registerBlockItem(ModBlocks.CHEESE_BLOCK);
	BlockItem DESH_BLOCK = registerBlockItem(ModBlocks.DESH_BLOCK);
	BlockItem OSTRUM_BLOCK = registerBlockItem(ModBlocks.OSTRUM_BLOCK);
	BlockItem CALORITE_BLOCK = registerBlockItem(ModBlocks.CALORITE_BLOCK);
	BlockItem RAW_DESH_BLOCK = registerBlockItem(ModBlocks.RAW_DESH_BLOCK);
	BlockItem RAW_OSTRUM_BLOCK = registerBlockItem(ModBlocks.RAW_OSTRUM_BLOCK);
	BlockItem RAW_CALORITE_BLOCK = registerBlockItem(ModBlocks.RAW_CALORITE_BLOCK);

	BlockItem IRON_PLATING = registerBlockItem(ModBlocks.IRON_PLATING);
	BlockItem IRON_PLATING_STAIRS = registerBlockItem(ModBlocks.IRON_PLATING_STAIRS);
	BlockItem IRON_PLATING_SLAB = registerBlockItem(ModBlocks.IRON_PLATING_SLAB);
	BlockItem IRON_PILLAR = registerBlockItem(ModBlocks.IRON_PILLAR);
	BlockItem IRON_PLATING_BUTTON = registerBlockItem(ModBlocks.IRON_PLATING_BUTTON);
	BlockItem IRON_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.IRON_PLATING_PRESSURE_PLATE);
	BlockItem MARKED_IRON_PILLAR = registerBlockItem(ModBlocks.MARKED_IRON_PILLAR);
	BlockItem BLUE_IRON_PILLAR = registerBlockItem(ModBlocks.BLUE_IRON_PILLAR);

	BlockItem STEEL_PLATING = registerBlockItem(ModBlocks.STEEL_PLATING);
	BlockItem STEEL_PLATING_STAIRS = registerBlockItem(ModBlocks.STEEL_PLATING_STAIRS);
	BlockItem STEEL_PLATING_SLAB = registerBlockItem(ModBlocks.STEEL_PLATING_SLAB);
	BlockItem STEEL_PILLAR = registerBlockItem(ModBlocks.STEEL_PILLAR);
	BlockItem STEEL_PLATING_BUTTON = registerBlockItem(ModBlocks.STEEL_PLATING_BUTTON);
	BlockItem STEEL_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.STEEL_PLATING_PRESSURE_PLATE);
	BlockItem STEEL_DOOR = registerBlockItem(ModBlocks.STEEL_DOOR);

	BlockItem DESH_PLATING = registerBlockItem(ModBlocks.DESH_PLATING);
	BlockItem DESH_PLATING_STAIRS = registerBlockItem(ModBlocks.DESH_PLATING_STAIRS);
	BlockItem DESH_PLATING_SLAB = registerBlockItem(ModBlocks.DESH_PLATING_SLAB);
	BlockItem DESH_PILLAR = registerBlockItem(ModBlocks.DESH_PILLAR);
	BlockItem DESH_PLATING_BUTTON = registerBlockItem(ModBlocks.DESH_PLATING_BUTTON);
	BlockItem DESH_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.DESH_PLATING_PRESSURE_PLATE);

	BlockItem OSTRUM_PLATING = registerBlockItem(ModBlocks.OSTRUM_PLATING);
	BlockItem OSTRUM_PLATING_STAIRS = registerBlockItem(ModBlocks.OSTRUM_PLATING_STAIRS);
	BlockItem OSTRUM_PLATING_SLAB = registerBlockItem(ModBlocks.OSTRUM_PLATING_SLAB);
	BlockItem OSTRUM_PILLAR = registerBlockItem(ModBlocks.OSTRUM_PILLAR);
	BlockItem OSTRUM_PLATING_BUTTON = registerBlockItem(ModBlocks.OSTRUM_PLATING_BUTTON);
	BlockItem OSTRUM_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE);

	BlockItem CALORITE_PLATING = registerBlockItem(ModBlocks.CALORITE_PLATING);
	BlockItem CALORITE_PLATING_STAIRS = registerBlockItem(ModBlocks.CALORITE_PLATING_STAIRS);
	BlockItem CALORITE_PLATING_SLAB = registerBlockItem(ModBlocks.CALORITE_PLATING_SLAB);
	BlockItem CALORITE_PILLAR = registerBlockItem(ModBlocks.CALORITE_PILLAR);
	BlockItem CALORITE_PLATING_BUTTON = registerBlockItem(ModBlocks.CALORITE_PLATING_BUTTON);
	BlockItem CALORITE_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.CALORITE_PLATING_PRESSURE_PLATE);

	BlockItem SKY_STONE = registerBlockItem(ModBlocks.SKY_STONE);

	BlockItem MOON_SAND = registerBlockItem(ModBlocks.MOON_SAND);
	BlockItem MOON_STONE = registerBlockItem(ModBlocks.MOON_STONE);
	BlockItem MOON_COBBLESTONE = registerBlockItem(ModBlocks.MOON_COBBLESTONE);
	BlockItem MOON_STONE_BRICKS = registerBlockItem(ModBlocks.MOON_STONE_BRICKS);
	BlockItem CRACKED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS);
	BlockItem MOON_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MOON_STONE_BRICK_SLAB);
	BlockItem MOON_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS);
	BlockItem CHISELED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_BRICKS);
	BlockItem CHISELED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_STAIRS);
	BlockItem CHISELED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_SLAB);
	BlockItem POLISHED_MOON_STONE = registerBlockItem(ModBlocks.POLISHED_MOON_STONE);
	BlockItem POLISHED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_STAIRS);
	BlockItem POLISHED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_SLAB);
	BlockItem MOON_PILLAR = registerBlockItem(ModBlocks.MOON_PILLAR);
	BlockItem MOON_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MOON_STONE_BRICK_WALL);

	BlockItem MARS_SAND = registerBlockItem(ModBlocks.MARS_SAND);
	BlockItem MARS_STONE = registerBlockItem(ModBlocks.MARS_STONE);
	BlockItem MARS_COBBLESTONE = registerBlockItem(ModBlocks.MARS_COBBLESTONE);
	BlockItem MARS_STONE_BRICKS = registerBlockItem(ModBlocks.MARS_STONE_BRICKS);
	BlockItem CRACKED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS);
	BlockItem MARS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MARS_STONE_BRICK_SLAB);
	BlockItem MARS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS);
	BlockItem CHISELED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_BRICKS);
	BlockItem CHISELED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_STAIRS);
	BlockItem CHISELED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_SLAB);
	BlockItem POLISHED_MARS_STONE = registerBlockItem(ModBlocks.POLISHED_MARS_STONE);
	BlockItem POLISHED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_STAIRS);
	BlockItem POLISHED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_SLAB);
	BlockItem MARS_PILLAR = registerBlockItem(ModBlocks.MARS_PILLAR);
	BlockItem MARS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MARS_STONE_BRICK_WALL);
	BlockItem CONGLOMERATE = registerBlockItem(ModBlocks.CONGLOMERATE);
	BlockItem POLISHED_CONGLOMERATE = registerBlockItem(ModBlocks.POLISHED_CONGLOMERATE);

	BlockItem VENUS_SAND = registerBlockItem(ModBlocks.VENUS_SAND);
	BlockItem VENUS_STONE = registerBlockItem(ModBlocks.VENUS_STONE);
	BlockItem VENUS_COBBLESTONE = registerBlockItem(ModBlocks.VENUS_COBBLESTONE);
	BlockItem VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.VENUS_STONE_BRICKS);
	BlockItem CRACKED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS);
	BlockItem VENUS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB);
	BlockItem VENUS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS);
	BlockItem CHISELED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_BRICKS);
	BlockItem CHISELED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_STAIRS);
	BlockItem CHISELED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_SLAB);
	BlockItem POLISHED_VENUS_STONE = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE);
	BlockItem POLISHED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_STAIRS);
	BlockItem POLISHED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_SLAB);
	BlockItem VENUS_PILLAR = registerBlockItem(ModBlocks.VENUS_PILLAR);
	BlockItem VENUS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_WALL);

	BlockItem VENUS_SANDSTONE = registerBlockItem(ModBlocks.VENUS_SANDSTONE);
	BlockItem VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICKS);
	BlockItem CRACKED_VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
	BlockItem VENUS_SANDSTONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_SLAB);
	BlockItem VENUS_SANDSTONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS);
	BlockItem INFERNAL_SPIRE_BLOCK = registerBlockItem(ModBlocks.INFERNAL_SPIRE_BLOCK);

	BlockItem MERCURY_STONE = registerBlockItem(ModBlocks.MERCURY_STONE);
	BlockItem MERCURY_COBBLESTONE = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE);
	BlockItem MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICKS);
	BlockItem CRACKED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
	BlockItem MERCURY_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB);
	BlockItem MERCURY_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS);
	BlockItem CHISELED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
	BlockItem CHISELED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_STAIRS);
	BlockItem CHISELED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_SLAB);
	BlockItem POLISHED_MERCURY_STONE = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE);
	BlockItem POLISHED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_STAIRS);
	BlockItem POLISHED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_SLAB);
	BlockItem MERCURY_PILLAR = registerBlockItem(ModBlocks.MERCURY_PILLAR);
	BlockItem MERCURY_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_WALL);

	BlockItem GLACIO_STONE = registerBlockItem(ModBlocks.GLACIO_STONE);
	BlockItem GLACIO_COBBLESTONE = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE);
	BlockItem GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICKS);
	BlockItem CRACKED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
	BlockItem GLACIO_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB);
	BlockItem GLACIO_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS);
	BlockItem CHISELED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
	BlockItem CHISELED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_STAIRS);
	BlockItem CHISELED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_SLAB);
	BlockItem POLISHED_GLACIO_STONE = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE);
	BlockItem POLISHED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_STAIRS);
	BlockItem POLISHED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_SLAB);
	BlockItem GLACIO_PILLAR = registerBlockItem(ModBlocks.GLACIO_PILLAR);
	BlockItem GLACIO_STONE_BRICK_WALL = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_WALL);
	BlockItem PERMAFROST = registerBlockItem(ModBlocks.PERMAFROST);
	BlockItem PERMAFROST_BRICKS = registerBlockItem(ModBlocks.PERMAFROST_BRICKS);
	BlockItem PERMAFROST_BRICK_STAIRS = registerBlockItem(ModBlocks.PERMAFROST_BRICK_STAIRS);
	BlockItem PERMAFROST_BRICK_SLAB = registerBlockItem(ModBlocks.PERMAFROST_BRICK_SLAB);
	BlockItem CRACKED_PERMAFROST_BRICKS = registerBlockItem(ModBlocks.CRACKED_PERMAFROST_BRICKS);

	BlockItem MOON_CHEESE_ORE = registerBlockItem(ModBlocks.MOON_CHEESE_ORE);
	BlockItem MOON_DESH_ORE = registerBlockItem(ModBlocks.MOON_DESH_ORE);
	BlockItem DEEPSLATE_DESH_ORE = registerBlockItem(ModBlocks.DEEPSLATE_DESH_ORE);
	BlockItem MOON_IRON_ORE = registerBlockItem(ModBlocks.MOON_IRON_ORE);
	BlockItem MOON_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MOON_ICE_SHARD_ORE);
	BlockItem DEEPSLATE_ICE_SHARD_ORE = registerBlockItem(ModBlocks.DEEPSLATE_ICE_SHARD_ORE);
	BlockItem MARS_IRON_ORE = registerBlockItem(ModBlocks.MARS_IRON_ORE);
	BlockItem MARS_DIAMOND_ORE = registerBlockItem(ModBlocks.MARS_DIAMOND_ORE);
	BlockItem MARS_OSTRUM_ORE = registerBlockItem(ModBlocks.MARS_OSTRUM_ORE);
	BlockItem DEEPSLATE_OSTRUM_ORE = registerBlockItem(ModBlocks.DEEPSLATE_OSTRUM_ORE);
	BlockItem MARS_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MARS_ICE_SHARD_ORE);
	BlockItem MERCURY_IRON_ORE = registerBlockItem(ModBlocks.MERCURY_IRON_ORE);
	BlockItem VENUS_COAL_ORE = registerBlockItem(ModBlocks.VENUS_COAL_ORE);
	BlockItem VENUS_GOLD_ORE = registerBlockItem(ModBlocks.VENUS_GOLD_ORE);
	BlockItem VENUS_DIAMOND_ORE = registerBlockItem(ModBlocks.VENUS_DIAMOND_ORE);
	BlockItem VENUS_CALORITE_ORE = registerBlockItem(ModBlocks.VENUS_CALORITE_ORE);
	BlockItem DEEPSLATE_CALORITE_ORE = registerBlockItem(ModBlocks.DEEPSLATE_CALORITE_ORE);
	BlockItem GLACIO_ICE_SHARD_ORE = registerBlockItem(ModBlocks.GLACIO_ICE_SHARD_ORE);
	BlockItem GLACIO_COAL_ORE = registerBlockItem(ModBlocks.GLACIO_COAL_ORE);
	BlockItem GLACIO_COPPER_ORE = registerBlockItem(ModBlocks.GLACIO_COPPER_ORE);
	BlockItem GLACIO_IRON_ORE = registerBlockItem(ModBlocks.GLACIO_IRON_ORE);
	BlockItem GLACIO_LAPIS_ORE = registerBlockItem(ModBlocks.GLACIO_LAPIS_ORE);

	// Spawn eggs
	Item LUNARIAN_SPAWN_EGG = register("lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.LUNARIAN, -13382401, -11650781, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item CORRUPED_LUNARIAN_SPAWN_EGG = register("corrupted_lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.CORRUPTED_LUNARIAN, -14804199, -16740159, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item STAR_CRAWLER_SPAWN_EGG = register("star_crawler_spawn_egg", new SpawnEggItem(ModEntityTypes.STAR_CRAWLER, -13421773, -16724788, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item MARTIAN_RAPTOR_SPAWN_EGG = register("martian_raptor_spawn_egg", new SpawnEggItem(ModEntityTypes.MARTIAN_RAPTOR, 5349438, -13312, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item PYGRO_SPAWN_EGG = register("pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO, -3381760, -6750208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item ZOMBIFIED_PYGRO_SPAWN_EGG = register("zombified_pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_PYGRO, 8473125, 6131271, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item PYGRO_BRUTE_SPAWN_EGG = register("pygro_brute_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO_BRUTE, -3381760, -67208, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item MOGLER_SPAWN_EGG = register("mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.MOGLER, -13312, -3407872, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item ZOMBIFIED_MOGLER_SPAWN_EGG = register("zombified_mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_MOGLER, 12537409, 7988821, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item SULFUR_CREEPER_SPAWN_EGG = register("sulfur_creeper_spawn_egg", new SpawnEggItem(ModEntityTypes.SULFUR_CREEPER, 13930288, 11303196, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));
	Item LUNARIAN_WANDERING_TRADER_SPAWN_EGG = register("lunarian_wandering_trader_spawn_egg", new SpawnEggItem(ModEntityTypes.LUNARIAN_WANDERING_TRADER, 5993415, 8537301, new FabricItemSettings().group(ITEM_GROUP_SPAWN_EGGS)));

	static void register() {
		registerTank(TIER_1_ROCKET);
		registerTank(TIER_2_ROCKET);
		registerTank(TIER_3_ROCKET);
		registerTank(TIER_4_ROCKET);
		registerTank(TIER_1_ROVER);
		registerTank(OXYGEN_TANK);
	}

	static BlockItem registerFlag(Block flag) {
		TallBlockItem item = new TallBlockItem(flag, new FabricItemSettings().group(ITEM_GROUP_FLAGS));
		register(Registry.BLOCK.getId(flag), item);
		return item;
	}

	static BlockItem registerGlobe(Block globe) {
		BlockItem item = new BlockItem(globe, new FabricItemSettings().group(ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
		register(Registry.BLOCK.getId(globe), item);
		return item;
	}

	static BlockItem registerBlockItem(Block block) {
		return registerBlockItem(block, ITEM_GROUP_BLOCKS);
	}

	static BlockItem registerBlockItem(Block block, ItemGroup group) {
		BlockItem item = new BlockItem(block, new FabricItemSettings().group(group));
		register(Registry.BLOCK.getId(block), item);
		return item;
	}

	static Item registerItem(String id, ItemGroup group) {
		Item item = new Item(new FabricItemSettings().group(group));
		register(id, item);
		return item;
	}

	static <T extends Item> T register(String id, T item) {
		return register(new ModIdentifier(id), item);
	}

	static <T extends Item> T register(Identifier id, T item) {
		Registry.register(Registry.ITEM, id, item);
		items.add(item);
		return item;
	}

	static void registerTank(Item tank) {
		FluidStorage.ITEM.registerForItems(TankStorage::new, tank);
	}
}