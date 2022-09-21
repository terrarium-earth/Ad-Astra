package com.github.alexnijjar.ad_astra.client.sound;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.ad_astra.registry.ModSoundEvents;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class PlanetWeatherSoundPlayer implements ClientPlayerTickable {

    private ClientPlayerEntity player;
    private SoundManager soundManager;
    private List<MusicLoop> soundLoops = new LinkedList<>();

    public PlanetWeatherSoundPlayer(ClientPlayerEntity player, SoundManager soundManager) {
        this.player = player;
        this.soundManager = soundManager;
    }

    @Override
    public void tick() {
        this.soundLoops.removeIf(MovingSoundInstance::isDone);
        if (soundLoops.isEmpty()) {
            if (ModUtils.isPlanet(this.player.world) && ModUtils.planetHasAtmosphere(this.player.world)) {
                MusicLoop loop = new MusicLoop(this.player, ModSoundEvents.WINDY);
                soundLoops.add(loop);
                this.soundManager.play(loop);
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class MusicLoop extends MovingSoundInstance {
        private final ClientPlayerEntity player;

        public MusicLoop(ClientPlayerEntity player, SoundEvent soundEvent) {
            super(soundEvent, SoundCategory.WEATHER, SoundInstance.method_43221());
            this.player = player;
            this.repeat = true;
            this.repeatDelay = 0;
            this.volume = 1.0f;
            this.relative = true;
        }

        @Override
        public void tick() {
            if (this.player.isRemoved()) {
                this.setDone();
                return;
            }
            ClientWorld world = (ClientWorld) player.getWorld();
            MinecraftClient client = MinecraftClient.getInstance();

            double height = 80.0;
            double max = 0.2;
            if (world.isRaining()) {
                height -= 10.0;
                max += 0.1;
            }
            if (world.isThundering()) {
                height -= 50.0;
                max += 0.2;
            }
            float volume = (float) MathHelper.clamp((this.player.getY() - 80) / height, 0.0f, max);

            if (client.currentScreen != null && client.currentScreen.isPauseScreen()) {
                volume = 0.0f;
                return;
            }

            if (!ModUtils.isPlanet(world)) {
                volume = 0.0f;
                return;
            }
            this.volume = MathHelper.clamp(volume, 0.0f, 1.0f);
        }
    }
}
