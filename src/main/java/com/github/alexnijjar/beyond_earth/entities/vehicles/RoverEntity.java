package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RoverEntity extends VehicleEntity {

    public double wheelPitch;

    public double prevWheelPitch;

    public static final long FUEL_PER_TICK = BeyondEarth.CONFIG.rover.fuelPerTick;

    protected static final TrackedData<Float> TURN_SPEED = DataTracker.registerData(RoverEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public RoverEntity(EntityType<?> type, World world) {
        super(type, world);
        this.stepHeight = 1.0f;
    }

    @Override
    public int getInventorySize() {
        return 10;
    }

    @Override
    public long getTankSize() {
        return BeyondEarth.CONFIG.rover.tankBuckets;
    }

    @Override
    public long getFuelPerTick() {
        return FUEL_PER_TICK;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        super.interact(player, hand);
        this.openInventory(player);
        return ActionResult.SUCCESS;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TURN_SPEED, 0.0f);
    }

    @Override
    public double getMountedHeightOffset() {
        super.getMountedHeightOffset();
        return super.getMountedHeightOffset() + 0.60f;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_1_ROVER.getDefaultStack();
    }

    @Override
    public void tick() {
        super.tick();
        this.travel();
        if (BeyondEarth.CONFIG.rover.explodeRoverInLava && this.isSubmergedIn(FluidTags.LAVA)) {
            this.explode(0.35f);
        }
    }

    @Override
    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        super.updatePositionAndAngles(x, y, z, yaw, pitch);
    }

    private void travel() {

        this.prevWheelPitch = wheelPitch;
        this.previousYaw = this.getYaw();

        if (this.getFirstPassenger() instanceof PlayerEntity player) {
            if (this.inputTank.amount > 0) {
                boolean shouldConsumeFuel = false;

                // Player is clicking 'w' to move forward.
                if (ModKeyBindings.forwardKeyDown(player)) {
                    // this.setSpeed((this.getSpeed() + BeyondEarth.CONFIG.rover.forwardSpeed) * ((this.isTouchingWater() || this.isInLava()) ? 0.5f : 1.0f));
                    shouldConsumeFuel = true;
                    this.setSpeed((this.isTouchingWater() || this.isInLava()) ? 0.25f : 1.0f);
                }
                // Player is clicking 's' to move backward.
                if (ModKeyBindings.backKeyDown(player)) {
                    // this.setSpeed((this.getSpeed() - BeyondEarth.CONFIG.rover.backwardSpeed) * ((this.isTouchingWater() || this.isInLava()) ? 0.5f : 1.0f));
                    shouldConsumeFuel = true;
                    this.setSpeed((this.isTouchingWater() || this.isInLava()) ? -0.1f : -0.5f);
                }

                // Player is clicking 'a' to move left.
                if (ModKeyBindings.leftKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() - BeyondEarth.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
                }
                // Player is clicking 'd' to move right.
                if (ModKeyBindings.rightKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() + BeyondEarth.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
                }

                if (shouldConsumeFuel) {
                    this.consumeFuel();
                }
            }
        }

        this.setTurnSpeed(MathHelper.clamp(this.getTurnSpeed(), -BeyondEarth.CONFIG.rover.maxTurnSpeed, BeyondEarth.CONFIG.rover.maxTurnSpeed));
        this.setTurnSpeed(this.getTurnSpeed() * BeyondEarth.CONFIG.rover.deceleration);
        if (this.getTurnSpeed() < 0.1 && this.getTurnSpeed() > -0.1) {
            this.setTurnSpeed(0.0f);
        }

        ModUtils.rotateVehicleYaw(this, this.getYaw() + this.getTurnSpeed());
        for (Entity passenger : this.getPassengerList()) {
            passenger.setYaw(passenger.getYaw() + this.getTurnSpeed());
        }

        // Animate wheels.
        this.wheelPitch += this.getSpeed() * 7;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        this.copyEntityData(passenger);
        this.updatePassengerPosition(passenger, Entity::setPosition);
    }

    public double getPassengerXOffset() {
        return 0.63;
    }

    public double getPassengerZOffset() {
        return -0.37;
    }

    public double getSecondPassengerXOffset() {
        return -0.5;
    }

    public double getSecondPassengerZOffset() {
        return -0.37;
    }

    private void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (!this.hasPassenger(passenger)) {
            return;
        }

        double d = this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset();

        // Keep the passengers in their seats
        if (passenger.equals(this.getFirstPassenger())) {
            double xRotator = Math.cos((this.getYaw() - 35) * ((float) Math.PI / 180)) * 0.55f;
            double zRotator = Math.sin((this.getYaw() - 35) * ((float) Math.PI / 180)) * 0.55f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        } else {
            double xRotator = Math.cos((this.getYaw() + 30) * ((float) Math.PI / 180)) * -0.65f;
            double zRotator = Math.sin((this.getYaw() + 30) * ((float) Math.PI / 180)) * -0.65f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
            Vec3d pos = passenger.getPos();
            if (this.getPassengerList().size() == 0) {
                double xRotator = Math.cos(this.getYaw() * ((float) Math.PI / 180)) * 0.9f;
                double zRotator = Math.sin(this.getYaw() * ((float) Math.PI / 180)) * 0.9f;
                passenger.setVelocity(this.getVelocity());
                return new Vec3d(pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator);
            } else {
                double xRotator = Math.cos(this.getYaw() * ((float) Math.PI / 180)) * -1.0f;
                double zRotator = Math.sin(this.getYaw() * ((float) Math.PI / 180)) * -1.0f;
                passenger.setVelocity(this.getVelocity());
                return new Vec3d(pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator);
            }
    }

    @Override
    public int getMaxPassengers() {
        return 2;
    }

    public float getTurnSpeed() {
        return this.dataTracker.get(TURN_SPEED);
    }

    public void setTurnSpeed(float value) {
        this.dataTracker.set(TURN_SPEED, value);
    }

    // Lock body rotation while still allowing the player to look around (same as boat).
    protected void copyEntityData(Entity entity) {
        entity.setBodyYaw(this.getYaw());
        float deg = MathHelper.wrapDegrees(entity.getYaw() - this.getYaw());
        float lookAroundFreedom = MathHelper.clamp(deg, -105.0f, 105.0f);
        entity.prevYaw += lookAroundFreedom - deg;
        entity.setYaw(entity.getYaw() + lookAroundFreedom - deg);
        entity.setHeadYaw(entity.getYaw());
    }

    @Override
    public void onPassengerLookAround(Entity passenger) {
        this.copyEntityData(passenger);
    }
}