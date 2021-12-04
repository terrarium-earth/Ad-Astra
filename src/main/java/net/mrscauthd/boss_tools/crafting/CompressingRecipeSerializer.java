package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class CompressingRecipeSerializer extends BossToolsRecipeSerializer<CompressingRecipe> {

	@Override
	public CompressingRecipe read(ResourceLocation id, JsonObject json) {
		return new CompressingRecipe(id, json);
	}

	@Override
	public CompressingRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new CompressingRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, CompressingRecipe recipe) {
		recipe.write(buffer);
	}

}
