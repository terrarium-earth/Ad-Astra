package com.github.alexnijjar.beyond_earth.entities.vehicles;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.block.Blocks;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class VehicleEntity extends Entity {

    protected int clientInterpolationSteps;
    protected double clientX;
    protected double clientY;
    protected double clientZ;
    public double clientYaw;
    public double clientPitch;
    protected double clientXVelocity;
    protected double clientYVelocity;
    protected double clientZVelocity;

    protected static final TrackedData<Float> SPEED = DataTracker.registerData(VehicleEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public VehicleEntity(EntityType<?> type, World world) {
        super(type, world);
        this.syncClient();
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPEED, 0.0f);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        this.syncClient();
        this.checkBlockCollision();
        this.doGravity();
    }

    public void syncClient() {
        if (this.world.isClient) {
            if (this.clientInterpolationSteps > 0) {
                double d = this.getX() + (this.clientX - this.getX()) / (double) this.clientInterpolationSteps;
                double e = this.getY() + (this.clientY - this.getY()) / (double) this.clientInterpolationSteps;
                double f = this.getZ() + (this.clientZ - this.getZ()) / (double) this.clientInterpolationSteps;
                double g = MathHelper.wrapDegrees(this.clientYaw - (double) this.getYaw());
                this.setYaw(this.getYaw() + (float) g / (float) this.clientInterpolationSteps);
                this.setPitch(this.getPitch() + (float) (this.clientPitch - (double) this.getPitch()) / (float) this.clientInterpolationSteps);
                --this.clientInterpolationSteps;
                this.setPosition(d, e, f);
                this.setRotation(this.getYaw(), this.getPitch());
            } else {
                this.refreshPosition();
                this.setRotation(this.getYaw(), this.getPitch());
            }
            return;
        }
    }

    public float getSpeed() {
        return this.dataTracker.get(SPEED);
    }

    public void setSpeed(float value) {
        this.dataTracker.set(SPEED, value);
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.clientX = x;
        this.clientY = y;
        this.clientZ = z;
        this.clientYaw = yaw;
        this.clientPitch = pitch;
        this.clientInterpolationSteps = interpolationSteps + 2;
        this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    @Override
    public void setVelocityClient(double x, double y, double z) {
        this.clientXVelocity = x;
        this.clientYVelocity = y;
        this.clientZVelocity = z;
        this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    public void setAnglesClient(double pitch, double yaw) {
        this.clientYaw = yaw;
        this.clientPitch = pitch;
    }

    public void doGravity() {
        if (!this.hasNoGravity()) {
            double modifier = 1.0;
            if (this.submergedInWater) {
                modifier *= 0.01;
            }

            float gravity = ModUtils.getPlanetGravity(this.world);
            if (this instanceof LanderEntity) {
                modifier *= MathHelper.clamp(gravity * 2, 0.2f, 1.0f);
            } else {
                modifier *= gravity;
            }

            this.setVelocity(this.getVelocity().add(0.0, -0.04 * modifier, 0.0));
        }

        // Slow down the vehicle.
        
        this.setSpeed(this.getSpeed() / 1.1f);
        if (this.getSpeed() < 0.001 && this.getSpeed() > -0.001) {
            this.setSpeed(0.0f);
        }
        this.setSpeed(MathHelper.clamp(this.getSpeed(), -0.1f, 0.2f));

        
        // Move the vehicle.
        BlockPos velocityAffectingPos = this.getVelocityAffectingPos();
        float slipperiness = this.world.getBlockState(velocityAffectingPos).getBlock().getSlipperiness();
        Vec3d movement = this.applyMovementInput(new Vec3d(this.getPitch(), 0, 1), slipperiness);
        double speedModifier = this.onGround ? slipperiness * 0.80f : 0.80f;
        this.setVelocity(movement.getX() * speedModifier, movement.getY() * 0.98, movement.getZ() * speedModifier);
        
        this.move(MovementType.SELF, this.getVelocity());
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.PASS;
        }
        if (this.hasPassengers()) {
            return ActionResult.PASS;
        }
        if (!this.world.isClient) {
            player.setYaw(this.getYaw());
            player.setPitch(this.getPitch());
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (amount > 0) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                this.drop();
                return true;
            }
        }
        return false;
    }

    public void drop() {
        if (getDropStack() != null) {
            BlockPos pos = this.getBlockPos();
            ItemStack dropStack = this.getDropStack();
            world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1);
            this.world.spawnEntity(new ItemEntity(this.world, pos.getX(), pos.getY() + 0.5f, pos.getZ(), dropStack));
            // TODO: Drop inventory and save nbt in item stack.
        }

        if (!this.world.isClient) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public void explode(float powerMultiplier) {
        if (!this.world.isClient) {
            for (int i = 0; i < 3; i++) {
                world.createExplosion(null, this.getX(), this.getY() + 0.5, this.getZ(), 7.0f * powerMultiplier, ModUtils.worldHasOxygen(this.world), Explosion.DestructionType.DESTROY);
            }
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.getVelocity().getY() < -1.0) {
            this.explode(1);
        }
        return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public ItemStack getDropStack() {
        return null;
    }

    private float getMovementSpeed(float slipperiness) {
        return (float) (this.getSpeed() * (0.21600002f / (slipperiness * slipperiness * slipperiness)));
    }

    public Vec3d applyMovementInput(Vec3d movementInput, float slipperiness) {
        this.updateVelocity(this.getMovementSpeed(slipperiness), movementInput);
        this.move(MovementType.SELF, this.getVelocity());
        Vec3d vec3d = this.getVelocity();
        if (this.horizontalCollision && (this.getBlockStateAtPos().isOf(Blocks.POWDER_SNOW) && PowderSnowBlock.canWalkOnPowderSnow(this))) {
            vec3d = new Vec3d(vec3d.x, 0.2, vec3d.z);
        }
        return vec3d;
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < this.getMaxPassengers();
    }

    public int getMaxPassengers() {
        return 1;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.0;
    }

    public boolean shouldSit() {
        return true;
    }

    public boolean shouldRenderPlayer() {
        return true;
    }

    public boolean doHighFov() {
        return false;
    }

    // Is the rider exposed to the outside air? If so, the rider becomes affected by acid rain.
    public boolean fullyConcealsRider() {
        return false;
    }

    public boolean canRiderTakeFallDamage() {
        return true;
    }

    public boolean renderPlanetBar() {
        return false;
    }
}