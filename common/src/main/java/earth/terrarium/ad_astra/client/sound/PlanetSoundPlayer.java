package earth.terrarium.ad_astra.client.sound;

import earth.terrarium.ad_astra.registry.ModSoundEvents;
import earth.terrarium.ad_astra.util.ModUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class PlanetSoundPlayer implements ClientPlayerTickable {

    private final ClientPlayerEntity player;
    private final SoundManager soundManager;
    private int ticksUntilPlay = 0;

    public PlanetSoundPlayer(ClientPlayerEntity player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {

        --this.ticksUntilPlay;
        if (this.ticksUntilPlay <= 0) {
            double randomVal = this.player.world.random.nextDouble();
            // 1 in 100 per tick
            if (randomVal < 0.000015) {
                SoundEvent randomSpaceSound;
                this.ticksUntilPlay = 5000;

                if (ModUtils.isOrbitWorld(this.player.world)) {
                    randomSpaceSound = ModSoundEvents.SPACE_SOUNDS.get(this.player.world.random.nextInt(ModSoundEvents.SPACE_SOUNDS.size())).get();
                } else if (ModUtils.isPlanet(this.player.world)) {
                    if (this.player.getY() > 80) {
                        return;
                    }
                    randomSpaceSound = ModSoundEvents.PLANET_SOUNDS.get(this.player.world.random.nextInt(ModSoundEvents.PLANET_SOUNDS.size())).get();
                } else {
                    return;
                }

                this.soundManager.play(new SpaceSound(this.player, randomSpaceSound));
            }
        }
    }

    public static class SpaceSound extends MovingSoundInstance {
        private final ClientPlayerEntity player;

        public SpaceSound(ClientPlayerEntity player, SoundEvent soundEvent) {
            super(soundEvent, SoundCategory.AMBIENT, SoundInstance.method_43221());
            this.player = player;
            this.repeat = false;
            this.repeatDelay = 0;
            this.volume = 0.7f;
            this.relative = true;
        }

        @Override
        public void tick() {
            if (this.player.isRemoved()) {
                this.setDone();
            }
        }
    }
}
