package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class VenusSky extends ModSky {

    private static final Identifier RED_SUN_TEXTURE = new ModIdentifier("textures/sky/red_sun.png");

    @Override
    public boolean isFixedStarColour() {
        return false;
    }

    @Override
    public int getSunsetAngle() {
        return 180;
    }

    public VenusSky withMercury() {
        Vec3f euler = new Vec3f(-25.0f, 90.0f, -25.0f);
        this.addToRenderingQueue(EARTH_LIGHT_TEXTURE, false, 2.78f * 2, euler, RenderType.DYNAMIC);
        return (VenusSky) this.addToRenderingQueue(OrbitSky.MERCURY_TEXTURE, true, 2, euler, RenderType.DYNAMIC);
    }

    @Override
    public VenusSky withEarth(float scale) {
        Vec3f euler = new Vec3f(-30.0f, -55.0f, 45.0f);
        this.addToRenderingQueue(EARTH_LIGHT_TEXTURE, false, 2.78f * scale, euler, RenderType.DYNAMIC);
        return (VenusSky) this.addToRenderingQueue(EARTH_TEXTURE, true, scale, euler, RenderType.DYNAMIC);
    }

    @Override
    public VenusSky withSun() {
        return (VenusSky) this.withSun(RED_SUN_TEXTURE, 20, new Vec3f(0.0f, 90.0f, 0.0f));
    }

    @Override
    public VenusSky withVanillaStars() {
        return (VenusSky) super.withVanillaStars();
    }
}