package net.mrscauthd.beyond_earth.client.renderer.sky;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.mixin.WorldRendererAccessor;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.WorldSeed;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class BeyondEarthSky {

    private static final Identifier SUN_TEXTURE = new ModIdentifier("textures/sky/no_a_sun.png");
    private static final Identifier EARTH_TEXTURE = new ModIdentifier("textures/sky/earth.png");
    private static final Identifier EARTH_LIGHT_TEXTURE = new ModIdentifier("textures/sky/earth_light.png");
    private static final Identifier MOON_TEXTURE = new ModIdentifier("textures/sky/moon.png");
    private static final Identifier MARS_TEXTURE = new ModIdentifier("textures/sky/mars.png");
    private static final Identifier VENUS_TEXTURE = new ModIdentifier("textures/sky/venus.png");
    private static final Identifier MERCURY_TEXTURE = new ModIdentifier("textures/sky/mercury.png");
    private static final Identifier GLACIO_TEXTURE = new ModIdentifier("textures/sky/glacio.png");

    private VertexBuffer starsBuffer;
    private WorldRenderContext context;
    private MatrixStack matrices;
    private ClientWorld world;
    private float tickDelta;
    private float skyAngle;

    public void updateRenderer(WorldRenderContext context) {
        this.context = context;
        this.matrices = context.matrixStack();
        this.tickDelta = context.tickDelta();
        this.world = context.world();
        this.skyAngle = this.world.getSkyAngle(tickDelta);

        Vec3d skyColor = this.world.getSkyColor(MinecraftClient.getInstance().gameRenderer.getCamera().getPos(), this.tickDelta);
        float f = (float) skyColor.x;
        float g = (float) skyColor.y;
        float h = (float) skyColor.z;
        RenderSystem.setShaderColor(f, g, h, 1.0f);
        Shader shader = RenderSystem.getShader();
        ((WorldRendererAccessor) context.worldRenderer()).getLightSkyBuffer().setShader(this.matrices.peek().getPositionMatrix(), this.context.projectionMatrix(), shader);
    }

    public void renderSun() {
        this.render(SUN_TEXTURE, false, 30.0f, new Vec3f(skyAngle * 360.0f, -90.0f, 0.0f));
    }

    public void renderEarth() {
        this.renderEarthLight();
        this.render(EARTH_TEXTURE, true, 9.0f, new Vec3f(0.0f, -90.0f, -30.0f));
    }

    private void renderEarthLight() {
        this.render(EARTH_LIGHT_TEXTURE, false, 25.0f, new Vec3f(0.0f, -90.0f, -30.0f));
    }

    public void renderEarthOrbit() {
        renderPlanetOrbit(EARTH_TEXTURE);
    }

    public void renderMoonOrbit() {
        renderPlanetOrbit(MOON_TEXTURE);
    }

    public void renderMarsOrbit() {
        renderPlanetOrbit(MARS_TEXTURE);
    }

    public void renderVenusOrbit() {
        renderPlanetOrbit(VENUS_TEXTURE);
    }

    public void renderMercuryOrbit() {
        renderPlanetOrbit(MERCURY_TEXTURE);
    }

    public void renderGlacioOrbit() {
        renderPlanetOrbit(GLACIO_TEXTURE);
    }

    private void renderPlanetOrbit(Identifier texture) {
        // Scale the planet size depending on the distance from the player.
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            float distance = (float) (-3000.0f + player.getY() * 6.0f);
            float scale = 100 * (0.2f - distance / 10000.0f);
            scale = Math.max(scale, 4.0f);
            this.render(texture, true, scale, new Vec3f(180.0f, 180.0f, 0.0f));
        }
    }

    private void render(Identifier texture, boolean disableBlending, float x, Vec3f vector) {

        Matrix4f projectionMatrix = this.context.projectionMatrix();

        RenderSystem.disableTexture();
        Vec3d skyColor = this.world.getSkyColor(MinecraftClient.getInstance().gameRenderer.getCamera().getPos(), tickDelta);
        float f = (float) skyColor.x;
        float g = (float) skyColor.y;
        float h = (float) skyColor.z;
        BackgroundRenderer.clearFog();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        Shader shader = RenderSystem.getShader();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderColouring(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

        this.matrices.push();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Rotation.
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(vector.getY()));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(vector.getZ()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(vector.getX()));

        Matrix4f positionMatrix = this.matrices.peek().getPositionMatrix();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        if (disableBlending) {
            RenderSystem.disableBlend();
        }
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(positionMatrix, -x, (float) 100.0, -x).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, x, (float) 100.0, -x).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, x, (float) 100.0, x).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(positionMatrix, -x, (float) 100.0, x).texture(0.0f, 1.0f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        RenderSystem.enableBlend();

        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();

        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            double d = player.getCameraPosVec(tickDelta).y - this.world.getLevelProperties().getSkyDarknessHeight(this.world);
            if (d < 0.0) {
                matrices.push();
                matrices.translate(0.0, 12.0, 0.0);
                ((WorldRendererAccessor) this.context.worldRenderer()).getDarkSkyBuffer().setShader(matrices.peek().getPositionMatrix(), projectionMatrix, shader);
                matrices.pop();
            }
        }

        RenderSystem.disableTexture();
        if (this.world.getDimensionEffects().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.setShaderColor(f, g, h, 1.0f);
        }
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
    }

    public void renderStars() {

        this.matrices.push();
        this.matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-30.0F));
        this.matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0.0F));
        this.matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(skyAngle * 360.0F));

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starsBuffer != null) {
            this.starsBuffer.close();
        }
        this.starsBuffer = new VertexBuffer();
        renderStars(bufferBuilder);
        bufferBuilder.end();
        this.starsBuffer.upload(bufferBuilder);

        RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
        BackgroundRenderer.clearFog();
        this.starsBuffer.setShader(this.matrices.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionShader());

        matrices.pop();
    }

    private void renderStars(BufferBuilder buffer) {
        Random random = new Random(WorldSeed.getSeed());
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < (this.context.advancedTranslucency() || MinecraftClient.getInstance().options.graphicsMode.equals(GraphicsMode.FANCY) ? 13000 : 6000); ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (!(h < 1.0) || !(h > 0.01)) continue;
            h = 1.0 / Math.sqrt(h);
            double j = (d *= h) * 100.0;
            double k = (e *= h) * 100.0;
            double l = (f *= h) * 100.0;
            double m = Math.atan2(d, f);
            double n = Math.sin(m);
            double o = Math.cos(m);
            double p = Math.atan2(Math.sqrt(d * d + f * f), e);
            double q = Math.sin(p);
            double r = Math.cos(p);
            double s = random.nextDouble() * Math.PI * 2.0;
            double t = Math.sin(s);
            double u = Math.cos(s);
            for (int v = 0; v < 4; ++v) {
                double x = (double) ((v & 2) - 1) * g;
                double y = (double) ((v + 1 & 2) - 1) * g;
                double aa = x * u - y * t;
                double ac = y * u + x * t;
                double ad = aa * q + 0.0 * r;
                double ae = 0.0 * q - aa * r;
                double af = ae * n - ac * o;
                double ah = ac * n + ae * o;
                buffer.vertex(j + af, k + ad, l + ah).next();
            }
        }
    }

    private void renderColouring(BufferBuilder bufferBuilder) {

        float[] fogColours = this.world.getDimensionEffects().getFogColorOverride(this.world.getSkyAngle(tickDelta), tickDelta);
        if (fogColours != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            float sine = MathHelper.sin(this.world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(sine));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(fogColours[0], fogColours[1], fogColours[2], fogColours[3]).next();

            for (int i = 0; i <= 16; ++i) {
                float o = (float) i * ((float) Math.PI * 2) / 16.0f;
                float cosine = MathHelper.cos(o);
                bufferBuilder.vertex(matrix4f, MathHelper.sin(o) * 120.0f, cosine * 120.0f, -cosine * 40.0f * fogColours[3]).color(fogColours[0], fogColours[1], fogColours[2], 0.0f).next();
            }
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            matrices.pop();
        }
    }
}
