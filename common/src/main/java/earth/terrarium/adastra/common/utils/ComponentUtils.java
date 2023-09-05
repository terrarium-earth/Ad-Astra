package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.api.upgrades.Upgradable;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class ComponentUtils {

    // No need to go beyond a trillion. We're not Mekanism.
    public static String getFormattedAmount(long number) {
        if (number >= 1_000_000_000_000L) {
            return number % 1_000_000_000_000L == 0 ? number / 1_000_000_000_000L + "T" : "%.2fT".formatted(number / 1_000_000_000_000f);
        } else if (number >= 1_000_000_000L) {
            return number % 1_000_000_000L == 0 ? number / 1_000_000_000L + "G" : "%.2fG".formatted(number / 1_000_000_000f);
        } else if (number >= 1_000_000L) {
            return number % 1_000_000L == 0 ? number / 1_000_000L + "M" : "%.2fM".formatted(number / 1_000_000f);
        } else if (number >= 1_000L) {
            return number % 1_000L == 0 ? number / 1_000L + "K" : "%.2fK".formatted(number / 1_000f);
        } else return String.valueOf(number);
    }

    public static Component getEnergyComponent(long energy, long capacity) {
        return Component.translatable("tooltip.adastra.energy",
            getFormattedAmount(energy),
            getFormattedAmount(capacity)).withStyle(ChatFormatting.GOLD);
    }

    public static Component getEnergyDifferenceComponent(long energy) {
        return Component.translatable("tooltip.adastra.energy_%s".formatted(energy < 0 ? "out" : "in"),
            getFormattedAmount(Math.abs(energy))).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxEnergyInComponent(long maxIn) {
        return Component.translatable("tooltip.adastra.max_energy_in",
            getFormattedAmount(maxIn)).withStyle(ChatFormatting.GREEN);
    }

    public static Component getMaxEnergyOutComponent(long maxOut) {
        return Component.translatable("tooltip.adastra.max_energy_out",
            getFormattedAmount(maxOut)).withStyle(ChatFormatting.GREEN);
    }

    public static Component getActiveInactiveComponent(boolean active) {
        return active ? ConstantComponents.ACTIVE.copy().withStyle(ChatFormatting.AQUA) : ConstantComponents.INACTIVE.copy().withStyle(ChatFormatting.AQUA);
    }

    public static Component getDistributionModeComponent(DistributionMode mode) {
        return switch (mode) {
            case SEQUENTIAL -> ConstantComponents.SEQUENTIAL.copy().withStyle(ChatFormatting.AQUA);
            case ROUND_ROBIN -> ConstantComponents.ROUND_ROBIN.copy().withStyle(ChatFormatting.AQUA);
        };
    }

    public static Component getFluidComponent(FluidHolder fluid, long capacity, Fluid fallback) {
        if (fluid.isEmpty()) {
            return Component.translatable("tooltip.adastra.fluid",
                getFormattedAmount(0),
                getFormattedAmount(capacity),
                ClientFluidHooks.getDisplayName(FluidHooks.newFluidHolder(fallback, capacity, null))
            ).withStyle(ChatFormatting.GOLD);
        }

        return getFluidComponent(fluid, capacity);
    }

    public static Component getFluidComponent(FluidHolder fluid, long capacity) {
        return Component.translatable("tooltip.adastra.fluid",
            getFormattedAmount(fluid.getFluidAmount()),
            getFormattedAmount(capacity),
            ClientFluidHooks.getDisplayName(fluid)
        ).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxFluidOutComponent(long maxOut) {
        return Component.translatable("tooltip.adastra.max_fluid_out",
            getFormattedAmount(maxOut)).withStyle(ChatFormatting.GREEN);
    }

    public static void addDescriptionComponent(List<Component> tooltipComponents, Component description) {
        tooltipComponents.add(Screen.hasShiftDown() ? description : ConstantComponents.SHIFT_DESCRIPTION);
    }

    public static void addUpgradesComponent(List<Component> tooltipComponents, ItemStack stack, Upgradable upgradeHolder) {
        var upgrades = upgradeHolder.getAllUpgrades(stack);
        if (upgrades.isEmpty()) return;
        tooltipComponents.add(ConstantComponents.UPGRADES);
        upgrades.forEach((upgrade, count) -> {
            Component upgradeText = Component.translatable("upgrade.%s.%s".formatted(upgrade.getNamespace(), upgrade.getPath()));
            Component upgradeEntry = Component.translatable("tooltip.adastra.upgrades.entry", count, upgradeText.getString()).withStyle(ChatFormatting.LIGHT_PURPLE);
            tooltipComponents.add(upgradeEntry);
        });
    }
}
