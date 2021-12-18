package net.mrscauthd.beyond_earth.paintings;

import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.mrscauthd.beyond_earth.BeyondEarthMod;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PaintingRegistry {
	@SubscribeEvent
	public static void registerPaintingType(RegistryEvent.Register<Motive> event) {
		event.getRegistry().register(new Motive(32, 16).setRegistryName("painting_earth"));
		event.getRegistry().register(new Motive(32, 32).setRegistryName("painting_galaxy"));
		event.getRegistry().register(new Motive(32, 16).setRegistryName("painting_math"));
		event.getRegistry().register(new Motive(64, 32).setRegistryName("painting_planet_math"));
		event.getRegistry().register(new Motive(16, 16).setRegistryName("painting_venus"));
	}
}