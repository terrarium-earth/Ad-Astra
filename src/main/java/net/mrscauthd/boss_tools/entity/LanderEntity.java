package net.mrscauthd.boss_tools.entity;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.events.Events;
import net.mrscauthd.boss_tools.gui.screens.lander.LanderGui;

import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.IPacket;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;

import io.netty.buffer.Unpooled;

import java.awt.*;

public class LanderEntity extends CreatureEntity {

	public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20);
	}

	public LanderEntity(EntityType<LanderEntity> type, World world) {
		super(type, world);
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
	public boolean func_241845_aY() {
		return true;
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

	@Override
	public double getMountedYOffset() {
		return super.getMountedYOffset() - 0.25;
	}

	@Override
	public void onKillCommand() {
		dropInventory();
		this.remove();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.getPosX(),this.getPosY(),this.getPosZ(),this.getPosX(),this.getPosY(), this.getPosZ()).grow(3,3,3);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getDamageType().equals("fall")) {
			if (!this.world.isRemote) {
				this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), 10, Explosion.Mode.BREAK);
			}
			dropInventory();
			this.remove();
			return true;
		}

		if (!source.isProjectile() && source.getTrueSource() != null && source.getTrueSource().isSneaking() && !this.isBeingRidden()) {
			dropInventory();
			this.remove();
		}

		return false;
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

	private final ItemStackHandler inventory = new ItemStackHandler(2) {
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
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		INBT inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundNBT) {
			inventory.deserializeNBT((CompoundNBT) inventoryCustom);
		}
	}

	@Override
	public ActionResultType func_230254_b_(PlayerEntity sourceentity, Hand hand) {
		super.func_230254_b_(sourceentity, hand);
		ActionResultType retval = ActionResultType.func_233537_a_(this.world.isRemote());

		if (sourceentity instanceof ServerPlayerEntity && sourceentity.isSneaking()) {

			NetworkHooks.openGui((ServerPlayerEntity) sourceentity, new INamedContainerProvider() {
				@Override
				public ITextComponent getDisplayName() {
					return new StringTextComponent("Lander");
				}

				@Override
				public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
					PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
					packetBuffer.writeVarInt(LanderEntity.this.getEntityId());
					return new LanderGui.GuiContainer(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeVarInt(this.getEntityId());
			});

			return retval;
		}

		sourceentity.startRiding(this);
		return retval;
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}
}
