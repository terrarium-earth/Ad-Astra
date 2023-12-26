package earth.terrarium.adastra.client.dimension;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.client.dimension.renderers.ModSkyRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LightTexture;
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
    private final BiFunction<Integer, Integer, Boolean> isFoggyAt;


    public ModDimensionSpecialEffects(
        float cloudLevel,
        boolean hasGround,
        SkyType skyType,
        boolean forceBrightLightmap,
        boolean constantAmbientLight,
        BiFunction<Vec3, Float, Vec3> fogColor,
        BiFunction<Integer, Integer, Boolean> isFoggyAt,
        int stars,
        BiFunction<ClientLevel, Float, Float> starBrightness,
        SimpleWeightedRandomList<Integer> starColors,
        List<SkyRenderable> skyRenderables
    ) {
        super(cloudLevel, hasGround, skyType, forceBrightLightmap, constantAmbientLight);
        this.skyRenderer = new ModSkyRenderer(stars, starBrightness, starColors, skyRenderables);
        this.fogColor = fogColor;
        this.isFoggyAt = isFoggyAt;
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
        return this.isFoggyAt.apply(x, y);
    }

    @Nullable
    @Override
    public float[] getSunriseColor(float timeOfDay, float partialTicks) {
        return super.getSunriseColor(timeOfDay, partialTicks);
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

        private BiFunction<Integer, Integer, Boolean> isFoggyAt = (x, y) -> false;

        private int stars = 1500;
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
            return this;
        }

        public Builder customWeather() {
            this.customWeather = true;
            return this;
        }

        public Builder cloudLevel(float cloudLevel) {
            this.cloudLevel = cloudLevel;
            return this;
        }

        public Builder hasGround(boolean hasGround) {
            this.hasGround = hasGround;
            return this;
        }

        public Builder skyType(SkyType skyType) {
            this.skyType = skyType;
            return this;
        }

        public Builder forceBrightLightmap(boolean forceBrightLightmap) {
            this.forceBrightLightmap = forceBrightLightmap;
            return this;
        }

        public Builder constantAmbientLight(boolean constantAmbientLight) {
            this.constantAmbientLight = constantAmbientLight;
            return this;
        }

        public Builder fogColor(BiFunction<Vec3, Float, Vec3> fogColor) {
            this.fogColor = fogColor;
            return this;
        }

        public Builder isFoggyAt(BiFunction<Integer, Integer, Boolean> isFoggyAt) {
            this.isFoggyAt = isFoggyAt;
            return this;
        }

        public Builder stars(int stars) {
            this.stars = stars;
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
                isFoggyAt,
                stars,
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
