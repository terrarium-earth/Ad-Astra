package com.github.alexnijjar.ad_astra.client.renderer.block.flag;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class FlagTexture extends ResourceTexture {

    private static final HttpClient CLIENT = HttpClient.newBuilder().build();

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Identifier DEFAULT_FLAG = new ModIdentifier("textures/block/flag/warning_flag.png");

    private final HttpRequest request;
    private boolean loaded;
    private CompletableFuture<?> loader;

    public FlagTexture(String url) {
        super(DEFAULT_FLAG);
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Ad Astra (Minecraft Mod)")
                .build();
    }

    @SuppressWarnings({"UnstableApiUsage", "deprecation"})
    public static Identifier getTextureId(String url) {
        return new ModIdentifier("flagtextures/" + Hashing.sha1().hashUnencodedChars(url));
    }

    private void upload(NativeImage image) {
        TextureUtil.prepareImage(this.getGlId(), image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) {
        MinecraftClient.getInstance().execute(() -> {
            if (!this.loaded) {
                try {
                    super.load(manager);
                } catch (IOException var3x) {
                    LOGGER.warn("Failed to load texture: {}", this.location, var3x);
                }
                this.loaded = true;
            }
        });

        if (this.loader == null) {
            this.loader = CompletableFuture.runAsync(() -> {
                try {
                    HttpResponse<InputStream> data = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());
                    if (data.statusCode() / 100 == 2) {
                        NativeImage image = this.loadTexture(data.body());
                        MinecraftClient.getInstance().execute(() -> {
                            if (image != null) {
                                MinecraftClient.getInstance().execute(() -> {
                                    this.loaded = true;
                                    if (!RenderSystem.isOnRenderThread()) {
                                        RenderSystem.recordRenderCall(() -> this.upload(image));
                                    } else {
                                        this.upload(image);
                                    }
                                });
                            }

                        });
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Couldn't download http texture", e);
                }
            }, Util.getMainWorkerExecutor());
        }
    }

    @Nullable
    private NativeImage loadTexture(InputStream stream) {
        NativeImage nativeImage = null;

        try {
            nativeImage = NativeImage.read(stream);
        } catch (Exception var4) {
            LOGGER.warn("Error while loading the skin texture", var4);
        }

        return nativeImage;
    }
}
