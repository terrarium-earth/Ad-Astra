package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ComponentUtils {

    // No need to go beyond a trillion. We're not Mekanism.
    public static String getFormattedEnergy(long energy) {
        if (energy >= 1_000_000_000_000L) {
            return energy % 1_000_000_000_000L == 0 ? energy / 1_000_000_000_000L + "T" : "%.2fT".formatted(energy / 1_000_000_000_000f);
        } else if (energy >= 1_000_000_000L) {
            return energy % 1_000_000_000L == 0 ? energy / 1_000_000_000L + "G" : "%.2fG".formatted(energy / 1_000_000_000f);
        } else if (energy >= 1_000_000L) {
            return energy % 1_000_000L == 0 ? energy / 1_000_000L + "M" : "%.2fM".formatted(energy / 1_000_000f);
        } else if (energy >= 1_000L) {
            return energy % 1_000L == 0 ? energy / 1_000L + "K" : "%.2fK".formatted(energy / 1_000f);
        } else return String.valueOf(energy);
    }

    public static Component getEnergyComponent(long energy, long capacity) {
        return Component.translatable("tooltip.adastra.energy",
            getFormattedEnergy(energy),
            getFormattedEnergy(capacity)).withStyle(ChatFormatting.GOLD);
    }

    public static Component getEnergyDifferenceComponent(long energy) {
        return Component.translatable("tooltip.adastra.energy_%s".formatted(energy < 0 ? "out" : "in"),
            getFormattedEnergy(Math.abs(energy))).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxEnergyInComponent(long maxIn) {
        return Component.translatable("tooltip.adastra.max_energy_in",
            getFormattedEnergy(maxIn)).withStyle(ChatFormatting.GREEN);
    }

    public static Component getMaxEnergyOutComponent(long maxOut) {
        return Component.translatable("tooltip.adastra.max_energy_out",
            getFormattedEnergy(maxOut)).withStyle(ChatFormatting.GREEN);
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
}
