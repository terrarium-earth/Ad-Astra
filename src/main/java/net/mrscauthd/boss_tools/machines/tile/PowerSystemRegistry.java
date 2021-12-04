package net.mrscauthd.boss_tools.machines.tile;

import java.util.LinkedHashMap;

import net.minecraft.util.ResourceLocation;

public class PowerSystemRegistry extends LinkedHashMap<ResourceLocation, PowerSystem> {

	private static final long serialVersionUID = 1L;

	public PowerSystemRegistry() {

	}

	public void put(PowerSystem powerSystem) {
		this.put(powerSystem.getName(), powerSystem);
	}

}
