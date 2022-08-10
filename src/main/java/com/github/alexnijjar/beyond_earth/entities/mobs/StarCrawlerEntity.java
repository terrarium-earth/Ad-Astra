package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.BeyondEarth;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class StarCrawlerEntity extends HostileEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4).add(EntityAttributes.GENERIC_MAX_HEALTH, 40).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9);
    }

    public StarCrawlerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 0.8, false));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(3, new RevengeGoal(this));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(5, new TargetGoal<>(this, PlayerEntity.class, false));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_TURTLE_DEATH;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if (!BeyondEarth.CONFIG.general.spawnStarCrawlers) {
            return false;
        }
        return super.canSpawn(world, spawnReason);
    }
}
