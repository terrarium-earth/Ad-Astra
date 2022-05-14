package net.mrscauthd.beyond_earth.entities.vehicles;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.world.World;

public class VehicleEntity extends Entity {

    protected int clientInterpolationSteps;
    protected double clientX;
    protected double clientY;
    protected double clientZ;
    protected double clientYaw;
    protected double clientPitch;
    protected double clientXVelocity;
    protected double clientYVelocity;
    protected double clientZVelocity;

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
            if (this.isTouchingWater()) {
                modifier *= 0.25f;
            }
            this.setVelocity(this.getVelocity().add(0.0, -0.04 * modifier, 0.0));
        }
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

    public ItemStack getDropStack() {
        return null;
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

    public double getClientYaw() {
        return this.clientYaw;
    }

    public double getClientPitch() {
        return this.clientPitch;
    }
}