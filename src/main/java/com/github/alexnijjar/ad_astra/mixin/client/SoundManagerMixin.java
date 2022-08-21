package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModSounds;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

// Custom space sounds.
@Mixin(SoundManager.class)
public class SoundManagerMixin {

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    public void play(SoundInstance sound, CallbackInfo ci) {

        if (sound.getCategory().equals(SoundCategory.MASTER)) {
            return;
        }

        if (!AdAstra.CONFIG.world.doSpaceMuffler) {
            return;
        }
        if (sound.getId().equals(ModSounds.ROCKET_LAUNCH_SOUND_ID)) {
            return;
        }

        if (sound.getId().equals(SoundEvents.ITEM_ELYTRA_FLYING.getId())) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && ModUtils.isOrbitWorld(client.world) && !ModUtils.worldHasOxygen(client.world, new BlockPos(sound.getX(), sound.getY(), sound.getZ()))) {
            ci.cancel();
            SoundManager manager = (SoundManager) (Object) (this);
            SoundInstance newSound = getSpaceSoundInstance(sound);
            manager.soundSystem.play(newSound);
        }
    }

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
    public void play(SoundInstance sound, int delay, CallbackInfo ci) {

        if (sound.getCategory().equals(SoundCategory.MASTER)) {
            return;
        }

        if (!AdAstra.CONFIG.world.doSpaceMuffler) {
            return;
        }
        if (sound.getId().equals(ModSounds.ROCKET_LAUNCH_SOUND_ID)) {
            return;
        }

        if (sound.getId().equals(SoundEvents.ITEM_ELYTRA_FLYING.getId())) {
            return;
        }

        if (!AdAstra.CONFIG.world.doSpaceMuffler) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && ModUtils.isOrbitWorld(client.world) && !ModUtils.worldHasOxygen(client.world, new BlockPos(sound.getX(), sound.getY(), sound.getZ()))) {
            ci.cancel();
            SoundManager manager = (SoundManager) (Object) (this);
            SoundInstance newSound = getSpaceSoundInstance(sound);
            manager.soundSystem.play(newSound, delay);
        }
    }

    private static SoundInstance getSpaceSoundInstance(SoundInstance sound) {
        return new SoundInstance() {

            @Override
            public Identifier getId() {
                return sound.getId();
            }

            @Override
            public WeightedSoundSet getSoundSet(SoundManager manager) {
                return sound.getSoundSet(manager);
            }

            @Override
            public Sound getSound() {
                return sound.getSound();
            }

            @Override
            public SoundCategory getCategory() {
                return sound.getCategory();
            }

            @Override
            public boolean isRepeatable() {
                return sound.isRepeatable();
            }

            @Override
            public boolean isRelative() {
                return sound.isRelative();
            }

            @Override
            public int getRepeatDelay() {
                return sound.getRepeatDelay();
            }

            @Override
            public float getVolume() {
                return sound.getVolume() / 10.0f;
            }

            @Override
            public float getPitch() {
                return sound.getPitch() / 10.0f;
            }

            @Override
            public double getX() {
                return sound.getX();
            }

            @Override
            public double getY() {
                return sound.getY();
            }

            @Override
            public double getZ() {
                return sound.getZ();
            }

            @Override
            public AttenuationType getAttenuationType() {
                return sound.getAttenuationType();
            }
        };
    }
}
