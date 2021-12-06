package net.mrscauthd.boss_tools.machines.tile;

import java.util.LinkedHashMap;

import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.BossToolsMod;

public class NamedComponentRegistry<T> extends LinkedHashMap<ResourceLocation, T> {
	public static final ResourceLocation UNNAMED = new ResourceLocation(BossToolsMod.ModId, "unnmaed");
	private static final long serialVersionUID = 1L;

	public void put(T value) {
		if (this.containsKey(UNNAMED)) {
			throw new IllegalArgumentException("UNNAMED component is already added");
		}

		this.put(UNNAMED, value);
	}
}
