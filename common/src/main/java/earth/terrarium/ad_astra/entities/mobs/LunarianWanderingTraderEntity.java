package earth.terrarium.ad_astra.entities.mobs;

import earth.terrarium.ad_astra.entities.LunarianTradeOffers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;

public class LunarianWanderingTraderEntity extends WanderingTraderEntity {

	public LunarianWanderingTraderEntity(EntityType<? extends WanderingTraderEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void fillRecipes() {
		TradeOffers.Factory[] factorys = LunarianTradeOffers.WANDERING_TRADER_TRADES.get(1);
		TradeOffers.Factory[] factorys2 = LunarianTradeOffers.WANDERING_TRADER_TRADES.get(2);
		if (factorys == null || factorys2 == null) {
			return;
		}
		TradeOfferList tradeOfferList = this.getOffers();
		this.fillRecipesFromPool(tradeOfferList, factorys, 5);
		int i = this.random.nextInt(factorys2.length);
		TradeOffers.Factory factory = factorys2[i];
		TradeOffer tradeOffer = factory.create(this, this.random);
		if (tradeOffer != null) {
			tradeOfferList.add(tradeOffer);
		}
	}
}
