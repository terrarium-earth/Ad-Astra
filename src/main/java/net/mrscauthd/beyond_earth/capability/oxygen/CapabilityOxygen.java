package net.mrscauthd.beyond_earth.capability.oxygen;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityOxygen {
	public static Capability<IOxygenStorage> OXYGEN = CapabilityManager.get(new CapabilityToken<>() {
	});
}
