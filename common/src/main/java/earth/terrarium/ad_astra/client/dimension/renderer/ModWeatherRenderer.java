package earth.terrarium.ad_astra.client.dimension.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.mixin.client.LevelRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class ModWeatherRenderer {

    private static final ResourceLocation VENUS_RAIN_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/sky/venus/rain.png");

    public static void render(ClientLevel level, int ticks, float tickDelta, LightTexture manager, double cameraX, double cameraY, double cameraZ) {

        Minecraft minecraft = Minecraft.getInstance();
        LevelRendererAccessor renderer = (LevelRendererAccessor) minecraft.levelRenderer;

        float h = minecraft.level.getRainLevel(tickDelta);
        if (!(h <= 0.0f)) {
            manager.turnOnLightLayer();
            int i = Mth.floor(cameraX);
            int j = Mth.floor(cameraY);
            int k = Mth.floor(cameraZ);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Minecraft.useFancyGraphics()) {
                l = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            int m = -1;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (int o = k - l; o <= k + l; ++o) {
                for (int p = i - l; p <= i + l; ++p) {
                    int q = (o - k + 16) * 32 + p - i + 16;
                    double r = (double) renderer.getRainSizeX()[q] * 0.5;
                    double s = (double) renderer.getRainSizeZ()[q] * 0.5;
                    mutable.set(p, cameraY, o);
                    Biome biome = level.getBiome(mutable).value();
                    if (biome.getPrecipitation() != Biome.Precipitation.NONE) {
                        int t = level.getHeight(Heightmap.Types.MOTION_BLOCKING, p, o);
                        int u = j - l;
                        int v = j + l;
                        if (u < t) {
                            u = t;
                        }

                        if (v < t) {
                            v = t;
                        }

                        int w = Math.max(t, j);

                        if (u != v) {
                            Random random = new Random((long) p * p * 3121 + p * 45238971L ^ (long) o * o * 418711 + o * 13761L);
                            mutable.set(p, u, o);
                            float y;
                            float ac;
                            if (biome.warmEnoughToRain(mutable)) {
                                if (m != 0) {

                                    m = 0;
                                    RenderSystem.setShaderTexture(0, VENUS_RAIN_TEXTURE);
                                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                }

                                int x = renderer.getTicks() + p * p * 3121 + p * 45238971 + o * o * 418711 + o * 13761 & 31;
                                y = -((float) x + tickDelta) / 32.0f * (3.0f + random.nextFloat());
                                double z = (double) p + 0.5 - cameraX;
                                double aa = (double) o + 0.5 - cameraZ;
                                float ab = (float) Math.sqrt(z * z + aa * aa) / (float) l;
                                ac = ((1.0f - ab * ab) * 0.5f + 0.5f) * h;
                                mutable.set(p, w, o);
                                int ad = LevelRenderer.getLightColor(level, mutable);
                                bufferBuilder.vertex((double) p - cameraX - r + 0.5, (double) v - cameraY, (double) o - cameraZ - s + 0.5).uv(0.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).uv2(ad).endVertex();
                                bufferBuilder.vertex((double) p - cameraX + r + 0.5, (double) v - cameraY, (double) o - cameraZ + s + 0.5).uv(1.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).uv2(ad).endVertex();
                                bufferBuilder.vertex((double) p - cameraX + r + 0.5, (double) u - cameraY, (double) o - cameraZ + s + 0.5).uv(1.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).uv2(ad).endVertex();
                                bufferBuilder.vertex((double) p - cameraX - r + 0.5, (double) u - cameraY, (double) o - cameraZ - s + 0.5).uv(0.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).uv2(ad).endVertex();
                            }
                        }
                    }
                }
            }

            if (m >= 0) {
                tessellator.end();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            manager.turnOffLightLayer();
        }
    }
}