package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class OrbitSky extends ModSky {

    private static final Identifier MOON_TEXTURE = new ModIdentifier("textures/sky/moon.png");
    private static final Identifier MARS_TEXTURE = new ModIdentifier("textures/sky/mars.png");
    private static final Identifier VENUS_TEXTURE = new ModIdentifier("textures/sky/venus.png");
    private static final Identifier MERCURY_TEXTURE = new ModIdentifier("textures/sky/mercury.png");
    private static final Identifier GLACIO_TEXTURE = new ModIdentifier("textures/sky/glacio.png");

    @Override
    public void render(WorldRenderContext context) {
        super.render(context);
    }

    protected OrbitSky withPlanetOrbit(Identifier texture) {
        // Scale the planet size depending on the distance from the player.
        this.addToRenderingQueue(texture, true, 0.0f, new Vec3f(180.0f, 180.0f, 0.0f), RenderType.SCALING);
        return this;
    }

    public OrbitSky withEarthOrbit() {
        return this.withPlanetOrbit(EARTH_TEXTURE);
    }

    public OrbitSky withMoonOrbit() {
        return this.withPlanetOrbit(MOON_TEXTURE);
    }

    public OrbitSky withMarsOrbit() {
        return this.withPlanetOrbit(MARS_TEXTURE);
    }

    public OrbitSky withVenusOrbit() {
        return this.withPlanetOrbit(VENUS_TEXTURE);
    }

    public OrbitSky withMercuryOrbit() {
        return this.withPlanetOrbit(MERCURY_TEXTURE);
    }

    public OrbitSky withGlacioOrbit() {
        return this.withPlanetOrbit(GLACIO_TEXTURE);
    }

    @Override
    public OrbitSky withSun() {
        return (OrbitSky) this.withSun(SUN_TEXTURE, 30);
    }

    @Override
    public OrbitSky withStars() {
        return (OrbitSky) super.withStars();
    }
}