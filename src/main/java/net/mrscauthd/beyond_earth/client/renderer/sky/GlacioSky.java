package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class GlacioSky extends ModSky {

    private static final Identifier BLUE_SUN_TEXTURE = new ModIdentifier("textures/sky/blue_sun.png");
    private static final Identifier VICINUS_TEXTURE = new ModIdentifier("textures/sky/vicinus.png");
    private static final Identifier VICINUS_LIGHT_TEXTURE = new ModIdentifier("textures/sky/vicinus_light.png");

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

    @Override
    public boolean isFixedStarColour() {
        return false;
    }

    public GlacioSky withVicinus() {
        Vec3f euler = new Vec3f(0.0f, -90.0f, -30.0f);
        this.addToRenderingQueue(VICINUS_LIGHT_TEXTURE, false, 48, euler, RenderType.STATIC);
        return (GlacioSky) this.addToRenderingQueue(VICINUS_TEXTURE, true, 16, euler, RenderType.STATIC);
    }

    @Override
    public GlacioSky withSun() {
        return (GlacioSky) this.withSun(BLUE_SUN_TEXTURE, 30);
    }

    @Override
    public GlacioSky withVanillaStars() {
        return (GlacioSky) super.withVanillaStars();
    }
}
