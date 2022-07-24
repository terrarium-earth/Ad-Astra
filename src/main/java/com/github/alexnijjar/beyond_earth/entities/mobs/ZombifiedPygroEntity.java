package com.github.alexnijjar.beyond_earth.entities.mobs;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ZombifiedPygroEntity extends ZombifiedPiglinEntity {

    public ZombifiedPygroEntity(EntityType<? extends ZombifiedPiglinEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return ZombieEntity.createZombieAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockState blockState = world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ()));

        if (blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.AIR)) {
            return false;
        }

        return super.canSpawn(world, spawnReason);
    }
}
