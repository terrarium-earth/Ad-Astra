package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.container.DoubleFluidTank;
import earth.terrarium.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.block.entity.BlockEntity;
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
        if (recipes == null) {
            return false;
        }
        FluidHolder inputTankFluid = tanks.getFluids().get(0);
        for (T recipe : recipes) {
            double conversionRatio = recipe.getConversionRatio();
            Fluid recipeOutputFluid = recipe.getFluidOutput();
            if (recipe.matches(inputTankFluid.getFluid())) {
                FluidHolder inputFluid = FluidHooks.newFluidHolder(inputTankFluid.getFluid(), transferPerTick, null);
                FluidHolder fluidHolder = tanks.getInput().extractFluid(inputFluid, false);
                if (!fluidHolder.isEmpty()) {
                    long inputAmount = fluidHolder.getFluidAmount();
                    long outputAmount = (long) (inputAmount * conversionRatio);
                    FluidHolder outputFluid = FluidHooks.newFluidHolder(recipeOutputFluid, outputAmount, null);
                    if (!outputFluid.isEmpty()) {
                        return tanks.getOutput().insertFluid(outputFluid, false) > 0;
                    }
                }
            }
        }
        return false;
    }

    public static boolean insertFluidToContainerFromItem(Inventory inventory, int inputSlot, int outputSlot, int tank, FluidContainer container, Predicate<Fluid> filter) {
        ItemStack stack = inventory.getStack(inputSlot);
        if (stack.isEmpty()) {
            return false;
        }
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            if (filter.test(fluidHolder.getFluid())) {
                inventory.setStack(outputSlot, stack.copy());
                stack.decrement(1);
                return container.insertFluid(FluidHooks.newFluidHolder(fluidHolder.getFluid(), FluidHooks.buckets(1), null), false) > 0;
            }
        }
        return false;
    }

    public static boolean extractFluidFromItem(Inventory inventory, int inputSlot, int outputSlot, int tank, BlockEntity blockEntity, Predicate<FluidHolder> filter) {
        ItemStack stack = inventory.getStack(inputSlot);
        if (stack.isEmpty()) {
            return false;
        }
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            Optional<PlatformFluidHandler> blockHandler = FluidHooks.safeGetBlockFluidManager(blockEntity, null);
            if (blockHandler.isPresent()) {
                PlatformFluidHandler blockFluidHandler = blockHandler.get();
                if (filter.test(fluidHolder)) {
                    blockFluidHandler.extractFluid(fluidHolder, false);
                    ItemStack newStack = stack.copy();
                    Optional<PlatformFluidHandler> newItemHandler = FluidHooks.safeGetItemFluidManager(newStack);
                    if (newItemHandler.isPresent()) {
                        newItemHandler.get().insertFluid(FluidHooks.newFluidHolder(fluidHolder.getFluid(), 1000, null), false);
                        inventory.setStack(outputSlot, newStack);
                        stack.decrement(1);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}