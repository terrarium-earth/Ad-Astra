package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class OxygenLoaderRecipeSerializer extends BossToolsRecipeSerializer<OxygenLoaderRecipe> {

	@Override
	public OxygenLoaderRecipe read(ResourceLocation id, JsonObject json) {
		return new OxygenLoaderRecipe(id, json);
	}

	@Override
	public OxygenLoaderRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new OxygenLoaderRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, OxygenLoaderRecipe recipe) {
		recipe.write(buffer);
	}

}
