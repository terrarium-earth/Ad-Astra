package net.mrscauthd.beyond_earth.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModSounds {

    public static final Identifier ROCKET_LAUNCH_SOUND_ID = new ModIdentifier("rocket_fly");
    public static SoundEvent ROCKET_LAUNCH_SOUND_EVENT = new SoundEvent(ROCKET_LAUNCH_SOUND_ID);
    
    public static void register() {
        Registry.register(Registry.SOUND_EVENT, ROCKET_LAUNCH_SOUND_ID, ROCKET_LAUNCH_SOUND_EVENT);
    }
}
