package earth.terrarium.adastra.common.utils;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.ClientFluidHooks;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluid;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TooltipUtils {

    public static String getFormattedAmount(long number) {
        if (Screen.hasShiftDown()) {
            return DecimalFormat.getNumberInstance().format(number);
        }
        return DecimalFormat.getCompactNumberInstance(Locale.ROOT, NumberFormat.Style.SHORT).format(number);
    }

    public static Component getEnergyComponent(long energy, long capacity) {
        return Component.translatable("tooltip.ad_astra.energy",
            getFormattedAmount(energy),
            getFormattedAmount(capacity)).withStyle(ChatFormatting.GOLD);
    }

    public static Component getEnergyDifferenceComponent(long energy) {
        return Component.translatable("tooltip.ad_astra.energy_%s".formatted(energy < 0 ? "out" : "in"),
            getFormattedAmount(Math.abs(energy))).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxEnergyInComponent(long maxIn) {
        return Component.translatable("tooltip.ad_astra.max_energy_in",
            getFormattedAmount(maxIn)).withStyle(ChatFormatting.GREEN);
    }

    public static Component getMaxEnergyOutComponent(long maxOut) {
        return Component.translatable("tooltip.ad_astra.max_energy_out",
            getFormattedAmount(maxOut)).withStyle(ChatFormatting.GREEN);
    }

    public static Component getEnergyUsePerTickComponent(long usePerTick) {
        return Component.translatable("tooltip.ad_astra.energy_use_per_tick",
            getFormattedAmount(Math.abs(usePerTick))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getEnergyGenerationPerTickComponent(long generationPerTick) {
        return Component.translatable("tooltip.ad_astra.energy_generation_per_tick",
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
            return Component.translatable("tooltip.ad_astra.fluid",
                getFormattedAmount(0),
                getFormattedAmount(FluidHooks.toMillibuckets(capacity)),
                ClientFluidHooks.getDisplayName(FluidHooks.newFluidHolder(fallback, capacity, null))
            ).withStyle(ChatFormatting.GOLD);
        }

        return getFluidComponent(fluid, capacity);
    }

    public static Component getFluidComponent(FluidHolder fluid, long capacity) {
        return Component.translatable("tooltip.ad_astra.fluid",
            getFormattedAmount(FluidHooks.toMillibuckets(fluid.getFluidAmount())),
            getFormattedAmount(FluidHooks.toMillibuckets(capacity)),
            ClientFluidHooks.getDisplayName(fluid)
        ).withStyle(ChatFormatting.GOLD);
    }

    public static Component getFluidDifferenceComponent(long fluid) {
        return Component.translatable("tooltip.ad_astra.fluid_%s".formatted(fluid < 0 ? "out" : "in"),
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(fluid)))).withStyle(ChatFormatting.GOLD);
    }

    public static Component getMaxFluidInComponent(long maxIn) {
        return Component.translatable("tooltip.ad_astra.max_fluid_in",
            getFormattedAmount(FluidHooks.toMillibuckets(maxIn))).withStyle(ChatFormatting.GREEN);
    }

    public static Component getMaxFluidOutComponent(long maxOut) {
        return Component.translatable("tooltip.ad_astra.max_fluid_out",
            getFormattedAmount(FluidHooks.toMillibuckets(maxOut))).withStyle(ChatFormatting.GREEN);
    }

    public static Component getFluidUsePerIterationComponent(long usePerTick) {
        return Component.translatable("tooltip.ad_astra.fluid_use_per_iteration",
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(usePerTick)))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getFluidGenerationPerIterationComponent(long gainPerTick) {
        return Component.translatable("tooltip.ad_astra.fluid_generation_per_iteration",
            getFormattedAmount(FluidHooks.toMillibuckets(Math.abs(gainPerTick)))).withStyle(ChatFormatting.AQUA);
    }

    public static Component getTicksPerIterationComponent(int time) {
        return Component.translatable("tooltip.ad_astra.ticks_per_iteration",
            getFormattedAmount(time)).withStyle(ChatFormatting.AQUA);
    }

    public static Component getDirectionComponent(Direction direction) {
        return Component.translatable("direction.ad_astra.%s".formatted(direction.getName()));
    }

    public static Component getRelativeDirectionComponent(Direction direction) {
        return Component.translatable("direction.ad_astra.relative.%s".formatted(direction.getName()));
    }

    public static void addDescriptionComponent(List<Component> tooltipComponents, Component description) {
        if (!Screen.hasShiftDown()) {
            tooltipComponents.add(ConstantComponents.SHIFT_DESCRIPTION);
            return;
        }

        // Split the description into multiple lines if it's too long
        for (FormattedCharSequence text : Minecraft.getInstance().font.split(description, 200)) {
            StringBuilder builder = new StringBuilder();
            text.accept((i, style, codePoint) -> {
                builder.appendCodePoint(codePoint);
                return true;
            });
            tooltipComponents.add(Component.literal(builder.toString()).withStyle(description.getStyle()));
        }
    }

    public static Component getProgressComponent(int progress, int maxProgress) {
        return Component.translatable("tooltip.ad_astra.progress",
            progress, maxProgress).withStyle(ChatFormatting.GOLD);
    }

    public static Component getEtaComponent(int progress, int maxProgress, boolean reverse) {
        int eta = (maxProgress - progress) / 20;
        if (reverse) eta = progress / 20;
        return Component.translatable("tooltip.ad_astra.eta",
            eta).withStyle(ChatFormatting.GOLD);
    }
}
