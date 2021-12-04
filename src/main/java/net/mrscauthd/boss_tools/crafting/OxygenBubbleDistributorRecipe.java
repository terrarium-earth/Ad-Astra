package net.mrscauthd.boss_tools.crafting;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.ModInnet;

public class OxygenBubbleDistributorRecipe extends OxygenMakingRecipeAbstract {

	public OxygenBubbleDistributorRecipe(ResourceLocation id, JsonObject json) {
		super(id, json);
	}

	public OxygenBubbleDistributorRecipe(ResourceLocation id, PacketBuffer buffer) {
		super(id, buffer);
	}

	public OxygenBubbleDistributorRecipe(ResourceLocation id, FluidIngredient ingredient, int oxygen) {
		super(id, ingredient, oxygen);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModInnet.RECIPE_SERIALIZER_OXYGENBUBBLEDISTRIBUTOR.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return BossToolsRecipeTypes.OXYGENBUBBLEDISTRIBUTOR;
	}

}
