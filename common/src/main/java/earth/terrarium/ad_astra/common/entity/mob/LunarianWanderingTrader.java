package earth.terrarium.ad_astra.common.entity.mob;

import earth.terrarium.ad_astra.common.entity.LunarianTradeOffers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

public class LunarianWanderingTrader extends WanderingTrader {

    public LunarianWanderingTrader(EntityType<? extends WanderingTrader> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void updateTrades() {
        VillagerTrades.ItemListing[] factorys = LunarianTradeOffers.WANDERING_TRADER_TRADES.get(1);
        VillagerTrades.ItemListing[] factorys2 = LunarianTradeOffers.WANDERING_TRADER_TRADES.get(2);
        if (factorys == null || factorys2 == null) {
            return;
        }
        MerchantOffers tradeOfferList = this.getOffers();
        this.addOffersFromItemListings(tradeOfferList, factorys, 5);
        int i = this.random.nextInt(factorys2.length);
        VillagerTrades.ItemListing factory = factorys2[i];
        MerchantOffer tradeOffer = factory.getOffer(this, this.random);
        if (tradeOffer != null) {
            tradeOfferList.add(tradeOffer);
        }
    }
}
