package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Custom space sounds.
@Mixin(SoundManager.class)
public class SoundManagerMixin {

    @Shadow
    @Final
    public SoundEngine soundEngine;

    @Unique
    private static SoundInstance getSpaceSoundInstance(SoundInstance sound, float volume, float pitch) {
        return new SoundInstance() {

            @Override
            public ResourceLocation getLocation() {
                return sound.getLocation();
            }

            @Override
            public WeighedSoundEvents resolve(SoundManager manager) {
                return sound.resolve(manager);
            }

            @Override
            public Sound getSound() {
                return sound.getSound();
            }

            @Override
            public SoundSource getSource() {
                return sound.getSource();
            }

            @Override
            public boolean isLooping() {
                return sound.isLooping();
            }

            @Override
            public boolean isRelative() {
                return sound.isRelative();
            }

            @Override
            public int getDelay() {
                return sound.getDelay();
            }

            @Override
            public float getVolume() {
                return sound.getVolume() * volume;
            }

            @Override
            public float getPitch() {
                return sound.getPitch() * pitch;
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
            public Attenuation getAttenuation() {
                return sound.getAttenuation();
            }
        };
    }

    @Inject(method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    public void ad_astra$play(SoundInstance sound, CallbackInfo ci) {
        this.ad_astra$modifySound(sound, 0, ci);
    }

    @Inject(method = "playDelayed(Lnet/minecraft/client/resources/sounds/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
    public void ad_astra$play(SoundInstance sound, int delay, CallbackInfo ci) {
        this.ad_astra$modifySound(sound, delay, ci);
    }

    @Unique
    private void ad_astra$modifySound(SoundInstance sound, int delay, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return;
        }

        if (!AdAstraConfig.doSpaceMuffler) {
            return;
        }

        if (sound.getSource().equals(SoundSource.MASTER)) {
            return;
        }

        if (sound.getLocation().equals(new ResourceLocation(AdAstra.MOD_ID, "rocket_fly"))) {
            return;
        }

        if (sound.getLocation().equals(SoundEvents.ELYTRA_FLYING.getLocation())) {
            return;
        }

        if (ModUtils.isOrbitlevel(minecraft.level)) {
            boolean noOxygen = !OxygenUtils.posHasOxygen(minecraft.level, new BlockPos((int) sound.getX(), (int) sound.getY(), (int) sound.getZ()));
            if (minecraft.level != null && noOxygen || sound.getSource().equals(SoundSource.MUSIC) || sound.getSource().equals(SoundSource.RECORDS) || sound.getSource().equals(SoundSource.AMBIENT)) {
                ci.cancel();
                SoundInstance newSound = getSpaceSoundInstance(sound, ((sound.getSource().equals(SoundSource.MUSIC) || sound.getSource().equals(SoundSource.RECORDS) || sound.getSource().equals(SoundSource.AMBIENT)) ? 1.0f : 0.1f), 0.1f);
                this.soundEngine.play(newSound);
            }
        }
    }
}
