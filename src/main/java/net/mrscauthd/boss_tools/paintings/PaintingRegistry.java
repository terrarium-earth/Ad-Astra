package net.mrscauthd.boss_tools.paintings;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;

import net.minecraft.entity.item.PaintingType;
import net.mrscauthd.boss_tools.BossToolsMod;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PaintingRegistry {
	@SubscribeEvent
	public static void registerPaintingType(RegistryEvent.Register<PaintingType> event) {
		event.getRegistry().register(new PaintingType(32, 16).setRegistryName("painting_earth"));
		event.getRegistry().register(new PaintingType(32, 32).setRegistryName("painting_galaxy"));
		event.getRegistry().register(new PaintingType(32, 16).setRegistryName("painting_math"));
		event.getRegistry().register(new PaintingType(64, 32).setRegistryName("painting_planet_math"));
		event.getRegistry().register(new PaintingType(16, 16).setRegistryName("painting_venus"));
	}
}