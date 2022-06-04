package com.github.alexnijjar.beyond_earth.items.armour;

import com.github.alexnijjar.beyond_earth.util.ModKeyBindings;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class JetSuit extends NetheriteSpaceSuit {
    public static final int MAX_OXYGEN = BASE_OXYGEN * 4;

    public JetSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public int getMaxOxygen() {
        return MAX_OXYGEN;
    }

    public void fly(PlayerEntity player) {

        World world = player.world;

        double speed = 0.5f;
        Vec3d rotationVector = player.getRotationVector().multiply(speed);

        if (ModKeyBindings.sprintKeyDown(player)) {

            if (!player.isOnGround() && !player.isFallFlying() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
                player.startFallFlying();
            }
            if (player.isFallFlying()) {
                Vec3d velocity = player.getVelocity();
                player.setVelocity(velocity.add(rotationVector.getX() * 0.1 + (rotationVector.getX() * 1.5 - velocity.getX()) * 0.5, rotationVector.getY() * 0.1 + (rotationVector.getY() * 1.5 - velocity.getY()) * 0.5,
                        rotationVector.getZ() * 0.1 + (rotationVector.getZ() * 1.5 - velocity.getZ()) * 0.5));
            }

        } else {
            player.setVelocity(player.getVelocity().add(0.0, ModUtils.getPlanetGravity(world) * 0.25, 0.0));
            if (player.getVelocity().getY() > 0.25) {
                player.setVelocity(player.getVelocity().getX(), 0.25, player.getVelocity().getZ());
            }
            player.stopFallFlying();
        }

        player.move(MovementType.SELF, player.getVelocity());

        if (world instanceof ServerWorld serverWorld) {
            Vec3d pos = player.getTrackedPosition();

            // TODO: redo particles.
            float xRotator = MathHelper.cos((float) (player.getY() * (Math.PI / 180f))) * (0.7f + 0.21f);
            float zRotator = MathHelper.sin((float) (player.getY() * (Math.PI / 180f))) * (0.7f + 0.21f);

            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() + xRotator, pos.getY(), pos.getZ() + zRotator, 1, 0.0, 0.0, 0.0, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() - xRotator, pos.getY(), pos.getZ() - zRotator, 1, 0.0, 0.0, 0.0, 0.001);

            // ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() + xRotator, pos.getY() + zRotator, pos.getZ(), 1, 0, 0, 0, 0.0f);
            // ModUtils.spawnForcedParticles(serverWorld, ParticleTypes.FLAME, pos.getX() - xRotator, pos.getY() - zRotator, pos.getZ(), 1, 0, 0, 0, 0.0f);
        }
    }
}