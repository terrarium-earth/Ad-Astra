package earth.terrarium.adastra.client.radio.audio;

import com.google.common.hash.Hashing;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.RadioConfig;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractSoundInstance;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.client.sounds.SoundBufferLibrary;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantFloat;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public final class RadioSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {

    private final String url;
    private boolean stopped = false;

    @SuppressWarnings("deprecation")
    public RadioSoundInstance(String url, RandomSource randomSource) {
        super(
            new ResourceLocation(AdAstra.MOD_ID, "radio/" + Hashing.sha1().hashUnencodedChars(url)),
            SoundSource.MASTER,
            randomSource
        );
        this.url = url;
    }

    @Override
    public WeighedSoundEvents resolve(@NotNull SoundManager manager) {
        WeighedSoundEvents soundEvents = new WeighedSoundEvents(this.getLocation(), null);
        soundEvents.addSound(new Sound(
            getLocation().toString(),
            ConstantFloat.of(1f),
            ConstantFloat.of(1f),
            1,
            Sound.Type.FILE,
            true,
            false,
            0
        ));
        this.sound = soundEvents.getSound(this.random);
        return soundEvents;
    }

    @Override
    public float getVolume() {
        return RadioConfig.volume / 100f;
    }

    @Override
    public boolean isStopped() {
        return this.stopped;
    }

    @Override
    public void tick() {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isAlive()) {
            this.x = (float) Minecraft.getInstance().player.getX();
            this.y = (float) Minecraft.getInstance().player.getY();
            this.z = (float) Minecraft.getInstance().player.getZ();
        } else {
            this.stopped = true;
        }
    }

    public CompletableFuture<AudioStream> getStream() {
        return RadioHandler.getRadioStream(this.url)
            .thenApplyAsync(stream -> {
                try {
                    return new Mp3AudioStream(stream);
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }, Util.backgroundExecutor())
            .handleAsync((stream, e) -> {
                if (e != null) {
                    e.printStackTrace();
                }
                return stream;
            }, Util.backgroundExecutor());
    }

    public String url() {
        return this.url;
    }

    // THIS IS USED BY FABRIC, THIS IS A SOFT OVERRIDE DO NOT REMOVE
    @SuppressWarnings("unused")
    public CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary library, ResourceLocation id, boolean loop) {
        return getStream();
    }

    // THIS IS USED BY FORGE, THIS IS A SOFT OVERRIDE DO NOT REMOVE
    @SuppressWarnings("unused")
    public CompletableFuture<AudioStream> getAudioStream(SoundBufferLibrary library, Sound sound, boolean loop) {
        return getStream();
    }
}
