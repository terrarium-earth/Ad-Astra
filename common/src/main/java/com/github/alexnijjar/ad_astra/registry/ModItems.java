package com.github.alexnijjar.ad_astra.registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import com.github.alexnijjar.ad_astra.items.*;
import com.github.alexnijjar.ad_astra.items.armour.JetSuit;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.items.vehicles.RocketItem;
import com.github.alexnijjar.ad_astra.items.vehicles.RoverItem;
import com.github.alexnijjar.ad_astra.mixin.AxeItemAccessor;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SignItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(AdAstra.MOD_ID, Registry.ITEM_KEY);
	public static final Set<Item> items = new HashSet<>();

	// Vehicles Items
	public static final RegistrySupplier<Item> TIER_1_ROCKET = register("tier_1_rocket", new RocketItem<>(ModEntityTypes.TIER_1_ROCKET.get(), 1, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final RegistrySupplier<Item> TIER_2_ROCKET = register("tier_2_rocket", new RocketItem<>(ModEntityTypes.TIER_2_ROCKET.get(), 2, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final RegistrySupplier<Item> TIER_3_ROCKET = register("tier_3_rocket", new RocketItem<>(ModEntityTypes.TIER_3_ROCKET.get(), 3, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final RegistrySupplier<Item> TIER_4_ROCKET = register("tier_4_rocket", new RocketItem<>(ModEntityTypes.TIER_4_ROCKET.get(), 4, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));
	public static final RegistrySupplier<Item> TIER_1_ROVER = register("tier_1_rover", new RoverItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1).fireproof()));

	public static final RegistrySupplier<Item> OXYGEN_TANK = register("oxygen_tank", new OxygenTankItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).maxCount(1)));

	public static final RegistrySupplier<Item> ASTRODUX = register("astrodux", new AstroduxItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL)));

	public static final RegistrySupplier<Item> SPACE_PAINTING = register("space_painting", new SpacePaintingItem(ModEntityTypes.SPACE_PAINTING.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).rarity(Rarity.UNCOMMON)));

	public static final RegistrySupplier<Item> CHEESE = register("cheese", new Item(new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).food(new FoodComponent.Builder().hunger(4).saturationModifier(1.0f).build())));

	public static final RegistrySupplier<Item> LAUNCH_PAD = register("launch_pad", new ModRenderedBlockItem(ModBlocks.LAUNCH_PAD.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_BLOCKS)));

	// Buckets
	public static final RegistrySupplier<Item> OIL_BUCKET = register("oil_bucket", new BucketItem(ModFluids.OIL_STILL.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final RegistrySupplier<Item> FUEL_BUCKET = register("fuel_bucket", new BucketItem(ModFluids.FUEL_STILL.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final RegistrySupplier<Item> CRYO_FUEL_BUCKET = register("cryo_fuel_bucket", new BucketItem(ModFluids.CRYO_FUEL_STILL.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)));
	public static final RegistrySupplier<Item> OXYGEN_BUCKET = register("oxygen_bucket", new BucketItem(ModFluids.OXYGEN_STILL.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).recipeRemainder(Items.BUCKET).maxCount(1)) {
		@Override
		public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
			ItemStack itemStack = user.getStackInHand(hand);
			return TypedActionResult.fail(itemStack);
		}
	});

	// Spacesuit
	public static final RegistrySupplier<SpaceSuit> SPACE_HELMET = register("space_helmet", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final RegistrySupplier<SpaceSuit> SPACE_SUIT = register("space_suit", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final RegistrySupplier<SpaceSuit> SPACE_PANTS = register("space_pants", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL)));
	public static final RegistrySupplier<SpaceSuit> SPACE_BOOTS = register("space_boots", new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL)));

	// Netherite Spacesuit
	public static final RegistrySupplier<NetheriteSpaceSuit> NETHERITE_SPACE_HELMET = register("netherite_space_helmet", new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<NetheriteSpaceSuit> NETHERITE_SPACE_SUIT = register("netherite_space_suit", new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<NetheriteSpaceSuit> NETHERITE_SPACE_PANTS = register("netherite_space_pants", new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<NetheriteSpaceSuit> NETHERITE_SPACE_BOOTS = register("netherite_space_boots", new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));

	// Jet Suit
	public static final RegistrySupplier<JetSuit> JET_SUIT_HELMET = register("jet_suit_helmet", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<JetSuit> JET_SUIT = register("jet_suit", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<JetSuit> JET_SUIT_PANTS = register("jet_suit_pants", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));
	public static final RegistrySupplier<JetSuit> JET_SUIT_BOOTS = register("jet_suit_boots", new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ModItemGroups.ITEM_GROUP_NORMAL).fireproof()));

	// Machines
	public static final RegistrySupplier<Item> COAL_GENERATOR = register("coal_generator", new MachineBlockItem(ModBlocks.COAL_GENERATOR.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((Text.translatable("item.ad_astra.generator_energy.tooltip", AdAstra.CONFIG.coalGenerator.energyPerTick).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.coal_generator.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> COMPRESSOR = register("compressor", new MachineBlockItem(ModBlocks.COMPRESSOR.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.compressor.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> NASA_WORKBENCH = register("nasa_workbench", new MachineBlockItem(ModBlocks.NASA_WORKBENCH.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.nasa_workbench.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> FUEL_REFINERY = register("fuel_refinery", new MachineBlockItem(ModBlocks.FUEL_REFINERY.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.fuel_refinery.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> OXYGEN_LOADER = register("oxygen_loader", new MachineBlockItem(ModBlocks.OXYGEN_LOADER.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.oxygen_loader.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.oxygen_loader.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> SOLAR_PANEL = register("solar_panel", new SolarPanelBlockItem(ModBlocks.SOLAR_PANEL.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((Text.translatable("item.ad_astra.generator_energy.tooltip", SolarPanelBlockEntity.getEnergyForDimension(world)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.solar_panel.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.solar_panel.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> OXYGEN_DISTRIBUTOR = register("oxygen_distributor", new MachineBlockItem(ModBlocks.OXYGEN_DISTRIBUTOR.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.oxygen_distributor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.oxygen_distributor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> WATER_PUMP = register("water_pump", new MachineBlockItem(ModBlocks.WATER_PUMP.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add((Text.translatable("item.ad_astra.fluid_transfer_rate.tooltip", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.BLUE))));
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.water_pump.tooltip[0]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.water_pump.tooltip[1]", FluidUtils.dropletsToMillibuckets(AdAstra.CONFIG.waterPump.transferPerTick)).setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> ENERGIZER = register("energizer", new EnergizerBlockItem(ModBlocks.ENERGIZER.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			super.appendTooltip(stack, world, tooltip, context);
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.energizer.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.energizer.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> CRYO_FREEZER = register("cryo_freezer", new MachineBlockItem(ModBlocks.CRYO_FREEZER.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.cryo_freezer.tooltip").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});
	public static final RegistrySupplier<Item> OXYGEN_SENSOR = register("oxygen_sensor", new MachineBlockItem(ModBlocks.OXYGEN_SENSOR.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)) {
		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			if (world != null && world.isClient) {
				if (Screen.hasShiftDown()) {
					tooltip.add((Text.translatable("item.ad_astra.oxygen_sensor.tooltip[0]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
					tooltip.add((Text.translatable("item.ad_astra.oxygen_sensor.tooltip[1]").setStyle(Style.EMPTY.withColor(Formatting.GREEN))));
				} else {
					tooltip.add((Text.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(Formatting.GRAY))));
				}
			}
		}
	});

	public static final RegistrySupplier<Item> WRENCH = register("wrench", new WrenchItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES).maxCount(1)));
	public static final RegistrySupplier<Item> HAMMER = register("hammer", new HammerItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_BASICS).maxCount(1).maxDamage(AdAstra.CONFIG.general.hammerDurability)));

	public static final RegistrySupplier<Item> IRON_ROD = registerItem("iron_rod", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> OXYGEN_GEAR = registerItem("oxygen_gear", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> WHEEL = registerItem("wheel", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> ENGINE_FRAME = registerItem("engine_frame", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> ENGINE_FAN = registerItem("engine_fan", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> ROCKET_NOSE_CONE = registerItem("rocket_nose_cone", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> STEEL_ENGINE = registerItem("steel_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> DESH_ENGINE = registerItem("desh_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> OSTRUM_ENGINE = registerItem("ostrum_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> CALORITE_ENGINE = registerItem("calorite_engine", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> STEEL_TANK = registerItem("steel_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> DESH_TANK = registerItem("desh_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> OSTRUM_TANK = registerItem("ostrum_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> CALORITE_TANK = registerItem("calorite_tank", ModItemGroups.ITEM_GROUP_BASICS);
	public static final RegistrySupplier<Item> ROCKET_FIN = registerItem("rocket_fin", ModItemGroups.ITEM_GROUP_BASICS);

	// Torch items
	public static final RegistrySupplier<Item> EXTINGUISHED_TORCH = register("extinguished_torch", new WallStandingBlockItem(ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_BASICS)));
	public static final RegistrySupplier<Item> EXTINGUISHED_LANTERN = registerBlockItem(ModBlocks.EXTINGUISHED_LANTERN, ModItemGroups.ITEM_GROUP_BASICS);

	public static final RegistrySupplier<Item> STEEL_INGOT = registerItem("steel_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> DESH_INGOT = registerItem("desh_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> OSTRUM_INGOT = registerItem("ostrum_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> CALORITE_INGOT = registerItem("calorite_ingot", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final RegistrySupplier<Item> ICE_SHARD = registerItem("ice_shard", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final RegistrySupplier<Item> IRON_PLATE = registerItem("iron_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> STEEL_PLATE = registerItem("steel_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> DESH_PLATE = registerItem("desh_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> OSTRUM_PLATE = registerItem("ostrum_plate", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> CALORITE_PLATE = registerItem("calorite_plate", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final RegistrySupplier<Item> STEEL_NUGGET = registerItem("steel_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> DESH_NUGGET = registerItem("desh_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> OSTRUM_NUGGET = registerItem("ostrum_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> CALORITE_NUGGET = registerItem("calorite_nugget", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final RegistrySupplier<Item> RAW_DESH = registerItem("raw_desh", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> RAW_OSTRUM = registerItem("raw_ostrum", ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> RAW_CALORITE = registerItem("raw_calorite", ModItemGroups.ITEM_GROUP_MATERIALS);

	public static final RegistrySupplier<Item> STROPHAR_MUSHROOM = registerBlockItem(ModBlocks.STROPHAR_MUSHROOM, ModItemGroups.ITEM_GROUP_MATERIALS);
	public static final RegistrySupplier<Item> AERONOS_MUSHROOM = registerBlockItem(ModBlocks.AERONOS_MUSHROOM, ModItemGroups.ITEM_GROUP_MATERIALS);

	// Flags
	public static final RegistrySupplier<Item> WHITE_FLAG = registerFlag(ModBlocks.WHITE_FLAG);
	public static final RegistrySupplier<Item> ORANGE_FLAG = registerFlag(ModBlocks.ORANGE_FLAG);
	public static final RegistrySupplier<Item> MAGENTA_FLAG = registerFlag(ModBlocks.MAGENTA_FLAG);
	public static final RegistrySupplier<Item> LIGHT_BLUE_FLAG = registerFlag(ModBlocks.LIGHT_BLUE_FLAG);
	public static final RegistrySupplier<Item> YELLOW_FLAG = registerFlag(ModBlocks.YELLOW_FLAG);
	public static final RegistrySupplier<Item> LIME_FLAG = registerFlag(ModBlocks.LIME_FLAG);
	public static final RegistrySupplier<Item> PINK_FLAG = registerFlag(ModBlocks.PINK_FLAG);
	public static final RegistrySupplier<Item> GRAY_FLAG = registerFlag(ModBlocks.GRAY_FLAG);
	public static final RegistrySupplier<Item> LIGHT_GRAY_FLAG = registerFlag(ModBlocks.LIGHT_GRAY_FLAG);
	public static final RegistrySupplier<Item> CYAN_FLAG = registerFlag(ModBlocks.CYAN_FLAG);
	public static final RegistrySupplier<Item> PURPLE_FLAG = registerFlag(ModBlocks.PURPLE_FLAG);
	public static final RegistrySupplier<Item> BLUE_FLAG = registerFlag(ModBlocks.BLUE_FLAG);
	public static final RegistrySupplier<Item> BROWN_FLAG = registerFlag(ModBlocks.BROWN_FLAG);
	public static final RegistrySupplier<Item> GREEN_FLAG = registerFlag(ModBlocks.GREEN_FLAG);
	public static final RegistrySupplier<Item> RED_FLAG = registerFlag(ModBlocks.RED_FLAG);
	public static final RegistrySupplier<Item> BLACK_FLAG = registerFlag(ModBlocks.BLACK_FLAG);

	// Globes
	public static final RegistrySupplier<Item> EARTH_GLOBE = registerGlobe(ModBlocks.EARTH_GLOBE);
	public static final RegistrySupplier<Item> MOON_GLOBE = registerGlobe(ModBlocks.MOON_GLOBE);
	public static final RegistrySupplier<Item> MARS_GLOBE = registerGlobe(ModBlocks.MARS_GLOBE);
	public static final RegistrySupplier<Item> MERCURY_GLOBE = registerGlobe(ModBlocks.MERCURY_GLOBE);
	public static final RegistrySupplier<Item> VENUS_GLOBE = registerGlobe(ModBlocks.VENUS_GLOBE);
	public static final RegistrySupplier<Item> GLACIO_GLOBE = registerGlobe(ModBlocks.GLACIO_GLOBE);

	// Cables
	public static final RegistrySupplier<Item> STEEL_CABLE = register("steel_cable", new BlockItem(ModBlocks.STEEL_CABLE.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)));
	public static final RegistrySupplier<Item> DESH_CABLE = register("desh_cable", new BlockItem(ModBlocks.DESH_CABLE.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)));

	// Fluid pipes
	public static final RegistrySupplier<Item> DESH_FLUID_PIPE = register("desh_fluid_pipe", new BlockItem(ModBlocks.DESH_FLUID_PIPE.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)));
	public static final RegistrySupplier<Item> OSTRUM_FLUID_PIPE = register("ostrum_fluid_pipe", new BlockItem(ModBlocks.OSTRUM_FLUID_PIPE.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_MACHINES)));

	// Blocks
	// Iron
	public static final RegistrySupplier<Item> IRON_PLATING = registerBlockItem(ModBlocks.IRON_PLATING);
	public static final RegistrySupplier<Item> IRON_PLATING_STAIRS = registerBlockItem(ModBlocks.IRON_PLATING_STAIRS);
	public static final RegistrySupplier<Item> IRON_PLATING_SLAB = registerBlockItem(ModBlocks.IRON_PLATING_SLAB);
	public static final RegistrySupplier<Item> IRON_PILLAR = registerBlockItem(ModBlocks.IRON_PILLAR);
	public static final RegistrySupplier<Item> IRON_PLATING_BUTTON = registerBlockItem(ModBlocks.IRON_PLATING_BUTTON);
	public static final RegistrySupplier<Item> IRON_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.IRON_PLATING_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> MARKED_IRON_PILLAR = registerBlockItem(ModBlocks.MARKED_IRON_PILLAR);
	public static final RegistrySupplier<Item> GLOWING_IRON_PILLAR = registerBlockItem(ModBlocks.GLOWING_IRON_PILLAR);
	public static final RegistrySupplier<Item> IRON_SLIDING_DOOR = registerBlockItem(ModBlocks.IRON_SLIDING_DOOR);

	// Steel
	public static final RegistrySupplier<Item> STEEL_BLOCK = registerBlockItem(ModBlocks.STEEL_BLOCK);
	public static final RegistrySupplier<Item> STEEL_PLATING = registerBlockItem(ModBlocks.STEEL_PLATING);
	public static final RegistrySupplier<Item> STEEL_PLATING_STAIRS = registerBlockItem(ModBlocks.STEEL_PLATING_STAIRS);
	public static final RegistrySupplier<Item> STEEL_PLATING_SLAB = registerBlockItem(ModBlocks.STEEL_PLATING_SLAB);
	public static final RegistrySupplier<Item> STEEL_PILLAR = registerBlockItem(ModBlocks.STEEL_PILLAR);
	public static final RegistrySupplier<Item> STEEL_PLATING_BUTTON = registerBlockItem(ModBlocks.STEEL_PLATING_BUTTON);
	public static final RegistrySupplier<Item> STEEL_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.STEEL_PLATING_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> GLOWING_STEEL_PILLAR = registerBlockItem(ModBlocks.GLOWING_STEEL_PILLAR);
	public static final RegistrySupplier<Item> STEEL_SLIDING_DOOR = registerBlockItem(ModBlocks.STEEL_SLIDING_DOOR);
	public static final RegistrySupplier<Item> AIRLOCK = registerBlockItem(ModBlocks.AIRLOCK);
	public static final RegistrySupplier<Item> REINFORCED_DOOR = registerBlockItem(ModBlocks.REINFORCED_DOOR);
	public static final RegistrySupplier<Item> STEEL_DOOR = registerBlockItem(ModBlocks.STEEL_DOOR);
	public static final RegistrySupplier<Item> STEEL_TRAPDOOR = registerBlockItem(ModBlocks.STEEL_TRAPDOOR);

	// Moon
	public static final RegistrySupplier<Item> CHEESE_BLOCK = registerBlockItem(ModBlocks.CHEESE_BLOCK);
	public static final RegistrySupplier<Item> DESH_BLOCK = registerBlockItem(ModBlocks.DESH_BLOCK);
	public static final RegistrySupplier<Item> RAW_DESH_BLOCK = registerBlockItem(ModBlocks.RAW_DESH_BLOCK);
	public static final RegistrySupplier<Item> DESH_PLATING = registerBlockItem(ModBlocks.DESH_PLATING);
	public static final RegistrySupplier<Item> DESH_PLATING_STAIRS = registerBlockItem(ModBlocks.DESH_PLATING_STAIRS);
	public static final RegistrySupplier<Item> DESH_PLATING_SLAB = registerBlockItem(ModBlocks.DESH_PLATING_SLAB);
	public static final RegistrySupplier<Item> DESH_PILLAR = registerBlockItem(ModBlocks.DESH_PILLAR);
	public static final RegistrySupplier<Item> DESH_PLATING_BUTTON = registerBlockItem(ModBlocks.DESH_PLATING_BUTTON);
	public static final RegistrySupplier<Item> DESH_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.DESH_PLATING_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> GLOWING_DESH_PILLAR = registerBlockItem(ModBlocks.GLOWING_DESH_PILLAR);
	public static final RegistrySupplier<Item> DESH_SLIDING_DOOR = registerBlockItem(ModBlocks.DESH_SLIDING_DOOR);

	// Mars
	public static final RegistrySupplier<Item> OSTRUM_BLOCK = registerBlockItem(ModBlocks.OSTRUM_BLOCK);
	public static final RegistrySupplier<Item> RAW_OSTRUM_BLOCK = registerBlockItem(ModBlocks.RAW_OSTRUM_BLOCK);
	public static final RegistrySupplier<Item> OSTRUM_PLATING = registerBlockItem(ModBlocks.OSTRUM_PLATING);
	public static final RegistrySupplier<Item> OSTRUM_PLATING_STAIRS = registerBlockItem(ModBlocks.OSTRUM_PLATING_STAIRS);
	public static final RegistrySupplier<Item> OSTRUM_PLATING_SLAB = registerBlockItem(ModBlocks.OSTRUM_PLATING_SLAB);
	public static final RegistrySupplier<Item> OSTRUM_PILLAR = registerBlockItem(ModBlocks.OSTRUM_PILLAR);
	public static final RegistrySupplier<Item> OSTRUM_PLATING_BUTTON = registerBlockItem(ModBlocks.OSTRUM_PLATING_BUTTON);
	public static final RegistrySupplier<Item> OSTRUM_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> GLOWING_OSTRUM_PILLAR = registerBlockItem(ModBlocks.GLOWING_OSTRUM_PILLAR);
	public static final RegistrySupplier<Item> OSTRUM_SLIDING_DOOR = registerBlockItem(ModBlocks.OSTRUM_SLIDING_DOOR);

	// Venus
	public static final RegistrySupplier<Item> CALORITE_BLOCK = registerBlockItem(ModBlocks.CALORITE_BLOCK);
	public static final RegistrySupplier<Item> RAW_CALORITE_BLOCK = registerBlockItem(ModBlocks.RAW_CALORITE_BLOCK);
	public static final RegistrySupplier<Item> CALORITE_PLATING = registerBlockItem(ModBlocks.CALORITE_PLATING);
	public static final RegistrySupplier<Item> CALORITE_PLATING_STAIRS = registerBlockItem(ModBlocks.CALORITE_PLATING_STAIRS);
	public static final RegistrySupplier<Item> CALORITE_PLATING_SLAB = registerBlockItem(ModBlocks.CALORITE_PLATING_SLAB);
	public static final RegistrySupplier<Item> CALORITE_PILLAR = registerBlockItem(ModBlocks.CALORITE_PILLAR);
	public static final RegistrySupplier<Item> CALORITE_PLATING_BUTTON = registerBlockItem(ModBlocks.CALORITE_PLATING_BUTTON);
	public static final RegistrySupplier<Item> CALORITE_PLATING_PRESSURE_PLATE = registerBlockItem(ModBlocks.CALORITE_PLATING_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> GLOWING_CALORITE_PILLAR = registerBlockItem(ModBlocks.GLOWING_CALORITE_PILLAR);
	public static final RegistrySupplier<Item> CALORITE_SLIDING_DOOR = registerBlockItem(ModBlocks.CALORITE_SLIDING_DOOR);

	// Earth blocks
	public static final RegistrySupplier<Item> SKY_STONE = registerBlockItem(ModBlocks.SKY_STONE);

	// Moon blocks
	public static final RegistrySupplier<Item> MOON_SAND = registerBlockItem(ModBlocks.MOON_SAND);
	public static final RegistrySupplier<Item> MOON_STONE = registerBlockItem(ModBlocks.MOON_STONE);
	public static final RegistrySupplier<Item> MOON_STONE_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_STAIRS);
	public static final RegistrySupplier<Item> MOON_STONE_SLAB = registerBlockItem(ModBlocks.MOON_STONE_SLAB);
	public static final RegistrySupplier<Item> MOON_COBBLESTONE = registerBlockItem(ModBlocks.MOON_COBBLESTONE);
	public static final RegistrySupplier<Item> MOON_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MOON_COBBLESTONE_STAIRS);
	public static final RegistrySupplier<Item> MOON_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MOON_COBBLESTONE_SLAB);
	public static final RegistrySupplier<Item> MOON_STONE_BRICKS = registerBlockItem(ModBlocks.MOON_STONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MOON_STONE_BRICKS);
	public static final RegistrySupplier<Item> MOON_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MOON_STONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> MOON_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MOON_STONE_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MOON_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MOON_STONE_SLAB);
	public static final RegistrySupplier<Item> POLISHED_MOON_STONE = registerBlockItem(ModBlocks.POLISHED_MOON_STONE);
	public static final RegistrySupplier<Item> POLISHED_MOON_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_MOON_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MOON_STONE_SLAB);
	public static final RegistrySupplier<Item> MOON_PILLAR = registerBlockItem(ModBlocks.MOON_PILLAR);
	public static final RegistrySupplier<Item> MOON_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MOON_STONE_BRICK_WALL);

	public static final RegistrySupplier<Item> MOON_CHEESE_ORE = registerBlockItem(ModBlocks.MOON_CHEESE_ORE);
	public static final RegistrySupplier<Item> MOON_DESH_ORE = registerBlockItem(ModBlocks.MOON_DESH_ORE);
	public static final RegistrySupplier<Item> DEEPSLATE_DESH_ORE = registerBlockItem(ModBlocks.DEEPSLATE_DESH_ORE);
	public static final RegistrySupplier<Item> MOON_IRON_ORE = registerBlockItem(ModBlocks.MOON_IRON_ORE);
	public static final RegistrySupplier<Item> MOON_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MOON_ICE_SHARD_ORE);

	public static final RegistrySupplier<Item> STROPHAR_CAP = registerBlockItem(ModBlocks.STROPHAR_CAP);
	public static final RegistrySupplier<Item> STROPHAR_DOOR = registerBlockItem(ModBlocks.STROPHAR_DOOR);
	public static final RegistrySupplier<Item> STROPHAR_TRAPDOOR = registerBlockItem(ModBlocks.STROPHAR_TRAPDOOR);
	public static final RegistrySupplier<Item> STROPHAR_PLANKS = registerBlockItem(ModBlocks.STROPHAR_PLANKS);
	public static final RegistrySupplier<Item> STROPHAR_STAIRS = registerBlockItem(ModBlocks.STROPHAR_STAIRS);
	public static final RegistrySupplier<Item> STROPHAR_SLAB = registerBlockItem(ModBlocks.STROPHAR_SLAB);
	public static final RegistrySupplier<Item> STROPHAR_FENCE = registerBlockItem(ModBlocks.STROPHAR_FENCE);
	public static final RegistrySupplier<Item> STROPHAR_FENCE_GATE = registerBlockItem(ModBlocks.STROPHAR_FENCE_GATE);
	public static final RegistrySupplier<Item> STROPHAR_STEM = registerBlockItem(ModBlocks.STROPHAR_STEM);
	public static final RegistrySupplier<Item> STROPHAR_CHEST = register("strophar_chest", new ModRenderedBlockItem(ModBlocks.STROPHAR_CHEST.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_BLOCKS)));
	public static final RegistrySupplier<Item> STROPHAR_LADDER = registerBlockItem(ModBlocks.STROPHAR_LADDER);

	public static final RegistrySupplier<Item> AERONOS_CAP = registerBlockItem(ModBlocks.AERONOS_CAP);
	public static final RegistrySupplier<Item> AERONOS_DOOR = registerBlockItem(ModBlocks.AERONOS_DOOR);
	public static final RegistrySupplier<Item> AERONOS_TRAPDOOR = registerBlockItem(ModBlocks.AERONOS_TRAPDOOR);
	public static final RegistrySupplier<Item> AERONOS_PLANKS = registerBlockItem(ModBlocks.AERONOS_PLANKS);
	public static final RegistrySupplier<Item> AERONOS_STAIRS = registerBlockItem(ModBlocks.AERONOS_STAIRS);
	public static final RegistrySupplier<Item> AERONOS_SLAB = registerBlockItem(ModBlocks.AERONOS_SLAB);
	public static final RegistrySupplier<Item> AERONOS_FENCE = registerBlockItem(ModBlocks.AERONOS_FENCE);
	public static final RegistrySupplier<Item> AERONOS_FENCE_GATE = registerBlockItem(ModBlocks.AERONOS_FENCE_GATE);
	public static final RegistrySupplier<Item> AERONOS_STEM = registerBlockItem(ModBlocks.AERONOS_STEM);
	public static final RegistrySupplier<Item> AERONOS_CHEST = register("aeronos_chest", new ModRenderedBlockItem(ModBlocks.AERONOS_CHEST.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_BLOCKS)));
	public static final RegistrySupplier<Item> AERONOS_LADDER = registerBlockItem(ModBlocks.AERONOS_LADDER);

	// Mars blocks
	public static final RegistrySupplier<Item> MARS_SAND = registerBlockItem(ModBlocks.MARS_SAND);
	public static final RegistrySupplier<Item> MARS_STONE = registerBlockItem(ModBlocks.MARS_STONE);
	public static final RegistrySupplier<Item> MARS_STONE_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_STAIRS);
	public static final RegistrySupplier<Item> MARS_STONE_SLAB = registerBlockItem(ModBlocks.MARS_STONE_SLAB);
	public static final RegistrySupplier<Item> MARS_COBBLESTONE = registerBlockItem(ModBlocks.MARS_COBBLESTONE);
	public static final RegistrySupplier<Item> MARS_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MARS_COBBLESTONE_STAIRS);
	public static final RegistrySupplier<Item> MARS_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MARS_COBBLESTONE_SLAB);
	public static final RegistrySupplier<Item> MARS_STONE_BRICKS = registerBlockItem(ModBlocks.MARS_STONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MARS_STONE_BRICKS);
	public static final RegistrySupplier<Item> MARS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MARS_STONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> MARS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MARS_STONE_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MARS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MARS_STONE_SLAB);
	public static final RegistrySupplier<Item> POLISHED_MARS_STONE = registerBlockItem(ModBlocks.POLISHED_MARS_STONE);
	public static final RegistrySupplier<Item> POLISHED_MARS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_MARS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MARS_STONE_SLAB);
	public static final RegistrySupplier<Item> MARS_PILLAR = registerBlockItem(ModBlocks.MARS_PILLAR);
	public static final RegistrySupplier<Item> MARS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MARS_STONE_BRICK_WALL);

	public static final RegistrySupplier<Item> CONGLOMERATE = registerBlockItem(ModBlocks.CONGLOMERATE);
	public static final RegistrySupplier<Item> POLISHED_CONGLOMERATE = registerBlockItem(ModBlocks.POLISHED_CONGLOMERATE);

	public static final RegistrySupplier<Item> DEEPSLATE_ICE_SHARD_ORE = registerBlockItem(ModBlocks.DEEPSLATE_ICE_SHARD_ORE);
	public static final RegistrySupplier<Item> MARS_IRON_ORE = registerBlockItem(ModBlocks.MARS_IRON_ORE);
	public static final RegistrySupplier<Item> MARS_DIAMOND_ORE = registerBlockItem(ModBlocks.MARS_DIAMOND_ORE);
	public static final RegistrySupplier<Item> MARS_OSTRUM_ORE = registerBlockItem(ModBlocks.MARS_OSTRUM_ORE);
	public static final RegistrySupplier<Item> DEEPSLATE_OSTRUM_ORE = registerBlockItem(ModBlocks.DEEPSLATE_OSTRUM_ORE);
	public static final RegistrySupplier<Item> MARS_ICE_SHARD_ORE = registerBlockItem(ModBlocks.MARS_ICE_SHARD_ORE);

	// Venus blocks
	public static final RegistrySupplier<Item> VENUS_SANDSTONE = registerBlockItem(ModBlocks.VENUS_SANDSTONE);
	public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_VENUS_SANDSTONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
	public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> VENUS_SANDSTONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS);

	public static final RegistrySupplier<Item> VENUS_SAND = registerBlockItem(ModBlocks.VENUS_SAND);
	public static final RegistrySupplier<Item> VENUS_STONE = registerBlockItem(ModBlocks.VENUS_STONE);
	public static final RegistrySupplier<Item> VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_STAIRS);
	public static final RegistrySupplier<Item> VENUS_STONE_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_SLAB);
	public static final RegistrySupplier<Item> VENUS_COBBLESTONE = registerBlockItem(ModBlocks.VENUS_COBBLESTONE);
	public static final RegistrySupplier<Item> VENUS_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.VENUS_COBBLESTONE_STAIRS);
	public static final RegistrySupplier<Item> VENUS_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.VENUS_COBBLESTONE_SLAB);
	public static final RegistrySupplier<Item> VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.VENUS_STONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_VENUS_STONE_BRICKS);
	public static final RegistrySupplier<Item> VENUS_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> VENUS_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_VENUS_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_VENUS_STONE_SLAB);
	public static final RegistrySupplier<Item> POLISHED_VENUS_STONE = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE);
	public static final RegistrySupplier<Item> POLISHED_VENUS_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_VENUS_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_VENUS_STONE_SLAB);
	public static final RegistrySupplier<Item> VENUS_PILLAR = registerBlockItem(ModBlocks.VENUS_PILLAR);
	public static final RegistrySupplier<Item> VENUS_STONE_BRICK_WALL = registerBlockItem(ModBlocks.VENUS_STONE_BRICK_WALL);

	public static final RegistrySupplier<Item> VENUS_COAL_ORE = registerBlockItem(ModBlocks.VENUS_COAL_ORE);
	public static final RegistrySupplier<Item> VENUS_GOLD_ORE = registerBlockItem(ModBlocks.VENUS_GOLD_ORE);
	public static final RegistrySupplier<Item> VENUS_DIAMOND_ORE = registerBlockItem(ModBlocks.VENUS_DIAMOND_ORE);
	public static final RegistrySupplier<Item> VENUS_CALORITE_ORE = registerBlockItem(ModBlocks.VENUS_CALORITE_ORE);
	public static final RegistrySupplier<Item> DEEPSLATE_CALORITE_ORE = registerBlockItem(ModBlocks.DEEPSLATE_CALORITE_ORE);

	public static final RegistrySupplier<Item> INFERNAL_SPIRE_BLOCK = registerBlockItem(ModBlocks.INFERNAL_SPIRE_BLOCK);

	// Mercury blocks
	public static final RegistrySupplier<Item> MERCURY_STONE = registerBlockItem(ModBlocks.MERCURY_STONE);
	public static final RegistrySupplier<Item> MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_STAIRS);
	public static final RegistrySupplier<Item> MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_SLAB);
	public static final RegistrySupplier<Item> MERCURY_COBBLESTONE = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE);
	public static final RegistrySupplier<Item> MERCURY_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE_STAIRS);
	public static final RegistrySupplier<Item> MERCURY_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.MERCURY_COBBLESTONE_SLAB);
	public static final RegistrySupplier<Item> MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
	public static final RegistrySupplier<Item> MERCURY_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> MERCURY_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MERCURY_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_MERCURY_STONE_SLAB);
	public static final RegistrySupplier<Item> POLISHED_MERCURY_STONE = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE);
	public static final RegistrySupplier<Item> POLISHED_MERCURY_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_MERCURY_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_MERCURY_STONE_SLAB);
	public static final RegistrySupplier<Item> MERCURY_PILLAR = registerBlockItem(ModBlocks.MERCURY_PILLAR);
	public static final RegistrySupplier<Item> MERCURY_STONE_BRICK_WALL = registerBlockItem(ModBlocks.MERCURY_STONE_BRICK_WALL);

	public static final RegistrySupplier<Item> MERCURY_IRON_ORE = registerBlockItem(ModBlocks.MERCURY_IRON_ORE);

	// Glacio blocks
	public static final RegistrySupplier<Item> GLACIO_STONE = registerBlockItem(ModBlocks.GLACIO_STONE);
	public static final RegistrySupplier<Item> GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_STAIRS);
	public static final RegistrySupplier<Item> GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_SLAB);
	public static final RegistrySupplier<Item> GLACIO_COBBLESTONE = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE);
	public static final RegistrySupplier<Item> GLACIO_COBBLESTONE_STAIRS = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE_STAIRS);
	public static final RegistrySupplier<Item> GLACIO_COBBLESTONE_SLAB = registerBlockItem(ModBlocks.GLACIO_COBBLESTONE_SLAB);
	public static final RegistrySupplier<Item> GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICKS);
	public static final RegistrySupplier<Item> CRACKED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
	public static final RegistrySupplier<Item> GLACIO_STONE_BRICK_SLAB = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_SLAB);
	public static final RegistrySupplier<Item> GLACIO_STONE_BRICK_STAIRS = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_GLACIO_STONE_BRICKS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.CHISELED_GLACIO_STONE_SLAB);
	public static final RegistrySupplier<Item> POLISHED_GLACIO_STONE = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE);
	public static final RegistrySupplier<Item> POLISHED_GLACIO_STONE_STAIRS = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_GLACIO_STONE_SLAB = registerBlockItem(ModBlocks.POLISHED_GLACIO_STONE_SLAB);
	public static final RegistrySupplier<Item> GLACIO_PILLAR = registerBlockItem(ModBlocks.GLACIO_PILLAR);
	public static final RegistrySupplier<Item> GLACIO_STONE_BRICK_WALL = registerBlockItem(ModBlocks.GLACIO_STONE_BRICK_WALL);

	public static final RegistrySupplier<Item> GLACIO_ICE_SHARD_ORE = registerBlockItem(ModBlocks.GLACIO_ICE_SHARD_ORE);
	public static final RegistrySupplier<Item> GLACIO_COAL_ORE = registerBlockItem(ModBlocks.GLACIO_COAL_ORE);
	public static final RegistrySupplier<Item> GLACIO_COPPER_ORE = registerBlockItem(ModBlocks.GLACIO_COPPER_ORE);
	public static final RegistrySupplier<Item> GLACIO_IRON_ORE = registerBlockItem(ModBlocks.GLACIO_IRON_ORE);
	public static final RegistrySupplier<Item> GLACIO_LAPIS_ORE = registerBlockItem(ModBlocks.GLACIO_LAPIS_ORE);

	public static final RegistrySupplier<Item> PERMAFROST = registerBlockItem(ModBlocks.PERMAFROST);
	public static final RegistrySupplier<Item> PERMAFROST_BRICKS = registerBlockItem(ModBlocks.PERMAFROST_BRICKS);
	public static final RegistrySupplier<Item> PERMAFROST_BRICK_STAIRS = registerBlockItem(ModBlocks.PERMAFROST_BRICK_STAIRS);
	public static final RegistrySupplier<Item> PERMAFROST_BRICK_SLAB = registerBlockItem(ModBlocks.PERMAFROST_BRICK_SLAB);
	public static final RegistrySupplier<Item> CRACKED_PERMAFROST_BRICKS = registerBlockItem(ModBlocks.CRACKED_PERMAFROST_BRICKS);
	public static final RegistrySupplier<Item> PERMAFROST_TILES = registerBlockItem(ModBlocks.PERMAFROST_TILES);
	public static final RegistrySupplier<Item> CHISELED_PERMAFROST_BRICKS = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICKS);
	public static final RegistrySupplier<Item> CHISELED_PERMAFROST_BRICK_STAIRS = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICK_STAIRS);
	public static final RegistrySupplier<Item> CHISELED_PERMAFROST_BRICK_SLAB = registerBlockItem(ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB);
	public static final RegistrySupplier<Item> POLISHED_PERMAFROST = registerBlockItem(ModBlocks.POLISHED_PERMAFROST);
	public static final RegistrySupplier<Item> POLISHED_PERMAFROST_STAIRS = registerBlockItem(ModBlocks.POLISHED_PERMAFROST_STAIRS);
	public static final RegistrySupplier<Item> POLISHED_PERMAFROST_SLAB = registerBlockItem(ModBlocks.POLISHED_PERMAFROST_SLAB);
	public static final RegistrySupplier<Item> PERMAFROST_PILLAR = registerBlockItem(ModBlocks.PERMAFROST_PILLAR);
	public static final RegistrySupplier<Item> PERMAFROST_BRICK_WALL = registerBlockItem(ModBlocks.PERMAFROST_BRICK_WALL);

	public static final RegistrySupplier<Item> GLACIAN_LOG = registerBlockItem(ModBlocks.GLACIAN_LOG);
	public static final RegistrySupplier<Item> STRIPPED_GLACIAN_LOG = registerBlockItem(ModBlocks.STRIPPED_GLACIAN_LOG);
	public static final RegistrySupplier<Item> GLACIAN_LEAVES = registerBlockItem(ModBlocks.GLACIAN_LEAVES);
	public static final RegistrySupplier<Item> GLACIAN_PLANKS = registerBlockItem(ModBlocks.GLACIAN_PLANKS);
	public static final RegistrySupplier<Item> GLACIAN_STAIRS = registerBlockItem(ModBlocks.GLACIAN_STAIRS);
	public static final RegistrySupplier<Item> GLACIAN_SLAB = registerBlockItem(ModBlocks.GLACIAN_SLAB);
	public static final RegistrySupplier<Item> GLACIAN_DOOR = registerBlockItem(ModBlocks.GLACIAN_DOOR);
	public static final RegistrySupplier<Item> GLACIAN_TRAPDOOR = registerBlockItem(ModBlocks.GLACIAN_TRAPDOOR);
	public static final RegistrySupplier<Item> GLACIAN_FENCE = registerBlockItem(ModBlocks.GLACIAN_FENCE);
	public static final RegistrySupplier<Item> GLACIAN_FENCE_GATE = registerBlockItem(ModBlocks.GLACIAN_FENCE_GATE);
	public static final RegistrySupplier<Item> GLACIAN_BUTTON = registerBlockItem(ModBlocks.GLACIAN_BUTTON);
	public static final RegistrySupplier<Item> GLACIAN_PRESSURE_PLATE = registerBlockItem(ModBlocks.GLACIAN_PRESSURE_PLATE);
	public static final RegistrySupplier<Item> GLACIAN_SIGN = register("glacian_sign", new SignItem(new Item.Settings().group(ModItemGroups.ITEM_GROUP_BLOCKS).maxCount(16), ModBlocks.GLACIAN_SIGN.get(), ModBlocks.GLACIAN_WALL_SIGN.get()));
	public static final RegistrySupplier<Item> GLACIAN_FUR = registerBlockItem(ModBlocks.GLACIAN_FUR);

	// Spawn eggs
	// Moon
	public static final RegistrySupplier<Item> LUNARIAN_SPAWN_EGG = register("lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.LUNARIAN.get(), -13382401, -11650781, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> CORRUPTED_LUNARIAN_SPAWN_EGG = register("corrupted_lunarian_spawn_egg", new SpawnEggItem(ModEntityTypes.CORRUPTED_LUNARIAN.get(), -14804199, -16740159, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> STAR_CRAWLER_SPAWN_EGG = register("star_crawler_spawn_egg", new SpawnEggItem(ModEntityTypes.STAR_CRAWLER.get(), -13421773, -16724788, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Mars
	public static final RegistrySupplier<Item> MARTIAN_RAPTOR_SPAWN_EGG = register("martian_raptor_spawn_egg", new SpawnEggItem(ModEntityTypes.MARTIAN_RAPTOR.get(), 5349438, -13312, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Venus
	public static final RegistrySupplier<Item> PYGRO_SPAWN_EGG = register("pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO.get(), -3381760, -6750208, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> ZOMBIFIED_PYGRO_SPAWN_EGG = register("zombified_pygro_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_PYGRO.get(), 8473125, 6131271, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> PYGRO_BRUTE_SPAWN_EGG = register("pygro_brute_spawn_egg", new SpawnEggItem(ModEntityTypes.PYGRO_BRUTE.get(), -3381760, -67208, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> MOGLER_SPAWN_EGG = register("mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.MOGLER.get(), -13312, -3407872, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> ZOMBIFIED_MOGLER_SPAWN_EGG = register("zombified_mogler_spawn_egg", new SpawnEggItem(ModEntityTypes.ZOMBIFIED_MOGLER.get(), 12537409, 7988821, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));
	public static final RegistrySupplier<Item> SULFUR_CREEPER_SPAWN_EGG = register("sulfur_creeper_spawn_egg", new SpawnEggItem(ModEntityTypes.SULFUR_CREEPER.get(), 13930288, 11303196, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Glacio
	public static final RegistrySupplier<Item> GLACIAN_RAM_SPAWN_EGG = register("glacian_ram_spawn_egg", new SpawnEggItem(ModEntityTypes.GLACIAN_RAM.get(), 16770815, 4406589, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	// Other
	public static final RegistrySupplier<Item> LUNARIAN_WANDERING_TRADER_SPAWN_EGG = register("lunarian_wandering_trader_spawn_egg", new SpawnEggItem(ModEntityTypes.LUNARIAN_WANDERING_TRADER.get(), 5993415, 8537301, new Item.Settings().group(ModItemGroups.ITEM_GROUP_SPAWN_EGGS)));

	public static void register() {

		Map<Block, Block> strippedBlocks = new HashMap<>(AxeItemAccessor.adastra_getStrippedBlocks());
		strippedBlocks.put(ModBlocks.GLACIAN_LOG.get(), ModBlocks.STRIPPED_GLACIAN_LOG.get());
		AxeItemAccessor.adastra_setStrippedBlocks(strippedBlocks);
	}

	public static RegistrySupplier<Item> registerFlag(Supplier<Block> flag) {
		TallBlockItem item = new TallBlockItem(flag.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_FLAGS));
		return register(Registry.BLOCK.getId(flag.get()), item);
	}

	public static RegistrySupplier<Item> registerGlobe(Supplier<Block> globe) {
		BlockItem item = new BlockItem(globe.get(), new Item.Settings().group(ModItemGroups.ITEM_GROUP_GLOBES).maxCount(1).rarity(Rarity.RARE));
		return register(Registry.BLOCK.getId(globe.get()), item);
	}

	public static RegistrySupplier<Item> registerBlockItem(Supplier<Block> block) {
		return registerBlockItem(block, ModItemGroups.ITEM_GROUP_BLOCKS);
	}

	public static RegistrySupplier<Item> registerBlockItem(Supplier<Block> block, ItemGroup group) {
		BlockItem item = new BlockItem(block.get(), new Item.Settings().group(group));
		return register(Registry.BLOCK.getId(block.get()), item);
	}

	public static RegistrySupplier<Item> registerItem(String id, ItemGroup group) {
		Item item = new Item(new Item.Settings().group(group));
		return register(id, item);
	}

	public static <T extends Item> RegistrySupplier<T> register(String id, T item) {
		return register(new ModIdentifier(id), item);
	}

	public static <T extends Item> RegistrySupplier<T> register(Identifier id, T item) {
		RegistrySupplier<T> registered = ITEMS.register(id.getPath(), () -> item);
		items.add(item);
		return registered;
	}
}