package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {

	public static SoundEvent ROCKET_LAUNCH_SOUND_EVENT;
	public static SoundEvent WRENCH;

	private static SoundEvent register(Identifier id) {
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	public static void register() {
		ROCKET_LAUNCH_SOUND_EVENT = register(new ModIdentifier("rocket_fly"));
		WRENCH = register(new ModIdentifier("wrench"));
	}
}
