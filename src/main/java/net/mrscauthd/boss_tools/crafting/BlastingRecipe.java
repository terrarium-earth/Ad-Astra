package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.mrscauthd.boss_tools.ModInnet;

public class BlastingRecipe extends ItemStackToItemStackRecipe {

	public BlastingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public BlastingRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
	}

	public BlastingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		super(id, ingredient, output, cookTime);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_BLASTING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.BLASTING;
	}

}
