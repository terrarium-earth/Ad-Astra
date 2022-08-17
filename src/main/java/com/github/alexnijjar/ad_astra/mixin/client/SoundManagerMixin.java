package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

// Custom space sounds.
@Mixin(SoundManager.class)
public class SoundManagerMixin {

	@Shadow
	@Final
	private SoundSystem soundSystem;

	@Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At("HEAD"), cancellable = true)
	public void play(SoundInstance sound, CallbackInfo ci) {
		this.modifySound(sound, 0, ci);
	}

	@Inject(method = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;I)V", at = @At("HEAD"), cancellable = true)
	public void play(SoundInstance sound, int delay, CallbackInfo ci) {
		this.modifySound(sound, delay, ci);
	}

	@Unique
	private void modifySound(SoundInstance sound, int delay, CallbackInfo ci) {
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
			boolean noOxygen = !OxygenUtils.worldHasOxygen(client.world, new BlockPos(sound.getX(), sound.getY(), sound.getZ()));
			if (client.world != null && noOxygen || sound.getCategory().equals(SoundCategory.MUSIC) || sound.getCategory().equals(SoundCategory.RECORDS)) {
				ci.cancel();
				SoundInstance newSound = getSpaceSoundInstance(sound, ((sound.getCategory().equals(SoundCategory.MUSIC) || sound.getCategory().equals(SoundCategory.RECORDS)) ? 1.0f : 0.1f), 0.1f);
				this.soundSystem.play(newSound);
			}
		}
	}

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
}
