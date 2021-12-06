package net.mrscauthd.boss_tools.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityOxygen {

	public static Capability<IOxygenStorage> OXYGEN = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public static void register(RegisterCapabilitiesEvent event) {
		//event.register(IOxygenStorage.class);
	}

}
