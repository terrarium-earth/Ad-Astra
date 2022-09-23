package com.github.alexnijjar.ad_astra.recipes;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.google.gson.JsonObject;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class GeneratingRecipe extends CookingRecipe {

	public GeneratingRecipe(Identifier id, Ingredient input, short cookTime) {
		super(id, input, ItemStack.EMPTY, cookTime);
	}

	public static Codec<GeneratingRecipe> codec(Identifier id) {
		return RecordCodecBuilder.create(instance -> instance.group(
				RecordCodecBuilder.point(id),
				IngredientCodec.CODEC.fieldOf("input").forGetter(GeneratingRecipe::getInputIngredient),
				Codec.SHORT.fieldOf("time").orElse((short) 200).forGetter(GeneratingRecipe::getCookTime)
		).apply(instance, GeneratingRecipe::new));
	}

	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.GENERATING_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.GENERATING_RECIPE;
	}

}