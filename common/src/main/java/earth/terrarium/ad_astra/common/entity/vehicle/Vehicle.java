package earth.terrarium.ad_astra.common.entity.vehicle;

import earth.terrarium.ad_astra.common.item.vehicle.VehicleItem;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.CustomInventory;
import earth.terrarium.ad_astra.common.util.FluidUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import earth.terrarium.ad_astra.common.config.VehiclesConfig;
import earth.terrarium.ad_astra.common.screen.VehicleScreenMenuProvider;
import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class Vehicle extends Entity implements Updatable {

    protected static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(Vehicle.class, EntityDataSerializers.FLOAT);
    private final SimpleUpdatingFluidContainer tank = new SimpleUpdatingFluidContainer(this, getTankSize(), 1, (amount, fluid) -> true);
    private final CustomInventory inventory = new CustomInventory(this.getInventorySize());
    public double clientYaw;
    public double clientPitch;
    public float previousYaw;
    protected double clientX;
    protected double clientY;
    protected double clientZ;
    protected double clientXVelocity;
    protected double clientYVelocity;
    protected double clientZVelocity;
    private int clientInterpolationSteps;

    public Vehicle(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPEED, 0.0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.inventory.fromTag(nbt.getList("Inventory", Tag.TAG_COMPOUND));
        getTankHolder().deserialize(nbt.getCompound("InputFluid"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.put("Inventory", this.inventory.createTag());
        nbt.put("InputFluid", getTankHolder().serialize());
    }

    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.clientX = x;
        this.clientY = y;
        this.clientZ = z;
        this.clientYaw = yaw;
        this.clientPitch = pitch;
        this.clientInterpolationSteps = 10;
        this.setDeltaMovement(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    private void updatePositionAndRotation() {
        if (this.isControlledByLocalInstance()) {
            this.clientInterpolationSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.clientInterpolationSteps <= 0) {
            return;
        }
        double d = this.getX() + (this.clientX - this.getX()) / (double) this.clientInterpolationSteps;
        double e = this.getY() + (this.clientY - this.getY()) / (double) this.clientInterpolationSteps;
        double f = this.getZ() + (this.clientZ - this.getZ()) / (double) this.clientInterpolationSteps;
        double g = Mth.wrapDegrees(this.clientYaw - (double) this.getYRot());
        this.setYRot(this.getYRot() + (float) g / (float) this.clientInterpolationSteps);
        this.setXRot(this.getXRot() + (float) (this.clientPitch - (double) this.getXRot()) / (float) this.clientInterpolationSteps);
        --this.clientInterpolationSteps;
        this.setPos(d, e, f);
        this.setRot(this.getYRot(), this.getXRot());
    }

    @Override
    public void lerpMotion(double x, double y, double z) {
        this.clientXVelocity = x;
        this.clientYVelocity = y;
        this.clientZVelocity = z;
        this.setDeltaMovement(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
    }

    @Override
    public void tick() {
        this.previousYaw = this.getYRot();

        super.tick();
        this.updatePositionAndRotation();
        this.doMovement();
        this.slowDown();
        this.doGravity();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.checkInsideBlocks();

        this.tryInsertingIntoTank();
    }

    // Sets the velocity based on the current speed and the current direction
    public void doMovement() {
        this.setXRot(0);
        Vec3 movement = this.calculateViewVector(this.getXRot(), this.getYRot());

        // Save the current y velocity so we can use it later
        double yVelocity = this.getDeltaMovement().y();

        this.setDeltaMovement(this.getDeltaMovement().add(movement.x(), 0.0, movement.z()).scale(this.getSpeed()));

        // Set the y velocity back to the original value, as it was modified by the movement
        this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), yVelocity, this.getDeltaMovement().z()));
    }

    // Slow down the vehicle until a full stop is reached
    public void slowDown() {
        this.setSpeed(this.getSpeed() / 1.05f);
        if (this.getSpeed() < 0.001 && this.getSpeed() > -0.001) {
            this.setSpeed(0.0f);
        }
        this.setSpeed(Mth.clamp(this.getSpeed(), this.getMinSpeed(), this.getMaxSpeed()));
    }

    public float getMinSpeed() {
        return -0.2f;
    }

    public float getMaxSpeed() {
        return 0.4f;
    }

    // Apply gravity to the vehicle.
    @SuppressWarnings("deprecation")
    public void doGravity() {

        if (!level.hasChunkAt(this.blockPosition())) {
            return;
        }

        if (!this.isNoGravity()) {
            // Slow down the gravity while in water
            if (this.isInWater()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.0001, 0));
            } else {
                this.setDeltaMovement(this.getDeltaMovement().add(0, -0.03, 0));
            }
            if (this.getDeltaMovement().y() < VehiclesConfig.gravity) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x(), VehiclesConfig.gravity, this.getDeltaMovement().z()));
            }
        }
    }

    public float getSpeed() {
        return this.entityData.get(SPEED);
    }

    public void setSpeed(float value) {
        this.entityData.set(SPEED, value);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        }
        if (!this.level.isClientSide) {
            if (this.getPassengers().size() > this.getMaxPassengers()) {
                return InteractionResult.PASS;
            }
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    public void openInventory(Player player) {
        openInventory(player, new VehicleScreenMenuProvider(this));
    }

    public void openInventory(Player player, ExtraDataMenuProvider handler) {
        if (!player.level.isClientSide) {
            if (player.isShiftKeyDown()) {
                MenuHooks.openMenu((ServerPlayer) player, handler);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (amount > 0) {
            if (source.getEntity() instanceof Player player) {
                if (!(player.getVehicle() instanceof Vehicle)) {
                    this.drop();
                    return true;
                }
            }
        }
        return false;
    }

    public void drop() {
        if (getDropStack() != null && this.level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            BlockPos pos = this.blockPosition();
            ItemStackHolder dropStack = new ItemStackHolder(this.getDropStack());

            // Set the fluid and fluid type in the dropped item.

            ((VehicleItem) dropStack.getStack().getItem()).insert(dropStack, this.getTankHolder());
            CompoundTag nbt = dropStack.getStack().getOrCreateTag();
            // Set the inventory in the dropped item.
            nbt.put("Inventory", this.inventory.createTag());

            level.playSound(null, pos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS, 1, 1);
            this.level.addFreshEntity(new ItemEntity(this.level, pos.getX(), pos.getY() + 0.5f, pos.getZ(), dropStack.getStack()));
        }

        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    public void explode(float powerMultiplier) {
        if (!this.level.isClientSide) {
            level.explode(this, this.getX(), this.getY() + 0.5, this.getZ(), 7.0f * powerMultiplier, OxygenUtils.levelHasOxygen(this.level), Explosion.BlockInteraction.DESTROY);
        }
        this.discard();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.getDeltaMovement().y() < VehiclesConfig.fallingExplosionThreshold) {
            if (this.isOnGround()) {
                this.explode(VehiclesConfig.fallingExplosionMultiplier);
                return true;
            }
        }
        return false;
    }

    public ItemStack getDropStack() {
        return null;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    public int getMaxPassengers() {
        return 1;
    }

    @Override
    public double getPassengersRidingOffset() {
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

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public long getTankSize() {
        return 0;
    }

    public long getFuelPerTick() {
        return 0;
    }

    public CustomInventory getInventory() {
        return this.inventory;
    }

    public abstract int getInventorySize();

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @SuppressWarnings("deprecation")
    public void tryInsertingIntoTank() {
        if (this.getInventorySize() > 1 && !this.getInventory().getItem(0).isEmpty()) {
            if (!this.level.isClientSide) {
                FluidUtils.insertItemFluidToTank(this.tank, this.getInventory(), 0, 1, 0, f -> f.is(ModTags.FUELS));
                FluidUtils.extractTankFluidToItem(this.tank, this.getInventory(), 0, 1, 0, f -> true);
            }
        }
    }

    public SimpleUpdatingFluidContainer getTank() {
        return this.tank;
    }

    public FluidHolder getTankHolder() {
        return tank.getFluids().get(0);
    }

    public long getTankAmount() {
        return getTankHolder().getFluidAmount();
    }

    public Fluid getTankFluid() {
        return getTankHolder().getFluid();
    }

    public void consumeFuel() {
        if (this.level.getGameTime() % 20 == 0) {
            getTank().extractFluid(FluidHooks.newFluidHolder(getTankFluid(), this.getFuelPerTick(), null), false);
        }
    }

    @Override
    public void update() {
    }
}