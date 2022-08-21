package com.github.alexnijjar.ad_astra.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class OxygenBubbleParticle extends SpriteBillboardParticle {

    OxygenBubbleParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.02f, 0.02f);
        this.scale *= this.random.nextFloat() * 0.6f + 0.2f;
        this.velocityX = velocityX * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.velocityY = velocityY * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.velocityZ = velocityZ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.maxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
            return;
        }
        this.velocityY += 0.002;
        if (this.world != null) {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        }
        this.velocityX *= (double) 0.85f;
        this.velocityY *= (double) 0.85f;
        this.velocityZ *= (double) 0.85f;
    }
    
    @Override
    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        return this.scale * (1.0f - f * f * 0.5f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            OxygenBubbleParticle oxygenBubbleParticle = new OxygenBubbleParticle(clientWorld, d, e, f, g, h, i);
            oxygenBubbleParticle.setSprite(this.spriteProvider);
            return oxygenBubbleParticle;
        }
    }
}

