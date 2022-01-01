package net.mrscauthd.beyond_earth.entity;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;

import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import io.netty.buffer.Unpooled;
import net.mrscauthd.beyond_earth.block.RocketLaunchPad;
import net.mrscauthd.beyond_earth.events.Methodes;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gui.screens.rocket.RocketGui;

import java.util.Set;

public class RocketTier3Entity extends PathfinderMob {
	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final EntityDataAccessor<Boolean> ROCKET_START = SynchedEntityData.defineId(RocketTier3Entity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> BUCKETS = SynchedEntityData.defineId(RocketTier3Entity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(RocketTier3Entity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(RocketTier3Entity.class, EntityDataSerializers.INT);

	public static final int FUEL_BUCKETS = 3;

	private static final double ROCKET_SPEED = 0.8;

	public RocketTier3Entity(EntityType type, Level world) {
		super(type, world);
		this.entityData.define(ROCKET_START, false);
		this.entityData.define(BUCKETS, 0);
		this.entityData.define(FUEL, 0);
		this.entityData.define(START_TIMER, 0);
	}

	public static AttributeSupplier.Builder setCustomAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean canBeLeashed(Player p_21418_) {
		return false;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected void doPush(Entity p_20971_) {
	}

	@Override
	public void push(Entity p_21294_) {
	}

	@Deprecated
	public boolean canBeRiddenInWater() {
		return true;
	}

	@Override
	public boolean isAffectedByPotions() {
		return false;
	}

	@Override
	protected void onEffectAdded(MobEffectInstance p_147190_, @Nullable Entity p_147191_) {
	}

	@Override
	public boolean addEffect(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
		return false;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.NONE;
	}

	@Override
	public boolean removeWhenFarAway(double p_21542_) {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_21239_) {
		return null;
	}

	@Override
	public SoundEvent getDeathSound() {
		return null;
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() - 2.65;
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		ItemStack itemStack = new ItemStack(ModInit.TIER_3_ROCKET_ITEM.get(), 1);
		itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":fuel", this.getEntityData().get(FUEL));
		itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":buckets", this.getEntityData().get(BUCKETS));

		return itemStack;
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
		Vec3[] avector3d = new Vec3[]{getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot()), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F), getCollisionHorizontalEscapeVector((double)this.getBbWidth(), (double)livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)};
		Set<BlockPos> set = Sets.newLinkedHashSet();
		double d0 = this.getBoundingBox().maxY;
		double d1 = this.getBoundingBox().minY - 0.5D;
		BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

		for(Vec3 vector3d : avector3d) {
			blockpos$mutable.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);

			for(double d2 = d0; d2 > d1; --d2) {
				set.add(blockpos$mutable.immutable());
				blockpos$mutable.move(Direction.DOWN);
			}
		}

