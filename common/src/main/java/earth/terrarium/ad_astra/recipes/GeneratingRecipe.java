package earth.terrarium.ad_astra.recipes;

import earth.terrarium.ad_astra.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;

import net.minecraft.item.ItemStack;
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
		return ModRecipes.GENERATING_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.GENERATING_RECIPE.get();
	}

}