package earth.terrarium.adastra.common.utils;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class FluidUtils {

    public static FluidHolder getTank(ItemStack stack) {
        return getTank(stack, 0);
    }

    public static FluidHolder getTank(ItemStack stack, int tank) {
        return FluidHooks.getItemFluidManager(stack).getFluidInTank(0);
    }

    public static boolean hasFluid(ItemStack stack) {
        return hasFluid(stack, 0);
    }

    public static boolean hasFluid(ItemStack stack, int tank) {
        return !getTank(stack, tank).isEmpty();
    }

    public static Fluid getFluid(ItemStack stack) {
        return getFluid(stack, 0);
    }

    public static Fluid getFluid(ItemStack stack, int tank) {
        return getTank(stack, tank).getFluid();
    }

    public static long getFluidAmount(ItemStack stack) {
        return getFluidAmount(stack, 0);
    }

    public static long getFluidAmount(ItemStack stack, int tank) {
        return getTank(stack, tank).getFluidAmount();
    }

    public static long insert(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).insertFluid(stack, fluid, false);
    }

    public static FluidHolder extract(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).extractFluid(stack, fluid, false);
    }
}
