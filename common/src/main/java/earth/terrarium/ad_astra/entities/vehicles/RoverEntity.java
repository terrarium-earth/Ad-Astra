package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.screen.LargeVehicleScreenHandlerFactory;
import earth.terrarium.ad_astra.util.ModKeyBindings;
import earth.terrarium.ad_astra.util.ModUtils;
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

// /summon ad_astra:tier_1_rover ~ ~1 ~ {Passengers:[{id:"ad_astra:pygro"},{id:"ad_astra:pygro"}]}
public class RoverEntity extends VehicleEntity {

    protected static final TrackedData<Float> TURN_SPEED = DataTracker.registerData(RoverEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public double wheelPitch;
    public double prevWheelPitch;

    public RoverEntity(EntityType<?> type, World world) {
        super(type, world);
        this.stepHeight = 1.0f;
    }

    @Override
    public int getInventorySize() {
        return 18;
    }

    @Override
    public void openInventory(PlayerEntity player) {
        openInventory(player, new LargeVehicleScreenHandlerFactory(this));
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
        return ModItems.TIER_1_ROVER.get().getDefaultStack();
    }

    @Override
    public void tick() {
        super.tick();
        this.travel();
        if (AdAstra.CONFIG.rover.explodeRoverInLava && this.isSubmergedIn(FluidTags.LAVA)) {
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
                    this.setSpeed(this.getSpeed() + ((this.isTouchingWater() || this.isInLava()) ? -0.01f : -0.05f));
                }

                // Player is clicking 'a' to move left.
                if (ModKeyBindings.leftKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() - AdAstra.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
                }
                // Player is clicking 'd' to move right.
                if (ModKeyBindings.rightKeyDown(player)) {
                    this.setTurnSpeed(this.getTurnSpeed() + AdAstra.CONFIG.rover.turnSpeed * this.getSpeed());
                    // Slow down for better turns.
                    this.setVelocity(new Vec3d(this.getVelocity().getX() / 1.1, this.getVelocity().getY(), this.getVelocity().getZ() / 1.1));
                }

                if (shouldConsumeFuel) {
                    this.consumeFuel();
                }
            }
        }

        this.setTurnSpeed(MathHelper.clamp(this.getTurnSpeed(), -AdAstra.CONFIG.rover.maxTurnSpeed, AdAstra.CONFIG.rover.maxTurnSpeed));
        this.setTurnSpeed(this.getTurnSpeed() * AdAstra.CONFIG.rover.deceleration);
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

    private void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (!this.hasPassenger(passenger)) {
            return;
        }

        double d = this.getY() + this.getMountedHeightOffset() + passenger.getHeightOffset();

        // Keep the passengers in their seats
        if (passenger.equals(this.getFirstPassenger())) {
            double xRotator = Math.cos((this.getYaw() - 50) * Math.PI / 180.0) * 0.85f;
            double zRotator = Math.sin((this.getYaw() - 50) * Math.PI / 180.0) * 0.85f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        } else {
            double xRotator = Math.cos((this.getYaw() + 50) * Math.PI / 180.0) * -0.85f;
            double zRotator = Math.sin((this.getYaw() + 50) * Math.PI / 180.0) * -0.85f;
            positionUpdater.accept(passenger, this.getX() + xRotator, d, this.getZ() + zRotator);
        }
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d pos = passenger.getPos();
        if (this.getPassengerList().size() == 0) {
            double xRotator = Math.cos(this.getYaw() * Math.PI / 180.0) * 0.9f;
            double zRotator = Math.sin(this.getYaw() * Math.PI / 180.0) * 0.9f;
            passenger.setVelocity(this.getVelocity());
            return new Vec3d(pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator);
        } else {
            double xRotator = Math.cos(this.getYaw() * Math.PI / 180.0) * -1.0f;
            double zRotator = Math.sin(this.getYaw() * Math.PI / 180.0) * -1.0f;
            passenger.setVelocity(this.getVelocity());
            return new Vec3d(pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator);
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