package earth.terrarium.ad_astra.common.entity.mob;

import earth.terrarium.ad_astra.common.entity.LunarianMerchantOffer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class LunarianWanderingTrader extends WanderingTrader {

    public LunarianWanderingTrader(EntityType<? extends WanderingTrader> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void updateTrades() {
        RecipeManager recipeManager = this.getLevel().getRecipeManager();
        VillagerTrades.ItemListing[] factorys = LunarianMerchantOffer.getWanderingItemListings(recipeManager, 1);
        VillagerTrades.ItemListing[] factorys2 = LunarianMerchantOffer.getWanderingItemListings(recipeManager, 2);

        MerchantOffers tradeOfferList = this.getOffers();
        this.addOffersFromItemListings(tradeOfferList, factorys, 5);

        if (factorys2.length > 0) {
            int i = this.random.nextInt(factorys2.length);
            VillagerTrades.ItemListing factory = factorys2[i];
            MerchantOffer tradeOffer = factory.getOffer(this, this.random);
            if (tradeOffer != null) {
                tradeOfferList.add(tradeOffer);
            }
        }
    }
}
