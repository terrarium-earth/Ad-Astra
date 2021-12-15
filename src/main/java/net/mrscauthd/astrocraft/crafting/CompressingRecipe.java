package net.mrscauthd.astrocraft.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.astrocraft.ModInnet;

public class CompressingRecipe extends ItemStackToItemStackRecipe {

	public CompressingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public CompressingRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
	}

	public CompressingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		super(id, ingredient, output, cookTime);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_COMPRESSING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return AstroCraftRecipeTypes.COMPRESSING;
	}

}
