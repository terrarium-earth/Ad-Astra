package net.mrscauthd.beyond_earth.entity;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.block.RocketLaunchPad;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public abstract class IRocketEntity extends VehicleEntity {

    public double ROCKET_SPEED;

    public double ar = 0;
    public double ay = 0;
    public double ap = 0;

    public static final EntityDataAccessor<Boolean> ROCKET_START = SynchedEntityData.defineId(RocketTier1Entity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> BUCKETS = SynchedEntityData.defineId(RocketTier1Entity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FUEL = SynchedEntityData.defineId(RocketTier1Entity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> START_TIMER = SynchedEntityData.defineId(RocketTier1Entity.class, EntityDataSerializers.INT);

    public IRocketEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
        this.entityData.define(ROCKET_START, false);
        this.entityData.define(BUCKETS, 0);
        this.entityData.define(FUEL, 0);
        this.entityData.define(START_TIMER, 0);
    }

    protected void setRocketSpeed(double speed) {
        this.ROCKET_SPEED = speed;
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
    public void push(Entity p_21294_) {

    }

    @Override
    public void kill() {
        this.dropEquipment();
        this.spawnRocketItem();
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean hurt(DamageSource source, float p_21017_) {
        Entity sourceEntity = source.getEntity();

        if (!source.isProjectile() && sourceEntity != null && sourceEntity.isCrouching() && !this.isVehicle()) {

            this.spawnRocketItem();
            this.dropEquipment();
            this.remove(RemovalReason.DISCARDED);

        }
        return false;
    }

    protected void spawnRocketItem() {

    }

    protected void dropEquipment() {
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

    public ItemStackHandler getInventory() {
        return inventory;
    }

    private final CombinedInvWrapper combined = new CombinedInvWrapper(inventory);

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

        compound.putBoolean("rocket_start", this.getEntityData().get(ROCKET_START));
        compound.putInt("buckets", this.getEntityData().get(BUCKETS));
        compound.putInt("fuel", this.getEntityData().get(FUEL));
        compound.putInt("start_timer", this.getEntityData().get(START_TIMER));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        Tag inventoryCustom = compound.get("InventoryCustom");
        if (inventoryCustom instanceof CompoundTag) {
            inventory.deserializeNBT((CompoundTag) inventoryCustom);
        }

        this.getEntityData().set(ROCKET_START, compound.getBoolean("rocket_start"));
        this.getEntityData().set(BUCKETS, compound.getInt("buckets"));
        this.getEntityData().set(FUEL, compound.getInt("fuel"));
        this.getEntityData().set(START_TIMER, compound.getInt("start_timer"));
    }

    public void particleSpawn() {

    }

    public void doesDrop() {
        if (this.isOnGround() || this.isInWater()) {

            BlockPos blockPos = new BlockPos(Math.floor(this.getX()), this.getY() - 0.01, Math.floor(this.getZ()));
            BlockState state = level.getBlockState(blockPos);

            if (!level.isEmptyBlock(this.getOnPos()) && ((state.getBlock() instanceof RocketLaunchPad && !state.getValue(RocketLaunchPad.STAGE)) || !(state.getBlock() instanceof RocketLaunchPad))) {

                this.dropEquipment();
                this.spawnRocketItem();
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void fillUpRocket() {

    }

    public void rocketAnimation() {
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
    }

    public void startTimerAndFlyMovement() {
        if (this.entityData.get(START_TIMER) < 200) {
            this.entityData.set(START_TIMER, this.entityData.get(START_TIMER) + 1);
        }

        if (this.entityData.get(START_TIMER) == 200) {
            if (this.getDeltaMovement().y < ROCKET_SPEED - 0.1) {
                this.setDeltaMovement(this.getDeltaMovement().x, this.getDeltaMovement().y + 0.1, this.getDeltaMovement().z);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().x, ROCKET_SPEED, this.getDeltaMovement().z);
            }
        }
    }

    public void openPlanetSelectionGui() {
        if (this.getY() > 600 && !this.getPassengers().isEmpty()) {
            Entity pass = this.getPassengers().get(0);

            if (pass instanceof Player && ((Player) pass).containerMenu != null) {
                ((Player) pass).closeContainer();
            }

            pass.getPersistentData().putBoolean(BeyondEarthMod.MODID + ":planet_selection_gui_open", true);
            pass.getPersistentData().putString(BeyondEarthMod.MODID + ":rocket_type", this.getType().toString());
            pass.getPersistentData().putString(BeyondEarthMod.MODID + ":slot0", this.getInventory().getStackInSlot(0).getItem().getRegistryName().toString());
            pass.setNoGravity(true);

            this.remove(RemovalReason.DISCARDED);
        } else if (this.getY() > 600 && this.getPassengers().isEmpty())  {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.doesDrop();
        this.fillUpRocket();

        if (this.entityData.get(ROCKET_START)) {
            this.particleSpawn();
            this.rocketAnimation();
            this.startTimerAndFlyMovement();
            this.openPlanetSelectionGui();
        }
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
}
