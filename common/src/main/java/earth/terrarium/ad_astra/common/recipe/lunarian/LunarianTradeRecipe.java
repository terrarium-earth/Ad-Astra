package earth.terrarium.ad_astra.common.recipe.lunarian;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;

import earth.terrarium.ad_astra.common.recipe.ModRecipe;
import earth.terrarium.ad_astra.common.recipe.ModRecipeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public abstract class LunarianTradeRecipe extends ModRecipe {

    private boolean wandering;
    private VillagerProfession profession;
    private int level;

    private ItemStack buyA;
    private ItemStack buyB;
    private ItemStack sell;

    private int maxUses;
    private int experience;
    private float multiplier;

    protected LunarianTradeRecipe(ResourceLocation id) {
        super(id);
    }

    protected LunarianTradeRecipe(ResourceLocation id, Builder<? extends LunarianTradeRecipe> builder) {
        super(id);

        this.wandering = builder.wandering;
        this.profession = builder.profession;
        this.level = builder.level;

        this.buyA = builder.buyA;
        this.buyB = builder.buyB;
        this.sell = builder.sell;

        this.maxUses = builder.maxUses;
        this.experience = builder.experience;
        this.multiplier = builder.multiplier;
    }

    protected void fromJson(JsonObject json) {
        this.wandering = GsonHelper.getAsBoolean(json, "wandering", false);
        this.profession = Registry.VILLAGER_PROFESSION.get(ResourceLocation.tryParse(GsonHelper.getAsString(json, "profession", "")));
        this.level = GsonHelper.getAsInt(json, "level");

        this.buyA = ItemStackCodec.CODEC.parse(JsonOps.INSTANCE, json.get("buyA")).result().orElse(this.getDefaultBuyA());
        this.buyB = ItemStackCodec.CODEC.parse(JsonOps.INSTANCE, json.get("buyB")).result().orElse(this.getDefaultBuyB());
        this.sell = ItemStackCodec.CODEC.parse(JsonOps.INSTANCE, json.get("sell")).result().orElse(this.getDefaultSell());

        this.maxUses = GsonHelper.getAsInt(json, "maxUses", this.getDefaultMaxUses());
        this.experience = GsonHelper.getAsInt(json, "experience", this.getDefaultExperience());
        this.multiplier = GsonHelper.getAsFloat(json, "multiplier", this.getDefaultMultiplier());
    }

    protected void toJson(JsonObject json) {
        if (this.wandering) {
            json.addProperty("wandering", this.wandering);
        } else {
            json.addProperty("profession", Registry.VILLAGER_PROFESSION.getKey(this.profession).toString());
        }
        json.addProperty("level", this.level);

        if (!this.buyA.isEmpty()) {
            json.add("buyA", ItemStackCodec.CODEC.encodeStart(JsonOps.INSTANCE, this.buyA).result().get());
        }
        if (!this.buyB.isEmpty()) {
            json.add("buyB", ItemStackCodec.CODEC.encodeStart(JsonOps.INSTANCE, this.buyB).result().get());
        }
        if (!this.sell.isEmpty()) {
            json.add("sell", ItemStackCodec.CODEC.encodeStart(JsonOps.INSTANCE, this.sell).result().get());
        }

        if (this.maxUses != this.getDefaultMaxUses()) {
            json.addProperty("maxUses", this.maxUses);
        }
        if (this.experience != this.getDefaultExperience()) {
            json.addProperty("experience", this.experience);
        }
        if (this.multiplier != this.getDefaultMultiplier()) {
            json.addProperty("multiplier", this.multiplier);
        }
    }

    protected ItemStack getDefaultBuyA() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getDefaultBuyB() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getDefaultSell() {
        return ItemStack.EMPTY;
    }

    protected int getDefaultMaxUses() {
        return 12;
    }

    protected int getDefaultExperience() {
        return 0;
    }

    protected float getDefaultMultiplier() {
        return 0.05F;
    }

    protected void fromNetwork(FriendlyByteBuf buffer) {
        this.wandering = buffer.readBoolean();
        this.profession = Registry.VILLAGER_PROFESSION.get(buffer.readResourceLocation());
        this.level = buffer.readInt();

        this.buyA = buffer.readItem();
        this.buyB = buffer.readItem();
        this.sell = buffer.readItem();

        this.maxUses = buffer.readInt();
        this.experience = buffer.readInt();
        this.multiplier = buffer.readFloat();
    }

    protected void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.wandering);
        buffer.writeResourceLocation(Registry.VILLAGER_PROFESSION.getKey(this.profession));
        buffer.writeInt(this.level);

        buffer.writeItem(this.buyA);
        buffer.writeItem(this.buyB);
        buffer.writeItem(this.sell);

        buffer.writeInt(this.maxUses);
        buffer.writeInt(this.experience);
        buffer.writeFloat(this.multiplier);
    }

    public boolean isWandering() {
        return this.wandering;
    }

    public VillagerProfession getProfession() {
        return this.profession;
    }

    public int getLevel() {
        return this.level;
    }

    public ItemStack getBuyA() {
        return this.buyA.copy();
    }

    public ItemStack getBuyB() {
        return this.buyB.copy();
    }

    public ItemStack getSell() {
        return this.sell.copy();
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public int getExperience() {
        return this.experience;
    }

    public float getMultiplier() {
        return this.multiplier;
    }

    public abstract ItemListing toItemListing();

    public static class Serializer<RECIPE extends LunarianTradeRecipe> implements RecipeSerializer<RECIPE> {

        private final Function<ResourceLocation, RECIPE> function;

        public Serializer(Function<ResourceLocation, RECIPE> function) {
            this.function = function;
        }

        @Override
        public RECIPE fromJson(ResourceLocation id, JsonObject json) {
            RECIPE recipe = this.function.apply(id);
            recipe.fromJson(json);
            return recipe;
        }

        @Override
        public RECIPE fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            RECIPE recipe = this.function.apply(id);
            recipe.fromNetwork(buffer);
            return recipe;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RECIPE recipe) {
            recipe.toNetwork(buffer);
        }
    }

    public static class Builder<RECIPE extends LunarianTradeRecipe> extends ModRecipeBuilder<RECIPE> {

        private final BiFunction<ResourceLocation, Builder<RECIPE>, RECIPE> function;

        private boolean wandering = false;
        private VillagerProfession profession = VillagerProfession.NONE;
        private int level = 0;

        private ItemStack buyA = ItemStack.EMPTY;
        private ItemStack buyB = ItemStack.EMPTY;
        private ItemStack sell = ItemStack.EMPTY;

        private int maxUses = 0;
        private int experience = 0;
        private float multiplier = 0.0F;

        @SuppressWarnings("unchecked")
        public Builder(BiFunction<ResourceLocation, ? extends Builder<RECIPE>, RECIPE> function) {
            this.function = (BiFunction<ResourceLocation, Builder<RECIPE>, RECIPE>) function;
        }

        public Builder<RECIPE> wandring() {
            this.wandering = true;
            this.profession = null;
            return this;
        }

        public Builder<RECIPE> profession(VillagerProfession profession) {
            this.wandering = false;
            this.profession = profession;
            return this;
        }

        public Builder<RECIPE> level(int level) {
            this.level = level;
            return this;
        }

        public Builder<RECIPE> buy(ItemStack buyA) {
            return this.buy(buyA, ItemStack.EMPTY);
        }

        public Builder<RECIPE> buy(ItemStack buyA, ItemStack buyB) {
            this.buyA = buyA;
            this.buyB = buyB;
            return this;
        }

        public Builder<RECIPE> sell(ItemStack sell) {
            this.sell = sell;
            return this;
        }

        public Builder<RECIPE> maxUses(int maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        public Builder<RECIPE> experience(int experience) {
            this.experience = experience;
            return this;
        }

        public Builder<RECIPE> multiplier(float multiplier) {
            this.multiplier = multiplier;
            return this;
        }

        public boolean isWandering() {
            return this.wandering;
        }

        public VillagerProfession getProfession() {
            return this.profession;
        }

        public int getLevel() {
            return this.level;
        }

        public ItemStack getBuyA() {
            return this.buyA;
        }

        public ItemStack getBuyB() {
            return this.buyB;
        }

        public ItemStack getSell() {
            return this.sell;
        }

        public int getMaxUses() {
            return this.maxUses;
        }

        public int getExperience() {
            return this.experience;
        }

        public float getMultiplier() {
            return this.multiplier;
        }

        public ModFinishedRecipe build(ResourceLocation id) {
            RECIPE recipe = this.function.apply(id, this);
            return new ModFinishedRecipe(this, id) {

                @Override
                public void serializeRecipeData(JsonObject json) {
                    super.serializeRecipeData(json);
                    recipe.toJson(json);
                }

                @Override
                public RecipeSerializer<?> getType() {
                    return recipe.getSerializer();
                }
            };
        }
    }
}
