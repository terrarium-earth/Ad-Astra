package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FluidUtils {

    /**
     * Transfers and converts a fluid from the input tank to the output tank.
     */
    public static <T extends ConversionRecipe> boolean convertFluid(DoubleFluidTank tanks, List<T> recipes, int transferPerTick) {
        FluidHolder inputHolder = tanks.getInput().getFluids().get(0);
        FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(inputHolder.getFluid(), transferPerTick, null);
        for (T recipe : recipes) {
            double conversionRatio = recipe.getConversionRatio();
            Fluid recipeOutputFluid = recipe.getFluidOutput();
            if (recipe.matches(inputHolder.getFluid())) {
                // Check if the input tank is able to extract fluid.
                long extracted = tanks.getInput().extractFluid(fluidToTransfer, true).getFluidAmount();
                if (extracted > 0) {
                    // Then check if the output tank can insert fluid.
                    long outputAmount = (long) (extracted * conversionRatio);
                    FluidHolder convertedFluid = FluidHooks.newFluidHolder(recipeOutputFluid, outputAmount, null);
                    if (tanks.getOutput().insertFluid(convertedFluid, true) > 0) {
                        tanks.getInput().extractFluid(fluidToTransfer, false);
                        tanks.getOutput().insertFluid(convertedFluid, false);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean insertItemFluidToTank(FluidContainer tank, Inventory inventory, int inputSlot, int outputSlot, int container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getStack(inputSlot);
        Optional<PlatformFluidHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder itemHolder = itemFluidHandler.getFluidInTank(container);
            if (filter.test(itemHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(itemHolder.getFluid(), Math.max(FluidHooks.buckets(1), itemHolder.getFluidAmount()), null);
                FluidHolder extracted = itemFluidHandler.extractFluid(fluidToTransfer, true);
                if (!extracted.isEmpty()) {
                    if (tank.insertFluid(extracted, true) > 0) {
                        itemFluidHandler.extractFluid(fluidToTransfer, false);
                        tank.insertFluid(extracted, false);

                        // TODO: Fix item slot handling
                        inventory.setStack(outputSlot, stack.copy());
                        stack.decrement(1);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean extractTankFluidToItem(FluidContainer tank, Inventory inventory, int inputSlot, int outputSlot, int container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getStack(inputSlot);
        Optional<PlatformFluidHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder tankHolder = tank.getFluids().get(container);
            if (filter.test(tankHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(tankHolder.getFluid(), Math.max(FluidHooks.buckets(1), tankHolder.getFluidAmount()), null);
                FluidHolder extracted = itemFluidHandler.extractFluid(fluidToTransfer, true);
                if (!extracted.isEmpty()) {
                    if (itemFluidHandler.insertFluid(extracted, true) > 0) {
                        tank.extractFluid(fluidToTransfer, false);
                        itemFluidHandler.insertFluid(extracted, false);

                        // TODO: Fix item slot handling
                        inventory.setStack(outputSlot, stack.copy());
                        stack.decrement(1);
                        return true;
                    }
                }
            }
        }

        return false;
    }
}