package earth.terrarium.adastra.client.radio.audio;

import com.teamresourceful.resourcefullib.common.utils.WebUtils;
import earth.terrarium.adastra.mixins.client.SoundEngineAccessor;
import earth.terrarium.adastra.mixins.client.SoundManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.RandomSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

public final class RadioHandler {

    private static final Map<String, CompletableFuture<InputStream>> IN_PROGRESS = new ConcurrentHashMap<>();
    private static RadioSoundInstance lastStation = null;

    /**
     * Returns an already existing stream or fetches a new one.
     */
    public static CompletableFuture<InputStream> getRadioStream(String url) {
        CompletableFuture<InputStream> future = IN_PROGRESS.get(url);
        if (future != null && !future.isDone()) {
            return future;
        }

        future = streamRadio(url).handle((value, e) -> {
            if (e != null) {
                throw new CompletionException(e);
            }
            return value;
        }).thenApplyAsync(value -> {
            IN_PROGRESS.remove(url);
            return value;
        }, Minecraft.getInstance());

        IN_PROGRESS.put(url, future);
        return future;
    }

    /**
     * Fetches a radio stream.
     */
    private static CompletableFuture<InputStream> streamRadio(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse<InputStream> response = WebUtils.get(url, HttpResponse.BodyHandlers.ofInputStream()).orElse(null);
                if (response == null) throw new IOException("Radio response was null for: " + url);
                if (response.headers().firstValueAsLong("Content-Length").orElse(-1L) >= 0)
                    throw new IOException("Radio response had content length for: " + url + " (" + response.headers().firstValueAsLong("Content-Length").orElse(-1L) + ")");
                return new RadioStream(response::body);
            } catch (Throwable e) {
                throw new CompletionException(e);
            }
        }, HttpUtil.DOWNLOAD_EXECUTOR);
    }

    public static void play(String url, RandomSource random) {
        stopRadioInstances();
        lastStation = new RadioSoundInstance(url, random);
        Minecraft.getInstance().getSoundManager().play(lastStation);
    }

    public static void stop() {
        stopRadioInstances();
        lastStation = null;
    }

    private static void stopRadioInstances() {
        SoundManager manager = Minecraft.getInstance().getSoundManager();
        SoundManagerAccessor accessor = (SoundManagerAccessor) manager;
        SoundEngine engine = accessor.getEngine();
        SoundEngineAccessor engineAccessor = (SoundEngineAccessor) engine;
        List<RadioSoundInstance> radioInstances = new ArrayList<>();
        for (SoundInstance soundInstance : engineAccessor.getSoundChannels().keySet()) {
            if (soundInstance instanceof RadioSoundInstance radioInstance) {
                radioInstances.add(radioInstance);
            }
        }
        radioInstances.forEach(manager::stop);
    }

    public static String getPlaying() {
        if (lastStation == null) return null;
        return lastStation.url();
    }

    public static RadioSoundInstance getLastStation() {
        return lastStation;
    }
}
