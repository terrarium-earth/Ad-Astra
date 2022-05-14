package net.mrscauthd.beyond_earth.entities.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.registry.ModItems;
import net.mrscauthd.beyond_earth.registry.ModParticles;

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
            serverWorld.spawnParticles(ModParticles.SMALL_FLAME, pos.getX() + 1, pos.getY(), pos.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
            serverWorld.spawnParticles(ModParticles.SMALL_SMOKE, pos.getX() + 1, pos.getY(), pos.getZ(), 10, 0.1, 0.1, 0.1, 0.04);

            serverWorld.spawnParticles(ModParticles.SMALL_FLAME, pos.getX() - 1, pos.getY(), pos.getZ(), 20, 0.1, 0.1, 0.1, 0.001);
            serverWorld.spawnParticles(ModParticles.SMALL_SMOKE, pos.getX() - 1, pos.getY(), pos.getZ(), 10, 0.1, 0.1, 0.1, 0.04);
        }
    }
}