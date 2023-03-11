package earth.terrarium.ad_astra.common.recipe.lunarian;

import com.google.gson.JsonObject;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class LunarianTradeSuspiciousStewRecipe extends LunarianTradeRecipe {

    private MobEffect mobEffect;
    private int duration;

    public LunarianTradeSuspiciousStewRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    protected void fromJson(JsonObject json) {
        super.fromJson(json);

        this.mobEffect = Registry.MOB_EFFECT.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "mobEffect", "")));
        this.duration = GsonHelper.getAsInt(json, "duration");
    }

    @Override
    protected void fromNetwork(FriendlyByteBuf buffer) {
        super.fromNetwork(buffer);

        this.mobEffect = Registry.MOB_EFFECT.get(buffer.readResourceLocation());
        this.duration = buffer.readInt();
    }

    @Override
    protected void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);

        buffer.writeResourceLocation(Registry.MOB_EFFECT.getKey(this.mobEffect));
        buffer.writeInt(this.duration);
    }

    @Override
    public ItemListing toItemListing() {
        return new LunarianMerchantOffer.SellSuspiciousStewFactory(this.getBuyA(), this.getBuyB(), this.getSell(), this.getMobEffect(), this.getDuration(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.LUNARIAN_TRADE_SUSPICIOUS_STEW_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.LUNARIAN_TRADE_SUSPICIOUS_STEW_RECIPE.get();
    }

    @Override
    protected ItemStack getDefaultBuyA() {
        return LunarianMerchantOffer.SellSuspiciousStewFactory.DEFAULT_BUY_A;
    }

    @Override
    protected ItemStack getDefaultBuyB() {
        return LunarianMerchantOffer.SellSuspiciousStewFactory.DEFAULT_BUY_B;
    }

    @Override
    protected ItemStack getDefaultSell() {
        return LunarianMerchantOffer.SellSuspiciousStewFactory.DEFAULT_SELL;
    }

    @Override
    protected int getDefaultMaxUses() {
        return LunarianMerchantOffer.SellSuspiciousStewFactory.DEFAULT_MAX_USES;
    }

    @Override
    protected float getDefaultMultiplier() {
        return LunarianMerchantOffer.SellSuspiciousStewFactory.DEFAULT_MULTIPLIER;
    }

    public MobEffect getMobEffect() {
        return this.mobEffect;
    }

    public int getDuration() {
        return this.duration;
    }
}