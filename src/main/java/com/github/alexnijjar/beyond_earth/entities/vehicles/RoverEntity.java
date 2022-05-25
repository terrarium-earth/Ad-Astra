package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RoverEntity extends VehicleEntity {

    private double turnSpeed;

    public double wheelPitch;
    public double antennaDishYaw;

    public double prevAntennaDishYaw;
    public double prevWheelPitch;

    public RoverEntity(EntityType<?> type, World world) {
        super(type, world);
        this.stepHeight = 1.0f;
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 0.60f;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.ROVER.getDefaultStack();
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.turnSpeed = nbt.getDouble("TurnSpeed");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putDouble("TurnSpeed", this.turnSpeed);
    }

    @Override
    public void tick() {
        super.tick();
        this.travel();
        antennaDishYaw += 0.1;
        if (this.isInLava()) {
            this.explode(0.35f);
        }
    }

    @Override
    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        super.updatePositionAndAngles(x, y, z, yaw, pitch);
    }

    private void travel() {

        this.prevWheelPitch = wheelPitch;

        // Player controls, sadly, only the rider detects the controls, so other clients do not know when the rider is pressing buttons
        // to move. This is problematic for the wheel rotation, so to counteract it, the wheel rotation needs to be calculated based on
        // the difference in position.
        if (this.getFirstPassenger() instanceof PlayerEntity player) {

            // Player is clicking 'w' to move forward.
            if (player.forwardSpeed > 0) {
                this.speed += 0.01 * (this.submergedInWater ? 0.5 : 1.0);
            }
            // Player is clicking 's' to move backward.
            else if (player.forwardSpeed < 0) {
                this.speed -= 0.005 * (this.submergedInWater ? 0.5 : 1.0);
            }

            // Player is clicking 'a' to move left.
            if (player.sidewaysSpeed > 0) {
                this.turnSpeed -= 10 * speed;
                // Slow down for better turns.
                this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
            }
            // Player is clicking 'd' to move right.
            else if (player.sidewaysSpeed < 0) {
                this.turnSpeed += 10 * speed;
                // Slow down for better turns.
                this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
            }
        }

        this.turnSpeed = MathHelper.clamp(this.turnSpeed, -3.0, 3.0);
        this.turnSpeed /= 1.1;
        if (this.turnSpeed < 0.1 && this.turnSpeed > -0.1) {
            this.turnSpeed = 0.0;
        }

        this.setYaw(this.getYaw() + (float) turnSpeed);

        // Animate wheels.
        double wheelSpeed = computeWheelSpeed();
        this.wheelPitch += wheelSpeed * 10.0;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        this.copyEntityData(passenger);
        super.updatePassengerPosition(passenger);
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

    // It needs to be done like this so that it synchs with all clients, and not just the rider.
    protected double computeWheelSpeed() {
        Vec3d difference = new Vec3d(this.prevX, this.prevY, this.prevZ).relativize(this.getPos());
        float yaw = this.getYaw();
        boolean isReversing = false;
        // System.out.println(yaw);

        // Calculate which way the wheels should rotate based on the yaw.
        if (yaw > 0) {
            if (yaw >= 180 && yaw < 270) {
                isReversing = difference.getZ() > 0;
            } else if (yaw >= 270) {
                isReversing = difference.getX() < 0;
            } else {
                isReversing = difference.getX() > 0;
            }
        } else {
            yaw = MathHelper.abs(yaw);
            if (yaw >= 90 && yaw < 180) {
                isReversing = difference.getX() < 0;
            } else if (yaw >= 180 && yaw < 270) {
                isReversing = difference.getZ() > 0;
            } else if (yaw >= 270) {
                isReversing = difference.getX() > 0;
            } else {
                isReversing = difference.getZ() < 0;
            }
        }

        return difference.horizontalLengthSquared() * (isReversing ? -1 : 1);
    }
}