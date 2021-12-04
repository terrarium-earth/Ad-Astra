package net.mrscauthd.boss_tools.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mrscauthd.boss_tools.ModInnet;

public class GeneratingRecipe extends BossToolsRecipe implements Predicate<ItemStack> {

	public static final int SLOT_FUEL = 0;

	private final Ingredient ingredient;
	private final int burnTime;

	public GeneratingRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
		JsonObject inputJson = JSONUtils.getJsonObject(json, "input");
		this.ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(inputJson, "ingredient"));
		this.burnTime = JSONUtils.getInt(json, "burnTime");
	}

	public GeneratingRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);

		this.ingredient = Ingredient.read(buffer);
		this.burnTime = buffer.readInt();
	}

	public GeneratingRecipe(ResourceLocation id, Ingredient ingredient, int burnTime) {
		super(id);
		this.ingredient = ingredient;
		this.burnTime = burnTime;
	}

	@Override
	public void write(PacketBuffer buffer) {
		super.write(buffer);

		this.getIngredient().write(buffer);
		buffer.writeInt(this.getBurnTime());
	}

	@Override
	public boolean canFit(int var1, int var2) {
		return true;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_GENERATING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.GENERATING;
	}

	public int getFuelSlot(IInventory inventory, World world) {
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
