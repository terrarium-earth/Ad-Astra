package net.mrscauthd.astrocraft.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.mrscauthd.astrocraft.ModInnet;

public class GeneratingRecipe extends AstroCraftRecipe implements Predicate<ItemStack> {

	public static final int SLOT_FUEL = 0;

	private final Ingredient ingredient;
	private final int burnTime;

	public GeneratingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = GsonHelper.getAsJsonObject(json, "input");
		this.ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(inputJson, "ingredient"));
		this.burnTime = GsonHelper.getAsInt(json, "burnTime");
	}

	public GeneratingRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.ingredient = Ingredient.fromNetwork(buffer);
		this.burnTime = buffer.readInt();
	}

	public GeneratingRecipe(ResourceLocation id, Ingredient ingredient, int burnTime) {
		super(id);
		this.ingredient = ingredient;
		this.burnTime = burnTime;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		this.getIngredient().toNetwork(buffer);
		buffer.writeInt(this.getBurnTime());
	}

	@Override
	public boolean canCraftInDimensions(int var1, int var2) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_GENERATING.get();
	}

	@Override
	public RecipeType<?> getType() {
		return AstroCraftRecipeTypes.GENERATING;
	}

	public int getFuelSlot(Container container, Level level) {
		return SLOT_FUEL;
	}

	@Override
	public boolean test(ItemStack ingredient) {
		return this.ingredient.test(ingredient);
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = super.getIngredients();
		list.add(this.getIngredient());
		return list;
	}

	public int getBurnTime() {
		return this.burnTime;
	}

}
