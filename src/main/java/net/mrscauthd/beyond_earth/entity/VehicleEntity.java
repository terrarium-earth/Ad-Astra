package net.mrscauthd.beyond_earth.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class VehicleEntity extends Entity {
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;

    private float speed;
    private boolean discardFriction = false;
    public float xxa;
    public float yya;
    public float zza;

    public VehicleEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    /**Enable Interact with the Entity*/
    @Override
    public boolean isPickable() {
        return true;
    }

    /**Interact with the Entity Gui,Spawn Egg...*/
    @Override
    public InteractionResult interact(Player p_19978_, InteractionHand p_19979_) {
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        /**ROT Anim*/
        this.tickLerp();
        this.rotAnim();

        /**Movement Physic*/
        Vec3 vec3 = this.getDeltaMovement();
        double d1 = vec3.x;
        double d3 = vec3.y;
        double d5 = vec3.z;
        if (Math.abs(vec3.x) < 0.003D) {
            d1 = 0.0D;
        }

        if (Math.abs(vec3.y) < 0.003D) {
            d3 = 0.0D;
        }

        if (Math.abs(vec3.z) < 0.003D) {
            d5 = 0.0D;
        }

        this.setDeltaMovement(d1, d3, d5);

        this.xxa *= 0.98F;
        this.zza *= 0.98F;
        this.travel(new Vec3((double)this.xxa, (double)this.yya, (double)this.zza));
    }

    public void rotAnim() {
        while(this.getYRot() - this.yRotO < -180.0F) {
            this.yRotO -= 360.0F;
        }

        while(this.getYRot() - this.yRotO >= 180.0F) {
            this.yRotO += 360.0F;
        }
    }

    @Override
    public void lerpTo(double p_20977_, double p_20978_, double p_20979_, float p_20980_, float p_20981_, int p_20982_, boolean p_20983_) {
        this.lerpX = p_20977_;
        this.lerpY = p_20978_;
        this.lerpZ = p_20979_;
        this.lerpYRot = (double)p_20980_;
        this.lerpXRot = (double)p_20981_;
        this.lerpSteps = p_20982_;
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d2 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d4 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d6 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d6 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d2, d4);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }

    /**Movement Physic*/
    public void travel(Vec3 p_21280_) {
        if (this.isControlledByLocalInstance()) {
            double d0 = 0.08D;

            boolean flag = this.getDeltaMovement().y <= 0.0D;

            if (this.isInWater()) {
                double d8 = this.getY();
                float f5 = this.isSprinting() ? 0.9F : this.getWaterSlowDown();
                float f6 = 0.02F;

                this.moveRelative(f6, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                Vec3 vec36 = this.getDeltaMovement();
                if (this.horizontalCollision) {
                    vec36 = new Vec3(vec36.x, 0.2D, vec36.z);
                }

                this.setDeltaMovement(vec36.multiply((double)f5, (double)0.8F, (double)f5));
                Vec3 vec32 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                this.setDeltaMovement(vec32);
                if (this.horizontalCollision && this.isFree(vec32.x, vec32.y + (double)0.6F - this.getY() + d8, vec32.z)) {
                    this.setDeltaMovement(vec32.x, (double)0.3F, vec32.z);
                }
            } else if (this.isInLava()) {
                double d7 = this.getY();
                this.moveRelative(0.02F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                if (this.getFluidHeight(FluidTags.LAVA) <= this.getFluidJumpThreshold()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
                    Vec3 vec33 = this.getFluidFallingAdjustedMovement(d0, flag, this.getDeltaMovement());
                    this.setDeltaMovement(vec33);
                } else {
                    this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
                }

                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vec34 = this.getDeltaMovement();
                if (this.horizontalCollision && this.isFree(vec34.x, vec34.y + (double)0.6F - this.getY() + d7, vec34.z)) {
                    this.setDeltaMovement(vec34.x, (double)0.3F, vec34.z);
                }
            } else {
                BlockPos blockpos = this.getBlockPosBelowThatAffectsMyMovement();
                float f3 = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFriction(level, this.getBlockPosBelowThatAffectsMyMovement(), this);
                float f4 = this.onGround ? f3 * 0.91F : 0.91F;
                Vec3 vec35 = this.handleRelativeFrictionAndCalculateMovement(p_21280_, f3);
                double d2 = vec35.y;
                if (this.level.isClientSide && !this.level.hasChunkAt(blockpos)) {
                    if (this.getY() > (double)this.level.getMinBuildHeight()) {
                        d2 = -0.1D;
                    } else {
                        d2 = 0.0D;
                    }
                } else if (!this.isNoGravity()) {
                    d2 -= d0;
                }

                if (this.shouldDiscardFriction()) {
                    this.setDeltaMovement(vec35.x, d2, vec35.z);
                } else {
                    this.setDeltaMovement(vec35.x * (double)f4, d2 * (double)0.98F, vec35.z * (double)f4);
                }
            }
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float p_21320_) {
        this.speed = p_21320_;
    }

    protected float getWaterSlowDown() {
        return 0.8F;
    }

    public Vec3 getFluidFallingAdjustedMovement(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
        if (!this.isNoGravity() && !this.isSprinting()) {
            double d0;
            if (p_20996_ && Math.abs(p_20997_.y - 0.005D) >= 0.003D && Math.abs(p_20997_.y - p_20995_ / 16.0D) < 0.003D) {
                d0 = -0.003D;
            } else {
                d0 = p_20997_.y - p_20995_ / 16.0D;
            }

            return new Vec3(p_20997_.x, d0, p_20997_.z);
        } else {
            return p_20997_;
        }
    }

    public float getFrictionInfluencedSpeed(float p_21331_) {
        return this.getSpeed() * (0.21600002F / (p_21331_ * p_21331_ * p_21331_));
    }

    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 p_21075_, float p_21076_) {
        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision) && (this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            vec3 = new Vec3(vec3.x, 0.2D, vec3.z);
        }

        return vec3;
    }

    public boolean shouldDiscardFriction() {
        return this.discardFriction;
    }

    public void setDiscardFriction(boolean p_147245_) {
        this.discardFriction = p_147245_;
    }
}
