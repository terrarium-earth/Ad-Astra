package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModIdentifier;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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
    public SoundSystem soundSystem;

    @Unique
    private static SoundInstance getSpaceSoundInstance(SoundInstance sound, float volume, float pitch) {
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
            public AttenuationType getAttenuationType() {
                return sound.getAttenuationType();
            }
        };
    }

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
    public void adastra_play(SoundInstance sound, CallbackInfo ci) {
        this.adastra_modifySound(sound, 0, ci);
    }

    @Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
    public void adastra_play(SoundInstance sound, int delay, CallbackInfo ci) {
        this.adastra_modifySound(sound, delay, ci);
    }

    @Unique
    private void adastra_modifySound(SoundInstance sound, int delay, CallbackInfo ci) {
        if (sound.getCategory().equals(SoundCategory.MASTER)) {
            return;
        }

        if (!AdAstra.CONFIG.general.doSpaceMuffler) {
            return;
        }
        if (sound.getId().equals(new ModIdentifier("rocket_fly"))) {
            return;
        }

        if (sound.getId().equals(SoundEvents.ITEM_ELYTRA_FLYING.getId())) {
            return;
        }

        if (!AdAstra.CONFIG.general.doSpaceMuffler) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) {
            return;
        }

        if (ModUtils.isOrbitWorld(client.world)) {
            boolean noOxygen = !OxygenUtils.posHasOxygen(client.world, new BlockPos(sound.getX(), sound.getY(), sound.getZ()));
            if (client.world != null && noOxygen || sound.getCategory().equals(SoundCategory.MUSIC) || sound.getCategory().equals(SoundCategory.RECORDS) || sound.getCategory().equals(SoundCategory.AMBIENT)) {
                ci.cancel();
                SoundInstance newSound = getSpaceSoundInstance(sound, ((sound.getCategory().equals(SoundCategory.MUSIC) || sound.getCategory().equals(SoundCategory.RECORDS) || sound.getCategory().equals(SoundCategory.AMBIENT)) ? 1.0f : 0.1f), 0.1f);
                this.soundSystem.play(newSound);
            }
        }
    }
}
