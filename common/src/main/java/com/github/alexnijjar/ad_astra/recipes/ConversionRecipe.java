package com.github.alexnijjar.ad_astra.recipes;

import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.HolderSet;
import net.minecraft.util.Identifier;

public abstract class ConversionRecipe extends ModRecipe {

	private Fluid input;
	private final Fluid output;
	private final double conversionRatio;

	public ConversionRecipe(Identifier id, Fluid input, Fluid output, double conversionRatio) {
		super(id);
		this.input = input;
		this.output = output;
		this.conversionRatio = conversionRatio;
	}

	public ConversionRecipe(Identifier id, Ingredient input, Fluid output, double conversionRatio) {
		super(id);
		this.inputs.add(input);
		this.output = output;
		this.conversionRatio = conversionRatio;
	}

	public boolean matches(Fluid input) {
		return this.input.equals(input);
	}

	public Fluid getFluidInput() {
		return this.input;
	}

	public Fluid getFluidOutput() {
		return this.output;
	}

	public double getConversionRatio() {
		return this.conversionRatio;
	}
}