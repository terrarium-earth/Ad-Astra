package net.mrscauthd.beyond_earth.crafting;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.mrscauthd.beyond_earth.ModInit;

public class AlienTradingRecipeDyedItem extends AlienTradingRecipeItemStack {
	private float chance1 = 0.7F;
	private float chance2 = 0.8F;

	public AlienTradingRecipeDyedItem(ResourceLocation id, JsonObject json) {
		super(id, json);

		this.chance1 = GsonHelper.getAsFloat(json, "chance1", this.chance1);
		this.chance2 = GsonHelper.getAsFloat(json, "chance2", this.chance2);
	}

	public AlienTradingRecipeDyedItem(ResourceLocation id, FriendlyByteBuf buffer) {
		super(id, buffer);

		this.chance1 = buffer.readFloat();
		this.chance2 = buffer.readFloat();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		super.write(buffer);

		buffer.writeFloat(this.chance1);
		buffer.writeFloat(this.chance2);
	}

	@Override
	public ItemStack getResult(Entity trader, Random rand) {
		
		ItemStack result = super.getResult(trader, rand);

		if (result.getItem() instanceof DyeableArmorItem) {
			List<DyeItem> list = Lists.newArrayList();
			list.add(getRandomDyeItem(rand));

			if (rand.nextFloat() > this.getChance1()) {
				list.add(getRandomDyeItem(rand));
			}

			if (rand.nextFloat() > this.getChance2()) {
				list.add(getRandomDyeItem(rand));
			}

			return DyeableLeatherItem.dyeArmor(result, list);
		} else {
			return result;
		}

	}

	public DyeItem getRandomDyeItem(Random random) {
		return DyeItem.byColor(DyeColor.byId(random.nextInt(16)));
	}

	public float getChance1() {
		return this.chance1;
	}

	public float getChance2() {
		return this.chance2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModInit.RECIPE_SERIALIZER_ALIEN_TRADING_DYEDITEM.get();
	}

	@Override
	public RecipeType<?> getType() {
		return BeyondEarthRecipeTypes.ALIEN_TRADING_DYEDITEM;
	}

	public static class Serializer extends BeyondEarthRecipeSerializer<AlienTradingRecipeDyedItem> {
		@Override
		public AlienTradingRecipeDyedItem fromJson(ResourceLocation id, JsonObject json) {
			return new AlienTradingRecipeDyedItem(id, json);
		}

		@Override
		public AlienTradingRecipeDyedItem fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
			return new AlienTradingRecipeDyedItem(id, buffer);
		}

		@Override
		public void toNetwork(FriendlyByteBuf buffer, AlienTradingRecipeDyedItem recipe) {
			recipe.write(buffer);
		}

	}

}
