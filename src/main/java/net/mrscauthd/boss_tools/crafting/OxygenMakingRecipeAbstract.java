package net.mrscauthd.boss_tools.crafting;

import java.util.function.Predicate;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public abstract class OxygenMakingRecipeAbstract extends BossToolsRecipe implements Predicate<FluidStack> {

	private final FluidIngredient input;
	private final int oxygen;

	public OxygenMakingRecipeAbstract(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.input = FluidIngredient.deserialize(JSONUtils.getJsonObject(json, "input"));
		this.oxygen = JSONUtils.getInt(json, "oxygen");
	}

	public OxygenMakingRecipeAbstract(ResourceLocation id, PacketBuffer buffer) {
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
	public void write(PacketBuffer buffer) {
		super.write(buffer);

		this.getInput().write(buffer);
		buffer.writeInt(this.getOxygen());
	}

	@Override
	public boolean canFit(int var1, int var2) {
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
	public abstract IRecipeSerializer<?> getSerializer();

	@Override
	public abstract IRecipeType<?> getType();

}
