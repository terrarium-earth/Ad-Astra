package earth.terrarium.ad_astra.client.sound;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.ad_astra.common.registry.ModSoundEvents;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

import java.util.List;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class PlanetSoundPlayer implements AmbientSoundHandler {

    private final LocalPlayer player;
    private final SoundManager soundManager;
    private int ticksUntilPlay = 0;

    public PlanetSoundPlayer(LocalPlayer player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {

        --this.ticksUntilPlay;
        if (this.ticksUntilPlay <= 0) {
            double randomVal = this.player.level.random.nextDouble();
            // 1 in 100 per tick
            if (randomVal < 0.000015) {
                SoundEvent randomSpaceSound;
                this.ticksUntilPlay = 5000;

                if (ModUtils.isOrbitlevel(this.player.level)) {
                    List<RegistryEntry<SoundEvent>> sounds = Stream.concat(ModSoundEvents.SPACE_SOUNDS.stream(), ModSoundEvents.PLANET_SOUNDS.stream()).toList();
                    randomSpaceSound = sounds.get(this.player.level.random.nextInt(sounds.size())).get();
                } else if (ModUtils.isPlanet(this.player.level)) {
                    if (this.player.getY() > 80) {
                        return;
                    }
                    List<RegistryEntry<SoundEvent>> sounds = ModSoundEvents.PLANET_SOUNDS.stream().toList();
                    randomSpaceSound = sounds.get(this.player.level.random.nextInt(sounds.size())).get();
                } else {
                    return;
                }

                this.soundManager.play(new SpaceSound(this.player, randomSpaceSound));
            }
        }
    }

    public static class SpaceSound extends AbstractTickableSoundInstance {
        private final LocalPlayer player;

        public SpaceSound(LocalPlayer player, SoundEvent soundEvent) {
            super(soundEvent, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
            this.player = player;
            this.looping = false;
            this.delay = 0;
            this.volume = 0.7f;
            this.relative = true;
        }

        @Override
        public void tick() {
            if (this.player.isRemoved()) {
                this.stop();
            }
        }
    }
}
