package net.mrscauthd.beyond_earth.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

public abstract class OxygenMakingRecipeAbstract extends BeyondEarthRecipe implements Predicate<FluidStack> {

	private final FluidIngredient input;
	private final int oxygen;

	public OxygenMakingRecipeAbstract(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.input = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "input"));
		this.oxygen = GsonHelper.getAsInt(json, "oxygen");
	}

	public OxygenMakingRecipeAbstract(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.input = FluidIngredient.read(buffer);
		this.oxygen = buffer.readInt();

	}

	public OxygenMakingRecipeAbstract(ResourceLocation id, FluidIngredient ingredient, int oxygen) {
		super(id);

		this.input = ingredient;
		this.oxygen = oxygen;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		this.getInput().write(buffer);
		buffer.writeInt(this.getOxygen());
	}

	@Override
	public boolean canCraftInDimensions(int var1, int var2) {
		return true;
	}

	@Override
	public boolean test(FluidStack fluidStack) {
		return this.input.test(fluidStack);
	}

	public FluidIngredient getInput() {
		return this.input;
	}

	public int getOxygen() {
		return this.oxygen;
	}

	@Override
	public abstract RecipeSerializer<?> getSerializer();

	@Override
	public abstract RecipeType<?> getType();

}
