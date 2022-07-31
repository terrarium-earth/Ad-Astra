package com.github.alexnijjar.beyond_earth.entities.mobs;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.entities.projectiles.IceSpitEntity;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CorruptedLunarianEntity extends HostileEntity implements RangedAttackMob {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3).add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    public CorruptedLunarianEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.add(2, new RevengeGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.8));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(5, new TargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(7, new TargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.add(8, new TargetGoal<>(this, LunarianWanderingTraderEntity.class, false));
        this.targetSelector.add(9, new TargetGoal<>(this, GolemEntity.class, false));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.25f, 20, 15) {
            @Override
            public boolean shouldContinue() {
                return this.canStart();
            }
        });
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        IceSpitEntity projectile = new IceSpitEntity(ModEntityTypes.ICE_SPIT_ENTITY, this, this.world);

        double targetX = target.getX() - this.getX();
        double targetY = target.getBodyY(0.3333333333333333) - projectile.getY() - 1.1f;
        double targetZ = target.getZ() - this.getZ();
        double calculated = Math.sqrt(targetX * targetX + targetZ * targetZ);
        projectile.setVelocity(targetX, targetY + calculated * (double) 0.2f, targetZ, 1.6f, 14 - this.world.getDifficulty().getId() * 4);

        projectile.setSilent(true);
        this.world.spawnEntity(projectile);
        this.world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 1);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if (!BeyondEarth.CONFIG.world.spawnCorruptedLunarians) {
            return false;
        }

        BlockState blockState = world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ()));

        if (blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.AIR)) {
            return false;
        }

        return super.canSpawn(world, spawnReason);
    }
}
