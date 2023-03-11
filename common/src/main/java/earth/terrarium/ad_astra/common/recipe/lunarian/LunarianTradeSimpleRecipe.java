package earth.terrarium.ad_astra.common.recipe.lunarian;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class LunarianTradeSimpleRecipe extends LunarianTradeRecipe {

    public LunarianTradeSimpleRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LUNARIAN_TRADE_SIMPLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LUNARIAN_TRADE_SIMPLE_RECIPE.get();
    }

    @Override
    public ItemListing toItemListing() {
        return new LunarianMerchantOffer.ItemStackForItemStackFactory(this.getBuyA(), this.getBuyB(), this.getSell(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
    }
}
