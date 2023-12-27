package earth.terrarium.adastra.client.dimension;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.client.dimension.renderers.ModSkyRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Common implementation of IForgeDimensionSpecialEffects.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ModDimensionSpecialEffects extends DimensionSpecialEffects {
    private final ModSkyRenderer skyRenderer;

    private final BiFunction<Vec3, Float, Vec3> fogColor;
    private final boolean foggy;
    private final int sunriseColor;
    private final int sunriseAngle;

    public ModDimensionSpecialEffects(
        float cloudLevel,
        boolean hasGround,
        SkyType skyType,
        boolean forceBrightLightmap,
        boolean constantAmbientLight,
        BiFunction<Vec3, Float, Vec3> fogColor,
        boolean foggy,
        int sunriseColor,
        int stars,
        int sunriseAngle,
        boolean renderInRain,
        BiFunction<ClientLevel, Float, Float> starBrightness,
        SimpleWeightedRandomList<Integer> starColors,
        List<SkyRenderable> skyRenderables
    ) {
        super(192, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
        this.skyRenderer = new ModSkyRenderer(stars, sunriseAngle, renderInRain, sunriseColor, starBrightness, starColors, skyRenderables);
        this.fogColor = fogColor;
        this.foggy = foggy;
        this.sunriseColor = sunriseColor;
        this.sunriseAngle = sunriseAngle;
    }

    public boolean hasCustomClouds() {
        return true;
    }

    public boolean hasCustomSky() {
        return true;
    }

    public boolean hasCustomWeather() {
        return true;
    }

    /**
     * Renders the clouds of this dimension.
     *
     * @return true to prevent vanilla cloud rendering
     */
    public boolean renderClouds(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, double camX, double camY, double camZ, Matrix4f projectionMatrix) {
        return hasCustomClouds();
    }

    /**
     * Renders the sky of this dimension.
     *
     * @return true to prevent vanilla sky rendering
     */
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        skyRenderer.render(level, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
        return hasCustomSky();
    }

    /**
     * Renders the snow and rain effects of this dimension.
     *
     * @return true to prevent vanilla snow and rain rendering
     */
    public boolean renderSnowAndRain(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return hasCustomWeather();
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float brightness) {
        return this.fogColor.apply(fogColor, brightness);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return foggy;
    }

    @Nullable
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        // Prevent the FogRenderer from rendering the sunrise if the sun isn't setting in the west.
        if (sunriseAngle != 0) return null;
        return getSunriseColor(timeOfDay, partialTicks, sunriseColor);
    }

    @Nullable
    public static float[] getSunriseColor(float timeOfDay, float partialTicks, int sunColor) {
        float timeCos = Mth.cos(timeOfDay * (float) (Math.PI * 2));
        if (timeCos >= -0.4f && timeCos <= 0.4f) {
            float time = timeCos / 0.4f * 0.5f + 0.5f;
            float alpha = 1 - (1 - Mth.sin(time * (float) Math.PI)) * 0.99F;
            alpha *= alpha;
            var rgba = new float[4];
            rgba[0] = time * 0.3f + FastColor.ARGB32.red(sunColor) / 255f * 0.7f;
            rgba[1] = time * time * 0.7f + FastColor.ARGB32.green(sunColor) / 255f * 0.5f;
            rgba[2] = FastColor.ARGB32.blue(sunColor) / 255f * 0.6f;
            rgba[3] = alpha;
            return rgba;
        } else {
            return null;
        }
    }

    public static class Builder {
        private boolean customClouds = false;
        private boolean customSky = false;
        private boolean customWeather = false;

        private float cloudLevel = OverworldEffects.CLOUD_LEVEL;
        private boolean hasGround = true;
        private SkyType skyType = SkyType.NORMAL;
        private boolean forceBrightLightmap = false;
        private boolean constantAmbientLight = false;

        private BiFunction<ClientLevel, Float, Float> starBrightness = (level, partialTick) -> {
            float rainLevel = 1 - level.getRainLevel(partialTick);
            return level.getStarBrightness(partialTick) * rainLevel;
        };

        private BiFunction<Vec3, Float, Vec3> fogColor = (fogColor, brightness) -> fogColor.multiply(
            brightness * 0.94 + 0.06,
            brightness * 0.94 + 0.06,
            brightness * 0.91 + 0.09);

        private boolean foggy = false;

        private int sunriseColor = 0xd85f33;

        private int stars = 1500;
        private int sunriseAngle;
        private boolean renderInRain;
        private SimpleWeightedRandomList<Integer> starColors = SimpleWeightedRandomList.<Integer>builder()
            .add(0xffffffff, 1)
            .build();

        private final List<SkyRenderable> skyRenderables = new ArrayList<>();

        public Builder customClouds() {
            this.customClouds = true;
            return this;
        }

        public Builder customSky() {
            this.customSky = true;
            return skyType(DimensionSpecialEffects.SkyType.NONE);
        }

        public Builder customWeather() {
            this.customWeather = true;
            return this;
        }

        public Builder cloudLevel(float cloudLevel) {
            this.cloudLevel = cloudLevel;
            return this;
        }

        public Builder noGround() {
            this.hasGround = false;
            return this;
        }

        public Builder skyType(SkyType skyType) {
            this.skyType = skyType;
            return this;
        }

        public Builder forceBrightLightmap() {
            this.forceBrightLightmap = true;
            return this;
        }

        public Builder constantAmbientLight() {
            this.constantAmbientLight = true;
            return this;
        }

        public Builder fogColor(BiFunction<Vec3, Float, Vec3> fogColor) {
            this.fogColor = fogColor;
            return this;
        }

        public Builder foggy() {
            this.foggy = true;
            return this;
        }

        public Builder sunriseColor(int color) {
            this.sunriseColor = color;
            return this;
        }

        public Builder stars(int stars) {
            this.stars = stars;
            return this;
        }

        public Builder sunriseAngle(int sunriseAngle) {
            this.sunriseAngle = sunriseAngle;
            return this;
        }

        public Builder renderInRain() {
            this.renderInRain = true;
            return this;
        }

        public Builder starBrightness(BiFunction<ClientLevel, Float, Float> starBrightness) {
            this.starBrightness = starBrightness;
            return this;
        }

        public Builder starColors(SimpleWeightedRandomList<Integer> starColors) {
            this.starColors = starColors;
            return this;
        }

        public Builder addSkyRenderables(SkyRenderable... renderables) {
            Collections.addAll(skyRenderables, renderables);
            return this;
        }

        public ModDimensionSpecialEffects build() {
            return new ModDimensionSpecialEffects(
                cloudLevel,
                hasGround,
                skyType,
                forceBrightLightmap,
                constantAmbientLight,
                fogColor,
                foggy,
                sunriseColor,
                stars,
                sunriseAngle,
                renderInRain,
                starBrightness,
                starColors,
                skyRenderables
            ) {
                @Override
                public boolean hasCustomClouds() {
                    return customClouds;
                }

                @Override
                public boolean hasCustomSky() {
                    return customSky;
                }

                @Override
                public boolean hasCustomWeather() {
                    return customWeather;
                }
            };
        }
    }
}
