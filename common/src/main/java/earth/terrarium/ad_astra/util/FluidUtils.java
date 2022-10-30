package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.api.item.ItemStackHolder;
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
    public static <T extends ConversionRecipe> boolean convertFluid(DoubleFluidTank tanks, List<T> recipes, long transferPerTick) {
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
        Optional<PlatformFluidItemHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidItemHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder itemHolder = itemFluidHandler.getFluidInTank(container);
            if (filter.test(itemHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(itemHolder.getFluid(), itemHolder.getFluidAmount(), null);
                ItemStackHolder item = new ItemStackHolder(stack);
                FluidHolder extracted = itemFluidHandler.extractFluid(item, fluidToTransfer, true);
                if (!extracted.isEmpty()) {
                    long l = tank.insertFluid(extracted, true);
                    if (l > 0) {
                        fluidToTransfer.setAmount(l);
                        itemFluidHandler.extractFluid(item, fluidToTransfer, false);
                        tank.insertFluid(fluidToTransfer, false);

                        if (item.isDirty()) {
                            ItemStack copy = item.getStack().copy();
                            ItemStack stack1 = inventory.getStack(outputSlot);
                            if (stack1.isEmpty() || ItemStack.areNbtEqual(copy, stack1) && ItemStack.areItemsEqual(copy, stack1) && copy.getCount() + stack1.getCount() <= copy.getMaxCount()) {
                                copy.setCount(copy.getCount() + stack1.getCount());
                                inventory.setStack(outputSlot, copy);
                                stack.decrement(1);
                            } else {
                                itemFluidHandler.insertFluid(item, fluidToTransfer, false);
                                tank.extractFluid(fluidToTransfer, false);
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean extractTankFluidToItem(FluidContainer tank, Inventory inventory, int inputSlot, int outputSlot, int container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getStack(inputSlot);
        Optional<PlatformFluidItemHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidItemHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder tankHolder = tank.getFluids().get(container);
            if (filter.test(tankHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(tankHolder.getFluid(), tankHolder.getFluidAmount(), null);
                ItemStackHolder item = new ItemStackHolder(stack);
                FluidHolder extracted = tank.extractFluid(fluidToTransfer, true);
                if (!extracted.isEmpty()) {
                    long l = itemFluidHandler.insertFluid(item, extracted, true);
                    if (l > 0) {
                        fluidToTransfer.setAmount(l);
                        tank.extractFluid(fluidToTransfer, false);
                        itemFluidHandler.insertFluid(item, fluidToTransfer, false);

                        if (item.isDirty()) {
                            ItemStack copy = item.getStack().copy();
                            ItemStack stack1 = inventory.getStack(outputSlot);
                            if (stack1.isEmpty() || ItemStack.areNbtEqual(copy, stack1) && ItemStack.areItemsEqual(copy, stack1) && copy.getCount() + stack1.getCount() <= copy.getMaxCount()) {
                                copy.setCount(copy.getCount() + stack1.getCount());
                                inventory.setStack(outputSlot, copy);
                                stack.decrement(1);
                            } else {
                                itemFluidHandler.extractFluid(item, fluidToTransfer, false);
                                tank.insertFluid(fluidToTransfer, false);
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }
}