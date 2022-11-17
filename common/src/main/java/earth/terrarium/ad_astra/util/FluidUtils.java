package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidItemHandler;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

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
                    if (!convertedFluid.isEmpty() && !fluidToTransfer.isEmpty()) {
                        if (tanks.getOutput().insertFluid(convertedFluid, true) > 0) {
                            tanks.getInput().extractFluid(fluidToTransfer, false);
                            tanks.getOutput().insertFluid(convertedFluid, false);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean insertItemFluidToTank(FluidContainer tank, Container inventory, int inputSlot, int outputSlot, int container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getItem(inputSlot).copy();
        int originalCount = stack.getCount();
        stack.setCount(1);
        Optional<PlatformFluidItemHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidItemHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder itemHolder = itemFluidHandler.getFluidInTank(container);
            if (filter.test(itemHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(itemHolder.getFluid(), itemHolder.getFluidAmount(), null);
                if (fluidToTransfer.isEmpty()) return false;
                ItemStackHolder item = new ItemStackHolder(inventory.getItem(inputSlot).copy());
                FluidHolder extracted = itemFluidHandler.extractFluid(item, fluidToTransfer, true);
                if (extracted.isEmpty()) return false;
                long l = tank.insertFluid(extracted, true);
                extracted.setAmount(l);
                if (tank.insertFluid(extracted, true) == itemFluidHandler.extractFluid(item, fluidToTransfer, true).getFluidAmount()) {
                    fluidToTransfer.setAmount(l);
                    itemFluidHandler.extractFluid(item, fluidToTransfer, false);
                    tank.insertFluid(fluidToTransfer, false);

                    if (item.isDirty()) {
                        ItemStack copy = item.getStack().copy();
                        ItemStack stack1 = inventory.getItem(outputSlot);
                        if ((stack1.isEmpty() && (!copy.hasTag() || !ItemStack.tagMatches(copy, inventory.getItem(inputSlot)))) || ItemStack.tagMatches(copy, stack1) && ItemStack.isSameIgnoreDurability(copy, stack1) && copy.getCount() + stack1.getCount() <= copy.getMaxStackSize()) {
                            copy.setCount(copy.getCount() + stack1.getCount());
                            inventory.setItem(outputSlot, copy);
                            inventory.getItem(inputSlot).setCount(originalCount - 1);
                        } else {
                            itemFluidHandler.insertFluid(item, fluidToTransfer, false);
                            tank.extractFluid(fluidToTransfer, false);
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static boolean extractTankFluidToItem(FluidContainer tank, Container inventory, int inputSlot, int outputSlot, int container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getItem(inputSlot).copy();
        int originalCount = stack.getCount();
        stack.setCount(1);
        Optional<PlatformFluidItemHandler> possibleItemFluidContainer = FluidHooks.safeGetItemFluidManager(stack);
        if (possibleItemFluidContainer.isPresent()) {
            PlatformFluidItemHandler itemFluidHandler = possibleItemFluidContainer.get();
            FluidHolder tankHolder = tank.getFluids().get(container);
            if (filter.test(tankHolder.getFluid())) {

                FluidHolder fluidToTransfer = FluidHooks.newFluidHolder(tankHolder.getFluid(), tankHolder.getFluidAmount(), null);
                if (fluidToTransfer.isEmpty()) return false;
                ItemStackHolder item = new ItemStackHolder(inventory.getItem(inputSlot).copy());
                FluidHolder extracted = tank.extractFluid(fluidToTransfer, true);
                if (extracted.isEmpty()) return false;
                long l = itemFluidHandler.insertFluid(item, extracted, true);
                extracted.setAmount(l);
                if (l > 0 && tank.extractFluid(extracted, true).getFluidAmount() == itemFluidHandler.insertFluid(item, fluidToTransfer, true)) {
                    fluidToTransfer.setAmount(l);
                    tank.extractFluid(fluidToTransfer, false);
                    itemFluidHandler.insertFluid(item, fluidToTransfer, false);

                    if (item.isDirty()) {
                        ItemStack copy = item.getStack().copy();
                        ItemStack stack1 = inventory.getItem(outputSlot);
                        if ((stack1.isEmpty() && (!copy.hasTag() || !ItemStack.tagMatches(copy, inventory.getItem(inputSlot)))) || ItemStack.tagMatches(copy, stack1) && ItemStack.isSameIgnoreDurability(copy, stack1) && copy.getCount() + stack1.getCount() <= copy.getMaxStackSize()) {
                            copy.setCount(copy.getCount() + stack1.getCount());
                            inventory.setItem(outputSlot, copy);
                            inventory.getItem(inputSlot).setCount(originalCount - 1);
                        } else {
                            itemFluidHandler.extractFluid(item, fluidToTransfer, false);
                            tank.insertFluid(fluidToTransfer, false);
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }
}