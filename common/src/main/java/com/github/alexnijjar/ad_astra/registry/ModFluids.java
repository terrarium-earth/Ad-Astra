package com.github.alexnijjar.ad_astra.registry;

import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.blocks.fluid.CryoFuelBlock;
import com.github.alexnijjar.ad_astra.blocks.fluid.CryoFuelFluid;
import com.github.alexnijjar.ad_astra.blocks.fluid.FuelFluid;
import com.github.alexnijjar.ad_astra.blocks.fluid.OilFluid;
import com.github.alexnijjar.ad_astra.blocks.fluid.OxygenFluid;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.registry.Registry;

public class ModFluids {

	public static FlowableFluid OIL_STILL;
	public static FlowableFluid FLOWING_OIL;
	public static Supplier<Block> OIL_BLOCK;

	public static FlowableFluid FUEL_STILL;
	public static FlowableFluid FLOWING_FUEL;
	public static Supplier<Block> FUEL_BLOCK;

	public static FlowableFluid CRYO_FUEL_STILL;
	public static FlowableFluid FLOWING_CRYO_FUEL;
	public static Block CRYO_FUEL_BLOCK;

	public static FlowableFluid OXYGEN_STILL;
	public static Supplier<Block> OXYGEN_BLOCK;

	public static void register() {

		// Oil
		OIL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("oil"), new OilFluid.Still());
		FLOWING_OIL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_oil"), new OilFluid.Flowing());

		OIL_BLOCK = ModBlocks.register("oil", new FluidBlock(OIL_STILL, AbstractBlock.Settings.copy(Blocks.WATER).strength(100.0f).dropsNothing()));

		// Fuel
		FUEL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("fuel"), new FuelFluid.Still());
		FLOWING_FUEL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_fuel"), new FuelFluid.Flowing());

		FUEL_BLOCK = ModBlocks.register("fuel", new FluidBlock(FUEL_STILL, AbstractBlock.Settings.copy(Blocks.WATER).strength(100.0f).dropsNothing()));

		// Cryo Fuel
		CRYO_FUEL_STILL = Registry.register(Registry.FLUID, new ModIdentifier("cryo_fuel"), new CryoFuelFluid.Still());
		FLOWING_CRYO_FUEL = Registry.register(Registry.FLUID, new ModIdentifier("flowing_cryo_fuel"), new CryoFuelFluid.Flowing());

		CRYO_FUEL_BLOCK = new CryoFuelBlock(CRYO_FUEL_STILL, AbstractBlock.Settings.copy(Blocks.WATER).strength(100.0f).dropsNothing());
		ModBlocks.register("cryo_fuel", CRYO_FUEL_BLOCK);

		// Oxygen
		OXYGEN_STILL = Registry.register(Registry.FLUID, new ModIdentifier("oxygen"), new OxygenFluid.Still());

		// Oxygen is not a placable fluid.
		OXYGEN_BLOCK = ModBlocks.register("oxygen", new FluidBlock(OXYGEN_STILL, AbstractBlock.Settings.copy(Blocks.WATER).strength(100.0f).dropsNothing()));
	}
}