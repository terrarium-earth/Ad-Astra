package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.BeyondEarth;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class MartianRaptorEntity extends HostileEntity {

    private int movementCooldownTicks;

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4).add(EntityAttributes.GENERIC_MAX_HEALTH, 26).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8);
    }

public MartianRaptorEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(2, new RevengeGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.8));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new PounceAtTargetGoal(this, 0.2f));
        this.targetSelector.add(6, new ActiveTargetGoal<PlayerEntity>(this, PlayerEntity.class, false));
        this.goalSelector.add(7, new WanderAroundFarGoal((PathAwareEntity) this, 1.0));
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_STRIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRIDER_DEATH;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.movementCooldownTicks = 10;
        this.world.sendEntityStatus(this, (byte) 4);
        return super.tryAttack(target);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 4) {
            this.movementCooldownTicks = 10;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (movementCooldownTicks > 0) {
            movementCooldownTicks--;
        }
    }

    public int getMovementCooldownTicks() {
        return this.movementCooldownTicks;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if (!BeyondEarth.CONFIG.world.spawnMartianRaptors) {
            return false;
        }
        
        BlockState blockState = world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ()));

        if (blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.AIR)) {
            return false;
        }

        return super.canSpawn(world, spawnReason);
    }
}
