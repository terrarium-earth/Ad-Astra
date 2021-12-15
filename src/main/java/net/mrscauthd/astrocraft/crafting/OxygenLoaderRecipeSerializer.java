package net.mrscauthd.astrocraft.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OxygenLoaderRecipeSerializer extends AstroCraftRecipeSerializer<OxygenLoaderRecipe> {

	@Override
	public OxygenLoaderRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new OxygenLoaderRecipe(id, json);
	}

	@Override
	public OxygenLoaderRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new OxygenLoaderRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, OxygenLoaderRecipe recipe) {
		recipe.write(buffer);
	}

}
