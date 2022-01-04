package net.mrscauthd.beyond_earth.crafting;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.ModInit;

public class AlienTradingRecipePotionedItem extends AlienTradingRecipeItemStack {
	public AlienTradingRecipePotionedItem(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public AlienTradingRecipePotionedItem(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand) {
		ItemStack result = super.getResult(trader, rand);
		List<Potion> potions = ForgeRegistries.POTIONS.getValues().stream().filter(this::testPotion).collect(Collectors.toList());
		Potion potion = potions.get(rand.nextInt(potions.size()));
		return PotionUtils.setPotion(result, potion);
	}

	public boolean testPotion(Potion potion) {
		return !potion.getEffects().isEmpty() && PotionBrewing.isBrewablePotion(potion);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInit.RECIPE_SERIALIZER_ALIEN_TRADING_POTIONEDITEM.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.ALIEN_TRADING_POTIONEDITEM;
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipePotionedItem> {
		@Override
		public AlienTradingRecipePotionedItem fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipePotionedItem(id, json);
		}

		@Override
		public AlienTradingRecipePotionedItem fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipePotionedItem(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipePotionedItem recipe) {
			recipe.write(buffer);
		}

	}

}
