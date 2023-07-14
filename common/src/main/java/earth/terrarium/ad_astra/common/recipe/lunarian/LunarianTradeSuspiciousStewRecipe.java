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

import java.util.function.BiFunction;

public class LunarianTradeSuspiciousStewRecipe extends LunarianTradeRecipe {

    private MobEffect mobEffect;
    private int duration;

    public LunarianTradeSuspiciousStewRecipe(ResourceLocation id) {
        super(id);
    }

    public LunarianTradeSuspiciousStewRecipe(ResourceLocation id, Builder<LunarianTradeSuspiciousStewRecipe> builder) {
        super(id, builder);

        this.mobEffect = builder.mobEffect;
        this.duration = builder.duration;
    }

    @Override
    protected void fromJson(JsonObject json) {
        super.fromJson(json);

        this.mobEffect = Registry.MOB_EFFECT.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "mobEffect", "")));
        this.duration = GsonHelper.getAsInt(json, "duration");
    }

    @Override
    protected void toJson(JsonObject json) {
        super.toJson(json);

        json.addProperty("mobEffect", Registry.MOB_EFFECT.getKey(this.mobEffect).toString());
        json.addProperty("duration", this.duration);
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
        return new LunarianMerchantOffer.SellSuspiciousStewFactory(this.getBuyA(), this.getBuyB(), this.getMobEffect(), this.getDuration(), this.getMaxUses(), this.getExperience(), this.getMultiplier());
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
    public ItemStack getSell() {
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

    public static class Builder<RECIPE extends LunarianTradeSuspiciousStewRecipe> extends LunarianTradeRecipe.Builder<RECIPE> {

        private MobEffect mobEffect;
        private int duration;

        public Builder(BiFunction<ResourceLocation, ? extends Builder<RECIPE>, RECIPE> function) {
            super(function);
        }

        public Builder<RECIPE> mobEffect(MobEffect mobEffect) {
            this.mobEffect = mobEffect;
            return this;
        }

        public Builder<RECIPE> duration(int duration) {
            this.duration = duration;
            return this;
        }

        public MobEffect getMobEffect() {
            return this.mobEffect;
        }

        public int getDuration() {
            return this.duration;
        }
    }
}