package net.mrscauthd.boss_tools.particle;

import net.minecraft.client.particle.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.client.world.ClientWorld;

@OnlyIn(Dist.CLIENT)
public class VenusRainParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteSet;
    private float angularVelocity;
    private float angularAcceleration;
    public VenusRainParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.particleScale *= (float) 1;
        this.canCollide = true;
        this.angularVelocity = (float) 0.1;
        this.angularAcceleration = (float) 0.01;
        this.motionX *= (double)0.3F;
        this.motionY = Math.random() * (double)0.2F + (double)0.1F;
        this.motionZ *= (double)0.3F;
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.maxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        this.selectSpriteRandomly(spriteSet);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.motionY -= (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98F;
            this.motionY *= (double)0.98F;
            this.motionZ *= (double)0.98F;
            if (this.onGround) {
                if (Math.random() < 0.5D) {
                    this.setExpired();
                }

                this.motionX *= (double)0.7F;
                this.motionZ *= (double)0.7F;
            }

            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            double d0 = Math.max(this.world.getBlockState(blockpos).getCollisionShape(this.world, blockpos).max(Direction.Axis.Y, this.posX - (double)blockpos.getX(), this.posZ - (double)blockpos.getZ()), (double)this.world.getFluidState(blockpos).getActualHeight(this.world, blockpos));
            if (d0 > 0.0D && this.posY < (double)blockpos.getY() + d0) {
                this.setExpired();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ParticleFactory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public ParticleFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new VenusRainParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
