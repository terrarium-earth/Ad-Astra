package com.github.alexnijjar.ad_astra.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import net.minecraft.recipe.Ingredient;

public record IngredientHolder(Ingredient ingredient, int count) {
    public static final Codec<IngredientHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IngredientCodec.CODEC.fieldOf("ingredient").forGetter(IngredientHolder::ingredient),
            Codec.INT.fieldOf("count").orElse(1).forGetter(IngredientHolder::count)
    ).apply(instance, IngredientHolder::new));
}
