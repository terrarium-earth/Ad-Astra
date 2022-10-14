package com.github.alexnijjar.ad_astra.util;

import com.github.alexnijjar.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import com.github.alexnijjar.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FluidUtils {

    /**
     * Transfers and converts a fluid from the input tank to the output tank.
     */
    public static <T extends ConversionRecipe> boolean convertFluid(FluidMachineBlockEntity inventory, List<T> recipes, int transferPerTick) {
        if (recipes == null) {
            return false;
        }
        FluidHolder inputTankFluid = inventory.getInputTank();
        for (T recipe : recipes) {
            double conversionRatio = recipe.getConversionRatio();
            Fluid recipeOutputFluid = recipe.getFluidOutput();
            if (recipe.matches(inputTankFluid.getFluid())) {
                FluidHolder inputFluid = FluidHooks.newFluidHolder(inputTankFluid.getFluid(), transferPerTick, null);
                FluidHolder fluidHolder = inventory.getFluidContainer().extractFluid(inputFluid, false);
                if (!fluidHolder.isEmpty()) {
                    long inputAmount = fluidHolder.getFluidAmount();
                    long outputAmount = (long) (inputAmount * conversionRatio);
                    FluidHolder outputFluid = FluidHooks.newFluidHolder(recipeOutputFluid, outputAmount, null);
                    inventory.getFluidContainer().insertFluid(outputFluid, false);
                }
            }
        }
        return true;
    }

    // TODO: convert to different slot
    public static boolean insertFluidFromItem(ItemStack stack, int tank, BlockEntity blockEntity, Predicate<FluidHolder> filter) {
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            Optional<PlatformFluidHandler> blockHandler = FluidHooks.safeGetBlockFluidManager(blockEntity, null);
            if (blockHandler.isPresent()) {
                PlatformFluidHandler blockFluidHandler = blockHandler.get();
                if (filter.test(fluidHolder)) {
                    return blockFluidHandler.insertFluid(fluidHolder, false) > 0;
                }
            }
        }
        return false;
    }

    public static boolean extractFluidFromItem(ItemStack stack, int tank, BlockEntity blockEntity, Predicate<FluidHolder> filter) {
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            Optional<PlatformFluidHandler> blockHandler = FluidHooks.safeGetBlockFluidManager(blockEntity, null);
            if (blockHandler.isPresent()) {
                PlatformFluidHandler blockFluidHandler = blockHandler.get();
                if (filter.test(fluidHolder)) {
                    blockFluidHandler.extractFluid(fluidHolder, false);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean insertFluidToContainerFromItem(ItemStack stack, int tank, FluidContainer container, Predicate<FluidHolder> filter) {
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            if (filter.test(fluidHolder)) {
                return container.insertFluid(fluidHolder, false) > 0;
            }
        }
        return false;
    }
}