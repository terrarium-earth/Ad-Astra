package net.mrscauthd.beyond_earth.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.ModInit;

public class SpaceStationRecipe extends BeyondEarthRecipe {

	public static final ResourceLocation KEY;

	static {
		ResourceLocation id = ModInit.RECIPE_SERIALIZER_SPACE_STATION.getId();
		KEY = new ResourceLocation(id.getNamespace(), id.getPath() + "/space_station");
	}

	private final NonNullList<IngredientStack> ingredients;

	public SpaceStationRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id);
		this.ingredients = NonNullList.withSize(buffer.readInt(), IngredientStack.EMPTY);

		for (int i = 0; i < this.ingredients.size(); i++) {
			this.ingredients.set(i, new IngredientStack(buffer));
		}
	}

	public SpaceStationRecipe(ResourceLocation id, JsonObject json) {
		super(id);

		JsonArray asJsonArray = GsonHelper.getAsJsonArray(json, "ingredients");
		this.ingredients = NonNullList.withSize(asJsonArray.size(), IngredientStack.EMPTY);

		for (int i = 0; i < this.ingredients.size(); i++) {
			this.ingredients.set(i, new IngredientStack(asJsonArray.get(i)));
		}
	}

	public SpaceStationRecipe(ResourceLocation id, NonNullList<IngredientStack> ingredients) {
		super(id);
		this.ingredients = NonNullList.of(IngredientStack.EMPTY, ingredients.toArray(IngredientStack[]::new));
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeInt(this.ingredients.size());
		this.ingredients.forEach(e -> e.toNetwork(buffer));
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInit.RECIPE_SERIALIZER_SPACE_STATION.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.SPACE_STATION;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.of(Ingredient.EMPTY, this.ingredients.stream().map(IngredientStack::getIngredient).toArray(Ingredient[]::new));
	}

	public NonNullList<IngredientStack> getIngredientStacks() {
		return NonNullList.of(IngredientStack.EMPTY, this.ingredients.toArray(IngredientStack[]::new));
	}

}
