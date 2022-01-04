package net.mrscauthd.beyond_earth.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;

public abstract class AlienTradingRecipeItemStackBase extends AlienTradingRecipe {

	private ItemStack costA;
	private ItemStack costB;

	public AlienTradingRecipeItemStackBase(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.costA = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "costA"), true);
		this.costB = json.has("costB") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "costB"), true) : ItemStack.EMPTY;
	}

	public AlienTradingRecipeItemStackBase(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.costA = buffer.readItem();
		this.costB = buffer.readItem();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeItem(this.costA);
		buffer.writeItem(this.costB);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand) {
		return Triple.of(this.getCostA(), this.getCostB(), this.getResult(trader, rand));
	}

	public abstract ItemStack getResult(Entity trader, Random rand);

	public ItemStack getCostA() {
		return this.costA.copy();
	}

	public ItemStack getCostB() {
		return this.costB.copy();
	}

}
