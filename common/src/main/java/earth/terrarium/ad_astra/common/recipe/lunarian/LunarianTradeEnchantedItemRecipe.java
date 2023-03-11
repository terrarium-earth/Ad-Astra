package earth.terrarium.ad_astra.common.recipe.lunarian;

import com.google.gson.JsonObject;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class LunarianTradeEnchantedItemRecipe extends LunarianTradeRecipe {

    private int basePrice;

    public LunarianTradeEnchantedItemRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    protected void fromJson(JsonObject json) {
        super.fromJson(json);

        this.basePrice = GsonHelper.getAsInt(json, "basePrice");
    }

    @Override
    protected void fromNetwork(FriendlyByteBuf buffer) {
        super.fromNetwork(buffer);

        this.basePrice = buffer.readInt();
    }

    @Override
    protected void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);

        buffer.writeInt(this.basePrice);
    }

    @Override
    public ItemListing toItemListing() {
        return new LunarianMerchantOffer.SellEnchantedToolFactory(this.getBuyA(), this.getBuyB(), this.getSell(), this.getBasePrice(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LUNARIAN_TRADE_ENCHANTED_ITEM_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LUNARIAN_TRADE_ENCHANTED_ITEM_RECIPE.get();
    }

    @Override
    protected ItemStack getDefaultBuyA() {
        return LunarianMerchantOffer.SellEnchantedToolFactory.DEFAULT_BUY_A;
    }

    @Override
    protected ItemStack getDefaultBuyB() {
        return LunarianMerchantOffer.SellEnchantedToolFactory.DEFAULT_BUY_B;
    }

    @Override
    protected float getDefaultMultiplier() {
        return LunarianMerchantOffer.SellEnchantedToolFactory.DEFAULT_MULTIPLIER;
    }

    public int getBasePrice() {
        return this.basePrice;
    }
}