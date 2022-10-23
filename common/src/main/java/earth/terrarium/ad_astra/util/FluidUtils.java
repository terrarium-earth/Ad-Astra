package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.blocks.fluid.ModFluid;
import earth.terrarium.ad_astra.blocks.machines.entity.FluidMachineBlockEntity;
import earth.terrarium.ad_astra.recipes.ConversionRecipe;
import earth.terrarium.ad_astra.registry.ModFluids;
import earth.terrarium.botarium.api.fluid.FluidContainer;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.PlatformFluidHandler;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FluidUtils {

    /**
     * Transfers and converts a fluid from the input tank to the output tank.
     */
    public static <T extends ConversionRecipe> boolean convertFluid(FluidMachineBlockEntity machine, List<T> recipes, int transferPerTick) {
        if (recipes == null) {
            return false;
        }
        FluidHolder inputTankFluid = machine.getInputTank();
        for (T recipe : recipes) {
            double conversionRatio = recipe.getConversionRatio();
            Fluid recipeOutputFluid = recipe.getFluidOutput();
            if (recipe.matches(inputTankFluid.getFluid())) {
                FluidHolder inputFluid = FluidHooks.newFluidHolder(inputTankFluid.getFluid(), transferPerTick, null);
                FluidHolder fluidHolder = machine.getFluidContainer().extractFluid(inputFluid, false);
                if (!fluidHolder.isEmpty()) {
                    long inputAmount = fluidHolder.getFluidAmount();
                    long outputAmount = (long) (inputAmount * conversionRatio);
                    FluidHolder outputFluid = FluidHooks.newFluidHolder(recipeOutputFluid, outputAmount, null);
                    machine.getFluidContainer().insertFluid(outputFluid, false);
                }
            }
        }
        return true;
    }

    // TODO: convert to different slot
    public static boolean insertFluidToContainerFromItem(ItemStack stack, int tank, FluidContainer container, Predicate<Fluid> filter) {
        if (stack.isEmpty()) {
            return false;
        }
        Optional<PlatformFluidHandler> itemHandler = FluidHooks.safeGetItemFluidManager(stack);
        if (itemHandler.isPresent()) {
            PlatformFluidHandler itemFluidHandler = itemHandler.get();
            FluidHolder fluidHolder = itemFluidHandler.getFluidInTank(tank);
            if (filter.test(fluidHolder.getFluid())) {
                return container.insertFluid(FluidHooks.newFluidHolder(ModFluids.FUEL_STILL.get(), 1000, null), false) > 0;
            }
        }
        return false;
    }

    public static boolean extractFluidFromItem(ItemStack stack, int tank, BlockEntity blockEntity, Predicate<FluidHolder> filter) {
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
                    return true;
                }
            }
        }
        return false;
    }
}