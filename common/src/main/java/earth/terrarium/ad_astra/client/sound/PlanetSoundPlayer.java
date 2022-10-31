package earth.terrarium.ad_astra.client.sound;

import earth.terrarium.ad_astra.registry.ModSoundEvents;
import earth.terrarium.ad_astra.util.ModUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.AmbientSoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

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
                    randomSpaceSound = ModSoundEvents.SPACE_SOUNDS.get(this.player.level.random.nextInt(ModSoundEvents.SPACE_SOUNDS.size())).get();
                } else if (ModUtils.isPlanet(this.player.level)) {
                    if (this.player.getY() > 80) {
                        return;
                    }
                    randomSpaceSound = ModSoundEvents.PLANET_SOUNDS.get(this.player.level.random.nextInt(ModSoundEvents.PLANET_SOUNDS.size())).get();
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
