package com.github.alexnijjar.beyond_earth.entities.mobs;

import javax.annotation.Nullable;

import com.github.alexnijjar.beyond_earth.registry.ModEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class MoglerEntity extends HoglinEntity implements ModEntity {

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
    }

    public MoglerEntity(EntityType<? extends HoglinEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MoglerEntity moglerEntity = ModEntities.MOGLER.create(world);
        if (moglerEntity != null) {
            moglerEntity.setPersistent();
        }
        return moglerEntity;
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockState blockState = world.getBlockState(new BlockPos(this.getX(), this.getY() - 1, this.getZ()));

        if (blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.AIR)) {
            return false;
        }

        return super.canSpawn(world, spawnReason);
    }

    @Override
    protected void zombify(ServerWorld world) {
        ZombifiedMoglerEntity zombifiedMoglerEntity = this.convertTo(ModEntities.ZOMBIFIED_MOGLER, true);
        if (zombifiedMoglerEntity != null) {
            zombifiedMoglerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
        }
    }
}
