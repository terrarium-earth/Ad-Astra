package earth.terrarium.ad_astra.client.renderer.block.flag;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class FlagTexture extends SimpleTexture {

    private static final HttpClient CLIENT = HttpClient.newBuilder().build();

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation DEFAULT_FLAG = new ResourceLocation(AdAstra.MOD_ID, "textures/block/flag/warning_flag.png");

    private final HttpRequest request;
    private boolean loaded;
    private CompletableFuture<?> loader;

    public FlagTexture(String url) {
        super(DEFAULT_FLAG);
        request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Ad Astra (Minecraft Mod)")
                .build();
    }

    @SuppressWarnings("deprecation")
    public static ResourceLocation getTextureId(String url) {
        return new ResourceLocation(AdAstra.MOD_ID, "flagtextures/" + Hashing.sha1().hashUnencodedChars(url));
    }

    private void upload(NativeImage image) {
        TextureUtil.prepareImage(getId(), image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) {
        Minecraft.getInstance().execute(() -> {
            if (!loaded) {
                try {
                    super.load(manager);
                } catch (IOException var3x) {
                    LOGGER.warn("Failed to load texture: {}", location, var3x);
                }
                loaded = true;
            }
        });

        if (loader == null) {
            loader = CompletableFuture.runAsync(() -> {
                try {
                    HttpResponse<InputStream> data = CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());
                    if (data.statusCode() / 100 == 2) {
                        NativeImage image = loadTexture(data.body());
                        Minecraft.getInstance().execute(() -> {
                            if (image != null) {
                                Minecraft.getInstance().execute(() -> {
                                    loaded = true;
                                    if (!RenderSystem.isOnRenderThread()) {
                                        RenderSystem.recordRenderCall(() -> upload(image));
                                    } else {
                                        upload(image);
                                    }
                                });
                            }

                        });
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Couldn't download http texture", e);
                }
            }, Util.backgroundExecutor());
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
