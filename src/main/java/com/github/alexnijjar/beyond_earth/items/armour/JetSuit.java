package com.github.alexnijjar.beyond_earth.items.armour;

import java.util.List;

import com.github.alexnijjar.beyond_earth.registry.ModArmour;
import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import team.reborn.energy.api.base.SimpleBatteryItem;

public class JetSuit extends NetheriteSpaceSuit implements SimpleBatteryItem, FabricElytraItem {

    // 0.2M E.
    public static final long MAX_ENERGY = 200000;
    public boolean isFlying;

    public JetSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public long getTankSize() {
        return 4 * FluidConstants.BUCKET;
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack chestStack, boolean tickElytra) {
        return isFlying;
    }

    // Display energy.
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (stack.isOf(ModArmour.JET_SUIT)) {
            long energy = this.getStoredEnergy(stack);
            tooltip.add(Text.translatable("gauge_text.beyond_earth.storage", energy,MAX_ENERGY).setStyle(Style.EMPTY.withColor(energy > 0 ? Formatting.GREEN : Formatting.RED)));
        }
    }

    public void fly(PlayerEntity player, ItemStack stack) {

        World world = player.world;

        if (this.getStoredEnergy(stack) <= 0) {
            return;
        }

        double speed = 0.5f;
        long energyUsed = 0;
        Vec3d rotationVector = player.getRotationVector().multiply(speed);

        // Don't fly the jetsuit in creative.
        if (player.getAbilities().flying) {
            return;
        }

        if (ModKeyBindings.sprintKeyDown(player)) {
            if (!player.isOnGround() && !player.isFallFlying() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
                player.startFallFlying();
                isFlying = true;
            }
            if (player.isFallFlying()) {
                energyUsed += 12;
                Vec3d velocity = player.getVelocity();
                player.setVelocity(velocity.add(rotationVector.getX() * 0.1 + (rotationVector.getX() * 1.5 - velocity.getX()) * 0.5, rotationVector.getY() * 0.1 + (rotationVector.getY() * 1.5 - velocity.getY()) * 0.5,
                        rotationVector.getZ() * 0.1 + (rotationVector.getZ() * 1.5 - velocity.getZ()) * 0.5));
            }

        } else {
            energyUsed += 6;
            player.setVelocity(player.getVelocity().add(0.0, ModUtils.getPlanetGravity(world) * 0.25, 0.0));
            if (player.getVelocity().getY() > 0.25) {
                player.setVelocity(player.getVelocity().getX(), 0.25, player.getVelocity().getZ());
            }
            player.stopFallFlying();
        }

        this.tryUseEnergy(stack, energyUsed);
        player.move(MovementType.SELF, player.getVelocity());

        if (world instanceof ServerWorld serverWorld) {
            Vec3d pos = player.getPos();

            // TODO: redo particles.
            float xRotator = MathHelper.cos((float) (player.getY() * (Math.PI / 180f))) * (0.7f + 0.21f);
            float zRotator = MathHelper.sin((float) (player.getY() * (Math.PI / 180f))) * (0.7f + 0.21f);

            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator, 1, 0.0, 0.0, 0.0, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() - xRotator, pos.getY(), pos.getZ() - zRotator, 1, 0.0, 0.0, 0.0, 0.001);

            // ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() + xRotator, pos.getY() + zRotator, pos.getZ(), 1,
            // 0, 0, 0, 0.0f);
            // ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() - xRotator, pos.getY() - zRotator, pos.getZ(), 1,
            // 0, 0, 0, 0.0f);
        }
    }

    @Override
    public long getEnergyCapacity() {
        return MAX_ENERGY;
    }

    @Override
    public long getEnergyMaxInput() {
        return 512;
    }

    @Override
    public long getEnergyMaxOutput() {
        return 256;
    }
}