package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class MarsSky extends ModSky {

    private static final Identifier BLUE_SUN_TEXTURE = new ModIdentifier("textures/sky/blue_sun.png");
    private static final Identifier PHOBOS_TEXTURE = new ModIdentifier("textures/sky/phobos.png");
    private static final Identifier DEIMOS_TEXTURE = new ModIdentifier("textures/sky/deimos.png");

    public MarsSky withPhobos() {
        return (MarsSky) this.addToRenderingQueue(PHOBOS_TEXTURE, false, 3.0f, new Vec3f(0.0f, -130.0f, 100.0f), RenderType.DYNAMIC);
    }

    public MarsSky withDeimos() {
        return (MarsSky) this.addToRenderingQueue(DEIMOS_TEXTURE, false, 4.0f, new Vec3f(0.0f, -130.0f, 210.0f), RenderType.DYNAMIC);
    }

    @Override
    public MarsSky withEarth(float scale) {
        Vec3f euler = new Vec3f(0.0f, -90.0f, -30.0f);
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
