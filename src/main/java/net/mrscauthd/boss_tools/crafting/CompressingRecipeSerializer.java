package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class CompressingRecipeSerializer extends BossToolsRecipeSerializer<CompressingRecipe> {

	@Override
	public CompressingRecipe fromJson(ResourceLocation id, JsonObject json) {
		return new CompressingRecipe(id, json);
	}

	@Override
	public CompressingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
		return new CompressingRecipe(id, buffer);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, CompressingRecipe recipe) {
		recipe.write(buffer);
	}

}
