package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

import java.util.ArrayList;
import java.util.List;

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

    private int fancyStars;
    private int fastStars;

    List<Renderable> renderingQueue = new ArrayList<>();

    @Override
    public void render(WorldRenderContext context) {
        this.updateRenderer(context);
        // Always render stars first.
        if (fastStars > 0) {
            starsBuffer = SkyUtil.renderStars(starsBuffer, fancyStars, fastStars, matrices, skyAngle, context.advancedTranslucency());
        }
        // Render everything in the queue in an orderly fashion.
        for (Renderable renderable : renderingQueue) {
            Vec3f euler = renderable.euler;
            float scale = renderable.scale;
            switch (renderable.type) {
                case DYNAMIC -> euler = new Vec3f(skyAngle * 360, renderable.euler.getY(), renderable.euler.getZ());
                case SCALING -> scale = SkyUtil.getScale();
            }

            SkyUtil.render(renderable.texture, renderable.disableBlending, scale, euler, matrices, world, tickDelta, context);
        }
    }

    public void updateRenderer(WorldRenderContext context) {
        this.context = context;
        this.matrices = context.matrixStack();
        this.tickDelta = context.tickDelta();
        this.world = context.world();
        this.skyAngle = this.world.getSkyAngle(tickDelta);
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
        return this.addToRenderingQueue(texture, false, scale, new Vec3f(0.0f, -90.0f, 0.0f), RenderType.DYNAMIC);
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

    /* Stores information that is needed to render things in the sky. */
    protected record Renderable(Identifier texture, boolean disableBlending, float scale, Vec3f euler,
                                RenderType type) {
    }

    /* Post-rendering after the Renderable object has been created. */
    protected enum RenderType {
        STATIC, /* Never moves. */
        DYNAMIC, /* Moves based on the time of day. */
        SCALING /* Scales based on the position away from the player. */
    }
}

