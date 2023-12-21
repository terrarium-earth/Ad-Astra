package earth.terrarium.adastra.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class OxygenBubbleParticle extends TextureSheetParticle {
    protected OxygenBubbleParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.02f, 0.02f);
        this.quadSize *= this.random.nextFloat() * 0.6f + 0.2f;
        this.xd = xSpeed * 0.2f + (Math.random() * 2.0 - 1.0) * 0.02f;
        this.yd = ySpeed * 0.2f + (Math.random() * 2.0 - 1.0) * 0.02f;
        this.zd = zSpeed * 0.2f + (Math.random() * 2.0 - 1.0) * 0.02f;
        this.lifetime = (int) (20.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
            return;
        }
        this.yd += 0.002;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.85f;
        this.yd *= 0.85f;
        this.zd *= 0.85f;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        float f = ((float) this.age + scaleFactor) / (float) this.lifetime;
        return this.quadSize * (1.0f - f * f * 0.5f);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            OxygenBubbleParticle oxygenBubbleParticle = new OxygenBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            oxygenBubbleParticle.pickSprite(sprites);
            return oxygenBubbleParticle;
        }
    }
}
