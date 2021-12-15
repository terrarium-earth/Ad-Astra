package net.mrscauthd.astrocraft.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class OxygenBubbleDistributorRecipeSerializer extends AstroCraftRecipeSerializer<OxygenBubbleDistributorRecipe> {

	@Override
	public OxygenBubbleDistributorRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new OxygenBubbleDistributorRecipe(id, json);
	}

	@Override
	public OxygenBubbleDistributorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new OxygenBubbleDistributorRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, OxygenBubbleDistributorRecipe recipe) {
		recipe.write(buffer);
	}

}
