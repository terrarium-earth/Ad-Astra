package com.github.alexnijjar.beyond_earth.entities.vehicles;

import com.github.alexnijjar.beyond_earth.registry.ModItems;
import com.github.alexnijjar.beyond_earth.registry.ModParticles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
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

            int particleX = switch ((int) this.getYaw()) {
            case 0, 360 -> 1;
            case 90 -> 0;
            case -90 -> 0;
            case -180, 180 -> 1;
            default -> 0;
            };

            int particleZ = particleX == 1 ? 0 : 1;

            serverWorld.spawnParticles(ModParticles.SMALL_FLAME, pos.getX() + particleX, pos.getY(), pos.getZ() + particleZ, 20, 0.1, 0.1, 0.1, 0.001);
            serverWorld.spawnParticles(ModParticles.SMALL_SMOKE, pos.getX() + particleX, pos.getY(), pos.getZ() + particleZ, 10, 0.1, 0.1, 0.1, 0.04);

            serverWorld.spawnParticles(ModParticles.SMALL_FLAME, pos.getX() - particleX, pos.getY(), pos.getZ() - particleZ, 20, 0.1, 0.1, 0.1, 0.001);
            serverWorld.spawnParticles(ModParticles.SMALL_SMOKE, pos.getX() - particleX, pos.getY(), pos.getZ() - particleZ, 10, 0.1, 0.1, 0.1, 0.04);
        }
    }
}