		for(BlockPos blockpos : set) {
			if (!this.level.getFluidState(blockpos).is(FluidTags.LAVA)) {
				double d3 = this.level.getBlockFloorHeight(blockpos);
				if (DismountHelper.isBlockFloorValid(d3)) {
					Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, d3);

					for(Pose pose : livingEntity.getDismountPoses()) {
						AABB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
						if (DismountHelper.isBlockFloorValid(this.level.getBlockFloorHeight(blockpos))) {
							livingEntity.setPose(pose);
							return vector3d1;
						}
					}
				}
			}
		}

		return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
	}

	@Override
	public void kill() {
		this.dropEquipment();
		this.spawnRocketItem();
		this.remove(RemovalReason.DISCARDED);
	}

	@Override
	public boolean hurt(DamageSource source, float p_21017_) {
		Entity sourceentity = source.getEntity();

		if (!source.isProjectile() && sourceentity != null && sourceentity.isCrouching() && !this.isVehicle()) {

			this.dropEquipment();
			this.spawnRocketItem();
			this.remove(RemovalReason.DISCARDED);

		}
		return false;
	}

	protected void spawnRocketItem() {
		if (!level.isClientSide) {
			ItemStack itemStack = new ItemStack(ModInit.TIER_3_ROCKET_ITEM.get(), 1);
			itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":fuel", this.getEntityData().get(FUEL));
			itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":buckets", this.getEntityData().get(BUCKETS));

			ItemEntity entityToSpawn = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), itemStack);
			entityToSpawn.setPickUpDelay(10);
			level.addFreshEntity(entityToSpawn);
		}
	}

	@Override
	protected void dropEquipment() {
		super.dropEquipment();
		for (int i = 0; i < inventory.getSlots(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
				this.spawnAtLocation(itemstack);
			}
		}
	}

	private final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public int getSlotLimit(int slot) {
			return 64;
		}
	};

	private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
		if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null) {
			return LazyOptional.of(() -> combined).cast();
		}
		return super.getCapability(capability, side);
	}

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("InventoryCustom", inventory.serializeNBT());

		compound.putBoolean("rocket_start", this.entityData.get(ROCKET_START));
		compound.putInt("buckets", this.entityData.get(BUCKETS));
		compound.putInt("fuel", this.entityData.get(FUEL));
		compound.putInt("start_timer", this.entityData.get(START_TIMER));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		Tag inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundTag) {
			inventory.deserializeNBT((CompoundTag) inventoryCustom);
		}

		this.entityData.set(ROCKET_START, compound.getBoolean("rocket_start"));
		this.entityData.set(BUCKETS, compound.getInt("buckets"));
		this.entityData.set(FUEL, compound.getInt("fuel"));
		this.entityData.set(START_TIMER, compound.getInt("start_timer"));
	}


	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		super.mobInteract(player, hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide);

		if (player instanceof ServerPlayer && player.isCrouching()) {

			NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return new TextComponent("Tier 3 Rocket");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
					packetBuffer.writeVarInt(RocketTier3Entity.this.getId());
					return new RocketGui.GuiContainer(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeVarInt(this.getId());
			});

			return retval;
		}

		player.startRiding(this);
		return retval;
	}

	@Override
	public void baseTick() {
		super.baseTick();
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();

		if (this.entityData.get(ROCKET_START)) {

			//Rocket Animation
			ar = ar + 1;
			if (ar == 1) {
				ay = ay + 0.006;
				ap = ap + 0.006;
			}
			if (ar == 2) {
				ar = 0;
				ay = 0;
				ap = 0;
			}

			if (this.entityData.get(START_TIMER) < 200) {
				this.entityData.set(START_TIMER, this.entityData.get(START_TIMER) + 1);
			}

			if (this.entityData.get(START_TIMER) == 200) {
				if (this.getDeltaMovement().y() < ROCKET_SPEED - 0.1) {
					this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
				} else {
					this.setDeltaMovement(this.getDeltaMovement().x, ROCKET_SPEED, this.getDeltaMovement().z);
				}
			}

			if (y > 600 && !this.getPassengers().isEmpty()) {
				Entity pass = this.getPassengers().get(0);

				pass.getPersistentData().putBoolean(BeyondEarthMod.MODID + ":planet_selection_gui_open", true);
				pass.getPersistentData().putString(BeyondEarthMod.MODID + ":rocket_type", this.getType().toString());
				pass.getPersistentData().putString(BeyondEarthMod.MODID + ":slot0", this.inventory.getStackInSlot(0).getItem().getRegistryName().toString());
				pass.setNoGravity(true);

				this.remove(RemovalReason.DISCARDED);
			} else if (y > 600 && this.getPassengers().isEmpty())  {
				this.remove(RemovalReason.DISCARDED);
			}

			Vec3 vec = this.getDeltaMovement();

			//Particle Spawn
			if (this.entityData.get(START_TIMER) == 200) {
				if (level instanceof ServerLevel) {
					for (ServerPlayer p : ((ServerLevel) level).getServer().getPlayerList().getPlayers()) {
						((ServerLevel) level).sendParticles(p, (ParticleOptions) ModInit.LARGE_FLAME_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 1.75, this.getZ() - vec.z, 20, 0.1, 0.1, 0.1, 0.001);
						((ServerLevel) level).sendParticles(p, (ParticleOptions) ModInit.LARGE_SMOKE_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 2.75, this.getZ() - vec.z, 10, 0.1, 0.1, 0.1, 0.04);
					}
				}
			} else {
				if (level instanceof ServerLevel) {
					for (ServerPlayer p : ((ServerLevel) level).getServer().getPlayerList().getPlayers()) {
						((ServerLevel) level).sendParticles(p, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX() - vec.x, this.getY() - vec.y - 0.1, this.getZ() - vec.z, 6, 0.1, 0.1, 0.1, 0.023);
					}
				}
			}

		}

		//Fuel Load up
		if (Methodes.tagCheck(FluidUtil2.findBucketFluid(this.inventory.getStackInSlot(0).getItem()), ModInit.FLUID_VEHICLE_FUEL_TAG) && this.entityData.get(BUCKETS) < 3) {

			if (this.entityData.get(FUEL) == 0 && this.entityData.get(BUCKETS) == 0) {

				this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			} else if (this.getEntityData().get(FUEL) == 100 && this.getEntityData().get(BUCKETS) == 1) {

				this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			} else if (this.getEntityData().get(FUEL) == 200 && this.getEntityData().get(BUCKETS) == 2) {

				this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			}
		}

		if (this.getEntityData().get(BUCKETS) == 1 && this.getEntityData().get(FUEL) < 100) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);

		} else if (this.getEntityData().get(BUCKETS) == 2 && this.getEntityData().get(FUEL) < 200) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);

		} else if (this.getEntityData().get(BUCKETS) == 3 && this.getEntityData().get(FUEL) < 300) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);
		}

		if (this.isOnGround() || this.isInWater()) {

			BlockState state = level.getBlockState(new BlockPos(Math.floor(x), y - 0.1, Math.floor(z)));

			if (!level.isEmptyBlock(new BlockPos(Math.floor(x), y - 0.01, Math.floor(z))) && state.getBlock() instanceof RocketLaunchPad && !state.getValue(RocketLaunchPad.STAGE)
					|| level.getBlockState(new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z))).getBlock() != ModInit.ROCKET_LAUNCH_PAD.get().defaultBlockState().getBlock()) {

				this.dropEquipment();
				this.spawnRocketItem();
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

}