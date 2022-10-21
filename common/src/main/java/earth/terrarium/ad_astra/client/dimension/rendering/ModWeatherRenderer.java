package earth.terrarium.ad_astra.client.dimension.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import earth.terrarium.ad_astra.mixin.client.WorldRendererAccessor;
import earth.terrarium.ad_astra.util.ModIdentifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class ModWeatherRenderer {

    private static final Identifier VENUS_RAIN_TEXTURE = new ModIdentifier("textures/sky/venus/rain.png");

    public static void render(ClientWorld world, int ticks, float tickDelta, LightmapTextureManager manager, double cameraX, double cameraY, double cameraZ) {

        MinecraftClient client = MinecraftClient.getInstance();
        WorldRendererAccessor renderer = (WorldRendererAccessor) client.worldRenderer;

        float h = client.world.getRainGradient(tickDelta);
        if (!(h <= 0.0f)) {
            manager.enable();
            int i = MathHelper.floor(cameraX);
            int j = MathHelper.floor(cameraY);
            int k = MathHelper.floor(cameraZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (MinecraftClient.isFancyGraphicsOrBetter()) {
                l = 10;
            }

            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            int m = -1;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int o = k - l; o <= k + l; ++o) {
                for (int p = i - l; p <= i + l; ++p) {
                    int q = (o - k + 16) * 32 + p - i + 16;
                    double r = (double) renderer.getRainSizeX()[q] * 0.5;
                    double s = (double) renderer.getRainSizeZ()[q] * 0.5;
                    mutable.set(p, cameraY, o);
                    Biome biome = world.getBiome(mutable).value();
                    if (biome.getPrecipitation() != Biome.Precipitation.NONE) {
                        int t = world.getTopY(Heightmap.Type.MOTION_BLOCKING, p, o);
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
                            if (biome.doesNotSnow(mutable)) {
                                if (m != 0) {

                                    m = 0;
                                    RenderSystem.setShaderTexture(0, VENUS_RAIN_TEXTURE);
                                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                                }

                                int x = renderer.getTicks() + p * p * 3121 + p * 45238971 + o * o * 418711 + o * 13761 & 31;
                                y = -((float) x + tickDelta) / 32.0f * (3.0f + random.nextFloat());
                                double z = (double) p + 0.5 - cameraX;
                                double aa = (double) o + 0.5 - cameraZ;
                                float ab = (float) Math.sqrt(z * z + aa * aa) / (float) l;
                                ac = ((1.0f - ab * ab) * 0.5f + 0.5f) * h;
                                mutable.set(p, w, o);
                                int ad = WorldRenderer.getLightmapCoordinates(world, mutable);
                                bufferBuilder.vertex((double) p - cameraX - r + 0.5, (double) v - cameraY, (double) o - cameraZ - s + 0.5).uv(0.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
                                bufferBuilder.vertex((double) p - cameraX + r + 0.5, (double) v - cameraY, (double) o - cameraZ + s + 0.5).uv(1.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
                                bufferBuilder.vertex((double) p - cameraX + r + 0.5, (double) u - cameraY, (double) o - cameraZ + s + 0.5).uv(1.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
                                bufferBuilder.vertex((double) p - cameraX - r + 0.5, (double) u - cameraY, (double) o - cameraZ - s + 0.5).uv(0.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
                            }
                        }
                    }
                }
            }

            if (m >= 0) {
                tessellator.draw();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            manager.disable();
        }
    }
}