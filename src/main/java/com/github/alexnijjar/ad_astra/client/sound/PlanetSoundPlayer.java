package com.github.alexnijjar.ad_astra.client.sound;

import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class PlanetSoundPlayer implements ClientPlayerTickable {

    private ClientPlayerEntity player;
    private SoundManager soundManager;
    private int ticksUntilPlay = 0;

    public PlanetSoundPlayer(ClientPlayerEntity player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {

        --this.ticksUntilPlay;
        if (this.ticksUntilPlay <= 0) {
            float randomVal = this.player.world.random.nextFloat();
            // 1 in 100 per tick
            if (randomVal < 0.0002f) {
                SoundEvent randomSpaceSound;
                this.ticksUntilPlay = 2000;

                if (ModUtils.isOrbitWorld(this.player.world)) {
                    randomSpaceSound = ModSoundEvents.SPACE_SOUNDS[this.player.world.random.nextInt(ModSoundEvents.SPACE_SOUNDS.length)];
                } else if (ModUtils.isPlanet(this.player.world)) {
                    if (this.player.getY() > 80) {
                        return;
                    }
                    randomSpaceSound = ModSoundEvents.PLANET_SOUNDS[this.player.world.random.nextInt(ModSoundEvents.PLANET_SOUNDS.length)];
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
            super(soundEvent, SoundCategory.AMBIENT);
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
                return;
            }
        }
    }
}
