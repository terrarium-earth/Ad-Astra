package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.registry.ModEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class AlienEntity extends VillagerEntity implements ModEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48);
    }

    public AlienEntity(EntityType<? extends VillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new FleeEntityGoal<>(this, AlienZombieEntity.class, 15.0f, 0.5f, 0.5f));
    }

    @Override
    public VillagerEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        AlienEntity entity = new AlienEntity(ModEntities.ALIEN, serverWorld);
        entity.initialize(serverWorld, serverWorld.getLocalDifficulty(entity.getBlockPos()), SpawnReason.BREEDING, null, null);
        return entity;
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
