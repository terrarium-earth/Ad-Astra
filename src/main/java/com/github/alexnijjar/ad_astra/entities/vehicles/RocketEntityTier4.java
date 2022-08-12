package com.github.alexnijjar.ad_astra.entities.vehicles;

import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RocketEntityTier4 extends RocketEntity {

    public RocketEntityTier4(EntityType<?> type, World world) {
        super(type, world, 4);
    }

    @Override
    public double getMountedHeightOffset() {
        return super.getMountedHeightOffset() + 1.7f;
    }

    @Override
    public boolean shouldSit() {
        return false;
    }

    @Override
    public ItemStack getDropStack() {
        return ModItems.TIER_4_ROCKET.getDefaultStack();
    }

    @Override
    public void spawnAfterburnerParticles() {
        super.spawnAfterburnerParticles();
        if (this.world instanceof ServerWorld serverWorld) {
            Vec3d pos = this.getPos();

            float xRotator = MathHelper.cos(this.getYaw() * ((float) Math.PI / 180.0f)) * 1.2f;
            float zRotator = MathHelper.sin(this.getYaw() * ((float) Math.PI / 180.0f)) * 1.2f;

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME, pos.getX() + xRotator, pos.getY() + 0.35, pos.getZ() + zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE, pos.getX() + xRotator, pos.getY() + 0.35, pos.getZ() + zRotator, 10, 0.1, 0.1, 0.1, 0.04);

            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_FLAME, pos.getX() - xRotator, pos.getY() + 0.35, pos.getZ() - zRotator, 20, 0.1, 0.1, 0.1, 0.001);
            ModUtils.spawnForcedParticles(serverWorld, ModParticleTypes.SMALL_SMOKE, pos.getX() - xRotator, pos.getY() + 0.35, pos.getZ() - zRotator, 10, 0.1, 0.1, 0.1, 0.04);
        }
    }
}