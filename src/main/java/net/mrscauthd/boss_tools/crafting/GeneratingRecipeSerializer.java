package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class GeneratingRecipeSerializer extends BossToolsRecipeSerializer<GeneratingRecipe> {

	@Override
	public GeneratingRecipe read(ResourceLocation id, JsonObject json) {
		return new GeneratingRecipe(id, json);
	}

	@Override
	public GeneratingRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new GeneratingRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, GeneratingRecipe recipe) {
		recipe.write(buffer);
	}

}
