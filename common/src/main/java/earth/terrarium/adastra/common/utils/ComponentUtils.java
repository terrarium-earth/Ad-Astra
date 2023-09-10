package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.api.upgrades.Upgradable;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public class ComponentUtils {

    // No need to go beyond a trillion. We're not Mekanism.
    public static String getFormattedAmount(long number) {
        String formatted;
        if (number >= 1_000_000_000_000L) {
            formatted = "%.2fT".formatted(number / 1_000_000_000_000f);
        } else if (number >= 1_000_000_000L) {
            formatted = "%.2fG".formatted(number / 1_000_000_000f);
        } else if (number >= 1_000_000L) {
            formatted = "%.2fM".formatted(number / 1_000_000f);
        } else if (number >= 1_000L) {
            formatted = "%.2fK".formatted(number / 1_000f);
        } else return String.valueOf(number);
        return formatted.replaceAll("(\\.?0+)([KMGT]?)$", "$2");
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

    public static Component getEnergyUsePerTickComponent(long usePerTick) {
        return Component.translatable("tooltip.adastra.energy_use_per_tick",
            getFormattedAmount(Math.abs(usePerTick))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getEnergyGenerationPerTickComponent(long generationPerTick) {
        return Component.translatable("tooltip.adastra.energy_generation_per_tick",
            getFormattedAmount(Math.abs(generationPerTick))).withStyle(ChatFormatting.AQUA);
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
                getFormattedAmount(FluidHooks.toMillibuckets(capacity)),
                ClientFluidHooks.getDisplayName(FluidHooks.newFluidHolder(fallback, capacity, null))
            ).withStyle(ChatFormatting.AQUA);
        }

        return getFluidComponent(fluid, capacity);
    }

    public static Component getFluidComponent(FluidHolder fluid, long capacity) {
        return Component.translatable("tooltip.adastra.fluid",
            getFormattedAmount(FluidHooks.toMillibuckets(fluid.getFluidAmount())),
            getFormattedAmount(FluidHooks.toMillibuckets(capacity)),
            ClientFluidHooks.getDisplayName(fluid)
        ).withStyle(ChatFormatting.GOLD);
    }

    public static Component getFluidDifferenceComponent(long fluid) {
        return Component.translatable("tooltip.adastra.fluid_%s".formatted(fluid < 0 ? "out" : "in"),
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(fluid)))).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxFluidInComponent(long maxIn) {
        return Component.translatable("tooltip.adastra.max_fluid_in",
            getFormattedAmount(FluidHooks.toMillibuckets(maxIn))).withStyle(ChatFormatting.GREEN);
    }

    public static Component getMaxFluidOutComponent(long maxOut) {
        return Component.translatable("tooltip.adastra.max_fluid_out",
            getFormattedAmount(FluidHooks.toMillibuckets(maxOut))).withStyle(ChatFormatting.GREEN);
    }

    public static Component getFluidUsePerIterationComponent(long usePerTick) {
        return Component.translatable("tooltip.adastra.fluid_use_per_iteration",
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(usePerTick)))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getFluidGenerationPerIterationComponent(long gainPerTick) {
        return Component.translatable("tooltip.adastra.fluid_generation_per_iteration",
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(gainPerTick)))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getTicksPerIterationComponent(int time) {
        return Component.translatable("tooltip.adastra.ticks_per_iteration",
            getFormattedAmount(time)).withStyle(ChatFormatting.AQUA);
    }

    public static Component getDirectionComponent(Direction direction) {
        return Component.translatable("direction.adastra.%s".formatted(direction.getName()));
    }

    public static Component getRelativeDirectionComponent(Direction direction) {
        return Component.translatable("direction.adastra.relative.%s".formatted(direction.getName()));
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
