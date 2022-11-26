package earth.terrarium.ad_astra.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.blocks.machines.entity.SolarPanelBlockEntity;
import earth.terrarium.ad_astra.config.AdAstraConfig;
import earth.terrarium.ad_astra.config.CoalGeneratorConfig;
import earth.terrarium.ad_astra.config.WaterPumpConfig;
import earth.terrarium.ad_astra.items.*;
import earth.terrarium.ad_astra.items.armour.JetSuit;
import earth.terrarium.ad_astra.items.armour.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.items.vehicles.RocketItem;
import earth.terrarium.ad_astra.items.vehicles.RoverItem;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.registry.fluid.FluidBucketItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
    public static final CreativeModeTab ITEM_GROUP = ModRegistryHelpers.createTab(new ModResourceLocation("main"), () -> new ItemStack(ModItems.TIER_1_ROCKET.get()));
    public static final Set<Item> items = new HashSet<>();

    // Vehicles Items
    public static final Supplier<Item> TIER_1_ROCKET = register("tier_1_rocket", () -> new RocketItem<>(ModEntityTypes.TIER_1_ROCKET.get(), 1, new Item.Properties().tab(ITEM_GROUP).stacksTo(1).fireResistant()));
    public static final Supplier<Item> TIER_2_ROCKET = register("tier_2_rocket", () -> new RocketItem<>(ModEntityTypes.TIER_2_ROCKET.get(), 2, new Item.Properties().tab(ITEM_GROUP).stacksTo(1).fireResistant()));
    public static final Supplier<Item> TIER_3_ROCKET = register("tier_3_rocket", () -> new RocketItem<>(ModEntityTypes.TIER_3_ROCKET.get(), 3, new Item.Properties().tab(ITEM_GROUP).stacksTo(1).fireResistant()));
    public static final Supplier<Item> TIER_4_ROCKET = register("tier_4_rocket", () -> new RocketItem<>(ModEntityTypes.TIER_4_ROCKET.get(), 4, new Item.Properties().tab(ITEM_GROUP).stacksTo(1).fireResistant()));
    public static final Supplier<Item> TIER_1_ROVER = register("tier_1_rover", () -> new RoverItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1).fireResistant()));

    public static final Supplier<Item> OXYGEN_TANK = register("oxygen_tank", () -> new OxygenTankItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));

    public static final Supplier<Item> ASTRODUX = register("astrodux", () -> new AstroduxItem(new Item.Properties().tab(ITEM_GROUP)));

    public static final Supplier<Item> SPACE_PAINTING = register("space_painting", () -> new SpacePaintingItem(ModEntityTypes.SPACE_PAINTING.get(), new Item.Properties().tab(ITEM_GROUP).rarity(Rarity.UNCOMMON)));

    public static final Supplier<Item> CHEESE = register("cheese", () -> new Item(new Item.Properties().tab(ITEM_GROUP).food(new FoodProperties.Builder().nutrition(4).saturationMod(1.0f).build())));

    public static final Supplier<Item> LAUNCH_PAD = register("launch_pad", () -> new HoldableOverHeadBlockItem(ModBlocks.LAUNCH_PAD.get(), new Item.Properties().tab(ITEM_GROUP)));

    // Buckets
    public static final Supplier<FluidBucketItem> OIL_BUCKET = register("oil_bucket", () -> new FluidBucketItem(ModFluidProperties.OIL_FLUID, new Item.Properties().tab(ITEM_GROUP).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<FluidBucketItem> FUEL_BUCKET = register("fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.FUEL_FLUID, new Item.Properties().tab(ITEM_GROUP).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<FluidBucketItem> CRYO_FUEL_BUCKET = register("cryo_fuel_bucket", () -> new FluidBucketItem(ModFluidProperties.CRYO_FUEL_FLUID, new Item.Properties().tab(ITEM_GROUP).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final Supplier<FluidBucketItem> OXYGEN_BUCKET = register("oxygen_bucket", () -> new FluidBucketItem(ModFluidProperties.OXYGEN_FLUID, new Item.Properties().tab(ITEM_GROUP).craftRemainder(Items.BUCKET).stacksTo(1)));

    // Spacesuit
    public static final Supplier<SpaceSuit> SPACE_HELMET = register("space_helmet", () -> new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpaceSuit> SPACE_SUIT = register("space_suit", () -> new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpaceSuit> SPACE_PANTS = register("space_pants", () -> new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpaceSuit> SPACE_BOOTS = register("space_boots", () -> new SpaceSuit(ModArmour.SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(ITEM_GROUP)));

    // Netherite Spacesuit
    public static final Supplier<NetheriteSpaceSuit> NETHERITE_SPACE_HELMET = register("netherite_space_helmet", () -> new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<NetheriteSpaceSuit> NETHERITE_SPACE_SUIT = register("netherite_space_suit", () -> new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<NetheriteSpaceSuit> NETHERITE_SPACE_PANTS = register("netherite_space_pants", () -> new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<NetheriteSpaceSuit> NETHERITE_SPACE_BOOTS = register("netherite_space_boots", () -> new NetheriteSpaceSuit(ModArmour.NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(ITEM_GROUP).fireResistant()));

    // Jet Suit
    public static final Supplier<JetSuit> JET_SUIT_HELMET = register("jet_suit_helmet", () -> new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<JetSuit> JET_SUIT = register("jet_suit", () -> new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<JetSuit> JET_SUIT_PANTS = register("jet_suit_pants", () -> new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Properties().tab(ITEM_GROUP).fireResistant()));
    public static final Supplier<JetSuit> JET_SUIT_BOOTS = register("jet_suit_boots", () -> new JetSuit(ModArmour.JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Properties().tab(ITEM_GROUP).fireResistant()));

    // Machines
    public static final Supplier<Item> COAL_GENERATOR = register("coal_generator", () -> new MachineBlockItem(ModBlocks.COAL_GENERATOR.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            tooltip.add((Component.translatable("item.ad_astra.generator_energy.tooltip", CoalGeneratorConfig.energyPerTick).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE))));
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.coal_generator.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> COMPRESSOR = register("compressor", () -> new MachineBlockItem(ModBlocks.COMPRESSOR.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.compressor.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> NASA_WORKBENCH = register("nasa_workbench", () -> new MachineBlockItem(ModBlocks.NASA_WORKBENCH.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.nasa_workbench.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> FUEL_REFINERY = register("fuel_refinery", () -> new MachineBlockItem(ModBlocks.FUEL_REFINERY.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.fuel_refinery.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> OXYGEN_LOADER = register("oxygen_loader", () -> new MachineBlockItem(ModBlocks.OXYGEN_LOADER.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_loader.tooltip[0]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_loader.tooltip[1]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> SOLAR_PANEL = register("solar_panel", () -> new SolarPanelBlockItem(ModBlocks.SOLAR_PANEL.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            tooltip.add((Component.translatable("item.ad_astra.generator_energy.tooltip", SolarPanelBlockEntity.getEnergyForDimension(level)).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE))));
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.solar_panel.tooltip[0]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                    tooltip.add((Component.translatable("item.ad_astra.solar_panel.tooltip[1]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> OXYGEN_DISTRIBUTOR = register("oxygen_distributor", () -> new MachineBlockItem(ModBlocks.OXYGEN_DISTRIBUTOR.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_distributor.tooltip[0]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_distributor.tooltip[1]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> WATER_PUMP = register("water_pump", () -> new MachineBlockItem(ModBlocks.WATER_PUMP.get(), new Item.Properties().tab(ITEM_GROUP)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            tooltip.add((Component.translatable("item.ad_astra.fluid_transfer_rate.tooltip", FluidHooks.toMillibuckets(WaterPumpConfig.transferPerTick))).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)));
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.water_pump.tooltip[0]")).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
                    tooltip.add((Component.translatable("item.ad_astra.water_pump.tooltip[1]")).setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> ENERGIZER = register("energizer", () -> new EnergizerBlockItem(ModBlocks.ENERGIZER.get(), new Item.Properties().tab(ITEM_GROUP).stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag context) {
            super.appendHoverText(stack, level, tooltip, context);
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.energizer.tooltip[0]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                    tooltip.add((Component.translatable("item.ad_astra.energizer.tooltip[1]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> CRYO_FREEZER = register("cryo_freezer", () -> new MachineBlockItem(ModBlocks.CRYO_FREEZER.get(), new Item.Properties().tab(ITEM_GROUP).stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.cryo_freezer.tooltip").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });
    public static final Supplier<Item> OXYGEN_SENSOR = register("oxygen_sensor", () -> new MachineBlockItem(ModBlocks.OXYGEN_SENSOR.get(), new Item.Properties().tab(ITEM_GROUP).stacksTo(1)) {
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
            if (level != null && level.isClientSide) {
                if (Screen.hasShiftDown()) {
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_sensor.tooltip[0]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                    tooltip.add((Component.translatable("item.ad_astra.oxygen_sensor.tooltip[1]").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))));
                } else {
                    tooltip.add((Component.translatable("tooltip.ad_astra.hold_shift").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))));
                }
            }
        }
    });

    public static final Supplier<Item> WRENCH = register("wrench", () -> new WrenchItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
    public static final Supplier<Item> HAMMER = register("hammer", () -> new HammerItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1).durability(AdAstraConfig.hammerDurability)));

    public static final Supplier<Item> IRON_ROD = registerItem("iron_rod");
    public static final Supplier<Item> OXYGEN_GEAR = registerItem("oxygen_gear");
    public static final Supplier<Item> WHEEL = registerItem("wheel");
    public static final Supplier<Item> ENGINE_FRAME = registerItem("engine_frame");
    public static final Supplier<Item> ENGINE_FAN = registerItem("engine_fan");
    public static final Supplier<Item> ROCKET_NOSE_CONE = registerItem("rocket_nose_cone");
    public static final Supplier<Item> STEEL_ENGINE = registerItem("steel_engine");
    public static final Supplier<Item> DESH_ENGINE = registerItem("desh_engine");
    public static final Supplier<Item> OSTRUM_ENGINE = registerItem("ostrum_engine");
    public static final Supplier<Item> CALORITE_ENGINE = registerItem("calorite_engine");
    public static final Supplier<Item> STEEL_TANK = registerItem("steel_tank");
    public static final Supplier<Item> DESH_TANK = registerItem("desh_tank");
    public static final Supplier<Item> OSTRUM_TANK = registerItem("ostrum_tank");
    public static final Supplier<Item> CALORITE_TANK = registerItem("calorite_tank");
    public static final Supplier<Item> ROCKET_FIN = registerItem("rocket_fin");

    // Torch items
    public static final Supplier<Item> EXTINGUISHED_TORCH = register("extinguished_torch", () -> new StandingAndWallBlockItem(ModBlocks.EXTINGUISHED_TORCH.get(), ModBlocks.WALL_EXTINGUISHED_TORCH.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<Item> EXTINGUISHED_LANTERN = registerBlockItem("extinguished_lantern", ModBlocks.EXTINGUISHED_LANTERN);

    public static final Supplier<Item> STEEL_INGOT = registerItem("steel_ingot");
    public static final Supplier<Item> DESH_INGOT = registerItem("desh_ingot");
    public static final Supplier<Item> OSTRUM_INGOT = registerItem("ostrum_ingot");
    public static final Supplier<Item> CALORITE_INGOT = registerItem("calorite_ingot");

    public static final Supplier<Item> ICE_SHARD = registerItem("ice_shard");

    public static final Supplier<Item> IRON_PLATE = registerItem("iron_plate");
    public static final Supplier<Item> STEEL_PLATE = registerItem("steel_plate");
    public static final Supplier<Item> DESH_PLATE = registerItem("desh_plate");
    public static final Supplier<Item> OSTRUM_PLATE = registerItem("ostrum_plate");
    public static final Supplier<Item> CALORITE_PLATE = registerItem("calorite_plate");

    public static final Supplier<Item> STEEL_NUGGET = registerItem("steel_nugget");
    public static final Supplier<Item> DESH_NUGGET = registerItem("desh_nugget");
    public static final Supplier<Item> OSTRUM_NUGGET = registerItem("ostrum_nugget");
    public static final Supplier<Item> CALORITE_NUGGET = registerItem("calorite_nugget");

    public static final Supplier<Item> RAW_DESH = registerItem("raw_desh");
    public static final Supplier<Item> RAW_OSTRUM = registerItem("raw_ostrum");
    public static final Supplier<Item> RAW_CALORITE = registerItem("raw_calorite");

    public static final Supplier<Item> STROPHAR_MUSHROOM = registerBlockItem("strophar_mushroom", ModBlocks.STROPHAR_MUSHROOM);
    public static final Supplier<Item> AERONOS_MUSHROOM = registerBlockItem("aeronos_mushroom", ModBlocks.AERONOS_MUSHROOM);

    // Flags
    public static final Supplier<Item> WHITE_FLAG = registerFlag("white_flag", ModBlocks.WHITE_FLAG);
    public static final Supplier<Item> ORANGE_FLAG = registerFlag("orange_flag", ModBlocks.ORANGE_FLAG);
    public static final Supplier<Item> MAGENTA_FLAG = registerFlag("magenta_flag", ModBlocks.MAGENTA_FLAG);
    public static final Supplier<Item> LIGHT_BLUE_FLAG = registerFlag("light_blue_flag", ModBlocks.LIGHT_BLUE_FLAG);
    public static final Supplier<Item> YELLOW_FLAG = registerFlag("yellow_flag", ModBlocks.YELLOW_FLAG);
    public static final Supplier<Item> LIME_FLAG = registerFlag("lime_flag", ModBlocks.LIME_FLAG);
    public static final Supplier<Item> PINK_FLAG = registerFlag("pink_flag", ModBlocks.PINK_FLAG);
    public static final Supplier<Item> GRAY_FLAG = registerFlag("gray_flag", ModBlocks.GRAY_FLAG);
    public static final Supplier<Item> LIGHT_GRAY_FLAG = registerFlag("light_gray_flag", ModBlocks.LIGHT_GRAY_FLAG);
    public static final Supplier<Item> CYAN_FLAG = registerFlag("cyan_flag", ModBlocks.CYAN_FLAG);
    public static final Supplier<Item> PURPLE_FLAG = registerFlag("purple_flag", ModBlocks.PURPLE_FLAG);
    public static final Supplier<Item> BLUE_FLAG = registerFlag("blue_flag", ModBlocks.BLUE_FLAG);
    public static final Supplier<Item> BROWN_FLAG = registerFlag("brown_flag", ModBlocks.BROWN_FLAG);
    public static final Supplier<Item> GREEN_FLAG = registerFlag("green_flag", ModBlocks.GREEN_FLAG);
    public static final Supplier<Item> RED_FLAG = registerFlag("red_flag", ModBlocks.RED_FLAG);
    public static final Supplier<Item> BLACK_FLAG = registerFlag("black_flag", ModBlocks.BLACK_FLAG);

    // Globes
    public static final Supplier<Item> EARTH_GLOBE = registerGlobe("earth_globe", ModBlocks.EARTH_GLOBE);
    public static final Supplier<Item> MOON_GLOBE = registerGlobe("moon_globe", ModBlocks.MOON_GLOBE);
    public static final Supplier<Item> MARS_GLOBE = registerGlobe("mars_globe", ModBlocks.MARS_GLOBE);
    public static final Supplier<Item> MERCURY_GLOBE = registerGlobe("mercury_globe", ModBlocks.MERCURY_GLOBE);
    public static final Supplier<Item> VENUS_GLOBE = registerGlobe("venus_globe", ModBlocks.VENUS_GLOBE);
    public static final Supplier<Item> GLACIO_GLOBE = registerGlobe("glacio_globe", ModBlocks.GLACIO_GLOBE);

    // Cables
    public static final Supplier<Item> STEEL_CABLE = register("steel_cable", () -> new BlockItem(ModBlocks.STEEL_CABLE.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<Item> DESH_CABLE = register("desh_cable", () -> new BlockItem(ModBlocks.DESH_CABLE.get(), new Item.Properties().tab(ITEM_GROUP)));

    // Fluid pipes
    public static final Supplier<Item> DESH_FLUID_PIPE = register("desh_fluid_pipe", () -> new BlockItem(ModBlocks.DESH_FLUID_PIPE.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<Item> OSTRUM_FLUID_PIPE = register("ostrum_fluid_pipe", () -> new BlockItem(ModBlocks.OSTRUM_FLUID_PIPE.get(), new Item.Properties().tab(ITEM_GROUP)));

    // Blocks
    // Iron
    public static final Supplier<Item> IRON_PLATING = registerBlockItem("iron_plating", ModBlocks.IRON_PLATING);
    public static final Supplier<Item> IRON_PLATING_STAIRS = registerBlockItem("iron_plating_stairs", ModBlocks.IRON_PLATING_STAIRS);
    public static final Supplier<Item> IRON_PLATING_SLAB = registerBlockItem("iron_plating_slab", ModBlocks.IRON_PLATING_SLAB);
    public static final Supplier<Item> IRON_PILLAR = registerBlockItem("iron_pillar", ModBlocks.IRON_PILLAR);
    public static final Supplier<Item> IRON_PLATING_BUTTON = registerBlockItem("iron_plating_button", ModBlocks.IRON_PLATING_BUTTON);
    public static final Supplier<Item> IRON_PLATING_PRESSURE_PLATE = registerBlockItem("iron_plating_pressure_plate", ModBlocks.IRON_PLATING_PRESSURE_PLATE);
    public static final Supplier<Item> MARKED_IRON_PILLAR = registerBlockItem("marked_iron_pillar", ModBlocks.MARKED_IRON_PILLAR);
    public static final Supplier<Item> GLOWING_IRON_PILLAR = registerBlockItem("glowing_iron_pillar", ModBlocks.GLOWING_IRON_PILLAR);
    public static final Supplier<Item> IRON_SLIDING_DOOR = registerBlockItem("iron_sliding_door", ModBlocks.IRON_SLIDING_DOOR);

    // Steel
    public static final Supplier<Item> STEEL_BLOCK = registerBlockItem("steel_block", ModBlocks.STEEL_BLOCK);
    public static final Supplier<Item> STEEL_PLATING = registerBlockItem("steel_plating", ModBlocks.STEEL_PLATING);
    public static final Supplier<Item> STEEL_PLATING_STAIRS = registerBlockItem("steel_plating_stairs", ModBlocks.STEEL_PLATING_STAIRS);
    public static final Supplier<Item> STEEL_PLATING_SLAB = registerBlockItem("steel_plating_slab", ModBlocks.STEEL_PLATING_SLAB);
    public static final Supplier<Item> STEEL_PILLAR = registerBlockItem("steel_pillar", ModBlocks.STEEL_PILLAR);
    public static final Supplier<Item> STEEL_PLATING_BUTTON = registerBlockItem("steel_plating_button", ModBlocks.STEEL_PLATING_BUTTON);
    public static final Supplier<Item> STEEL_PLATING_PRESSURE_PLATE = registerBlockItem("steel_plating_pressure_plate", ModBlocks.STEEL_PLATING_PRESSURE_PLATE);
    public static final Supplier<Item> GLOWING_STEEL_PILLAR = registerBlockItem("glowing_steel_pillar", ModBlocks.GLOWING_STEEL_PILLAR);
    public static final Supplier<Item> STEEL_SLIDING_DOOR = registerBlockItem("steel_sliding_door", ModBlocks.STEEL_SLIDING_DOOR);
    public static final Supplier<Item> AIRLOCK = registerBlockItem("airlock", ModBlocks.AIRLOCK);
    public static final Supplier<Item> REINFORCED_DOOR = registerBlockItem("reinforced_door", ModBlocks.REINFORCED_DOOR);
    public static final Supplier<Item> STEEL_DOOR = registerBlockItem("steel_door", ModBlocks.STEEL_DOOR);
    public static final Supplier<Item> STEEL_TRAPDOOR = registerBlockItem("steel_trapdoor", ModBlocks.STEEL_TRAPDOOR);

    // Moon
    public static final Supplier<Item> CHEESE_BLOCK = registerBlockItem("cheese_block", ModBlocks.CHEESE_BLOCK);
    public static final Supplier<Item> DESH_BLOCK = registerBlockItem("desh_block", ModBlocks.DESH_BLOCK);
    public static final Supplier<Item> RAW_DESH_BLOCK = registerBlockItem("raw_desh_block", ModBlocks.RAW_DESH_BLOCK);
    public static final Supplier<Item> DESH_PLATING = registerBlockItem("desh_plating", ModBlocks.DESH_PLATING);
    public static final Supplier<Item> DESH_PLATING_STAIRS = registerBlockItem("desh_plating_stairs", ModBlocks.DESH_PLATING_STAIRS);
    public static final Supplier<Item> DESH_PLATING_SLAB = registerBlockItem("desh_plating_slab", ModBlocks.DESH_PLATING_SLAB);
    public static final Supplier<Item> DESH_PILLAR = registerBlockItem("desh_pillar", ModBlocks.DESH_PILLAR);
    public static final Supplier<Item> DESH_PLATING_BUTTON = registerBlockItem("desh_plating_button", ModBlocks.DESH_PLATING_BUTTON);
    public static final Supplier<Item> DESH_PLATING_PRESSURE_PLATE = registerBlockItem("desh_plating_pressure_plate", ModBlocks.DESH_PLATING_PRESSURE_PLATE);
    public static final Supplier<Item> GLOWING_DESH_PILLAR = registerBlockItem("glowing_desh_pillar", ModBlocks.GLOWING_DESH_PILLAR);
    public static final Supplier<Item> DESH_SLIDING_DOOR = registerBlockItem("desh_sliding_door", ModBlocks.DESH_SLIDING_DOOR);

    // Mars
    public static final Supplier<Item> OSTRUM_BLOCK = registerBlockItem("ostrum_block", ModBlocks.OSTRUM_BLOCK);
    public static final Supplier<Item> RAW_OSTRUM_BLOCK = registerBlockItem("raw_ostrum_block", ModBlocks.RAW_OSTRUM_BLOCK);
    public static final Supplier<Item> OSTRUM_PLATING = registerBlockItem("ostrum_plating", ModBlocks.OSTRUM_PLATING);
    public static final Supplier<Item> OSTRUM_PLATING_STAIRS = registerBlockItem("ostrum_plating_stairs", ModBlocks.OSTRUM_PLATING_STAIRS);
    public static final Supplier<Item> OSTRUM_PLATING_SLAB = registerBlockItem("ostrum_plating_slab", ModBlocks.OSTRUM_PLATING_SLAB);
    public static final Supplier<Item> OSTRUM_PILLAR = registerBlockItem("ostrum_pillar", ModBlocks.OSTRUM_PILLAR);
    public static final Supplier<Item> OSTRUM_PLATING_BUTTON = registerBlockItem("ostrum_plating_button", ModBlocks.OSTRUM_PLATING_BUTTON);
    public static final Supplier<Item> OSTRUM_PLATING_PRESSURE_PLATE = registerBlockItem("ostrum_plating_pressure_plate", ModBlocks.OSTRUM_PLATING_PRESSURE_PLATE);
    public static final Supplier<Item> GLOWING_OSTRUM_PILLAR = registerBlockItem("glowing_ostrum_pillar", ModBlocks.GLOWING_OSTRUM_PILLAR);
    public static final Supplier<Item> OSTRUM_SLIDING_DOOR = registerBlockItem("ostrum_sliding_door", ModBlocks.OSTRUM_SLIDING_DOOR);

    // Venus
    public static final Supplier<Item> CALORITE_BLOCK = registerBlockItem("calorite_block", ModBlocks.CALORITE_BLOCK);
    public static final Supplier<Item> RAW_CALORITE_BLOCK = registerBlockItem("raw_calorite_block", ModBlocks.RAW_CALORITE_BLOCK);
    public static final Supplier<Item> CALORITE_PLATING = registerBlockItem("calorite_plating", ModBlocks.CALORITE_PLATING);
    public static final Supplier<Item> CALORITE_PLATING_STAIRS = registerBlockItem("calorite_plating_stairs", ModBlocks.CALORITE_PLATING_STAIRS);
    public static final Supplier<Item> CALORITE_PLATING_SLAB = registerBlockItem("calorite_plating_slab", ModBlocks.CALORITE_PLATING_SLAB);
    public static final Supplier<Item> CALORITE_PILLAR = registerBlockItem("calorite_pillar", ModBlocks.CALORITE_PILLAR);
    public static final Supplier<Item> CALORITE_PLATING_BUTTON = registerBlockItem("calorite_plating_button", ModBlocks.CALORITE_PLATING_BUTTON);
    public static final Supplier<Item> CALORITE_PLATING_PRESSURE_PLATE = registerBlockItem("calorite_plating_pressure_plate", ModBlocks.CALORITE_PLATING_PRESSURE_PLATE);
    public static final Supplier<Item> GLOWING_CALORITE_PILLAR = registerBlockItem("glowing_calorite_pillar", ModBlocks.GLOWING_CALORITE_PILLAR);
    public static final Supplier<Item> CALORITE_SLIDING_DOOR = registerBlockItem("calorite_sliding_door", ModBlocks.CALORITE_SLIDING_DOOR);

    // Earth blocks
    public static final Supplier<Item> SKY_STONE = registerBlockItem("sky_stone", ModBlocks.SKY_STONE);

    // Moon blocks
    public static final Supplier<Item> MOON_SAND = registerBlockItem("moon_sand", ModBlocks.MOON_SAND);
    public static final Supplier<Item> MOON_STONE = registerBlockItem("moon_stone", ModBlocks.MOON_STONE);
    public static final Supplier<Item> MOON_STONE_STAIRS = registerBlockItem("moon_stone_stairs", ModBlocks.MOON_STONE_STAIRS);
    public static final Supplier<Item> MOON_STONE_SLAB = registerBlockItem("moon_stone_slab", ModBlocks.MOON_STONE_SLAB);
    public static final Supplier<Item> MOON_COBBLESTONE = registerBlockItem("moon_cobblestone", ModBlocks.MOON_COBBLESTONE);
    public static final Supplier<Item> MOON_COBBLESTONE_STAIRS = registerBlockItem("moon_cobblestone_stairs", ModBlocks.MOON_COBBLESTONE_STAIRS);
    public static final Supplier<Item> MOON_COBBLESTONE_SLAB = registerBlockItem("moon_cobblestone_slab", ModBlocks.MOON_COBBLESTONE_SLAB);
    public static final Supplier<Item> MOON_STONE_BRICKS = registerBlockItem("moon_stone_bricks", ModBlocks.MOON_STONE_BRICKS);
    public static final Supplier<Item> CRACKED_MOON_STONE_BRICKS = registerBlockItem("cracked_moon_stone_bricks", ModBlocks.CRACKED_MOON_STONE_BRICKS);
    public static final Supplier<Item> MOON_STONE_BRICK_SLAB = registerBlockItem("moon_stone_brick_slab", ModBlocks.MOON_STONE_BRICK_SLAB);
    public static final Supplier<Item> MOON_STONE_BRICK_STAIRS = registerBlockItem("moon_stone_brick_stairs", ModBlocks.MOON_STONE_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_MOON_STONE_BRICKS = registerBlockItem("chiseled_moon_stone_bricks", ModBlocks.CHISELED_MOON_STONE_BRICKS);
    public static final Supplier<Item> CHISELED_MOON_STONE_STAIRS = registerBlockItem("chiseled_moon_stone_stairs", ModBlocks.CHISELED_MOON_STONE_STAIRS);
    public static final Supplier<Item> CHISELED_MOON_STONE_SLAB = registerBlockItem("chiseled_moon_stone_slab", ModBlocks.CHISELED_MOON_STONE_SLAB);
    public static final Supplier<Item> POLISHED_MOON_STONE = registerBlockItem("polished_moon_stone", ModBlocks.POLISHED_MOON_STONE);
    public static final Supplier<Item> POLISHED_MOON_STONE_STAIRS = registerBlockItem("polished_moon_stone_stairs", ModBlocks.POLISHED_MOON_STONE_STAIRS);
    public static final Supplier<Item> POLISHED_MOON_STONE_SLAB = registerBlockItem("polished_moon_stone_slab", ModBlocks.POLISHED_MOON_STONE_SLAB);
    public static final Supplier<Item> MOON_PILLAR = registerBlockItem("moon_pillar", ModBlocks.MOON_PILLAR);
    public static final Supplier<Item> MOON_STONE_BRICK_WALL = registerBlockItem("moon_stone_brick_wall", ModBlocks.MOON_STONE_BRICK_WALL);

    public static final Supplier<Item> MOON_CHEESE_ORE = registerBlockItem("moon_cheese_ore", ModBlocks.MOON_CHEESE_ORE);
    public static final Supplier<Item> MOON_DESH_ORE = registerBlockItem("moon_desh_ore", ModBlocks.MOON_DESH_ORE);
    public static final Supplier<Item> DEEPSLATE_DESH_ORE = registerBlockItem("deepslate_desh_ore", ModBlocks.DEEPSLATE_DESH_ORE);
    public static final Supplier<Item> MOON_IRON_ORE = registerBlockItem("moon_iron_ore", ModBlocks.MOON_IRON_ORE);
    public static final Supplier<Item> MOON_ICE_SHARD_ORE = registerBlockItem("moon_ice_shard_ore", ModBlocks.MOON_ICE_SHARD_ORE);

    public static final Supplier<Item> STROPHAR_CAP = registerBlockItem("strophar_cap", ModBlocks.STROPHAR_CAP);
    public static final Supplier<Item> STROPHAR_DOOR = registerBlockItem("strophar_door", ModBlocks.STROPHAR_DOOR);
    public static final Supplier<Item> STROPHAR_TRAPDOOR = registerBlockItem("strophar_trapdoor", ModBlocks.STROPHAR_TRAPDOOR);
    public static final Supplier<Item> STROPHAR_PLANKS = registerBlockItem("strophar_planks", ModBlocks.STROPHAR_PLANKS);
    public static final Supplier<Item> STROPHAR_STAIRS = registerBlockItem("strophar_stairs", ModBlocks.STROPHAR_STAIRS);
    public static final Supplier<Item> STROPHAR_SLAB = registerBlockItem("strophar_slab", ModBlocks.STROPHAR_SLAB);
    public static final Supplier<Item> STROPHAR_FENCE = registerBlockItem("strophar_fence", ModBlocks.STROPHAR_FENCE);
    public static final Supplier<Item> STROPHAR_FENCE_GATE = registerBlockItem("strophar_fence_gate", ModBlocks.STROPHAR_FENCE_GATE);
    public static final Supplier<Item> STROPHAR_STEM = registerBlockItem("strophar_stem", ModBlocks.STROPHAR_STEM);
    public static final Supplier<Item> STROPHAR_CHEST = register("strophar_chest", () -> new ModRenderedBlockItem(ModBlocks.STROPHAR_CHEST.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<Item> STROPHAR_LADDER = registerBlockItem("strophar_ladder", ModBlocks.STROPHAR_LADDER);

    public static final Supplier<Item> AERONOS_CAP = registerBlockItem("aeronos_cap", ModBlocks.AERONOS_CAP);
    public static final Supplier<Item> AERONOS_DOOR = registerBlockItem("aeronos_door", ModBlocks.AERONOS_DOOR);
    public static final Supplier<Item> AERONOS_TRAPDOOR = registerBlockItem("aeronos_trapdoor", ModBlocks.AERONOS_TRAPDOOR);
    public static final Supplier<Item> AERONOS_PLANKS = registerBlockItem("aeronos_planks", ModBlocks.AERONOS_PLANKS);
    public static final Supplier<Item> AERONOS_STAIRS = registerBlockItem("aeronos_stairs", ModBlocks.AERONOS_STAIRS);
    public static final Supplier<Item> AERONOS_SLAB = registerBlockItem("aeronos_slab", ModBlocks.AERONOS_SLAB);
    public static final Supplier<Item> AERONOS_FENCE = registerBlockItem("aeronos_fence", ModBlocks.AERONOS_FENCE);
    public static final Supplier<Item> AERONOS_FENCE_GATE = registerBlockItem("aeronos_fence_gate", ModBlocks.AERONOS_FENCE_GATE);
    public static final Supplier<Item> AERONOS_STEM = registerBlockItem("aeronos_stem", ModBlocks.AERONOS_STEM);
    public static final Supplier<Item> AERONOS_CHEST = register("aeronos_chest", () -> new ModRenderedBlockItem(ModBlocks.AERONOS_CHEST.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<Item> AERONOS_LADDER = registerBlockItem("aeronos_ladder", ModBlocks.AERONOS_LADDER);

    // Mars blocks
    public static final Supplier<Item> MARS_SAND = registerBlockItem("mars_sand", ModBlocks.MARS_SAND);
    public static final Supplier<Item> MARS_STONE = registerBlockItem("mars_stone", ModBlocks.MARS_STONE);
    public static final Supplier<Item> MARS_STONE_STAIRS = registerBlockItem("mars_stone_stairs", ModBlocks.MARS_STONE_STAIRS);
    public static final Supplier<Item> MARS_STONE_SLAB = registerBlockItem("mars_stone_slab", ModBlocks.MARS_STONE_SLAB);
    public static final Supplier<Item> MARS_COBBLESTONE = registerBlockItem("mars_cobblestone", ModBlocks.MARS_COBBLESTONE);
    public static final Supplier<Item> MARS_COBBLESTONE_STAIRS = registerBlockItem("mars_cobblestone_stairs", ModBlocks.MARS_COBBLESTONE_STAIRS);
    public static final Supplier<Item> MARS_COBBLESTONE_SLAB = registerBlockItem("mars_cobblestone_slab", ModBlocks.MARS_COBBLESTONE_SLAB);
    public static final Supplier<Item> MARS_STONE_BRICKS = registerBlockItem("mars_stone_bricks", ModBlocks.MARS_STONE_BRICKS);
    public static final Supplier<Item> CRACKED_MARS_STONE_BRICKS = registerBlockItem("cracked_mars_stone_bricks", ModBlocks.CRACKED_MARS_STONE_BRICKS);
    public static final Supplier<Item> MARS_STONE_BRICK_SLAB = registerBlockItem("mars_stone_brick_slab", ModBlocks.MARS_STONE_BRICK_SLAB);
    public static final Supplier<Item> MARS_STONE_BRICK_STAIRS = registerBlockItem("mars_stone_brick_stairs", ModBlocks.MARS_STONE_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_MARS_STONE_BRICKS = registerBlockItem("chiseled_mars_stone_bricks", ModBlocks.CHISELED_MARS_STONE_BRICKS);
    public static final Supplier<Item> CHISELED_MARS_STONE_STAIRS = registerBlockItem("chiseled_mars_stone_stairs", ModBlocks.CHISELED_MARS_STONE_STAIRS);
    public static final Supplier<Item> CHISELED_MARS_STONE_SLAB = registerBlockItem("chiseled_mars_stone_slab", ModBlocks.CHISELED_MARS_STONE_SLAB);
    public static final Supplier<Item> POLISHED_MARS_STONE = registerBlockItem("polished_mars_stone", ModBlocks.POLISHED_MARS_STONE);
    public static final Supplier<Item> POLISHED_MARS_STONE_STAIRS = registerBlockItem("polished_mars_stone_stairs", ModBlocks.POLISHED_MARS_STONE_STAIRS);
    public static final Supplier<Item> POLISHED_MARS_STONE_SLAB = registerBlockItem("polished_mars_stone_slab", ModBlocks.POLISHED_MARS_STONE_SLAB);
    public static final Supplier<Item> MARS_PILLAR = registerBlockItem("mars_pillar", ModBlocks.MARS_PILLAR);
    public static final Supplier<Item> MARS_STONE_BRICK_WALL = registerBlockItem("mars_stone_brick_wall", ModBlocks.MARS_STONE_BRICK_WALL);

    public static final Supplier<Item> CONGLOMERATE = registerBlockItem("conglomerate", ModBlocks.CONGLOMERATE);
    public static final Supplier<Item> POLISHED_CONGLOMERATE = registerBlockItem("polished_conglomerate", ModBlocks.POLISHED_CONGLOMERATE);

    public static final Supplier<Item> DEEPSLATE_ICE_SHARD_ORE = registerBlockItem("deepslate_ice_shard_ore", ModBlocks.DEEPSLATE_ICE_SHARD_ORE);
    public static final Supplier<Item> MARS_IRON_ORE = registerBlockItem("mars_iron_ore", ModBlocks.MARS_IRON_ORE);
    public static final Supplier<Item> MARS_DIAMOND_ORE = registerBlockItem("mars_diamond_ore", ModBlocks.MARS_DIAMOND_ORE);
    public static final Supplier<Item> MARS_OSTRUM_ORE = registerBlockItem("mars_ostrum_ore", ModBlocks.MARS_OSTRUM_ORE);
    public static final Supplier<Item> DEEPSLATE_OSTRUM_ORE = registerBlockItem("deepslate_ostrum_ore", ModBlocks.DEEPSLATE_OSTRUM_ORE);
    public static final Supplier<Item> MARS_ICE_SHARD_ORE = registerBlockItem("mars_ice_shard_ore", ModBlocks.MARS_ICE_SHARD_ORE);

    // Venus blocks
    public static final Supplier<Item> VENUS_SANDSTONE = registerBlockItem("venus_sandstone", ModBlocks.VENUS_SANDSTONE);
    public static final Supplier<Item> VENUS_SANDSTONE_BRICKS = registerBlockItem("venus_sandstone_bricks", ModBlocks.VENUS_SANDSTONE_BRICKS);
    public static final Supplier<Item> CRACKED_VENUS_SANDSTONE_BRICKS = registerBlockItem("cracked_venus_sandstone_bricks", ModBlocks.CRACKED_VENUS_SANDSTONE_BRICKS);
    public static final Supplier<Item> VENUS_SANDSTONE_BRICK_SLAB = registerBlockItem("venus_sandstone_brick_slab", ModBlocks.VENUS_SANDSTONE_BRICK_SLAB);
    public static final Supplier<Item> VENUS_SANDSTONE_BRICK_STAIRS = registerBlockItem("venus_sandstone_brick_stairs", ModBlocks.VENUS_SANDSTONE_BRICK_STAIRS);

    public static final Supplier<Item> VENUS_SAND = registerBlockItem("venus_sand", ModBlocks.VENUS_SAND);
    public static final Supplier<Item> VENUS_STONE = registerBlockItem("venus_stone", ModBlocks.VENUS_STONE);
    public static final Supplier<Item> VENUS_STONE_STAIRS = registerBlockItem("venus_stone_stairs", ModBlocks.VENUS_STONE_STAIRS);
    public static final Supplier<Item> VENUS_STONE_SLAB = registerBlockItem("venus_stone_slab", ModBlocks.VENUS_STONE_SLAB);
    public static final Supplier<Item> VENUS_COBBLESTONE = registerBlockItem("venus_cobblestone", ModBlocks.VENUS_COBBLESTONE);
    public static final Supplier<Item> VENUS_COBBLESTONE_STAIRS = registerBlockItem("venus_cobblestone_stairs", ModBlocks.VENUS_COBBLESTONE_STAIRS);
    public static final Supplier<Item> VENUS_COBBLESTONE_SLAB = registerBlockItem("venus_cobblestone_slab", ModBlocks.VENUS_COBBLESTONE_SLAB);
    public static final Supplier<Item> VENUS_STONE_BRICKS = registerBlockItem("venus_stone_bricks", ModBlocks.VENUS_STONE_BRICKS);
    public static final Supplier<Item> CRACKED_VENUS_STONE_BRICKS = registerBlockItem("cracked_venus_stone_bricks", ModBlocks.CRACKED_VENUS_STONE_BRICKS);
    public static final Supplier<Item> VENUS_STONE_BRICK_SLAB = registerBlockItem("venus_stone_brick_slab", ModBlocks.VENUS_STONE_BRICK_SLAB);
    public static final Supplier<Item> VENUS_STONE_BRICK_STAIRS = registerBlockItem("venus_stone_brick_stairs", ModBlocks.VENUS_STONE_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_VENUS_STONE_BRICKS = registerBlockItem("chiseled_venus_stone_bricks", ModBlocks.CHISELED_VENUS_STONE_BRICKS);
    public static final Supplier<Item> CHISELED_VENUS_STONE_STAIRS = registerBlockItem("chiseled_venus_stone_stairs", ModBlocks.CHISELED_VENUS_STONE_STAIRS);
    public static final Supplier<Item> CHISELED_VENUS_STONE_SLAB = registerBlockItem("chiseled_venus_stone_slab", ModBlocks.CHISELED_VENUS_STONE_SLAB);
    public static final Supplier<Item> POLISHED_VENUS_STONE = registerBlockItem("polished_venus_stone", ModBlocks.POLISHED_VENUS_STONE);
    public static final Supplier<Item> POLISHED_VENUS_STONE_STAIRS = registerBlockItem("polished_venus_stone_stairs", ModBlocks.POLISHED_VENUS_STONE_STAIRS);
    public static final Supplier<Item> POLISHED_VENUS_STONE_SLAB = registerBlockItem("polished_venus_stone_slab", ModBlocks.POLISHED_VENUS_STONE_SLAB);
    public static final Supplier<Item> VENUS_PILLAR = registerBlockItem("venus_pillar", ModBlocks.VENUS_PILLAR);
    public static final Supplier<Item> VENUS_STONE_BRICK_WALL = registerBlockItem("venus_stone_brick_wall", ModBlocks.VENUS_STONE_BRICK_WALL);

    public static final Supplier<Item> VENUS_COAL_ORE = registerBlockItem("venus_coal_ore", ModBlocks.VENUS_COAL_ORE);
    public static final Supplier<Item> VENUS_GOLD_ORE = registerBlockItem("venus_gold_ore", ModBlocks.VENUS_GOLD_ORE);
    public static final Supplier<Item> VENUS_DIAMOND_ORE = registerBlockItem("venus_diamond_ore", ModBlocks.VENUS_DIAMOND_ORE);
    public static final Supplier<Item> VENUS_CALORITE_ORE = registerBlockItem("venus_calorite_ore", ModBlocks.VENUS_CALORITE_ORE);
    public static final Supplier<Item> DEEPSLATE_CALORITE_ORE = registerBlockItem("deepslate_calorite_ore", ModBlocks.DEEPSLATE_CALORITE_ORE);

    public static final Supplier<Item> INFERNAL_SPIRE_BLOCK = registerBlockItem("infernal_spire_block", ModBlocks.INFERNAL_SPIRE_BLOCK);

    // Mercury blocks
    public static final Supplier<Item> MERCURY_STONE = registerBlockItem("mercury_stone", ModBlocks.MERCURY_STONE);
    public static final Supplier<Item> MERCURY_STONE_STAIRS = registerBlockItem("mercury_stone_stairs", ModBlocks.MERCURY_STONE_STAIRS);
    public static final Supplier<Item> MERCURY_STONE_SLAB = registerBlockItem("mercury_stone_slab", ModBlocks.MERCURY_STONE_SLAB);
    public static final Supplier<Item> MERCURY_COBBLESTONE = registerBlockItem("mercury_cobblestone", ModBlocks.MERCURY_COBBLESTONE);
    public static final Supplier<Item> MERCURY_COBBLESTONE_STAIRS = registerBlockItem("mercury_cobblestone_stairs", ModBlocks.MERCURY_COBBLESTONE_STAIRS);
    public static final Supplier<Item> MERCURY_COBBLESTONE_SLAB = registerBlockItem("mercury_cobblestone_slab", ModBlocks.MERCURY_COBBLESTONE_SLAB);
    public static final Supplier<Item> MERCURY_STONE_BRICKS = registerBlockItem("mercury_stone_bricks", ModBlocks.MERCURY_STONE_BRICKS);
    public static final Supplier<Item> CRACKED_MERCURY_STONE_BRICKS = registerBlockItem("cracked_mercury_stone_bricks", ModBlocks.CRACKED_MERCURY_STONE_BRICKS);
    public static final Supplier<Item> MERCURY_STONE_BRICK_SLAB = registerBlockItem("mercury_stone_brick_slab", ModBlocks.MERCURY_STONE_BRICK_SLAB);
    public static final Supplier<Item> MERCURY_STONE_BRICK_STAIRS = registerBlockItem("mercury_stone_brick_stairs", ModBlocks.MERCURY_STONE_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_MERCURY_STONE_BRICKS = registerBlockItem("chiseled_mercury_stone_bricks", ModBlocks.CHISELED_MERCURY_STONE_BRICKS);
    public static final Supplier<Item> CHISELED_MERCURY_STONE_STAIRS = registerBlockItem("chiseled_mercury_stone_stairs", ModBlocks.CHISELED_MERCURY_STONE_STAIRS);
    public static final Supplier<Item> CHISELED_MERCURY_STONE_SLAB = registerBlockItem("chiseled_mercury_stone_slab", ModBlocks.CHISELED_MERCURY_STONE_SLAB);
    public static final Supplier<Item> POLISHED_MERCURY_STONE = registerBlockItem("polished_mercury_stone", ModBlocks.POLISHED_MERCURY_STONE);
    public static final Supplier<Item> POLISHED_MERCURY_STONE_STAIRS = registerBlockItem("polished_mercury_stone_stairs", ModBlocks.POLISHED_MERCURY_STONE_STAIRS);
    public static final Supplier<Item> POLISHED_MERCURY_STONE_SLAB = registerBlockItem("polished_mercury_stone_slab", ModBlocks.POLISHED_MERCURY_STONE_SLAB);
    public static final Supplier<Item> MERCURY_PILLAR = registerBlockItem("mercury_pillar", ModBlocks.MERCURY_PILLAR);
    public static final Supplier<Item> MERCURY_STONE_BRICK_WALL = registerBlockItem("mercury_stone_brick_wall", ModBlocks.MERCURY_STONE_BRICK_WALL);

    public static final Supplier<Item> MERCURY_IRON_ORE = registerBlockItem("mercury_iron_ore", ModBlocks.MERCURY_IRON_ORE);

    // Glacio blocks
    public static final Supplier<Item> GLACIO_STONE = registerBlockItem("glacio_stone", ModBlocks.GLACIO_STONE);
    public static final Supplier<Item> GLACIO_STONE_STAIRS = registerBlockItem("glacio_stone_stairs", ModBlocks.GLACIO_STONE_STAIRS);
    public static final Supplier<Item> GLACIO_STONE_SLAB = registerBlockItem("glacio_stone_slab", ModBlocks.GLACIO_STONE_SLAB);
    public static final Supplier<Item> GLACIO_COBBLESTONE = registerBlockItem("glacio_cobblestone", ModBlocks.GLACIO_COBBLESTONE);
    public static final Supplier<Item> GLACIO_COBBLESTONE_STAIRS = registerBlockItem("glacio_cobblestone_stairs", ModBlocks.GLACIO_COBBLESTONE_STAIRS);
    public static final Supplier<Item> GLACIO_COBBLESTONE_SLAB = registerBlockItem("glacio_cobblestone_slab", ModBlocks.GLACIO_COBBLESTONE_SLAB);
    public static final Supplier<Item> GLACIO_STONE_BRICKS = registerBlockItem("glacio_stone_bricks", ModBlocks.GLACIO_STONE_BRICKS);
    public static final Supplier<Item> CRACKED_GLACIO_STONE_BRICKS = registerBlockItem("cracked_glacio_stone_bricks", ModBlocks.CRACKED_GLACIO_STONE_BRICKS);
    public static final Supplier<Item> GLACIO_STONE_BRICK_SLAB = registerBlockItem("glacio_stone_brick_slab", ModBlocks.GLACIO_STONE_BRICK_SLAB);
    public static final Supplier<Item> GLACIO_STONE_BRICK_STAIRS = registerBlockItem("glacio_stone_brick_stairs", ModBlocks.GLACIO_STONE_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_GLACIO_STONE_BRICKS = registerBlockItem("chiseled_glacio_stone_bricks", ModBlocks.CHISELED_GLACIO_STONE_BRICKS);
    public static final Supplier<Item> CHISELED_GLACIO_STONE_STAIRS = registerBlockItem("chiseled_glacio_stone_stairs", ModBlocks.CHISELED_GLACIO_STONE_STAIRS);
    public static final Supplier<Item> CHISELED_GLACIO_STONE_SLAB = registerBlockItem("chiseled_glacio_stone_slab", ModBlocks.CHISELED_GLACIO_STONE_SLAB);
    public static final Supplier<Item> POLISHED_GLACIO_STONE = registerBlockItem("polished_glacio_stone", ModBlocks.POLISHED_GLACIO_STONE);
    public static final Supplier<Item> POLISHED_GLACIO_STONE_STAIRS = registerBlockItem("polished_glacio_stone_stairs", ModBlocks.POLISHED_GLACIO_STONE_STAIRS);
    public static final Supplier<Item> POLISHED_GLACIO_STONE_SLAB = registerBlockItem("polished_glacio_stone_slab", ModBlocks.POLISHED_GLACIO_STONE_SLAB);
    public static final Supplier<Item> GLACIO_PILLAR = registerBlockItem("glacio_pillar", ModBlocks.GLACIO_PILLAR);
    public static final Supplier<Item> GLACIO_STONE_BRICK_WALL = registerBlockItem("glacio_stone_brick_wall", ModBlocks.GLACIO_STONE_BRICK_WALL);

    public static final Supplier<Item> GLACIO_ICE_SHARD_ORE = registerBlockItem("glacio_ice_shard_ore", ModBlocks.GLACIO_ICE_SHARD_ORE);
    public static final Supplier<Item> GLACIO_COAL_ORE = registerBlockItem("glacio_coal_ore", ModBlocks.GLACIO_COAL_ORE);
    public static final Supplier<Item> GLACIO_COPPER_ORE = registerBlockItem("glacio_copper_ore", ModBlocks.GLACIO_COPPER_ORE);
    public static final Supplier<Item> GLACIO_IRON_ORE = registerBlockItem("glacio_iron_ore", ModBlocks.GLACIO_IRON_ORE);
    public static final Supplier<Item> GLACIO_LAPIS_ORE = registerBlockItem("glacio_lapis_ore", ModBlocks.GLACIO_LAPIS_ORE);

    public static final Supplier<Item> PERMAFROST = registerBlockItem("permafrost", ModBlocks.PERMAFROST);
    public static final Supplier<Item> PERMAFROST_BRICKS = registerBlockItem("permafrost_bricks", ModBlocks.PERMAFROST_BRICKS);
    public static final Supplier<Item> PERMAFROST_BRICK_STAIRS = registerBlockItem("permafrost_brick_stairs", ModBlocks.PERMAFROST_BRICK_STAIRS);
    public static final Supplier<Item> PERMAFROST_BRICK_SLAB = registerBlockItem("permafrost_brick_slab", ModBlocks.PERMAFROST_BRICK_SLAB);
    public static final Supplier<Item> CRACKED_PERMAFROST_BRICKS = registerBlockItem("cracked_permafrost_bricks", ModBlocks.CRACKED_PERMAFROST_BRICKS);
    public static final Supplier<Item> PERMAFROST_TILES = registerBlockItem("permafrost_tiles", ModBlocks.PERMAFROST_TILES);
    public static final Supplier<Item> CHISELED_PERMAFROST_BRICKS = registerBlockItem("chiseled_permafrost_bricks", ModBlocks.CHISELED_PERMAFROST_BRICKS);
    public static final Supplier<Item> CHISELED_PERMAFROST_BRICK_STAIRS = registerBlockItem("chiseled_permafrost_brick_stairs", ModBlocks.CHISELED_PERMAFROST_BRICK_STAIRS);
    public static final Supplier<Item> CHISELED_PERMAFROST_BRICK_SLAB = registerBlockItem("chiseled_permafrost_brick_slab", ModBlocks.CHISELED_PERMAFROST_BRICK_SLAB);
    public static final Supplier<Item> POLISHED_PERMAFROST = registerBlockItem("polished_permafrost", ModBlocks.POLISHED_PERMAFROST);
    public static final Supplier<Item> POLISHED_PERMAFROST_STAIRS = registerBlockItem("polished_permafrost_stairs", ModBlocks.POLISHED_PERMAFROST_STAIRS);
    public static final Supplier<Item> POLISHED_PERMAFROST_SLAB = registerBlockItem("polished_permafrost_slab", ModBlocks.POLISHED_PERMAFROST_SLAB);
    public static final Supplier<Item> PERMAFROST_PILLAR = registerBlockItem("permafrost_pillar", ModBlocks.PERMAFROST_PILLAR);
    public static final Supplier<Item> PERMAFROST_BRICK_WALL = registerBlockItem("permafrost_brick_wall", ModBlocks.PERMAFROST_BRICK_WALL);

    public static final Supplier<Item> GLACIAN_LOG = registerBlockItem("glacian_log", ModBlocks.GLACIAN_LOG);
    public static final Supplier<Item> STRIPPED_GLACIAN_LOG = registerBlockItem("stripped_glacian_log", ModBlocks.STRIPPED_GLACIAN_LOG);
    public static final Supplier<Item> GLACIAN_LEAVES = registerBlockItem("glacian_leaves", ModBlocks.GLACIAN_LEAVES);
    public static final Supplier<Item> GLACIAN_PLANKS = registerBlockItem("glacian_planks", ModBlocks.GLACIAN_PLANKS);
    public static final Supplier<Item> GLACIAN_STAIRS = registerBlockItem("glacian_stairs", ModBlocks.GLACIAN_STAIRS);
    public static final Supplier<Item> GLACIAN_SLAB = registerBlockItem("glacian_slab", ModBlocks.GLACIAN_SLAB);
    public static final Supplier<Item> GLACIAN_DOOR = registerBlockItem("glacian_door", ModBlocks.GLACIAN_DOOR);
    public static final Supplier<Item> GLACIAN_TRAPDOOR = registerBlockItem("glacian_trapdoor", ModBlocks.GLACIAN_TRAPDOOR);
    public static final Supplier<Item> GLACIAN_FENCE = registerBlockItem("glacian_fence", ModBlocks.GLACIAN_FENCE);
    public static final Supplier<Item> GLACIAN_FENCE_GATE = registerBlockItem("glacian_fence_gate", ModBlocks.GLACIAN_FENCE_GATE);
    public static final Supplier<Item> GLACIAN_BUTTON = registerBlockItem("glacian_button", ModBlocks.GLACIAN_BUTTON);
    public static final Supplier<Item> GLACIAN_PRESSURE_PLATE = registerBlockItem("glacian_pressure_plate", ModBlocks.GLACIAN_PRESSURE_PLATE);
    public static final Supplier<Item> GLACIAN_SIGN = register("glacian_sign", () -> new SignItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(16), ModBlocks.GLACIAN_SIGN.get(), ModBlocks.GLACIAN_WALL_SIGN.get()));
    public static final Supplier<Item> GLACIAN_FUR = registerBlockItem("glacian_fur", ModBlocks.GLACIAN_FUR);

    // Spawn eggs
    // Moon
    public static final Supplier<SpawnEggItem> LUNARIAN_SPAWN_EGG = register("lunarian_spawn_egg", createSpawnEggItem(ModEntityTypes.LUNARIAN, -13382401, -11650781, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> CORRUPTED_LUNARIAN_SPAWN_EGG = register("corrupted_lunarian_spawn_egg", createSpawnEggItem(ModEntityTypes.CORRUPTED_LUNARIAN, -14804199, -16740159, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> STAR_CRAWLER_SPAWN_EGG = register("star_crawler_spawn_egg", createSpawnEggItem(ModEntityTypes.STAR_CRAWLER, -13421773, -16724788, new Item.Properties().tab(ITEM_GROUP)));

    // Mars
    public static final Supplier<SpawnEggItem> MARTIAN_RAPTOR_SPAWN_EGG = register("martian_raptor_spawn_egg", createSpawnEggItem(ModEntityTypes.MARTIAN_RAPTOR, 5349438, -13312, new Item.Properties().tab(ITEM_GROUP)));

    // Venus
    public static final Supplier<SpawnEggItem> PYGRO_SPAWN_EGG = register("pygro_spawn_egg", createSpawnEggItem(ModEntityTypes.PYGRO, -3381760, -6750208, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> ZOMBIFIED_PYGRO_SPAWN_EGG = register("zombified_pygro_spawn_egg", createSpawnEggItem(ModEntityTypes.ZOMBIFIED_PYGRO, 8473125, 6131271, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> PYGRO_BRUTE_SPAWN_EGG = register("pygro_brute_spawn_egg", createSpawnEggItem(ModEntityTypes.PYGRO_BRUTE, -3381760, -67208, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> MOGLER_SPAWN_EGG = register("mogler_spawn_egg", createSpawnEggItem(ModEntityTypes.MOGLER, -13312, -3407872, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> ZOMBIFIED_MOGLER_SPAWN_EGG = register("zombified_mogler_spawn_egg", createSpawnEggItem(ModEntityTypes.ZOMBIFIED_MOGLER, 12537409, 7988821, new Item.Properties().tab(ITEM_GROUP)));
    public static final Supplier<SpawnEggItem> SULFUR_CREEPER_SPAWN_EGG = register("sulfur_creeper_spawn_egg", createSpawnEggItem(ModEntityTypes.SULFUR_CREEPER, 13930288, 11303196, new Item.Properties().tab(ITEM_GROUP)));

    // Glacio
    public static final Supplier<SpawnEggItem> GLACIAN_RAM_SPAWN_EGG = register("glacian_ram_spawn_egg", createSpawnEggItem(ModEntityTypes.GLACIAN_RAM, 16770815, 4406589, new Item.Properties().tab(ITEM_GROUP)));

    // Other
    public static final Supplier<SpawnEggItem> LUNARIAN_WANDERING_TRADER_SPAWN_EGG = register("lunarian_wandering_trader_spawn_egg", createSpawnEggItem(ModEntityTypes.LUNARIAN_WANDERING_TRADER, 5993415, 8537301, new Item.Properties().tab(ITEM_GROUP)));


    public static Supplier<Item> registerFlag(String id, Supplier<Block> flag) {
        Supplier<Item> item = () -> new FlagBlockItem(flag.get(), new Item.Properties().tab(ITEM_GROUP));
        return register(id, item);
    }

    public static Supplier<Item> registerGlobe(String id, Supplier<Block> globe) {
        Supplier<Item> item = () -> new ModRenderedBlockItem(globe.get(), new Item.Properties().tab(ITEM_GROUP).stacksTo(1).rarity(Rarity.RARE));
        return register(id, item);
    }

    public static Supplier<Item> registerBlockItem(String id, Supplier<Block> block) {
        Supplier<Item> item = () -> new BlockItem(block.get(), new Item.Properties().tab(ITEM_GROUP));
        return register(id, item);
    }

    public static Supplier<Item> registerItem(String id) {
        return register(id, () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    }

    private static <T extends Item> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.ITEM, id, object);
    }

    public static void init() {
    }

    @ExpectPlatform
    public static Supplier<SpawnEggItem> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties settings) {
        throw new NotImplementedException();
    }
}