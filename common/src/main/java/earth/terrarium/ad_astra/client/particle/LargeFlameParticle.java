package earth.terrarium.ad_astra.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class LargeFlameParticle extends ExplodeParticle {

    protected LargeFlameParticle(ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(level, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.gravity = 2.5f;
    }

    @Override
    public void tick() {
        super.tick();
        this.yd -= 0.004 + 0.04 * this.gravity;
    }

    @Environment(value = EnvType.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Provider(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            return new LargeFlameParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
