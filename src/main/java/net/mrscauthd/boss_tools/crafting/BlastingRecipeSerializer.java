package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class BlastingRecipeSerializer extends BossToolsRecipeSerializer<BlastingRecipe> {

	@Override
	public BlastingRecipe read(ResourceLocation id, JsonObject json) {
		return new BlastingRecipe(id, json);
	}

	@Override
	public BlastingRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new BlastingRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, BlastingRecipe recipe) {
		recipe.write(buffer);
	}

}
