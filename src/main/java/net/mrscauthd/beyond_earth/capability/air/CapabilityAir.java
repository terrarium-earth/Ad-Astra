package net.mrscauthd.beyond_earth.capability.air;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityAir {
    public static Capability<IAirStorage> AIR = CapabilityManager.get(new CapabilityToken<>() {
    });
}
