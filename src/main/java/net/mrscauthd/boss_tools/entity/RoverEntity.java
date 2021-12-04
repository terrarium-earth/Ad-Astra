package net.mrscauthd.boss_tools.entity;

import com.google.common.collect.Sets;
import io.netty.buffer.Unpooled;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.events.Methodes;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.rover.RoverGui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class RoverEntity extends CreatureEntity {
    private double speed = 0;
    private boolean forward = false;

    public static final DataParameter<Integer> FUEL = EntityDataManager.createKey(RocketTier1Entity.class, DataSerializers.VARINT);

	public static final int FUEL_BUCKETS = 3;

    public RoverEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
        this.dataManager.register(FUEL, 0);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity p_82167_1_) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Deprecated
    public boolean canBeRiddenInWater() {
        return true;
    }

    public boolean canBeHitWithPotion() {
        return false;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEFINED;
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return null;
    }

    @Override
    public SoundEvent getDeathSound() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    //Save Entity Dismount
    @Override
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        Vector3d[] avector3d = new Vector3d[]{func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw - 22.5F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw + 22.5F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw - 45.0F), func_233559_a_((double)this.getWidth(), (double)livingEntity.getWidth(), livingEntity.rotationYaw + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Vector3d vector3d : avector3d) {
            blockpos$mutable.setPos(this.getPosX() + vector3d.x, d0, this.getPosZ() + vector3d.z);

            for(double d2 = d0; d2 > d1; --d2) {
                set.add(blockpos$mutable.toImmutable());
                blockpos$mutable.move(Direction.DOWN);
            }
        }

        for(BlockPos blockpos : set) {
            if (!this.world.getFluidState(blockpos).isTagged(FluidTags.LAVA)) {
                double d3 = this.world.func_242403_h(blockpos);
                if (TransportationHelper.func_234630_a_(d3)) {
                    Vector3d vector3d1 = Vector3d.copyCenteredWithVerticalOffset(blockpos, d3);

                    for(Pose pose : livingEntity.getAvailablePoses()) {
                        AxisAlignedBB axisalignedbb = livingEntity.getPoseAABB(pose);
                        if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d1))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }

        return new Vector3d(this.getPosX(), this.getBoundingBox().maxY, this.getPosZ());
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        if (passenger.isSneaking() && !passenger.world.isRemote) {
            if (passenger instanceof ServerPlayerEntity) {
                this.setAIMoveSpeed(0f);
            }
        }
        super.removePassenger(passenger);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        ItemStack itemStack = new ItemStack(ModInnet.ROVER_ITEM.get(), 1);
        itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.getDataManager().get(FUEL));

        return itemStack;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.15;
    }

    @Override
    public void onKillCommand() {
        this.spawnRoverItem();
        this.dropInventory();
        this.remove();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPosX(),this.getPosY(),this.getPosZ(),this.getPosX(),this.getPosY(), this.getPosZ()).grow(4.5,4.5,4.5);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!source.isProjectile() && source.getTrueSource() != null && source.getTrueSource().isSneaking() && !this.isBeingRidden()) {
            this.spawnRoverItem();
            this.dropInventory();
            this.remove();
        }

        return false;
    }

    protected void spawnRoverItem() {
        if (!world.isRemote()) {
            ItemStack itemStack = new ItemStack(ModInnet.ROVER_ITEM.get(), 1);
            itemStack.getOrCreateTag().putInt(BossToolsMod.ModId + ":fuel", this.getDataManager().get(FUEL));

            ItemEntity entityToSpawn = new ItemEntity(world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack);
            entityToSpawn.setPickupDelay(10);
            world.addEntity(entityToSpawn);
        }
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                this.entityDropItem(itemstack);
            }
        }
    }

    private final ItemStackHandler inventory = new ItemStackHandler(9) {
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("InventoryCustom", inventory.serializeNBT());

        compound.putInt("fuel", this.dataManager.get(FUEL));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        INBT inventoryCustom = compound.get("InventoryCustom");
        if (inventoryCustom instanceof CompoundNBT) {
            inventory.deserializeNBT((CompoundNBT) inventoryCustom);
        }

        this.dataManager.set(FUEL, compound.getInt("fuel"));
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
        super.func_230254_b_(sourceentity, hand);
        ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());

        if (sourceentity instanceof ServerPlayerEntity && sourceentity.isSneaking()) {

            NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return RoverEntity.this.getDisplayName();
                }

                @Override
                public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                    PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                    packetBuffer.writeVarInt(RoverEntity.this.getEntityId());
                    return new RoverGui.GuiContainer(id, inventory, packetBuffer);
                }
            }, buf -> {
                buf.writeVarInt(this.getEntityId());
            });

            return retval;
        }

        sourceentity.startRiding(this);
        return retval;
    }

    public boolean getforward() {
        return forward;
    }

    @Override
    public void baseTick() {
        super.baseTick();

        //Fuel Load up
        if (Methodes.tagCheck(FluidUtil2.findBucketFluid(this.inventory.getStackInSlot(0).getItem()), ModInnet.FLUID_VEHICLE_FUEL_TAG)) {

            if (this.dataManager.get(FUEL) <= 2000) {
                this.getDataManager().set(FUEL, this.getDataManager().get(FUEL) + 1000);
                this.inventory.setStackInSlot(0, new ItemStack(Items.BUCKET));
            }
        }

        if (this.getPassengers().isEmpty()) {
            return;
        }

        if (!(this.getPassengers().get(0) instanceof PlayerEntity)) {
            return;
        }

        if (this.areEyesInFluid(FluidTags.WATER)) {
            return;
        }

        PlayerEntity passanger = (PlayerEntity) this.getPassengers().get(0);

        if (passanger.moveForward > 0.01 && this.getDataManager().get(FUEL) != 0) {

            this.dataManager.set(FUEL, this.getDataManager().get(FUEL) - 1);
            forward = true;
        } else if (passanger.moveForward < -0.01 && this.getDataManager().get(FUEL) != 0) {

            this.dataManager.set(FUEL, this.getDataManager().get(FUEL) - 1);
            forward = false;
        }
    }

    @Override
    public void travel(Vector3d dir) {

        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) instanceof PlayerEntity) {

            PlayerEntity passanger = (PlayerEntity) this.getPassengers().get(0);

            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.15F;
            this.stepHeight = 1.0F;

            double pmovement = passanger.moveForward;

            if (pmovement == 0 || this.getDataManager().get(FUEL) == 0 || this.areEyesInFluid(FluidTags.WATER)) {
                pmovement = 0;
                this.setAIMoveSpeed(0f);

                if (speed != 0) {
                    if (speed > 0.02) {
                        speed = speed - 0.02;
                    }
                }
            }

            if (this.forward && this.getDataManager().get(FUEL) != 0) {
                if (this.getAIMoveSpeed() >= 0.01) {
                    if (speed <= 0.32) {
                        speed = speed + 0.02;
                    }
                }

                if (this.getAIMoveSpeed() < 0.25) {
                    this.setAIMoveSpeed(this.getAIMoveSpeed() + 0.02F);
                }

            }

            if (!this.forward) {

                if (this.getDataManager().get(FUEL) != 0 && !this.areEyesInFluid(FluidTags.WATER)) {

                    if (this.getAIMoveSpeed() <= 0.04) {
                        this.setAIMoveSpeed(this.getAIMoveSpeed() + 0.02f);
                    }
                }

                if (this.getAIMoveSpeed() >= 0.08) {
                    this.setAIMoveSpeed(0f);
                }
            }

            if (this.forward) {
                this.setWellRotationPlus(4.0f, 0.4f);
            } else {
                this.setWellRotationMinus(8.0f, 0.8f);
            }

            super.travel(new Vector3d(0, 0, pmovement));
            return;
        }

        super.travel(new Vector3d(0, 0, 0));
    }

    public void setWellRotationMinus(float rotation1, float rotation2) {
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.getPosX() - this.prevPosX;
        double d0 = this.getPosZ() - this.prevPosZ;
        float f1 = -MathHelper.sqrt(d1 * d1 + d0 * d0) * rotation1;
        if (f1 > 1.0F)
            f1 = 1.0F;
        this.limbSwingAmount += (f1 - this.limbSwingAmount) * rotation2;
        this.limbSwing += this.limbSwingAmount;
    }

    public void setWellRotationPlus(float rotation1, float rotation2) {
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.getPosX() - this.prevPosX;
        double d0 = this.getPosZ() - this.prevPosZ;
        float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * rotation1;
        if (f1 > 1.0F)
            f1 = 1.0F;
        this.limbSwingAmount += (f1 - this.limbSwingAmount) * rotation2;
        this.limbSwing += this.limbSwingAmount;
    }
}
