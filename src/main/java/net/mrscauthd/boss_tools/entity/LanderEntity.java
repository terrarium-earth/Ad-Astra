package net.mrscauthd.boss_tools.entity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.boss_tools.gui.screens.lander.LanderGui;

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

public class LanderEntity extends PathfinderMob {

	public LanderEntity(EntityType<LanderEntity> type, Level world) {
		super(type, world);
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
		return super.getPassengersRidingOffset() - 0.25;
	}

	@Override
	public void kill() {
		dropEquipment();
		this.remove(RemovalReason.DISCARDED);
	}

	@Override
	public AABB getBoundingBoxForCulling() {
		return new AABB(this.getX(),this.getY(),this.getZ(),this.getX(),this.getY(), this.getZ()).inflate(3,3,3);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.getMsgId().equals("fall")) {
			if (!this.level.isClientSide) {
				this.level.explode(null, this.getX(), this.getY(), this.getZ(), 10, Explosion.BlockInteraction.BREAK);
			}
			dropEquipment();
			this.remove(RemovalReason.DISCARDED);
			return true;
		}

		if (!source.isProjectile() && source.getEntity() != null && source.getEntity().isCrouching() && !this.isVehicle()) {
			dropEquipment();
			this.remove(RemovalReason.DISCARDED);
		}

		return false;
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
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("InventoryCustom", inventory.serializeNBT());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		Tag inventoryCustom = compound.get("InventoryCustom");
		if (inventoryCustom instanceof CompoundTag) {
			inventory.deserializeNBT((CompoundTag) inventoryCustom);
		}
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		return super.mobInteract(player, hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide);

		if (player instanceof ServerPlayer && player.isCrouching()) {

			NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return new TextComponent("Lander");
				}

				@Override
				public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
					FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
					packetBuffer.writeVarInt(LanderEntity.this.getId());
					return new LanderGui.GuiContainer(id, inventory, packetBuffer);
				}
			}, buf -> {
				buf.writeVarInt(this.getId());
			});

			return retval;
		}

		player.startRiding(this);
		return retval;
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}
}
