package com.github.alexnijjar.beyond_earth.entities.vehicles;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.gui.VehicleScreenHandlerFactory;
import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;
import com.github.alexnijjar.beyond_earth.registry.ModFluids;
import com.github.alexnijjar.beyond_earth.util.CustomInventory;
import com.github.alexnijjar.beyond_earth.util.FluidUtils;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public abstract class VehicleEntity extends Entity {

    protected int clientInterpolationSteps;
    protected double clientX;
    protected double clientY;
    protected double clientZ;
    public double clientYaw;
    public double clientPitch;
    protected double clientXVelocity;
    protected double clientYVelocity;
    protected double clientZVelocity;
    public float previousYaw;

    public final SingleVariantStorage<FluidVariant> inputTank = FluidUtils.createTank(this.getTankSize());
    private final CustomInventory inventory = new CustomInventory(this.getInventorySize());

    protected static final TrackedData<Float> SPEED = DataTracker.registerData(VehicleEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Integer> FLUID_AMOUNT = DataTracker.registerData(VehicleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<String> FLUID_VARIANT = DataTracker.registerData(VehicleEntity.class, TrackedDataHandlerRegistry.STRING);

    public VehicleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(SPEED, 0.0f);
        this.dataTracker.startTracking(FLUID_AMOUNT, 0);
        this.dataTracker.startTracking(FLUID_VARIANT, "beyond_earth:fuel");
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.inventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
        inputTank.variant = FluidVariant.fromNbt(nbt.getCompound("inputFluid"));
        inputTank.amount = nbt.getLong("inputAmount");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("Inventory", this.inventory.toNbtList());
        nbt.put("inputFluid", inputTank.variant.toNbt());
        nbt.putLong("inputAmount", inputTank.amount);
    }

    @Override
    public void tick() {
        super.tick();
        this.previousYaw = this.getYaw();

        this.tryInsertingIntoTank();
        this.checkBlockCollision();
        if (!this.world.isClient) {
            this.dataTracker.set(FLUID_AMOUNT, (int) this.inputTank.amount);
            this.dataTracker.set(FLUID_VARIANT, Registry.FLUID.getId(this.inputTank.variant.getFluid()).toString());
        }


        this.doGravity();
        this.slowDown();
        this.doMovement();
    }

    public void slowDown() {
        this.setSpeed(this.getSpeed() / 1.1f);
        if (this.getSpeed() < 0.001 && this.getSpeed() > -0.001) {
            this.setSpeed(0.0f);
        }
        this.setSpeed(MathHelper.clamp(this.getSpeed(), BeyondEarth.CONFIG.mainConfig.vehicleMinSpeed, BeyondEarth.CONFIG.mainConfig.vehicleMaxSpeed));
    }

    public void doGravity() {
        if (!this.hasNoGravity()) {
            double gravity = 0.8;
            if (this.submergedInWater) {
                gravity *= 0.01;
            }

            float planetGravity = ModUtils.getPlanetGravity(this.world);
            if (this instanceof LanderEntity) {
                gravity = 0.5;
            } else {
                gravity *= planetGravity;
            }

            this.setVelocity(this.getVelocity().add(0.0, BeyondEarth.CONFIG.mainConfig.vehicleGravity * gravity, 0.0));
        }
    }

    public void doMovement() {
        BlockPos velocityAffectingPos = this.getVelocityAffectingPos();
        float slipperiness = this.world.getBlockState(velocityAffectingPos).getBlock().getSlipperiness();
        Vec3d movement = this.applyMovementInput(new Vec3d(this.getPitch(), 0, 1), slipperiness);
        double speedModifier = this.onGround ? slipperiness * 1.0f : slipperiness * 0.70f;
        this.setVelocity(movement.getX() * speedModifier, movement.getY() * 0.98, movement.getZ() * speedModifier);

        this.move(MovementType.SELF, this.getVelocity());
    }

    private float getMovementSpeed(float slipperiness) {
        return this.getSpeed() * (0.21600002f / (slipperiness * slipperiness * slipperiness));
    }

    public Vec3d applyMovementInput(Vec3d movementInput, float slipperiness) {
        this.updateVelocity(this.getMovementSpeed(slipperiness), movementInput);
        this.move(MovementType.SELF, this.getVelocity());
        return this.getVelocity();
    }

    public void syncClient() {
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

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {

        if (passenger instanceof PlayerEntity player) {
            if (!player.isCreative()) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }
        }

        return super.updatePassengerForDismount(passenger);
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
            player.getAbilities().allowFlying = true;
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }

    public void openInventory(PlayerEntity player) {
        openInventory(player, new VehicleScreenHandlerFactory(this));
    }

    public void openInventory(PlayerEntity player, ExtendedScreenHandlerFactory handler) {
        if (!player.world.isClient) {
            if (player.isSneaking()) {
                player.openHandledScreen(handler);
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (amount > 0) {
            if (source.getAttacker() instanceof PlayerEntity player) {
                if (!(player.getVehicle() instanceof VehicleEntity)) {
                    this.drop();
                    return true;
                }
            }
        }
        return false;
    }

    public void drop() {
        if (getDropStack() != null) {
            BlockPos pos = this.getBlockPos();
            ItemStack dropStack = this.getDropStack();

            // Set the fluid and fluid variant in the dropped item.
            ((VehicleItem) dropStack.getItem()).setAmount(dropStack, this.inputTank.amount);
            ((VehicleItem) dropStack.getItem()).setFluid(dropStack, this.inputTank.variant);
            NbtCompound nbt = dropStack.getOrCreateNbt();
            // Set the inventory in the dropped item.
            nbt.put("Inventory", this.inventory.toNbtList());

            world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1);
            this.world.spawnEntity(new ItemEntity(this.world, pos.getX(), pos.getY() + 0.5f, pos.getZ(), dropStack));
        }

        if (!this.world.isClient) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    public void explode(float powerMultiplier) {
        if (!this.world.isClient) {
            world.createExplosion(this, this.getX(), this.getY() + 0.5, this.getZ(), 7.0f * powerMultiplier, ModUtils.worldHasOxygen(this.world), Explosion.DestructionType.DESTROY);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.getVelocity().getY() < BeyondEarth.CONFIG.mainConfig.vehicleFallingExplosionThreshold) {
            this.explode(BeyondEarth.CONFIG.mainConfig.vehicleFallingExplosionMultiplier);
            return true;
        }
        return false;
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
    public boolean collides() {
        return true;
    }

    @Override
    public boolean isCollidable() {
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
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public void tryInsertingIntoTank() {
        if (this.getInventorySize() > 1) {
            if (!this.world.isClient) {
                FluidUtils.insertFluidIntoTank(this.getInventory(), this.inputTank, 0, 1, f -> f.equals(FluidVariant.of(ModFluids.FUEL_STILL)));
            }
        }
    }

    public int getFluidAmount() {
        return this.dataTracker.get(FLUID_AMOUNT);
    }

    public FluidVariant getFluidVariant() {
        return FluidVariant.of(Registry.FLUID.get(new Identifier(this.dataTracker.get(FLUID_VARIANT))));
    }

    public void consumeFuel() {
        if (!this.world.isClient) {
            try (Transaction transaction = Transaction.openOuter()) {
                if (this.inputTank.extract(FluidVariant.of(ModFluids.FUEL_STILL), this.getFuelPerTick(), transaction) > 0) {
                    transaction.commit();
                }
            }
        }
    }
}