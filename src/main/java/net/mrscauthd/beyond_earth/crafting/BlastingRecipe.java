package net.mrscauthd.beyond_earth.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.ModInnet;

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
	public RecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_BLASTING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.BLASTING;
	}

}
