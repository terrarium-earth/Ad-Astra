package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.ILunarianTradeRecipeBuilderProvider;
import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import earth.terrarium.ad_astra.common.recipe.condition.LunarianDefaultTradesCondition;
import earth.terrarium.ad_astra.common.recipe.lunarian.LunarianTradeRecipe.Builder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;

import java.util.Map.Entry;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        String lunarianTradePath = "lunarian_trades/";

        for (Entry<VillagerProfession, Int2ObjectMap<ItemListing[]>> professionEntry : LunarianMerchantOffer.DEFAULT_PROFESSION_TO_LEVELED_TRADE.entrySet()) {
            VillagerProfession profession = professionEntry.getKey();

            for (Int2ObjectMap.Entry<ItemListing[]> levelEntry : professionEntry.getValue().int2ObjectEntrySet()) {
                int level = levelEntry.getIntKey();
                ItemListing[] listings = levelEntry.getValue();

                for (int i = 0; i < listings.length; i++) {
                    ItemListing listing = listings[i];

                    if (listing instanceof ILunarianTradeRecipeBuilderProvider provider) {
                        Builder<?> builder = provider.provideRecipeBuilder();
                        builder.profession(profession).level(level);
                        builder.addCondition(LunarianDefaultTradesCondition.INSTANCE);

                        ResourceLocation id = new ResourceLocation(AdAstra.MOD_ID, lunarianTradePath + profession.name() + "_" + level + "_" + (i + 1) + "_" + provider.getRecipeNameSuffix());
                        consumer.accept(builder.build(id));
                    }
                }
            }
        }

        for (Int2ObjectMap.Entry<ItemListing[]> levelEntry : LunarianMerchantOffer.DEFAULT_WANDERING_TRADER_TRADES.int2ObjectEntrySet()) {
            int level = levelEntry.getIntKey();
            ItemListing[] listings = levelEntry.getValue();

            for (int i = 0; i < listings.length; i++) {
                ItemListing listing = listings[i];

                if (listing instanceof ILunarianTradeRecipeBuilderProvider provider) {
                    Builder<?> builder = provider.provideRecipeBuilder();
                    builder.wandring().level(level);
                    builder.addCondition(LunarianDefaultTradesCondition.INSTANCE);

                    ResourceLocation id = new ResourceLocation(AdAstra.MOD_ID, lunarianTradePath + "wandering_" + level + "_" + (i + 1) + "_" + provider.getRecipeNameSuffix());
                    consumer.accept(builder.build(id));
                }
            }
        }
    }
}
