package net.mrscauthd.beyond_earth.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ExplodeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmallFlameParticle extends ExplodeParticle {
    private SmallFlameParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet spriteWithAge) {
        super(world, x, y, z, motionX, motionY, motionZ, spriteWithAge);
        this.gravity = 2.5F;
        this.scale(0.5f);
    }

    public void tick() {
        super.tick();
        this.yd -= 0.004D + 0.04D * (double)this.gravity;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ParticleFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public ParticleFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SmallFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}