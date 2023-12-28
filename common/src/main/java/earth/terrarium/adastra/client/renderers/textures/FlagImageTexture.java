package earth.terrarium.adastra.client.renderers.textures;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.flag.FlagColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.IOException;

public class FlagImageTexture extends SimpleTexture {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ResourceLocation DEFAULT_FLAG = new ResourceLocation(AdAstra.MOD_ID, "textures/block/flag/warning_flag.png");

    private final FlagColor[] colors;
    private boolean loaded;

    public FlagImageTexture(byte[] data) {
        super(DEFAULT_FLAG);
        this.colors = FlagColor.fromBytes(data);
    }

    private void upload(NativeImage image) {
        TextureUtil.prepareImage(this.getId(), image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) {
        Minecraft.getInstance().execute(() -> {
            if (!this.loaded) {
                try {
                    super.load(manager);
                } catch (IOException var3x) {
                    LOGGER.warn("Failed to load texture: {}", this.location, var3x);
                }
                this.loaded = true;
            }
        });

        Minecraft.getInstance().execute(() -> {
            NativeImage image = this.loadTexture(colors);
            if (image != null) {
                this.loaded = true;
                if (!RenderSystem.isOnRenderThread()) {
                    RenderSystem.recordRenderCall(() -> this.upload(image));
                } else {
                    this.upload(image);
                }
            }
        });
    }

    private NativeImage loadTexture(FlagColor[] colors) {
        try {
            NativeImage nativeImage = new NativeImage(22, 16, false);
            for (int x = 0; x < 22; x++) {
                for (int y = 0; y < 16; y++) {
                    FlagColor color = colors[x + y * 22];
                    if (color == FlagColor.NONE) {
                        nativeImage.setPixelRGBA(x, y, 0x00000000);
                    } else {
                        nativeImage.setPixelRGBA(x, y, color.color() | 0xFF000000);
                    }
                }
            }
            return nativeImage;
        } catch (Exception e) {
            LOGGER.warn("Failed to load texture: {}", this.location, e);
            return null;
        }
    }
}
