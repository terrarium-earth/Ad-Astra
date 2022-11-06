package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.screen.LargeVehicleMenuProvider;
import earth.terrarium.ad_astra.util.ModKeyBindings;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// /summon ad_astra:tier_1_rover ~ ~1 ~ {Passengers:[{id:"ad_astra:pygro"},{id:"ad_astra:pygro"}]}
public class Rover extends Vehicle {

    protected static final EntityDataAccessor<Float> TURN_SPEED = SynchedEntityData.defineId(Rover.class, EntityDataSerializers.FLOAT);
    public double wheelPitch;
    public double prevWheelPitch;

    public Rover(EntityType<?> type, Level level) {
        super(type, level);
        this.maxUpStep = 1.0f;
    }

    @Override
    public int getInventorySize() {
        return 18;
    }

    @Override
    public void openInventory(Player player) {
        openInventory(player, new LargeVehicleMenuProvider(this));
    }

    @Override
    public long getTankSize() {
        return AdAstra.CONFIG.rover.tankSize;
    }

    @Override
    public long getFuelPerTick() {
        return AdAstra.CONFIG.rover.fuelPerSecond;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        super.interact(player, hand);
        this.openInventory(player);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TURN_SPEED, 0.0f);
    }

    @Override
    public double getPassengersRidingOffset() {
        super.getPassengersRidingOffset();
        return super.getPassengersRidingOffset() + 0.60f;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_1_ROVER.get().getDefaultInstance();
    }

    @Override
    public void tick() {
        super.tick();
        this.travel();
        if (AdAstra.CONFIG.rover.explodeRoverInLava && this.isEyeInFluid(FluidTags.LAVA)) {
            this.explode(0.35f);
        }
    }

    @Override
    public void absMoveTo(double x, double y, double z, float yaw, float pitch) {
        super.absMoveTo(x, y, z, yaw, pitch);
    }

    private void travel() {

        this.prevWheelPitch = wheelPitch;
        this.previousYaw = this.getYRot();

        if (this.getFirstPassenger() instanceof Player player) {
            if (getTankHolder().getFluidAmount() > 0) {
                boolean shouldConsumeFuel = false;

                // Player is clicking 'w' to move forward.
                if (ModKeyBindings.forwardKeyDown(player)) {
                    shouldConsumeFuel = true;
                    this.setSpeed(this.getSpeed() + 0.1f);
                }
                // Player is clicking 's' to move backward.
                if (ModKeyBindings.backKeyDown(player)) {
                    shouldConsumeFuel = true;
                    this.setSpeed(this.getSpeed() + ((this.isInWater() || this.isInLava()) ? -0.01f : -0.05f));
                }

                // Player is clicking 'a' to move left.
                if (ModKeyBindings.leftKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() - AdAstra.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setDeltaMovement(new Vec3(this.getDeltaMovement().x() / 1.1, this.getDeltaMovement().y(), this.getDeltaMovement().z() / 1.1));
                }
                // Player is clicking 'd' to move right.
                if (ModKeyBindings.rightKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() + AdAstra.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setDeltaMovement(new Vec3(this.getDeltaMovement().x() / 1.1, this.getDeltaMovement().y(), this.getDeltaMovement().z() / 1.1));
                }

                if (shouldConsumeFuel) {
                    this.consumeFuel();
                }
            }
        }

        this.setTurnSpeed(Mth.clamp(this.getTurnSpeed(), -AdAstra.CONFIG.rover.maxTurnSpeed, AdAstra.CONFIG.rover.maxTurnSpeed));
        this.setTurnSpeed(this.getTurnSpeed() * AdAstra.CONFIG.rover.deceleration);
        if (this.getTurnSpeed() < 0.1 && this.getTurnSpeed() > -0.1) {
            this.setTurnSpeed(0.0f);
        }

        ModUtils.rotateVehicleYaw(this, this.getYRot() + this.getTurnSpeed());
        for (Entity passenger : this.getPassengers()) {
            passenger.setYRot(passenger.getYRot() + this.getTurnSpeed());
        }

        // Animate wheels.
        this.wheelPitch += this.getSpeed() * 7;
    }

    @Override
    public void positionRider(Entity passenger) {
        this.copyEntityData(passenger);
        this.positionRider(passenger, Entity::setPos);
    }

    private void positionRider(Entity passenger, MoveFunction positionUpdater) {
        if (!this.hasPassenger(passenger)) {
            return;
        }

        double d = this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset();

        // Keep the passengers in their seats
        if (passenger.equals(this.getFirstPassenger())) {
            double xRotator = Math.cos((this.getYRot() - 50) * Math.PI / 180.0) * 0.85f;
            double zRotator = Math.sin((this.getYRot() - 50) * Math.PI / 180.0) * 0.85f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        } else {
            double xRotator = Math.cos((this.getYRot() + 50) * Math.PI / 180.0) * -0.85f;
            double zRotator = Math.sin((this.getYRot() + 50) * Math.PI / 180.0) * -0.85f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        Vec3 pos = passenger.position();
        if (this.getPassengers().size() == 0) {
            double xRotator = Math.cos(this.getYRot() * Math.PI / 180.0) * 0.9f;
            double zRotator = Math.sin(this.getYRot() * Math.PI / 180.0) * 0.9f;
            passenger.setDeltaMovement(this.getDeltaMovement());
            return new Vec3(pos.x() + xRotator, pos.y(), pos.z() + zRotator);
        } else {
            double xRotator = Math.cos(this.getYRot() * Math.PI / 180.0) * -1.0f;
            double zRotator = Math.sin(this.getYRot() * Math.PI / 180.0) * -1.0f;
            passenger.setDeltaMovement(this.getDeltaMovement());
            return new Vec3(pos.x() + xRotator, pos.y(), pos.z() + zRotator);
        }
    }

    @Override
    public int getMaxPassengers() {
        return 2;
    }

    @Override
    public float getMinSpeed() {
        return AdAstra.CONFIG.rover.minSpeed;
    }

    @Override
    public float getMaxSpeed() {
        return AdAstra.CONFIG.rover.maxSpeed;
    }

    public float getTurnSpeed() {
        return this.entityData.get(TURN_SPEED);
    }

    public void setTurnSpeed(float value) {
        this.entityData.set(TURN_SPEED, value);
    }

    // Lock body rotation while still allowing the player to look around (same as boat).
    protected void copyEntityData(Entity entity) {
        entity.setYBodyRot(this.getYRot());
        float deg = Mth.wrapDegrees(entity.getYRot() - this.getYRot());
        float lookAroundFreedom = Mth.clamp(deg, -105.0f, 105.0f);
        entity.yRotO += lookAroundFreedom - deg;
        entity.setYRot(entity.getYRot() + lookAroundFreedom - deg);
        entity.setYHeadRot(entity.getYRot());
    }

    @Override
    public void onPassengerTurned(Entity passenger) {
        this.copyEntityData(passenger);
    }
}