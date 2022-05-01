package net.mrscauthd.beyond_earth.client.renderer.sky;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.client.resource_pack.SkyRenderer.SkyObject;
import net.mrscauthd.beyond_earth.client.resource_pack.SkyRenderer.StarsRenderer;
import net.mrscauthd.beyond_earth.client.resource_pack.SkyRenderer.SunsetColour;

@Environment(EnvType.CLIENT)
public class ModSky implements DimensionRenderingRegistry.SkyRenderer {

    protected VertexBuffer starsBuffer;
    protected float skyAngle;

    private StarsRenderer starsRenderer;
    private SunsetColour sunsetColour;
    private List<SkyObject> skyObjects;
    private int horizonAngle;

    @Override
    public void render(WorldRenderContext context) {

        this.skyAngle = context.world().getSkyAngle(context.tickDelta());
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        // Cancel rendering if the player is in fog, i.e. in lava or powdered snow.
        if (SkyUtil.isSubmerged(context.camera())) {
            return;
        }

        SkyUtil.preRender(context, bufferBuilder, this.sunsetColour, horizonAngle, context.matrixStack(), context.world(), context.tickDelta());

        // Stars.
        if (this.starsRenderer.fastStars() > 0) {
            int stars = (context.advancedTranslucency() || MinecraftClient.getInstance().options.graphicsMode.equals(GraphicsMode.FANCY) ? this.starsRenderer.fancyStars() : this.starsRenderer.fastStars());
            starsBuffer = SkyUtil.renderStars(context, bufferBuilder, starsBuffer, stars, this.starsRenderer.daylightVisible());
        }

        // Render all sky objects.
        for (SkyObject skyObject : this.skyObjects) {

            float scale = skyObject.scale();
            Vec3f rotation = skyObject.rotation();
            switch (skyObject.renderType()) {
            case STATIC -> {
                // Do not modify the scale or rotation.
            }
            case DYNAMIC -> rotation = new Vec3f(skyAngle * 360.0f + rotation.getX(), rotation.getY(), rotation.getZ());
            case SCALING -> scale = SkyUtil.getScale();
            case DEBUG -> {
                // Test things without restarting Minecraft.
                rotation = new Vec3f(skyAngle * 360.0f + rotation.getX(), rotation.getY(), rotation.getZ());
            }
            }
            SkyUtil.render(context, bufferBuilder, skyObject.texture(), rotation, scale, skyObject.blending());
        }

        SkyUtil.postRender(context, context.matrixStack(), context.world(), context.tickDelta());
    }


    public void setStars(StarsRenderer starsRenderer) {
        this.starsRenderer = starsRenderer;
    }

    public void setSunsetColour(SunsetColour sunsetColour) {
        this.sunsetColour = sunsetColour;
    }

    public void setHorizonAngle(int horizonAngle) {
        this.horizonAngle = horizonAngle;
    }

    public void setSkyObjects(List<SkyObject> skyObjects) {
        this.skyObjects = skyObjects;
    }

    // Custom pink sunset and sunrise.
    public static float[] getMarsColour(float skyAngle) {
        float[] colours = new float[4];

        float cosine = MathHelper.cos(skyAngle * ((float) Math.PI * 2F)) - 0.0F;
        if (cosine >= -0.4F && cosine <= 0.4F) {
            float c = (cosine + 0.0F) / 0.4F * 0.5F + 0.5F;
            float sine = 1.0F - (1.0F - MathHelper.sin(c * (float) Math.PI)) * 0.99F;
            sine *= sine;
            colours[0] = c * 0.3F + 0.7F;
            colours[1] = c * c * 0.6F + 0.4F;
            colours[2] = c * c * 0.0F + 0.4F;
            colours[3] = sine;
            return colours;
        } else {
            return null;
        }
    }
}