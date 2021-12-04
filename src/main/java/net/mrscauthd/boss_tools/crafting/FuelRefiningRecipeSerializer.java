package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class FuelRefiningRecipeSerializer extends BossToolsRecipeSerializer<FuelRefiningRecipe> {

	@Override
	public FuelRefiningRecipe read(ResourceLocation id, JsonObject json) {
		return new FuelRefiningRecipe(id, json);
	}

	@Override
	public FuelRefiningRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new FuelRefiningRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, FuelRefiningRecipe recipe) {
		recipe.write(buffer);
	}

}
