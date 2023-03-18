package earth.terrarium.ad_astra.common.recipe.lunarian;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class LunarianTradeEnchantedBookRecipe extends LunarianTradeRecipe {

    public LunarianTradeEnchantedBookRecipe(ResourceLocation id) {
        super(id);
    }

    public LunarianTradeEnchantedBookRecipe(ResourceLocation id, Builder<LunarianTradeEnchantedBookRecipe> builder) {
        super(id, builder);
    }

    @Override
    public ItemListing toItemListing() {
        return new LunarianMerchantOffer.EnchantBookFactory(this.getBuyA(), this.getBuyB(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LUNARIAN_TRADE_ENCHANTED_BOOK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LUNARIAN_TRADE_ENCHANTED_BOOK_RECIPE.get();
    }

    @Override
    protected ItemStack getDefaultBuyA() {
        return LunarianMerchantOffer.EnchantBookFactory.DEFAULT_BUY_A;
    }

    @Override
    protected ItemStack getDefaultBuyB() {
        return LunarianMerchantOffer.EnchantBookFactory.DEFAULT_BUY_B;
    }

    @Override
    protected int getDefaultMaxUses() {
        return LunarianMerchantOffer.EnchantBookFactory.DEFAULT_MAX_USES;
    }

    @Override
    protected float getDefaultMultiplier() {
        return LunarianMerchantOffer.EnchantBookFactory.DEFAULT_MULTIPLIER;
    }
}