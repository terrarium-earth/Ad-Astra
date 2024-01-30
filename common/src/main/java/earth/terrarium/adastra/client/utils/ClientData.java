package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.api.systems.PlanetData;
import earth.terrarium.adastra.common.registry.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class ClientData {
    @Nullable
    private static PlanetData localData;

    public static void updateLocalData(PlanetData data) {
        if (localData != null && localData.oxygen() != data.oxygen()) {
            SoundEvent sound = data.oxygen() ? ModSoundEvents.OXYGEN_INTAKE.get() : ModSoundEvents.OXYGEN_OUTTAKE.get();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forLocalAmbience(sound, 1, 1));
        }
        localData = data;
    }

    @Nullable
    public static PlanetData getLocalData() {
        return localData;
    }
}
