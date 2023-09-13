package earth.terrarium.adastra.common.constants;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ConstantComponents {
    public static final Component ITEM_GROUP = Component.translatable("itemGroup.adastra.main");
    public static final Component DEATH_OXYGEN = Component.translatable("death.attack.oxygen");
    public static final Component DEATH_OXYGEN_PLAYER = Component.translatable("death.attack.oxygen.player");

    public static final Component TOGGLE_SUIT_FLIGHT_KEY = Component.translatable("key.adastra.toggle_suit_flight");
    public static final Component AD_ASTRA_CATEGORY = Component.translatable("key.categories.adastra");

    public static final Component SUIT_FLIGHT_ENABLED = Component.translatable("message.adastra.suit_flight_enabled").withStyle(ChatFormatting.GOLD);
    public static final Component SUIT_FLIGHT_DISABLED = Component.translatable("message.adastra.suit_flight_disabled").withStyle(ChatFormatting.GOLD);

    public static final Component OXYGEN_TRUE = Component.translatable("text.adastra.oxygen_true");
    public static final Component OXYGEN_FALSE = Component.translatable("text.adastra.oxygen_false");

    public static final Component SIDE_CONFIG = Component.translatable("tooltip.adastra.side_config").copy().withStyle(ChatFormatting.GRAY);
    public static final Component REDSTONE_CONTROL = Component.translatable("tooltip.adastra.redstone_control");

    public static final Component REDSTONE_ALWAYS_ON = Component.translatable("tooltip.adastra.redstone.always_on");
    public static final Component REDSTONE_NEVER_ON = Component.translatable("tooltip.adastra.redstone.never_on");
    public static final Component REDSTONE_ON_WHEN_POWERED = Component.translatable("tooltip.adastra.redstone.on_when_powered");
    public static final Component REDSTONE_ON_WHEN_NOT_POWERED = Component.translatable("tooltip.adastra.redstone.on_when_not_powered");

    public static final Component ACTIVE = Component.translatable("tooltip.adastra.active");
    public static final Component INACTIVE = Component.translatable("tooltip.adastra.inactive");

    public static final Component SEQUENTIAL = Component.translatable("tooltip.adastra.distribution_mode.sequential");
    public static final Component ROUND_ROBIN = Component.translatable("tooltip.adastra.distribution_mode.round_robin");

    public static final Component CAPACITOR_ENABLED = Component.translatable("tooltip.adastra.capacitor.enabled");
    public static final Component CAPACITOR_DISABLED = Component.translatable("tooltip.adastra.capacitor.disabled");

    public static final Component CHANGE_MODE_SEQUENTIAL = Component.translatable("tooltip.adastra.change_mode.sequential");
    public static final Component CHANGE_MODE_ROUND_ROBIN = Component.translatable("tooltip.adastra.change_mode.round_robin");

    public static final Component SHIFT_DESCRIPTION = Component.translatable("tooltip.adastra.shift_description").withStyle(ChatFormatting.GRAY);
    public static final Component TI_69_INFO = Component.translatable("info.adastra.ti_69").withStyle(ChatFormatting.GRAY);
    public static final Component ETRIONIC_CAPACITOR_INFO = Component.translatable("info.adastra.etrionic_capacitor").withStyle(ChatFormatting.GRAY);
    public static final Component ZIP_GUN_INFO = Component.translatable("info.adastra.zip_gun").withStyle(ChatFormatting.GRAY);
    public static final Component GAS_TANK_INFO = Component.translatable("info.adastra.gas_tank").withStyle(ChatFormatting.GRAY);

    public static final Component UPGRADES = Component.translatable("tooltip.adastra.upgrades").withStyle(ChatFormatting.LIGHT_PURPLE);
    public static final Component CLEAR_FLUID_TANK = Component.translatable("tooltip.adastra.clear_fluid_tank").withStyle(ChatFormatting.DARK_RED);

    public static final Component NEXT = Component.translatable("tooltip.adastra.next").copy().withStyle(ChatFormatting.GRAY);
    public static final Component PREVIOUS = Component.translatable("tooltip.adastra.previous").copy().withStyle(ChatFormatting.GRAY);
    public static final Component RESET_TO_DEFAULT = Component.translatable("tooltip.adastra.reset_to_default");

    public static final Component SIDE_CONFIG_SLOTS = Component.translatable("side_config.adastra.slots");
    public static final Component SIDE_CONFIG_ENERGY = Component.translatable("side_config.adastra.energy");
    public static final Component SIDE_CONFIG_FLUID = Component.translatable("side_config.adastra.fluid");
    public static final Component SIDE_CONFIG_INPUT_SLOTS = Component.translatable("side_config.adastra.input_slots");
    public static final Component SIDE_CONFIG_OUTPUT_SLOTS = Component.translatable("side_config.adastra.output_slots");
    public static final Component SIDE_CONFIG_EXTRACTION_SLOTS = Component.translatable("side_config.adastra.extraction_slots");
    public static final Component SIDE_CONFIG_INPUT_FLUID = Component.translatable("side_config.adastra.input_fluid");
    public static final Component SIDE_CONFIG_OUTPUT_FLUID = Component.translatable("side_config.adastra.output_fluid");

    public static final Component DETECTOR_INVERTED_TRUE = Component.translatable("text.adastra.detector.inverted_true");
    public static final Component DETECTOR_INVERTED_FALSE = Component.translatable("text.adastra.detector.inverted_false");
    public static final Component DETECTOR_OXYGEN_MODE = Component.translatable("text.adastra.detector.oxygen_mode");
    public static final Component DETECTOR_GRAVITY_MODE = Component.translatable("text.adastra.detector.gravity_mode");
    public static final Component DETECTOR_TEMPERATURE_MODE = Component.translatable("text.adastra.detector.temperature_mode");
}
