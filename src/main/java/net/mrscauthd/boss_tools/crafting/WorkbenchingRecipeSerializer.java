package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class WorkbenchingRecipeSerializer extends BossToolsRecipeSerializer<WorkbenchingRecipe> {

	@Override
	public WorkbenchingRecipe read(ResourceLocation id, JsonObject json) {
		return new WorkbenchingRecipe(id, json);
	}

	@Override
	public WorkbenchingRecipe read(ResourceLocation id, PacketBuffer buffer) {
		return new WorkbenchingRecipe(id, buffer);
	}

	@Override
	public void write(PacketBuffer buffer, WorkbenchingRecipe recipe) {
		recipe.write(buffer);
	}

}
