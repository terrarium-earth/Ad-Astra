package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class MarsSky extends ModSky {

    private static final Identifier BLUE_SUN_TEXTURE = new ModIdentifier("textures/sky/blue_sun.png");
    private static final Identifier PHOBOS_TEXTURE = new ModIdentifier("textures/sky/phobos.png");
    private static final Identifier DEIMOS_TEXTURE = new ModIdentifier("textures/sky/deimos.png");

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

    public MarsSky withPhobos() {
        return (MarsSky) this.addToRenderingQueue(PHOBOS_TEXTURE, false, 3.0f, new Vec3f(45.0f, -160.0f, 0.0f), RenderType.DYNAMIC);
    }

    public MarsSky withDeimos() {
        return (MarsSky) this.addToRenderingQueue(DEIMOS_TEXTURE, false, 4.0f, new Vec3f(0.0f, 90.0f, -10.0f), RenderType.DYNAMIC);
    }

    @Override
    public ColourType getColourType() {
        return ColourType.MARS;
    }

    @Override
    public boolean isFixedStarColour() {
        return false;
    }

    @Override
    public MarsSky withEarth(float scale) {
        Vec3f euler = new Vec3f(45.0f, 45.0f, -135.0f);
        this.addToRenderingQueue(EARTH_LIGHT_TEXTURE, false, 2.78f * scale, euler, RenderType.DYNAMIC);
        return (MarsSky) this.addToRenderingQueue(EARTH_TEXTURE, true, scale, euler, RenderType.DYNAMIC);
    }

    @Override
    public MarsSky withSun() {
        return (MarsSky) this.withSun(BLUE_SUN_TEXTURE, 30);
    }

    @Override
    public MarsSky withVanillaStars() {
        return (MarsSky) super.withVanillaStars();
    }
}