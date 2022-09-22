package com.github.alexnijjar.ad_astra.recipes;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.google.gson.JsonObject;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NasaWorkbenchRecipe extends CookingRecipe {

	public NasaWorkbenchRecipe(Identifier id, List<Ingredient> input, List<Integer> stackCounts, ItemStack output) {
		super(id, input, stackCounts, output);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.NASA_WORKBENCH_SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.NASA_WORKBENCH_RECIPE;
	}

	public static class Serializer implements RecipeSerializer<NasaWorkbenchRecipe> {

		@Override
		public NasaWorkbenchRecipe read(Identifier id, JsonObject json) {

			List<Ingredient> ingredients = new LinkedList<>();
			List<Integer> stackCounts = new LinkedList<>();
			json.getAsJsonArray("ingredients").forEach(element -> {
				JsonObject jsonObject = element.getAsJsonObject();
				ingredients.add(Ingredient.fromJson(jsonObject.get("ingredient").getAsJsonObject()));
				if (jsonObject.has("count")) {
					stackCounts.add(jsonObject.get("count").getAsInt());
				} else {
					stackCounts.add(1);
				}
			});

			Item item = Registry.ITEM.get(new Identifier(json.get("output").getAsJsonObject().get("item").getAsString()));
			short stackSize = json.get("output").getAsJsonObject().get("count").getAsShort();
			ItemStack output = new ItemStack(item, stackSize);

			return new NasaWorkbenchRecipe(id, ingredients, stackCounts, output);
		}

		@Override
		public NasaWorkbenchRecipe read(Identifier id, PacketByteBuf buf) {

			List<Ingredient> ingredients = buf.readList(buf2 -> {
				return Ingredient.fromPacket(buf2);
			});
			List<Integer> stackCounts = buf.readList(buf2 -> {
				return buf2.readInt();
			});
			ItemStack output = buf.readItemStack();

			return new NasaWorkbenchRecipe(id, ingredients, stackCounts, output);
		}

		@Override
		public void write(PacketByteBuf buf, NasaWorkbenchRecipe recipe) {
			buf.writeCollection(recipe.getIngredients(), (buf2, ingredient) -> {
				ingredient.write(buf2);
			});
			buf.writeCollection(recipe.getStackCounts(), (buf2, count) -> {
				buf2.writeInt(count);
			});
			buf.writeItemStack(recipe.getOutput());
		}
	}
}