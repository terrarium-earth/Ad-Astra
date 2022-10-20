package earth.terrarium.ad_astra.recipes;

import earth.terrarium.ad_astra.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.IngredientCodec;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class CompressingRecipe extends CookingRecipe {

	public CompressingRecipe(Identifier id, Ingredient input, ItemStack output, short cookTime) {
		super(id, input, output, cookTime);
	}

	public static Codec<CompressingRecipe> codec(Identifier id) {
		return RecordCodecBuilder.create(instance -> instance.group(
				RecordCodecBuilder.point(id),
				IngredientCodec.CODEC.fieldOf("input").forGetter(CompressingRecipe::getInputIngredient),
				ItemStackCodec.CODEC.fieldOf("output").forGetter(CompressingRecipe::getOutput),
				Codec.SHORT.fieldOf("time").orElse((short) 200).forGetter(CompressingRecipe::getCookTime)
			).apply(instance, CompressingRecipe::new));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.COMPRESSING_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.COMPRESSING_RECIPE.get();
	}
}