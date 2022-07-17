package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.registry.ModParticleTypes;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RocketEntityTier3 extends RocketEntity {

    public RocketEntityTier3(EntityType<?> type, World world) {
        super(type, world, 3);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.0f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_3_ROCKET.getDefaultStack();
    }

    @Override
    public void spawnAfterburnerParticles() {
        super.spawnAfterburnerParticles();
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();

            float xRotator = MathHelper.cos(this.getYaw() * ((float) Math.PI / 180)) * (0.7f + 0.21f * (float) 1);
            float zRotator = MathHelper.sin(this.getYaw() * ((float) Math.PI / 180)) * (0.7f + 0.21f * (float) 1);

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME, pos.getX() + xRotator, pos.getY() + 0.35, pos.getZ() + zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE, pos.getX() + xRotator, pos.getY() + 0.35, pos.getZ() + zRotator, 10, 0.1, 0.1, 0.1, 0.04);

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME, pos.getX() - xRotator, pos.getY() + 0.35, pos.getZ() - zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE, pos.getX() - xRotator, pos.getY() + 0.35, pos.getZ() - zRotator, 10, 0.1, 0.1, 0.1, 0.04);
        }
    }
}