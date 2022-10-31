package earth.terrarium.ad_astra.client.sound;

import earth.terrarium.ad_astra.registry.ModSoundEvents;
import earth.terrarium.ad_astra.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import java.util.LinkedList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PlanetWeatherSoundPlayer implements AmbientSoundHandler {

    private final LocalPlayer player;
    private final SoundManager soundManager;
    private final List<MusicLoop> soundLoops = new LinkedList<>();

    public PlanetWeatherSoundPlayer(LocalPlayer player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {
        this.soundLoops.removeIf(AbstractTickableSoundInstance::isStopped);
        if (soundLoops.isEmpty()) {
            if (ModUtils.isPlanet(this.player.level) && ModUtils.planetHasAtmosphere(this.player.level)) {
                MusicLoop loop = new MusicLoop(this.player, ModSoundEvents.WINDY.get());
                soundLoops.add(loop);
                this.soundManager.play(loop);
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class MusicLoop extends AbstractTickableSoundInstance {
        private final LocalPlayer player;

        public MusicLoop(LocalPlayer player, SoundEvent soundEvent) {
            super(soundEvent, SoundSource.WEATHER, SoundInstance.createUnseededRandom());
            this.player = player;
            this.looping = true;
            this.delay = 0;
            this.volume = 1.0f;
            this.relative = true;
        }

        @Override
        public void tick() {
            if (this.player.isRemoved()) {
                this.stop();
                return;
            }
            ClientLevel level = (ClientLevel) player.getLevel();
            Minecraft client = Minecraft.getInstance();

            double height = 80.0;
            double max = 0.2;
            if (level.isRaining()) {
                height -= 10.0;
                max += 0.1;
            }
            if (level.isThundering()) {
                height -= 50.0;
                max += 0.2;
            }
            float volume = (float) Mth.clamp((this.player.getY() - 80) / height, 0.0f, max);

            if (client.screen != null && client.screen.isPauseScreen()) {
                volume = 0.0f;
                return;
            }

            if (!ModUtils.isPlanet(level)) {
                volume = 0.0f;
                return;
            }
            this.volume = Mth.clamp(volume, 0.0f, 1.0f);
        }
    }
}
