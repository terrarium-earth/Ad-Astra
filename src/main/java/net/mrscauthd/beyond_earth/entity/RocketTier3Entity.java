package net.mrscauthd.beyond_earth.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;

import io.netty.buffer.Unpooled;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.fluid.FluidUtil2;
import net.mrscauthd.beyond_earth.gui.screens.rocket.RocketGui;

public class RocketTier3Entity extends IRocketEntity {

	public RocketTier3Entity(EntityType type, Level world) {
		super(type, world);
		this.setRocketSpeed(0.8);
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() - 2.65;
	}

	@Override
	public ItemStack getPickResult() {
		ItemStack itemStack = new ItemStack(ModInit.TIER_3_ROCKET_ITEM.get(), 1);
		itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":fuel", this.getEntityData().get(FUEL));
		itemStack.getOrCreateTag().putInt(BeyondEarthMod.MODID + ":buckets", this.getEntityData().get(BUCKETS));

		return itemStack;
	}

	@Override
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
	public InteractionResult interact(Player player, InteractionHand hand) {
		super.interact(player, hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide);

		if (player instanceof ServerPlayer && player.isCrouching()) {

			NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
				@Override
				public Component getDisplayName() {
					return new TranslatableComponent("container.entity." + BeyondEarthMod.MODID +".rocket_t3");
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
	public void particleSpawn() {
		Vec3 vec = this.getDeltaMovement();

		if (this.entityData.get(START_TIMER) == 200) {
			if (level instanceof ServerLevel) {
				for (ServerPlayer p : ((ServerLevel) level).getServer().getPlayerList().getPlayers()) {
					((ServerLevel) level).sendParticles(p, (ParticleOptions) ModInit.LARGE_FLAME_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 2.6, this.getZ() - vec.z, 20, 0.1, 0.1, 0.1, 0.001);
					((ServerLevel) level).sendParticles(p, (ParticleOptions) ModInit.LARGE_SMOKE_PARTICLE.get(), true, this.getX() - vec.x, this.getY() - vec.y - 3.6, this.getZ() - vec.z, 10, 0.1, 0.1, 0.1, 0.04);
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

	@Override
	public void fillUpRocket() {
		if (Methods.tagCheck(FluidUtil2.findBucketFluid(this.getInventory().getStackInSlot(0).getItem()), ModInit.FLUID_VEHICLE_FUEL_TAG) && this.entityData.get(BUCKETS) < 3) {

			if (this.entityData.get(FUEL) == 0 && this.entityData.get(BUCKETS) == 0) {
				this.getInventory().setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			}
			else if (this.getEntityData().get(FUEL) == 100 && this.getEntityData().get(BUCKETS) == 1) {
				this.getInventory().setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			}
			else if (this.getEntityData().get(FUEL) == 200 && this.getEntityData().get(BUCKETS) == 2) {
				this.getInventory().setStackInSlot(0, new ItemStack(Items.BUCKET));
				this.getEntityData().set(BUCKETS, this.getEntityData().get(BUCKETS) + 1);
			}
		}

		if (this.getEntityData().get(BUCKETS) == 1 && this.getEntityData().get(FUEL) < 100) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);
		}
		else if (this.getEntityData().get(BUCKETS) == 2 && this.getEntityData().get(FUEL) < 200) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);
		}
		else if (this.getEntityData().get(BUCKETS) == 3 && this.getEntityData().get(FUEL) < 300) {
			this.getEntityData().set(FUEL, this.getEntityData().get(FUEL) + 1);
		}
	}
}