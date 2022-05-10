package net.mrscauthd.beyond_earth.entities.vehicles;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VehicleEntity extends Entity {

    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private int interpolationSteps;
    
    private ItemStack dropStack = null;

    public VehicleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

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

        this.tickLerp();

        Vec3d vector = this.getVelocity();
        double x = vector.x;
        double y = vector.y;
        double z = vector.z;
        if (Math.abs(vector.x) < 0.003D) {
            x = 0.0;
        }

        if (Math.abs(vector.y) < 0.003D) {
            y = 0.0;
        }

        if (Math.abs(vector.z) < 0.003D) {
            z = 0.0;
        }

        this.setVelocity(x, y, z);

        this.checkBlockCollision();
    }

    private void tickLerp() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.interpolationSteps = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }
        if (this.interpolationSteps <= 0) {
            return;
        }
        double d = this.getX() + (this.x - this.getX()) / (double) this.interpolationSteps;
        double e = this.getY() + (this.y - this.getY()) / (double) this.interpolationSteps;
        double f = this.getZ() + (this.z - this.getZ()) / (double) this.interpolationSteps;
        double g = MathHelper.wrapDegrees(this.yaw - (double) this.getYaw());
        this.setYaw(this.getYaw() + (float) g / (float) this.interpolationSteps);
        this.setPitch(this.getPitch() + (float) (this.pitch - (double) this.getPitch()) / (float) this.interpolationSteps);
        --this.interpolationSteps;
        this.setPosition(d, e, f);
        this.setRotation(this.getYaw(), this.getPitch());
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.interpolationSteps = interpolationSteps;
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
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            if (player.isSneaking()) {
                this.drop();
                return true;
            }
        }
        return false;
    }

    public void drop() {
        if (getDropStack() != null) {
            Vec3d pos = this.getPos();
            ItemStack dropStack = this.getDropStack();
            this.world.spawnEntity(new ItemEntity(this.world, pos.x, pos.y + 0.5f, pos.z, dropStack));
            // TODO: Drop inventory.
        }

        if (!this.world.isClient) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public ItemStack getDropStack() {
        return this.dropStack;
    }

    public final void setDropStack(ItemStack dropStack) {
        this.dropStack = dropStack;
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

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        return super.updatePassengerForDismount(passenger);
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

    public double getLocalYaw() {
        return this.yaw;
    }

    public double getLocalPitch() {
        return this.pitch;
    }
}