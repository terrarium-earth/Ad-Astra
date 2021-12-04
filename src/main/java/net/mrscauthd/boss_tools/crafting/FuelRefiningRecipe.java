package net.mrscauthd.boss_tools.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.mrscauthd.boss_tools.ModInnet;

public class FuelRefiningRecipe extends BossToolsRecipe implements Predicate<FluidStack>{
	private FluidIngredient input;
	private FluidIngredient output;

	public FuelRefiningRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.input = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "input"));
		this.output = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "output"));
	}

	public FuelRefiningRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);

		this.input = FluidIngredient.read(buffer);
		this.output = FluidIngredient.read(buffer);
	}

	public FuelRefiningRecipe(ResourceLocation id, FluidIngredient input, FluidIngredient output) {
		super(id);

		this.input = input;
		this.output = output;
	}
	
	@Override
	public void write(PacketBuffer buffer) {
		super.write(buffer);

		this.input.write(buffer);
		this.output.write(buffer);
	}

	@Override
	public boolean test(FluidStack stack) {
		return this.input.test(stack);
	}
	
	@Override
	public boolean canFit(int var1, int var2) {
		return false;
	}

	public FluidIngredient getInput() {
		return this.input;
	}

	public FluidIngredient getOutput() {
		return this.output;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_FUELREFINING.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.FUELREFINING;
	}

}
