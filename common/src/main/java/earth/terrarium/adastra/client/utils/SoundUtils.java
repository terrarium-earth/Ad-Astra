package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.client.sounds.LanderSoundInstance;
import earth.terrarium.adastra.client.sounds.RocketSoundInstance;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import net.minecraft.client.Minecraft;

public class SoundUtils {
    public static void playRocketSound(Rocket rocket) {
        Minecraft.getInstance().getSoundManager().play(new RocketSoundInstance(rocket));
    }

    public static void playLanderSound(Lander lander) {
        Minecraft.getInstance().getSoundManager().play(new LanderSoundInstance(lander));
    }
}
