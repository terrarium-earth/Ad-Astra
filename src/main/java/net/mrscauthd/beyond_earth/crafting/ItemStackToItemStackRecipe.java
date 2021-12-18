package net.mrscauthd.beyond_earth.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class ItemStackToItemStackRecipe extends BeyondEarthRecipe implements Predicate<ItemStack> {
	private final Ingredient ingredient;
	private final ItemStack output;
	private final int cookTime;

	public ItemStackToItemStackRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = GsonHelper.getAsJsonObject(json, "input");
		this.ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(inputJson, "ingredient"));
		this.output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
		this.cookTime = GsonHelper.getAsInt(json, "cookTime");
	}

	public ItemStackToItemStackRecipe(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
		this.ingredient = Ingredient.fromNetwork(buffer);
		this.output = buffer.readItem();
		this.cookTime = buffer.readInt();
	}

	public ItemStackToItemStackRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, int cookTime) {
		super(id);
		this.ingredient = ingredient;
		this.output = output.copy();
		this.cookTime = cookTime;
	}

	public void write(FriendlyByteBuf buffer) {
		this.getIngredient().toNetwork(buffer);
		buffer.writeItem(this.getOutput());
		buffer.writeInt(this.getCookTime());
	}

	@Override
	public boolean test(ItemStack stack) {
		return this.ingredient.test(stack);
	}

	@Override
	public boolean canCraftInDimensions(int var1, int var2) {
		return true;
	}
	
	public ItemStack getOutput() {
		return this.output.copy();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> list = super.getIngredients();
		list.add(this.getIngredient());
		return list;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}

	public int getCookTime() {
		return this.cookTime;
	}

}
