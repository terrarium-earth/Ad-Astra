package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.Lists;
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
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;

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
import net.mrscauthd.boss_tools.block.RocketLaunchPad;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.rocket.RocketGui;

import java.util.List;

public class RocketTier2Entity extends PathfinderMob {
	public double ar = 0;
	public double ay = 0;
	public double ap = 0;

	public static final EntityDataAccessor<Boolean> ROCKET_START = SynchedEntityData.defineId(RocketTier2Entity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> BUCKETS = SynchedEntityData.defineId(RocketTier2Entity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(RocketTier2Entity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(RocketTier2Entity.class, EntityDataSerializers.INT);

	public static final int FUEL_BUCKETS = 3;

	public RocketTier2Entity(EntityType type, Level world) {
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
		return true;
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
		return super.getPassengersRidingOffset() - 2.5;
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		ItemStack itemStack = new ItemStack(ModInnet.TIER_2_ROCKET_ITEM.get(), 1);
		itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.getEntityData().get(FUEL));
		itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":buckets", this.getEntityData().get(BUCKETS));

		return itemStack;
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity p_38357_) {
		Vec3 vec3 = getCollisionHorizontalEscapeVector((double)(this.getBbWidth() * Mth.SQRT_OF_TWO), (double)p_38357_.getBbWidth(), p_38357_.getYRot());
		double d0 = this.getX() + vec3.x;
		double d1 = this.getZ() + vec3.z;
		BlockPos blockpos = new BlockPos(d0, this.getBoundingBox().maxY, d1);
		BlockPos blockpos1 = blockpos.below();
		if (!this.level.isWaterAt(blockpos1)) {
			List<Vec3> list = Lists.newArrayList();
			double d2 = this.level.getBlockFloorHeight(blockpos);
			if (DismountHelper.isBlockFloorValid(d2)) {
				list.add(new Vec3(d0, (double)blockpos.getY() + d2, d1));
			}

			double d3 = this.level.getBlockFloorHeight(blockpos1);
			if (DismountHelper.isBlockFloorValid(d3)) {
				list.add(new Vec3(d0, (double)blockpos1.getY() + d3, d1));
			}

			for(Pose pose : p_38357_.getDismountPoses()) {
				for(Vec3 vec31 : list) {
					if (DismountHelper.canDismountTo(this.level, vec31, p_38357_, pose)) {
						p_38357_.setPose(pose);
						return vec31;
					}
				}
			}
		}

		return super.getDismountLocationForPassenger(p_38357_);
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
			ItemStack itemStack = new ItemStack(ModInnet.TIER_2_ROCKET_ITEM.get(), 1);
			itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.getEntityData().get(FUEL));
			itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":buckets", this.getEntityData().get(BUCKETS));

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
					return new TextComponent("Tier 2 Rocket");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
					packetBuffer.writeVarInt(RocketTier2Entity.this.getId());
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
				if (this.getDeltaMovement().y() < 0.5) {
					this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
				} else {
					this.setDeltaMovement(this.getDeltaMovement().x, 0.63, this.getDeltaMovement().y);
				}
			}

			if (y > 600 && !this.getPassengers().isEmpty()) {
				Entity pass = this.getPassengers().get(0);

				pass.getPersistentData().putBoolean(BossToolsMod.ModId + ":planet_selection_gui_open", true);
				pass.getPersistentData().putString(BossToolsMod.ModId + ":rocket_type", this.getType().toString());
				pass.getPersistentData().putString(BossToolsMod.ModId + ":slot0", this.inventory.getStackInSlot(0).getItem().getRegistryName().toString());
				pass.setNoGravity(true);

				this.remove(RemovalReason.DISCARDED);
			} else if (y > 600 && this.getPassengers().isEmpty())  {
				this.remove(RemovalReason.DISCARDED);
			}

			//Particle Spawn
			if (this.entityData.get(START_TIMER) == 200) {
				if (level instanceof ServerLevel) {
					((ServerLevel) level).sendParticles(null, (ParticleOptions) ModInnet.LARGE_FLAME_PARTICLE.get(), true, this.getX(), this.getY() - 2.2, this.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
					((ServerLevel) level).sendParticles(null, (ParticleOptions) ModInnet.SMOKE_PARTICLE.get(), true, this.getX(), this.getY() - 3.2, this.getZ(), 10, 0.1, 0.1, 0.1, 0.04);
				}
			} else {
				if (level instanceof ServerLevel) {
					((ServerLevel) level).sendParticles(null, ParticleTypes.CAMPFIRE_COSY_SMOKE, true, this.getX(), this.getY() - 0.1, this.getZ(), 6, 0.1, 0.1, 0.1, 0.023);
				}
			}

		}

		//Fuel Load up
		if (Methodes.tagCheck(FluidUtil2.findBucketFluid(this.inventory.getStackInSlot(0).getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG) && this.entityData.get(BUCKETS) < 3) {

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
					|| level.getBlockState(new BlockPos(Math.floor(x), Math.floor(y), Math.floor(z))).getBlock() != ModInnet.ROCKET_LAUNCH_PAD.get().defaultBlockState().getBlock()) {

				this.dropEquipment();
				this.spawnRocketItem();
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

}