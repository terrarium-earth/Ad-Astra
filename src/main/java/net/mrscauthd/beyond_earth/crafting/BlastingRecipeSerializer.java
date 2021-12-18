package net.mrscauthd.beyond_earth.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class BlastingRecipeSerializer extends BeyondEarthRecipeSerializer<BlastingRecipe> {

	@Override
	public BlastingRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new BlastingRecipe(id, json);
	}

	@Override
	public BlastingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new BlastingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, BlastingRecipe recipe) {
		recipe.write(buffer);
	}

}
