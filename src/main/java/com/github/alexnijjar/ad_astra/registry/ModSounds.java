package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static final Identifier ROCKET_LAUNCH_SOUND_ID = new ModIdentifier("rocket_fly");
    public static SoundEvent ROCKET_LAUNCH_SOUND_EVENT = new SoundEvent(ROCKET_LAUNCH_SOUND_ID);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, ROCKET_LAUNCH_SOUND_ID, ROCKET_LAUNCH_SOUND_EVENT);
    }
}
