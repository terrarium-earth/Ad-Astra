package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class OxygenBubbleDistributorRecipeSerializer extends BossToolsRecipeSerializer<OxygenBubbleDistributorRecipe> {

	@Override
	public OxygenBubbleDistributorRecipe read(ResourceLocation id, JsonObject json) {
		return new OxygenBubbleDistributorRecipe(id, json);
	}

	@Override
	public OxygenBubbleDistributorRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new OxygenBubbleDistributorRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, OxygenBubbleDistributorRecipe recipe) {
		recipe.write(buffer);
	}

}
