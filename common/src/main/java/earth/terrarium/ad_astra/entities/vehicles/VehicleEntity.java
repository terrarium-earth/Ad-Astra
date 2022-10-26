package earth.terrarium.ad_astra.entities.vehicles;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.screen.VehicleScreenHandlerFactory;
import earth.terrarium.ad_astra.util.CustomInventory;
import earth.terrarium.ad_astra.util.FluidUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.menu.ExtraDataMenuProvider;
import earth.terrarium.botarium.api.menu.MenuHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public abstract class VehicleEntity extends Entity implements Updatable {

	protected double clientX;
	protected double clientY;
	protected double clientZ;
	public double clientYaw;
	public double clientPitch;
	private int clientInterpolationSteps;

	protected double clientXVelocity;
	protected double clientYVelocity;
	protected double clientZVelocity;

	public float previousYaw;

	private final SimpleUpdatingFluidContainer tank = new SimpleUpdatingFluidContainer(this, getTankSize(), 1, (amount, fluid) -> true);
	private final CustomInventory inventory = new CustomInventory(this.getInventorySize());

	protected static final TrackedData<Float> SPEED = DataTracker.registerData(VehicleEntity.class, TrackedDataHandlerRegistry.FLOAT);

	public VehicleEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void initDataTracker() {
		this.dataTracker.startTracking(SPEED, 0.0f);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.inventory.readNbtList(nbt.getList("inventory", NbtElement.COMPOUND_TYPE));
		getTankHolder().deserialize(nbt.getCompound("inputFluid"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.put("inventory", this.inventory.toNbtList());
		nbt.put("inputFluid", getTankHolder().serialize());
	}

	@Override
	public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
		this.clientX = x;
		this.clientY = y;
		this.clientZ = z;
		this.clientYaw = yaw;
		this.clientPitch = pitch;
		this.clientInterpolationSteps = 10;
		this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
	}

	private void updatePositionAndRotation() {
		if (this.isLogicalSideForUpdatingMovement()) {
			this.clientInterpolationSteps = 0;
			this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
		}
		if (this.clientInterpolationSteps <= 0) {
			return;
		}
		double d = this.getX() + (this.clientX - this.getX()) / (double) this.clientInterpolationSteps;
		double e = this.getY() + (this.clientY - this.getY()) / (double) this.clientInterpolationSteps;
		double f = this.getZ() + (this.clientZ - this.getZ()) / (double) this.clientInterpolationSteps;
		double g = MathHelper.wrapDegrees(this.clientYaw - (double) this.getYaw());
		this.setYaw(this.getYaw() + (float) g / (float) this.clientInterpolationSteps);
		this.setPitch(this.getPitch() + (float) (this.clientPitch - (double) this.getPitch()) / (float) this.clientInterpolationSteps);
		--this.clientInterpolationSteps;
		this.setPosition(d, e, f);
		this.setRotation(this.getYaw(), this.getPitch());
	}

	@Override
	public void setVelocityClient(double x, double y, double z) {
		this.clientXVelocity = x;
		this.clientYVelocity = y;
		this.clientZVelocity = z;
		this.setVelocity(this.clientXVelocity, this.clientYVelocity, this.clientZVelocity);
	}

	@Override
	public void tick() {
		this.previousYaw = this.getYaw();

		super.tick();
		this.updatePositionAndRotation();
		this.doMovement();
		this.slowDown();
		this.doGravity();
		this.move(MovementType.SELF, this.getVelocity());
		this.checkBlockCollision();

		this.tryInsertingIntoTank();
	}

	// Sets the velocity based on the current speed and the current direction
	public void doMovement() {
		this.setPitch(0);
		Vec3d movement = this.getRotationVector(this.getPitch(), this.getYaw());

		// Save the current y velocity so we can use it later
		double yVelocity = this.getVelocity().getY();

		this.setVelocity(this.getVelocity().add(movement.getX(), 0.0, movement.getZ()).multiply(this.getSpeed()));

		// Set the y velocity back to the original value, as it was modified by the movement
		this.setVelocity(new Vec3d(this.getVelocity().getX(), yVelocity, this.getVelocity().getZ()));
	}

	// Slow down the vehicle until a full stop is reached
	public void slowDown() {
		this.setSpeed(this.getSpeed() / 1.05f);
		if (this.getSpeed() < 0.001 && this.getSpeed() > -0.001) {
			this.setSpeed(0.0f);
		}
		this.setSpeed(MathHelper.clamp(this.getSpeed(), this.getMinSpeed(), this.getMaxSpeed()));
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

		if (!world.isChunkLoaded(this.getBlockPos())) {
			return;
		}

		if (!this.hasNoGravity()) {
			// Slow down the gravity while in water
			if (this.isTouchingWater()) {
				this.setVelocity(this.getVelocity().add(0, -0.0001, 0));
			} else {
				this.setVelocity(this.getVelocity().add(0, -0.03, 0));
			}
			if (this.getVelocity().getY() < AdAstra.CONFIG.vehicles.gravity) {
				this.setVelocity(new Vec3d(this.getVelocity().getX(), AdAstra.CONFIG.vehicles.gravity, this.getVelocity().getZ()));
			}
		}
	}

	public float getSpeed() {
		return this.dataTracker.get(SPEED);
	}

	public void setSpeed(float value) {
		this.dataTracker.set(SPEED, value);
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		if (player.shouldCancelInteraction()) {
			return ActionResult.PASS;
		}
		if (!this.world.isClient) {
			if (this.getPassengerList().size() > this.getMaxPassengers()) {
				return ActionResult.PASS;
			}
			player.setYaw(this.getYaw());
			player.setPitch(this.getPitch());
			return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
		}
		return ActionResult.SUCCESS;
	}

	public void openInventory(PlayerEntity player) {
		openInventory(player, new VehicleScreenHandlerFactory(this));
	}

	public void openInventory(PlayerEntity player, ExtraDataMenuProvider handler) {
		if (!player.world.isClient) {
			if (player.isSneaking()) {
				MenuHooks.openMenu((ServerPlayerEntity) player, handler);
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
		if (getDropStack() != null && this.world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
			BlockPos pos = this.getBlockPos();
			ItemStack dropStack = this.getDropStack();

			// Set the fluid and fluid type in the dropped item.
			((VehicleItem) dropStack.getItem()).setFluid(dropStack, getTankHolder());
			NbtCompound nbt = dropStack.getOrCreateNbt();
			// Set the inventory in the dropped item.
			nbt.put("inventory", this.inventory.toNbtList());

			world.playSound(null, pos, SoundEvents.BLOCK_NETHERITE_BLOCK_BREAK, SoundCategory.BLOCKS, 1, 1);
			this.world.spawnEntity(new ItemEntity(this.world, pos.getX(), pos.getY() + 0.5f, pos.getZ(), dropStack));
		}

		if (!this.world.isClient) {
			this.discard();
		}
	}

	public void explode(float powerMultiplier) {
		if (!this.world.isClient) {
			world.createExplosion(this, this.getX(), this.getY() + 0.5, this.getZ(), 7.0f * powerMultiplier, OxygenUtils.worldHasOxygen(this.world), Explosion.DestructionType.DESTROY);
		}
		this.discard();
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		if (this.getVelocity().getY() < AdAstra.CONFIG.vehicles.fallingExplosionThreshold) {
			if (this.isOnGround()) {
				this.explode(AdAstra.CONFIG.vehicles.fallingExplosionMultiplier);
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

	@SuppressWarnings("deprecation")
	public void tryInsertingIntoTank() {
		if (this.getInventorySize() > 1) {
			if (!this.world.isClient) {
				FluidUtils.insertFluidToContainerFromItem(this.getInventory().getStack(0), 0, this.tank, f -> f.isIn(ModTags.FUELS));
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
		getTank().extractFluid(FluidHooks.newFluidHolder(getTankFluid(), this.getFuelPerTick(), null), false);
	}

	@Override
	public void update() {
	}
}