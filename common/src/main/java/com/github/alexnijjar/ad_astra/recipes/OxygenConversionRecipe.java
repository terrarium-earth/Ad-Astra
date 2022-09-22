package com.github.alexnijjar.ad_astra.recipes;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.google.gson.JsonObject;

import net.minecraft.fluid.Fluid;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OxygenConversionRecipe extends ConversionRecipe {

	public OxygenConversionRecipe(Identifier id, Fluid input, Fluid output, double conversionRatio) {
		super(id, input, output, conversionRatio);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.OXYGEN_CONVERSION_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.OXYGEN_CONVERSION_RECIPE;
	}

	public static class Serializer implements RecipeSerializer<OxygenConversionRecipe> {

		@Override
		public OxygenConversionRecipe read(Identifier id, JsonObject json) {
			Fluid input = Registry.FLUID.get(new Identifier(json.get("input").getAsJsonObject().get("tag").getAsString()));
			Fluid output = Registry.FLUID.get(new Identifier(json.get("output").getAsJsonObject().get("name").getAsString()));
			double conversionRatio = json.get("conversion_ratio").getAsDouble();
			return new OxygenConversionRecipe(id, input, output, conversionRatio);
		}

		@Override
		public OxygenConversionRecipe read(Identifier id, PacketByteBuf buf) {
			Fluid input = Registry.FLUID.get(buf.readIdentifier());
			Fluid output = Registry.FLUID.get(buf.readIdentifier());
			double conversionRatio = buf.readDouble();
			return new OxygenConversionRecipe(id, input, output, conversionRatio);
		}

		@Override
		public void write(PacketByteBuf buf, OxygenConversionRecipe recipe) {
			buf.writeIdentifier(Registry.FLUID.getId(recipe.getFluidInput()));
			buf.writeIdentifier(Registry.FLUID.getId(recipe.getFluidOutput()));
			buf.writeDouble(recipe.getConversionRatio());
		}
	}
}