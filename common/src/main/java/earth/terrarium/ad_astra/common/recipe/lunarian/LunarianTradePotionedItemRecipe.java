package earth.terrarium.ad_astra.common.recipe.lunarian;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.BiFunction;

public class LunarianTradePotionedItemRecipe extends LunarianTradeRecipe {

    public LunarianTradePotionedItemRecipe(ResourceLocation id) {
        super(id);
    }

    public LunarianTradePotionedItemRecipe(ResourceLocation id, Builder<LunarianTradePotionedItemRecipe> builder) {
        super(id, builder);
    }

    @Override
    public ItemListing toItemListing() {
        return new LunarianMerchantOffer.SellPotionHoldingItemFactory(this.getBuyA(), this.getBuyB(), this.getSell(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LUNARIAN_TRADE_POTIONED_ITEM_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LUNARIAN_TRADE_POTIONED_ITEM_RECIPE.get();
    }

    @Override
    protected ItemStack getDefaultBuyA() {
        return LunarianMerchantOffer.SellPotionHoldingItemFactory.DEFAULT_BUY_A;
    }

    @Override
    protected ItemStack getDefaultBuyB() {
        return LunarianMerchantOffer.SellPotionHoldingItemFactory.DEFAULT_BUY_B;
    }

    @Override
    protected ItemStack getDefaultSell() {
        return LunarianMerchantOffer.SellPotionHoldingItemFactory.DEFAULT_SELL;
    }

    @Override
    protected float getDefaultMultiplier() {
        return LunarianMerchantOffer.SellPotionHoldingItemFactory.DEFAULT_MULTIPLIER;
    }

    public static class Builder<RECIPE extends LunarianTradePotionedItemRecipe> extends LunarianTradeRecipe.Builder<RECIPE> {

        public Builder(BiFunction<ResourceLocation, ? extends Builder<RECIPE>, RECIPE> function) {
            super(function);
        }
    }
}