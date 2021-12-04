package net.mrscauthd.boss_tools.gauge;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemFuelBurnTime;

import ResourceLocation;

public class GaugeValueHelper {

	public static final ResourceLocation ENERGY_NAME = new ResourceLocation(BossToolsMod.ModId, "energy");
	public static final ResourceLocation OXYGEN_NAME = new ResourceLocation(BossToolsMod.ModId, "oxygen");
	public static final ResourceLocation FLUID_NAME = new ResourceLocation(BossToolsMod.ModId, "fluid");
	public static final ResourceLocation FUEL_NAME = new ResourceLocation(BossToolsMod.ModId, "fuel");
	public static final ResourceLocation BURNTIME_NAME = new ResourceLocation(BossToolsMod.ModId, "burntime");
	public static final ResourceLocation COOKTIME_NAME = new ResourceLocation(BossToolsMod.ModId, "cooktime");

	public static final int ENERGY_COLOR = 0xA0FF404B;
	public static final int OXYGEN_COLOR = 0xA000FFFF;
	public static final int FUEL_COLOR = 0xA099993D;
	public static final int BURNTIME_COLOR = 0xA0FF3F00;
	public static final int COOKTIME_COLOR = 0xA0FFFFFF;

	public static final String ENERGY_UNIT = "FE";
	public static final String OXYGEN_UNIT = "mB";
	public static final String FLUID_UNIT = "mB";

	public static String makeTranslationKey(ResourceLocation name) {
		return "gauge." + name.getNamespace() + "." + name.getPath();
	}

	public static IGaugeValue getFluid(int amount) {
		return getFluid(amount, 0);
	}

	public static IGaugeValue getFluid(int amount, int capacity) {
		return new GaugeValueSimple(FLUID_NAME, amount, capacity, null, FLUID_UNIT);
	}

	public static IGaugeValue getFluid(Fluid fluid, int amount) {
		return getFluid(new FluidStack(fluid, amount));
	}

	public static IGaugeValue getFluid(Fluid fluid, int amount, int capacity) {
		return getFluid(new FluidStack(fluid, amount), capacity);
	}

	public static IGaugeValue getFluid(FluidStack stack) {
		return getFluid(stack, 0);
	}

	public static IGaugeValue getFluid(FluidStack stack, int capacity) {
		return new GaugeValueFluidStack(stack, capacity);
	}

	public static IGaugeValue getFluid(IFluidTank tank) {
		return getFluid(tank.getFluid(), tank.getCapacity());
	}

	public static IGaugeValue getEnergy(int amount) {
		return getEnergy(amount, 0);
	}

	public static IGaugeValue getEnergy(int stored, int capacity) {
		return new GaugeValueSimple(ENERGY_NAME, stored, capacity, null, ENERGY_UNIT).color(ENERGY_COLOR);
	}

	public static IGaugeValue getEnergy(IEnergyStorage energyStorage) {
		return getEnergy(energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored());
	}

	public static IGaugeValue getEnergy(AbstractMachineTileEntity machineTileEntity) {
		return getEnergy(machineTileEntity.getPrimaryEnergyStorage());
	}

	public static IGaugeValue getOxygen(int amount) {
		return getOxygen(amount, 0);
	}

	public static IGaugeValue getOxygen(int amount, int capacity) {
		return new GaugeValueSimple(OXYGEN_NAME, amount, capacity, null, OXYGEN_UNIT).color(OXYGEN_COLOR);
	}

	public static IGaugeValue getOxygen(IOxygenStorage oxygenStorage) {
		return getOxygen(oxygenStorage.getOxygenStored(), oxygenStorage.getMaxOxygenStored());
	}

	public static IGaugeValue getBurnTime(int amount) {
		return getBurnTime(amount, 0);
	}

	public static IGaugeValue getBurnTime(int amount, int capacity) {
		return new GaugeValueSimple(BURNTIME_NAME, amount, capacity).color(BURNTIME_COLOR);
	}

	public static IGaugeValue getBurnTime(PowerSystemFuelBurnTime fuelPowerSystem) {
		return getBurnTime(fuelPowerSystem.getStored(), fuelPowerSystem.getCapacity());
	}

	public static IGaugeValue getCookTime(int timer, int maxTimer) {
		return new GaugeValueSimple(COOKTIME_NAME, maxTimer - timer, maxTimer).color(COOKTIME_COLOR).reverse(true);
	}
	
	public static IGaugeValue getFuel(int amount) {
		return getFuel(amount, 0);
	}
	
	public static IGaugeValue getFuel(int amount, int capacity) {
		return new GaugeValueSimple(FUEL_NAME, amount, capacity, null, FLUID_UNIT).color(FUEL_COLOR);
	}

}
