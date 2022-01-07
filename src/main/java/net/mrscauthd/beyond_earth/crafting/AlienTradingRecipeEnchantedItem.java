package net.mrscauthd.beyond_earth.crafting;

import java.util.Random;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.mrscauthd.beyond_earth.ModInit;

public class AlienTradingRecipeEnchantedItem extends AlienTradingRecipeItemStack {
	
	private int levelBase = 5;
	private int levelRange = 15;

	private int costBaseLevelMultiplier = 1;
	private int costRangeBase = 0;
	private int costRangeLevelMultiplier = 0;

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.levelBase = GsonHelper.getAsInt(json, "levelBase", this.levelBase);
		this.levelRange = GsonHelper.getAsInt(json, "levelRange", this.levelRange);

		this.costBaseLevelMultiplier = GsonHelper.getAsInt(json, "costBaseLevelMultiplier", this.costBaseLevelMultiplier);
		this.costRangeBase = GsonHelper.getAsInt(json, "costRangeBase", this.costRangeBase);
		this.costRangeLevelMultiplier = GsonHelper.getAsInt(json, "costRangeLevelMultiplier", this.costRangeLevelMultiplier);
	}

	public AlienTradingRecipeEnchantedItem(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.levelBase = buffer.readInt();
		this.levelRange = buffer.readInt();

		this.costBaseLevelMultiplier = buffer.readInt();
		this.costRangeBase = buffer.readInt();
		this.costRangeLevelMultiplier = buffer.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeInt(this.levelBase);
		buffer.writeInt(this.levelRange);

		buffer.writeInt(this.costBaseLevelMultiplier);
		buffer.writeInt(this.costRangeBase);
		buffer.writeInt(this.costRangeLevelMultiplier);
	}

	@Override
	public Triple<ItemStack, ItemStack, ItemStack> getTrade(Entity trader, Random rand) {
		int level = this.getLevelBase();
		int bound1 = this.getLevelRange();
		ItemStack costA = this.getCostA();
		ItemStack costB = this.getCostB();

		if (bound1 > 0) {
			level += rand.nextInt(bound1);
		}

		int cost = costA.getCount() + level * this.getCostBaseLevelMultiplier();
		int bound2 = this.getCostRangeBase() + level * this.getCostRangeLevelMultiplier();

		if (bound2 > 0) {
			cost += rand.nextInt(bound2);
		}

		costA.setCount(Math.min(cost, costA.getMaxStackSize()));
		ItemStack result = EnchantmentHelper.enchantItem(rand, this.getResult(trader, rand), level, false);
		return Triple.of(costA, costB, result);
	}

	public int getLevelBase() {
		return this.levelBase;
	}

	public int getLevelRange() {
		return this.levelRange;
	}

	public int getCostBaseLevelMultiplier() {
		return this.costBaseLevelMultiplier;
	}

	public int getCostRangeBase() {
		return this.costRangeBase;
	}

	public int getCostRangeLevelMultiplier() {
		return this.costRangeLevelMultiplier;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInit.RECIPE_SERIALIZER_ALIEN_TRADING_ENCHANTEDITEM.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.ALIEN_TRADING_ENCHANTEDITEM;
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipeEnchantedItem> {
		@Override
		public AlienTradingRecipeEnchantedItem fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipeEnchantedItem(id, json);
		}

		@Override
		public AlienTradingRecipeEnchantedItem fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipeEnchantedItem(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipeEnchantedItem recipe) {
			recipe.write(buffer);
		}

	}

}
