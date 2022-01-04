package net.mrscauthd.beyond_earth.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.alien.AlienTrade;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RecipesUpdatedEvent {

	@SubscribeEvent
	public static void onRecipesUpdated(net.minecraftforge.client.event.RecipesUpdatedEvent event) {
		AlienTrade.registerTrades(event.getRecipeManager());
	}

}
