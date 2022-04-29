package net.mrscauthd.beyond_earth.client.renderer.sky;

import java.util.ArrayList;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class ModSky implements DimensionRenderingRegistry.SkyRenderer {

    protected static final Identifier SUN_TEXTURE = new ModIdentifier("textures/sky/no_a_sun.png");
    protected static final Identifier EARTH_TEXTURE = new ModIdentifier("textures/sky/earth.png");
    protected static final Identifier EARTH_LIGHT_TEXTURE = new ModIdentifier("textures/sky/earth_light.png");

    protected VertexBuffer starsBuffer;
    protected WorldRenderContext context;
    protected MatrixStack matrices;
    protected ClientWorld world;
    protected float tickDelta;
    protected float skyAngle;
    List<Renderable> renderingQueue = new ArrayList<>();
    private int fancyStars;
    private int fastStars;

    @Override
    public void render(WorldRenderContext context) {
        this.updateRenderer(context);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

        if (!SkyUtil.preRender(context, bufferBuilder, getColourType(), getSunsetAngle(), matrices, world, tickDelta)) {
            // Cancel rendering if the player is in fog, i.e. in lava or powdered snow.
            return;
        }

        // Always render stars first.
        if (fastStars > 0) {
            int stars = (this.context.advancedTranslucency() || MinecraftClient.getInstance().options.graphicsMode.equals(GraphicsMode.FANCY) ? this.fancyStars : this.fastStars);
            starsBuffer = SkyUtil.renderStars(context, bufferBuilder, starsBuffer, stars, isFixedStarColour());
        }
        // Render everything in the queue orderly.
        for (Renderable renderable : renderingQueue) {
            Vec3f euler = renderable.euler;
            float scale = renderable.scale;
            switch (renderable.type) {
            case DYNAMIC -> euler = new Vec3f(skyAngle * 360.0f + euler.getX(), euler.getY(), euler.getZ());
            case SCALING -> scale = SkyUtil.getScale();
            case STATIC -> {
            }
            case DEBUG -> {
                // Test things without restarting Minecraft.
                euler = new Vec3f(skyAngle * 360.0f + euler.getX(), euler.getY(), euler.getZ());
            }
            }

            SkyUtil.render(context, bufferBuilder, renderable.texture, euler, scale, renderable.disableBlending);
        }

        SkyUtil.postRender(context, matrices, world, tickDelta);
    }

    public void updateRenderer(WorldRenderContext context) {
        this.context = context;
        this.matrices = context.matrixStack();
        this.tickDelta = context.tickDelta();
        this.world = context.world();
        this.skyAngle = this.world.getSkyAngle(tickDelta);
    }

    public ColourType getColourType() {
        return ColourType.VANILLA;
    }

    public boolean isFixedStarColour() {
        return true;
    }

    public int getSunsetAngle() {
        return 0;
    }

    // Objects are rendered in the order that they are added.
    protected ModSky addToRenderingQueue(Identifier texture, boolean disableBlending, float scale, Vec3f euler, RenderType type) {
        renderingQueue.add(new Renderable(texture, disableBlending, scale, euler, type));
        return this;
    }

    public ModSky withStars() {
        return prepareStars(13000, 6000);
    }

    public ModSky withVanillaStars() {
        return prepareStars(1500, 1500);
    }

    public ModSky withSun() {
        return this.withSun(30);
    }

    public ModSky withSun(int scale) {
        return this.withSun(SUN_TEXTURE, scale);
    }

    protected ModSky withSun(Identifier texture, int scale) {
        return this.withSun(texture, scale, new Vec3f(0.0f, -90.0f, 0.0f));
    }

    protected ModSky withSun(Identifier texture, int scale, Vec3f euler) {
        return this.addToRenderingQueue(texture, false, scale, euler, RenderType.DYNAMIC);
    }

    public ModSky withEarth(float scale) {
        Vec3f euler = new Vec3f(0.0f, -90.0f, -30.0f);
        this.addToRenderingQueue(EARTH_LIGHT_TEXTURE, false, 2.78f * scale, euler, RenderType.STATIC);
        return this.addToRenderingQueue(EARTH_TEXTURE, true, scale, euler, RenderType.STATIC);
    }

    private ModSky prepareStars(int fancyStars, int fastStars) {
        this.fancyStars = fancyStars;
        this.fastStars = fastStars;
        return this;
    }

    // Post-rendering after the Renderable object has been created.
    protected enum RenderType {
        STATIC, // Never moves.
        DYNAMIC, // Moves based on the time of day.
        SCALING, // Scales based on the position away from the player.
        DEBUG // Only for testing while in a debug environment without restarting Minecraft.
    }

    // Changes the colour of the sunset and sunrise.
    protected enum ColourType {
        VANILLA, // Vanilla.
        MARS // Custom pink-ish red hue.
    }

    // Stores information that is needed to render things in the sky.
    protected record Renderable(Identifier texture, boolean disableBlending, float scale, Vec3f euler, RenderType type) {
    }
}