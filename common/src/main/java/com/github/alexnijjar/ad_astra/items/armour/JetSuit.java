package com.github.alexnijjar.ad_astra.items.armour;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import team.reborn.energy.api.base.SimpleBatteryItem;

public class JetSuit extends NetheriteSpaceSuit implements SimpleBatteryItem, FabricElytraItem {

	public boolean isFallFlying;

	public JetSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public long getTankSize() {
		return AdAstra.CONFIG.spaceSuit.jetSuitTankSize;
	}

	@Override
	public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
		return this.isFallFlying;
	}

	// Display energy
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		if (stack.isOf(ModItems.JET_SUIT)) {
			long energy = this.getStoredEnergy(stack);
			tooltip.add(Text.translatable("gauge_text.ad_astra.storage", energy, AdAstra.CONFIG.spaceSuit.jetSuitMaxEnergy).setStyle(Style.EMPTY.withColor(energy > 0 ? Formatting.GREEN : Formatting.RED)));
		}
	}

	public void fly(PlayerEntity player, ItemStack stack) {
		// Don't fly the Jet Suit in creative
		if (player.getAbilities().flying) {
			stack.getOrCreateNbt().putBoolean("spawn_particles", false);
			return;
		}

		// Don't fly if the Jet Suit has no energy
		if (this.getStoredEnergy(stack) <= 0) {
			stack.getOrCreateNbt().putBoolean("spawn_particles", false);
			return;
		}

		stack.getOrCreateNbt().putBoolean("spawn_particles", true);
		if (ModKeyBindings.sprintKeyDown(player)) {
			this.fallFly(player, stack);
		} else {
			this.hover(player, stack);
		}

		if (!player.world.isClient) {
			if (isFallFlying) {
				if (!player.isFallFlying()) {
					player.startFallFlying();
				}
			} else {
				if (player.isFallFlying()) {
					player.stopFallFlying();
				}
			}
		}
	}

	public void hover(PlayerEntity player, ItemStack stack) {
		player.fallDistance /= 2;
		if (!player.isCreative() && !this.tryUseEnergy(stack, AdAstra.CONFIG.spaceSuit.jetSuitEnergyPerTick)) {
			this.setStoredEnergy(stack, 0);
		}
		isFallFlying = false;

		double speed = AdAstra.CONFIG.spaceSuit.jetSuitUpwardsSpeed;
		player.setVelocity(player.getVelocity().add(0.0, speed, 0.0));
		if (player.getVelocity().getY() > speed) {
			player.setVelocity(player.getVelocity().getX(), speed, player.getVelocity().getZ());
		}
	}

	public void fallFly(PlayerEntity player, ItemStack stack) {
		if (player.isOnGround()) {
			player.fallDistance /= 2;
		}
		if (!player.isCreative() && !this.tryUseEnergy(stack, AdAstra.CONFIG.spaceSuit.jetSuitEnergyPerTick * 2)) {
			this.setStoredEnergy(stack, 0);
		}
		isFallFlying = true;

		double speed = AdAstra.CONFIG.spaceSuit.jetSuitSpeed - (ModUtils.getPlanetGravity(player.world) * 0.25);
		Vec3d rotationVector = player.getRotationVector().multiply(speed);
		Vec3d velocity = player.getVelocity();
		player.setVelocity(velocity.add(rotationVector.getX() * 0.1 + (rotationVector.getX() * 1.5 - velocity.getX()) * 0.5, rotationVector.getY() * 0.1 + (rotationVector.getY() * 1.5 - velocity.getY()) * 0.5,
				rotationVector.getZ() * 0.1 + (rotationVector.getZ() * 1.5 - velocity.getZ()) * 0.5));
	}

	public static void spawnParticles(World world, LivingEntity entity, BipedEntityModel<LivingEntity> model) {

		if (entity instanceof PlayerEntity player) {
			ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
			NbtCompound nbt = chest.getOrCreateNbt();
			if (nbt.contains("spawn_particles")) {
				if (!nbt.getBoolean("spawn_particles")) {
					return;
				}
			} else {
				return;
			}
		}
		spawnParticles(world, entity, model, model.rightArm.pitch + 0.05, entity.isFallFlying() ? 0.0 : 0.8, -0.45);
		spawnParticles(world, entity, model, model.leftArm.pitch + 0.05, entity.isFallFlying() ? 0.0 : 0.8, 0.45);

		spawnParticles(world, entity, model, model.rightLeg.pitch + 0.05, entity.isFallFlying() ? 0.1 : 0.0, -0.1);
		spawnParticles(world, entity, model, model.leftLeg.pitch + 0.05, entity.isFallFlying() ? 0.1 : 0.0, 0.1);
	}

	// Spawns particles at the limbs of the player
	private static void spawnParticles(World world, LivingEntity entity, BipedEntityModel<LivingEntity> model, double pitch, double yOffset, double zOffset) {
		double yaw = entity.bodyYaw;
		double xRotator = Math.cos(yaw * Math.PI / 180.0) * zOffset;
		double zRotator = Math.sin(yaw * Math.PI / 180.0) * zOffset;
		double xRotator1 = Math.cos((yaw - 90) * Math.PI / 180.0) * pitch;
		double zRotator1 = Math.sin((yaw - 90) * Math.PI / 180.0) * pitch;

		world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, true, entity.getX() + xRotator + xRotator1, entity.getY() + yOffset, entity.getZ() + zRotator1 + zRotator, 0.0, 0.0, 0.0);
	}

	@Override
	public long getEnergyCapacity() {
		return AdAstra.CONFIG.spaceSuit.jetSuitMaxEnergy;
	}

	@Override
	public long getEnergyMaxInput() {
		return 512;
	}

	@Override
	public long getEnergyMaxOutput() {
		return 256;
	}

	public static boolean hasFullSet(LivingEntity entity) {
		for (ItemStack stack : entity.getArmorItems()) {
			if (!(stack.getItem() instanceof JetSuit)) {
				return false;
			}
		}
		return true;
	}
}