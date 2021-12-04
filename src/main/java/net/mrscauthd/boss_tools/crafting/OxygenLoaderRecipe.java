package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.ModInnet;

public class OxygenLoaderRecipe extends OxygenMakingRecipeAbstract {

	public OxygenLoaderRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public OxygenLoaderRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);
	}

	public OxygenLoaderRecipe(ResourceLocation id, FluidIngredient ingredient, int oxygen) {
		super(id, ingredient, oxygen);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_OXYGENLOADER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.OXYGENLOADER;
	}

}
