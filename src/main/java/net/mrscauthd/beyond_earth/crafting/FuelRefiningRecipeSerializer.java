package net.mrscauthd.beyond_earth.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class FuelRefiningRecipeSerializer extends BeyondEarthRecipeSerializer<FuelRefiningRecipe> {

	@Override
	public FuelRefiningRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new FuelRefiningRecipe(id, json);
	}

	@Override
	public FuelRefiningRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new FuelRefiningRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, FuelRefiningRecipe recipe) {
		recipe.write(buffer);
	}

}
